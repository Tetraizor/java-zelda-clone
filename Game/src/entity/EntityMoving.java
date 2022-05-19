package entity;

import resource.Collision;
import resource.MoveOrder;
import util.Vector2;

import java.util.ArrayList;

public class EntityMoving extends Entity{


    public float speed;
    public int health;
    public int maxHealth;

    public ArrayList<MoveOrder> moveOrders;
    public MoveOrder currentMoveOrder;

    public float invincibilityTime = 0;

    public EntityMoving(String _name, Vector2 _position, float _speed, int _maxHealth)
    {
        super(_name, _position);
        maxHealth = _maxHealth;
        speed = _speed;
    }

    public EntityMoving() {
        super();
    }

    public void update() {
        super.update();

        boolean isMoving = true;
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

            for (Collision collision : collider.collisions) {
                if (collision.direction == entityDirection && collision.isSolid)
                    isMoving = false;
            }

            if (isMoving)
                position.Add(moveVector);
        }
        else
        {
            if(moveOrders.size() < 2) {

            }
            else
            {
                moveOrders.remove(currentMoveOrder);
                currentMoveOrder = moveOrders.get(0);
            }

            collider.CheckForCollisions();
        }
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
    }
}
