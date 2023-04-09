package com.l0raxeo.sdw.scenes;

import com.l0raxeo.sdw.objects.GameObject;
import com.l0raxeo.sdw.prefabs.Prefabs;
import org.joml.Vector2f;

import java.awt.*;

public class MainMenu extends Scene{

    @Override
    public void loadResources() {
        setBackdrop(Color.DARK_GRAY);
    }

    @Override
    public void init()
    {
//        addGameObject(Prefabs.generate(
//                "Object 1",
//                new Vector2f(),
//                new Vector2f()
//        ));
//
//        addGameObject(Prefabs.generate(
//                "Object 2",
//                new Vector2f(),
//                new Vector2f()
//        ));
//
//        addGameObject(Prefabs.generate(
//                "Object 3",
//                new Vector2f(),
//                new Vector2f()
//        ));
    }

    @Override
    public void start()
    {

    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void render(Graphics g) {

    }
}
