package com.l0raxeo.sdw.scenes;

import com.l0raxeo.sdw.dataStructure.AssetPool;
import com.l0raxeo.sdw.ui.*;
import com.l0raxeo.sdw.utils.Utils;
import com.l0raxeo.sdw.window.Window;
import net.ClientInfo;
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
                new Vector2i(256, 64),
                "Host Match",
                AssetPool.getFont("assets/fonts/default_font.ttf", 32),
                new Color[]{Color.gray, Color.white},
                true,
                this::hostMenu
        ));

        GuiLayer.getInstance().addGuiComponent(new GuiButton(
                "Join_Btn",
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 160),
                new Vector2i(256, 64),
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
                new Vector2i(256, 64),
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
                new Vector2i(256, 64),
                "Create",
                AssetPool.getFont("assets/fonts/default_font.ttf", 32),
                new Color[]{Color.gray, Color.white},
                true,
                () -> {
                    int port = Integer.parseInt(((GuiTextField) GuiLayer.getInstance().getGuiComponent("Port")).getText());
                    String rawHost = MultiplayerHandler.createHost(port, ((GuiTextField) GuiLayer.getInstance().getGuiComponent("Username")).getText());
                    waitingMenu(rawHost, port);
                }
        ));

        addCancelBtn(256);

        GuiLayer.getInstance().addGuiComponent(new GuiTextField(
                "Username",
                new Vector2i(Window.WINDOW_WIDTH / 2 - 256, Window.WINDOW_HEIGHT - 128),
                new Vector2i(512, 64),
                AssetPool.getFont(
                        "assets/fonts/default_font.ttf", 32
                ),
                new Color[]{
                        Color.gray, Color.lightGray
                }
        ));
    }

    private void joinMenu()
    {
        GuiLayer.getInstance().clear();
        GuiLayer.getInstance().addGuiComponent(new GuiTextField(
                "IP Address",
                new Vector2i(Window.WINDOW_WIDTH / 2 - 256, 64),
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
                new Vector2i(Window.WINDOW_WIDTH / 2 - 256, 160),
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
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 256),
                new Vector2i(256, 64),
                "Join",
                AssetPool.getFont("assets/fonts/default_font.ttf", 32),
                new Color[]{Color.gray, Color.white},
                true,
                () -> {
                    String rawHost = Utils.decrypt(((GuiTextField) (GuiLayer.getInstance().getGuiComponent("IP Address"))).getText().toUpperCase());
                    int port = Integer.parseInt(((GuiTextField) (GuiLayer.getInstance().getGuiComponent("Port"))).getText());
                    MultiplayerHandler.createClient(rawHost, port, ((GuiTextField) GuiLayer.getInstance().getGuiComponent("Username")).getText());
                    waitingMenu(rawHost, port);
                }
        ));

        addCancelBtn(352);

        GuiLayer.getInstance().addGuiComponent(new GuiTextField(
                "Username",
                new Vector2i(Window.WINDOW_WIDTH / 2 - 256, Window.WINDOW_HEIGHT - 128),
                new Vector2i(512, 64),
                AssetPool.getFont(
                        "assets/fonts/default_font.ttf", 32
                ),
                new Color[]{
                        Color.gray, Color.lightGray
                }
        ));
    }

    private void waitingMenu(String rawHost, int port)
    {
        GuiLayer.getInstance().clear();

        GuiLayer.getInstance().addGuiComponent(new GuiTextObj
                (
                        "Join_Code",
                        "Join Code: " + Utils.encrypt(rawHost),
                        new Vector2i(Window.WINDOW_WIDTH / 2, 64),
                        Color.WHITE,
                        AssetPool.getFont("assets/fonts/default_font.ttf", 32)
                )
        );

        GuiLayer.getInstance().addGuiComponent(new GuiTextObj
                (
                        "Port",
                        "Port: " + port,
                        new Vector2i(Window.WINDOW_WIDTH / 2, 128),
                        Color.WHITE,
                        AssetPool.getFont("assets/fonts/default_font.ttf", 32)
                )
        );

        GuiLayer.getInstance().addGuiComponent(new GuiButton(
                "Leave_Server",
                new Vector2i(Window.WINDOW_WIDTH / 2 - 128, 256),
                new Vector2i(256, 64),
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
                new Vector2i(256, 64),
                "Cancel",
                AssetPool.getFont("assets/fonts/default_font.ttf", 32),
                new Color[]{Color.gray, Color.white},
                true,
                this::init
        ));
    }

    @Override
    public void gui(Graphics g)
    {
        if (GuiLayer.getInstance().getGuiComponent("Leave_Server") != null)
        {
            // length of 15 characters max on one line for the player list
            String[][] playerList = new String[16][8];

            int lineLength = 0;
            int line = 0;
            int slot = 0;
            for (ClientInfo ci : MultiplayerHandler.socketClient.playerList)
            {
                if (lineLength + ci.getUsername().length() <= 15)
                {
                    playerList[line][slot] = ci.getUsername();
                    slot++;
                    lineLength += ci.getUsername().length();
                    continue;
                }

                lineLength = 0;
                slot = 0;
                line++;
            }

            for (int l = 0; l < playerList.length; l++)
            {
                StringBuilder lineBuilder = new StringBuilder();
                for (String p : playerList[l])
                    if (p != null) lineBuilder.append(p);

                GuiText.drawString(
                        g,
                        lineBuilder.toString(),
                        new Vector2i(Window.WINDOW_WIDTH / 2, 384 + (l * 32)),
                        true,
                        Color.WHITE,
                        AssetPool.getFont("assets/fonts/default_font.ttf", 32)
                );
            }
        }
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
