package entity;

import component.AnimationManager;
import component.Collider;

import java.awt.*;

public class Player extends Entity
{
    public Boolean isMoving = false;

    public Player(int _x, int _y, int _speed)
    {
        SetEntityProperties(_x, _y, _speed);
    }

    public void start()
    {
        super.start();

        // Walk
        animationManager.CreateAnimation("player", 1, 1, 10); // Idle Down
        animationManager.CreateAnimation("player", 5, 5, 10); // Idle Right
        animationManager.CreateAnimation("player", 9, 9, 10); // Idle Up
        animationManager.CreateAnimation("player", 13, 13, 10); // Idle Left

        // Idle
        animationManager.CreateAnimation("player", 0, 3, 10); // Walk Down
        animationManager.CreateAnimation("player", 4, 7, 10); // Walk Right
        animationManager.CreateAnimation("player", 8, 11, 10); // Walk Up
        animationManager.CreateAnimation("player", 12, 15, 10); // Walk Left

        collider = new Collider(this, 4, 7, 9, 9);

    }

    public void update()
    {

        // Update Entity class as well.
        super.update();

        // Handle inputs on separate function.
        InputHandler();

        if(isMoving)
        {
            animationManager.SwitchAnimation((int)(entityDirection.getValue() + 4));
        }
        else
        {
            animationManager.SwitchAnimation((int)(entityDirection.getValue()));
        }
    }

    public void render(Graphics2D g2D)
    {
        // Render Entity class as well.
        super.render(g2D);
    }

    void InputHandler()
    {
        isMoving = false;

        if(playerKeyHandler.keyList.get(0).isButtonDown)
        {
            entityDirection = Direction.UP;
            y -= speed;
            isMoving = true;
        }

        if(playerKeyHandler.keyList.get(1).isButtonDown)
        {
            entityDirection = Direction.DOWN;
            y += speed;
            isMoving = true;
        }

        if(playerKeyHandler.keyList.get(2).isButtonDown)
        {
            entityDirection = Direction.RIGHT;
            x += speed;
            isMoving = true;
        }

        if(playerKeyHandler.keyList.get(3).isButtonDown)
        {
            entityDirection = Direction.LEFT;
            x -= speed;
            isMoving = true;
        }
    }

    public void PrintPlayerValues()
    {
        System.out.println("Player position: " + this.x + ", " + this.y);
    }
}
