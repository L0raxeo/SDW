package com.l0raxeo.sdw.components;

import com.l0raxeo.sdw.input.keyboard.KeyManager;
import org.joml.Vector2f;

public class PlayerInput extends Component
{

    @Override
    public void update(double dt)
    {
        if (KeyManager.isHeld('w'))
            gameObject.transform.move(new Vector2f(0, 5));
        if (KeyManager.isHeld('s'))
            gameObject.transform.move(new Vector2f(0, -5));
        if (KeyManager.isHeld('a'))
            gameObject.transform.move(new Vector2f(-5, 0));
        if (KeyManager.isHeld('d'))
            gameObject.transform.move(new Vector2f(5, 0));
    }

}
