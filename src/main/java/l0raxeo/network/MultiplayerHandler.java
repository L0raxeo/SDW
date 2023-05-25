package l0raxeo.network;

import l0raxeo.network.clientInfo.ClientInfoServer;
import l0raxeo.rendering.AppWindow;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MultiplayerHandler
{

    public static GameServer socketServer;
    public static GameClient socketClient;

    private static long lastClientConnectionTest = System.currentTimeMillis();
    public static boolean clientConnectionValid = true;
    //<UID, validConnection> checking if the clients are still connected and responding
    public static Hashtable<Integer, Boolean> serverConnectionsValid = new Hashtable<>();

    public static String rawHost = null;
    public static int port = -1;
    public static int CONNECTION_TEST_DELAY = 10000;

    public static void localThreadUpdate()
    {
        if (GameClient.isConnected() && lastClientConnectionTest + CONNECTION_TEST_DELAY < System.currentTimeMillis())
        {
            if (GameServer.isConnected())
            {
                List<ClientInfoServer> toDisconnect = new ArrayList<>();
                for (ClientInfoServer cis : socketServer.getConnectedClients())
                    if (!serverConnectionsValid.get(cis.uid()))
                    {
                        toDisconnect.add(cis);
                    }

                for (ClientInfoServer cis : toDisconnect)
                    socketServer.removeConnection(cis);

                serverConnectionsValid.forEach((k,v) -> serverConnectionsValid.replace(k, false));

                socketServer.sendDataToAllClients("cts,0");
            }

            if (!clientConnectionValid)
            {
                disconnectClient();
                AppWindow.getScene().init();
                serverConnectionsValid.clear();
                clientConnectionValid = true;
                return;
            }

            lastClientConnectionTest = System.currentTimeMillis();
            socketClient.sendData("ctc,0");
            clientConnectionValid = false;
        }
    }

    public static void createHost(int port, String username)
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
    }

    public static void createClient(String rawHost, int port, String username)
    {
        InetAddress ipAddress = null;
        try {
            ipAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // Create Client
        assert ipAddress != null;
        GameClient socketClient = GameClient.createInstance(rawHost, port);
        socketClient.start();
        socketClient.sendData("l," + username);
        MultiplayerHandler.rawHost = ipAddress.getHostAddress();
        MultiplayerHandler.port = port;

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

    public static boolean isHosting()
    {
        return socketServer != null;
    }

    public static void getNetworkIPs() {
        final byte[] ip;
        try {
            ip = InetAddress.getLocalHost().getAddress();
        } catch (Exception e) {
            return;     // exit method, otherwise "ip might not have been initialized"
        }

        for(int i=1;i<=254;i++) {
            final int j = i;  // i as non-final variable cannot be referenced from inner class
            // new thread for parallel execution
            new Thread(() -> {
                try {
                    ip[3] = (byte)j;
                    InetAddress address = InetAddress.getByAddress(ip);
                    String output = address.toString().substring(1);
                    if (address.isReachable(5000)) {
                        System.out.println(output + " is on the network");
                    } else {
                        System.out.println("Not Reachable: "+output);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();     // dont forget to start the thread
        }
    }

}
