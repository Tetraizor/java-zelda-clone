package main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import component.*;

import entity.*;
import resource.Tile;
import util.GameSetupManager;
import util.ImageUtils;
import util.Vector2;

public class GamePanel extends JPanel implements Runnable
{
    // *------ Screen Settings ------*
    public final static int originalTileSize = 16;

    public final static int scaleFactor = 3; // Multiply each pixel to make it bigger.

    public final static int tileSize = originalTileSize * scaleFactor;

    public final static int column = 24;
    public final static int row = 14;

    public final static int screenHeight = row * tileSize;
    public final static int screenWidth = column * tileSize;

    public static GamePanel instance;

    public int mapIndex;

    // *------ Threading ------*
    Thread gameThread;
    final int fpsCap = 60;

    // *------ Debug ------*
    public ArrayList<Entity> entityList = new ArrayList<Entity>();
    public static KeyHandler mainKeyHandler = new KeyHandler();
    public TileManager tileManager = new TileManager(mapIndex, column, row);
    public Camera mainCamera = new Camera(16, 16);

    public GamePanel(int mapIndex)
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.addKeyListener(mainKeyHandler);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.mapIndex = mapIndex;
        instance = this;
    }

    // Start Game
    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run()
    {
        System.out.println("*------Game Started------*");

        boolean isRunning = true;
        boolean render = false;

        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime = 0;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        // Start Function
        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Start game loop
        while(isRunning)
        {
            render = false;

            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while(unprocessedTime >= 1.0/60.0)
            {
                unprocessedTime -= 1.0/60.0;
                render = true;

                // TODO: Update game
                update();

                if(frameTime >= 1.0)
                {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;

                    System.out.println("FPS: " + fps);
                }
            }

            if(render)
            {
                // TODO: Render
                repaint();
                frames++;
            }
            else
            {
                try
                {
                    Thread.sleep(1);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }

        dispose();
    }

    private void dispose()
    {

    }

    public void start() throws IOException {
        GameSetupManager.SetupImages(Color.red);

        entityList.add(new Player("Player", new Vector2(12 * originalTileSize, 7 * originalTileSize), 1));
        entityList.add(new Enemy(new Vector2(12 * originalTileSize, 5 * originalTileSize)));
        entityList.add(new Enemy(new Vector2(13 * originalTileSize, 5 * originalTileSize)));
        entityList.add(new Enemy(new Vector2(14 * originalTileSize, 5 * originalTileSize)));
        entityList.add(new Enemy(new Vector2(15 * originalTileSize, 5 * originalTileSize)));
        entityList.add(new Enemy(new Vector2(16 * originalTileSize, 5 * originalTileSize)));
        mainCamera.SetTarget(entityList.get(0));

    }

    public void update()
    {
        mainCamera.update();
        for(Entity entity : entityList)
            entity.update();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D)g;

        tileManager.draw(g2D);

        for(Entity entity : entityList)
            entity.render(g2D);

        g2D.dispose();
    }
}

