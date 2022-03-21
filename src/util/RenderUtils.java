package util;

import main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderUtils
{
    public static void DrawSprite(int x, int y, BufferedImage image, Boolean checkIfOnBoundary, Graphics2D g2D)
    {
        if(checkIfOnBoundary)
            if(!GamePanel.instance.mainCamera.IsInsideBoundaries(x, y, GamePanel.tileSize))
                return;

        g2D.drawImage(image, x - GamePanel.instance.mainCamera.x  + GamePanel.screenWidth / 2, y - GamePanel.instance.mainCamera.y + GamePanel.screenHeight / 2, GamePanel.tileSize, GamePanel.tileSize, null);
    }
}
