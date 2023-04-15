package com.l0raxeo.sdw.file;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class FileLoader
{

    public static Font loadFont(String path, float size)
    {
        try
        {
            return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
        }
        catch (FontFormatException | IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    public static BufferedImage loadImage(String path)
    {
        try
        {
            return ImageIO.read(new File(path).toURI().toURL().openStream());
        }
        catch (IOException e)
        {
            if (!e.getMessage().equals("closed"))
                e.printStackTrace();
        }

        return null;
    }

}
