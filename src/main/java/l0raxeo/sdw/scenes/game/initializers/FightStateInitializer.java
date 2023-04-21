package l0raxeo.sdw.scenes.game.initializers;

import l0raxeo.network.GameClient;

import java.awt.*;

public class FightStateInitializer implements GameStateInitializer
{

    @Override
    public void loadResources() {

    }

    @Override
    public void start() {

    }

    @Override
    public void init() {
        GameClient.getInstance().sendData("np," + GameClient.getInstance().myUid + "," + 0 + "," + 0);
    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void render(Graphics g) {

    }

}
