package l0raxeo.sdw.objects;

import l0raxeo.rendering.gameRendering.Camera;
import l0raxeo.rendering.Window;
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

    /**
     * Screen to world coordinates gets translated here
     */
    public void init(Vector3i position, Vector2i scale, float rotation)
    {
        // world to screen coordinates
        this.position = new Vector2i(position.x, Window.WINDOW_HEIGHT - position.y);
        this.scale = scale;
        this.rotation = rotation;
        this.zIndex = position.z;
    }

    // returns world coordinates
    public Vector2i position()
    {
        return new Vector2i(position.x, Window.WINDOW_HEIGHT - position.y);
    }

    public Vector2i getScreenPosition()
    {
        return new Vector2i(position.x + Camera.xOffset(), position.y + Camera.yOffset());
    }

    /**
     * @param position in world coords
     */
    public void setPosition(Vector2i position)
    {
        this.position = new Vector2i(position.x, Window.WINDOW_HEIGHT - position.y);
    }

    /**
     * @param position position in world coordinates (includes zIndex)
     */
    public void setPosition(Vector3i position)
    {
        this.position = new Vector2i(position.x, Window.WINDOW_HEIGHT - position.y);
        setzIndex(position.z);
    }

    public void setzIndex(int zIndex)
    {
        this.zIndex = zIndex;
    }

    public int getzIndex()
    {
        return zIndex;
    }

    /**
     * @param offset in world coords
     */
    public void move(Vector2i offset)
    {
        this.position.x += offset.x;
        this.position.y -= offset.y;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Transform t)) return false;

        return t.position.equals(this.position) && t.scale.equals(this.scale);
    }



}
