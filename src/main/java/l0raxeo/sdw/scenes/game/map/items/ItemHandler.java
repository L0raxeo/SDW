package l0raxeo.sdw.scenes.game.map.items;

import l0raxeo.sdw.objects.GameObject;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Used when handling items queued from the draft state and place/managed during build and fight state
 */
public class ItemHandler
{

    private final LinkedList<ItemType> storedItemTypes = new LinkedList<>();
    private final LinkedList<GameObject> storedGameObjects = new LinkedList<>();

    // ITEM TYPE MANAGEMENT

    public void storeItemType(ItemType itemType)
    {
        storedItemTypes.offer(itemType);
    }

    public ItemType retrieveItemType()
    {
        try {
            return storedItemTypes.pop();
        } catch (NoSuchElementException e) {
            return ItemType.EMPTY_ITEM;
        }
    }

    public LinkedList<ItemType> peakStoredItemTypes()
    {
        return storedItemTypes;
    }

    public LinkedList<ItemType> retrieveStoredItemTypes()
    {
        LinkedList<ItemType> result = new LinkedList<>(storedItemTypes);
        storedItemTypes.clear();
        return result;
    }

    public BufferedImage getNextStoredItemTypeImage()
    {
        assert storedItemTypes.peek() != null;
        return storedItemTypes.peek().cardImage;
    }

    public BufferedImage[] getStoredItemTypeImages()
    {
        BufferedImage[] result = new BufferedImage[storedItemTypes.size()];

        for (int i = 0; i < result.length; i++)
            result[i] = storedItemTypes.get(i).cardImage;

        return result;
    }

    public int getStoredItemTypeCount()
    {
        return storedItemTypes.size();
    }

    // ITEM GAME OBJECT MANAGEMENT

    public void storeGameObject(GameObject item)
    {
        storedGameObjects.offer(item);
    }

    public GameObject retrieveStoredGameObject()
    {
        return storedGameObjects.pop();
    }

    public List<GameObject> retrieveAllStoredGameObjects()
    {
        List<GameObject> result = storedGameObjects.stream().toList();
        storedGameObjects.clear();
        return result;
    }

    public List<GameObject> peakAllStoredGameObjects()
    {
        return storedGameObjects.stream().toList();
    }

    public int getStoredGameObjectCount()
    {
        return storedGameObjects.size();
    }

}
