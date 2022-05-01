INSERT INTO einheit (id, name)
VALUES ('d20b6519-bc2d-4e9f-b3e7-9bebc995f110', 'kg');

INSERT INTO einheit (id, name)
VALUES ('b9db54bc-8c54-4163-bc25-1ed10b75edea', 'Stueck');

INSERT INTO einheit (id, name)
VALUES ('50ac5ca8-0a29-45fe-88cc-d2070fb90303', 'Liter');

INSERT INTO kategorie (id, name)
VALUES ('6abeec3f-fdc4-49b1-b64e-e005b45051cb', 'Gemuese');

INSERT INTO kategorie (id, name)
VALUES ('87c9cc6b-735d-4f48-badb-1d3fe39871dd', 'Teigwaren');

INSERT INTO lagerprodukt (id, name, kategorie_id, einheit_id, ist_lagerbestand, soll_lagerbestand)
VALUES ( '30724b6f-01ec-4c47-aba7-1a5f2bc5f833'
       , 'Honig'
       , '6abeec3f-fdc4-49b1-b64e-e005b45051cb'
       , 'd20b6519-bc2d-4e9f-b3e7-9bebc995f110'
       , 2.3
       , 3.5);

INSERT INTO lagerprodukt (id, name, kategorie_id, einheit_id, ist_lagerbestand, soll_lagerbestand)
VALUES ( 'ed149481-71cf-4fc3-8b21-6cb763fa6848'
       , 'Reis'
       , '6abeec3f-fdc4-49b1-b64e-e005b45051cb'
       , 'b9db54bc-8c54-4163-bc25-1ed10b75edea'
       , 3
       , 5);

INSERT INTO lagerprodukt (id, name, kategorie_id, einheit_id, ist_lagerbestand, soll_lagerbestand)
VALUES ( 'ed149481-71cf-4f27-8b33-6cb763fa6848'
       , 'Kekse'
       , '6abeec3f-fdc4-49b1-b64e-e005b45051cb'
       , 'b9db54bc-8c54-4163-bc25-1ed10b75edea'
       , 0
       , 0);

INSERT INTO lagerprodukt (id, name, kategorie_id, einheit_id, ist_lagerbestand, soll_lagerbestand)
VALUES ( '35186fbe-6f46-4824-8d91-c80b7ad50f5d'
       , 'Spaghetti'
       , '87c9cc6b-735d-4f48-badb-1d3fe39871dd'
       , 'b9db54bc-8c54-4163-bc25-1ed10b75edea'
       , 3.5
       , 9);

INSERT INTO frischbestand(id, name, verfuegbarkeit, herkunftsland, gebindegroesse, einheit_id, kategorie_id, preis)
VALUES ( '1234abcd-139e-466c-80e0-a1bcad7c9996'
       , 'Lauch'
       , true
       , 'DE'
       , 20
       , 'b9db54bc-8c54-4163-bc25-1ed10b75edea'
       , '6abeec3f-fdc4-49b1-b64e-e005b45051cb'
       , 1.5);

INSERT INTO frischbestand(id, name, verfuegbarkeit, herkunftsland, gebindegroesse, einheit_id, kategorie_id, preis)
VALUES ( '1234abcd-139e-468e-80e0-a1bcad7c9996'
       , 'Blumenkohl'
       , true
       , 'DE'
       , 12
       , 'b9db54bc-8c54-4163-bc25-1ed10b75edea'
       , '6abeec3f-fdc4-49b1-b64e-e005b45051cb'
       , 1.0);

INSERT INTO frischbestand(id, name, verfuegbarkeit, herkunftsland, gebindegroesse, einheit_id, kategorie_id, preis)
VALUES ( '1234abcd-139e-467d-80e0-a1bcad7c9996'
       , 'Paprika'
       , true
       , 'ES'
       , 20
       , 'd20b6519-bc2d-4e9f-b3e7-9bebc995f110'
       , '6abeec3f-fdc4-49b1-b64e-e005b45051cb'
       , 3.0);

INSERT INTO frischbestand(id, name, verfuegbarkeit, herkunftsland, gebindegroesse, einheit_id, kategorie_id, preis)
VALUES ( '1234abcd-139e-469f-80e0-a1bcad7c9996'
       , 'Tomate'
       , false
       , 'IT'
       , 10
       , 'd20b6519-bc2d-4e9f-b3e7-9bebc995f110'
       , '6abeec3f-fdc4-49b1-b64e-e005b45051cb'
       , 2.5);

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '2049vbow-138e-467d-80e0-r3slwp9d2390'
       , '1234abcd-139e-467d-80e0-a1bcad7c9996'
       , 'test_admin'
       , 3
       , '2022-04-12 00:00:00');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '2049vbow-139e-467d-80e0-r3slwp9d2390'
       , '1234abcd-139e-468e-80e0-a1bcad7c9996'
       , 'test_admin'
       , 5
       , '2022-04-12 00:00:00');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '2049vbow-140e-467d-80e0-r3slwp9d2390'
       , '1234abcd-139e-466c-80e0-a1bcad7c9996'
       , 'test_admin'
       , 4
       , '2022-04-12 00:00:00');

INSERT INTO brotbestand(id, name, verfuegbarkeit, gewicht, preis)
VALUES ( '2370sfng-140e-467d-80e0-r3slwp9d2390'
       , 'Roggenbrot'
       , true
       , 500
       , 2.5);

INSERT INTO brotbestand(id, name, verfuegbarkeit, gewicht, preis)
VALUES ( '2370sfng-140e-468o-80e0-r3slwp9d2390'
       , 'Mischbrot'
       , true
       , 750
       , 3.0);

INSERT INTO brotbestand(id, name, verfuegbarkeit, gewicht, preis)
VALUES ( '2370sfng-140e-490z-80e0-r3slwp9d2390'
       , 'Bauernbrot'
       , false
       , 1000
       , 4.0);

INSERT INTO brotbestellung(id, brotbestand_id, person_id, bestellmenge, datum)
VALUES ( '28390wer-12af-490z-80e0-as234jkl231a'
       , '2370sfng-140e-467d-80e0-r3slwp9d2390'
       , '11589rqw-139e-466c-80e0-a1bcad7c9996'
       , 5
       , '2022-03-08 00:00:00');

INSERT INTO brotbestellung(id, brotbestand_id, person_id, bestellmenge, datum)
VALUES ( '28390wer-12af-490z-81u8-as234jkl231a'
       , '2370sfng-140e-468o-80e0-r3slwp9d2390'
       , '11589rqw-189p-466c-80e0-a1bcad7c9996'
       , 2
       , '2022-03-08 00:00:00');

INSERT INTO brotbestellung(id, brotbestand_id, person_id, bestellmenge, datum)
VALUES ( '28390wer-12af-490z-82i9-as234jkl231a'
       , '2370sfng-140e-490z-80e0-r3slwp9d2390'
       , '11589rqw-124q-466c-80e0-a1bcad7c9996'
       , 3
       , '2022-03-08 00:00:00');
       
INSERT INTO deadline(id, datum)
VALUES ('28qwe2wer-123f-413z-q2i9-asdfb2kl231a'
        , '2022-05-06 10:00:00');
