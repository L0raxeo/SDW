package com.l0raxeo.sdw.ui;

import org.joml.Vector2i;

import java.awt.*;

public class GuiTextObj extends GuiComponent{

    private String text;
    private Color color;
    private Font font;

    public GuiTextObj(String name, String text, Vector2i position, Color color, Font font) {
        super(name, position, new Vector2i());

        this.text = text;
        this.color = color;
        this.font = font;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        GuiText.drawString(
                g,
                text,
                position,
                true,
                color,
                font
        );
    }

    @Override
    public void onClick() {

    }
}
