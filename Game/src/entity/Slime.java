package entity;

import component.Collider;
import resource.Collision;
import util.Vector2;

public class Slime extends Enemy implements ObjectInterface {
    public Slime (String _name, Vector2 _position, float _speed, int _maxHealth) {
        super(_name, _position, _speed, _maxHealth);

        maxHealth = 10;
        health = maxHealth;

    }

    public void start() {
        super.start();

        // Walk
        animationManager.CreateAnimation("/sprite/slime/slime", 0, 0, 10); // Idle Down
        animationManager.CreateAnimation("/sprite/slime/slime", 1, 1, 10); // Idle Right
        animationManager.CreateAnimation("/sprite/slime/slime", 2, 2, 10); // Idle Up
        animationManager.CreateAnimation("/sprite/slime/slime", 3, 3, 10); // Idle Left

        collider = new Collider(this, 0, 0, 16, 16, true, true);
    }

    public void ProcessAI() {
        if(moveOrders.size() < 2) {
            Move((int)(Math.random() * 32) + 32, intToDirection((int) (Math.random() * 4.5)), 1, false);
        }
    }

    public void update() throws InterruptedException {
        super.update();

        for(Collision collision : collider.collisions) {
            if(collision.entity instanceof Player) {
                ((Player) collision.entity).GetDamage(1, 1.6f, 0, entityDirection);
            }
        }

    }
}
