package net;

import com.l0raxeo.sdw.components.GameObjectNetwork;
import com.l0raxeo.sdw.exceptions.DuplicateNetworkException;
import com.l0raxeo.sdw.window.Window;

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
            case "gon" -> Window.getScene()
                    .getGameObjectWithUid(Integer.parseInt(parsedPacket[1]))
                    .getComponent(GameObjectNetwork.class).receive(strPacket);
            case "cm" -> System.out.println("[Client]: " + parsedPacket[1]);
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
