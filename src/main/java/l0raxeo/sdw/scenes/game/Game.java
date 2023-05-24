package l0raxeo.sdw.scenes.game;

import l0raxeo.sdw.objects.GameObject;
import l0raxeo.sdw.scenes.Scene;
import l0raxeo.sdw.scenes.assetLoaders.GameAssets;
import l0raxeo.sdw.scenes.game.map.MapHandler;

import java.awt.*;
import java.util.Comparator;
import java.util.Iterator;

public class Game extends Scene
{

    private final Comparator<GameObject> renderSorter = Comparator.comparingInt(c -> c.transform.getzIndex());
    public final MapHandler mapHandler = new MapHandler();

    private final GameAssets gameAssets = new GameAssets(1000);

    public GameObject player;

    @Override
    public void loadResources()
    {
        gameAssets.loadAssets();
        setBackdrop(Color.LIGHT_GRAY);
    }

    @Override
    public void init()
    {
        GameState.setState(GameState.DRAFT);
    }

    @Override
    public void update(double dt)
    {
        if (gameAssets.assetsLoaded)
        {
            getGameObjects().sort(renderSorter);
            updateGameObjects(dt);
            GameState.getGameState().initializer.update(dt);
        }
    }

    private void updateGameObjects(double dt)
    {
        Iterator<GameObject> iterator = getGameObjects().iterator();
        while (iterator.hasNext())
        {
            GameObject gameObject = iterator.next();
            if (gameObject.isDead())
            {
                iterator.remove();
                continue;
            }

            gameObject.update(dt);
        }
    }

    @Override
    public void render(Graphics g)
    {
        if (!renderLoadingScreen(g))
        {
            GameState.getGameState().initializer.render(g);
            getGameObjects().forEach(gameObject -> gameObject.render(g));
        }
    }

    private boolean renderLoadingScreen(Graphics g)
    {
        if (!gameAssets.assetsLoaded)
        {
            gameAssets.renderLoadingScreen(g);
            return true;
        }

        return false;
    }

    @Override
    public void onDestroy()
    {
        gameAssets.unloadAssets();
    }

}
