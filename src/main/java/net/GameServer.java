package net;

import com.l0raxeo.sdw.components.GameObjectNetwork;
import com.l0raxeo.sdw.exceptions.DuplicateNetworkException;
import com.l0raxeo.sdw.window.Window;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.channels.AsynchronousCloseException;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread
{

    private static GameServer instance;

    private DatagramSocket socket;
    private boolean connected = true;

    private final List<ClientInfo> connectedClients = new ArrayList<>();

    private GameServer(int port)
    {
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        while (connected)
        {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);

            try {
                try {
                    socket.receive(packet);
                } catch (SocketException | AsynchronousCloseException e) {
                    System.out.println("[WARNING]: closed socket cannot receive datagram packets in GameServer.");
                }
            } catch (IOException e) {
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
            case "l" -> addConnection(new ClientInfo(address, port));
            case "gon" -> Window.getScene()
                    .getGameObjectWithUid(Integer.parseInt(parsedPacket[1]))
                    .getComponent(GameObjectNetwork.class).receive(strPacket);
            case "cm" -> System.out.println(parsedPacket[1]);
        }
    }

    /**
     * @param ipAddress of receiver
     */
    public void sendData(byte[] data, InetAddress ipAddress, int port)
    {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);

        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDataToAllClients(byte[] data)
    {
        for (ClientInfo c : connectedClients)
        {
            sendData(data, c.ipAddress(), c.port());
        }
    }

    private void addConnection(ClientInfo client)
    {
        boolean alreadyConnected = false;

        for (ClientInfo c : connectedClients)
        {
            if (c.ipAddress().equals(client.ipAddress())) {
                alreadyConnected = true;
                break;
            }
        }

        if (!alreadyConnected)
        {
            connectedClients.add(client);
            System.out.println("Connection added: " + client.ipToString());
            sendDataToAllClients("cm,Connection Added!".getBytes());
        }
    }

    public static GameServer getInstance()
    {
        return instance;
    }

    public static GameServer createInstance(int port)
    {
        if (instance != null)
            throw new DuplicateNetworkException("Cannot create another instance of GameServer");
        instance = new GameServer(port);
        return instance;
    }

    public static void destroyGameServer()
    {
        instance.connected = false;
        instance.socket.close();
        instance = null;
    }

}
