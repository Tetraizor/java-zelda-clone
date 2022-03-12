package entity;

import component.AnimationManager;
import component.KeyHandler;
import main.GamePanel;
import main.Main;

import java.awt.*;

public class Entity
{
    public int x;
    public int y;
    public int speed;

    public enum Direction
    {
        UP(2),
        LEFT(3),
        RIGHT(1),
        DOWN(0);

        private final int value;

        private Direction(int _value)
        {
            value = _value;
        }

        public int getValue()
        {
            return value;
        }
    }

    public Direction entityDirection = Direction.DOWN;

    KeyHandler playerKeyHandler = GamePanel.mainKeyHandler;
    GamePanel currentGP = Main.currentGamePanel;
    AnimationManager animationManager;

    public Entity(int _x, int _y)
    {
        SetEntityProperties(x, y, 1);

        start();
    }

    public Entity()
    {
        SetEntityProperties(0, 0, 1);

        start();
    }

    void SetEntityProperties(int _x, int _y, int _speed)
    {
        x = _x;
        y = _y;
        speed = _speed;
    }

    public void start()
    {
        System.out.println("Entity start");
        animationManager = new AnimationManager();
    }

    public void update()
    {
        animationManager.update();
    }

    public void render(Graphics2D g2D)
    {
        g2D.setColor(Color.red);
        g2D.drawImage(animationManager.currentImage, x, y, GamePanel.tileSize, GamePanel.tileSize, null);
    }
}
