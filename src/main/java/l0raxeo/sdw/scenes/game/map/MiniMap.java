package l0raxeo.sdw.scenes.game.map;

import l0raxeo.rendering.Window;
import l0raxeo.rendering.postRenderGraphics.GraphicsDraw;
import l0raxeo.sdw.components.gfxComponents.ImageTexture;
import l0raxeo.sdw.objects.GameObject;
import org.joml.Vector2i;

import java.awt.*;

public class MiniMap
{

    private final MapHandler mapHandler;
    private Vector2i topLeft = new Vector2i();
    private Vector2i bottomRight = new Vector2i();
    private boolean buildingGrid = false;
    private final int MINI_MAP_TILE_WIDTH = 32, MINI_MAP_TILE_HEIGHT = 32;

    public MiniMap(MapHandler mapHandler)
    {
        this.mapHandler = mapHandler;
    }

    public void render(Graphics g)
    {
        for (int v = topLeft.x * 32; v <= Window.WINDOW_WIDTH - (bottomRight.x * MINI_MAP_TILE_WIDTH); v += MINI_MAP_TILE_WIDTH)
            GraphicsDraw.addLine2D(new Vector2i(v, topLeft.y * MINI_MAP_TILE_HEIGHT), new Vector2i(v, Window.WINDOW_HEIGHT - (bottomRight.y * MINI_MAP_TILE_HEIGHT)), Color.WHITE);
        for (int h = topLeft.y * 32; h <= Window.WINDOW_HEIGHT - (bottomRight.y * MINI_MAP_TILE_HEIGHT); h += MINI_MAP_TILE_HEIGHT)
            GraphicsDraw.addLine2D(new Vector2i(topLeft.x * MINI_MAP_TILE_WIDTH, h), new Vector2i(Window.WINDOW_WIDTH - (bottomRight.x * MINI_MAP_TILE_WIDTH), h), Color.WHITE);
    }

    public Vector2i tileSnapPosition(Vector2i position)
    {
        return new Vector2i((position.x / MINI_MAP_TILE_WIDTH) * MINI_MAP_TILE_WIDTH, (position.y / MINI_MAP_TILE_HEIGHT) * MINI_MAP_TILE_HEIGHT);
    }

    public boolean rendersBuildingGrid()
    {
        return buildingGrid;
    }

    public void renderBuildingGrid(boolean buildingGrid)
    {
        this.buildingGrid = buildingGrid;
    }

    /**
     * @param start number of tiles
     * @param end number of tiles
     */
    public void setMiniMapBorderSize(Vector2i start, Vector2i end)
    {
        this.topLeft = start;
        this.bottomRight = end;
    }

}
