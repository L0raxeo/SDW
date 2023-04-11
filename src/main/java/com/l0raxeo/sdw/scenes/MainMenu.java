package com.l0raxeo.sdw.scenes;

import com.l0raxeo.sdw.dataStructure.AssetPool;
import com.l0raxeo.sdw.ui.*;
import com.l0raxeo.sdw.utils.Utils;
import com.l0raxeo.sdw.window.Window;
import net.MultiplayerHandler;
import org.joml.Vector2i;

import java.awt.*;

public class MainMenu extends Scene{

    @Override
    public void loadResources()
    {
        setBackdrop(Color.DARK_GRAY);

        AssetPool.getFont("assets/fonts/default_font.ttf", 16);
        AssetPool.getFont("assets/fonts/default_font.ttf", 24);
        AssetPool.getFont("assets/fonts/default_font.ttf", 32);
    }

    @Override
    public void init()
    {
        GuiLayer.getInstance().clear();
        GuiLayer.getInstance().addGuiComponent(new GuiButton(
                "Host_btn",
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 64),
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 64),
                "Host Match",
                AssetPool.getFont("assets/fonts/default_font.ttf", 32),
                new Color[]{Color.gray, Color.white},
                true,
                this::hostMenu
        ));

        GuiLayer.getInstance().addGuiComponent(new GuiButton(
                "Join_Btn",
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 160),
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 64),
                "Join Match",
                AssetPool.getFont(
                        "assets/fonts/default_font.ttf", 32
                ),
                new Color[]{Color.gray, Color.white},
                true,
                this::joinMenu
        ));
    }

    private void hostMenu()
    {
        GuiLayer.getInstance().clear();
        GuiLayer.getInstance().addGuiComponent(new GuiTextField(
                "Port",
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 64),
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 64),
                AssetPool.getFont(
                        "assets/fonts/default_font.ttf", 32
                ),
                new Color[]{
                        Color.gray, Color.lightGray
                }
        ));

        GuiLayer.getInstance().addGuiComponent(new GuiButton(
                "Create_Server_btn",
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 160),
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 64),
                "Create",
                AssetPool.getFont("assets/fonts/default_font.ttf", 32),
                new Color[]{Color.gray, Color.white},
                true,
                () -> {
                    String rawHost = MultiplayerHandler.createHost(Integer.parseInt(((GuiTextField) GuiLayer.getInstance().getGuiComponent("Port")).getText()));
                    hostWaitingMenu(rawHost);
                }
        ));

        addCancelBtn(256);
    }

    private void joinMenu()
    {
        GuiLayer.getInstance().clear();
        GuiLayer.getInstance().addGuiComponent(new GuiTextField(
                "IP Address",
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 64),
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 64),
                AssetPool.getFont(
                        "assets/fonts/default_font.ttf", 32
                ),
                new Color[]{
                        Color.gray, Color.lightGray
                }
        ));

        GuiLayer.getInstance().addGuiComponent(new GuiTextField(
                "Port",
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 160),
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 64),
                AssetPool.getFont(
                        "assets/fonts/default_font.ttf", 32
                ),
                new Color[]{
                        Color.gray, Color.lightGray
                }
        ));

        GuiLayer.getInstance().addGuiComponent(new GuiButton(
                "Join_btn",
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 256),
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 64),
                "Join",
                AssetPool.getFont("assets/fonts/default_font.ttf", 32),
                new Color[]{Color.gray, Color.white},
                true,
                () -> {
                    MultiplayerHandler.createClient(
                            Utils.decrypt(((GuiTextField) (GuiLayer.getInstance().getGuiComponent("IP Address"))).getText().toUpperCase()),
                            Integer.parseInt(((GuiTextField) (GuiLayer.getInstance().getGuiComponent("Port"))).getText())
                    );
                }
        ));

        addCancelBtn(352);
    }

    private void hostWaitingMenu(String rawHost)
    {
        GuiLayer.getInstance().clear();

        GuiLayer.getInstance().addGuiComponent(new GuiTextObj
                (
                        "Join_Code_Label",
                        "Join Code",
                        new Vector2i(Window.WINDOW_WIDTH / 2, 160),
                        Color.WHITE,
                        AssetPool.getFont("assets/fonts/default_font.ttf", 32)
                )
        );

        GuiLayer.getInstance().addGuiComponent(new GuiTextObj
                (
                        "Join_Code",
                        Utils.encrypt(rawHost),
                        new Vector2i(Window.WINDOW_WIDTH / 2, 192),
                        Color.WHITE,
                        AssetPool.getFont("assets/fonts/default_font.ttf", 32)
                )
        );

        GuiLayer.getInstance().addGuiComponent(new GuiButton(
                "Leave_Server",
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 256),
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 64),
                "Leave Match",
                AssetPool.getFont("assets/fonts/default_font.ttf", 32),
                new Color[]{Color.gray, Color.white},
                true,
                () -> {
                    MultiplayerHandler.destroyGameClient();
                    MultiplayerHandler.destroyGameServer();
                    hostMenu();
                }
        ));
    }

    private void addCancelBtn(int yPos)
    {
        GuiLayer.getInstance().addGuiComponent(new GuiButton(
                "cancel_btn",
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, yPos),
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 64),
                "Cancel",
                AssetPool.getFont("assets/fonts/default_font.ttf", 32),
                new Color[]{Color.gray, Color.white},
                true,
                this::init
        ));
    }

    @Override
    public void start()
    {

    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void render(Graphics g) {
        gui(g);
    }
}
