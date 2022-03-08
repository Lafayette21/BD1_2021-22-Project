SET search_path TO projekt;

--Raport: Sumaryczny koszt ubezpieczeń dla poszczególnych samochodów
CREATE VIEW koszt_ubezpieczen AS 
SELECT id_samochodu,marka, model_samochodu, SUM(koszt) FROM Samochod
JOIN Asoc_Ubezpieczenie USING(id_samochodu)
JOIN Ubezpieczenia_samochodu USING(id_ubezpieczenia)
GROUP BY id_samochodu, marka, model_samochodu ORDER BY id_samochodu; 


--Raport: Wypożyczone i jeszcze nie oddane samochody oraz kto je wypożyczył
CREATE VIEW niedostepne AS
SELECT marka, model_samochodu, imie, nazwisko, telefon FROM Samochod
JOIN Wypozyczenie USING(id_wypozyczenia)
JOIN Klient USING(id_klienta)
WHERE data_zwrotu IS NULL;

--Raport: Dostepne samochody oraz kto je wypożyczał
CREATE VIEW dostepne AS
SELECT marka, model_samochodu, imie, nazwisko, telefon FROM Samochod
JOIN Wypozyczenie USING(id_wypozyczenia)
JOIN Klient USING(id_klienta)
WHERE data_zwrotu IS NOT NULL;

--Raport: Ilość przeprowadzonych wypożyczeń przez danych pracowników posortowana malejaco
CREATE VIEW wyniki_pracownikow AS
SELECT imie,nazwisko, COUNT(Wypozyczenie.id_pracownika) AS ilosc_umow FROM Pracownik
JOIN Wypozyczenie USING(id_pracownika) GROUP BY imie, nazwisko ORDER BY ilosc_umow DESC;

--Widok pomocniczy potrzebny aby uzyskac ilosc wierszy
CREATE VIEW pomoc AS 
SELECT id_wypozyczenia,marka,model_samochodu, imie, nazwisko, data_wypozyczenia, data_zwrotu FROM Wypozyczenie JOIN Klient USING(id_klienta) JOIN Samochod USING(id_wypozyczenia);