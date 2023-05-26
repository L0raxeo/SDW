package l0raxeo.sdw.components;

import l0raxeo.sdw.objects.GameObject;

import java.awt.*;
import java.util.UUID;

public abstract class Component
{

    private String uid;

    public transient GameObject gameObject = null;

    public void start()
    {

    }

    public void update(double dt){}

    public void render(Graphics g) {}

    public void generateId()
    {
        this.uid = UUID.randomUUID().toString();
    }

    public String uid()
    {
        return uid;
    }

    public void onDestroy()
    {

    }

    public void handlePacketArgs(String args) {}

}
