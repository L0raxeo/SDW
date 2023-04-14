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
    private int ID_COUNTER = 0;

    private final List<ClientInfoServer> connectedClients = new ArrayList<>();

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
                    System.out.println("[Server] - WARNING: closed socket cannot receive datagram packets in GameServer.");
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
            case "l" -> addConnection(new ClientInfoServer(address, port, parsedPacket[1], ID_COUNTER++));
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

    public void sendData(String data, InetAddress ipAddress, int port)
    {
        sendData(data.getBytes(), ipAddress, port);
    }

    public void sendDataToAllClients(byte[] data)
    {
        for (ClientInfoServer c : connectedClients)
        {
            sendData(data, c.ipAddress(), c.port());
        }
    }

    public void sendDataToAllClients(String data)
    {
        sendDataToAllClients(data.getBytes());
    }

    private void addConnection(ClientInfoServer client)
    {
        boolean alreadyConnected = false;

        for (ClientInfoServer c : connectedClients)
        {
            if (c.ipAddress().equals(client.ipAddress())) {
                alreadyConnected = true;
                break;
            }
        }

        if (!alreadyConnected)
        {
            connectedClients.add(client);
            System.out.println("[Server]: Connection added: " + client.username());
            sendData("lc," + client.uid(), client.ipAddress(), client.port());
            sendDataToAllClients(generatePlayerListPacket());
        }
    }

    public String generatePlayerListPacket()
    {
        StringBuilder packetBuilder = new StringBuilder("pl,");

        for (ClientInfoServer cif : connectedClients)
            packetBuilder.append(cif.username()).append(",").append(cif.uid()).append(",");

        packetBuilder.deleteCharAt(packetBuilder.length() - 1);
        return packetBuilder.toString();
    }

    public static GameServer getInstance()
    {
        return instance;
    }

    public static GameServer createInstance(int port)
    {
        if (instance != null)
            throw new DuplicateNetworkException("[Server]: Cannot create another instance of GameServer");
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
