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

public class RentClientWorker extends JFrame implements ActionListener {
    // Vector wypozyczen
    private Vector<Integer> vector;
    // Panele
    private JPanel header;
    private JPanel contentWorker;
    private JPanel contentWorkerHeader;
    private JPanel contentClient;
    private JPanel contentClientHeader;
    private JPanel footer;
    // Label
    private JLabel headerLabel;
    private JLabel workerLabel;
    private JLabel clientLabel;

    // Przyciski
    private JButton submitButton;
    private JButton newClientButton;
    private JButton returnButton;

    // Tablice ButtonRadio
    JRadioButton[] workerButtons;
    ButtonGroup workerGroup;
    JRadioButton[] clientButtons;
    ButtonGroup clientGroup;

    public RentClientWorker(Vector<Integer> vect) {
        vector = vect;
        // header Panel ustawienia
        header = new JPanel();
        header.setBounds(0, 0, 840, 150);
        header.setBackground(new Color(12, 40, 64));
        header.setLayout(new BorderLayout());
        // Etykieta do naglowka
        headerLabel = new JLabel();
        headerLabel.setText("Szczegoly transakcji");
        headerLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        headerLabel.setForeground(Color.white);
        headerLabel.setVerticalAlignment(JLabel.CENTER);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        // ContentWorkerHeader
        contentWorkerHeader = new JPanel();
        contentWorkerHeader.setBounds(0, 150, 420, 50);
        contentWorkerHeader.setBackground(new Color(9, 115, 104));
        contentWorkerHeader.setLayout(new BorderLayout());
        contentWorkerHeader.setBorder(BorderFactory.createLineBorder(Color.black));

        // contentWorker panel ustawienia
        contentWorker = new JPanel();
        contentWorker.setBounds(0, 200, 420, 350);
        contentWorker.setBackground(new Color(9, 115, 104));
        contentWorker.setLayout(new GridLayout(3, 2));
        contentWorker.setBorder(BorderFactory.createLineBorder(Color.black));
        // contentClientHeader panel ustawienia
        contentClientHeader = new JPanel();
        contentClientHeader.setBounds(420, 150, 420, 50);
        contentClientHeader.setBackground(new Color(9, 115, 104));
        contentClientHeader.setLayout(new BorderLayout());
        contentClientHeader.setBorder(BorderFactory.createLineBorder(Color.black));
        // contentClient panel ustawienia
        contentClient = new JPanel();
        contentClient.setBounds(420, 200, 420, 350);
        contentClient.setBackground(new Color(9, 115, 104));
        contentClient.setLayout(new GridLayout(3, 2));
        contentClient.setBorder(BorderFactory.createLineBorder(Color.black));
        // footer panel
        footer = new JPanel();
        footer.setBounds(0, 550, 840, 50);
        footer.setBackground(new Color(9, 115, 104));
        footer.setLayout(new FlowLayout());

        // Label
        workerLabel = new JLabel("Pracownik");
        workerLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        workerLabel.setForeground(Color.black);
        workerLabel.setVerticalAlignment(JLabel.CENTER);
        workerLabel.setHorizontalAlignment(JLabel.CENTER);

        clientLabel = new JLabel("Klient");
        clientLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        clientLabel.setForeground(Color.black);
        clientLabel.setVerticalAlignment(JLabel.CENTER);
        clientLabel.setHorizontalAlignment(JLabel.CENTER);

        // Button
        submitButton = new JButton("Gotowe");
        submitButton.addActionListener(this);

        newClientButton = new JButton("Nowy Klient");
        newClientButton.addActionListener(this);

        returnButton = new JButton("Powrot");
        returnButton.addActionListener(this);

        // Ramka ustawienia
        this.setTitle("Formlarz");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(840, 600);
        this.setLayout(null);
        this.setResizable(false);
        // Dodawanie do paneli
        header.add(headerLabel);

        contentClientHeader.add(clientLabel);
        contentWorkerHeader.add(workerLabel);
        // Przygotowanie list na stronie
        readyContent();

        footer.add(submitButton);
        footer.add(newClientButton);
        footer.add(returnButton);

        // Dodawanie do ramki
        this.add(header);
        this.add(contentWorkerHeader);
        this.add(contentWorker);
        this.add(contentClientHeader);
        this.add(contentClient);
        this.add(footer);

        this.setVisible(true);
    }

    private void readyContent() {
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
                PreparedStatement pstWorker1 = conn.prepareStatement(
                        "SELECT id_pracownika,imie,nazwisko FROM pracownik JOIN dzial USING(id_dzialu) WHERE nazwa_dzialu='Obslugi klienta'");
                PreparedStatement pstWorker2 = conn.prepareStatement(
                        "SELECT COUNT(*) AS ilosc FROM pracownik JOIN dzial USING(id_dzialu) WHERE nazwa_dzialu='Obslugi klienta'");

                ResultSet rsWorker1 = pstWorker1.executeQuery();
                ResultSet rsWorker2 = pstWorker2.executeQuery();

                // Ilosc Wierszy
                rsWorker2.next();
                int rowsWorker = rsWorker2.getInt("ilosc");
                rowsWorker++;

                workerButtons = new JRadioButton[rowsWorker - 1];
                workerGroup = new ButtonGroup();

                contentWorker.setLayout(new GridLayout(rowsWorker, 3));
                contentWorker.add(new JLabel("Id"));
                contentWorker.add(new JLabel("Imie"));
                contentWorker.add(new JLabel("Nazwisko"));
                while (rsWorker1.next()) {
                    int id = rsWorker1.getInt("id_pracownika");
                    String imie = rsWorker1.getString("imie");
                    String nazwisko = rsWorker1.getString("nazwisko");
                    JRadioButton button = new JRadioButton(Integer.valueOf(id).toString());
                    button.setFocusable(false);
                    button.setBackground(new Color(9, 115, 104));
                    button.setVerticalAlignment(JRadioButton.CENTER);
                    button.setHorizontalAlignment(JRadioButton.CENTER);
                    workerButtons[id - 1] = button;
                    workerGroup.add(workerButtons[id - 1]);
                    contentWorker.add(workerButtons[id - 1]);

                    contentWorker.add(new JLabel(imie));
                    contentWorker.add(new JLabel(nazwisko));
                }
                pstWorker1.close();
                pstWorker2.close();
                rsWorker1.close();
                rsWorker2.close();

                // Klient
                PreparedStatement pstClient1 = conn.prepareStatement("SELECT id_klienta,imie,nazwisko FROM klient");
                PreparedStatement pstClient2 = conn.prepareStatement("SELECT COUNT(*) AS ilosc FROM klient");

                ResultSet rsClient1 = pstClient1.executeQuery();
                ResultSet rsClient2 = pstClient2.executeQuery();
                // Ilosc Wierszy
                rsClient2.next();
                int rowsClient = rsClient2.getInt("ilosc");
                rowsClient++;

                clientButtons = new JRadioButton[rowsClient - 1];
                clientGroup = new ButtonGroup();

                contentClient.setLayout(new GridLayout(rowsClient, 3));
                contentClient.add(new JLabel("Id"));
                contentClient.add(new JLabel("Imie"));
                contentClient.add(new JLabel("Nazwisko"));
                while (rsClient1.next()) {
                    int id = rsClient1.getInt("id_klienta");
                    String imie = rsClient1.getString("imie");
                    String nazwisko = rsClient1.getString("nazwisko");
                    JRadioButton button = new JRadioButton(Integer.valueOf(id).toString());
                    button.setFocusable(false);
                    button.setBackground(new Color(9, 115, 104));
                    clientButtons[id - 1] = button;
                    clientGroup.add(clientButtons[id - 1]);
                    contentClient.add(clientButtons[id - 1]);

                    contentClient.add(new JLabel(imie));
                    contentClient.add(new JLabel(nazwisko));
                }
                pstClient1.close();
                pstClient2.close();
                rsClient1.close();
                rsClient2.close();
            } catch (SQLException e) {
                System.out.println("Blad podczas przetwarzania danych:" + e);
            }
        }
    }

    private int workerChosen(JRadioButton[] workerButtons, int n) {
        for (int i = 0; i < n; i++) {
            if (workerButtons[i].isSelected()) {
                return i + 1;
            }
        }
        return 0;
    }

    private int clientChosen(JRadioButton[] clientButtons, int n) {
        for (int i = 0; i < n; i++) {
            if (clientButtons[i].isSelected()) {
                return i + 1;
            }
        }
        return 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnButton) {
            this.dispose();
            new RentForm();
        }
        if (e.getSource() == submitButton) {
            this.dispose();
            int idClient = clientChosen(workerButtons, workerButtons.length);
            int idWorker = workerChosen(clientButtons, clientButtons.length);
            System.out.println(idClient + " " + idWorker);
            new RentFinalise(idClient, idWorker, vector);
        }
        if (e.getSource() == newClientButton) {
            this.dispose();
            new ClientForm();
        }
    }

}
