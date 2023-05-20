package l0raxeo.sdw.scenes.game.initializers;

import l0raxeo.rendering.Window;
import l0raxeo.sdw.input.mouse.MouseManager;
import l0raxeo.sdw.scenes.game.Game;
import l0raxeo.sdw.scenes.game.GameState;
import l0raxeo.sdw.scenes.game.map.items.ItemHandler;
import l0raxeo.sdw.scenes.game.map.items.ItemType;
import l0raxeo.sdw.scenes.game.map.MiniMap;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class BuildStateInitializer extends GameStateInitializer
{

    private final MiniMap miniMap;
    private final ItemHandler itemHandler;

    private HashMap<ItemType, Vector2i> placedItemTypes;
    private ItemType selectedItemType;

    private Vector2i mouseTileSnapPosition;

    public BuildStateInitializer(Game gameScene)
    {
        this.itemHandler = gameScene.mapHandler.itemHandler;
        this.miniMap = gameScene.mapHandler.miniMap;
    }

    @Override
    public void loadResources()
    {
        Window.setBackdrop(Color.DARK_GRAY);
    }

    @Override
    public void start()
    {
        placedItemTypes = new HashMap<>();
        selectedItemType = itemHandler.retrieveItemType();

        miniMap.setMiniMapBorderSize(new Vector2i(0, 0), new Vector2i(0, 0));
        miniMap.renderBuildingGrid(true);
    }

    @Override
    public void update(double dt)
    {
        mouseTileSnapPosition = miniMap.tileSnapPosition(MouseManager.getMouseScreenPosition());
        if (MouseManager.onPress(MouseEvent.BUTTON1))
        {
            placedItemTypes.put(selectedItemType, mouseTileSnapPosition);
            itemHandler.storeGameObject(ItemType.createItemFromType(selectedItemType));
            selectedItemType = itemHandler.retrieveItemType();

            if (selectedItemType == ItemType.EMPTY_ITEM)
                GameState.setState(GameState.FIGHT);
        }
    }

    @Override
    public void render(Graphics g)
    {
        miniMap.render(g);

        placedItemTypes.forEach(
                (itemType, position) -> g.drawImage(itemType.cardImage, position.x, position.y, itemType.cardImage.getWidth(), itemType.cardImage.getHeight(), null)
        );

        if (selectedItemType != ItemType.EMPTY_ITEM)
        {
            g.drawImage(selectedItemType.cardImage, mouseTileSnapPosition.x, mouseTileSnapPosition.y, selectedItemType.cardImage.getWidth(), selectedItemType.cardImage.getHeight(), null);
        }
    }

}
