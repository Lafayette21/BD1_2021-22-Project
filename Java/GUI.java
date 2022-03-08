import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import javax.swing.*;

public class GUI extends JFrame implements ActionListener {
    // Elementy Interfejsu
    private JPanel header;
    private JPanel content;
    private JPanel footer;
    // Przyciski
    private JButton checkButton;
    private JButton addButton;
    private JButton reportButton;
    // Ramki z tekstem
    private JLabel headerLabel;
    private JLabel checkLabel;
    private JLabel addLabel;
    private JLabel reportLabel;
    private JLabel footerLabel;

    public GUI() {
        // header Panel ustawienia
        header = new JPanel();
        header.setBounds(0, 0, 750, 150);
        header.setBackground(new Color(12, 40, 64));
        header.setLayout(new BorderLayout());
        // label do naglowka
        headerLabel = new JLabel();
        headerLabel.setText("Witamy w interfejsie bazy danych naszej Firmy");
        headerLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        headerLabel.setForeground(Color.white);
        headerLabel.setVerticalAlignment(JLabel.CENTER);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        // content Panel ustawienia
        content = new JPanel();
        content.setBounds(0, 150, 750, 210);
        content.setBackground(new Color(9, 115, 104));
        content.setLayout(null);
        // footer Panel ustawienia
        footer = new JPanel();
        footer.setBounds(0, 360, 750, 100);
        footer.setBackground(new Color(69, 191, 146));
        footer.setLayout(null);

        // Label ustawienia
        checkLabel = new JLabel();
        checkLabel.setText("<html><body><center>Sprawdzanie stanu bazy danych</center></body></html>");
        checkLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        checkLabel.setForeground(Color.black);
        checkLabel.setVerticalAlignment(JLabel.CENTER);
        checkLabel.setHorizontalAlignment(JLabel.CENTER);
        checkLabel.setBounds(0, 0, 250, 120);
        checkLabel.setBorder(BorderFactory.createLineBorder(Color.black));

        addLabel = new JLabel();
        addLabel.setText("<html><body><center>Dodaj nowe dane do bazy danych</center></body></html>");
        addLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        addLabel.setForeground(Color.black);
        addLabel.setVerticalAlignment(JLabel.CENTER);
        addLabel.setHorizontalAlignment(JLabel.CENTER);
        addLabel.setBounds(250, 0, 250, 120);
        addLabel.setBorder(BorderFactory.createLineBorder(Color.black));

        reportLabel = new JLabel();
        reportLabel.setText("<html><body><center>Raporty Specjalne</center></body></html>");
        reportLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        reportLabel.setForeground(Color.black);
        reportLabel.setVerticalAlignment(JLabel.CENTER);
        reportLabel.setHorizontalAlignment(JLabel.CENTER);
        reportLabel.setBounds(500, 0, 250, 120);
        reportLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        // Przyciski ustawienia
        checkButton = new JButton("Sprawdź");
        checkButton.addActionListener(this);
        checkButton.setBounds(80, 145, 100, 50);

        addButton = new JButton("Sprawdź");
        addButton.addActionListener(this);
        addButton.setBounds(320, 145, 100, 50);

        reportButton = new JButton("Sprawdź");
        reportButton.addActionListener(this);
        reportButton.setBounds(570, 145, 100, 50);
        // Stopka
        footerLabel = new JLabel();
        footerLabel.setText("Autor Marcin Urbanowicz");
        footerLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        footerLabel.setBounds(0, 0, 750, 100);
        footerLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        footerLabel.setForeground(Color.black);
        footerLabel.setVerticalAlignment(JLabel.CENTER);
        footerLabel.setHorizontalAlignment(JLabel.CENTER);
        // Frame
        this.setTitle("Projekt Marcin Urbanowicz");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(750, 460);
        this.setResizable(false);
        // Dodanie Elementu do header
        header.add(headerLabel);
        // Dodawanie Elementów do Siatki w content
        content.add(checkLabel);
        content.add(addLabel);
        content.add(reportLabel);
        content.add(checkButton);
        content.add(addButton);
        content.add(reportButton);
        // Dodanie do elemntu footer
        footer.add(footerLabel);
        // Dodawanie elementów na Ramkę
        this.add(header);
        this.add(content);
        this.add(footer);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Sprawdzanie zawartości baz danych interfejs
        if (e.getSource() == checkButton) {
            this.dispose();
            new CheckFrame();
            System.out.println("checkButton");
        } // Dodawanie do baz danych interfejs
        else if (e.getSource() == addButton) {
            this.dispose();
            new AddFrame();
            System.out.println("addButton");
        } // Raporty z baz danych interfejs
        else if (e.getSource() == reportButton) {
            this.dispose();
            new ReportFrame();
            System.out.println("reportButton");
        }
    }
}