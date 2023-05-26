package l0raxeo.sdw.objects;

import l0raxeo.network.GameClient;
import l0raxeo.sdw.components.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class GameObject
{


    private final String name;
    private final List<Component> components;
    private String uid = "";
    public Transform transform;
    private boolean isDead = false;

    public GameObject(String name, Transform transform) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = transform;

        generateUid();
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Casting component.";
                }
            }
        }

        return null;
    }

    public Component getComponent(String uid)
    {
        for (Component c : components)
            if (Objects.equals(c.uid(), uid))
                return c;

        return null;
    }

    public <T extends Component> boolean hasComponent(Class<T> componentClass)
    {
        for (Component c : components)
            if (componentClass.isAssignableFrom(c.getClass()))
                return true;

        return false;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass)
    {
        for (int i=0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c)
    {
        c.generateId();
        this.components.add(c);
        c.gameObject = this;
    }

    public void update(double dt) {
        for (Component component : components) {
            component.update(dt);
        }
    }

    public void render(Graphics g)
    {
        for (Component component : components)
            component.render(g);
    }

    public void start() {
        for (Component component : components) {
            component.start();
        }
    }

    public String getName()
    {
        return this.name;
    }

    public String getUid()
    {
        return this.uid;
    }

    public List<Component> getAllComponents()
    {
        return this.components;
    }

    public boolean isDead()
    {
        return isDead;
    }

    public void die()
    {
        this.isDead = true;

        for (Component c : getAllComponents())
            c.onDestroy();
    }

    private void generateUid()
    {
        this.uid = UUID.randomUUID().toString();
    }

}
