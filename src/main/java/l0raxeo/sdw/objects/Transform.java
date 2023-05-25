package l0raxeo.sdw.objects;

import l0raxeo.rendering.gameRendering.Camera;
import l0raxeo.rendering.AppWindow;
import org.joml.Vector2i;
import org.joml.Vector3i;

public class Transform
{

    private Vector2i position;
    public Vector2i scale;
    private int zIndex = 0;
    public float rotation;

    public Transform(Vector3i position, Vector2i scale, float rotation)
    {
        init(position, scale, rotation);
    }

    public Transform(Vector3i position, Vector2i scale)
    {
        init(position, scale, 0);
    }

    public void init(Vector3i worldPosition, Vector2i scale, float rotation)
    {
        // world to screen coordinates
        this.position = new Vector2i(worldPosition.x, AppWindow.WINDOW_HEIGHT - worldPosition.y);
        this.scale = scale;
        this.rotation = rotation;
        this.zIndex = worldPosition.z;
    }

    public Vector2i worldPosition()
    {
        return new Vector2i(position.x, AppWindow.WINDOW_HEIGHT - position.y);
    }

    public Vector2i getScreenPosition()
    {
        return new Vector2i(position.x + Camera.xOffset(), position.y + Camera.yOffset());
    }

    public Vector2i getCenterPosition()
    {
        return getScreenPosition().add(scale.x / 2, scale.y / 2);
    }

    public void setPosition(Vector2i worldPosition)
    {
        this.position = new Vector2i(worldPosition.x, AppWindow.WINDOW_HEIGHT - worldPosition.y);
    }

    public void setPosition(Vector3i worldPosition)
    {
        this.position = new Vector2i(worldPosition.x, AppWindow.WINDOW_HEIGHT - worldPosition.y);
        setzIndex(worldPosition.z);
    }

    public void setzIndex(int zIndex)
    {
        this.zIndex = zIndex;
    }

    public int getzIndex()
    {
        return zIndex;
    }

    public void move(Vector2i worldCoordinateOffset)
    {
        this.position.x += worldCoordinateOffset.x;
        this.position.y -= worldCoordinateOffset.y;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Transform t)) return false;

        return t.position.equals(this.position) && t.scale.equals(this.scale);
    }

}
