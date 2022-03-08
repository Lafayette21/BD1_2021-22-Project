import java.sql.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;

public class CheckSQL extends JFrame implements ActionListener {
  // Panele
  private JPanel header;
  private JPanel content;
  private JPanel footer;
  // Etykiety z tekstem
  private JLabel headerLabel;

  // Przyciski
  private JButton returnButton;

  public CheckSQL(String data) {
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

    // Czesc bazodanowa
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
      c = DriverManager.getConnection("jdbc:postgresql://pascal.fis.agh.edu.pl:5432/u9urbanowicz?currentSchema=projekt",
          "u9urbanowicz", "9urbanowicz");
    } catch (SQLException se) {
      System.out.println("Brak polaczenia z baza danych, wydruk logu sledzenia i koniec.");
      System.exit(1);
    }
    // Udane polaczenie
    if (c != null) {
      System.out.println("Polaczenie z baza danych OK ! ");
      try {
        // Klient
        if (data == "Klienci") {
          PreparedStatement pst1 = c.prepareStatement("SELECT id_klienta,imie,nazwisko,telefon FROM Klient");
          PreparedStatement pst2 = c.prepareStatement("SELECT COUNT(*) AS ilosc FROM Klient");
          selectKlient(pst1, pst2);
        }
        // Samochody
        if (data == "Samochody") {
          PreparedStatement pst1 = c
              .prepareStatement("SELECT id_samochodu,marka,model_samochodu, rocznik,silnik,wartosc FROM Samochod");
          PreparedStatement pst2 = c.prepareStatement("SELECT COUNT(*) AS ilosc FROM Samochod");
          selectSamochod(pst1, pst2);
        }
        // Pracownik
        if (data == "Pracownicy") {
          PreparedStatement pst1 = c.prepareStatement(
              "SELECT id_pracownika,imie, nazwisko, staz, wynagrodzenie,stanowisko, nazwa_dzialu FROM Dzial JOIN Pracownik USING(id_dzialu)");
          PreparedStatement pst2 = c.prepareStatement("SELECT COUNT(*) AS ilosc FROM pracownik");
          selectPracownik(pst1, pst2);
        }
        // Dzial
        if (data == "Dzialy") {
          PreparedStatement pst1 = c.prepareStatement("SELECT id_dzialu,nazwa_dzialu,ilosc_etatow FROM dzial");
          PreparedStatement pst2 = c.prepareStatement("SELECT COUNT(*) AS ilosc FROM dzial");
          selectDzial(pst1, pst2);
        }
        // Ubezpieczenia_samochodu
        if (data == "Rodzaje ubezpieczen") {
          PreparedStatement pst1 = c
              .prepareStatement("SELECT id_ubezpieczenia, rodzaj_ubezpieczenia, koszt FROM Ubezpieczenia_samochodu");
          PreparedStatement pst2 = c.prepareStatement("SELECT COUNT(*) AS ilosc FROM Ubezpieczenia_samochodu");
          selectUbezpieczenie(pst1, pst2);
        }
        // Ubezpieczyciele
        if (data == "Ubezpieczyciele") {
          PreparedStatement pst1 = c
              .prepareStatement("SELECT id_ubezpieczyciela, nazwa_ubezpieczyciela, telefon, adres FROM Ubezpieczyciel");
          PreparedStatement pst2 = c.prepareStatement("SELECT COUNT(*) AS ilosc FROM Ubezpieczyciel");
          selectUbezpieczyciel(pst1, pst2);
        }
        // Wypozyczenie
        if (data == "Wypozyczenia") {
          PreparedStatement pst1 = c.prepareStatement("SELECT * FROM pomoc");
          PreparedStatement pst2 = c.prepareStatement("SELECT COUNT(*) AS ilosc FROM pomoc");
          selectWypozyczenie(pst1, pst2);
        }
      } catch (SQLException e) {
        System.out.println("Blad podczas przetwarzania danych:" + e);
      }
    }
    // Nieudane polaczenie
    else {
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

  // Klienci
  private void selectKlient(PreparedStatement pst1, PreparedStatement pst2) {
    try {
      ResultSet rs1 = pst1.executeQuery();
      ResultSet rs2 = pst2.executeQuery();

      // Ilosc Wierszy
      rs2.next();
      int rows = rs2.getInt("ilosc");
      rows++;

      content.setLayout(new GridLayout(rows, 4));
      content.add(new JLabel("id"));
      content.add(new JLabel("imie"));
      content.add(new JLabel("nazwisko"));
      content.add(new JLabel("telefon"));

      while (rs1.next()) {
        int id = rs1.getInt("id_klienta");
        String imie = rs1.getString("imie");
        String nazwisko = rs1.getString("nazwisko");
        String telefon = rs1.getString("telefon");
        content.add(new JLabel(Integer.valueOf(id).toString()));
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

  // Samochody
  private void selectSamochod(PreparedStatement pst1, PreparedStatement pst2) {
    try {
      ResultSet rs1 = pst1.executeQuery();
      ResultSet rs2 = pst2.executeQuery();

      // Ilosc Wierszy
      rs2.next();
      int rows = rs2.getInt("ilosc");
      rows++;

      content.setLayout(new GridLayout(rows, 6));
      content.add(new JLabel("Id"));
      content.add(new JLabel("Marka"));
      content.add(new JLabel("Model"));
      content.add(new JLabel("silnik"));
      content.add(new JLabel("rocznik"));
      content.add(new JLabel("wartosc"));

      while (rs1.next()) {
        int id = rs1.getInt("id_samochodu");
        String marka = rs1.getString("marka");
        String model = rs1.getString("model_samochodu");
        int rocznik = rs1.getInt("rocznik");
        String silnik = rs1.getString("silnik");
        int wartosc = rs1.getInt("wartosc");
        content.add(new JLabel(Integer.valueOf(id).toString()));
        content.add(new JLabel(marka));
        content.add(new JLabel(model));
        content.add(new JLabel(silnik));
        content.add(new JLabel(Integer.valueOf(rocznik).toString()));
        content.add(new JLabel(Integer.valueOf(wartosc).toString()));
      }
      rs1.close();
      rs2.close();
      pst1.close();
      pst2.close();
    } catch (SQLException e) {
      System.out.println("Blad podczas przetwarzania danych:" + e);
    }
  }

  // Pracownik
  private void selectPracownik(PreparedStatement pst1, PreparedStatement pst2) {
    try {
      ResultSet rs1 = pst1.executeQuery();
      ResultSet rs2 = pst2.executeQuery();

      // Ilosc Wierszy
      rs2.next();
      int rows = rs2.getInt("ilosc");
      rows++;
      System.out.println(rows);

      content.setLayout(new GridLayout(rows, 6));
      content.add(new JLabel("Id"));
      content.add(new JLabel("Imie"));
      content.add(new JLabel("Nazwisko"));
      content.add(new JLabel("Staz"));
      content.add(new JLabel("Wynagrodzenie"));
      content.add(new JLabel("Stanowisko"));
      content.add(new JLabel("Dzial"));

      while (rs1.next()) {
        int id = rs1.getInt("id_pracownika");
        String imie = rs1.getString("imie");
        String nazwisko = rs1.getString("nazwisko");
        float staz = rs1.getFloat("staz");
        int wynagrodzenie = rs1.getInt("wynagrodzenie");
        String stanowisko = rs1.getString("stanowisko");
        String dzial = rs1.getString("nazwa_dzialu");
        content.add(new JLabel(Integer.valueOf(id).toString()));
        content.add(new JLabel(imie));
        content.add(new JLabel(nazwisko));
        content.add(new JLabel(Float.valueOf(staz).toString()));
        content.add(new JLabel(Integer.valueOf(wynagrodzenie).toString()));
        content.add(new JLabel("<html><body>" + stanowisko + "</body></html>"));
        content.add(new JLabel(dzial));

      }
      rs1.close();
      rs2.close();
      pst1.close();
      pst2.close();
    } catch (SQLException e) {
      System.out.println("Blad podczas przetwarzania danych:" + e);
    }
  }

  // Dzialy
  private void selectDzial(PreparedStatement pst1, PreparedStatement pst2) {
    try {
      ResultSet rs1 = pst1.executeQuery();
      ResultSet rs2 = pst2.executeQuery();

      // Ilosc Wierszy
      rs2.next();
      int rows = rs2.getInt("ilosc");
      rows++;

      content.setLayout(new GridLayout(rows, 3));
      content.add(new JLabel("Id"));
      content.add(new JLabel("Nazwa"));
      content.add(new JLabel("Ilosc etatow"));
      while (rs1.next()) {
        int id = rs1.getInt("id_dzialu");
        String nazwa = rs1.getString("nazwa_dzialu");
        int iloscEtatow = rs1.getInt("ilosc_etatow");
        content.add(new JLabel(Integer.valueOf(id).toString()));
        content.add(new JLabel(nazwa));
        content.add(new JLabel(Integer.valueOf(iloscEtatow).toString()));
      }
      rs1.close();
      rs2.close();
      pst1.close();
      pst2.close();
    } catch (SQLException e) {
      System.out.println("Blad podczas przetwarzania danych:" + e);
    }
  }

  // Ubezpieczenia_samochodu
  private void selectUbezpieczenie(PreparedStatement pst1, PreparedStatement pst2) {
    try {
      ResultSet rs1 = pst1.executeQuery();
      ResultSet rs2 = pst2.executeQuery();

      // Ilosc Wierszy
      rs2.next();
      int rows = rs2.getInt("ilosc");
      rows++;

      content.setLayout(new GridLayout(rows, 3));
      content.add(new JLabel("Id"));
      content.add(new JLabel("Rodzaj"));
      content.add(new JLabel("koszt"));

      while (rs1.next()) {
        int id = rs1.getInt("id_ubezpieczenia");
        String rodzaj = rs1.getString("rodzaj_ubezpieczenia");
        int koszt = rs1.getInt("koszt");
        content.add(new JLabel(Integer.valueOf(id).toString()));
        content.add(new JLabel(rodzaj));
        content.add(new JLabel(Integer.valueOf(koszt).toString()));
      }
      rs1.close();
      rs2.close();
      pst1.close();
      pst2.close();
    } catch (SQLException e) {
      System.out.println("Blad podczas przetwarzania danych:" + e);
    }
  }

  // Ubezpieczyciele
  private void selectUbezpieczyciel(PreparedStatement pst1, PreparedStatement pst2) {
    try {
      ResultSet rs1 = pst1.executeQuery();
      ResultSet rs2 = pst2.executeQuery();

      // Ilosc Wierszy
      rs2.next();
      int rows = rs2.getInt("ilosc");
      rows++;

      content.setLayout(new GridLayout(rows, 4));
      content.add(new JLabel("Id"));
      content.add(new JLabel("Nazwa"));
      content.add(new JLabel("Telefon"));
      content.add(new JLabel("Siedziba"));
      while (rs1.next()) {
        int id = rs1.getInt("id_ubezpieczyciela");
        String nazwa = rs1.getString("nazwa_ubezpieczyciela");
        String telefon = rs1.getString("telefon");
        String adres = rs1.getString("adres");
        content.add(new JLabel(Integer.valueOf(id).toString()));
        content.add(new JLabel(nazwa));
        content.add(new JLabel(telefon));
        content.add(new JLabel(adres));
      }
      rs1.close();
      rs2.close();
      pst1.close();
      pst2.close();
    } catch (SQLException e) {
      System.out.println("Blad podczas przetwarzania danych:" + e);
    }
  }

  // Wypozyczenie
  private void selectWypozyczenie(PreparedStatement pst1, PreparedStatement pst2) {
    try {
      ResultSet rs1 = pst1.executeQuery();
      ResultSet rs2 = pst2.executeQuery();

      // Ilosc Wierszy
      rs2.next();
      int rows = rs2.getInt("ilosc");
      rows++;

      content.setLayout(new GridLayout(rows, 7));
      content.add(new JLabel("Id"));
      content.add(new JLabel("Marka"));
      content.add(new JLabel("Model"));
      content.add(new JLabel("Imie"));
      content.add(new JLabel("Nazwisko"));
      content.add(new JLabel("Data Wypozyczenia"));
      content.add(new JLabel("Data Zwrotu"));

      while (rs1.next()) {
        int id = rs1.getInt("id_wypozyczenia");
        String marka = rs1.getString("marka");
        String model = rs1.getString("model_samochodu");
        String imie = rs1.getString("imie");
        String nazwisko = rs1.getString("nazwisko");
        String dataWy = rs1.getString("data_wypozyczenia");
        String dataZw = rs1.getString("data_zwrotu");
        if (dataZw == null)
          dataZw = "";
        content.add(new JLabel(Integer.valueOf(id).toString()));
        content.add(new JLabel(marka));
        content.add(new JLabel(model));
        content.add(new JLabel(imie));
        content.add(new JLabel(nazwisko));
        content.add(new JLabel(dataWy));
        content.add(new JLabel(dataZw));
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
      new CheckFrame();
    }
  }
}
