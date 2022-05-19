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
    public Tool currentTool;
    PlayerTool currentToolEntity;

    public boolean canInput;

    public int arrowCount;
    public int magic;

    boolean isToolBeingUsed;
    float toolTime;
    float cooldownTime;

    public Player(String _name, Vector2 _position, int _speed, int _health)
    {
        super(_name, _position, _speed, _health);

        toolList.add(new Tool("Sword", .4f, 6, 1.3f,false, 0, 0));
        toolList.add(new Tool("Bow", .4f, 4, 0.8f, true, 4, 1));
        toolList.add(new Tool("Fire Wand", .6f, 3, .2f, true, 8, 2));
        toolList.add(new Tool("Stick", .6f, 4, 1f, false, 12, 3));

        toolList.get(3).Enable();
        currentTool = toolList.get(3);

        currentToolEntity = (PlayerTool) GamePanel.instance.CreateObject(new PlayerTool("PlayerTool", new Vector2(position.x, position.y), this));

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

        // Collect item
        animationManager.CreateAnimation("/sprite/player/original/player", 31, 1);

        // Die
        animationManager.CreateAnimation("/sprite/player/original/player", 19, 1);

        collider = new Collider(this, 0, 0, 16, 16, true, true);

        canInput = true;
    }

    public void start() {
        super.start();
    }

    public void update() throws InterruptedException {
        // Update EntityMoving class as well.
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
        if(canInput)
            InputHandler();

        if(!isMovingForced && canInput) {
            if(isToolBeingUsed)
                animationManager.SwitchAnimation((int)(entityDirection.getValue() + 8));
            else if(!isToolBeingUsed && isMoving)
                animationManager.SwitchAnimation((int)(entityDirection.getValue() + 4));
            else
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
        if(isMoving && !isToolBeingUsed && !isMovingForced)
            position.Add(moveVector);
    }

    public void PrintPlayerValues()
    {
        System.out.println("Player position: " + this.position.x + ", " + this.position.y);
    }

    public void SwingWeapon()
    {
        if(!isToolBeingUsed && !isMovingForced) {
            switch (entityDirection) {
                case UP:
                    currentToolEntity.position.x = position.x;
                    currentToolEntity.position.y = position.y - 15;
                    currentToolEntity.animationManager.SwitchAnimation(1 + 4 * currentTool.index);
                    break;

                case DOWN:
                    currentToolEntity.position.x = position.x;
                    currentToolEntity.position.y = position.y + 15;
                    currentToolEntity.animationManager.SwitchAnimation(0 + 4 * currentTool.index);
                    break;

                case LEFT:
                    currentToolEntity.position.x = position.x - 10;
                    currentToolEntity.position.y = position.y + 1;
                    currentToolEntity.animationManager.SwitchAnimation(3 + 4 * currentTool.index);
                    break;

                case RIGHT:
                    currentToolEntity.position.x = position.x + 10;
                    currentToolEntity.position.y = position.y + 1;
                    currentToolEntity.animationManager.SwitchAnimation(2 + 4 * currentTool.index);
                    break;
            }


            isToolBeingUsed = true;
            toolTime = currentTool.swingTime;
            currentToolEntity.SetActive(true);

            currentToolEntity.Hurt(currentTool);
        }
    }

    public void ChangeWeapon() {

        boolean gotTool = false;

        int currentIndex = toolList.indexOf(currentTool);

        while(!gotTool) {
            int toolCount = 0;

            for(Tool tool : toolList)
                if(tool.isEnabled)
                    toolCount++;

            if(toolCount < 2)
                break;

            if(currentIndex == toolList.size() - 1)
                currentIndex = 0;
            else
                currentIndex++;

            gotTool = toolList.get(currentIndex).isEnabled;

            if(gotTool)
                currentTool = toolList.get(currentIndex);
        }
    }

    public void KillEntity() {

        if(!isDead) {
            isDead = true;

            System.out.println("Im dead :(");
            GamePanel.instance.stopMusic();
            GamePanel.instance.playSound(6);
            Move(100, entityDirection, 1,true);
            animationManager.SwitchAnimation(13);
            canInput = false;
        }
    }
}
