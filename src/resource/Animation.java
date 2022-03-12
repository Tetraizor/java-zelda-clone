package resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Animation
{
    public int startFrame;
    public int endFrame;
    public int speed;
    public int frameCount;

    public ArrayList<BufferedImage> animationFrames = new ArrayList<BufferedImage>();

    public Animation(String name, int _startFrame, int _endFrame, int _speed)
    {
        this.startFrame = _startFrame;
        this.endFrame = _endFrame;
        this.speed = _speed;

        try
        {
            for(int i = _startFrame; i <= _endFrame; i++)
            {
                animationFrames.add(ImageIO.read(getClass().getResourceAsStream("/sprite/player/player_" + i + ".png")));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        frameCount = (_endFrame - _startFrame) + 1;
    }




}
