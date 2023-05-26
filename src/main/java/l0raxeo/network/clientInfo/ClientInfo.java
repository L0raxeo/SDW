package l0raxeo.network.clientInfo;

public record ClientInfo(String username, String uid)
{

    public String getUsername()
    {
        return username;
    }

    public String getUid()
    {
        return uid;
    }

}
