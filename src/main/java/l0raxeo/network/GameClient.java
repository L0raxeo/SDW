package l0raxeo.network;

import l0raxeo.network.clientInfo.ClientInfo;
import l0raxeo.network.packetManagement.ClientPacketHandler;
import l0raxeo.sdw.components.Component;
import l0raxeo.sdw.components.entityComponents.HealthSystem;
import l0raxeo.sdw.components.entityComponents.playerComponents.PlayerAttributes;
import l0raxeo.sdw.components.entityComponents.playerComponents.PlayerInventory;
import l0raxeo.sdw.components.networkComponents.GameObjectNetwork;
import l0raxeo.sdw.components.entityComponents.playerComponents.PlayerControlledTexture;
import l0raxeo.sdw.components.entityComponents.playerComponents.PlayerController;
import l0raxeo.sdw.dataStructure.exceptions.DuplicateNetworkException;
import l0raxeo.rendering.AppWindow;
import l0raxeo.sdw.objects.GameObject;
import l0raxeo.sdw.prefabs.Prefabs;
import l0raxeo.sdw.scenes.game.Game;
import l0raxeo.sdw.scenes.game.GameState;
import org.joml.Vector2i;
import org.joml.Vector3i;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class GameClient extends Thread
{

    private static GameClient instance;
    public final List<ClientInfo> clientList = new ArrayList<>();
    public int myUid;

    private InetAddress ipAddress;
    private int port;
    private DatagramSocket socket;

    public boolean connected = true;

    private GameClient(String ipAddress, int port)
    {
        try
        {
            this.port = port;
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        }
        catch (SocketException | UnknownHostException e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        while (connected)
        {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);

            try
            {
                socket.receive(packet);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port)
    {
        String strPacket = new String(data).trim();
        String[] parsedPacket = strPacket.split(",");
        switch (parsedPacket[0])
        {
            case "gon", "comp" -> ClientPacketHandler.handleGameObjectNetworkAndComponentNetworkPacket(strPacket, parsedPacket);
            case "cm" -> ClientPacketHandler.handleConsoleMessagePacket(parsedPacket[1]);
            case "lc" -> {
                myUid = Integer.parseInt(parsedPacket[1]);
                System.out.println("[Client]: Connected to server! My UID: " + myUid);
            }
            case "pl" -> {
                getClientList().clear();
                for (int i = 1; i < parsedPacket.length; i += 2)
                    getClientList().add(new ClientInfo(
                            parsedPacket[i],
                            Integer.parseInt(parsedPacket[i + 1]))
                    );
            }
            case "lo" -> getClientList().remove(getPlayerClientInfo(Integer.parseInt(parsedPacket[1])));
            case "ctc" -> {
                if (Integer.parseInt(parsedPacket[1]) == 1)
                    MultiplayerHandler.clientConnectionValid = true;
            }
            case "cts" -> {
                if (Integer.parseInt(parsedPacket[1]) == 0)
                    sendData("cts,1");
            }
            case "sm" -> AppWindow.changeScene(Game.class);
            case "np" -> {
                Component[] playerComponents = {
                        new PlayerControlledTexture(),
                        Integer.parseInt(parsedPacket[1]) == myUid ? new PlayerController(Integer.parseInt(parsedPacket[1])): null,
                        new GameObjectNetwork(),
                        new HealthSystem(true),
                        new PlayerAttributes(Integer.parseInt(parsedPacket[1])),
                        Integer.parseInt(parsedPacket[1]) == myUid ? new PlayerInventory() : null
                };

                AppWindow.getScene().addGameObject(Prefabs.generate(
                        getPlayerClientInfo(Integer.parseInt(parsedPacket[1])).getUsername(),
                        new Vector3i(Integer.parseInt(parsedPacket[2]), Integer.parseInt(parsedPacket[3]), 0),
                        new Vector2i(46, 76),
                        playerComponents
                ));
            }
            case "goidu" -> GameObject.checkAndUpdateIdCounter(Integer.parseInt(parsedPacket[1]));
            case "cidu" -> Component.checkAndUpdateIdCounter(Integer.parseInt(parsedPacket[1]));
            case "gbs" -> GameState.setState(GameState.BUILD);
            case "gfs" -> GameState.setState(GameState.FIGHT);
        }
    }

    public void sendData(byte[] data)
    {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);

        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(String data)
    {
        sendData(data.getBytes());
    }

    public static GameClient getInstance()
    {
        return instance;
    }

    public static GameClient createInstance(String ipAddress, int port)
    {
        if (instance != null)
            throw new DuplicateNetworkException("Cannot create another instance of GameClient");
        instance = new GameClient(ipAddress, port);
        return instance;
    }

    public static void destroyGameClient()
    {
        instance.connected = false;
        instance = null;
    }

    public List<ClientInfo> getClientList()
    {
        return clientList;
    }

    public ClientInfo getPlayerClientInfo(int uid)
    {
        for (ClientInfo ci : getClientList())
            if (ci.uid() == uid)
                return ci;

        return null;
    }

    public static boolean isConnected()
    {
        if (instance == null)
            return false;

        return instance.connected;
    }

}
