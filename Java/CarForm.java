import java.sql.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;

public class CarForm extends JFrame implements ActionListener {
    // Panele
    private JPanel header;
    private JPanel content;
    private JPanel footer;
    // Etykiety z tekstem
    private JLabel headerLabel;
    private JLabel markaLabel;
    private JLabel modelLabel;
    private JLabel rocznikLabel;
    private JLabel silnikLabel;
    private JLabel wartoscLabel;
    // Pola do wpisywanie
    private JTextField markaField;
    private JTextField modelField;
    private JTextField rocznikField;
    private JTextField silnikField;
    private JTextField wartoscField;

    // Przyciski
    private JButton returnButton;
    private JButton submitButton;

    public CarForm() {
        // header Panel ustawienia
        header = new JPanel();
        header.setBounds(0, 0, 500, 150);
        header.setBackground(new Color(12, 40, 64));
        header.setLayout(new BorderLayout());
        // Etykieta do naglowka
        headerLabel = new JLabel();
        headerLabel.setText("Nowy Samochod");
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
        markaLabel = new JLabel("Marka");
        markaLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        markaLabel.setForeground(Color.black);
        markaLabel.setVerticalAlignment(JLabel.CENTER);
        markaLabel.setHorizontalAlignment(JLabel.CENTER);

        modelLabel = new JLabel("Model");
        modelLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        modelLabel.setForeground(Color.black);
        modelLabel.setVerticalAlignment(JLabel.CENTER);
        modelLabel.setHorizontalAlignment(JLabel.CENTER);

        rocznikLabel = new JLabel("Rocznik");
        rocznikLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        rocznikLabel.setForeground(Color.black);
        rocznikLabel.setVerticalAlignment(JLabel.CENTER);
        rocznikLabel.setHorizontalAlignment(JLabel.CENTER);

        silnikLabel = new JLabel("Silnik");
        silnikLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        silnikLabel.setForeground(Color.black);
        silnikLabel.setVerticalAlignment(JLabel.CENTER);
        silnikLabel.setHorizontalAlignment(JLabel.CENTER);

        wartoscLabel = new JLabel("Wartosc");
        wartoscLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        wartoscLabel.setForeground(Color.black);
        wartoscLabel.setVerticalAlignment(JLabel.CENTER);
        wartoscLabel.setHorizontalAlignment(JLabel.CENTER);

        // Pola tekstowe
        markaField = new JTextField();
        markaField.setFont(new Font("Verdana", Font.PLAIN, 20));

        modelField = new JTextField();
        modelField.setFont(new Font("Verdana", Font.PLAIN, 20));

        rocznikField = new JTextField();
        rocznikField.setFont(new Font("Verdana", Font.PLAIN, 20));

        silnikField = new JTextField();
        silnikField.setFont(new Font("Verdana", Font.PLAIN, 20));

        wartoscField = new JTextField();
        wartoscField.setFont(new Font("Verdana", Font.PLAIN, 20));

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

        content.add(markaLabel);
        content.add(markaField);

        content.add(modelLabel);
        content.add(modelField);

        content.add(rocznikLabel);
        content.add(rocznikField);

        content.add(silnikLabel);
        content.add(silnikField);

        content.add(wartoscLabel);
        content.add(wartoscField);

        footer.add(submitButton);
        footer.add(returnButton);
        // Dodawanie gotowych elementow do ramki
        this.add(header);
        this.add(content);
        this.add(footer);

        this.setVisible(true);
    }

    private void insertToDataBase(String marka, String model, String rocznik, String silnik, int wartosc) {
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
                PreparedStatement pst = conn.prepareStatement("SELECT COUNT(*) AS ilosc FROM samochod");
                ResultSet rs = pst.executeQuery();
                rs.next();
                int rows = rs.getInt("ilosc");
                rows++;
                String sql = "INSERT INTO samochod (id_samochodu,id_wypozyczenia,marka,model_samochodu,rocznik,silnik,wartosc) values ("
                        + rows + ",null,'" + marka + "','" + model + "','" + rocznik + "','" + silnik + "'," + wartosc
                        + ")";
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
            String marka = markaField.getText();
            String model = modelField.getText();
            String rocznik = rocznikField.getText();
            String silnik = silnikField.getText();
            String wart = wartoscField.getText();
            if (marka != "" && model != "" && rocznik != "" && silnik != "" && wart != "") {
                insertToDataBase(marka, model, rocznik, silnik, Integer.valueOf(wart));
            }
        }
    }

}