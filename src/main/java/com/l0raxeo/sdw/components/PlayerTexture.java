package com.l0raxeo.sdw.components;

import com.l0raxeo.sdw.file.FileLoader;
import com.l0raxeo.sdw.gfx.Animation;

import java.awt.*;

public class PlayerTexture extends Component
{

    private Animation walkingAnimation;

    @Override
    public void start()
    {
        walkingAnimation = new Animation(
                150,
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_walking_forward_0.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_forward.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_walking_forward_1.png"),
                FileLoader.loadImage("assets/textures/entities/players/noah/noah_standing_forward.png")
        );
    }

    @Override
    public void update(double dt)
    {
        walkingAnimation.update();
    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(
                walkingAnimation.getCurrentFrame(),
                (int) gameObject.transform.getScreenPosition().x,
                (int) gameObject.transform.getScreenPosition().y, (int) gameObject.transform.scale.x,
                (int) gameObject.transform.scale.y,
                null
        );
    }

}
