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

    public float originalSpeed;

    public boolean canHurt;
    public boolean isMovingForced;

    public boolean isOnFire;
    public boolean isFrozen;

    public float fireTimer;
    public float iceTimer;

    public float fireCooldown;

    public ArrayList<MoveOrder> moveOrders;
    public MoveOrder currentMoveOrder;

    public Decor fireEffect;
    public Decor iceEffect;

    public float invincibilityTime = 0;

    public EntityMoving(String _name, Vector2 _position, float _speed, int _maxHealth)
    {
        super(_name, _position);
        maxHealth = _maxHealth;
        health = maxHealth;
        isMovingForced = false;

        originalSpeed = _speed;
        speed = originalSpeed;

        fireEffect = (Decor)GamePanel.instance.CreateObject(new Decor("FireEffect", position, 1,true));
        iceEffect = (Decor)GamePanel.instance.CreateObject(new Decor("IceEffect", position, 2, true));

        fireEffect.SetActive(false);
        iceEffect.SetActive(false);
    }

    public EntityMoving() {
        super();
    }

    public void SetOnFire(float _time) {
        if(!isOnFire && !isFrozen) {
            isOnFire = true;
            speed = originalSpeed * 1.4f;
            fireEffect.SetActive(true);
            fireTimer = _time;
            fireCooldown = 0;
        }
    }

    public void ExtinguishFire() {
        if(isOnFire) {
            isOnFire = false;
            speed = originalSpeed;
            fireEffect.SetActive(false);
            fireCooldown = 0;
        }
    }

    public void Freeze(float _time) {
        if(!isOnFire && !isFrozen) {
            isFrozen = true;
            speed = 0;
            iceEffect.SetActive(true);
            iceTimer = _time;
        }
    }

    public void RemoveIce() {
        if(isFrozen) {
            isFrozen = false;
            speed = originalSpeed;
            iceEffect.SetActive(false);
        }
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

        if(fireTimer > 0) {
            fireTimer -= 1/60.0f;
            if(fireCooldown > 0) {
                fireCooldown -= 1/60.0f;
            }
            else
            {
                GetDamage(1, 0.2f, 0, entityDirection);
                fireCooldown = 1;
            }
        }else
        {
            if(isOnFire)
                ExtinguishFire();
        }

        if(iceTimer > 0) {
            iceTimer -= 1/60.0f;
        }else
        {
            if(isFrozen)
                RemoveIce();
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
            GamePanel.instance.CreateObject(new Decor("SlimeCorpse", new Vector2(position.x, position.y), 0, false));
        }
    }

    public void GetDamage(int _damage, float _invincibilityTime, float _knockback, Direction _direction) {
        if(canHurt) {
            GamePanel.instance.playSound(1);
            canHurt = false;
            health -= _damage;
            invincibilityTime = _invincibilityTime;
            Move(_knockback, _direction, 10, true);
        }
    }
}
