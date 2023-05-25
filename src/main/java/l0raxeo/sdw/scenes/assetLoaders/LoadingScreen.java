package l0raxeo.sdw.scenes.assetLoaders;

import l0raxeo.rendering.AppWindow;
import l0raxeo.sdw.dataStructure.AssetPool;
import l0raxeo.sdw.ui.GuiText;
import org.joml.Vector2i;

import java.awt.*;

public class LoadingScreen
{

    private static final long NOT_LOADING = -1;
    private static final long MIN_DURATION_MILLI = 1000;

    private static long minEndTime = NOT_LOADING;

    private static boolean isLoading = false;

    public static void renderLoadingScreen(Graphics g)
    {
        if (isLoading)
        {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, AppWindow.WINDOW_WIDTH, AppWindow.WINDOW_HEIGHT);
            GuiText.drawString(
                    g,
                    "Loading...",
                    new Vector2i(AppWindow.WINDOW_WIDTH / 2, AppWindow.WINDOW_HEIGHT / 2),
                    true,
                    Color.WHITE,
                    AssetPool.getFont("assets/fonts/default_font.ttf", 24)
            );
        }
    }

    public static void load()
    {
        minEndTime = System.currentTimeMillis() + MIN_DURATION_MILLI;
        isLoading = true;
    }

    public static void stop()
    {
        waitUntilMinEndTime();

        minEndTime = NOT_LOADING;
        isLoading = false;
    }

    private static void waitUntilMinEndTime()
    {
        while (System.currentTimeMillis() < minEndTime)
            isLoading = true;
    }

    public static long getMinDurationMilli()
    {
        return MIN_DURATION_MILLI;
    }

    public static boolean isLoading()
    {
        return isLoading;
    }

}