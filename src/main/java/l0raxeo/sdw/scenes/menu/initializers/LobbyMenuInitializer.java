package l0raxeo.sdw.scenes.menu.initializers;

import l0raxeo.network.GameServer;
import l0raxeo.network.MultiplayerHandler;
import l0raxeo.network.clientInfo.ClientInfo;
import l0raxeo.rendering.AppWindow;
import l0raxeo.sdw.dataStructure.AssetPool;
import l0raxeo.sdw.dataStructure.encryption.Encryptor;
import l0raxeo.sdw.scenes.menu.MenuState;
import l0raxeo.sdw.ui.GuiLayer;
import l0raxeo.sdw.ui.GuiText;
import l0raxeo.sdw.ui.components.GuiButton;
import l0raxeo.sdw.ui.components.GuiTextObj;
import org.joml.Vector2i;

import java.awt.*;
import java.util.List;

import static l0raxeo.network.MultiplayerHandler.port;
import static l0raxeo.network.MultiplayerHandler.rawHost;

public class LobbyMenuInitializer implements MenuStateInitializer
{

    @Override
    public void create() {
        GuiLayer.getInstance().addGuiComponent(new GuiTextObj
                (
                        "Join_Code",
                        "Join Code: " + Encryptor.encrypt(rawHost),
                        new Vector2i(AppWindow.WINDOW_WIDTH / 2, 64),
                        Color.WHITE,
                        AssetPool.getFont("assets/fonts/default_font.ttf", 32)
                )
        );

        GuiLayer.getInstance().addGuiComponent(new GuiTextObj
                (
                        "Port",
                        "Port: " + port,
                        new Vector2i(AppWindow.WINDOW_WIDTH / 2, 128),
                        Color.WHITE,
                        AssetPool.getFont("assets/fonts/default_font.ttf", 32)
                )
        );

        if (MultiplayerHandler.isHosting())
        {
            GuiLayer.getInstance().addGuiComponent(new GuiButton(
                    "Start_Game",
                    new Vector2i(AppWindow.WINDOW_WIDTH / 2 - 128, 160),
                    new Vector2i(256, 64),
                    "Start Game",
                    AssetPool.getFont("assets/fonts/default_font.ttf", 32),
                    new Color[]{Color.gray, Color.white},
                    true,
                    () -> GameServer.getInstance().sendDataToAllClients("sm")
            ));
        }

        GuiLayer.getInstance().addGuiComponent(new GuiButton(
                "Leave_Server",
                new Vector2i(AppWindow.WINDOW_WIDTH / 2 - 128, 256),
                new Vector2i(256, 64),
                "Leave Lobby",
                AssetPool.getFont("assets/fonts/default_font.ttf", 32),
                new Color[]{Color.gray, Color.white},
                true,
                () -> {
                    MultiplayerHandler.disconnectClient();
                    MenuState.setState(MenuState.MAIN);
                }
        ));
    }

    @Override
    public void gui(Graphics g) {
        if (MultiplayerHandler.socketClient != null)
        {
            List<ClientInfo> playerList = MultiplayerHandler.socketClient.clientList;

            for (int i = 0; i < playerList.size(); i++)
                GuiText.drawString(
                        g,
                        playerList.get(i).getUsername(),
                        new Vector2i(AppWindow.WINDOW_WIDTH / 2, 384 + (i * 32)),
                        true,
                        Color.WHITE,
                        AssetPool.getFont("assets/fonts/default_font.ttf", 32)
                );
        }
    }
}
