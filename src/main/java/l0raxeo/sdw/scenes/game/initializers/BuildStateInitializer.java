package l0raxeo.sdw.scenes.game.initializers;

import l0raxeo.network.GameClient;
import l0raxeo.network.MultiplayerHandler;
import l0raxeo.rendering.AppWindow;
import l0raxeo.sdw.dataStructure.AssetPool;
import l0raxeo.sdw.input.keyboard.KeyManager;
import l0raxeo.sdw.input.mouse.MouseManager;
import l0raxeo.sdw.scenes.game.Game;
import l0raxeo.sdw.scenes.game.GameState;
import l0raxeo.sdw.scenes.game.map.items.ItemHandler;
import l0raxeo.sdw.scenes.game.map.items.ItemType;
import l0raxeo.sdw.scenes.game.map.MiniMap;
import l0raxeo.sdw.ui.GuiText;
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

    private boolean readyToFight = false;

    public BuildStateInitializer(Game gameScene)
    {
        this.itemHandler = gameScene.mapHandler.itemHandler;
        this.miniMap = gameScene.mapHandler.miniMap;
    }

    @Override
    public void loadResources()
    {
        AppWindow.setBackdrop(Color.DARK_GRAY);
    }

    @Override
    public void start()
    {
        placedItemTypes = new HashMap<>();
        selectedItemType = itemHandler.retrieveItemType();

        miniMap.setMiniMapBorderSize(new Vector2i(0, 0), new Vector2i(0, 0));
        miniMap.renderBuildingGrid(true);
        mouseTileSnapPosition = new Vector2i();
    }

    @Override
    public void update(double dt)
    {
        mouseTileSnapPosition = miniMap.tileSnapPosition(MouseManager.getMouseScreenPosition());

        if (MouseManager.onPress(MouseEvent.BUTTON1))
            placeItem();
    }

    private void placeItem()
    {
        placedItemTypes.put(selectedItemType, mouseTileSnapPosition);
        itemHandler.storeGameObject(ItemType.createItemFromType(selectedItemType));
        selectedItemType = itemHandler.retrieveItemType();

        readyToFight = selectedItemType == ItemType.EMPTY_ITEM;
    }

    @Override
    public void render(Graphics g)
    {
        if (!readyToFight)
        {
            miniMap.render(g);
            renderPlacedItems(g);
            renderSelectedItems(g);
        }
        else
        {
            GuiText.drawString(g, "Ready to build!", new Vector2i(AppWindow.WINDOW_WIDTH / 2, AppWindow.WINDOW_HEIGHT / 2), true, Color.GREEN, AssetPool.getFont("assets/fonts/default_font.ttf", 32));

            if (MultiplayerHandler.isHosting())
                promptFightState(g);
        }
    }

    private void promptFightState(Graphics g)
    {
        GuiText.drawString(g, "Click any button to start", new Vector2i(AppWindow.WINDOW_WIDTH / 2, (AppWindow.WINDOW_HEIGHT / 2) + 32), true, Color.WHITE, AssetPool.getFont("assets/fonts/default_font.ttf", 16));

        if (KeyManager.hasPressedInput())
            GameClient.getInstance().sendData("gfs");
    }

    private void renderPlacedItems(Graphics g)
    {
        placedItemTypes.forEach(
                (itemType, position) -> g.drawImage(itemType.cardImage, position.x, position.y, itemType.cardImage.getWidth(), itemType.cardImage.getHeight(), null)
        );
    }

    private void renderSelectedItems(Graphics g)
    {
        if (selectedItemType != ItemType.EMPTY_ITEM)
            g.drawImage(selectedItemType.cardImage, mouseTileSnapPosition.x, mouseTileSnapPosition.y, selectedItemType.cardImage.getWidth(), selectedItemType.cardImage.getHeight(), null);
    }

}
