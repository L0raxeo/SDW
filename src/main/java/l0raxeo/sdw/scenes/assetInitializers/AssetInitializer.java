package l0raxeo.sdw.scenes.assetInitializers;

import java.awt.*;

public interface AssetInitializer
{

    void renderLoadingScreen(Graphics g);

    void loadAssets();

    void unloadAssets();

}
