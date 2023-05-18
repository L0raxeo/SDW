package l0raxeo.sdw.scenes.game.initializers;

import l0raxeo.network.GameClient;
import l0raxeo.network.MultiplayerHandler;
import l0raxeo.rendering.Window;
import l0raxeo.sdw.components.entityComponents.playerComponents.PlayerAttributes;
import l0raxeo.sdw.components.itemComponents.ItemComponent;
import l0raxeo.sdw.objects.GameObject;
import l0raxeo.sdw.scenes.assetLoaders.LoadingScreen;
import l0raxeo.sdw.scenes.game.Game;
import l0raxeo.sdw.scenes.game.map.items.ItemHandler;
import l0raxeo.sdw.scenes.game.map.MapHandler;
import l0raxeo.sdw.scenes.game.map.items.ItemState;

import java.awt.*;

public class FightStateInitializer extends GameStateInitializer
{

    private final Game gameScene;
    private final ItemHandler itemHandler;
    private final MapHandler mapHandler;

    private final LoadingScreen loadingScreen;

    private boolean initialized = false; // TODO remove this

    public FightStateInitializer(Game gameScene)
    {
        this.gameScene = gameScene;
        this.mapHandler = gameScene.mapHandler;
        this.itemHandler = mapHandler.itemHandler;
        this.loadingScreen = new LoadingScreen(1000);
    }

    @Override
    public void loadResources()
    {
        Window.setBackdrop(Color.DARK_GRAY);
    }

    @Override
    public void start()
    {
        GameClient.getInstance().sendData("np," + GameClient.getInstance().myUid + "," + 0 + "," + 0);

        loadingScreen.load();
    }

    @Override
    public void update(double dt) {
        if (gameScene.getGameObjectsWithComponent(PlayerAttributes.class).size() >= GameClient.getInstance().clientList.size())
        {
            loadingScreen.isLoading = false;
        }

        if (!initialized && !loadingScreen.isLoading)
        {
            System.out.println(loadingScreen.isLoading);
            for (GameObject player : gameScene.getGameObjectsWithComponent(PlayerAttributes.class))
            {
                if (player.getComponent(PlayerAttributes.class).uid == GameClient.getInstance().myUid)
                {
                    gameScene.player = player;
                    break;
                }
            }

            // TODO get times from ItemHandler and send them to the server
            for (GameObject gameObject : itemHandler.retrieveAllStoredGameObjects())
            {
                if (gameObject.hasComponent(ItemComponent.class))
                {
                    gameObject.getComponent(ItemComponent.class).assignToParent(gameScene.player);
                    gameObject.getComponent(ItemComponent.class).setItemState(ItemState.USED);
                    gameScene.addGameObject(gameObject);
                }
            }
            initialized = true;
        }
    }

    @Override
    public void render(Graphics g) {
        gameScene.mapHandler.render(g);

        if (loadingScreen.isLoading)
        {
            loadingScreen.renderLoadingScreen(g);
        }
    }

}
