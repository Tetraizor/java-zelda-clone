package entity;

import component.AnimationManager;
import component.Collider;
import component.KeyHandler;
import main.GamePanel;
import main.Main;
import resource.Collision;
import util.RenderUtils;
import util.Vector2;

import java.awt.*;

public class Entity implements ObjectInterface
{
    public String name;
    public Vector2 position;

    public int activeChunkX;
    public int activeChunkY;

    public boolean isRendering;
    private boolean isActive;
    public boolean isDead;

    public boolean isCreated;

    public boolean IsActive() {
        return isActive;
    }

    public void SetActive(boolean state) {
        isActive = state;
    }

    public enum Direction {
        UP(2),
        LEFT(3),
        RIGHT(1),
        DOWN(0);

        private final int value;

        private Direction(int _value)
        {
            value = _value;
        }

        public int getValue()
        {
            return value;
        }
    }

    public Direction entityDirection = Direction.DOWN;

    public Direction intToDirection(int i) {
        switch (i) {
            case 0:
                return Direction.DOWN;

            case 1:
                return Direction.RIGHT;

            case 2:
                return Direction.UP;

            default: // LEFT
                return Direction.LEFT;
        }
    }

    GamePanel currentGP = Main.currentGamePanel;
    AnimationManager animationManager;
    public Collider collider;

    public Entity(String name, Vector2 _position)
    {
        start();

        entityDirection = Direction.DOWN;

        this.name = name;
        position = _position;
        isActive = true;
    }

    public void CalculateActiveChunk() {
        activeChunkX = (int)(position.x / (GamePanel.column * GamePanel.originalTileSize));
        activeChunkY = (int)(position.y / (GamePanel.row * GamePanel.originalTileSize));
    }

    public boolean IsInActiveChunk() {
        CalculateActiveChunk();

        return (activeChunkX == GamePanel.instance.activeChunkX && activeChunkY == GamePanel.instance.activeChunkY);
    }

    public Entity()
    {
        this("New Entity", new Vector2(32, 32));
        start();
    }

    public void KillEntity(int _delay) throws InterruptedException {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        KillEntity();
                    }
                },
                _delay
        );
    }

    public void KillEntity() {
        if(this.IsActive()) {
            this.SetActive(false);
        }
    }

    public void start()
    {
        animationManager = new AnimationManager();
    }

    public void update() throws InterruptedException {
        animationManager.update();
    }

    public void render(Graphics2D g2D)
    {
        if(isActive) {
            RenderUtils.DrawSprite(new Vector2(position.x, position.y), animationManager.currentImage, true, g2D);
            // RenderUtils.DrawRect(position, GamePanel.originalTileSize, GamePanel.originalTileSize, Color.red, g2D);
            if(collider != null) collider.render(g2D);
        }

    }
}
