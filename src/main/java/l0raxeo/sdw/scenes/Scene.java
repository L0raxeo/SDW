package l0raxeo.sdw.scenes;

import l0raxeo.network.GameClient;
import l0raxeo.sdw.components.Component;
import l0raxeo.sdw.objects.GameObject;
import l0raxeo.rendering.AppWindow;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Scene
{

    protected List<GameObject> gameObjects = new ArrayList<>();
    protected Color backdrop = new Color(0, 0, 0, 0);

    public Scene() {

    }

    public void init() {

    }

    public void start() {
        for (GameObject go : gameObjects) {
            go.start();
        }
    }

    public void addGameObject(GameObject go) {
        gameObjects.add(go);
        go.start();
    }

    public GameObject getGameObjectWithUid(String uid)
    {
        for (GameObject go : gameObjects)
            if (Objects.equals(go.getUid(), uid))
                return go;

        return null;
    }

    public List<GameObject> getGameObjects()
    {
        return this.gameObjects;
    }

    public List<GameObject> getGameObjectsWithComponent(Class<? extends Component> componentClass)
    {
        List<GameObject> result = new ArrayList<>();

        for (GameObject go : getGameObjects())
            if (go.hasComponent(componentClass))
                result.add(go);

        return result;
    }

    public abstract void update(double dt);
    public abstract void render(Graphics g);

    public void loadResources() {}

    protected void setBackdrop(Color color)
    {
        this.backdrop = color;
        setBackdrop();
    }

    private void setBackdrop()
    {
        AppWindow.setBackdrop(this.backdrop);
    }

    public void onDestroy() {}

}
