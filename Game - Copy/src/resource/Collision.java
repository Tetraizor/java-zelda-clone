package resource;

import entity.Entity;
import entity.EntityMoving;
import util.Vector2;

public class Collision
{
    // Collision enum
    public enum CollisionType {
        TILE(0),
        ENTITY(1);

        private final int value;

        private CollisionType(int _value)
        {
            value = _value;
        }

        public int getValue()
        {
            return value;
        }
    }

    // Values
    public Vector2 position;
    public EntityMoving.Direction direction;
    public CollisionType collisionType;
    public Entity entity;
    public Tile tile;
    public boolean isSolid;

    public Collision(Vector2 _pos, EntityMoving.Direction _direction, Tile _tile, boolean _isSolid)
    {
        position = _pos;
        direction = _direction;
        tile = _tile;
        collisionType = CollisionType.TILE;
        isSolid = _isSolid;
    }

    public Collision(Vector2 _pos, EntityMoving.Direction _direction, Entity _entity, boolean _isSolid)
    {
        position = _pos;
        direction = _direction;
        entity = _entity;
        collisionType = CollisionType.ENTITY;
        isSolid = _isSolid;
    }
}
