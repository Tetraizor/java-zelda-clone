package resource;

import entity.Entity;

public class MoveOrder {

    public MoveOrder (float _moveAmount, float _moveMultiplier, Entity.Direction _moveDirection) {
        moveAmount = _moveAmount;
        moveMultiplier = _moveMultiplier;
        moveDirection = _moveDirection;
    }

    public float moveAmount;
    public float moveMultiplier;
    public Entity.Direction moveDirection;


}
