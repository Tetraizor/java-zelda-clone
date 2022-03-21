package component;

import entity.Entity;
import main.GamePanel;
import util.RenderUtils;

import java.awt.*;

public class Collider
{
    Entity parent;
    int xPos = 0, yPos = 0;
    int xOffset = 0, yOffset = 0;

    public Collider(Entity _parent, int _xPos, int _yPos, int _xOffset, int _yOffset)
    {
        parent = _parent;
        xPos = _xPos;
        yPos = _yPos;
        xOffset = _xOffset;
        yOffset = _yOffset;
    }

    public Boolean IsColliding()
    {
        return true;
    }

    public Boolean OnCollisionEnter()
    {
        return true;
    }

    public void update()
    {
        switch(parent.entityDirection)
        {
            case DOWN:
                break;

            case UP:
                break;

            case LEFT:
                break;

            case RIGHT:
                break;
        }
    }

    public void render(Graphics2D g2D)
    {
        g2D.setColor(Color.red);
        RenderUtils.DrawRect(parent.x + xPos, parent.y + yPos, xOffset, yOffset, Color.red, g2D);
    }
}
