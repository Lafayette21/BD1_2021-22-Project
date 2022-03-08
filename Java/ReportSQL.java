import java.sql.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;

public class ReportSQL extends JFrame implements ActionListener {
    // Panele
    private JPanel header;
    private JPanel content;
    private JPanel footer;
    // Etykiety z tekstem
    private JLabel headerLabel;

    // Przyciski
    private JButton returnButton;

    public ReportSQL(String data) {
        // Czesc interfejsowa

        // header Panel ustawienia
        header = new JPanel();
        header.setBounds(0, 0, 800, 150);
        header.setBackground(new Color(12, 40, 64));
        header.setLayout(new BorderLayout());
        // Etykieta do naglowka
        headerLabel = new JLabel();
        headerLabel.setText(data);
        headerLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        headerLabel.setForeground(Color.white);
        headerLabel.setVerticalAlignment(JLabel.CENTER);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        // content panel ustawienia
        content = new JPanel();
        content.setBounds(0, 150, 800, 500);
        content.setBackground(new Color(9, 115, 104));

        // footer panel
        footer = new JPanel();
        footer.setBounds(0, 650, 800, 50);
        footer.setBackground(new Color(9, 115, 104));
        footer.setLayout(new FlowLayout());

        // Przycisk
        returnButton = new JButton("Powrot");
        returnButton.addActionListener(this);

        // Ramka ustawienia
        this.setTitle(data);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 700);
        this.setLayout(null);

        this.setResizable(false);

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Nie znaleziono sterownika!");
            System.out.println("Wyduk sledzenia bledu i zakonczenie.");
            cnfe.printStackTrace();
            System.exit(1);
        }
        Connection c = null;
        // Polaczenie z baza danych
        try {
            c = DriverManager.getConnection(
                    "jdbc:postgresql://pascal.fis.agh.edu.pl:5432/u9urbanowicz?currentSchema=projekt", "u9urbanowicz",
                    "9urbanowicz");
        } catch (SQLException se) {
            System.out.println("Brak polaczenia z baza danych, wydruk logu sledzenia i koniec.");
            System.exit(1);
        }
        if (c != null) {
            System.out.println("Polaczenie z baza danych OK ! ");
            try {
                // Wypozyczone
                if (data == "Wypozyczone") {
                    PreparedStatement pst1 = c.prepareStatement("SELECT * FROM niedostepne");
                    PreparedStatement pst2 = c.prepareStatement("SELECT COUNT(*) AS ilosc FROM niedostepne");
                    selectNiedostepne(pst1, pst2);
                }
                if (data == "Dostepne") {
                    PreparedStatement pst1 = c.prepareStatement("SELECT * FROM dostepne");
                    PreparedStatement pst2 = c.prepareStatement("SELECT COUNT(*) AS ilosc FROM dostepne");
                    selectDostepne(pst1, pst2);
                }
                if (data == "Wyniki Pracownikow") {
                    PreparedStatement pst1 = c.prepareStatement("SELECT * FROM wyniki_pracownikow");
                    PreparedStatement pst2 = c.prepareStatement("SELECT COUNT(*) AS ilosc FROM wyniki_pracownikow");
                    selectWyniki(pst1, pst2);
                }
                if (data == "Koszty ubezpieczen") {
                    PreparedStatement pst1 = c.prepareStatement("SELECT * FROM koszt_ubezpieczen");
                    PreparedStatement pst2 = c.prepareStatement("SELECT COUNT(*) AS ilosc FROM koszt_ubezpieczen");
                    selectKoszt(pst1, pst2);
                }
            } catch (SQLException e) {
                System.out.println("Blad podczas przetwarzania danych:" + e);
            }
        } else {
            System.out.println("Brak polaczenia z baza, dalsza czesc aplikacji nie jest wykonywana.");
        }

        // Dodawanie elementow do panel
        header.add(headerLabel);

        footer.add(returnButton);
        // Dodawanie gotowych elementow do ramki
        this.add(header);
        this.add(content);
        this.add(footer);

        this.setVisible(true);
    }

    private void selectNiedostepne(PreparedStatement pst1, PreparedStatement pst2) {
        try {

            ResultSet rs1 = pst1.executeQuery();
            ResultSet rs2 = pst2.executeQuery();

            // Ilosc Wierszy
            rs2.next();
            int rows = rs2.getInt("ilosc");
            rows++;

            content.setLayout(new GridLayout(rows, 5));
            content.add(new JLabel("Marka"));
            content.add(new JLabel("Model"));
            content.add(new JLabel("imie"));
            content.add(new JLabel("Nazwisko"));
            content.add(new JLabel("Telefon"));

            while (rs1.next()) {
                String marka = rs1.getString("marka");
                String model = rs1.getString("model_samochodu");
                String imie = rs1.getString("imie");
                String nazwisko = rs1.getString("nazwisko");
                String telefon = rs1.getString("telefon");
                content.add(new JLabel(marka));
                content.add(new JLabel(model));
                content.add(new JLabel(imie));
                content.add(new JLabel(nazwisko));
                content.add(new JLabel(telefon));
            }
            rs1.close();
            rs2.close();
            pst1.close();
            pst2.close();
        } catch (SQLException e) {
            System.out.println("Blad podczas przetwarzania danych:" + e);
        }
    }

    private void selectDostepne(PreparedStatement pst1, PreparedStatement pst2) {
        try {
            ResultSet rs1 = pst1.executeQuery();
            ResultSet rs2 = pst2.executeQuery();

            // Ilosc Wierszy
            rs2.next();
            int rows = rs2.getInt("ilosc");
            rows++;

            content.setLayout(new GridLayout(rows, 5));
            content.add(new JLabel("Marka"));
            content.add(new JLabel("Model"));
            content.add(new JLabel("imie"));
            content.add(new JLabel("Nazwisko"));
            content.add(new JLabel("Telefon"));

            while (rs1.next()) {
                String marka = rs1.getString("marka");
                String model = rs1.getString("model_samochodu");
                String imie = rs1.getString("imie");
                String nazwisko = rs1.getString("nazwisko");
                String telefon = rs1.getString("telefon");
                content.add(new JLabel(marka));
                content.add(new JLabel(model));
                content.add(new JLabel(imie));
                content.add(new JLabel(nazwisko));
                content.add(new JLabel(telefon));
            }
            rs1.close();
            rs2.close();
            pst1.close();
            pst2.close();
        } catch (SQLException e) {
            System.out.println("Blad podczas przetwarzania danych:" + e);
        }
    }

    private void selectWyniki(PreparedStatement pst1, PreparedStatement pst2) {
        try {
            ResultSet rs1 = pst1.executeQuery();
            ResultSet rs2 = pst2.executeQuery();

            // Ilosc Wierszy
            rs2.next();
            int rows = rs2.getInt("ilosc");
            rows++;

            content.setLayout(new GridLayout(rows, 3));
            content.add(new JLabel("imie"));
            content.add(new JLabel("Nazwisko"));
            content.add(new JLabel("Ilosc zawartych umow"));

            while (rs1.next()) {
                String imie = rs1.getString("imie");
                String nazwisko = rs1.getString("nazwisko");
                String umowy = rs1.getString("ilosc_umow");
                content.add(new JLabel(imie));
                content.add(new JLabel(nazwisko));
                content.add(new JLabel(umowy));
            }
            rs1.close();
            rs2.close();
            pst1.close();
            pst2.close();
        } catch (SQLException e) {
            System.out.println("Blad podczas przetwarzania danych:" + e);
        }
    }

    // Koszty ubezpieczen
    private void selectKoszt(PreparedStatement pst1, PreparedStatement pst2) {
        try {

            ResultSet rs1 = pst1.executeQuery();
            ResultSet rs2 = pst2.executeQuery();

            // Ilosc Wierszy
            rs2.next();
            int rows = rs2.getInt("ilosc");
            rows++;

            content.setLayout(new GridLayout(rows, 4));
            content.add(new JLabel("Id"));
            content.add(new JLabel("Marka"));
            content.add(new JLabel("Model"));
            content.add(new JLabel("Koszt ubezpieczen"));
            while (rs1.next()) {
                int id = rs1.getInt("id_samochodu");
                String nazwa = rs1.getString("marka");
                String model = rs1.getString("model_samochodu");
                int suma = rs1.getInt("sum");
                content.add(new JLabel(Integer.valueOf(id).toString()));
                content.add(new JLabel(nazwa));
                content.add(new JLabel(model));
                content.add(new JLabel(Integer.valueOf(suma).toString()));
            }
            rs1.close();
            rs2.close();
            pst1.close();
            pst2.close();
        } catch (SQLException e) {
            System.out.println("Blad podczas przetwarzania danych:" + e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnButton) {
            this.dispose();
            new ReportFrame();
        }
    }
}