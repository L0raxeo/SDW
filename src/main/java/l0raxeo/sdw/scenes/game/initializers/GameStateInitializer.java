package l0raxeo.sdw.scenes.game.initializers;

import java.awt.*;

public interface GameStateInitializer
{

    void loadResources();

    void init();

    void start();

    void update(double dt);

    void render(Graphics g);

}
