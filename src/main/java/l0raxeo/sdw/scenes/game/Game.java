package l0raxeo.sdw.scenes.game;

import l0raxeo.sdw.objects.GameObject;
import l0raxeo.sdw.scenes.Scene;
import l0raxeo.sdw.scenes.game.initializers.GameStateInitializer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Game extends Scene
{

    private final Comparator<GameObject> renderSorter = Comparator.comparingInt(c -> c.transform.getzIndex());

    @Override
    public void loadResources()
    {
        setBackdrop(Color.LIGHT_GRAY);
    }

    @Override
    public void init()
    {
        GameState.setGameState(GameState.DRAFT);
    }

    @Override
    public void start()
    {

    }

    @Override
    public void update(double dt)
    {
        getGameObjects().sort(renderSorter);
        getGameObjects().forEach
                (
                gameObject -> gameObject.update(dt)
                );

        GameState.getGameState().initializer.update(dt);
    }

    @Override
    public void render(Graphics g)
    {
        getGameObjects().forEach(
                gameObject -> gameObject.getAllComponents().forEach(
                        component -> component.render(g)
                )
        );

        GameState.getGameState().initializer.render(g);
    }

}
