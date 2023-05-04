package l0raxeo.sdw.scenes.game.items;

import l0raxeo.sdw.objects.GameObject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Used when handling items queued from the draft state and place/managed during build and fight state
 */
public class ItemHandler
{

    private final LinkedList<ItemType> storedItemTypes = new LinkedList<>();
    private final LinkedList<GameObject> storedItemGameObjects = new LinkedList<>();

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

    public void storeItemGameObject(GameObject item)
    {
        storedItemGameObjects.offer(item);
    }

    public GameObject retrieveStoredItemGameObject()
    {
        return storedItemGameObjects.pop();
    }

    public List<GameObject> peakAllStoredItemGameObjects()
    {
        return storedItemGameObjects.stream().toList();
    }

}
