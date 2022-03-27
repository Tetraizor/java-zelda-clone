package component;

import entity.Entity;
import main.GamePanel;
import util.Vector2;

public class Camera
{
    public Vector2 position;
    public Entity target;

    public Camera(int xPosition, int yPosition)
    {
        SetPosition(xPosition, yPosition);
    }

    public Camera(int xPosition, int yPosition, Entity target)
    {
        SetPosition(xPosition, yPosition);
        this.target = target;
    }

    public void update()
    {
        if(target == null)
            return;

        SetPosition(target.position.x, target.position.y);
    }

    public void SetTarget(Entity _target)
    {
        target = _target;
    }

    public void SetPosition(float x, float y)
    {
        // Clamp position

        this.position = new Vector2(x, y);

    }

    public Boolean IsInsideBoundaries(float _x, float _y, float _offset)
    {
        if(_x <= (position.x + (float)GamePanel.screenWidth / (2 * GamePanel.scaleFactor)) + _offset && _x >= (position.x - (float)GamePanel.screenWidth / (2 * GamePanel.scaleFactor)) - _offset)
        {
            if(_y <= (position.y + (float)GamePanel.screenHeight / (2 * GamePanel.scaleFactor)) + _offset && _y >= (position.y - (float)GamePanel.screenHeight / (2 * GamePanel.scaleFactor)) - _offset)
            {
                return true;
            }
        }

        return false;
    }

    public Boolean IsInsideBoundaries(float _x, float _y)
    {
        if(_x <= (position.x + (float)GamePanel.screenWidth / (2 * GamePanel.scaleFactor)) && _x >= (position.x - (float)GamePanel.screenWidth / (2 * GamePanel.scaleFactor)))
        {
            if(_y <= (position.y + (float)GamePanel.screenHeight / (2 * GamePanel.scaleFactor)) && _y >= (position.y - (float)GamePanel.screenHeight / (2 * GamePanel.scaleFactor)))
            {
                return true;
            }
        }

        return false;
    }
}
