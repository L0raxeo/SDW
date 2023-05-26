package l0raxeo.network.clientInfo;

import java.net.InetAddress;

public record ClientInfoServer(InetAddress ipAddress, int port, String username, String uid)
{

    public String ipToString()
    {
        return ipAddress.toString();
    }

}
