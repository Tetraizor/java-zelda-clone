package resource;

import entity.Entity;
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
    public Entity.Direction direction;
    public CollisionType collisionType;
    public Entity entity;
    public Tile tile;

    public Collision(Vector2 _pos, Entity.Direction _direction, Tile _tile)
    {
        position = _pos;
        direction = _direction;
        tile = _tile;
        collisionType = CollisionType.TILE;
    }

    public Collision(Vector2 _pos, Entity.Direction _direction, Entity _entity)
    {
        position = _pos;
        direction = _direction;
        entity = _entity;
        collisionType = CollisionType.ENTITY;
    }
}
