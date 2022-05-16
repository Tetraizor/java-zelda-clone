package entity;

import component.Collider;
import component.KeyHandler;
import main.GamePanel;
import resource.Collision;
import resource.Tool;
import util.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class Player extends EntityMoving implements ObjectInterface
{
    public Boolean isMoving = false;
    KeyHandler playerKeyHandler = GamePanel.mainKeyHandler;

    ArrayList<Tool> toolList = new ArrayList<Tool>();
    Tool currentTool;
    PlayerTool currentToolEntity;

    boolean isToolBeingUsed;
    float toolTime;
    float cooldownTime;

    public Player(String _name, Vector2 _position, int _speed, int _health)
    {
        super(_name, _position, _speed, _health);

        toolList.add(new Tool("Sword", .4f, 4, false, 0));
        toolList.add(new Tool("Bow", .4f, 4, true, 0));

        toolList.get(0).Enable();
        toolList.get(1).Enable();

        currentTool = toolList.get(0);
        currentToolEntity = (PlayerTool) GamePanel.instance.CreateObject(new PlayerTool("Tool", new Vector2(position.x, position.y), this));

        // Idle
        animationManager.CreateAnimation("/sprite/player/original/player", 1, 1, 10); // Idle Down
        animationManager.CreateAnimation("/sprite/player/original/player", 5, 5, 10); // Idle Right
        animationManager.CreateAnimation("/sprite/player/original/player", 9, 9, 10); // Idle Up
        animationManager.CreateAnimation("/sprite/player/original/player", 13, 13, 10); // Idle Left

        // Walk
        animationManager.CreateAnimation("/sprite/player/original/player", 0, 3, 10); // Walk Down
        animationManager.CreateAnimation("/sprite/player/original/player", 4, 7, 10); // Walk Right
        animationManager.CreateAnimation("/sprite/player/original/player", 8, 11, 10); // Walk Up
        animationManager.CreateAnimation("/sprite/player/original/player", 12, 15, 10); // Walk Left

        // Hit
        animationManager.CreateAnimation("/sprite/player/original/player", 2, 2, 10); // Hit Down
        animationManager.CreateAnimation("/sprite/player/original/player", 6, 6, 10); // Hit Right
        animationManager.CreateAnimation("/sprite/player/original/player", 10, 10, 10); // Hit Up
        animationManager.CreateAnimation("/sprite/player/original/player", 14, 14, 10); // Hit Left

        collider = new Collider(this, 0, 0, 16, 16, true, true);
    }

    public void start() {
        super.start();
    }

    public void update()
    {
        // Update Entity class as well.
        super.update();

        // Handle tools
        if(toolTime > 0) {
            toolTime -= 1/60.0;
        } else {
            isToolBeingUsed = false;
            if(currentToolEntity != null)
                if(currentToolEntity.IsActive()) {
                    currentToolEntity.SetActive(false);
                    cooldownTime = .2f;
                }
        }

        if(cooldownTime > 0) {
            cooldownTime -= 1/60.f;
        }

        // Handle inputs on separate function.
        InputHandler();

        if(isToolBeingUsed)
            animationManager.SwitchAnimation((int)(entityDirection.getValue() + 8));
        else if(!isToolBeingUsed && isMoving)
            animationManager.SwitchAnimation((int)(entityDirection.getValue() + 4));
        else
            animationManager.SwitchAnimation((int)(entityDirection.getValue()));
    }

    public void render(Graphics2D g2D)
    {
        // Render Entity class as well.
        super.render(g2D);
    }

    void InputHandler()
    {
        isMoving = false;

        Vector2 moveVector = new Vector2(0, 0);

        if(playerKeyHandler.keyList.get(0).isButtonDown)
        {
            if(!isToolBeingUsed)
            entityDirection = Direction.UP;
            moveVector.y = -speed;
            isMoving = true;
        }

        else if(playerKeyHandler.keyList.get(1).isButtonDown)
        {
            if(!isToolBeingUsed)
            entityDirection = Direction.DOWN;
            moveVector.y = speed;
            isMoving = true;
        }

        else if(playerKeyHandler.keyList.get(2).isButtonDown)
        {
            if(!isToolBeingUsed)
            entityDirection = Direction.RIGHT;
            moveVector.x = speed;
            isMoving = true;
        }

        else if(playerKeyHandler.keyList.get(3).isButtonDown)
        {
            if(!isToolBeingUsed)
            entityDirection = Direction.LEFT;
            moveVector.x = -speed;
            isMoving = true;
        }

        if(playerKeyHandler.keyList.get(4).isButtonDown && !playerKeyHandler.keyList.get(4).isPressed) {
            playerKeyHandler.keyList.get(4).isPressed = true;
            ChangeWeapon();
        }

        if(playerKeyHandler.keyList.get(5).isButtonDown && !playerKeyHandler.keyList.get(5).isPressed) {
            playerKeyHandler.keyList.get(5).isPressed = true;
            if(cooldownTime < 0)
                SwingWeapon();
        }

        collider.CheckForCollisions();

        moveVector.Normalize();

        for (Collision collision : collider.collisions) {
            if(collision.direction == entityDirection && collision.isSolid)
                isMoving = false;
        }
        if(isMoving && !isToolBeingUsed)
            position.Add(moveVector);
    }

    public void PrintPlayerValues()
    {
        System.out.println("Player position: " + this.position.x + ", " + this.position.y);
    }

    public void SwingWeapon()
    {
        if(!isToolBeingUsed) {
            switch (entityDirection) {
                case UP:
                    currentToolEntity.position.x = position.x;
                    currentToolEntity.position.y = position.y - 15;
                    currentToolEntity.animationManager.SwitchAnimation(1);
                    break;

                case DOWN:
                    currentToolEntity.position.x = position.x;
                    currentToolEntity.position.y = position.y + 15;
                    currentToolEntity.animationManager.SwitchAnimation(0);
                    break;

                case LEFT:
                    currentToolEntity.position.x = position.x - 10;
                    currentToolEntity.position.y = position.y + 1;
                    currentToolEntity.animationManager.SwitchAnimation(3);
                    break;

                case RIGHT:
                    currentToolEntity.position.x = position.x + 10;
                    currentToolEntity.position.y = position.y + 1;
                    currentToolEntity.animationManager.SwitchAnimation(2);
                    break;
            }

            isToolBeingUsed = true;
            toolTime = currentTool.swingTime;
            currentToolEntity.SetActive(true);

            currentToolEntity.Hurt(currentTool.damage, currentTool.swingTime);
        }
    }

    public void ChangeWeapon() {

        boolean gotTool = false;

        while(!gotTool) {
            int currentIndex = toolList.indexOf(currentTool);

            if(currentIndex == toolList.size() - 1)
                currentIndex = 0;
            else
                currentIndex++;

            gotTool = toolList.get(currentIndex).isEnabled;

            if(gotTool)
                currentTool = toolList.get(currentIndex);
        }

        System.out.println("Changed to tool " + currentTool.name);
    }
}
