package l0raxeo.sdw.components.entityComponents.playerComponents;

import l0raxeo.rendering.Window;
import l0raxeo.sdw.components.Component;
import l0raxeo.sdw.dataStructure.AssetPool;
import l0raxeo.sdw.input.keyboard.KeyManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerInventory extends Component
{

    // textures
    private final BufferedImage[] slotTextures = new BufferedImage[3];

    // stores the uid of the objects in the inventory
    // uid = -1 means empty
    private int[] inventory = new int[3];
    // slots: 0, 1, 2
    private int curSlot = 0;

    public PlayerInventory(boolean visible)
    {
        for (int i = 0; i < 3; i++)
            slotTextures[i] = AssetPool.getBufferedImage("assets/textures/gui/player/inventory/inventory_slot_" + i + ".png");
    }

    @Override
    public void update(double dt)
    {
        if (KeyManager.onPress('e'))
            quickSwap(1);
        if (KeyManager.onPress('q'))
            quickSwap(-1);
    }

    @Override
    public void render(Graphics g)
    {
        for (int s = 0; s < 3; s++)
        {
            g.drawImage(
                    slotTextures[s],
                    s * (slotTextures[s == 0 ? s : s - 1].getWidth() * 2) + (s == 2 ? (slotTextures[0].getWidth() * 2) - (slotTextures[1].getWidth() * 2) : 0),
                    Window.WINDOW_HEIGHT - (slotTextures[s].getHeight() * 2),
                    slotTextures[s].getWidth() * 2,
                    slotTextures[s].getHeight() * 2,
                    null
            );
        }
    }

    /**
     * @param direction = +-1
     */
    private void quickSwap(int direction)
    {
        curSlot += direction;
    }

}
