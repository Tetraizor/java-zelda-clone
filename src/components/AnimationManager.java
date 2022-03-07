package components;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AnimationManager
{
    BufferedImage[][] animations;
    BufferedImage[] frames;

    public AnimationManager(String _directory, int _frameCount)
    {
        BufferedImage[] frames = new BufferedImage[_frameCount];

        try
        {
            for(int i = 0; i < _frameCount; i++)
            {
                frames[i] = ImageIO.read(getClass().getResourceAsStream(_directory + i + ".png"));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}
