package util;

import main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderUtils
{
    public static void DrawSprite(Vector2 pos, BufferedImage image, Boolean checkIfOnBoundary, Graphics2D g2D)
    {
        if(checkIfOnBoundary)
            if(!GamePanel.instance.mainCamera.IsInsideBoundaries(pos, GamePanel.originalTileSize)) {
                return;
            }

        g2D.drawImage(image, (int)((pos.x - GamePanel.instance.mainCamera.position.x) * GamePanel.scaleFactor  + GamePanel.screenWidth / 2), (int)((pos.y - GamePanel.instance.mainCamera.position.y) * GamePanel.scaleFactor + GamePanel.screenHeight / 2), GamePanel.tileSize, GamePanel.tileSize, null);
    }

    public static void DrawRect(Vector2 pos, int sizeX, int sizeY, Color color, Graphics2D g2D)
    {
        g2D.setColor(color);
        g2D.drawRect((int)((pos.x - GamePanel.instance.mainCamera.position.x) * GamePanel.scaleFactor  + GamePanel.screenWidth / 2), (int)((pos.y - GamePanel.instance.mainCamera.position.y) * GamePanel.scaleFactor + GamePanel.screenHeight / 2), sizeX * GamePanel.scaleFactor, sizeY * GamePanel.scaleFactor);
    }

    public static void DrawLine(Vector2 pos1, Vector2 pos2, Color color, Graphics2D g2D)
    {
        g2D.setColor(color);

        int x1 = (int)(pos1.x - GamePanel.instance.mainCamera.position.x) * GamePanel.scaleFactor + GamePanel.screenWidth / 2;
        int x2 = (int)(pos2.x - GamePanel.instance.mainCamera.position.x) * GamePanel.scaleFactor + GamePanel.screenWidth / 2;

        int y1 = (int)(pos1.y - GamePanel.instance.mainCamera.position.y) * GamePanel.scaleFactor + GamePanel.screenHeight / 2;
        int y2 = (int)(pos2.y - GamePanel.instance.mainCamera.position.y) * GamePanel.scaleFactor + GamePanel.screenHeight / 2;

        g2D.drawLine(x1, y1, x2, y2);
    }
}
