package l0raxeo.sdw.scenes.assetLoaders;

import l0raxeo.rendering.Window;
import l0raxeo.sdw.dataStructure.AssetPool;
import l0raxeo.sdw.ui.GuiText;
import org.joml.Vector2i;

import java.awt.*;

public class LoadingScreen
{

    protected final long MIN_DURATION_MILLI;
    public boolean isLoading = false;

    public LoadingScreen(long durationMilli)
    {
        this.MIN_DURATION_MILLI = durationMilli;
    }

    public void renderLoadingScreen(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
        GuiText.drawString(
                g,
                "Loading...",
                new Vector2i(l0raxeo.rendering.Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2),
                true,
                Color.WHITE,
                AssetPool.getFont("assets/fonts/default_font.ttf", 24)
        );
    }

    public void load()
    {
        isLoading = true;
    }

}
