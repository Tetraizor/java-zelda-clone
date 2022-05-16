package entity;

import component.AnimationManager;
import component.Collider;
import util.Vector2;

public class ToolEntity extends Entity {
    public ToolEntity(String _name, Vector2 _position) {
        super(_name, _position);

        collider = new Collider(this, 0, 0, 16, 16, false, false);

        animationManager.CreateAnimation("/sprite/tool/tool_tool", 0, 0, 1);

    }

    public void start() {
        super.start();
    }

    public void update() {
        super.update();
    }
}
