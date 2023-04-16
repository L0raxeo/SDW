package com.l0raxeo.sdw.components;

import org.joml.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageTexture extends Component
{

    private final Vector2i texPos = new Vector2i();
    private final Vector2i texScale = new Vector2i();
    private BufferedImage texture;

    @Override
    public void render(Graphics g)
    {
        g.drawImage(texture, texPos.x, texPos.y, texScale.x, texScale.y, null);
    }

    public BufferedImage getTexture()
    {
        return texture;
    }

    public void setTexture(BufferedImage texture)
    {
        this.texture = texture;
    }

    public void setTexPos(int x, int y)
    {
        texPos.x = x;
        texPos.y = y;
    }

    public void setTexScale(int x, int y)
    {
        texScale.x = x;
        texScale.y = y;
    }

    public Vector2i getTexPos()
    {
        return texPos;
    }

    public Vector2i getTexScale()
    {
        return texScale;
    }

}
