package net;

import java.net.InetAddress;

public record ClientInfoServer(InetAddress ipAddress, int port, String username, int uid)
{

    public String ipToString()
    {
        return ipAddress.toString();
    }

}
