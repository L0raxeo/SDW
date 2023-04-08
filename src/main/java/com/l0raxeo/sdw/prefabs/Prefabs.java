package com.l0raxeo.sdw.prefabs;

import com.l0raxeo.sdw.components.Component;
import com.l0raxeo.sdw.dataStructure.Transform;
import com.l0raxeo.sdw.objects.GameObject;
import org.joml.Vector2f;

public class Prefabs {

    public static GameObject generate(String name, Vector2f pos, Vector2f size, Component... comps)
    {
        GameObject go = new GameObject(name, new Transform(pos.add(0, size.y), size));

        for (Component c : comps)
            go.addComponent(c);

        return go;
    }

}
