package l0raxeo.sdw.scenes.assetInitializers;

import l0raxeo.sdw.dataStructure.AssetPool;

import java.awt.*;

public class MenuAssets implements AssetInitializer
{

    @Override
    public void renderLoadingScreen(Graphics g) {

    }

    @Override
    public void loadAssets()
    {
        AssetPool.getFont("assets/fonts/default_font.ttf", 16);
        AssetPool.getFont("assets/fonts/default_font.ttf", 24);
        AssetPool.getFont("assets/fonts/default_font.ttf", 32);
    }

    @Override
    public void unloadAssets()
    {
        AssetPool.unloadAllFonts();
    }

}
