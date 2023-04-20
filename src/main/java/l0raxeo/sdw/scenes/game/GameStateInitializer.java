package l0raxeo.sdw.scenes.game;

import java.awt.*;

public interface GameStateInitializer
{

    void loadResources();

    void start();

    void init();

    void update();

    void render(Graphics g);

}
