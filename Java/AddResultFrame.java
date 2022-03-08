import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AddResultFrame extends JFrame implements ActionListener {
    // Przycisk
    JButton returnButton;
    // Label
    JLabel message;

    public AddResultFrame(String text) {
        returnButton = new JButton("Powrot");
        returnButton.addActionListener(this);
        returnButton.setBounds(150, 200, 100, 40);

        message = new JLabel(text);
        message.setFont(new Font("Verdana", Font.BOLD, 15));
        message.setForeground(Color.black);
        message.setBounds(50, 100, 300, 100);
        message.setVerticalAlignment(JLabel.CENTER);
        message.setHorizontalAlignment(JLabel.CENTER);

        this.add(message);
        this.add(returnButton);

        this.setTitle("Wynik");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setBackground(new Color(9, 115, 104));
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnButton) {
            this.dispose();
            new AddFrame();
        }
    }

}