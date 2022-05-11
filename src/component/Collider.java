package component;

import entity.Entity;
import main.GamePanel;
import resource.Collision;
import resource.Tile;
import util.RenderUtils;
import util.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class Collider
{
    Entity parent;

    int xOffset = 0, yOffset = 0;
    int sizeX = 0, sizeY = 0;

    Vector2[] posCheck = {new Vector2(0, 0), new Vector2(0, 0)};

    public ArrayList<Collision> collisions = new ArrayList<Collision>();
    public static ArrayList<Collider> colliderList = new ArrayList<Collider>();

    public Collider(Entity _parent, int _xOffset, int _yOffset, int _sizeX, int _sizeY)
    {
        colliderList.add(this);
        parent = _parent;
        xOffset = _xOffset;
        yOffset = _yOffset;
        sizeX = _sizeX;
        sizeY = _sizeY;
    }

    public Boolean IsColliding()
    {
        return true;
    }

    public Boolean OnCollisionEnter()
    {
        return true;
    }

    public ArrayList<Collision> CheckForCollisions()
    {
        collisions = new ArrayList<Collision>();
        switch (parent.entityDirection) {
            case DOWN -> {
                posCheck[0] = new Vector2(parent.position.x + xOffset + 2, parent.position.y + yOffset + sizeY);
                posCheck[1] = new Vector2(parent.position.x + xOffset + sizeX - 2, parent.position.y + yOffset + sizeY);
            }
            case UP -> {
                posCheck[0] = new Vector2(parent.position.x + xOffset + 2, parent.position.y + yOffset);
                posCheck[1] = new Vector2(parent.position.x + xOffset + sizeX - 2, parent.position.y + yOffset);
            }
            case LEFT -> {
                posCheck[0] = new Vector2(parent.position.x + xOffset, parent.position.y + yOffset + 2);
                posCheck[1] = new Vector2(parent.position.x + xOffset, parent.position.y + yOffset + sizeY - 2);

            }
            case RIGHT -> {
                posCheck[0] = new Vector2(parent.position.x + xOffset + sizeX, parent.position.y + yOffset + 2);
                posCheck[1] = new Vector2(parent.position.x + xOffset + sizeX, parent.position.y + yOffset + sizeY - 2);
            }
        }
        Tile tile1 = GamePanel.instance.tileManager.WorldCoordinateToTile(posCheck[0]);
        Tile tile2 = GamePanel.instance.tileManager.WorldCoordinateToTile(posCheck[1]);

        if(tile1.solid)
            AddCollision(posCheck[0], parent.entityDirection, tile1);

        if(tile2.solid)
            AddCollision(posCheck[1], parent.entityDirection, tile2);

        for(Entity entity : GamePanel.instance.entityList) {
            if(entity != this.parent) {
                if (entity.collider.CheckIfInsideBoundBox(posCheck[0]))
                    AddCollision(posCheck[0], parent.entityDirection, entity);
                if (entity.collider.CheckIfInsideBoundBox(posCheck[1]))
                    AddCollision(posCheck[1], parent.entityDirection, entity);
            }
        }

        return collisions;
    }

    // Add collision - TILE
    public boolean AddCollision(Vector2 _position, Entity.Direction _direction, Tile _tile)
    {
        if(collisions.size() != 0) {
            for (Collision collision : collisions)
                if (collision.collisionType == Collision.CollisionType.TILE)
                    if (collision.tile != _tile) {
                        collisions.add(new Collision(_position, _direction, _tile));
                        return true;
                    }
        }
        else {
            collisions.add(new Collision(_position, _direction, _tile));
            return true;
        }
        return false;
    }
    // Add collision - ENTITY
    public boolean AddCollision(Vector2 _position, Entity.Direction _direction, Entity _entity)
    {
        if(collisions.size() != 0) {
            for(Collision collision : collisions)
                if(collision.collisionType == Collision.CollisionType.ENTITY)
                    if(collision.entity != _entity) {
                        collisions.add(new Collision(_position, _direction, _entity));
                        return true;
                    }
        }
        else
        {
            collisions.add(new Collision(_position, _direction, _entity));
            return true;
        }
        return false;
    }

    public boolean CheckIfInsideBoundBox(Vector2 _pos)
    {
        if(_pos.x >= parent.position.x + xOffset && _pos.x <= parent.position.x + xOffset + sizeX)
            if(_pos.y >= parent.position.y + yOffset && _pos.y <= parent.position.y + yOffset + sizeY)
                return true;
        return false;
    }

    public void render(Graphics2D g2D)
    {
        g2D.setColor(Color.red);

        RenderUtils.DrawRect(new Vector2(parent.position.x + xOffset, parent.position.y + yOffset), sizeX, sizeY, Color.green, g2D);
        RenderUtils.DrawLine(posCheck[0], posCheck[1], Color.yellow, g2D);
    }
}
