package entity;

import component.Collider;
import main.GamePanel;
import resource.Animation;
import resource.Collision;
import resource.Tool;
import util.Vector2;

public class PlayerTool extends EntityStationary {

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

        animationManager.CreateAnimation("/sprite/tool/tool_tool", 0,  1);
        animationManager.CreateAnimation("/sprite/tool/tool_tool", 1,  1);
        animationManager.CreateAnimation("/sprite/tool/tool_tool", 2,  1);
        animationManager.CreateAnimation("/sprite/tool/tool_tool", 3,  1);

        animationManager.CreateAnimation("/sprite/tool/tool_tool", 4,  1);
        animationManager.CreateAnimation("/sprite/tool/tool_tool", 5,  1);
        animationManager.CreateAnimation("/sprite/tool/tool_tool", 6,  1);
        animationManager.CreateAnimation("/sprite/tool/tool_tool", 7,  1);

        collider = new Collider(this, 0, 0, 16, 16, false, false);
    }

    public void update() {
        super.update();

        for(Collision collision : collider.collisions)
        {
            if(collision.entity instanceof Enemy) {
                ((Enemy) (collision.entity)).GetDamage(damage, invincibilityTime + .2f, player.entityDirection);
                System.out.println(player.entityDirection);
            }
        }
    }

    public void Hurt(Tool _tool) {
        invincibilityTime = _tool.swingTime;
        damage = _tool.damage;

        if(_tool.isProjectileBased) {
            System.out.println("Direction: " + player.entityDirection);
            GamePanel.instance.CreateObject(new Projectile("Arrow", new Vector2(position.x, position.y), player.entityDirection));
        }
    }
}
