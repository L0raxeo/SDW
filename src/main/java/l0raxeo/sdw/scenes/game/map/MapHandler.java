package l0raxeo.sdw.scenes.game.map;

import l0raxeo.rendering.Window;
import l0raxeo.rendering.gameRendering.Camera;
import l0raxeo.sdw.dataStructure.AssetPool;
import l0raxeo.sdw.scenes.game.items.ItemHandler;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapHandler
{

    public final MiniMap miniMap = new MiniMap(this);
    public final ItemHandler itemHandler = new ItemHandler();

    //              tileUnits        [           24           ][           24            ]
    //              pixels           [          1536          ][          1536           ]
    private final BufferedImage[][] tileMap = new BufferedImage[Window.WINDOW_WIDTH / 32][Window.WINDOW_HEIGHT / 32];
    public final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    public void generateTileMap()
    {
        for (int r = 0; r < Window.WINDOW_HEIGHT / 32; r++)
            for (int c = 0; c < Window.WINDOW_WIDTH / 32; c++)
                tileMap[r][c] = AssetPool.getBufferedImage("assets/textures/map/floor_tile_" + (int) (Math.random() * 4) + ".png");
    }

    public void render(Graphics g)
    {
        for (int r = 0; r < Window.WINDOW_WIDTH / 32; r++)
            for (int c = 0; c < Window.WINDOW_HEIGHT / 32; c++)
            {
                if ((TILE_WIDTH * c) + Camera.xOffset() > -TILE_WIDTH &&
                        (TILE_WIDTH * c) + Camera.xOffset() < Window.WINDOW_WIDTH + TILE_WIDTH &&
                        (TILE_HEIGHT * r) + Camera.yOffset() > -TILE_HEIGHT &&
                        (TILE_HEIGHT * r) + Camera.yOffset() < Window.WINDOW_HEIGHT + TILE_HEIGHT)
                    g.drawImage(tileMap[r][c], (TILE_WIDTH * c) + Camera.xOffset(), (TILE_HEIGHT * r) + Camera.yOffset(), TILE_WIDTH, TILE_HEIGHT, null);
            }
    }

    public Vector2i tileSnapPosition(Vector2i position)
    {
        return new Vector2i((position.x / 64) * 64, (position.y / 64) * 64);
    }

}
