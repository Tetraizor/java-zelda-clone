package entity;

import components.AnimationManager;
import components.KeyHandler;
import main.Main;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.IOException;

public class Player extends Entity
{
    public Player(int _x, int _y, int _speed)
    {
        SetEntityProperties(_x, _y, _speed);
    }

    public void start()
    {
        super.start();
        animationManager = new AnimationManager("/player/player_", 16);

        // TODO: Setup animations for player only.

    }

    public void update()
    {
        super.update();
        InputHandler();
    }

    void InputHandler()
    {
        if(playerKeyHandler.keyList.get(0).isButtonDown)
            y -= speed;
        if(playerKeyHandler.keyList.get(1).isButtonDown)
            y += speed;
        if(playerKeyHandler.keyList.get(2).isButtonDown)
            x += speed;
        if(playerKeyHandler.keyList.get(3).isButtonDown)
            x -= speed;
    }
}
