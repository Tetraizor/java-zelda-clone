package component;

import entity.Entity;
import main.GamePanel;
import util.Vector2;

public class Camera
{
    public Vector2 position = new Vector2(0, 0);
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

    public Boolean IsInsideBoundaries(Vector2 _pos, float _offset)
    {
        if(_pos.x <= (position.x + (float)GamePanel.screenWidth / (2 * GamePanel.scaleFactor)) + _offset && _pos.x >= (position.x - (float)GamePanel.screenWidth / (2 * GamePanel.scaleFactor)) - _offset)
        {
            if(_pos.y <= (position.y + (float)GamePanel.screenHeight / (2 * GamePanel.scaleFactor)) + _offset && _pos.y >= (position.y - (float)GamePanel.screenHeight / (2 * GamePanel.scaleFactor)) - _offset)
            {
                return true;
            }
        }

        return false;
    }

    public Boolean IsInsideBoundaries(Vector2 _pos)
    {
        if(_pos.x <= (position.x + (float)GamePanel.screenWidth / (2 * GamePanel.scaleFactor)) && _pos.x >= (position.x - (float)GamePanel.screenWidth / (2 * GamePanel.scaleFactor)))
        {
            if(_pos.y <= (position.y + (float)GamePanel.screenHeight / (2 * GamePanel.scaleFactor)) && _pos.y >= (position.y - (float)GamePanel.screenHeight / (2 * GamePanel.scaleFactor)))
            {
                return true;
            }
        }

        return false;
    }
}
