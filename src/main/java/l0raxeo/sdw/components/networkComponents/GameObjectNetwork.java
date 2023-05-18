package l0raxeo.sdw.components.networkComponents;

import l0raxeo.sdw.components.Component;
import l0raxeo.sdw.dataStructure.exceptions.PacketMismatchException;
import l0raxeo.network.GameClient;
import l0raxeo.network.packetManagement.PacketTransceiver;
import org.joml.Vector2i;
import org.joml.Vector3i;

public class GameObjectNetwork extends Component implements PacketTransceiver
{

    /*
    Game Object Packet Format:
    packetId,gameObjectUid,xPos,yPos,zIndex,xSize,ySize,rotation,isDead,compUid,args
    Example:
    gon,0,64,64,16,16,false,3,hello_component
     */

    @Override
    public void receive(String packet) {
        String[] parsedPacket = packet.split(",");
        String packetId = parsedPacket[0];
        if (packetId.equals("gon"))
        {
            int gameObjectUid = Integer.parseInt(parsedPacket[1]);
            Vector3i position = new Vector3i(Integer.parseInt(parsedPacket[2]), Integer.parseInt(parsedPacket[3]), Integer.parseInt(parsedPacket[4]));
            Vector2i scale = new Vector2i(Integer.parseInt(parsedPacket[5]), Integer.parseInt(parsedPacket[6]));
            float rotation = Float.parseFloat(parsedPacket[7]);
            boolean isDead = Boolean.parseBoolean(parsedPacket[8]);
            handleGameObjectPacket(gameObjectUid, position, scale, rotation, isDead);
        }
        else if (packetId.equals("comp"))
        {
            String[] parsedCompPacket = packet.split(",", 4);
            int gameObjectUid = Integer.parseInt(parsedCompPacket[1]);
            int compUid = Integer.parseInt(parsedCompPacket[2]);
            String args = parsedCompPacket[3];
            handleComponentPacket(gameObjectUid, compUid, args);
        }
        else throw new PacketMismatchException("Received packet of ID '" + packetId + "' in a GameObjectNetwork that only receives packets of ID 'gon' and 'comp'");
    }

    @Override
    public void send()
    {
        String packet = "gon," +
                        gameObject.getUid() + "," +
                        gameObject.transform.worldPosition().x + "," +
                        gameObject.transform.worldPosition().y + "," +
                        gameObject.transform.scale.x + "," +
                        gameObject.transform.scale.y + "," +
                        gameObject.isDead();

        GameClient.getInstance().sendData(packet.getBytes());
    }

    private void handleGameObjectPacket(int gameObjectUid, Vector3i position, Vector2i scale, float rotation, boolean isDead)
    {
        if (gameObjectUid != gameObject.getUid())
            throw new PacketMismatchException("Received packet for object of UID '" + gameObjectUid + "' in a GameObjectNetwork of a GameObject with UID '" + gameObject.getUid() + "'");

        gameObject.transform.setPosition(position);
        gameObject.transform.scale = scale;
        gameObject.transform.rotation= rotation;

        if (isDead)
            gameObject.die();
    }

    private void handleComponentPacket(int gameObjectUid, int compUid, String args)
    {
        if (gameObjectUid != gameObject.getUid())
            throw new PacketMismatchException("Received packet for object of UID '" + gameObjectUid + "' in a GameObjectNetwork of a GameObject with UID '" + gameObject.getUid() + "'");

        gameObject.getComponent(compUid).handlePacketArgs(args);
    }

}
