package l0raxeo.sdw.scenes.assetLoaders;

import l0raxeo.sdw.dataStructure.AssetPool;

public class MenuAssets implements AssetLoader
{

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
