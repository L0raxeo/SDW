package l0raxeo.sdw.scenes.game;

public enum GameState
{

    DRAFT,
    BUILD,
    FIGHT,
    DEATH,
    SCORE,
    NONE;

    private static GameState gameState = NONE;

    public static void setGameState(GameState state)
    {
        gameState = state;
    }

    public static GameState getGameState()
    {
        return gameState;
    }

}
