package net;

import com.l0raxeo.sdw.ui.GuiLayer;
import com.l0raxeo.sdw.ui.GuiTextField;
import com.l0raxeo.sdw.utils.Utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MultiplayerHandler
{

    public static GameServer socketServer;
    public static GameClient socketClient;

    public static String createHost(int port)
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
        createClient(ipAddress.getHostAddress(), port);

        MultiplayerHandler.socketServer = socketServer;

        return ipAddress.getHostAddress();
    }

    public static void createClient(String joinCode, int port)
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
        socketClient.sendData("l");

        MultiplayerHandler.socketClient = socketClient;
    }

    public static void destroyGameServer()
    {
        GameServer.destroyGameServer();
        MultiplayerHandler.socketServer = null;
    }

    public static void destroyGameClient()
    {
        GameClient.destroyGameClient();
        MultiplayerHandler.socketClient = null;
    }

}
