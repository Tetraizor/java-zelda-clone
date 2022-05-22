package ui;

import main.GamePanel;
import util.ImageUtils;
import util.RenderUtils;

import java.awt.*;

public class UI
{
    GamePanel gp;
    Font pixelArtFont;

    public int health;
    public String name;
    public int arrowCount;
    public int magic;


    public UI(GamePanel gp) {
        this.gp = gp;
        pixelArtFont = new Font("PixelArtFont", Font.PLAIN, 48);
    }

    public void draw(Graphics2D g2) {
        g2.setFont(pixelArtFont);
        g2.setColor(Color.black);
        g2.fillRect(0, 0, GamePanel.screenWidth, GamePanel.originalTileSize * 4 * GamePanel.scaleFactor);
        g2.setColor(Color.white);

        g2.drawString("Player Name", 32, 48);
        g2.drawString("Health: " + health, 32, 48 + 48);
        g2.drawString("Tool: " + GamePanel.player.currentTool.name, 250, 48);
        g2.drawString("Use Space key to attack, R key to change tools. Use WASD keys to move.", 32, 96 + 48);

        if(GamePanel.player.isDead) {
            g2.setColor(Color.black);
            g2.fillRect(0, 0, GamePanel.screenWidth, GamePanel.screenHeight);
            GamePanel.player.render(g2);
        }
    }

    public void update() {
        health = GamePanel.player.health;
    }


}
