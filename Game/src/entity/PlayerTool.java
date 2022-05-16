package entity;

import component.Collider;
import resource.Animation;
import resource.Collision;
import util.Vector2;

public class PlayerTool extends Entity {

    public float invincibilityTime;
    public int damage;
    public Player player;

    public PlayerTool (String _name, Vector2 _position, Player _player) {
        super(_name, _position);
        player = _player;
        start();
    }

    public void start() {
        super.start();

        animationManager.CreateAnimation("/sprite/tool/tool_tool", 0, 0, 1);
        animationManager.CreateAnimation("/sprite/tool/tool_tool", 1, 1, 1);
        animationManager.CreateAnimation("/sprite/tool/tool_tool", 2, 2, 1);
        animationManager.CreateAnimation("/sprite/tool/tool_tool", 3, 3, 1);
        collider = new Collider(this, 0, 0, 16, 16, false, false);
    }

    public void update() {
        super.update();

        for(Collision collision : collider.collisions)
        {
            if(collision.entity instanceof Enemy)
                ((Enemy)(collision.entity)).GetDamage(damage, invincibilityTime + .2f, player.entityDirection);
        }
    }

    public void Hurt(int _damage, float _invincibilityTime) {
        invincibilityTime = _invincibilityTime;
        damage = _damage;
    }
}
