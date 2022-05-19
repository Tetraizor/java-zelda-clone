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



    public Enemy() {
        super();
    }

    public void update() throws InterruptedException {
        super.update();

        ProcessAI();
    }

    public void ProcessAI() {

    }

    public void start() {
        super.start();

    }
}
