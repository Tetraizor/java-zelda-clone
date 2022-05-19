package entity;

import main.GamePanel;
import resource.Collision;
import resource.MoveOrder;
import util.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class EntityMoving extends Entity{


    public float speed;
    public int health;
    public int maxHealth;

    public boolean canHurt;
    public boolean isMovingForced;

    public ArrayList<MoveOrder> moveOrders;
    public MoveOrder currentMoveOrder;

    public float invincibilityTime = 0;

    public EntityMoving(String _name, Vector2 _position, float _speed, int _maxHealth)
    {
        super(_name, _position);
        maxHealth = _maxHealth;
        health = maxHealth;
        speed = _speed;
        isMovingForced = false;
    }

    public EntityMoving() {
        super();
    }

    public void CheckForHealth() {
        if(invincibilityTime > 0) {

            invincibilityTime -= 1/60.0f;
        }else
        {
            canHurt = true;
        }

        if (health <= 0) {
            this.KillEntity();
        }
    }

    public void update() throws InterruptedException {
        super.update();

        boolean isMoving = true;
        isMovingForced = false;
        Vector2 moveVector = new Vector2(0, 0);

        if(currentMoveOrder.moveAmount > 0) {
            switch (currentMoveOrder.moveDirection) {
                case UP -> {
                    moveVector.y -= speed * currentMoveOrder.moveMultiplier;
                    currentMoveOrder.moveAmount -= speed;
                    entityDirection = Direction.UP;
                }
                case LEFT -> {
                    moveVector.x -= speed * currentMoveOrder.moveMultiplier;
                    currentMoveOrder.moveAmount -= speed;
                    entityDirection = Direction.LEFT;
                }
                case RIGHT -> {
                    moveVector.x += speed * currentMoveOrder.moveMultiplier;
                    currentMoveOrder.moveAmount -= speed;
                    entityDirection = Direction.RIGHT;
                }
                case DOWN -> {
                    moveVector.y += speed * currentMoveOrder.moveMultiplier;
                    currentMoveOrder.moveAmount -= speed;
                    entityDirection = Direction.DOWN;
                }
            }

            collider.CheckForCollisions();

            isMovingForced = true;

            for (Collision collision : collider.collisions) {
                if (collision.direction == entityDirection && collision.isSolid)
                    isMoving = false;
            }

            if (isMoving)
                position.Add(moveVector);
        }
        else
        {
            isMovingForced = false;

            if(moveOrders.size() >= 2) {
                moveOrders.remove(currentMoveOrder);
                currentMoveOrder = moveOrders.get(0);
            }

            collider.CheckForCollisions();
        }

        CheckForHealth();
    }

    public void Move(float _amount, Direction _direction, float _multiplier, boolean _override) {

        MoveOrder temp = new MoveOrder(_amount, _multiplier, _direction);

        if(!_override)
            moveOrders.add(temp);
        else
            if(moveOrders.size() == 0)
                moveOrders.add(temp);
            else
                moveOrders.set(0, temp);

        currentMoveOrder = moveOrders.get(0);
    }

    public void start() {
        super.start();
        moveOrders = new ArrayList<MoveOrder>();
        Move(16, entityDirection, 0, false);

        canHurt = true;
    }



    public void KillEntity() {
        super.KillEntity();

        if(name.equalsIgnoreCase("Slime")) {
            GamePanel.instance.CreateObject(new Decor("SlimeCorpse", new Vector2(position.x, position.y), 0));
            System.out.println("AAA");
        }
    }

    public void GetDamage(int _damage, float _invincibilityTime, float _knockback, Direction _direction) {
        if(canHurt) {
            GamePanel.instance.playSound(1);
            canHurt = false;
            health -= _damage;
            invincibilityTime = _invincibilityTime;
            System.out.println(name + " got hurt from " + _direction + " damage! Current health is: " + health);
            Move(_knockback, _direction, 10, true);
        }
    }
}
