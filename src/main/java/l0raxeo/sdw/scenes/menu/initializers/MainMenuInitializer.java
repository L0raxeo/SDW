package l0raxeo.sdw.scenes.menu.initializers;

import l0raxeo.rendering.AppWindow;
import l0raxeo.sdw.dataStructure.AssetPool;
import l0raxeo.sdw.scenes.menu.MenuState;
import l0raxeo.sdw.ui.GuiLayer;
import l0raxeo.sdw.ui.components.GuiButton;
import org.joml.Vector2i;

import java.awt.*;

public class MainMenuInitializer implements MenuStateInitializer
{
    @Override
    public void create()
    {
        GuiLayer.getInstance().addGuiComponent(new GuiButton(
                "Host_btn",
                new Vector2i(AppWindow.WINDOW_WIDTH / 2 - 128, 64),
                new Vector2i(256, 64),
                "Host Match",
                AssetPool.getFont("assets/fonts/default_font.ttf", 32),
                new Color[]{Color.gray, Color.white},
                true,
                () -> MenuState.setState(MenuState.HOST)
        ));

        GuiLayer.getInstance().addGuiComponent(new GuiButton(
                "Join_Btn",
                new Vector2i(AppWindow.WINDOW_WIDTH / 2 - 128, 160),
                new Vector2i(256, 64),
                "Join Match",
                AssetPool.getFont(
                        "assets/fonts/default_font.ttf", 32
                ),
                new Color[]{Color.gray, Color.white},
                true,
                () -> MenuState.setState(MenuState.JOIN)
        ));
    }

    @Override
    public void gui(Graphics g)
    {

    }

}
