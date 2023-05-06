package l0raxeo.sdw.components.entityComponents;

import l0raxeo.sdw.components.Component;

import java.awt.*;

public class HealthSystem extends Component
{

    private boolean visible;
    private float health = 100;
    private Color curColor = Color.GREEN;

    public HealthSystem(boolean visible)
    {
        this.visible = visible;
    }

    @Override
    public void update(double dt)
    {
        if (health <= 0)
            gameObject.die();
    }

    @Override
    public void render(Graphics g)
    {
        if (visible)
        {
            g.setColor(Color.GRAY);
            g.fillRect((gameObject.transform.getScreenPosition().x - 32) + (gameObject.transform.scale.x / 2), gameObject.transform.getCenterPosition().y - (gameObject.transform.scale.y / 2) - 18, 64, 12);
            g.setColor(curColor);
            g.fillRect((gameObject.transform.getScreenPosition().x - 30) + (gameObject.transform.scale.x / 2), gameObject.transform.getCenterPosition().y - (gameObject.transform.scale.y / 2) - 16, (int) ((health / 100) * 60), 8);
        }
    }

    public float getHealth()
    {
        return health;
    }

    public void damage(int damage)
    {
        this.health -= damage;
        curColor = health > 50 ? (health > 75 ? Color.GREEN : Color.YELLOW) : (health > 25 ? Color.ORANGE : Color.RED);
    }

    public void heal(int health)
    {
        this.health += health;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

}
