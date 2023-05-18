package l0raxeo.sdw.scenes.game.initializers;

import l0raxeo.rendering.Window;
import l0raxeo.sdw.scenes.game.Game;
import l0raxeo.sdw.scenes.game.GameState;
import l0raxeo.sdw.scenes.game.map.items.ItemType;
import l0raxeo.sdw.ui.GuiLayer;
import l0raxeo.sdw.ui.components.GuiImageButton;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DraftStateInitializer extends GameStateInitializer
{

    private final Game gameScene;

    private boolean drafting = false;
    private int NUM_DRAFTS = 2; // TODO this can be changed via game settings settings

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
            GameState.setState(GameState.BUILD);
            return;
        }

        if (!drafting)
        {
            createDraftLineup();
            drafting = true;
        }
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

    @Override
    public void render(Graphics g)
    {

    }

}
