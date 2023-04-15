package com.l0raxeo.sdw.scenes;

import com.l0raxeo.sdw.components.PlayerInput;
import com.l0raxeo.sdw.components.PlayerTexture;
import com.l0raxeo.sdw.prefabs.Prefabs;
import org.joml.Vector2f;

import java.awt.*;

public class Arena extends Scene
{

    @Override
    public void init() {
        addGameObject(Prefabs.generate(
                "Noah",
                new Vector2f(10, 10),
                new Vector2f(46,76),
                new PlayerTexture(),
                new PlayerInput()
        ));
    }

    @Override
    public void update(double dt)
    {
        getGameObjects().forEach(gameObject -> gameObject.update(dt));
    }

    @Override
    public void render(Graphics g)
    {
        getGameObjects().forEach(
                gameObject -> gameObject.getAllComponents().forEach(
                        component -> component.render(g)
                )
        );
    }

}
