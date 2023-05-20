package l0raxeo.sdw.scenes.game.initializers;

import l0raxeo.network.GameClient;
import l0raxeo.network.MultiplayerHandler;
import l0raxeo.rendering.Window;
import l0raxeo.sdw.dataStructure.AssetPool;
import l0raxeo.sdw.input.mouse.MouseManager;
import l0raxeo.sdw.scenes.game.Game;
import l0raxeo.sdw.scenes.game.GameState;
import l0raxeo.sdw.scenes.game.map.items.ItemType;
import l0raxeo.sdw.ui.GuiLayer;
import l0raxeo.sdw.ui.GuiText;
import l0raxeo.sdw.ui.components.GuiImageButton;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DraftStateInitializer extends GameStateInitializer
{

    private final Game gameScene;

    private boolean drafting = false;
    private int NUM_DRAFTS = 2; // TODO this can be changed via game settings settings
    private boolean readyToBuild = false;

    public DraftStateInitializer(Game gameScene)
    {
        this.gameScene = gameScene;
    }

    @Override
    public void loadResources()
    {
        Window.setBackdrop(Color.DARK_GRAY);
    }

    @Override
    public void update(double dt)
    {


        if (NUM_DRAFTS <= 0) //after initial number of drafts are used it changes state
        {
            readyToBuild = true;
            return;
        }

        if (!drafting)
        {
            createDraftLineup();
            drafting = true;
        }
    }

    @Override
    public void render(Graphics g)
    {
        if (readyToBuild)
        {
            GuiText.drawString(g, "Ready to build!", new Vector2i(Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2), true, Color.GREEN, AssetPool.getFont("assets/fonts/default_font.ttf", 32));

            if (MultiplayerHandler.isHosting())
                promptBuildState(g);
        }
    }

    private void promptBuildState(Graphics g)
    {
        GuiText.drawString(g, "Click any button to start", new Vector2i(Window.WINDOW_WIDTH / 2, (Window.WINDOW_HEIGHT / 2) + 32), true, Color.WHITE, AssetPool.getFont("assets/fonts/default_font.ttf", 16));

        if (MouseManager.hasPressedInput())
            GameClient.getInstance().sendData("gbs");
    }

    private void createDraftLineup()
    {
        int NUM_OF_DRAFTS = 5;
        for (int i = 1; i < NUM_OF_DRAFTS + 1; i++)
        {
            ItemType item = ItemType.getRandomItemType();
            GuiLayer.getInstance().addGuiComponent(new GuiImageButton(
                    "Item_" + (i - 1),
                    new Vector2i(108 * i, 256),
                    new Vector2i(64, 64),
                    2,
                    new Color[]{Color.LIGHT_GRAY, Color.GRAY},
                    new BufferedImage[]{
                            item.cardImage,
                            item.cardImage
                    },
                    () -> {
                        gameScene.mapHandler.itemHandler.storeItemType(item);
                        NUM_DRAFTS--;
                        GuiLayer.getInstance().clear();
                        drafting = false;
                    }
            ));
        }
    }

}
