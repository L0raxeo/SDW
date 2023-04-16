package com.l0raxeo.sdw.components;

import com.l0raxeo.sdw.input.keyboard.KeyManager;
import com.l0raxeo.sdw.input.mouse.MouseManager;
import org.joml.Vector2f;

public class PlayerController extends Component
{

    /**
     * The angle (in degrees) that the player is looking [0,360)
     */
    private float direction = 0;

    public float xOffset = 0;
    public float yOffset = 0;

    @Override
    public void update(double dt)
    {
        move();
        look();
    }

    private void move()
    {
        xOffset = 0;
        yOffset = 0;
        boolean w = false, s = false, a = false, d = false;
        if (KeyManager.isHeld('w'))
            w = true;
        if (KeyManager.isHeld('s'))
            s = true;
        if (KeyManager.isHeld('a'))
            a = true;
        if (KeyManager.isHeld('d'))
            d = true;

        if (w)
        {
            if (a)
            {
                xOffset = -3;
                yOffset = 3;
            }
            else if (d)
            {
                xOffset = 3;
                yOffset = 3;
            }
            else yOffset = 4;
        }
        else if (s)
        {
            if (a)
            {
                xOffset = -3;
                yOffset = -3;
            }
            else if (d)
            {
                xOffset = 3;
                yOffset = -3;
            }
            else yOffset = -4;
        }
        else if (d)
            xOffset = 4;
        else if (a) xOffset = -4;

        gameObject.transform.move(new Vector2f(xOffset, yOffset));
    }

    private void look()
    {
        int xMouse = MouseManager.getMouseX();
        int yMouse = MouseManager.getGraphMouseY();
        int xOrigin = (int) (gameObject.transform.position().x + (gameObject.transform.scale.x / 2));
        int yOrigin = (int) (gameObject.transform.position().y - (gameObject.transform.scale.y / 3));
        float theta = 0;

        if (xMouse < xOrigin && yMouse < yOrigin)
        {
            theta = (float) (Math.toDegrees(Math.atan((float) (yMouse - yOrigin) / (xMouse - xOrigin))) + 180);
        }
        else if (xMouse > xOrigin) {
            theta = (float) Math.toDegrees(Math.atan((float) (yMouse - yOrigin) / (xMouse - xOrigin)));
            if (theta < 0) theta += 360;
        }
        else if (xMouse == xOrigin)
        {
            if (yMouse > yOrigin)
                theta = 90;
            else if (yMouse < yOrigin)
                theta = 270;
        }
        else {
            theta = (float) Math.toDegrees(Math.acos((xMouse - xOrigin) / (Math.sqrt(Math.pow(xMouse - xOrigin, 2) + Math.pow(yMouse - yOrigin, 2)))));
        }

        direction = theta;
    }

    public float getDirection()
    {
        return direction;
    }

}
