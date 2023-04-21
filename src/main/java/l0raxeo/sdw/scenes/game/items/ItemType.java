package l0raxeo.sdw.scenes.game.items;

import l0raxeo.sdw.components.gfxComponents.ImageTexture;
import l0raxeo.sdw.components.itemComponents.ItemComponent;
import l0raxeo.sdw.dataStructure.AssetPool;
import l0raxeo.sdw.objects.GameObject;
import l0raxeo.sdw.prefabs.Prefabs;
import org.joml.Vector2i;

import java.awt.image.BufferedImage;
import java.util.Random;

public enum ItemType
{

    HAIR_BALL_SPITTING_CAT(AssetPool.getBufferedImage("assets/textures/entities/items/hairball_spitting_cat.png"), 0),
    EMPTY_ITEM(null, -1);

    public final BufferedImage cardImage;
    public final int itemId;

    ItemType(BufferedImage cardImage, int itemId)
    {
        this.cardImage = cardImage;
        this.itemId = itemId;
    }

    public static ItemType getRandomItemType()
    {
        // change as more item types are added
        switch ((int) Math.random())
        {
            case 0 -> {
                return HAIR_BALL_SPITTING_CAT;
            }
        }

        return EMPTY_ITEM;
    }

    public static GameObject createItem(ItemType item)
    {
        switch (item)
        {
            case HAIR_BALL_SPITTING_CAT -> {
                return Prefabs.generate(
                    "Hairball Spitting Cat",
                        new Vector2i(),
                        new Vector2i(24, 34),
                        new ItemComponent(),
                        new ImageTexture(
                                HAIR_BALL_SPITTING_CAT.cardImage,
                                new Vector2i(12, 17)
                        )
                );
            }
            case EMPTY_ITEM -> {
                return Prefabs.generate(
                    "Empty",
                    new Vector2i(),
                    new Vector2i(),
                    new ItemComponent()
                );
            }
        }

        // returns an empty item by default
        return createItem(EMPTY_ITEM);
    }

}
