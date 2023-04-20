package l0raxeo.sdw.scenes.game;

import l0raxeo.sdw.scenes.game.initializers.BuildStateInitializer;
import l0raxeo.sdw.scenes.game.initializers.DraftStateInitializer;
import l0raxeo.sdw.scenes.game.initializers.FightStateInitializer;
import l0raxeo.sdw.scenes.game.initializers.GameStateInitializer;
import l0raxeo.sdw.ui.GuiLayer;

public enum GameState
{

    DRAFT(new DraftStateInitializer()),
    BUILD(new BuildStateInitializer()),
    FIGHT(new FightStateInitializer()),
    NONE(null);

    public final GameStateInitializer initializer;

    GameState(GameStateInitializer initializer)
    {
        this.initializer = initializer;
    }

    private static GameState gameState = NONE;

    public static void setGameState(GameState state)
    {
        GuiLayer.getInstance().clear();
        if (state.initializer != null)
        {
            state.initializer.loadResources();
            state.initializer.init();
            state.initializer.start();
        }

        gameState = state;
    }

    public static GameState getGameState()
    {
        return gameState;
    }

}
