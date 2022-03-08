import java.sql.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import java.util.Vector;

public class RentForm extends JFrame implements ActionListener {
    // Panele
    private JPanel header;
    private JPanel content;
    private JPanel footer;
    // Etykiety z tekstem
    private JLabel headerLabel;
    // Przyciski
    private JButton returnButton;
    private JButton submitButton;

    // CheckBox
    private JCheckBox[] checkBoxTable;

    public RentForm() {

        // header Panel ustawienia
        header = new JPanel();
        header.setBounds(0, 0, 800, 150);
        header.setBackground(new Color(12, 40, 64));
        header.setLayout(new BorderLayout());
        // Etykieta do naglowka
        headerLabel = new JLabel();
        headerLabel.setText("Jakie samochody sa wypozyczane");
        headerLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        headerLabel.setForeground(Color.white);
        headerLabel.setVerticalAlignment(JLabel.CENTER);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        // content panel ustawienia
        content = new JPanel();
        content.setBounds(0, 150, 800, 400);
        content.setBackground(new Color(9, 115, 104));
        content.setLayout(new GridLayout(3, 2));

        // footer panel
        footer = new JPanel();
        footer.setBounds(0, 550, 800, 50);
        footer.setBackground(new Color(9, 115, 104));
        footer.setLayout(new FlowLayout());

        // Przyciski
        submitButton = new JButton("Dalej");
        submitButton.addActionListener(this);

        returnButton = new JButton("Powrot");
        returnButton.addActionListener(this);

        // Ramka ustawienia
        this.setTitle("Formlarz");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(null);

        this.setResizable(false);

        // Dodawanie elementow do paneli
        header.add(headerLabel);

        footer.add(submitButton);
        footer.add(returnButton);
        // Dodawanie gotowych elementow do ramki
        this.add(header);
        this.add(content);
        this.add(footer);

        this.setVisible(true);
        // Czesc Bazodanowa
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Nie znaleziono sterownika!");
            System.out.println("Wyduk sledzenia bledu i zakonczenie.");
            cnfe.printStackTrace();
            System.exit(1);
        }
        Connection c = null;
        try {
            c = DriverManager.getConnection(
                    "jdbc:postgresql://pascal.fis.agh.edu.pl:5432/u9urbanowicz?currentSchema=projekt", "u9urbanowicz",
                    "9urbanowicz");
        } catch (SQLException se) {
            System.out.println("Brak polaczenia z baza danych, wydruk logu sledzenia i koniec.");
            System.exit(1);
        }
        // Udane polaczenie
        if (c != null) {
            System.out.println("Polaczenie z baza danych OK ! ");
            try {
                PreparedStatement pst1 = c.prepareStatement(
                        "SELECT id_samochodu,marka,model_samochodu, rocznik,silnik,wartosc FROM Samochod JOIN wypozyczenie USING(id_wypozyczenia) WHERE data_zwrotu IS NOT NULL");
                PreparedStatement pst2 = c.prepareStatement(
                        "SELECT COUNT(*) AS ilosc FROM Samochod JOIN wypozyczenie USING(id_wypozyczenia) WHERE data_zwrotu IS NOT NULL");

                ResultSet rs1 = pst1.executeQuery();
                ResultSet rs2 = pst2.executeQuery();

                // Ilosc Wierszy
                rs2.next();
                int rows = rs2.getInt("ilosc");
                rows++;

                content.setLayout(new GridLayout(rows, 6));
                content.add(new JLabel("Wypozyczony"));
                content.add(new JLabel("Marka"));
                content.add(new JLabel("Model"));
                content.add(new JLabel("silnik"));
                content.add(new JLabel("rocznik"));
                content.add(new JLabel("wartosc"));
                checkBoxTable = new JCheckBox[rows - 1];
                int i = 0;
                while (rs1.next()) {
                    int id = rs1.getInt("id_samochodu");
                    String marka = rs1.getString("marka");
                    String model = rs1.getString("model_samochodu");
                    int rocznik = rs1.getInt("rocznik");
                    String silnik = rs1.getString("silnik");
                    int wartosc = rs1.getInt("wartosc");
                    JCheckBox checkBox = new JCheckBox();
                    checkBox.setFocusable(false);
                    checkBox.setText(Integer.valueOf(id).toString());
                    checkBox.setBackground(new Color(9, 115, 104));
                    checkBox.setVerticalAlignment(JCheckBox.CENTER);
                    checkBox.setHorizontalAlignment(JCheckBox.CENTER);
                    checkBoxTable[i] = checkBox;
                    content.add(checkBoxTable[i]);
                    content.add(new JLabel(marka));
                    content.add(new JLabel(model));
                    content.add(new JLabel(silnik));
                    content.add(new JLabel(Integer.valueOf(rocznik).toString()));
                    content.add(new JLabel(Integer.valueOf(wartosc).toString()));
                    i++;
                }
                rs1.close();
                rs2.close();
                pst1.close();
                pst2.close();
            } catch (SQLException e) {
                System.out.println("Blad podczas przetwarzania danych:" + e);
                this.dispose();
                new AddResultFrame("Operacja sie nie powiodla");
            }
        }
    }

    private Vector<Integer> areSelected(JCheckBox[] checkBoxTable, int n) {
        Vector<Integer> vect = new Vector<Integer>(0);
        for (int i = 0; i < n; i++) {
            if (checkBoxTable[i].isSelected()) {
                vect.addElement(Integer.valueOf(i + 1));
            }
        }
        return vect;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnButton) {
            this.dispose();
            new AddFrame();
        }
        if (e.getSource() == submitButton) {
            this.dispose();
            new RentClientWorker(areSelected(checkBoxTable, checkBoxTable.length));
        }
    }
}