package l0raxeo.network.packetManagement;

import l0raxeo.rendering.Window;
import l0raxeo.sdw.components.networkComponents.GameObjectNetwork;

public class ClientPacketHandler
{

    public static void handleGameObjectNetworkAndComponentNetworkPacket(String packet, String[] parsedPacket)
    {
        try {
            Window.getScene()
                    .getGameObjectWithUid(Integer.parseInt(parsedPacket[1]))
                    .getComponent(GameObjectNetwork.class).receive(packet);
        } catch (NullPointerException e) {
            System.out.println("[Client] - WARNING: could not handle packet '" + parsedPacket[0] + "'");
        }
    }

    public static void handleConsoleMessagePacket(String msg)
    {
        System.out.println("[Server]: " + msg);
    }

}
