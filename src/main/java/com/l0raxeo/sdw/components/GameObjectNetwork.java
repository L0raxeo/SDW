package com.l0raxeo.sdw.components;

import com.l0raxeo.sdw.exceptions.PacketMismatchException;
import net.GameClient;
import net.PacketHandler;
import org.joml.Vector2f;

public class GameObjectNetwork extends Component implements PacketHandler
{

    /*
    Packet Format:
    packetId,gameObjectUid,xPos,yPos,xSize,ySize,isDead,compUid,args
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
            Vector2f position = new Vector2f(Float.parseFloat(parsedPacket[2]), Float.parseFloat(parsedPacket[3]));
            Vector2f scale = new Vector2f(Float.parseFloat(parsedPacket[4]), Float.parseFloat(parsedPacket[5]));
            boolean isDead = Boolean.parseBoolean(parsedPacket[6]);
            handleGameObjectPacket(gameObjectUid, position,scale,isDead);
        }
        else if (packetId.equals("comp"))
        {
            int gameObjectUid = Integer.parseInt(parsedPacket[1]);
            int compUid = Integer.parseInt(parsedPacket[2]);
            String args = parsedPacket[3];
            handleComponentPacket(gameObjectUid, compUid, args);
        }
        else throw new PacketMismatchException("Received packet of ID '" + packetId + "' in a GameObjectNetwork that only receives packets of ID 'gon' and 'comp'");
    }

    @Override
    public void send()
    {
        String packet = "gon," +
                        gameObject.getUid() + "," +
                        gameObject.transform.position().x + "," +
                        gameObject.transform.position().y + "," +
                        gameObject.transform.scale.x + "," +
                        gameObject.transform.scale.y + "," +
                        gameObject.isDead();

        GameClient.getInstance().sendData(packet.getBytes());
    }

    private void handleGameObjectPacket(int gameObjectUid, Vector2f position, Vector2f scale, boolean isDead)
    {
        if (gameObjectUid != gameObject.getUid())
            throw new PacketMismatchException("Received packet for object of UID '" + gameObjectUid + "' in a GameObjectNetwork of a GameObject with UID '" + gameObject.getUid() + "'");

        gameObject.transform.setPosition(position);
        gameObject.transform.scale = scale;

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
