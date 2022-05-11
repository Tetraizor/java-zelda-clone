package resource;

import main.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tile
{
    public BufferedImage image;
    public Boolean solid = false;
    public String name;

    public Tile(String _name, int _index, Boolean _isSolid)
    {
        this.name = _name;
        this.solid = _isSolid;

        try
        {
            System.out.print(_index);
            // image = ImageIO.read(getClass().getResourceAsStream("sprite/environment/tile_" + _index + ".png"));
            image = ImageIO.read(Main.class.getResourceAsStream("/sprite/environment/tile_" + _index + ".png"));
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
