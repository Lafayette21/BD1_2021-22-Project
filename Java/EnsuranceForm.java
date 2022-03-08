import java.sql.*;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;

public class EnsuranceForm extends JFrame implements ActionListener {
    // Panele
    private JPanel header;
    private JPanel content;
    private JPanel footer;
    // Etykiety z tekstem
    private JLabel headerLabel;
    private JLabel rodzajLabel;
    private JLabel kosztLabel;

    // Pola do wpisywanie
    private JTextField rodzajField;
    private JTextField kosztField;

    // Przyciski
    private JButton returnButton;
    private JButton submitButton;

    public EnsuranceForm() {
        // header Panel ustawienia
        header = new JPanel();
        header.setBounds(0, 0, 500, 150);
        header.setBackground(new Color(12, 40, 64));
        header.setLayout(new BorderLayout());
        // Etykieta do naglowka
        headerLabel = new JLabel();
        headerLabel.setText("Nowy rodzaj ubezpieczenie");
        headerLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        headerLabel.setForeground(Color.white);
        headerLabel.setVerticalAlignment(JLabel.CENTER);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        // content panel ustawienia
        content = new JPanel();
        content.setBounds(0, 150, 500, 150);
        content.setBackground(new Color(9, 115, 104));
        content.setLayout(new GridLayout(2, 2));
        // footer
        footer = new JPanel();
        footer.setBounds(0, 300, 500, 50);
        footer.setBackground(new Color(9, 115, 104));
        footer.setLayout(new FlowLayout());

        // Ustawienie Label
        rodzajLabel = new JLabel("Nazwa");
        rodzajLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        rodzajLabel.setForeground(Color.black);
        rodzajLabel.setVerticalAlignment(JLabel.CENTER);
        rodzajLabel.setHorizontalAlignment(JLabel.CENTER);

        kosztLabel = new JLabel("Koszt");
        kosztLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        kosztLabel.setForeground(Color.black);
        kosztLabel.setVerticalAlignment(JLabel.CENTER);
        kosztLabel.setHorizontalAlignment(JLabel.CENTER);

        // Pola tekstowe
        rodzajField = new JTextField();
        rodzajField.setFont(new Font("Verdana", Font.PLAIN, 20));
        kosztField = new JTextField();
        kosztField.setFont(new Font("Verdana", Font.PLAIN, 20));

        // Przyciski
        submitButton = new JButton("Dodaj");
        submitButton.addActionListener(this);

        returnButton = new JButton("Powrot");
        returnButton.addActionListener(this);

        // Ramka ustawienia
        this.setTitle("Formlarz");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 350);
        this.setLayout(null);

        this.setResizable(false);

        // Dodawanie elementow do panel
        header.add(headerLabel);

        content.add(rodzajLabel);
        content.add(rodzajField);

        content.add(kosztLabel);
        content.add(kosztField);

        footer.add(submitButton);
        footer.add(returnButton);
        // Dodawanie gotowych elementow do ramki
        this.add(header);
        this.add(content);
        this.add(footer);

        this.setVisible(true);
    }

    private void insertToDataBase(String rodzaj, int koszt) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Nie znaleziono sterownika!");
            System.out.println("Wyduk sledzenia bledu i zakonczenie.");
            cnfe.printStackTrace();
            System.exit(1);
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://pascal.fis.agh.edu.pl:5432/u9urbanowicz?currentSchema=projekt", "u9urbanowicz",
                    "9urbanowicz");
        } catch (SQLException se) {
            System.out.println("Brak polaczenia z baza danych, wydruk logu sledzenia i koniec.");
            System.exit(1);
        }
        // Udane polaczenie
        if (conn != null) {
            System.out.println("Polaczenie z baza danych OK ! ");
            try {
                PreparedStatement pst = conn.prepareStatement("SELECT COUNT(*) AS ilosc FROM ubezpieczenia_samochodu");
                ResultSet rs = pst.executeQuery();
                rs.next();
                int rows = rs.getInt("ilosc");
                rows++;
                String sql = "INSERT INTO ubezpieczenia_samochodu (id_ubezpieczenia,rodzaj_ubezpieczenia,koszt) values ("
                        + rows + ",'" + rodzaj + "'," + koszt + ")";
                Statement myStmt = conn.createStatement();
                myStmt.executeUpdate(sql);
                pst.close();
                rs.close();
                this.dispose();
                new AddResultFrame("Operacja sie powiodla");
            } catch (SQLException e) {
                System.out.println("Blad podczas przetwarzania danych:" + e);
                this.dispose();
                new AddResultFrame("Operacja sie nie powiodla");
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnButton) {
            this.dispose();
            new AddFrame();
        }
        if (e.getSource() == submitButton) {
            String rodzaj = rodzajField.getText();
            String koszt = kosztField.getText();
            if (rodzaj != "" && koszt != "") {
                insertToDataBase(rodzaj, Integer.valueOf(koszt));
            }
        }
    }
}