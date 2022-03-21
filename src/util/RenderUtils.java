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
            if(!GamePanel.instance.mainCamera.IsInsideBoundaries(x, y, GamePanel.originalTileSize))
                return;

        g2D.drawImage(image, ((x - GamePanel.instance.mainCamera.x) * GamePanel.scaleFactor  + GamePanel.screenWidth / 2), ((y - GamePanel.instance.mainCamera.y) * GamePanel.scaleFactor + GamePanel.screenHeight / 2), GamePanel.tileSize, GamePanel.tileSize, null);
    }

    public static void DrawRect(int x, int y, int sizeX, int sizeY, Color color, Graphics2D g2D)
    {
        g2D.setColor(color);
        g2D.drawRect(((x - GamePanel.instance.mainCamera.x) * GamePanel.scaleFactor  + GamePanel.screenWidth / 2), ((y - GamePanel.instance.mainCamera.y) * GamePanel.scaleFactor + GamePanel.screenHeight / 2), sizeX * GamePanel.scaleFactor, sizeY * GamePanel.scaleFactor);
    }
}
