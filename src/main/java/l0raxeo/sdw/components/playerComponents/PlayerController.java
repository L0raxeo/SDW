package l0raxeo.sdw.components.playerComponents;

import l0raxeo.network.GameClient;
import l0raxeo.network.GameServer;
import l0raxeo.sdw.components.Component;
import l0raxeo.sdw.input.keyboard.KeyManager;
import l0raxeo.sdw.input.mouse.MouseManager;
import l0raxeo.rendering.Camera;
import l0raxeo.rendering.Window;
import org.joml.Vector2i;

public class PlayerController extends Component
{

    /**
     * The angle (in degrees) that the player is looking [0,360)
     */
    private float direction = 0;

    public int xMove = 0;
    public int yMove = 0;

    public int xCam;
    public int yCam;
    public int xMouseCamOffset;
    public int yMouseCamOffset;

    private final int clientUid;

    public PlayerController(int clientUid)
    {
        this.clientUid = clientUid;
    }

    @Override
    public void update(double dt)
    {
        if (clientUid == GameClient.getInstance().myUid)
        {
            move();
            look();
            GameClient.getInstance().sendData(
                    "gon," + gameObject.getUid() + "," + gameObject.transform.position().x + "," + gameObject.transform.position().y + "," + gameObject.transform.scale.x + "," + gameObject.transform.scale.y + "," + gameObject.transform.rotation + "," + gameObject.isDead());
            GameClient.getInstance().sendData(
                    "comp," + gameObject.getUid() + "," + gameObject.getComponent(PlayerControlledTexture.class).uid() + "," + getDirection() + "," + xMove + "," + yMove
            );
        }
    }

    private void move()
    {
        xMove = 0;
        yMove = 0;
        boolean w = false, s = false, a = false, d = false;
        if (KeyManager.isHeld('w'))
            w = true;
        if (KeyManager.isHeld('s'))
            s = true;
        if (KeyManager.isHeld('a'))
            a = true;
        if (KeyManager.isHeld('d'))
            d = true;

        if (w)
        {
            if (a)
            {
                xMove = -3;
                yMove = 3;
            }
            else if (d)
            {
                xMove = 3;
                yMove = 3;
            }
            else yMove = 4;
        }
        else if (s)
        {
            if (a)
            {
                xMove = -3;
                yMove = -3;
            }
            else if (d)
            {
                xMove = 3;
                yMove = -3;
            }
            else yMove = -4;
        }
        else if (d)
            xMove = 4;
        else if (a) xMove = -4;

        gameObject.transform.move(new Vector2i(xMove, yMove));
    }

    private void look()
    {
        direction = Camera.getAngleFromOriginToTarget(
                gameObject.transform.position().x + (gameObject.transform.scale.x / 2),
                gameObject.transform.position().y - (gameObject.transform.scale.y / 3),
                MouseManager.getMouseX(), MouseManager.getMouseY()
        );

        xCam = gameObject.transform.position().x - (Window.WINDOW_WIDTH / 2) + (gameObject.transform.scale.x / 2);
        yCam = gameObject.transform.position().y - (Window.WINDOW_HEIGHT / 2) - (gameObject.transform.scale.y / 2);

        Vector2i targetCamPos = new Vector2i(MouseManager.getMouseX(), MouseManager.getMouseY());

        xMouseCamOffset = (int) ((targetCamPos.x - 480) * 0.20);
        yMouseCamOffset = (int) ((targetCamPos.y - 480) * 0.20);

        Camera.setPosition(new Vector2i(xCam + xMouseCamOffset, yCam + yMouseCamOffset));
    }

    public float getDirection()
    {
        return direction;
    }

}
