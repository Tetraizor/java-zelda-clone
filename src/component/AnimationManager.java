package component;

import java.awt.image.BufferedImage;
import java.util.*;
import resource.Animation;



public class AnimationManager
{
    ArrayList<Animation> animations = new ArrayList<Animation>();

    public BufferedImage currentImage;

    int currentAnimation = 0;

    int time = 0;
    int currentFrame = 0;

    public AnimationManager()
    {

    }

    public void CreateAnimation(String _name, int _startFrame, int _endFrame, int _speed)
    {
        animations.add(new Animation(_name, _startFrame, _endFrame, _speed));
    }

    public void SwitchAnimation(int _index)
    {
        if(animations.get(_index) == null)
        {
            System.out.println("Animation with index " + _index + " does not exist!");
            return;
        }

        if(currentAnimation == _index)
            return;

        currentAnimation = _index;
        currentFrame = 0;
        time = 0;

        System.out.println("Switched animation.");
    }

    public void update()
    {
        System.out.println(time);

        if (animations.get(currentAnimation) == null)
            return;

        if(time == animations.get(currentAnimation).speed)
        {
            time = 0;
        }
        else
        {
            time++;
        }

        if(time == 1)
        {
            System.out.println(currentFrame);

            currentImage = animations.get(currentAnimation).animationFrames.get(currentFrame);

            if (currentFrame == animations.get(currentAnimation).frameCount - 1)
            {
                currentFrame = 0;
            }
            else
            {
                currentFrame++;
            }
        }
    }
}
