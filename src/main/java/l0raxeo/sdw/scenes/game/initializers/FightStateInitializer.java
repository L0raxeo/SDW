package l0raxeo.sdw.scenes.game.initializers;

import l0raxeo.network.GameClient;
import l0raxeo.rendering.Window;
import l0raxeo.sdw.scenes.game.Game;
import l0raxeo.sdw.scenes.game.items.ItemHandler;
import l0raxeo.sdw.scenes.game.map.MapHandler;

import java.awt.*;

public class FightStateInitializer implements GameStateInitializer
{

    private final Game gameScene;
    private final ItemHandler itemHandler;
    private final MapHandler mapHandler;

    public FightStateInitializer(Game gameScene)
    {
        this.gameScene = gameScene;
        this.mapHandler = gameScene.mapHandler;
        this.itemHandler = mapHandler.itemHandler;
    }

    @Override
    public void loadResources()
    {
        Window.setBackdrop(Color.DARK_GRAY);
    }

    @Override
    public void start()
    {
        // TODO get times from ItemHandler and send them to the server
        GameClient.getInstance().sendData("np," + GameClient.getInstance().myUid + "," + 0 + "," + 0);
    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void render(Graphics g) {
        gameScene.mapHandler.render(g);
    }

}
