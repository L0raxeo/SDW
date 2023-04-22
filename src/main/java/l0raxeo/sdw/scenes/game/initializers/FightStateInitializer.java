package l0raxeo.sdw.scenes.game.initializers;

import l0raxeo.network.GameClient;
import l0raxeo.sdw.scenes.game.Game;

import java.awt.*;

public class FightStateInitializer implements GameStateInitializer
{

    private final Game gameScene;

    public FightStateInitializer(Game gameScene)
    {
        this.gameScene = gameScene;
    }

    @Override
    public void loadResources()
    {

    }

    @Override
    public void init()
    {
        GameClient.getInstance().sendData("np," + GameClient.getInstance().myUid + "," + 0 + "," + 0);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void render(Graphics g) {

    }

}
