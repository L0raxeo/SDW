package net;

public record ClientInfo(String username, int uid)
{

    public String getUsername()
    {
        return username;
    }

    public int getUid()
    {
        return uid;
    }

}
