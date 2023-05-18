package l0raxeo.sdw.scenes.game.initializers;

import l0raxeo.rendering.Window;
import l0raxeo.rendering.postRenderGraphics.GraphicsDraw;
import l0raxeo.sdw.input.keyboard.KeyManager;
import l0raxeo.sdw.input.mouse.MouseManager;
import l0raxeo.sdw.scenes.game.Game;
import l0raxeo.sdw.scenes.game.GameState;
import l0raxeo.sdw.scenes.game.items.ItemHandler;
import l0raxeo.sdw.scenes.game.items.ItemType;
import l0raxeo.sdw.ui.GuiLayer;
import l0raxeo.sdw.ui.components.GuiImage;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.event.MouseEvent;

public class BuildStateInitializer implements GameStateInitializer
{

    private final Game gameScene;
    private final ItemHandler itemHandler;

    private ItemType itemTypeHeld = ItemType.EMPTY_ITEM;
    private boolean finishedBuilding = false;

    public BuildStateInitializer(Game gameScene)
    {
        this.gameScene = gameScene;
        this.itemHandler = gameScene.mapHandler.itemHandler;
    }

    @Override
    public void loadResources()
    {
        Window.setBackdrop(Color.DARK_GRAY);
    }

    @Override
    public void start()
    {
        itemTypeHeld = gameScene.mapHandler.itemHandler.retrieveItemType();

        gameScene.mapHandler.miniMap.setMiniMapBorderSize(new Vector2i(0, 0), new Vector2i(0, 0));
        gameScene.mapHandler.miniMap.renderBuildingGrid(true);
    }

    @Override
    public void update(double dt)
    {
        if (MouseManager.onPress(MouseEvent.BUTTON1))
        {
            itemHandler.storeItemGameObject(ItemType.createItem(itemTypeHeld));
            itemTypeHeld = gameScene.mapHandler.itemHandler.retrieveItemType();

            if (itemTypeHeld == ItemType.EMPTY_ITEM)
            {
                finishedBuilding = true;
                GameState.setState(GameState.FIGHT);
            }
        }
    }

    @Override
    public void render(Graphics g)
    {
        gameScene.mapHandler.miniMap.render(g);

        if (itemTypeHeld != ItemType.EMPTY_ITEM)
        {
            Vector2i itemCardPos = gameScene.mapHandler.miniMap.tileSnapPosition(MouseManager.getMouseScreenPosition());
            g.drawImage(itemTypeHeld.cardImage, itemCardPos.x, itemCardPos.y, itemTypeHeld.cardImage.getWidth(), itemTypeHeld.cardImage.getHeight(), null);
        }
    }

}
