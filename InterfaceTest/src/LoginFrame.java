import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

public class LoginFrame {
    public static void main(String[] a) {    	
    	
    	Border border = BorderFactory.createLineBorder(Color.yellow, 10);
        
    	JLabel label = new JLabel();
    	label.setBorder(border);
    	label.setOpaque(true);
    	
    	LoginComponents frame = new LoginComponents();
    	frame.setLayout(null);
        frame.setTitle("Game Menu - Welcome!");
        frame.setVisible(true);
        frame.setBounds(550, 130, 750, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        ImageIcon image = new ImageIcon("player_1.png");
        frame.setIconImage(image.getImage());
    }

}