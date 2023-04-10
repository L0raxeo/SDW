package com.l0raxeo.sdw.ui;

import com.l0raxeo.sdw.dataStructure.AssetPool;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GuiTextField extends GuiKeyListener
{

    private final Color[] color;
    private String text;
    private boolean selected;

    public GuiTextField(String name, Vector2i position, Vector2i scale, Color[] color) {
        super(name, position, scale);

        this.text = "";
        this.color = color;
        this.selected = false;
    }

    @Override
    public void render(Graphics g) {
        if (selected)
            g.setColor(color[0]);
        else g.setColor(color[1]);
        g.fillRect(position.x, position.y, scale.x, scale.y);
        if (!selected)
            g.setColor(color[0]);
        else g.setColor(color[1]);
        g.fillRect(position.x + 4, position.y + 4, scale.x - 8, scale.y - 8);
        String curText = text;
        if (curText.equals(""))
            curText = name;
        GuiText.drawString(g,
                            curText,
                            new Vector2i(position.x + scale.x / 2, position.y + scale.y / 2),
                            true,
                            Color.WHITE,
                            AssetPool.getFont(
                                    "assets/fonts/default_font.ttf", 32
                            ));
    }

    @Override
    public void onClick()
    {
        selected = !selected;
    }

    public void keyPress(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
        {
            if (text.length() > 0)
                text = text.substring(0, text.length() - 1);
        }
        else if (selected && !e.isShiftDown())
        {
            text = text.concat(String.valueOf(e.getKeyChar()));
        }
    }

}
