package entity;

import component.Collider;
import resource.Collision;
import util.Vector2;

import java.util.Random;

public class Enemy extends EntityMoving implements ObjectInterface
{
    public Enemy(String _name, Vector2 _position, float _speed, int _maxHealth)
    {
        super(_name, _position, _speed, _maxHealth);
    }

    private float moveAmount;
    private float multiplier = 1;
    private Direction moveDirection;
    private boolean canHurt;

    public Enemy() {
        super();
    }

    public void update() {
        super.update();

        boolean isMoving = true;
        Vector2 moveVector = new Vector2(0, 0);

        if(moveAmount > 0) {
            switch (moveDirection) {
                case UP:
                    moveVector.y -= speed * multiplier;
                    moveAmount -= speed * multiplier;
                    entityDirection = Direction.UP;
                    break;

                case LEFT:
                    moveVector.x -= speed * multiplier;
                    moveAmount -= speed * multiplier;
                    entityDirection = Direction.LEFT;
                    break;

                case RIGHT:
                    moveVector.x += speed * multiplier;
                    moveAmount -= speed * multiplier;
                    entityDirection = Direction.RIGHT;
                    break;

                case DOWN:
                    moveVector.y += speed * multiplier;
                    moveAmount -= speed * multiplier;
                    entityDirection = Direction.DOWN;
                    break;
            }

            collider.CheckForCollisions();

            for (Collision collision : collider.collisions) {
                if (collision.direction == entityDirection && collision.isSolid)
                    isMoving = false;
            }

            if (isMoving)
                position.Add(moveVector);
        }else
        {
            Move((int)(Math.random() * 32) + 32, intToDirection((int) (Math.random() * 4.5)), 1);
        }

        if(invincibilityTime > 0) {
            invincibilityTime -= 1/60.0f;
        }else
        {
            canHurt = true;
        }

        if (health <= 0) {
            this.SetActive(false);
        }
    }

    public void start() {
        super.start();
        canHurt = true;
        Move((int)(Math.random() * 32) + 32, intToDirection((int) (Math.random() * 4.5)), 1);
    }

    public void GetDamage(int _damage, float _invincibilityTime, Direction _direction) {
        if(canHurt) {
            canHurt = false;
            health -= _damage;
            invincibilityTime = _invincibilityTime;
            System.out.println(name + " got hurt with " + _damage + " damage! Current health is: " + health);
            moveAmount = 20;
            moveDirection = _direction;
            multiplier = 10;
        }
    }

    public void Move(float _amount, Direction _direction, float _multiplier) {
        moveAmount = _amount;
        moveDirection = _direction;
        multiplier = _multiplier;
    }
}
