import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginComponents extends JFrame implements ActionListener {
		
	Container container = getContentPane();
    JLabel userLabel = new JLabel("PLAYER  " + "  NAME");
    JTextField userTextField = new JTextField();
    JButton loginButton = new JButton("PROCEED --->");
    JLabel title = new JLabel("ZELDA  RPG");

    LoginComponents() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }

    public void setLayoutManager() {
        container.setLayout(null);
    	container.setBackground(Color.black);
        
    }

    public void setLocationAndSize() {
        userLabel.setBounds(180, 280, 200, 30);
        userTextField.setBounds(400, 280, 180, 30);
        loginButton.setBounds(270, 450, 160, 30);
        title.setBounds(220, 80, 270, 60);
        title.setFont(new Font("Algerian", Font.BOLD, 45));
        userLabel.setFont(new Font("CopperPlate Gothic Bold", Font.BOLD, 16));
        userTextField.setFont(new Font("Baskerville Old Face", Font.BOLD, 16));
        loginButton.setFont(new Font("CopperPlate Gothic Bold", Font.BOLD, 12));
        userLabel.setForeground(Color.yellow);
        loginButton.setBackground(Color.white);
        title.setForeground(Color.yellow);
    }

    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(userTextField);
        container.add(loginButton);
        container.add(title);
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
    }

    public String userText;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            userText = userTextField.getText();
            if (!userText.equalsIgnoreCase("")) {
            	this.dispose();
                Character C = new Character();
            } 
            else {
                JOptionPane.showMessageDialog(this, "Enter Valid Player Name!");
            }

        }
        


    }
}


