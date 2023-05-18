package l0raxeo.sdw.scenes.game;

import l0raxeo.sdw.objects.GameObject;
import l0raxeo.sdw.scenes.Scene;
import l0raxeo.sdw.scenes.assetLoaders.GameAssets;
import l0raxeo.sdw.scenes.game.map.MapHandler;

import java.awt.*;
import java.util.Comparator;
import java.util.ConcurrentModificationException;

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
        if (!gameAssets.assetsLoaded)
            return;

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
        if (!gameAssets.assetsLoaded)
        {
            gameAssets.renderLoadingScreen(g);
            return;
        }

        try {
            GameState.getGameState().initializer.render(g);
        } catch (NullPointerException e) {
            System.out.println("[Game] - WARNING: Null Pointer Exception when rendering game state");
        }

        try {
            getGameObjects().forEach(
                    gameObject -> gameObject.getAllComponents().forEach(
                            component -> component.render(g)
                    )
            );
        } catch (ConcurrentModificationException e) {
            System.out.println("[Game] - WARNING: Concurrent Modification Exception when rendering game objects");
        }
    }

    @Override
    public void onDestroy()
    {
        gameAssets.unloadAssets();
    }

}
