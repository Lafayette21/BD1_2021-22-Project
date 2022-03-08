import java.sql.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;

public class WorkerForm extends JFrame implements ActionListener {
    // Panele
    private JPanel header;
    private JPanel content;
    private JPanel footer;
    // Etykiety z tekstem
    private JLabel headerLabel;
    private JLabel imieLabel;
    private JLabel nazwiskoLabel;
    private JLabel dzialLabel;
    private JLabel stanowiskoLabel;
    private JLabel wynagrodzenieLabel;
    // Pola do wpisywanie
    private JTextField imieField;
    private JTextField nazwiskoField;
    private JTextField dzialField;
    private JTextField stanowiskoField;
    private JTextField wynagrodzenieField;

    // Przyciski
    private JButton returnButton;
    private JButton submitButton;

    public WorkerForm() {
        // header Panel ustawienia
        header = new JPanel();
        header.setBounds(0, 0, 500, 150);
        header.setBackground(new Color(12, 40, 64));
        header.setLayout(new BorderLayout());
        // Etykieta do naglowka
        headerLabel = new JLabel();
        headerLabel.setText("Nowy Pracownik");
        headerLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        headerLabel.setForeground(Color.white);
        headerLabel.setVerticalAlignment(JLabel.CENTER);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        // content panel ustawienia
        content = new JPanel();
        content.setBounds(0, 150, 500, 320);
        content.setBackground(new Color(9, 115, 104));
        content.setLayout(new GridLayout(5, 2));
        // footer
        footer = new JPanel();
        footer.setBounds(0, 470, 500, 50);
        footer.setBackground(new Color(9, 115, 104));
        footer.setLayout(new FlowLayout());

        // Ustawienie Label
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

        dzialLabel = new JLabel("Dzial");
        dzialLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        dzialLabel.setForeground(Color.black);
        dzialLabel.setVerticalAlignment(JLabel.CENTER);
        dzialLabel.setHorizontalAlignment(JLabel.CENTER);

        stanowiskoLabel = new JLabel("Stanowisko");
        stanowiskoLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        stanowiskoLabel.setForeground(Color.black);
        stanowiskoLabel.setVerticalAlignment(JLabel.CENTER);
        stanowiskoLabel.setHorizontalAlignment(JLabel.CENTER);

        wynagrodzenieLabel = new JLabel("Wynagrodzenie");
        wynagrodzenieLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        wynagrodzenieLabel.setForeground(Color.black);
        wynagrodzenieLabel.setVerticalAlignment(JLabel.CENTER);
        wynagrodzenieLabel.setHorizontalAlignment(JLabel.CENTER);

        // Pola tekstowe
        imieField = new JTextField();
        imieField.setFont(new Font("Verdana", Font.PLAIN, 20));
        nazwiskoField = new JTextField();
        nazwiskoField.setFont(new Font("Verdana", Font.PLAIN, 20));
        dzialField = new JTextField();
        dzialField.setFont(new Font("Verdana", Font.PLAIN, 20));
        stanowiskoField = new JTextField();
        stanowiskoField.setFont(new Font("Verdana", Font.PLAIN, 20));
        wynagrodzenieField = new JTextField();
        wynagrodzenieField.setFont(new Font("Verdana", Font.PLAIN, 20));

        // Przyciski
        submitButton = new JButton("Dodaj");
        submitButton.addActionListener(this);

        returnButton = new JButton("Powrot");
        returnButton.addActionListener(this);
        // Ramka ustawienia
        this.setTitle("Formlarz");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 520);
        this.setLayout(null);

        this.setResizable(false);

        // Dodawanie elementow do panel
        header.add(headerLabel);

        content.add(imieLabel);
        content.add(imieField);

        content.add(nazwiskoLabel);
        content.add(nazwiskoField);

        content.add(dzialLabel);
        content.add(dzialField);

        content.add(stanowiskoLabel);
        content.add(stanowiskoField);

        content.add(wynagrodzenieLabel);
        content.add(wynagrodzenieField);

        footer.add(submitButton);
        footer.add(returnButton);
        // Dodawanie gotowych elementow do ramki
        this.add(header);
        this.add(content);
        this.add(footer);

        this.setVisible(true);
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
            String dzial = dzialField.getText();
            String stanowisko = stanowiskoField.getText();
            String wyn = wynagrodzenieField.getText();
            if (imie != "" && nazwisko != "" && dzial != "" && stanowisko != "" && wyn != "") {
                int wynagrodzenie = Integer.valueOf(wyn);
                insertToDataBase(imie, nazwisko, dzial, stanowisko, wynagrodzenie);
            }
        }
    }

    private void insertToDataBase(String imie, String nazwisko, String dzial, String stanowisko, int wynagrodzenie) {
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

                // Sprawdzenie dzialu
                PreparedStatement pst1 = conn
                        .prepareStatement("SELECT id_dzialu,nazwa_dzialu FROM dzial WHERE nazwa_dzialu=?");
                pst1.setString(1, dzial);
                ResultSet rs1 = pst1.executeQuery();
                rs1.next();
                int id = rs1.getInt("id_dzialu");
                String w = rs1.getString("nazwa_dzialu");
                if (w != null) {
                    PreparedStatement pst2 = conn.prepareStatement("SELECT COUNT(*) AS ilosc FROM Pracownik");
                    ResultSet rs2 = pst2.executeQuery();
                    rs2.next();
                    int rows = rs2.getInt("ilosc");
                    rows++;
                    int staz = 0;
                    String sql = "INSERT INTO Pracownik (id_pracownika, id_dzialu,imie,nazwisko,staz,wynagrodzenie,stanowisko) values ("
                            + rows + "," + id + ",'" + imie + "','" + nazwisko + "'," + staz + "," + wynagrodzenie
                            + ",'" + stanowisko + "')";
                    Statement myStmt = conn.createStatement();
                    myStmt.executeUpdate(sql);

                    pst2.close();
                    rs2.close();
                } else {
                    System.out.println("Blad wprowadzania danych");
                }
                pst1.close();
                rs1.close();

                this.dispose();
                new AddResultFrame("Operacja sie powiodla");

            } catch (SQLException e) {
                System.out.println("Blad podczas przetwarzania danych:" + e);
                this.dispose();
                new AddResultFrame("Operacja sie nie powiodla");
            }

        }

    }
}