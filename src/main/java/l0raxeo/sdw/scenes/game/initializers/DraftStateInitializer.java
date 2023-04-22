package l0raxeo.sdw.scenes.game.initializers;

import l0raxeo.rendering.Window;
import l0raxeo.sdw.scenes.game.Game;
import l0raxeo.sdw.scenes.game.GameState;
import l0raxeo.sdw.scenes.game.items.ItemHandler;
import l0raxeo.sdw.scenes.game.items.ItemType;
import l0raxeo.sdw.ui.GuiLayer;
import l0raxeo.sdw.ui.components.GuiImageButton;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DraftStateInitializer implements GameStateInitializer
{

    private final Game gameScene;

    public DraftStateInitializer(Game gameScene)
    {
        this.gameScene = gameScene;
    }

    private boolean drafting = false;
    private int numOfDrafts = 2;

    @Override
    public void loadResources()
    {
        Window.setBackdrop(Color.DARK_GRAY);
    }

    @Override
    public void init()
    {
    }

    @Override
    public void start()
    {

    }

    @Override
    public void update(double dt)
    {
        if (numOfDrafts <= 0) //after initial number of drafts are used it changes state
        {
            GameState.setState(GameState.BUILD);
            return;
        }

        if (!drafting)
        {
            for (int i = 1; i < 6; i++)
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
                            numOfDrafts--;
                            GuiLayer.getInstance().clear();
                            drafting = false;
                        }
                ));
            }

            drafting = true;
        }
    }

    @Override
    public void render(Graphics g)
    {

    }

}
