package l0raxeo.sdw.dataStructure;

import l0raxeo.sdw.dataStructure.file.FileLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class AssetPool
{

    //          resource, File
    private static final Map<String, Font> loadedFonts = new HashMap<>();
    private static final Map<String, BufferedImage> loadedBufferedImages = new HashMap<>();

    public static BufferedImage getBufferedImage(String resource)
    {
        BufferedImage img;

        if (!loadedFonts.containsKey(resource))
        {
            img = FileLoader.loadImage(resource);
            loadedBufferedImages.put(resource, img);
        }
        else
            img = loadedBufferedImages.get(resource);

        return img;
    }

    public static Font getFont(String resource, int size)
    {
        Font font = null;

        if (!loadedFonts.containsKey(resource))
        {
            font = FileLoader.loadFont(resource, size);
            loadedFonts.put(resource, font);
        }
        else
        {
            if (loadedFonts.get(resource).getSize() != size)
            {
                font = FileLoader.loadFont(resource, size);
                loadedFonts.put(resource, font);
            }

            for (Map.Entry<String, Font> fontSet : loadedFonts.entrySet())
            {
                if (fontSet.getKey().equals(resource) && fontSet.getValue().getSize() == size)
                    return fontSet.getValue();
            }
        }

        return font;
    }

}
