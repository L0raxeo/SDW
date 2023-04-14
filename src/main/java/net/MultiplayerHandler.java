package net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MultiplayerHandler
{

    public static GameServer socketServer;
    public static GameClient socketClient;

    public static String createHost(int port, String username)
    {
        InetAddress ipAddress = null;

        try {
            ipAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // Server Host
        GameServer socketServer = GameServer.createInstance(port);
        socketServer.start();

        // Game Client
        assert ipAddress != null;
        createClient(ipAddress.getHostAddress(), port, username);

        MultiplayerHandler.socketServer = socketServer;

        return ipAddress.getHostAddress();
    }

    public static void createClient(String joinCode, int port, String username)
    {
        InetAddress ipAddress = null;
        try {
            ipAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // Create Client
        assert ipAddress != null;
        GameClient socketClient = GameClient.createInstance(joinCode, port);
        socketClient.start();
        socketClient.sendData("l," + username);

        MultiplayerHandler.socketClient = socketClient;
    }

    public static void destroyGameServer()
    {
        socketServer.interrupt();
        GameServer.destroyGameServer();
        MultiplayerHandler.socketServer = null;
    }

    private static void destroyGameClient()
    {
        socketClient.interrupt();
        GameClient.destroyGameClient();
        MultiplayerHandler.socketClient = null;
    }

    public static void disconnectClient()
    {
        if (socketServer != null)
            destroyGameServer();
        else
            socketClient.sendData("lo," + MultiplayerHandler.socketClient.myUid);
        destroyGameClient();
    }

}
