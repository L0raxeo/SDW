package l0raxeo.sdw.scenes.game.initializers;

import l0raxeo.rendering.Window;
import l0raxeo.rendering.postRenderGraphics.GraphicsDraw;
import l0raxeo.sdw.scenes.game.Game;
import l0raxeo.sdw.scenes.game.items.ItemHandler;
import l0raxeo.sdw.ui.GuiLayer;
import l0raxeo.sdw.ui.components.GuiImage;
import org.joml.Vector2i;

import java.awt.*;

public class BuildStateInitializer implements GameStateInitializer
{

    private final Game gameScene;

    public BuildStateInitializer(Game gameScene)
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

    }

    @Override
    public void start()
    {
        GuiLayer.getInstance().addGuiComponent(new GuiImage(
                "Item Being Built",
                new Vector2i(32, 32),
                new Vector2i(gameScene.mapHandler.itemHandler.getNextStoredItemTypeImage().getWidth() * 2, gameScene.mapHandler.itemHandler.getNextStoredItemTypeImage().getHeight() * 2),
                gameScene.mapHandler.itemHandler.getNextStoredItemTypeImage()
        ));
    }

    @Override
    public void update(double dt)
    {
        for (int i = 0; i <= Window.WINDOW_WIDTH; i += 32)
            GraphicsDraw.addLine2D(new Vector2i(i, 0), new Vector2i(i, Window.WINDOW_HEIGHT), Color.WHITE);
        for (int k = 0; k <= Window.WINDOW_HEIGHT; k += 32)
            GraphicsDraw.addLine2D(new Vector2i(0, k), new Vector2i(Window.WINDOW_WIDTH, k), Color.WHITE);
    }

    @Override
    public void render(Graphics g)
    {

    }

}
