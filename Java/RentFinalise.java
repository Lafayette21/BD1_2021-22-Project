import java.sql.*;

import java.util.Vector;

public class RentFinalise {
    // Fields
    private int idClient;
    private int idWorker;
    private Vector<Integer> cars;

    public RentFinalise(int idC, int idP, Vector<Integer> vect) {
        idClient = idC;
        idWorker = idP;
        cars = vect;
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
                PreparedStatement pst = conn.prepareStatement("SELECT COUNT(*) AS ilosc FROM wypozyczenie");
                ResultSet rs = pst.executeQuery();

                Date dataWypozyczenia = new Date(System.currentTimeMillis());

                rs.next();
                int rows = rs.getInt("ilosc");
                rows++;
                String sql = "INSERT INTO Wypozyczenie (id_wypozyczenia, id_klienta,id_pracownika,data_wypozyczenia,data_zwrotu) values ("
                        + rows + "," + idClient + "," + idWorker + ",'" + dataWypozyczenia + "',null)";
                System.out.println(sql);
                Statement myStmt1 = conn.createStatement();
                myStmt1.executeUpdate(sql);

                // Update samochodow
                for (Integer i : cars) {
                    String command = "UPDATE samochod SET id_wypozyczenia=" + rows + " WHERE id_samochodu=" + i;
                    System.out.println(command);
                    Statement myStmt2 = conn.createStatement();
                    myStmt2.executeUpdate(command);
                }
                new AddResultFrame("Operacja sie powiodla");

            } catch (SQLException e) {
                System.out.println("Blad podczas przetwarzania danych:" + e);
                new AddResultFrame("Operacja sie nie powiodla");
            }
        }

    }

}