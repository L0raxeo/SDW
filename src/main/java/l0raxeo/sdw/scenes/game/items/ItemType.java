package l0raxeo.sdw.scenes.game.items;

import l0raxeo.sdw.components.gfxComponents.ImageTexture;
import l0raxeo.sdw.components.itemComponents.ItemComponent;
import l0raxeo.sdw.dataStructure.AssetPool;
import l0raxeo.sdw.objects.GameObject;
import l0raxeo.sdw.prefabs.Prefabs;
import org.joml.Vector2i;
import org.joml.Vector3i;

import java.awt.image.BufferedImage;

public enum ItemType
{

    HAIR_BALL_SPITTING_CAT(AssetPool.getBufferedImage("assets/textures/entities/items/hairball_spitting_cat.png"), 0),
    BARBED_WIRE(AssetPool.getBufferedImage("assets/textures/entities/items/barbed_wire.png"), 1),
    EMPTY_ITEM(null, -1);

    public final BufferedImage cardImage;
    public final int itemTypeId;

    ItemType(BufferedImage cardImage, int itemTypeId)
    {
        this.cardImage = cardImage;
        this.itemTypeId = itemTypeId;
    }

    public static final int NUMBER_OF_ITEM_TYPES = 2;

    public static ItemType getRandomItemType()
    {
        int randomItemTypeId = (int) (Math.random() * NUMBER_OF_ITEM_TYPES);
        return getItemType(randomItemTypeId);
    }

    public static ItemType getItemType(int typeId)
    {
        for (ItemType itemType : ItemType.values())
            if (itemType.itemTypeId == typeId)
                return itemType;

        return EMPTY_ITEM;
    }

    public static GameObject createItem(ItemType item)
    {
        switch (item)
        {
            case HAIR_BALL_SPITTING_CAT -> {
                return Prefabs.generate(
                    "Hairball Spitting Cat",
                        new Vector3i(),
                        new Vector2i(24, 34),
                        new ItemComponent(),
                        new ImageTexture(
                                HAIR_BALL_SPITTING_CAT.cardImage,
                                new Vector2i(12, 17)
                        )
                );
            }
            case BARBED_WIRE -> {
                return Prefabs.generate(
                        "Barbed Wire",
                        new Vector3i(),
                        new Vector2i(64, 64),
                        new ItemComponent(),
                        new ImageTexture(
                                BARBED_WIRE.cardImage,
                                new Vector2i(32, 32)
                        )
                );
            }
            case EMPTY_ITEM -> {
                return Prefabs.generate(
                    "Empty",
                    new Vector3i(),
                    new Vector2i(),
                    new ItemComponent()
                );
            }
        }

        // returns an empty item by default
        return createItem(EMPTY_ITEM);
    }

}
