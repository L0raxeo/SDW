package com.l0raxeo.sdw.components;

import com.l0raxeo.sdw.file.FileLoader;
import com.l0raxeo.sdw.gfx.Animation;

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
        standingForwards = FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_forward.png");
        standingBackwards = FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_back.png");
        standingLeftwards = FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_left.png");
        standingRightwards = FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_right.png");
        standingBackRight = FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_backright.png");
        standingBackLeft = FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_backleft.png");

        walkingForwardAnimation = new Animation(
                150,
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_walking_forward_0.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_forward.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_walking_forward_1.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_forward.png")
        );

        walkingBackAnimation = new Animation(
                150,
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_walking_back_0.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_back.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_walking_back_1.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_back.png")
        );

        walkingRightAnimation = new Animation(
                150,
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_walking_right_0.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_right.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_walking_right_1.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_right.png")
        );

        walkingLeftAnimation = new Animation(
                150,
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_walking_left_0.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_left.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_walking_left_1.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_left.png")
        );

        walkingBackLeftAnimation = new Animation(
                150,
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_walking_backleft_0.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_backleft.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_walking_backleft_1.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_backleft.png")
        );

        walkingBackRightAnimation = new Animation(
                150,
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_walking_backright_0.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_backright.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_walking_backright_1.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_backright.png")
        );

        curAnim = walkingForwardAnimation;
    }

    @Override
    public void update(double dt)
    {
        float theta = gameObject.getComponent(PlayerController.class).getDirection();

        float xOffset = gameObject.getComponent(PlayerController.class).xOffset, yOffset = gameObject.getComponent(PlayerController.class).yOffset;

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

        curAnim.update();
        curTex = curAnim.getCurrentFrame();

        if (xOffset == 0 && yOffset == 0)
        {
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
                (int) gameObject.transform.getScreenPosition().x,
                (int) gameObject.transform.getScreenPosition().y, (int) gameObject.transform.scale.x,
                (int) gameObject.transform.scale.y,
                null
        );
    }

}
