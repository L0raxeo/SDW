package l0raxeo.sdw.components.gfxComponents;

import l0raxeo.sdw.components.Component;
import org.joml.Vector2i;

import java.awt.*;

public class BoxTexture extends Component
{

    private final Color color;
    private final boolean solid;

    public BoxTexture(Color color, boolean solid)
    {
        this.color = color;
        this.solid = solid;
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(color);
        if (solid) g.fillRect(gameObject.transform.getScreenPosition().x, gameObject.transform.getScreenPosition().y, gameObject.transform.scale.x, gameObject.transform.scale.y);
        else g.drawRect(gameObject.transform.getScreenPosition().x, gameObject.transform.getScreenPosition().y, gameObject.transform.scale.x, gameObject.transform.scale.y);
    }

}
