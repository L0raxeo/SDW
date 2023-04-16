package com.l0raxeo.sdw.scenes;

import com.l0raxeo.sdw.components.PlayerController;
import com.l0raxeo.sdw.components.PlayerControlledTexture;
import com.l0raxeo.sdw.prefabs.Prefabs;
import org.joml.Vector2f;

import java.awt.*;

public class Arena extends Scene
{

    enum GameState
    {
        BUILD,
        FIGHT,
        SCORE
    }

    @Override
    public void init() {
        addGameObject(Prefabs.generate(
                "Noah",
                new Vector2f(10, 10),
                new Vector2f(46,76),
                new PlayerControlledTexture(),
                new PlayerController()
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
