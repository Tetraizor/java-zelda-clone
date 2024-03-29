package main;

import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import component.*;

import entity.*;
import resource.Sound;
import resource.Tile;
import ui.UI;
import util.GameSetupManager;
import util.ImageUtils;
import util.Vector2;

public class GamePanel extends JPanel implements Runnable
{
    // *------ Screen Settings ------*
    public final static int originalTileSize = 16;

    public final static int scaleFactor = 3; // Multiply each pixel to make it bigger.

    public final static int tileSize = originalTileSize * scaleFactor;

    public final static int column = 22;
    public final static int row = 14;

    public final static int screenHeight = (row + 4) * tileSize;
    public final static int screenWidth = column * tileSize;

    public static float volume = 1;

    public static GamePanel instance;

    public int mapIndex;
    public int activeChunkX;
    public int activeChunkY;

    // *------ Threading ------*
    Thread gameThread;
    final int fpsCap = 60;

    // *------ Debug ------*
    public ArrayList<Entity> entityList = new ArrayList<Entity>();
    public ArrayList<Entity> objectCreatorList = new ArrayList<Entity>();
    public static KeyHandler mainKeyHandler = new KeyHandler();
    public TileManager tileManager = new TileManager(mapIndex, 176, 104);
    public Camera mainCamera = new Camera(0, 0);
    public UI ui;
    public static Player player;

    public static Sound sound = new Sound();
    public static Sound music = new Sound();

    public GamePanel(int mapIndex) throws IOException {
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
                try {
                    update();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(frameTime >= 1.0)
                {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
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

        player = (Player)CreateObject(new Player("Player", new Vector2(12 * originalTileSize, 7 * originalTileSize), 1, 6));
        CreateObject(new Slime("Slime", new Vector2(12 * originalTileSize, 5 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(14 * originalTileSize, 5 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(16 * originalTileSize, 5 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(30 * originalTileSize, 35 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(35 * originalTileSize, 35 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(35 * originalTileSize, 39 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(30 * originalTileSize, 39 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(29 * originalTileSize, 17 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(36 * originalTileSize, 17 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(36 * originalTileSize, 25 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(29 * originalTileSize, 25 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(49 * originalTileSize, 18 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(49 * originalTileSize, 23 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(29 * originalTileSize, 10 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(36 * originalTileSize, 10 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(47 * originalTileSize, 7 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(9 * originalTileSize, 32 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(11 * originalTileSize, 34 * originalTileSize), (float)(Math.random() * .3 + .2), 1));
        CreateObject(new Slime("Slime", new Vector2(13 * originalTileSize, 36 * originalTileSize), (float)(Math.random() * .3 + .2), 1));

        CreateObject(new ToolEntity("Sword", new Vector2(5 * originalTileSize, 8 * originalTileSize), 0));
        CreateObject(new ToolEntity("Bow", new Vector2(60 * originalTileSize, 3 * originalTileSize), 1));
        CreateObject(new ToolEntity("Fire Rod", new Vector2(62 * originalTileSize, 21 * originalTileSize), 2));
        CreateObject(new ToolEntity("Ice Rod", new Vector2(32 * originalTileSize, 37 * originalTileSize), 4));

        UpdateCreatedObjects();

        mainCamera.SetTargetChunk(0, 0);

        // UI
        ui = new UI(this);

        // Audio Manager
        setVolume(-30);
        playMusic(0);

    }

    public void UpdateCreatedObjects() {
        for(Entity entity : objectCreatorList) {
            if(!entity.isCreated) {
                entity.isCreated = true;
                entityList.add(entity);
            }

        }
    }

    public void update() throws InterruptedException {

        UpdateCreatedObjects();
        player.CalculateActiveChunk();
        activeChunkX = player.activeChunkX;
        activeChunkY = player.activeChunkY;

        for(Entity entity : entityList)
            if(entity.IsActive()) {
                if(entity instanceof Enemy || entity instanceof Decor) {
                    if(entity.IsInActiveChunk()) {
                        entity.update();
                    }
                }
                else
                    entity.update();

            }

        mainCamera.SetTargetChunk(activeChunkX, activeChunkY);

        mainCamera.update();

        ui.update();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D)g;

        tileManager.draw(g2D);

        for(int i = entityList.size() - 1; i >= 0; i--)
            if(entityList.get(i).IsActive())
                entityList.get(i).render(g2D);

        if(ui != null)
            ui.draw(g2D);

        g2D.dispose();
    }

    public void playMusic(int i) {


        music.setFile(i, volume);
        music.loop();
        music.play();


    }

    public void stopMusic() {
        music.stop();
    }

    public void playSound(int i) {
        sound.setFile(i, volume);
        sound.play();
    }

    public Entity CreateObject(Entity object) {
        objectCreatorList.add(object);
        return objectCreatorList.get(objectCreatorList.size() - 1);
    }

    public void setVolume(float _volume) {
        volume = _volume;
    }

    public void pauseMusic(int _delay) {
        music.pauseMusic(_delay);
    }
}

