package l0raxeo.sdw.network;

public interface PacketHandler
{

    void receive(String packet);
    void send();

}
