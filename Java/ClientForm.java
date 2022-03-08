import java.sql.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;

public class ClientForm extends JFrame implements ActionListener {
    // Panele
    private JPanel header;
    private JPanel content;
    private JPanel footer;
    // Etykiety z tekstem
    private JLabel headerLabel;
    private JLabel telefonLabel;
    private JLabel imieLabel;
    private JLabel nazwiskoLabel;
    // Pola do wpisywanie
    private JTextField telefonField;
    private JTextField imieField;
    private JTextField nazwiskoField;
    // Przyciski
    private JButton returnButton;
    private JButton submitButton;

    public ClientForm() {
        // header Panel ustawienia
        header = new JPanel();
        header.setBounds(0, 0, 500, 150);
        header.setBackground(new Color(12, 40, 64));
        header.setLayout(new BorderLayout());
        // Etykieta do naglowka
        headerLabel = new JLabel();
        headerLabel.setText("Nowy Klient");
        headerLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        headerLabel.setForeground(Color.white);
        headerLabel.setVerticalAlignment(JLabel.CENTER);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        // content panel ustawienia
        content = new JPanel();
        content.setBounds(0, 150, 500, 200);
        content.setBackground(new Color(9, 115, 104));
        content.setLayout(new GridLayout(3, 2));
        // footer
        footer = new JPanel();
        footer.setBounds(0, 350, 500, 50);
        footer.setBackground(new Color(9, 115, 104));
        footer.setLayout(new FlowLayout());

        // Ustawienie Label
        telefonLabel = new JLabel("Telefon");
        telefonLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        telefonLabel.setForeground(Color.black);
        telefonLabel.setVerticalAlignment(JLabel.CENTER);
        telefonLabel.setHorizontalAlignment(JLabel.CENTER);

        imieLabel = new JLabel("Imie");
        imieLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        imieLabel.setForeground(Color.black);
        imieLabel.setVerticalAlignment(JLabel.CENTER);
        imieLabel.setHorizontalAlignment(JLabel.CENTER);

        nazwiskoLabel = new JLabel("Nazwisko");
        nazwiskoLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        nazwiskoLabel.setForeground(Color.black);
        nazwiskoLabel.setVerticalAlignment(JLabel.CENTER);
        nazwiskoLabel.setHorizontalAlignment(JLabel.CENTER);

        // Pola tekstowe
        telefonField = new JTextField();
        telefonField.setFont(new Font("Verdana", Font.PLAIN, 20));

        imieField = new JTextField();
        imieField.setFont(new Font("Verdana", Font.PLAIN, 20));

        nazwiskoField = new JTextField();
        nazwiskoField.setFont(new Font("Verdana", Font.PLAIN, 20));

        // Przyciski
        submitButton = new JButton("Dodaj");
        submitButton.addActionListener(this);

        returnButton = new JButton("Powrot");
        returnButton.addActionListener(this);

        // Ramka ustawienia
        this.setTitle("Formlarz");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 400);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);

        // Dodawanie elementow do paneli
        header.add(headerLabel);

        content.add(telefonLabel);
        content.add(telefonField);

        content.add(imieLabel);
        content.add(imieField);

        content.add(nazwiskoLabel);
        content.add(nazwiskoField);

        footer.add(submitButton);
        footer.add(returnButton);
        // Dodawanie gotowych elementow do ramki
        this.add(header);
        this.add(content);
        this.add(footer);
    }

    private void insertToDataBase(String telefon, String imie, String nazwisko) {
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
                PreparedStatement pst = conn.prepareStatement("SELECT COUNT(*) AS ilosc FROM klient");
                ResultSet rs = pst.executeQuery();
                rs.next();
                int rows = rs.getInt("ilosc");
                rows++;
                System.out.println(rows);
                String sql = "INSERT INTO klient (id_klienta,telefon,imie,nazwisko) values (" + rows + ",'" + telefon
                        + "','" + imie + "','" + nazwisko + "')";
                Statement myStmt = conn.createStatement();
                myStmt.executeUpdate(sql);
                pst.close();
                rs.close();
                this.dispose();
                new AddResultFrame("Operacja sie powiodla");
            } catch (SQLException e) {
                System.out.println("Blad podczas przetwarzania danych:" + e);
                this.dispose();
                new AddResultFrame("Operacja nie powiodla sie");
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
            String imie = imieField.getText();
            String nazwisko = nazwiskoField.getText();
            String telefon = telefonField.getText();
            if (telefon != "" && imie != "" && nazwisko != "") {
                insertToDataBase(telefon, imie, nazwisko);
            }
        }
    }
}