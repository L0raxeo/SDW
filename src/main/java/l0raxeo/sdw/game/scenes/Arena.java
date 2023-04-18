package l0raxeo.sdw.game.scenes;

import l0raxeo.sdw.game.components.gfxComponents.ImageTexture;
import l0raxeo.sdw.game.components.itemComponents.Item;
import l0raxeo.sdw.game.components.itemComponents.ItemState;
import l0raxeo.sdw.game.dataStructure.AssetPool;
import l0raxeo.sdw.game.objects.GameObject;
import l0raxeo.sdw.game.prefabs.Prefabs;
import l0raxeo.sdw.game.components.playerComponents.PlayerController;
import l0raxeo.sdw.game.components.playerComponents.PlayerControlledTexture;
import org.joml.Vector2i;

import java.awt.*;
import java.util.Comparator;

public class Arena extends Scene
{

    private final Comparator<GameObject> renderSorter = Comparator.comparingInt(c -> c.transform.getzIndex());

    enum GameState
    {
        DRAFT,
        BUILD,
        FIGHT,
        DEATH,
        SCORE
    }

    @Override
    public void loadResources() {
        setBackdrop(Color.LIGHT_GRAY);
    }

    @Override
    public void init() {
        addGameObject(Prefabs.generate(
                "Noah",
                new Vector2i(),
                new Vector2i(46,76),
                new PlayerControlledTexture(),
                new PlayerController()
        ));

        addGameObject(Prefabs.generate(
                "Cat",
                new Vector2i(),
                new Vector2i(24, 34),
                new Item(getGameObject("Noah"), ItemState.USED),
                new ImageTexture(
                        AssetPool.getBufferedImage("assets/textures/entities/items/hairball_spitting_cat.png"),
                        new Vector2i(12, 17)
                )
        ));
    }

    @Override
    public void update(double dt)
    {
        getGameObjects().sort(renderSorter);
        getGameObjects().forEach
                (
                gameObject -> gameObject.update(dt)
                );
    }

    @Override
    public void render(Graphics g)
    {
        getGameObjects().forEach(
                gameObject -> gameObject.getAllComponents().forEach(
                        component -> component.render(g)
                )
        );
    }

}
