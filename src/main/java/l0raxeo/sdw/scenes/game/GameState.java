package l0raxeo.sdw.scenes.game;

import l0raxeo.rendering.Window;
import l0raxeo.sdw.scenes.game.initializers.BuildStateInitializer;
import l0raxeo.sdw.scenes.game.initializers.DraftStateInitializer;
import l0raxeo.sdw.scenes.game.initializers.FightStateInitializer;
import l0raxeo.sdw.scenes.game.initializers.GameStateInitializer;
import l0raxeo.sdw.ui.GuiLayer;

import java.util.Objects;

public enum GameState
{

    DRAFT(new DraftStateInitializer((Game) Window.getScene(Game.class))),
    BUILD(new BuildStateInitializer((Game) Objects.requireNonNull(Window.getScene(Game.class)))),
    FIGHT(new FightStateInitializer((Game) Objects.requireNonNull(Window.getScene(Game.class)))),
    NONE(null);

    public final GameStateInitializer initializer;

    GameState(GameStateInitializer initializer)
    {
        this.initializer = initializer;
    }

    private static GameState gameState = NONE;

    public static void setState(GameState state)
    {
        GuiLayer.getInstance().clear();
        if (state.initializer != null)
        {
            state.initializer.loadResources();
            state.initializer.start();
        }

        gameState = state;
    }

    public static GameState getGameState()
    {
        return gameState;
    }

}
