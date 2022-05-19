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

    private boolean canHurt;

    public Enemy() {
        super();
    }

    public void update() {
        super.update();

        if(invincibilityTime > 0) {
            invincibilityTime -= 1/60.0f;
        }else
        {
            canHurt = true;
        }

        if (health <= 0) {
            this.SetActive(false);
        }

        if(moveOrders.size() < 2) {
            Move((int)(Math.random() * 32) + 32, intToDirection((int) (Math.random() * 4.5)), 1, false);
        }
    }

    public void start() {
        super.start();
        canHurt = true;
    }

    public void GetDamage(int _damage, float _invincibilityTime, Direction _direction) {
        if(canHurt) {
            canHurt = false;
            health -= _damage;
            invincibilityTime = _invincibilityTime;
            System.out.println(name + " got hurt from " + _direction + " damage! Current health is: " + health);
            Move(2, _direction, 10, true);
        }
    }


}
