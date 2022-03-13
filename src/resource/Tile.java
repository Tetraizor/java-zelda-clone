package resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tile
{
    public BufferedImage image;
    public Boolean solid = false;

    public Tile(int index)
    {
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream("/sprite/environment/tile_" + index + ".png"));
        }
        catch (IOException e)
        {
            System.out.println("Tile not found. Creating grass tile instead.");
            try
            {
                image = ImageIO.read(getClass().getResourceAsStream("/sprite/environment/tile_" + 0 + ".png"));
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
