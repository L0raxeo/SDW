package l0raxeo.sdw.scenes.menu;

import l0raxeo.sdw.scenes.menu.initializers.*;
import l0raxeo.sdw.ui.GuiLayer;

public enum MenuState
{

    MAIN(new MainMenuInitializer()),
    HOST(new HostMenuInitializer()),
    JOIN(new JoinMenuInitializer()),
    LOBBY(new LobbyMenuInitializer()),
    NONE(null);

    public final MenuStateInitializer initializer;

    MenuState(MenuStateInitializer initializer)
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
