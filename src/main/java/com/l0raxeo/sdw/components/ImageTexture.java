package com.l0raxeo.sdw.components;

import com.l0raxeo.sdw.window.Camera;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageTexture extends Component
{

    /**
     * world coordinates
     */
    private Vector2i texPos;
    private Vector2i texScale;
    private Vector2i rotationAnchor;
    private float rotation = -1;
    private final boolean inheritRotation;
    private BufferedImage texture;

    public ImageTexture(BufferedImage texture)
    {
        this.texture = texture;
        this.rotationAnchor = new Vector2i();
        inheritRotation = true;
    }

    public ImageTexture(BufferedImage texture, Vector2i rotationAnchor)
    {
        this.texture = texture;
        this.rotationAnchor = rotationAnchor;
        inheritRotation = true;
    }

    public ImageTexture(BufferedImage texture, float rotation, Vector2i rotationAnchor)
    {
        this.texture = texture;
        this.rotation = rotation;
        this.rotationAnchor = rotationAnchor;
        inheritRotation = false;
    }

    @Override
    public void start() {
        if (inheritRotation)
            rotation = gameObject.transform.rotation;

        texPos = gameObject.transform.getScreenPosition();
        texScale = gameObject.transform.scale;
    }

    @Override
    public void update(double dt)
    {
        if (inheritRotation)
            rotation = gameObject.transform.rotation;

        texPos = gameObject.transform.getScreenPosition();
        texScale = gameObject.transform.scale;
    }

    @Override
    public void render(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        if (!flipped)
            g2d.rotate(Math.toRadians(rotation), texPos.x + rotationAnchor.x, texPos.y + rotationAnchor.y);
        else
            g2d.rotate(Math.toRadians(rotation), texPos.x + texScale.x - (rotationAnchor.x * 1.6), texPos.y + rotationAnchor.y);
        g2d.drawImage(texture, texPos.x, texPos.y, texScale.x, texScale.y, null);
        g2d.setTransform(old);
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

    /**
     * @return rotation in degrees
     */
    public float getRotation()
    {
        return rotation;
    }

    /**
     * @param theta in degrees
     */
    public void setRotation(float theta)
    {
        this.rotation = theta;
    }

    private boolean flipped = false;

    public void setFlip(boolean flip)
    {
        if (flipped != flip)
        {
            AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
            tx.translate(0, -texture.getHeight(null));
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            texture = op.filter(texture, null);
            flipped = flip;
        }
    }

}
