package com.l0raxeo.sdw.scenes;

import com.l0raxeo.sdw.dataStructure.AssetPool;
import com.l0raxeo.sdw.input.keyboard.KeyManager;
import com.l0raxeo.sdw.objects.GameObject;
import com.l0raxeo.sdw.prefabs.Prefabs;
import com.l0raxeo.sdw.ui.GuiLayer;
import com.l0raxeo.sdw.ui.GuiText;
import com.l0raxeo.sdw.ui.GuiTextField;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MainMenu extends Scene{

    @Override
    public void loadResources()
    {
        setBackdrop(Color.DARK_GRAY);

        AssetPool.getFont("assets/fonts/default_font.ttf", 16);
        AssetPool.getFont("assets/fonts/default_font.ttf", 24);
        AssetPool.getFont("assets/fonts/default_font.ttf", 48);
    }

    @Override
    public void init()
    {
        GuiLayer.getInstance().addGuiComponent(new GuiTextField(
                "IP Address",
                new Vector2i(50, 50),
                new Vector2i(256, 64),
                new Color[]{
                        Color.gray, Color.lightGray
                }));
    }

    @Override
    public void gui(Graphics g)
    {

    }

    @Override
    public void start()
    {

    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render(Graphics g) {
        gui(g);
    }
}
