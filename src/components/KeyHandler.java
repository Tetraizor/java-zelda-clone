package components;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class KeyHandler implements KeyListener
{
    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();

        for(KeyBehaviour key : keyList)
        {
            if(code == key.keyCode || code == key.altKeyCode)
            {
                key.isButtonDown = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int code = e.getKeyCode();

        for(KeyBehaviour key : keyList)
        {
            if(code == key.keyCode || code == key.altKeyCode)
            {
                key.isButtonDown = false;
            }
        }
    }

    public List<KeyBehaviour> keyList = new ArrayList<KeyBehaviour>()
    {
        {
            add(new KeyBehaviour("Up", KeyEvent.VK_UP, KeyEvent.VK_W));
            add(new KeyBehaviour("Down", KeyEvent.VK_DOWN, KeyEvent.VK_S));
            add(new KeyBehaviour("Right", KeyEvent.VK_RIGHT, KeyEvent.VK_D));
            add(new KeyBehaviour("Left", KeyEvent.VK_LEFT, KeyEvent.VK_A));
        }
    };

    public class KeyBehaviour
    {
        public KeyBehaviour(String name, int keyCode, int altKeyCode)
        {
            this.keyCode = keyCode;
            this.name = name;
            this.altKeyCode = altKeyCode;
        }

        public KeyBehaviour(String name, int keyCode)
        {
            this.name = name;
            this.keyCode = keyCode;
            this.altKeyCode = -1;
        }

        public boolean isButtonDown;

        public int keyCode;
        public int altKeyCode;

        public String name;
    }
}
