package l0raxeo.sdw.prefabs;

import l0raxeo.sdw.components.Component;
import l0raxeo.sdw.objects.Transform;
import l0raxeo.sdw.objects.GameObject;
import org.joml.Vector2i;

public class Prefabs {

    public static GameObject generate(String name, Vector2i pos, Vector2i size, Component... comps)
    {
        GameObject go = new GameObject(name, new Transform(pos.add(0, size.y), size));

        for (Component c : comps)
            go.addComponent(c);

        return go;
    }

}