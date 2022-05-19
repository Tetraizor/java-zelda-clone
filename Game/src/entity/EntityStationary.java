package entity;

import util.Vector2;

public class EntityStationary extends Entity {

    public EntityStationary (String _name, Vector2 _position) {
        super(_name, _position);
    }

    public void start() {
        super.start();

    }

    public void update() throws InterruptedException {
        super.update();

        collider.CheckForCollisions();
    }
}
