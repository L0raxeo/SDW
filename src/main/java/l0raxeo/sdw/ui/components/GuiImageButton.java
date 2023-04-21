package l0raxeo.sdw.ui.components;

import l0raxeo.sdw.ui.GuiLayer;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GuiImageButton extends GuiComponent
{

    private final Vector2i rawSize;
    private final Vector2i imageBorder;
    private final BufferedImage[] images;
    private final Color[] colors;
    private final int scaleFactor;

    private final ClickListener clicker;

    public GuiImageButton(String name, Vector2i position, int scaleFactor, Vector2i imageBorder, Color[] colors, BufferedImage[] images, ClickListener clicker)
    {
        super(name, position, new Vector2i(images[0].getWidth() * scaleFactor, images[0].getHeight() * scaleFactor));

        this.rawSize = new Vector2i(images[0].getWidth(), images[0].getHeight());
        this.imageBorder = imageBorder;
        this.colors = colors;
        this.images = images;
        this.scaleFactor = scaleFactor;
        this.clicker = clicker;
    }

    @Override
    public void update()
    {

    }

    @Override
    public void render(Graphics g)
    {
        if (hovering)
        {
            g.setColor(colors[1]);
            g.fillRect(position.x - imageBorder.x - 5, position.y - imageBorder.y - 5, scale.x + (imageBorder.x * 2) + 10, scale.y + (imageBorder.y * 2) + 10);
            g.drawImage(images[1], position.x - 5, position.y - 5, (rawSize.x * scaleFactor) + 10, (rawSize.y * scaleFactor) + 10, null);
        }
        else
        {
            g.setColor(colors[0]);
            g.fillRect(position.x - imageBorder.x, position.y - imageBorder.y, scale.x + (imageBorder.x * 2), scale.y + (imageBorder.y * 2));
            g.drawImage(images[0], position.x, position.y, (rawSize.x * scaleFactor), (rawSize.y * scaleFactor), null);
        }
    }

    @Override
    public void onClick()
    {
        clicker.onClick();
        GuiLayer.getInstance().selectComponent(null);
    }

}
