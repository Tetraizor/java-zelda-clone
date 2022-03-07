package entity;

import components.AnimationManager;
import components.KeyHandler;
import main.GamePanel;
import main.Main;

import java.awt.*;
import java.security.Key;

public class Entity
{
    public int x;
    public int y;
    public int speed;

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
        System.out.println("Default entity constructor called.");

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
    }

    public void update()
    {
        // System.out.println("Position: " + x + ", " + y);
    }

    public void render(Graphics2D g2D)
    {
        g2D.setColor(Color.red);
        g2D.fillRect(x, y, GamePanel.tileSize, GamePanel.tileSize);
    }
}
