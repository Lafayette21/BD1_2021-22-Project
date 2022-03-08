import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import javax.swing.*;

public class CheckFrame extends JFrame implements ActionListener {
    // Panel
    JPanel header;
    JPanel content;
    // Przyciski
    private JButton buttonWorker;
    private JButton buttonCar;
    private JButton buttonClient;
    private JButton buttonEnsurancer;
    private JButton buttonDepartment;
    private JButton buttonEnsurance;
    private JButton buttonRented;
    private JButton buttonReturn;

    // Ramki z tekstem
    private JLabel headerLabel;
    private JLabel labelWorker;
    private JLabel labelCar;
    private JLabel labelClient;
    private JLabel labelEnsurancer;
    private JLabel labelDepartment;
    private JLabel labelEnsurance;
    private JLabel labelRented;

    public CheckFrame() {
        // header Panel ustawienia
        header = new JPanel();
        header.setBounds(0, 0, 816, 150);
        header.setBackground(new Color(12, 40, 64));
        header.setLayout(new BorderLayout());
        // Ramka do nagłówla
        headerLabel = new JLabel();
        headerLabel.setText("Sprawdzanie stanu bazy danych");
        headerLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        headerLabel.setForeground(Color.white);
        headerLabel.setVerticalAlignment(JLabel.CENTER);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);

        // content Panel ustawienia
        content = new JPanel();
        content.setBounds(0, 150, 816, 500);
        content.setBackground(new Color(9, 115, 104));
        content.setLayout(null);
        // Label ustawienia
        labelWorker = new JLabel();
        labelWorker.setText("<html><body><center>Pracownicy i ich działy</center></body></html>");
        labelWorker.setFont(new Font("Verdana", Font.BOLD, 15));
        labelWorker.setForeground(Color.black);
        labelWorker.setVerticalAlignment(JLabel.CENTER);
        labelWorker.setHorizontalAlignment(JLabel.CENTER);
        labelWorker.setBounds(0, 0, 200, 100);
        labelWorker.setBorder(BorderFactory.createLineBorder(Color.black));

        labelCar = new JLabel();
        labelCar.setText("<html><body><center>Samochody</center></body></html>");
        labelCar.setFont(new Font("Verdana", Font.BOLD, 15));
        labelCar.setForeground(Color.black);
        labelCar.setVerticalAlignment(JLabel.CENTER);
        labelCar.setHorizontalAlignment(JLabel.CENTER);
        labelCar.setBounds(200, 0, 200, 100);
        labelCar.setBorder(BorderFactory.createLineBorder(Color.black));

        labelClient = new JLabel();
        labelClient.setText("<html><body><center>Klienci</center></body></html>");
        labelClient.setFont(new Font("Verdana", Font.BOLD, 15));
        labelClient.setForeground(Color.black);
        labelClient.setVerticalAlignment(JLabel.CENTER);
        labelClient.setHorizontalAlignment(JLabel.CENTER);
        labelClient.setBounds(400, 0, 200, 100);
        labelClient.setBorder(BorderFactory.createLineBorder(Color.black));

        labelEnsurancer = new JLabel();
        labelEnsurancer.setText("<html><body><center>Firmy ubezpieczeniowe</center></body></html>");
        labelEnsurancer.setFont(new Font("Verdana", Font.BOLD, 15));
        labelEnsurancer.setForeground(Color.black);
        labelEnsurancer.setVerticalAlignment(JLabel.CENTER);
        labelEnsurancer.setHorizontalAlignment(JLabel.CENTER);
        labelEnsurancer.setBounds(600, 0, 200, 100);
        labelEnsurancer.setBorder(BorderFactory.createLineBorder(Color.black));

        labelDepartment = new JLabel();
        labelDepartment.setText("<html><body><center>Działy</center></body></html>");
        labelDepartment.setFont(new Font("Verdana", Font.BOLD, 15));
        labelDepartment.setForeground(Color.black);
        labelDepartment.setVerticalAlignment(JLabel.CENTER);
        labelDepartment.setHorizontalAlignment(JLabel.CENTER);
        labelDepartment.setBounds(100, 200, 200, 100);
        labelDepartment.setBorder(BorderFactory.createLineBorder(Color.black));

        labelEnsurance = new JLabel();
        labelEnsurance.setText("<html><body><center>Rodzaje ubezpieczenia</center></body></html>");
        labelEnsurance.setFont(new Font("Verdana", Font.BOLD, 15));
        labelEnsurance.setForeground(Color.black);
        labelEnsurance.setVerticalAlignment(JLabel.CENTER);
        labelEnsurance.setHorizontalAlignment(JLabel.CENTER);
        labelEnsurance.setBounds(300, 200, 200, 100);
        labelEnsurance.setBorder(BorderFactory.createLineBorder(Color.black));

        labelRented = new JLabel();
        labelRented.setText("<html><body><center>Wypożyczenia</center></body></html>");
        labelRented.setFont(new Font("Verdana", Font.BOLD, 15));
        labelRented.setForeground(Color.black);
        labelRented.setVerticalAlignment(JLabel.CENTER);
        labelRented.setHorizontalAlignment(JLabel.CENTER);
        labelRented.setBounds(500, 200, 200, 100);
        labelRented.setBorder(BorderFactory.createLineBorder(Color.black));
        // Przyciski ustawienia
        buttonWorker = new JButton("Sprawdz");
        buttonWorker.addActionListener(this);
        buttonWorker.setBounds(50, 125, 100, 50);

        buttonCar = new JButton("Sprawdz");
        buttonCar.addActionListener(this);
        buttonCar.setBounds(250, 125, 100, 50);

        buttonClient = new JButton("Sprawdz");
        buttonClient.addActionListener(this);
        buttonClient.setBounds(450, 125, 100, 50);

        buttonEnsurancer = new JButton("Sprawdz");
        buttonEnsurancer.addActionListener(this);
        buttonEnsurancer.setBounds(650, 125, 100, 50);

        buttonDepartment = new JButton("Sprawdz");
        buttonDepartment.addActionListener(this);
        buttonDepartment.setBounds(150, 325, 100, 50);

        buttonEnsurance = new JButton("Sprawdz");
        buttonEnsurance.addActionListener(this);
        buttonEnsurance.setBounds(350, 325, 100, 50);

        buttonRented = new JButton("Sprawdz");
        buttonRented.addActionListener(this);
        buttonRented.setBounds(550, 325, 100, 50);
        // Przycisk powrót
        buttonReturn = new JButton("Powrot");
        buttonReturn.addActionListener(this);
        buttonReturn.setBounds(350, 400, 100, 40);
        // Ramka ustawienia
        this.setTitle("Dodawanie do bazy danych");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(816, 650);

        this.setResizable(false);
        // Dodanie Elementu do header
        header.add(headerLabel);
        // Dodawanie Elementów do Siatki w content
        content.add(labelWorker);
        content.add(labelCar);
        content.add(labelClient);
        content.add(labelEnsurancer);
        content.add(labelDepartment);
        content.add(labelEnsurance);
        content.add(labelRented);

        content.add(buttonWorker);
        content.add(buttonCar);
        content.add(buttonClient);
        content.add(buttonEnsurancer);
        content.add(buttonDepartment);
        content.add(buttonEnsurance);
        content.add(buttonRented);
        content.add(buttonReturn);
        // Dodawanie elementów na Ramkę
        this.add(header);
        this.add(content);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Pracownicy
        if (e.getSource() == buttonWorker) {
            this.dispose();
            new CheckSQL("Pracownicy");
            System.out.println("Nowy pracownik");
        } // Samochody
        else if (e.getSource() == buttonCar) {
            this.dispose();
            new CheckSQL("Samochody");
            System.out.println("Nowy samochód");
        } // Klienci
        else if (e.getSource() == buttonClient) {
            this.dispose();
            new CheckSQL("Klienci");
            System.out.println("nowy klient");
        } // Ubezpieczyciele
        else if (e.getSource() == buttonEnsurancer) {
            this.dispose();
            new CheckSQL("Ubezpieczyciele");
            System.out.println("nowy ubezpieczyciel");
        } // Dzialy
        else if (e.getSource() == buttonDepartment) {
            this.dispose();
            new CheckSQL("Dzialy");
        } // Rodzaje ubezpieczen
        else if (e.getSource() == buttonEnsurance) {
            this.dispose();
            new CheckSQL("Rodzaje ubezpieczen");
        } // Wypozyczenia
        else if (e.getSource() == buttonRented) {
            this.dispose();
            new CheckSQL("Wypozyczenia");
        } // Powrot
        else if (e.getSource() == buttonReturn) {
            this.dispose();
            new GUI();
        }
    }

}