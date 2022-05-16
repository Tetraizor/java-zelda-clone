package entity;

import util.Vector2;

public class EntityMoving extends Entity{


    public float speed;
    public int health;
    public int maxHealth;

    public float invincibilityTime = 0;

    public EntityMoving(String _name, Vector2 _position, float _speed, int _maxHealth)
    {
        super(_name, _position);
        maxHealth = _maxHealth;
        speed = _speed;
        start();
    }

    public EntityMoving() {
        super();
    }

    public void update() {
        super.update();
    }

    public void start() {
        super.start();
    }
}
