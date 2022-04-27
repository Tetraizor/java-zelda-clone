package entity;

import component.Collider;
import util.Vector2;

public class Enemy extends Entity
{
    public Enemy(Vector2 pos)
    {
        position = new Vector2(pos.x, pos.y);
    }

    public void start(){
        super.start();

        // Walk
        animationManager.CreateAnimation("slime", 0, 0, 10); // Idle Down
        animationManager.CreateAnimation("slime", 1, 1, 10); // Idle Right
        animationManager.CreateAnimation("slime", 2, 2, 10); // Idle Up
        animationManager.CreateAnimation("slime", 3, 3, 10); // Idle Left

        collider = new Collider(this, 4, 7, 9, 9);
    }



}
