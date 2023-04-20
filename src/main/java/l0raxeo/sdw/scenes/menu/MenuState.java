package l0raxeo.sdw.scenes.menu;

import l0raxeo.sdw.ui.GuiLayer;

public enum MenuState
{

    MAIN(new MainMenu()),
    HOST(new HostMenu()),
    JOIN(new JoinMenu()),
    LOBBY(new LobbyMenu()),
    NONE(null);

    public final MenuInitializer initializer;

    MenuState(MenuInitializer initializer)
    {
        this.initializer = initializer;
    }

    private static MenuState menuState = NONE;

    public static void setState(MenuState state)
    {
        GuiLayer.getInstance().clear();
        if (state.initializer != null)
            state.initializer.create();

        menuState = state;
    }

    public static MenuState getState()
    {
        return menuState;
    }

}
