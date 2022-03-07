package main;

import javax.swing.*;

public class Main
{
    public static GamePanel currentGamePanel;

    public static void main(String[] args)
    {
        // Create window object.
        JFrame window = new JFrame();

        // Set exit button properly.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set resizable to false.
        window.setResizable(false);

        // Set window name.
        window.setTitle("Game 2D");

        // Create Scene
        currentGamePanel = new GamePanel();
        window.add(currentGamePanel);

        window.pack();

        // Set parent location to null, hence starting it from the middle of the screen.
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Send request to start thread.
        currentGamePanel.startGameThread();
    }
}
