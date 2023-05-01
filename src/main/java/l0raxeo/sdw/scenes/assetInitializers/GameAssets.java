package l0raxeo.sdw.scenes.assetInitializers;

import l0raxeo.rendering.Window;
import l0raxeo.sdw.dataStructure.AssetPool;
import l0raxeo.sdw.ui.GuiLayer;
import l0raxeo.sdw.ui.GuiText;
import org.joml.Vector2i;

import java.awt.*;

public class GameAssets implements AssetInitializer
{

    public boolean assetsLoaded = false;

    @Override
    public void renderLoadingScreen(Graphics g)
    {
        Window.setBackdrop(Color.BLACK);
        GuiText.drawString(
                g,
                "Loading...",
                new Vector2i(Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2),
                true,
                Color.WHITE,
                AssetPool.getFont("assets/fonts/default_font.ttf", 24)
        );
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

        while (startLoadingTime + 2000 > System.currentTimeMillis())
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
