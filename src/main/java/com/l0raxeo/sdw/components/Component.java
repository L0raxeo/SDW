package com.l0raxeo.sdw.components;

import com.l0raxeo.sdw.objects.GameObject;

import java.awt.*;

public abstract class Component
{

    // component class in general
    private static int ID_COUNTER = 0;
    // associated with individual components/objects
    private int uid = -1;

    public transient GameObject gameObject = null;

    public void start()
    {

    }

    public void update(double dt){}

    public void render(Graphics g) {}

    public void generateId()
    {
        if (this.uid == -1)
            this.uid = ID_COUNTER++;
    }

    public void onDestroy()
    {

    }

}
