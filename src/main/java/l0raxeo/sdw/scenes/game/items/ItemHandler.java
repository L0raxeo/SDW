package l0raxeo.sdw.scenes.game.items;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Used when handling items queued from the draft state and place/managed during build and fight state
 */
public class ItemHandler
{

    private final LinkedList<ItemType> storedItemTypes = new LinkedList<>();

    public void storeItemType(ItemType itemType)
    {
        storedItemTypes.offer(itemType);
    }

    public ItemType retrieveItemType()
    {
        return storedItemTypes.pop();
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

}
