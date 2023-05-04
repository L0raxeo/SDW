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

    public MiniMap(MapHandler mapHandler)
    {
        this.mapHandler = mapHandler;
    }

    public void render(Graphics g)
    {
        for (int v = topLeft.x * 32; v <= Window.WINDOW_WIDTH - (bottomRight.x * 32); v += 32)
            GraphicsDraw.addLine2D(new Vector2i(v, topLeft.y * 32), new Vector2i(v, Window.WINDOW_HEIGHT - (bottomRight.y * 32)), Color.WHITE);
        for (int h = topLeft.y * 32; h <= Window.WINDOW_HEIGHT - (bottomRight.y * 32); h += 32)
            GraphicsDraw.addLine2D(new Vector2i(topLeft.x * 32, h), new Vector2i(Window.WINDOW_WIDTH - (bottomRight.x * 32), h), Color.WHITE);
    }

    public Vector2i tileSnapPosition(Vector2i position)
    {
        return new Vector2i((position.x / 32) * 32, (position.y / 32) * 32);
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
