package net;

import java.net.InetAddress;

public record ClientInfo(InetAddress ipAddress, int port)
{

    public String ipToString()
    {
        return ipAddress.toString();
    }

}
