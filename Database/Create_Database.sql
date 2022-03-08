CREATE SCHEMA projekt;

SET search_path TO projekt;

CREATE TABLE Samochod(
    id_samochodu int NOT NULL PRIMARY KEY,
    id_wypozyczenia int,
    marka varchar(40) NOT NULL,
    model_samochodu varchar(40) NOT NULL, 
    rocznik int NOT NULL,
    silnik varchar(50) NOT NULL,
    wartosc int NOT NULL
);

CREATE TABLE Ubezpieczenia_samochodu(
    id_ubezpieczenia int NOT NULL PRIMARY KEY,
    rodzaj_ubezpieczenia varchar(60) NOT NULL,
    koszt int NOT NULL
);

CREATE TABLE Ubezpieczyciel(
    id_ubezpieczyciela int NOT NULL PRIMARY KEY,
    nazwa_ubezpieczyciela varchar(40) NOT NULL,
    telefon varchar(15) NOT NULL,
    adres varchar(60) NOT NULL
);

CREATE TABLE Wypozyczenie(
    id_wypozyczenia int NOT NULL PRIMARY KEY,
    id_klienta int NOT NULL,
    id_pracownika int NOT NULL,
    data_wypozyczenia date NOT NULL,
    data_zwrotu date
);

CREATE TABLE Klient(
    id_klienta int NOT NULL PRIMARY KEY,
    telefon varchar(15) NOT NULL,
    imie varchar(40) NOT NULL,
    nazwisko varchar(40) NOT NULL
);

CREATE TABLE Pracownik(
    id_pracownika int NOT NULL PRIMARY KEY,
    id_dzialu int NOT NULL,
    imie varchar(40) NOT NULL,
    nazwisko varchar(40) NOT NULL,
    staz real NOT NULL,
    wynagrodzenie real NOT NULL,
    stanowisko varchar(40) NOT NULL
);

CREATE TABLE Dzial(
    id_dzialu int NOT NULL PRIMARY KEY,
    nazwa_dzialu varchar(60) NOT NULL,
    ilosc_etatow int NOT NULL
);

CREATE TABLE Ubezpieczyciel_Asoc(
    id_samochodu int NOT NULL,
    id_ubezpieczyciela int NOT NULL
);

CREATE TABLE Asoc_ubezpieczenie(
    id_samochodu int NOT NULL,
    id_ubezpieczenia int NOT NULL    
);

--Relacja Ubezpieczenie - Asoc_ubezpieczenie
ALTER TABLE Asoc_ubezpieczenie ADD FOREIGN KEY (id_ubezpieczenia) REFERENCES Ubezpieczenia_samochodu (id_ubezpieczenia);

--Relacja samochód - Asoc_ubezpieczenie
ALTER TABLE Asoc_ubezpieczenie ADD FOREIGN KEY (id_samochodu) REFERENCES Samochod (id_samochodu);

--Relacja Samochód - Ubezpieczyciel_Asoc
ALTER TABLE Ubezpieczyciel_Asoc ADD FOREIGN KEY (id_samochodu) REFERENCES Samochod (id_samochodu);

--Relacja Ubezpieczyciel - Ubezpieczyciel_Asoc
ALTER TABLE Ubezpieczyciel_Asoc ADD FOREIGN KEY (id_ubezpieczyciela) REFERENCES Ubezpieczyciel (id_ubezpieczyciela);

--Relacja Dział - Pracownik
ALTER TABLE Pracownik ADD FOREIGN KEY (id_dzialu) REFERENCES Dzial (id_dzialu);

--Relacja Wypożyczenie - Pracownik
ALTER TABLE Wypozyczenie ADD FOREIGN KEY (id_pracownika) REFERENCES Pracownik (id_pracownika);

--Relacje Klient - Wypożyczenie
ALTER TABLE Wypozyczenie ADD FOREIGN KEY (id_klienta) REFERENCES Klient (id_klienta);

--Relacja Wypożyczenie - Samochód
ALTER TABLE Samochod ADD FOREIGN KEY (id_wypozyczenia) REFERENCES Wypozyczenie (id_wypozyczenia);

