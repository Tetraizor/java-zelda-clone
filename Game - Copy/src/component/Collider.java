package component;

import entity.Entity;
import entity.EntityMoving;
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

    Vector2[] posCheck = {new Vector2(0, 0), new Vector2(0, 0), new Vector2(0, 0), new Vector2(0, 0)};
    public ArrayList<String> mask;

    boolean isSolid;
    boolean isDirectional;

    public ArrayList<Collision> collisions = new ArrayList<Collision>();
    public static ArrayList<Collider> colliderList = new ArrayList<Collider>();

    public Collider(Entity _parent, int _xOffset, int _yOffset, int _sizeX, int _sizeY, boolean _isSolid, boolean _isDirectional)
    {
        colliderList.add(this);
        parent = _parent;
        xOffset = _xOffset;
        yOffset = _yOffset;
        sizeX = _sizeX;
        sizeY = _sizeY;
        isSolid = _isSolid;
        isDirectional = _isDirectional;

        mask = new ArrayList<String>();
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

        if(isDirectional) {
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
                AddCollision(posCheck[0], parent.entityDirection, tile1, tile1.solid);

            if(tile2.solid)
                AddCollision(posCheck[1], parent.entityDirection, tile2, tile2.solid);

            for(Entity entity : GamePanel.instance.entityList) {
                if(entity.IsActive()){
                    if(entity != this.parent) {
                        if (entity.collider.CheckIfInsideBoundBox(posCheck[0]))
                            AddCollision(posCheck[0], parent.entityDirection, entity, entity.collider.isSolid);
                        if (entity.collider.CheckIfInsideBoundBox(posCheck[1]))
                            AddCollision(posCheck[1], parent.entityDirection, entity, entity.collider.isSolid);
                    }
                }
            }
        }
        else
        {
            posCheck[0] = new Vector2(parent.position.x + xOffset, parent.position.y + yOffset);
            posCheck[1] = new Vector2(parent.position.x + xOffset + sizeX, parent.position.y + yOffset);
            posCheck[2] = new Vector2(parent.position.x + xOffset, parent.position.y + yOffset + sizeY);
            posCheck[3] = new Vector2(parent.position.x + xOffset + sizeX, parent.position.y + yOffset + sizeY);

            Tile tile1 = GamePanel.instance.tileManager.WorldCoordinateToTile(posCheck[0]);
            Tile tile2 = GamePanel.instance.tileManager.WorldCoordinateToTile(posCheck[1]);
            Tile tile3 = GamePanel.instance.tileManager.WorldCoordinateToTile(posCheck[2]);
            Tile tile4 = GamePanel.instance.tileManager.WorldCoordinateToTile(posCheck[3]);

            if(tile1.solid)
                AddCollision(posCheck[0], parent.entityDirection, tile1, tile1.solid);

            if(tile2.solid)
                AddCollision(posCheck[1], parent.entityDirection, tile2, tile2.solid);

            if(tile1.solid)
                AddCollision(posCheck[2], parent.entityDirection, tile3, tile3.solid);

            if(tile2.solid)
                AddCollision(posCheck[3], parent.entityDirection, tile4, tile4.solid);

            for(Entity entity : GamePanel.instance.entityList) {
                if(entity.IsActive()){
                    if(entity != this.parent) {
                        if (entity.collider.CheckIfInsideBoundBox(posCheck[0]))
                            AddCollision(posCheck[0], parent.entityDirection, entity, entity.collider.isSolid);
                        if (entity.collider.CheckIfInsideBoundBox(posCheck[1]))
                            AddCollision(posCheck[1], parent.entityDirection, entity, entity.collider.isSolid);
                        if (entity.collider.CheckIfInsideBoundBox(posCheck[2]))
                            AddCollision(posCheck[2], parent.entityDirection, entity, entity.collider.isSolid);
                        if (entity.collider.CheckIfInsideBoundBox(posCheck[3]))
                            AddCollision(posCheck[3], parent.entityDirection, entity, entity.collider.isSolid);
                    }
                }
            }
        }


        return collisions;
    }

    // Add collision - TILE
    public boolean AddCollision(Vector2 _position, EntityMoving.Direction _direction, Tile _tile, boolean _isSolid) {
        if(collisions.size() != 0) {
            for (Collision collision : collisions)
                if (collision.collisionType == Collision.CollisionType.TILE)
                    if (collision.tile != _tile) {
                        collisions.add(new Collision(_position, _direction, _tile, _isSolid));
                        return true;
                    }
        }
        else {
            collisions.add(new Collision(_position, _direction, _tile, _isSolid));
            return true;
        }
        return false;
    }
    // Add collision - ENTITY
    public boolean AddCollision(Vector2 _position, EntityMoving.Direction _direction, Entity _entity, boolean _isSolid)
    {
        boolean isMasked = false;

        for (String maskName : mask)
            if(_entity.name.equalsIgnoreCase(maskName))
                isMasked = true;

        if(!isMasked)
            if(collisions.size() != 0)
            {
                for(Collision collision : collisions)
                    if(collision.collisionType == Collision.CollisionType.ENTITY)
                    {
                        if(collision.entity != _entity) {
                            collisions.add(new Collision(_position, _direction, _entity, _isSolid));
                            return true;
                        }
                    }
            }
            else
            {
                collisions.add(new Collision(_position, _direction, _entity, _isSolid));
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
        // g2D.setColor(Color.red);

        /*
        if(isDirectional)
            RenderUtils.DrawRect(new Vector2(parent.position.x + xOffset, parent.position.y + yOffset), sizeX, sizeY, Color.green, g2D);
        else
            RenderUtils.DrawRect(new Vector2(parent.position.x + xOffset, parent.position.y + yOffset), sizeX, sizeY, Color.BLUE, g2D);


        RenderUtils.DrawLine(posCheck[0], posCheck[1], Color.yellow, g2D);
        */
    }
}
