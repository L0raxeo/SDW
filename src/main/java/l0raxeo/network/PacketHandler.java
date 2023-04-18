package l0raxeo.network;

public interface PacketHandler
{

    void receive(String packet);
    void send();

}
