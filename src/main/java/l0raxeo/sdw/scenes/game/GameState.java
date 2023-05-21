package l0raxeo.sdw.scenes.game;

import l0raxeo.rendering.Window;
import l0raxeo.sdw.scenes.game.initializers.*;
import l0raxeo.sdw.ui.GuiLayer;

import java.util.Objects;

public enum GameState
{

    DRAFT(new DraftStateInitializer((Game) Window.getScene(Game.class))),
    BUILD(new BuildStateInitializer((Game) Objects.requireNonNull(Window.getScene(Game.class)))),
    FIGHT(new FightStateInitializer((Game) Objects.requireNonNull(Window.getScene(Game.class)))),
    EMPTY(new EmptyStateInitializer());

    public final GameStateInitializer initializer;

    GameState(GameStateInitializer initializer)
    {
        this.initializer = initializer;
    }

    private static GameState gameState = EMPTY;

    public static void setState(GameState state)
    {
        GuiLayer.getInstance().clear();
        state.initializer.loadResources();
        state.initializer.start();
        gameState = state;
    }

    public static GameState getGameState()
    {
        return gameState;
    }

}
