package l0raxeo.sdw.scenes.game;

import l0raxeo.sdw.objects.GameObject;
import l0raxeo.sdw.scenes.Scene;

import java.awt.*;
import java.util.Comparator;

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
        GameState.setState(GameState.DRAFT);
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

        try {
            GameState.getGameState().initializer.update(dt);
        } catch (NullPointerException e) {
            System.out.println("[Game] - WARNING: Null Pointer Exception when updating the game state");
        }
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
