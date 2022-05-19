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
    public float knockback;
    public Player player;

    public PlayerTool (String _name, Vector2 _position, Player _player) {
        super(_name, _position);
        player = _player;

        animationManager.SwitchAnimation(11);
        start();
    }

    public void start() {
        super.start();

        for(int i = 0; i < 16; i++)
            animationManager.CreateAnimation("/sprite/tool/tool_tool", i,  1);

        collider = new Collider(this, 0, 0, 16, 16, false, false);
    }

    public void update() throws InterruptedException {
        super.update();

        for(Collision collision : collider.collisions)
        {
            if(collision.entity instanceof Enemy) {
                ((Enemy) (collision.entity)).GetDamage(damage, invincibilityTime + .2f, knockback, player.entityDirection);
                System.out.println(player.entityDirection);
            }
        }
    }

    public void Hurt(Tool _tool) {
        invincibilityTime = _tool.swingTime;
        damage = _tool.damage;
        knockback = _tool.knockback;

        switch (_tool.index) {
            case 0: // Sword
                GamePanel.instance.playSound(3);
                break;
            case 1: // Bow
                GamePanel.instance.playSound(9);
                GamePanel.instance.CreateObject(new Projectile("Arrow", 0, new Vector2(position.x, position.y), damage, invincibilityTime, knockback, player.entityDirection));
                break;
            case 2: // Fire rod
                GamePanel.instance.playSound(2);
                GamePanel.instance.CreateObject(new Projectile("Fire", 1, new Vector2(position.x, position.y), damage, invincibilityTime, knockback, player.entityDirection));
                break;
            case 3: // Stick
                GamePanel.instance.playSound(3);
                break;
        }
    }
}
