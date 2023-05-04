package l0raxeo.sdw.components.itemComponents;

import l0raxeo.sdw.components.Component;
import l0raxeo.sdw.components.gfxComponents.ImageTexture;
import l0raxeo.sdw.objects.GameObject;
import l0raxeo.sdw.input.mouse.MouseManager;
import l0raxeo.rendering.gameRendering.Camera;
import l0raxeo.sdw.scenes.game.items.ItemState;

public class ItemComponent extends Component
{

    private GameObject parent;
    private ItemState itemState;

    /**
     * Constructor should only be used if item is being generated
     * on the ground (i.e. will not be inherited by a player or
     * an entity). By default, the item state will be set to idle.
     */
    public ItemComponent()
    {
        this.itemState = ItemState.IDLE;
    }

    /**
     * Constructor should only be used if item is being generated
     * on the ground (i.e. will not be inherited by a player or
     * an entity).
     * @param itemState should always be IDLE
     */
    public ItemComponent(ItemState itemState)
    {
        this.itemState = itemState;
    }

    /**
     * Constructor should only be used if item is immediately being
     * used, or if it is immediately going into an inventory.
     * @param parent only if being STORED or USED
     * @param itemState STORED or USED
     */
    public ItemComponent(GameObject parent, ItemState itemState)
    {
        this.parent = parent;
        this.itemState = itemState;
    }

    @Override
    public void update(double dt) {
        if (itemState == ItemState.USED)
            rotateItemInHand();
    }

    private void rotateItemInHand()
    {
        float parentTheta = Camera.getAngleFromOriginToTarget(
                parent.transform.position().x + (parent.transform.scale.x / 2),
                parent.transform.position().y + (parent.transform.scale.y / 2),
                MouseManager.getMouseX(),
                MouseManager.getMouseY()
        );

        if ((parentTheta >= 270 && parentTheta <= 360) || (parentTheta >= 0 && parentTheta <= 90)) {
            gameObject.transform.setPosition(parent.transform.position().sub(0, parent.transform.scale.y / 2).add((int) (parent.transform.scale.x * .85), 0));
            gameObject.getComponent(ImageTexture.class).setFlip(false);
        } else if (parentTheta > 90 && parentTheta < 270) {
            gameObject.getComponent(ImageTexture.class).setFlip(true);
            gameObject.transform.setPosition(parent.transform.position().sub(0, parent.transform.scale.y / 2));
        }

        gameObject.transform.rotation = -Camera.getAngleFromOriginToTarget(
                gameObject.transform.position().x,
                gameObject.transform.position().y,
                MouseManager.getMouseX(),
                MouseManager.getMouseY()
        );

        if (gameObject.transform.rotation < 0 && gameObject.transform.rotation > -180)
            gameObject.transform.setzIndex(-1);
        else
            gameObject.transform.setzIndex(1);
    }

    public void assignToParent(GameObject parent)
    {
        this.parent = parent;
    }

    public void unassign()
    {
        this.parent = null;
    }

}

