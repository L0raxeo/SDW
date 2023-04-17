package com.l0raxeo.sdw.components;

import com.l0raxeo.sdw.input.keyboard.KeyManager;
import com.l0raxeo.sdw.input.mouse.MouseManager;
import com.l0raxeo.sdw.window.Camera;
import com.l0raxeo.sdw.window.Window;
import org.joml.Vector2i;

public class PlayerController extends Component
{

    /**
     * The angle (in degrees) that the player is looking [0,360)
     */
    private float direction = 0;

    public int xOffset = 0;
    public int yOffset = 0;

    public int xCam;
    public int yCam;

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

        gameObject.transform.move(new Vector2i(xOffset, yOffset));
    }

    private void look()
    {
        direction = Camera.getAngleFromOriginToTarget(
                gameObject.transform.position().x + (gameObject.transform.scale.x / 2),
                gameObject.transform.position().y - (gameObject.transform.scale.y / 3),
                MouseManager.getMouseX(), MouseManager.getGraphMouseY()
        );

//        xCam = gameObject.transform.position().x;
//        yCam = gameObject.transform.position().y;
//
//        xCam += Math.pow(MouseManager.getMouseX() - xCam, 0);
//        yCam += Math.pow(MouseManager.getMouseY() - yCam, 0);
//
//        Camera.setPosition(new Vector2i(-((Window.WINDOW_WIDTH / 2) - xCam), -((Window.WINDOW_HEIGHT / 2) - yCam)));
    }

    public float getDirection()
    {
        return direction;
    }

}
