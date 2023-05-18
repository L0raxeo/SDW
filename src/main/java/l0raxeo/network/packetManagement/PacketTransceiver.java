package l0raxeo.network.packetManagement;

public interface PacketTransceiver
{

    void receive(String packet);
    void send();

}
