package l0raxeo.sdw.scenes.menu;

import l0raxeo.rendering.AppWindow;
import l0raxeo.sdw.dataStructure.AssetPool;
import l0raxeo.sdw.scenes.Scene;
import l0raxeo.sdw.scenes.assetLoaders.MenuAssets;
import l0raxeo.sdw.ui.GuiLayer;
import l0raxeo.sdw.ui.components.GuiButton;
import org.joml.Vector2i;

import java.awt.*;

public class Menu extends Scene
{

    private final MenuAssets menuAssets = new MenuAssets();

    @Override
    public void loadResources()
    {
        menuAssets.loadAssets();
        setBackdrop(Color.DARK_GRAY);
    }

    @Override
    public void init()
    {
        MenuState.setState(MenuState.MAIN);
    }

    public static void addCancelBtn(int yPos)
    {
        GuiLayer.getInstance().addGuiComponent(new GuiButton(
                "cancel_btn",
                new Vector2i(AppWindow.WINDOW_WIDTH / 2 - 128, yPos),
                new Vector2i(256, 64),
                "Cancel",
                AssetPool.getFont("assets/fonts/default_font.ttf", 32),
                new Color[]{Color.gray, Color.white},
                true,
                () -> MenuState.setState(MenuState.MAIN)
        ));
    }

    @Override
    public void update(double dt) {}

    @Override
    public void render(Graphics g) {
        MenuState.getState().initializer.gui(g);
    }

    @Override
    public void onDestroy() {
        menuAssets.unloadAssets();
    }
}
