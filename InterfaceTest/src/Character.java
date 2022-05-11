import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Character {
	
	JFrame frame = new JFrame();
	JLabel title2 = new JLabel("Choose Character Color");
	
	Character() {
		
		title2.setFont(new Font("Algerian", Font.BOLD, 40));
		title2.setBounds(100, 60, 600, 60);
        title2.setForeground(Color.yellow);
    	
        frame.setBackground(Color.black);
    	frame.setLayout(null);
		frame.add(title2);
		frame.setTitle("Character Menu");
        frame.setVisible(true);
        frame.setBounds(550, 130, 750, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        ImageIcon image = new ImageIcon("player_1.png");
        frame.setIconImage(image.getImage());
		
	}
	
	
}
