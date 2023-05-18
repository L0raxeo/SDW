package l0raxeo.sdw.scenes.assetLoaders;

import l0raxeo.rendering.Window;
import l0raxeo.sdw.dataStructure.AssetPool;
import l0raxeo.sdw.scenes.game.Game;
import l0raxeo.sdw.ui.GuiText;
import org.joml.Vector2i;

import java.awt.*;

public class GameAssets extends LoadingScreen implements AssetLoader
{

    public boolean assetsLoaded = false;

    public GameAssets(long durationMilli) {
        super(durationMilli);
    }

    @Override
    public void loadAssets()
    {
        long startLoadingTime = System.currentTimeMillis();
        AssetPool.getBufferedImage("assets/textures/entities/items/hairball_spitting_cat.png");
        AssetPool.getBufferedImage("assets/textures/entities/items/barbed_wire.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_back.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_backleft.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_backright.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_forward.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_left.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_right.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_back_0.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_back_1.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_backleft_0.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_backleft_1.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_backright_0.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_backright_1.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_forward_0.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_forward_1.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_left_0.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_left_1.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_right_0.png");
        AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_right_1.png");
        AssetPool.getBufferedImage("assets/textures/map/floor_tile_0.png");
        AssetPool.getBufferedImage("assets/textures/map/floor_tile_1.png");
        AssetPool.getBufferedImage("assets/textures/map/floor_tile_2.png");
        AssetPool.getBufferedImage("assets/textures/map/floor_tile_3.png");
        AssetPool.getBufferedImage("assets/textures/gui/player/inventory/inventory_slot_0.png");
        AssetPool.getBufferedImage("assets/textures/gui/player/inventory/inventory_slot_1.png");
        AssetPool.getBufferedImage("assets/textures/gui/player/inventory/inventory_slot_2.png");

        ((Game) Window.getScene()).mapHandler.generateTileMap();

        while (startLoadingTime + MIN_DURATION_MILLI > System.currentTimeMillis())
            assetsLoaded = false;

        assetsLoaded = true;
    }

    @Override
    public void unloadAssets()
    {
        AssetPool.unloadAllFonts();
        AssetPool.unloadAllBufferedImages();
        assetsLoaded = false;
    }

}
