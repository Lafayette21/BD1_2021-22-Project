import java.sql.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;

public class DepartmentForm extends JFrame implements ActionListener {
    // Panele
    private JPanel header;
    private JPanel content;
    private JPanel footer;

    // Etykiety z tekstem
    private JLabel headerLabel;
    private JLabel nazwaLabel;
    private JLabel etatyLabel;
    // Pola do wpisywanie
    private JTextField nazwaField;
    private JTextField etatyField;

    // Przyciski
    private JButton returnButton;
    private JButton submitButton;

    public DepartmentForm() {
        // header Panel ustawienia
        header = new JPanel();
        header.setBounds(0, 0, 500, 150);
        header.setBackground(new Color(12, 40, 64));
        header.setLayout(new BorderLayout());
        // Etykieta do naglowka
        headerLabel = new JLabel();
        headerLabel.setText("Nowy Dzial");
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
        nazwaLabel = new JLabel("Nazwa");
        nazwaLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        nazwaLabel.setForeground(Color.black);
        nazwaLabel.setVerticalAlignment(JLabel.CENTER);
        nazwaLabel.setHorizontalAlignment(JLabel.CENTER);

        etatyLabel = new JLabel("Ilosc etatow");
        etatyLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        etatyLabel.setForeground(Color.black);
        etatyLabel.setVerticalAlignment(JLabel.CENTER);
        etatyLabel.setHorizontalAlignment(JLabel.CENTER);
        // Pola tekstowe
        nazwaField = new JTextField();
        nazwaField.setFont(new Font("Verdana", Font.PLAIN, 20));

        etatyField = new JTextField();
        etatyField.setFont(new Font("Verdana", Font.PLAIN, 20));

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

        content.add(nazwaLabel);
        content.add(nazwaField);

        content.add(etatyLabel);
        content.add(etatyField);

        footer.add(submitButton);
        footer.add(returnButton);
        // Dodawanie gotowych elementow do ramki
        this.add(header);
        this.add(content);
        this.add(footer);

        this.setVisible(true);

    }

    private void insertToDataBase(String nazwa, int etaty) {
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
            try {
                System.out.println("Polaczenie z baza danych OK ! ");

                PreparedStatement pst = conn.prepareStatement("SELECT COUNT(*) AS ilosc FROM dzial");
                ResultSet rs = pst.executeQuery();
                rs.next();
                int rows = rs.getInt("ilosc");
                rows++;
                String sql = "INSERT INTO dzial (id_dzialu,nazwa_dzialu,ilosc_etatow) values (" + rows + ",'" + nazwa
                        + "'," + etaty + ")";
                Statement myStmt = conn.createStatement();
                myStmt.executeUpdate(sql);
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
            String nazwa = nazwaField.getText();
            String etaty = etatyField.getText();
            if (nazwa != "" && etaty != "") {
                insertToDataBase(nazwa, Integer.valueOf(etaty));
            }
        }
    }

}