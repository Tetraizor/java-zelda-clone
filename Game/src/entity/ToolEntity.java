package entity;

import component.AnimationManager;
import component.Collider;
import main.GamePanel;
import resource.Collision;
import util.Vector2;

public class ToolEntity extends Entity {

    boolean isTaken;
    int toolIndex;

    public ToolEntity(String _name, Vector2 _position, int _toolIndex) {
        super(_name, _position);

        collider = new Collider(this, 0, 0, 16, 16, false, false);
        toolIndex = _toolIndex;
        animationManager.CreateAnimation("/sprite/tool/tool_tool", _toolIndex * 4 + 1, 1);
        isTaken = false;
    }

    public void start() {
        super.start();
    }

    public void update() throws InterruptedException {
        super.update();

        collider.CheckForCollisions();

        for(Collision collision : collider.collisions) {
            if(collision.entity instanceof Player) {
                if(!isTaken) {

                    ((Player) collision.entity).Move(64, Direction.DOWN, 0, true);
                    ((Player) collision.entity).animationManager.SwitchAnimation(12);
                    position.x = ((Player) collision.entity).position.x;
                    position.y = ((Player) collision.entity).position.y - GamePanel.originalTileSize;
                    ((Player) collision.entity).toolList.get(toolIndex).isEnabled = true;
                    GamePanel.instance.playSound(8);
                    KillEntity(1000);
                    GamePanel.instance.pauseMusic(1300);
                    System.out.println("AAA");
                    isTaken = true;
                }

            }

        }
    }
}
