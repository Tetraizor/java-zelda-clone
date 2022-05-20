package component;

import entity.Entity;
import main.GamePanel;
import util.Vector2;

public class Camera
{
    public Vector2 position = new Vector2(0, 0);
    public Vector2 targetChunk;
    public Vector2 targetPosition;
    public float speed = 8;

    public Camera(int xPosition, int yPosition)
    {
        targetChunk = new Vector2(1, 1);
        targetPosition = new Vector2(0, 0);
        SetTargetChunk((int)targetChunk.x, (int)targetChunk.y);
        SetPosition();

    }

    public void update()
    {
        if(targetChunk == null)
            return;

        SetPosition();
    }

    public void SetTargetChunk(int x, int y)
    {
        targetChunk.x = x;
        targetChunk.y = y;
    }

    public void SetPosition()
    {
        this.targetPosition = new Vector2( (targetChunk.x + .5f) * GamePanel.column * GamePanel.originalTileSize,
                                     ((targetChunk.y * GamePanel.row) + (5) ) * GamePanel.originalTileSize);


        if(position.x < targetPosition.x - 10) {
            position.x += speed;
        } else if(position.x > targetPosition.x + 10) {
            position.x -= speed;
        }
        else {
            position.x = targetPosition.x;
        }

        if(position.y < targetPosition.y - 10) {
            position.y += speed;
        } else if(position.y > targetPosition.y + 10) {
            position.y -= speed;
        }
        else {
            position.y = targetPosition.y;
        }



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
