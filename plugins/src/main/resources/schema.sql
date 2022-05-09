USE foodcoop;
DROP DATABASE foodcoop;

CREATE DATABASE foodcoop;
USE foodcoop;

CREATE TABLE einheit(
                            id VARCHAR(50) PRIMARY KEY,
                            name VARCHAR(50) NOT NULL
);

CREATE TABLE kategorie(
                            id VARCHAR(50) PRIMARY KEY,
                            name VARCHAR(50) NOT NULL
);

CREATE TABLE lagerprodukt(
                             id VARCHAR(50) PRIMARY KEY,
                             name VARCHAR(50) NOT NULL,
                             kategorie_id VARCHAR(50) references kategorie(id),
                             einheit_id VARCHAR(50) references einheit(id),
                             ist_lagerbestand VARCHAR(50) NOT NULL,
                             soll_lagerbestand VARCHAR(50) NOT NULL
);

CREATE TABLE brotbestand(
                            id VARCHAR(50) PRIMARY KEY,
                            name VARCHAR(50) NOT NULL,
                            verfuegbarkeit BOOLEAN NOT NULL,
                            gewicht INT NOT NULL,
                            preis FLOAT NOT NULL
);

CREATE TABLE brotbestellung(
                            id VARCHAR(50) NOT NULL,
                            brotbestand_id VARCHAR(50) references brotbestand(id),
                            person_id VARCHAR(50) NOT NULL,
                            bestellmenge FLOAT NOT NULL,
                            datum TIMESTAMP NOT NULL,
                            PRIMARY KEY(brotbestand_id, person_id, datum)
);

CREATE TABLE frischbestand(
                            id VARCHAR(50) PRIMARY KEY,
                            name VARCHAR(50) NOT NULL,
                            verfuegbarkeit BOOLEAN NOT NULL,
                            herkunftsland VARCHAR(50) NOT NULL,
                            gebindegroesse INT NOT NULL,
                            einheit_id VARCHAR(50) references einheit(id),
                            kategorie_id VARCHAR(50) references kategorie(id),
                            preis FLOAT NOT NULL
);

CREATE TABLE frischbestellung(
                            id VARCHAR(50) NOT NULL,
                            frischbestand_id VARCHAR(50) references frischbestand(id),
                            person_id VARCHAR(50) NOT NULL,
                            bestellmenge FLOAT NOT NULL,
                            datum TIMESTAMP NOT NULL,
                            PRIMARY KEY(frischbestand_id, person_id, datum)
);

CREATE TABLE deadline(
                            id VARCHAR(50) PRIMARY KEY,
                            datum TIMESTAMP NOT NULL,
                            time TIME NOT NULL,
                            weekday VARCHAR(50) NOT NULL
);
