package l0raxeo.sdw.game.components.playerComponents;

import l0raxeo.sdw.game.components.Component;
import l0raxeo.sdw.game.dataStructure.AssetPool;
import l0raxeo.sdw.game.gfx.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerControlledTexture extends Component
{

    private Animation curAnim;
    private Animation walkingForwardAnimation;
    private Animation walkingBackAnimation;
    private Animation walkingRightAnimation;
    private Animation walkingLeftAnimation;
    private Animation walkingBackRightAnimation;
    private Animation walkingBackLeftAnimation;

    private BufferedImage curTex;
    private BufferedImage standingForwards;
    private BufferedImage standingBackwards;
    private BufferedImage standingLeftwards;
    private BufferedImage standingRightwards;
    private BufferedImage standingBackRight;
    private BufferedImage standingBackLeft;

    @Override
    public void start()
    {
        standingForwards = AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_forward.png");
        standingBackwards = AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_back.png");
        standingLeftwards = AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_left.png");
        standingRightwards = AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_right.png");
        standingBackRight = AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_backright.png");
        standingBackLeft = AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_backleft.png");

        walkingForwardAnimation = new Animation(
                150,
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_forward_0.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_forward.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_forward_1.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_forward.png")
        );

        walkingBackAnimation = new Animation(
                150,
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_back_0.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_back.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_back_1.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_back.png")
        );

        walkingRightAnimation = new Animation(
                150,
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_right_0.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_right.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_right_1.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_right.png")
        );

        walkingLeftAnimation = new Animation(
                150,
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_left_0.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_left.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_left_1.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_left.png")
        );

        walkingBackLeftAnimation = new Animation(
                150,
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_backleft_0.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_backleft.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_backleft_1.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_backleft.png")
        );

        walkingBackRightAnimation = new Animation(
                150,
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_backright_0.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_backright.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_walking_backright_1.png"),
                AssetPool.getBufferedImage("assets/textures/entities/players/noah/noah_standing_backright.png")
        );

        curAnim = walkingForwardAnimation;
    }

    @Override
    public void update(double dt)
    {
        float theta = gameObject.getComponent(PlayerController.class).getDirection();

        float xOffset = gameObject.getComponent(PlayerController.class).xMove, yOffset = gameObject.getComponent(PlayerController.class).yMove;

        if ((theta >= 0 && theta <= 22.5) || (theta > 292.5 && theta <= 360))
            curAnim = walkingRightAnimation;
        else if (theta > 22.5 && theta <= 67.5)
            curAnim = walkingBackRightAnimation;
        else if (theta > 67.5 && theta <= 112.5)
            curAnim = walkingBackAnimation;
        else if (theta > 112.5 && theta <= 157.5)
            curAnim = walkingBackLeftAnimation;
        else if ((theta > 157.5 && theta <= 202.5) || (theta > 202.5 && theta <= 247.5))
            curAnim = walkingLeftAnimation;
        else if (theta > 247.5 && theta <= 292.5)
            curAnim = walkingForwardAnimation;

        if (xOffset != 0 || yOffset != 0)
        {
            curAnim.update();
            curTex = curAnim.getCurrentFrame();
        }
        else {
            if (curAnim.equals(walkingForwardAnimation))
                curTex = standingForwards;
            else if (curAnim.equals(walkingBackAnimation))
                curTex = standingBackwards;
            else if (curAnim.equals(walkingLeftAnimation))
                curTex = standingLeftwards;
            else if (curAnim.equals(walkingRightAnimation))
                curTex = standingRightwards;
            else if (curAnim.equals(walkingBackLeftAnimation))
                curTex = standingBackLeft;
            else if (curAnim.equals(walkingBackRightAnimation))
                curTex = standingBackRight;
        }
    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(
                curTex,
                gameObject.transform.getScreenPosition().x,
                gameObject.transform.getScreenPosition().y,
                gameObject.transform.scale.x,
                gameObject.transform.scale.y,
                null
        );
    }

}
