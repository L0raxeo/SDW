package l0raxeo.network.packetManagement;

import l0raxeo.rendering.AppWindow;
import l0raxeo.sdw.components.networkComponents.GameObjectNetwork;

public class ClientPacketHandler
{

    public static void handleGameObjectNetworkAndComponentNetworkPacket(String packet, String[] parsedPacket)
    {
        try {
            AppWindow.getScene()
                    .getGameObjectWithUid(Integer.parseInt(parsedPacket[1]))
                    .getComponent(GameObjectNetwork.class).receive(packet);
        } catch (NullPointerException e) {
            e.printStackTrace();
            //System.out.println("[Client] - WARNING: could not handle packet '" + parsedPacket[0] + "'");
        }
    }

    public static void handleConsoleMessagePacket(String msg)
    {
        System.out.println("[Server]: " + msg);
    }

}
