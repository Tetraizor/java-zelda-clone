package entity;

import component.Collider;
import main.GamePanel;
import resource.Animation;
import resource.Collision;
import util.Vector2;

public class Projectile extends EntityMoving{

    Direction projectileDirection;
    public int damage;
    float invincibilityTime;
    float knockback;

    public int index;

    public Projectile(String _name, int _index, Vector2 _position, int _damage, float _invincibilityTime, float _knockback, Direction _direction) {
        super(_name, _position, 2, 999);

        entityDirection = _direction;
        projectileDirection = _direction;
        invincibilityTime = _invincibilityTime;
        knockback = _knockback;
        damage = _damage;
        index = _index;

        Move(2000, entityDirection, 1, false);

        if(index == 0) {
            animationManager.CreateAnimation("/sprite/projectile/projectile_projectile", 0, 1); // DOWN
            animationManager.CreateAnimation("/sprite/projectile/projectile_projectile", 2, 1); // LEFT
            animationManager.CreateAnimation("/sprite/projectile/projectile_projectile", 1, 1);
            animationManager.CreateAnimation("/sprite/projectile/projectile_projectile", 3, 1);
        }
        else if(index == 1)
        {
            animationManager.CreateAnimation("/sprite/projectile/projectile_projectile", 4, 7, 1); // DOWN
            animationManager.CreateAnimation("/sprite/projectile/projectile_projectile", 4, 7, 1); // DOWN
            animationManager.CreateAnimation("/sprite/projectile/projectile_projectile", 4, 7, 1); // DOWN
            animationManager.CreateAnimation("/sprite/projectile/projectile_projectile", 4, 7, 1); // DOWN
        }

        animationManager.SwitchAnimation(entityDirection.getValue());

    }

    public void start() {
        super.start();
        collider = new Collider(this, 0, 0, 16, 16, false, false);

        collider.mask.add("Player");
        collider.mask.add("PlayerTool");



    }

    public void update() throws InterruptedException {
        super.update();

        for (Collision collision : collider.collisions) {
            if(collision.entity instanceof Enemy) {
                ((Enemy)collision.entity).GetDamage(damage, invincibilityTime, knockback, entityDirection);
                this.SetActive(false);
            }else if(collision.tile != null) {
                if(collision.tile.solid)
                    SetActive(false);
            }

        }
    }
}
