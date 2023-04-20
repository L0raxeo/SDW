package l0raxeo.sdw.scenes.menu;

import l0raxeo.network.MultiplayerHandler;
import l0raxeo.network.clientInfo.ClientInfo;
import l0raxeo.rendering.Window;
import l0raxeo.sdw.dataStructure.AssetPool;
import l0raxeo.sdw.encryption.Encryptor;
import l0raxeo.sdw.ui.GuiLayer;
import l0raxeo.sdw.ui.GuiText;
import l0raxeo.sdw.ui.components.GuiButton;
import l0raxeo.sdw.ui.components.GuiTextObj;
import org.joml.Vector2i;

import java.awt.*;
import java.util.List;

import static l0raxeo.network.MultiplayerHandler.port;
import static l0raxeo.network.MultiplayerHandler.rawHost;

public class LobbyMenu implements MenuStateInitializer
{

    @Override
    public void create() {
        GuiLayer.getInstance().addGuiComponent(new GuiTextObj
                (
                        "Join_Code",
                        "Join Code: " + Encryptor.encrypt(rawHost),
                        new Vector2i(l0raxeo.rendering.Window.WINDOW_WIDTH / 2, 64),
                        Color.WHITE,
                        AssetPool.getFont("assets/fonts/default_font.ttf", 32)
                )
        );

        GuiLayer.getInstance().addGuiComponent(new GuiTextObj
                (
                        "Port",
                        "Port: " + port,
                        new Vector2i(l0raxeo.rendering.Window.WINDOW_WIDTH / 2, 128),
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
                        new Vector2i(Window.WINDOW_WIDTH / 2, 384 + (i * 32)),
                        true,
                        Color.WHITE,
                        AssetPool.getFont("assets/fonts/default_font.ttf", 32)
                );
        }
    }
}
