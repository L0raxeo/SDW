package com.l0raxeo.sdw.components;

import com.l0raxeo.sdw.input.mouse.MouseManager;
import com.l0raxeo.sdw.objects.GameObject;
import com.l0raxeo.sdw.window.Camera;
import org.joml.Vector2i;

public class Item extends Component
{

    private GameObject parent;
    private ItemState itemState;

    public Item()
    {

    }

    public Item(GameObject parent, ItemState itemState)
    {
        this.parent = parent;
        this.itemState = itemState;
    }

    @Override
    public void update(double dt) {
        float parentTheta = Camera.getAngleFromOriginToTarget(
                parent.transform.position().x + (parent.transform.scale.x / 2),
                parent.transform.position().y + (parent.transform.scale.y / 2),
                MouseManager.getMouseX(),
                MouseManager.getMouseY()
        );

        if ((parentTheta >= 270 && parentTheta <= 360) || (parentTheta >= 0 && parentTheta <= 90))
        {
            gameObject.transform.setPosition(parent.transform.position().sub(0, parent.transform.scale.y / 2).add((int) (parent.transform.scale.x * .85), 0));
            gameObject.getComponent(ImageTexture.class).setFlip(false);
        }
        else if (parentTheta > 90 && parentTheta < 270)
        {
            gameObject.getComponent(ImageTexture.class).setFlip(true);
            gameObject.transform.setPosition(parent.transform.position().sub(0, parent.transform.scale.y / 2));
        }

        gameObject.transform.rotation = -Camera.getAngleFromOriginToTarget(
                gameObject.transform.position().x,
                gameObject.transform.position().y,
                MouseManager.getMouseX(),
                MouseManager.getMouseY()
        );

        if (gameObject.transform.rotation < 0 && gameObject.transform.rotation > -180)
            gameObject.transform.setzIndex(-1);
        else
            gameObject.transform.setzIndex(1);
    }

}

