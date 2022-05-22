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
    Entity sender;

    public int index;

    public Projectile(String _name, int _index, Vector2 _position, int _damage, float _invincibilityTime, float _knockback, Direction _direction, Entity _sender) {
        super(_name, _position, 2, 999);

        entityDirection = _direction;
        projectileDirection = _direction;
        invincibilityTime = _invincibilityTime;
        knockback = _knockback;
        damage = _damage;
        index = _index;
        sender = _sender;

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

        else if(index == 2)
        {
            animationManager.CreateAnimation("/sprite/projectile/projectile_projectile", 8,  1); // DOWN
            animationManager.CreateAnimation("/sprite/projectile/projectile_projectile", 8,  1); // DOWN
            animationManager.CreateAnimation("/sprite/projectile/projectile_projectile", 8,  1); // DOWN
            animationManager.CreateAnimation("/sprite/projectile/projectile_projectile", 8,  1); // DOWN
        }

        animationManager.SwitchAnimation(entityDirection.getValue());

        collider = new Collider(this, 0, 0, 16, 16, false, false);

        if(sender == GamePanel.player) {
            collider.mask.add("PlayerTool");
            collider.mask.add("Player");
        }else
        {
            collider.mask.add(sender.name);
        }

    }

    public void start() {
        super.start();


    }

    public void update() throws InterruptedException {
        super.update();

        for (Collision collision : collider.collisions) {
            if(collision.entity instanceof EntityMoving) {
                if(collision.entity != sender) {
                    this.SetActive(false);
                    HurtEntity((EntityMoving) collision.entity);
                }
            }else if(collision.tile != null) {
                if(collision.tile.solid)
                    SetActive(false);
            }
        }
    }

    public void HurtEntity(EntityMoving entity) {
        entity.GetDamage(damage, invincibilityTime, knockback, entityDirection);
        if(index == 1) // Fire
            entity.SetOnFire(4);

        if(index == 2) // Ice
            entity.Freeze(10);
    }
}
