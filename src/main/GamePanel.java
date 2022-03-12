package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import components.KeyHandler;
import entity.*;

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

    // *------ Threading ------*
    Thread gameThread;
    final int fpsCap = 60;

    // *------ Debug ------*
    public ArrayList<Entity> entityList = new ArrayList<Entity>();
    public static KeyHandler mainKeyHandler = new KeyHandler();

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.addKeyListener(mainKeyHandler);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
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

        double drawInterval = 1000000000/fpsCap;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        // Start Function
        start();

        // Start game loop
        while(gameThread != null)
        {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if(delta >= 1)
            {
                // Update every frame
                update();

                // Render screen
                repaint();

                delta--;
            }
        }
    }

    public void start()
    {

        entityList.add(new Player(100, 100, 4));
        entityList.add(new Player(200, 100, 4));
        System.out.println(entityList.size());

        ((Player)entityList.get(0)).PrintPlayerValues();
    }

    public void update()
    {
        for(Entity entity : entityList)
            entity.update();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D)g;

        for(Entity entity : entityList)
            entity.render(g2D);

        g2D.dispose();
    }
}

