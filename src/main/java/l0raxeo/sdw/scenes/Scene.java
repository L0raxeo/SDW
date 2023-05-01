package l0raxeo.sdw.scenes;

import l0raxeo.network.GameClient;
import l0raxeo.network.MultiplayerHandler;
import l0raxeo.sdw.components.Component;
import l0raxeo.sdw.objects.GameObject;
import l0raxeo.rendering.Window;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Scene
{

    protected List<GameObject> gameObjects = new ArrayList<>();
    protected Color backdrop = new Color(0, 0, 0, 0);

    private boolean isRunning = false;

    public Scene() {

    }

    public void init() {

    }

    public void start() {
        for (GameObject go : gameObjects) {
            go.start();
        }
        isRunning = true;
    }

    public void addGameObject(GameObject go) {
        if (!isRunning) {
            gameObjects.add(go);
        } else {
            gameObjects.add(go);
            go.start();
        }

        GameClient.getInstance().sendData("goidu," + GameObject.getIdCounter());
    }

    public GameObject getGameObject(String name)
    {
        Optional<GameObject> result = this.gameObjects.stream()
                .filter(gameObject -> gameObject.getName().equals(name))
                .findFirst();
        return result.orElse(null);
    }

    public GameObject getGameObjectWithUid(int uid)
    {
        int low = 0;
        int high = getGameObjects().size() - 1;

        while (low <= high)
        {
            int midIndex = (low + high) >>> 1;
            Comparable<Integer> midUid = getGameObjects().get(midIndex).getUid();
            int cmp = midUid.compareTo(uid);

            if (cmp < 0) low = midIndex + 1;
            else if (cmp > 0) high = midIndex - 1;
            else return getGameObjects().get(midIndex);
        }

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

    public void gui(Graphics g)
    {

    }

    public void loadResources()
    {

    }

    protected void setBackdrop(Color color)
    {
        this.backdrop = color;
        setBackdrop();
    }

    private void setBackdrop()
    {
        Window.setBackdrop(this.backdrop);
    }

    public void onDestroy()
    {

    }

}
