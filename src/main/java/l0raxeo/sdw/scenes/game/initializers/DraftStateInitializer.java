package l0raxeo.sdw.scenes.game.initializers;

import l0raxeo.rendering.Window;
import l0raxeo.sdw.scenes.game.GameState;
import l0raxeo.sdw.scenes.game.items.ItemType;
import l0raxeo.sdw.ui.GuiLayer;
import l0raxeo.sdw.ui.components.GuiImageButton;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DraftStateInitializer implements GameStateInitializer
{

    ItemType[] chosenItems;

    private boolean drafting = false;
    private int numOfDrafts = 1;

    @Override
    public void loadResources()
    {
        Window.setBackdrop(Color.DARK_GRAY);
    }

    @Override
    public void init()
    {
        chosenItems = new ItemType[numOfDrafts];
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
            GameState.setState(GameState.FIGHT);
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
                        4,
                        new Vector2i(15, 5),
                        new Color[]{Color.LIGHT_GRAY, Color.GRAY},
                        new BufferedImage[]{
                                item.cardImage,
                                item.cardImage
                        },
                        () -> {
                            chosenItems[chosenItems.length - numOfDrafts] = item;
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
