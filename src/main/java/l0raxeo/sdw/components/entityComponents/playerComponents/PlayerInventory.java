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
    private final BufferedImage[] textures = new BufferedImage[3];

    // stores the uid of the objects in the inventory
    // uid = -1 means empty
    private int[] inventory = new int[3];
    // slots: 0, 1, 2
    private int curSlot = 0;

    public PlayerInventory(boolean visible)
    {
        for (int i = 0; i < 3; i++)
            textures[i] = AssetPool.getBufferedImage("assets/textures/gui/player/inventory/inventory_slot_" + i + ".png");
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
                    textures[s],
                    s * (textures[s == 0 ? s : s - 1].getWidth() * 2) + (s == 2 ? (textures[0].getWidth() * 2) - (textures[1].getWidth() * 2) : 0),
                    Window.WINDOW_HEIGHT - (textures[s].getHeight() * 2),
                    textures[s].getWidth() * 2,
                    textures[s].getHeight() * 2,
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
