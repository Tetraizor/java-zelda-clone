package entity;

import component.Collider;
import resource.Animation;
import resource.Collision;
import util.Vector2;

public class Projectile extends EntityMoving{

    Direction projectileDirection;

    public Projectile(String _name, Vector2 _position, Direction _direction) {
        super(_name, _position, 2, 999);

        entityDirection = _direction;
        projectileDirection = _direction;
        System.out.println(entityDirection);

        Move(2000, entityDirection, 1, false);

        animationManager.SwitchAnimation(entityDirection.getValue());

    }

    public void start() {
        super.start();
        collider = new Collider(this, 0, 0, 16, 16, false, false);

        collider.mask.add("Player");
        collider.mask.add("PlayerTool");

        animationManager.CreateAnimation("/sprite/tool/tool_tool", 0, 1); // DOWN
        animationManager.CreateAnimation("/sprite/tool/tool_tool", 2, 1); // LEFT
        animationManager.CreateAnimation("/sprite/tool/tool_tool", 1, 1);
        animationManager.CreateAnimation("/sprite/tool/tool_tool", 3, 1);
    }

    public void update() {
        super.update();

        for (Collision collision : collider.collisions) {
            if(collision.entity instanceof Enemy) {
                ((Enemy)collision.entity).GetDamage(4, .4f, entityDirection);
                this.SetActive(false);
            }else if(collision.tile != null) {
                if(collision.tile.solid)
                    SetActive(false);
            }

        }
    }
}
