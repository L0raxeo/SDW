package com.l0raxeo.sdw.scenes;

import com.l0raxeo.sdw.components.Component;
import com.l0raxeo.sdw.components.ImageTexture;
import com.l0raxeo.sdw.components.Item;
import com.l0raxeo.sdw.components.PlayerController;
import com.l0raxeo.sdw.components.PlayerControlledTexture;
import com.l0raxeo.sdw.file.FileLoader;
import com.l0raxeo.sdw.objects.GameObject;
import com.l0raxeo.sdw.prefabs.Prefabs;
import org.joml.Vector2i;

import java.awt.*;
import java.util.Comparator;

import static com.l0raxeo.sdw.components.ItemState.USED;

public class Arena extends Scene
{

    private final Comparator<GameObject> renderSorter = Comparator.comparingInt(c -> c.transform.getzIndex());

    enum GameState
    {
        DRAFT,
        BUILD,
        FIGHT,
        DEATH,
        SCORE
    }

    @Override
    public void loadResources() {
        setBackdrop(Color.LIGHT_GRAY);
    }

    @Override
    public void init() {
        addGameObject(Prefabs.generate(
                "Noah",
                new Vector2i(),
                new Vector2i(46,76),
                new PlayerControlledTexture(),
                new PlayerController()
        ));

        addGameObject(Prefabs.generate(
                "Cat",
                new Vector2i(),
                new Vector2i(24, 34),
                new Item(getGameObject("Noah"), USED),
                new ImageTexture(
                        FileLoader.loadImage("assets/textures/entities/items/hairball_spitting_cat.png"),
                        new Vector2i(12, 17)
                )
        ));
    }

    @Override
    public void update(double dt)
    {
        getGameObjects().sort(renderSorter);
        getGameObjects().forEach
                (
                gameObject -> gameObject.update(dt)
                );
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
