--Poprawianie nazwiska i imienia
CREATE OR REPLACE FUNCTION ludzie_normalizacja()
RETURNS TRIGGER AS
$$
    DECLARE help1 varchar(50);
    DECLARE help2 varchar(50);
    DECLARE h1 varchar(50);
    DECLARE h2 varchar(50);
    DECLARE pos integer;
    BEGIN
        -- imie --
        IF NEW.imie ~ '^[A-Za-z]+$' AND NEW.nazwisko ~ '[A-Za-z/\-/]+$' THEN
            RAISE NOTICE 'Poprawne dane';
            NEW.imie=CONCAT(UPPER(substring(NEW.imie,0,2)),LOWER(substring(NEW.imie,2)));
            -- nazwisko --
            IF STRPOS(NEW.nazwisko,' ') IS NOT NULL THEN
                help1=SPLIT_PART(NEW.nazwisko,' ',1);
                help2=SPLIT_PART(NEW.nazwisko,' ',2);
                h1=CONCAT(UPPER(substring(help1,0,2)),LOWER(substring(help1,2)));
                h2=CONCAT(UPPER(substring(help2,0,2)),LOWER(substring(help2,2)));
                NEW.nazwisko=CONCAT(h1,' ',h2);
                RETURN NEW;
            ELSE
                NEW.imie=CONCAT(UPPER(substring(NEW.nazwisko,0,2)),LOWER(substring(NEW.nazwisko,2)));
                RETURN NEW;
            END IF;
        ELSE
            RAISE EXCEPTION 'Niepoprawne dane';
        END IF;
    END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER klient_normalizuj
    BEFORE INSERT OR UPDATE ON klient
    FOR EACH ROW
    EXECUTE PROCEDURE ludzie_normalizacja();

CREATE TRIGGER pracownik_normalizuj
    BEFORE INSERT OR UPDATE ON Pracownik
    FOR EACH ROW
    EXECUTE PROCEDURE ludzie_normalizacja();

--stanowisko
CREATE OR REPLACE FUNCTION stanowisko_normalizacja()
RETURNS TRIGGER AS
$$
    BEGIN
        IF NEW.stanowisko ~ '[A-Za-z/\-/]+$' THEN
            RAISE NOTICE 'Poprawne dane';
            NEW.stanowisko=CONCAT(UPPER(substring(NEW.stanowisko,0,2)),LOWER(substring(NEW.stanowisko,2)));
            RETURN NEW;
        ELSE
            RAISE EXCEPTION 'Niepoprawne dane';
        END IF;
    END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER stanowisko_normalizuj
    BEFORE INSERT OR UPDATE ON Pracownik
    FOR EACH ROW
    EXECUTE PROCEDURE stanowisko_normalizacja();

--dzial
CREATE OR REPLACE FUNCTION dzial_normalizacja()
RETURNS TRIGGER AS
$$
    BEGIN
        IF NEW.nazwa_dzialu ~ '[A-Za-z/\-/]+$' THEN
            RAISE NOTICE 'Poprawne dane';
            NEW.nazwa_dzialu=CONCAT(UPPER(substring(NEW.nazwa_dzialu,0,2)),LOWER(substring(NEW.nazwa_dzialu,2)));
            RETURN NEW;
        ELSE
            RAISE EXCEPTION 'Niepoprawne dane';
        END IF;
    END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER dzial_normalizuj
    BEFORE INSERT OR UPDATE ON dzial
    FOR EACH ROW
    EXECUTE PROCEDURE dzial_normalizacja();

--telefon
CREATE OR REPLACE FUNCTION telefon_normalizacja()
RETURNS TRIGGER AS
$$
    BEGIN
        IF NEW.telefon ~ '[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9]' THEN
            RAISE NOTICE 'poprawe dane';
            RETURN NEW;
        ELSE
            RAISE EXCEPTION 'Niepoprawne dane';
        END IF;
    END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER telefon_klient_validate
    BEFORE INSERT OR UPDATE ON klient
    FOR EACH ROW
    EXECUTE PROCEDURE telefon_normalizacja();

CREATE TRIGGER telefon_ubezpieczyciel_validate
    BEFORE INSERT OR UPDATE ON Ubezpieczyciel
    FOR EACH ROW
    EXECUTE PROCEDURE telefon_normalizacja();