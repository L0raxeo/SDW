package net;

import com.l0raxeo.sdw.components.GameObjectNetwork;
import com.l0raxeo.sdw.exceptions.DuplicateNetworkException;
import com.l0raxeo.sdw.window.Window;

import java.io.IOException;
import java.net.*;

public class GameClient extends Thread
{

    private static GameClient instance;

    private InetAddress ipAddress;
    private int port;
    private DatagramSocket socket;

    private boolean connected = true;

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
            case "cm" -> System.out.println(parsedPacket[1]);
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

}
