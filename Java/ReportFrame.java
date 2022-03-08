import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import javax.swing.*;

public class ReportFrame extends JFrame implements ActionListener {
    // Panel
    JPanel header;
    JPanel content;
    // Przyciski
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton buttonReturn;
    // Etykiety z tekstem
    private JLabel headerLabel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;

    public ReportFrame() {
        // header Panel ustawienia
        header = new JPanel();
        header.setBounds(0, 0, 800, 150);
        header.setBackground(new Color(12, 40, 64));
        header.setLayout(new BorderLayout());
        // Ramka do nagłówla
        headerLabel = new JLabel();
        headerLabel.setText("Raporty dotyczące pewnych dziedzin firmy");
        headerLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        headerLabel.setForeground(Color.white);
        headerLabel.setVerticalAlignment(JLabel.CENTER);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);

        // content Panel ustawienia
        content = new JPanel();
        content.setBounds(0, 150, 816, 350);
        content.setBackground(new Color(9, 115, 104));
        content.setLayout(null);
        // Label ustawienia
        label1 = new JLabel();
        label1.setText(
                "<html><body><center>Wypożyczone i jeszcze nie oddane samochody oraz kto je wypożyczył</center></body></html>");
        label1.setFont(new Font("Verdana", Font.BOLD, 15));
        label1.setForeground(Color.black);
        label1.setVerticalAlignment(JLabel.CENTER);
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setBounds(0, 0, 200, 120);
        label1.setBorder(BorderFactory.createLineBorder(Color.black));

        label2 = new JLabel();
        label2.setText("<html><body><center>Dostepne samochody oraz kto je wypożyczał</center></body></html>");
        label2.setFont(new Font("Verdana", Font.BOLD, 15));
        label2.setForeground(Color.black);
        label2.setVerticalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setBounds(200, 0, 200, 120);
        label2.setBorder(BorderFactory.createLineBorder(Color.black));

        label3 = new JLabel();
        label3.setText(
                "<html><body><center>Ilość przeprowadzonych wypożyczeń przez danych pracowników</center></body></html>");
        label3.setFont(new Font("Verdana", Font.BOLD, 15));
        label3.setForeground(Color.black);
        label3.setVerticalAlignment(JLabel.CENTER);
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setBounds(400, 0, 200, 120);
        label3.setBorder(BorderFactory.createLineBorder(Color.black));

        label4 = new JLabel();
        label4.setText(
                "<html><body><center>Sumaryczny koszt ubezpieczeń dla poszczególnych samochodów</center></body></html>");
        label4.setFont(new Font("Verdana", Font.BOLD, 15));
        label4.setForeground(Color.black);
        label4.setVerticalAlignment(JLabel.CENTER);
        label4.setHorizontalAlignment(JLabel.CENTER);
        label4.setBounds(600, 0, 200, 120);
        label4.setBorder(BorderFactory.createLineBorder(Color.black));
        // Przyciski ustawienia
        button1 = new JButton("Sprawdz");
        button1.addActionListener(this);
        button1.setBounds(50, 145, 100, 50);

        button2 = new JButton("Sprawdz");
        button2.addActionListener(this);
        button2.setBounds(250, 145, 100, 50);

        button3 = new JButton("Sprawdz");
        button3.addActionListener(this);
        button3.setBounds(450, 145, 100, 50);

        button4 = new JButton("Sprawdz");
        button4.addActionListener(this);
        button4.setBounds(650, 145, 100, 50);
        // Przycisk powrót
        buttonReturn = new JButton("Powrot");
        buttonReturn.addActionListener(this);
        buttonReturn.setBounds(350, 230, 100, 40);

        // Ramka ustawienia
        this.setTitle("Raporty");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(816, 500);

        this.setResizable(false);
        // Dodanie Elementu do header
        header.add(headerLabel);
        // Dodawanie Elementów do Siatki w content
        content.add(label1);
        content.add(label2);
        content.add(label3);
        content.add(label4);
        content.add(button1);
        content.add(button2);
        content.add(button3);
        content.add(button4);
        content.add(buttonReturn);
        // Dodawanie elementów na Ramkę
        this.add(header);
        this.add(content);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Wypozyczone
        if (e.getSource() == button1) {
            this.dispose();
            new ReportSQL("Wypozyczone");
        } // Dostepne
        else if (e.getSource() == button2) {
            this.dispose();
            new ReportSQL("Dostepne");
        } // Wyniki pracownikow
        else if (e.getSource() == button3) {
            this.dispose();
            new ReportSQL("Wyniki Pracownikow");
        } else if (e.getSource() == button4) {
            this.dispose();
            new ReportSQL("Koszty ubezpieczen");
        } else if (e.getSource() == buttonReturn) {
            this.dispose();
            new GUI();
        }
    }
}
