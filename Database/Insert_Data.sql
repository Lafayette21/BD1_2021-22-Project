SET search_path TO projekt;

INSERT INTO Ubezpieczenia_samochodu (id_ubezpieczenia,rodzaj_ubezpieczenia,koszt) values
(1,'OC',500),
(2,'AC',700),
(3,'Asistance',300),
(4,'NNW',400);

--Dzial
INSERT INTO Dzial (id_dzialu, nazwa_dzialu, ilosc_etatow) values
(1, 'Obslugi Klienta', 6),
(2, 'Sprzatajacy', 4),
(3, 'Marketing', 4),
(4, 'Zarzadzanie',2),
(5, 'Analiza Danych',4),
(6, 'Kontrola Jakosci',3);

--Ubezpieczyciel
INSERT INTO Ubezpieczyciel (id_ubezpieczyciela, nazwa_ubezpieczyciela, telefon, adres) values
(1, 'AXA Direct', '234-667-456', 'Poznan'),
(2, 'Link4', '435-847-965', 'Warszawa'),
(3, 'Aviva Direct', '946-555-086','Krakow'),
(4, 'PZU', '345-754-897','Warszawa'),
(5, 'Warta', '778-345-765', 'Poznan'),
(6, 'Proama', '566-332-619','Warszawa'),
(7, 'ALLIANZ Direct', '777-346-909', 'Gdansk'),
(8, 'Gothaer','334-756-899','Wroclaw');

--Klient
INSERT INTO Klient (id_klienta,telefon,imie,nazwisko) values
(1, '345-667-999', 'Piotr', 'Tusin'),
(2, '556-567-223', 'Marek','Urban'),
(3, '345-768-234', 'Tomasz','Problem'),
(4, '765-234-788', 'Roman','Zupa'),
(5, '234-667-875', 'Kordian','Gwiazdowski'),
(6, '556-334-234', 'Zbigniew','Bialek'),
(7, '234-876-543', 'Andrzej','Biedny'),
(8, '556-655-786', 'Aleksander', 'Pila'),
(9, '234-567-345', 'Tymoteusz','Wrzutka'),
(10, '667-334-889', 'Michal','Imigrant'),
(11, '333-567-905', 'Jeremiasz','Smietana'),
(12, '111-598-345', 'Antek','Szwagier'),
(13, '324-564-230', 'Marzanna','Topielec'),
(14, '542-725-219', 'Michalina','Mickiewicz-Slowacka'),
(15, '569-345-975', 'Karol','Majster');

--Pracownik
INSERT INTO Pracownik (id_pracownika,id_dzialu,imie,nazwisko,staz,wynagrodzenie,stanowisko) values
(1,1,'Michal','Wojtasik',5,5000,'Doradca klienta'),
(2,1,'Mateusz','Gotowka',0.5,1500,'Stazysta'),
(3,1,'Anna','Zaradna',10,7500,'Kierownik'),
(4,1,'Adam','Obecny',7,5000,'Doradca Klienta'),
(5,1,'Patrycja','Hurkacz',3,5000,'Doradca Klienta'),
(6,1,'Grzegorz','Brzeczyszczykiewicz',6,6300,'Zastepca Kierownika'),
(7,2,'Dorota','Kalinowska',10,6000,'Kierownik'),
(8,2,'Roman','Torba',3,3000,'Konserwator powieszni plaskich'),
(9,2,'Anna','Szyc',5,3000,'Konserwator powieszni plaskich'),
(10,2,'Pola','Nowak',2,3000,'Konserwator powieszni plaskich'),
(11,3,'Roman','Michal',10,7500,'Kierownik'),
(12,3,'Marta','Kowalska',7,5000,'Analityk Rynku'),
(13,3,'Jan','Glowacki',3,4000,'Markter'),
(14,3,'Katarzyna','Polak',5,4000,'Copywriter'),
(15,4,'Joanna','Rybak',7,6000,'Acount manager'),
(16,4,'Tomasz','Powaga',4,4000,'Area manager'),
(17,5,'Adrian','Tomasz',10,7000,'Senior'),
(18,5,'Konrad','Mazowiecki',8,7000,'Senior'),
(19,5,'Maciej','Kowalczyk',2,3700,'Junior'),
(20,5,'Damian','Brzoza',0.5,2500,'Stazysta'),
(21,6,'Robert','Janowski',12,8000,'Inzynier'),
(22,6,'Oktaw','Nowomiejski',5,5000,'Doradca techniczny'),
(23,6,'Jan','Niezbedny',10,6000,'Audytor');

INSERT INTO Wypozyczenie (id_wypozyczenia,id_klienta,id_pracownika,data_wypozyczenia,data_zwrotu) values
(1,15,1,'2021-12-18',NULL),
(2,13,1,'2020-02-28','2020-09-15'),
(3,5,4,'2020-04-20','2020-11-20'),
(4,7,5,'2019-01-30','2021-01-25'),
(5,2,3,'2020-09-25',NULL),
(6,1,3,'2020-03-22','2020-12-20'),
(7,6,2,'2021-06-14',NULL),
(8,12,2,'2022-01-14',NULL),
(9,10,3,'2021-03-01','2022-01-20'),
(10,9,5,'2020-05-30','2022-01-5'),
(11,4,4,'2021-03-24','2021-11-03');

INSERT INTO Samochod (id_samochodu,id_wypozyczenia,marka,model_samochodu,rocznik,silnik,wartosc) values
(1,1,'Opel','Astra','2007','1.7',12000),
(2,1,'Skoda','Fabia','2009','1.6',19000),
(3,2,'Audi','A6','2021','2.0',300000),
(4,3,'Volvo','XC 60 D4','2015','2.0',88000),
(5,3,'Peugeot','208','2017','1.4',73000),
(6,4,'Renault','Megane','2019','1.6',75000),
(7,4,'Dacia','Sandero','2014','1.0',44000),
(8,5,'Toyota','RAV4','2013','2.0',64000),
(9,6,'Citroen','Berlingo Multispace','2015','1.6',54000),
(10,7,'Fiat','Tipo','2017','1.6',54000),
(11,8,'Ford','Mondeo','2014','2.0',49000),
(12,9,'Hyundai','I40','2017','1.7',80000),
(13,10,'Nissan','Qashqai','2016','1.8',85000),
(14,11,'Mitsubishi','Lancer','2011','2.0',70000);

INSERT INTO Ubezpieczyciel_Asoc (id_samochodu,id_ubezpieczyciela) values
(1,1),
(1,2),
(2,3),
(3,8),
(3,7),
(4,6),
(5,5),
(6,4),
(7,2),
(8,3),
(9,8),
(10,7),
(10,3),
(11,5),
(12,5),
(13,4),
(14,3);

INSERT INTO Asoc_ubezpieczenie (id_samochodu,id_ubezpieczenia) values 
(1,1),
(1,2),
(1,3),
(2,1),
(2,2),
(2,4),
(3,1),
(3,4),
(5,1),
(6,1),
(6,3),
(7,1),
(7,2),
(8,1),
(9,1),
(10,1),
(10,2),
(10,4),
(11,1),
(11,2),
(12,1),
(13,2),
(13,3),
(13,4),
(14,1),
(14,2);


