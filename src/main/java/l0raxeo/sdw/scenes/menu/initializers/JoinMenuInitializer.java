package l0raxeo.sdw.scenes.menu.initializers;

import l0raxeo.network.MultiplayerHandler;
import l0raxeo.rendering.Window;
import l0raxeo.sdw.dataStructure.AssetPool;
import l0raxeo.sdw.encryption.Encryptor;
import l0raxeo.sdw.scenes.menu.MenuState;
import l0raxeo.sdw.ui.GuiLayer;
import l0raxeo.sdw.ui.components.GuiButton;
import l0raxeo.sdw.ui.components.GuiTextField;
import l0raxeo.sdw.ui.components.GuiTextObj;
import org.joml.Vector2i;

import java.awt.*;

import static l0raxeo.sdw.scenes.menu.Menu.addCancelBtn;

public class JoinMenuInitializer implements MenuStateInitializer {

    @Override
    public void create() {
        GuiLayer.getInstance().addGuiComponent(new GuiTextField(
                "IP Address",
                new Vector2i(l0raxeo.rendering.Window.WINDOW_WIDTH / 2 - 256, 64),
                new Vector2i(512, 64),
                AssetPool.getFont(
                        "assets/fonts/default_font.ttf", 32
                ),
                new Color[]{
                        Color.gray, Color.lightGray
                }
        ));

        GuiLayer.getInstance().addGuiComponent(new GuiTextField(
                "Port",
                new Vector2i(l0raxeo.rendering.Window.WINDOW_WIDTH / 2 - 256, 160),
                new Vector2i(512, 64),
                AssetPool.getFont(
                        "assets/fonts/default_font.ttf", 32
                ),
                new Color[]{
                        Color.gray, Color.lightGray
                }
        ));

        GuiLayer.getInstance().addGuiComponent(new GuiButton(
                "Join_btn",
                new Vector2i(l0raxeo.rendering.Window.WINDOW_WIDTH / 2 - 128, 256),
                new Vector2i(256, 64),
                "Join",
                AssetPool.getFont("assets/fonts/default_font.ttf", 32),
                new Color[]{Color.gray, Color.white},
                true,
                () -> {
                    String username = ((GuiTextField) GuiLayer.getInstance().getGuiComponent("Username")).getText();
                    if (username.equals(""))
                    {
                        GuiLayer.getInstance().addGuiComponent(new GuiTextObj(
                                "Name_Warning",
                                "Please enter a username",
                                new Vector2i(l0raxeo.rendering.Window.WINDOW_WIDTH / 2, 496),
                                Color.WHITE,
                                AssetPool.getFont(
                                        "assets/fonts/default_font.ttf",
                                        32
                                )
                        ));
                        return;
                    }

                    String rawHost = Encryptor.decrypt(((GuiTextField) (GuiLayer.getInstance().getGuiComponent("IP Address"))).getText().toUpperCase());
                    int port = Integer.parseInt(((GuiTextField) (GuiLayer.getInstance().getGuiComponent("Port"))).getText());
                    MultiplayerHandler.createClient(rawHost, port, username);

                    MenuState.setState(MenuState.LOBBY);
                }
        ));

        addCancelBtn(352);

        GuiLayer.getInstance().addGuiComponent(new GuiTextField(
                "Username",
                new Vector2i(l0raxeo.rendering.Window.WINDOW_WIDTH / 2 - 256, Window.WINDOW_HEIGHT - 128),
                new Vector2i(512, 64),
                AssetPool.getFont(
                        "assets/fonts/default_font.ttf", 32
                ),
                new Color[]{
                        Color.gray, Color.lightGray
                }
        ));
    }

    @Override
    public void gui(Graphics g) {

    }
}
