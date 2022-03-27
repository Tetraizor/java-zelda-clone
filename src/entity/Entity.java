package entity;

import component.AnimationManager;
import component.Collider;
import component.KeyHandler;
import main.GamePanel;
import main.Main;
import util.RenderUtils;
import util.Vector2;

import java.awt.*;

public class Entity
{
    public String name;
    public Vector2 position;
    public float speed;

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
    Collider collider;

    public Entity(String name, int _x, int _y)
    {
        SetEntityProperties(name, new Vector2(position.x, position.y), 1);

        start();
    }

    public Entity()
    {
        SetEntityProperties("New Entity", new Vector2(0, 0), 1);
        start();
    }

    void SetEntityProperties(String name, Vector2 _position, float _speed)
    {
        this.name = name;
        position = _position;
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
        collider.update();
    }

    public void render(Graphics2D g2D)
    {
        RenderUtils.DrawSprite(position.x, position.y, animationManager.currentImage, true, g2D);
        collider.render(g2D);
    }
}
