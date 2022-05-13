INSERT INTO einheit (id, name)
VALUES ('d20b6519-bc2d-4e9f-b3e7-9bebc995f110', 'kg');

INSERT INTO einheit (id, name)
VALUES ('b9db54bc-8c54-4163-bc25-1ed10b75edea', 'Stueck');

INSERT INTO einheit (id, name)
VALUES ('50ac5ca8-0a29-45fe-88cc-d2070fb90303', 'Liter');

INSERT INTO kategorie (id, name)
VALUES ('6abeec3f-fdc4-49b1-b64e-e005b45051cb', 'Salat');

INSERT INTO kategorie (id, name)
VALUES ('87c9cc6b-735d-4f48-badb-1d3fe39871dd', 'Kartoffeln');

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
VALUES ( '1234abcd-139e-466c-80e0-a1bcad7d9991'
       , 'Eisbergsalat'
       , true
       , 'DE'
       , 20
       , 'b9db54bc-8c54-4163-bc25-1ed10b75edea'
       , '6abeec3f-fdc4-49b1-b64e-e005b45051cb'
       , 1.5);

INSERT INTO frischbestand(id, name, verfuegbarkeit, herkunftsland, gebindegroesse, einheit_id, kategorie_id, preis)
VALUES ( '1234abcd-139e-468e-80e0-a1bcad7d9992'
       , 'Kopfsalat'
       , true
       , 'DE'
       , 12
       , 'b9db54bc-8c54-4163-bc25-1ed10b75edea'
       , '6abeec3f-fdc4-49b1-b64e-e005b45051cb'
       , 1.0);

INSERT INTO frischbestand(id, name, verfuegbarkeit, herkunftsland, gebindegroesse, einheit_id, kategorie_id, preis)
VALUES ( '1234abcd-139e-467d-80e0-a1bcad7d9993'
       , 'Romanasalat'
       , true
       , 'ES'
       , 15
       , 'b9db54bc-8c54-4163-bc25-1ed10b75edea'
       , '6abeec3f-fdc4-49b1-b64e-e005b45051cb'
       , 3.0);

INSERT INTO frischbestand(id, name, verfuegbarkeit, herkunftsland, gebindegroesse, einheit_id, kategorie_id, preis)
VALUES ( '1234abcd-139e-469f-80e0-a1bcad7d9994'
       , 'Endiviensalat'
       , true
       , 'IT'
       , 10
       , 'b9db54bc-8c54-4163-bc25-1ed10b75edea'
       , '6abeec3f-fdc4-49b1-b64e-e005b45051cb'
       , 2.5);

INSERT INTO frischbestand(id, name, verfuegbarkeit, herkunftsland, gebindegroesse, einheit_id, kategorie_id, preis)
VALUES ( '1234abcd-139e-469f-8090-a1bcad7d9995'
       , 'Eichblattsalat'
       , true
       , 'IT'
       , 18
       , 'b9db54bc-8c54-4163-bc25-1ed10b75edea'
       , '6abeec3f-fdc4-49b1-b64e-e005b45051cb'
       , 2.5);

INSERT INTO frischbestand(id, name, verfuegbarkeit, herkunftsland, gebindegroesse, einheit_id, kategorie_id, preis)
VALUES ( '2234abcd-139e-467d-80e0-a1bcad7d9993'
       , 'Kartoffel Linda'
       , true
       , 'DE'
       , 15
       , 'd20b6519-bc2d-4e9f-b3e7-9bebc995f110'
       , '87c9cc6b-735d-4f48-badb-1d3fe39871dd'
       , 3.0);

INSERT INTO frischbestand(id, name, verfuegbarkeit, herkunftsland, gebindegroesse, einheit_id, kategorie_id, preis)
VALUES ( '2234abcd-139e-467d-80e0-a1bcad7d9994'
       , 'Kartoffel Annabelle'
       , true
       , 'DE'
       , 10
       , 'd20b6519-bc2d-4e9f-b3e7-9bebc995f110'
       , '87c9cc6b-735d-4f48-badb-1d3fe39871dd'
       , 3.0);

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-469f-8090-a1bcad7c9991'
       , '1234abcd-139e-469f-8090-a1bcad7d9995'
       , 'test_admin'
       , 3
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-469f-8090-a1bcad7c9992'
       , '1234abcd-139e-469f-8090-a1bcad7d9995'
       , 'test_a'
       , 5
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-469f-8090-a1bcad7c9993'
       , '1234abcd-139e-469f-8090-a1bcad7d9995'
       , 'test_b'
       , 2
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-469f-8090-a1bcad7c9994'
       , '1234abcd-139e-469f-8090-a1bcad7d9995'
       , 'test_c'
       , 1
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-469f-8090-a1bcad7c9995'
       , '1234abcd-139e-469f-8090-a1bcad7d9995'
       , 'test_d'
       , 4
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-469f-80e0-a1bcad7c9996'
       , '1234abcd-139e-469f-80e0-a1bcad7d9994'
       , 'test_admin'
       , 1
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-469f-80e0-a1bcad7c9997'
       , '1234abcd-139e-469f-80e0-a1bcad7d9994'
       , 'test_a'
       , 5
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-469f-80e0-a1bcad7c9998'
       , '1234abcd-139e-469f-80e0-a1bcad7d9994'
       , 'test_b'
       , 2
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-469f-80e0-a1bcad7c9999'
       , '1234abcd-139e-469f-80e0-a1bcad7d9994'
       , 'test_c'
       , 1
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-469f-80e0-a1bcad7c9916'
       , '1234abcd-139e-469f-80e0-a1bcad7d9994'
       , 'test_d'
       , 2
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-467d-80e0-a1bcad7c9926'
       , '1234abcd-139e-467d-80e0-a1bcad7d9993'
       , 'test_admin'
       , 2
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-467d-80e0-a1bcad7c9936'
       , '1234abcd-139e-467d-80e0-a1bcad7d9993'
       , 'test_a'
       , 3
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-467d-80e0-a1bcad7c9946'
       , '1234abcd-139e-467d-80e0-a1bcad7d9993'
       , 'test_b'
       , 2
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-467d-80e0-a1bcad7c9956'
       , '1234abcd-139e-467d-80e0-a1bcad7d9993'
       , 'test_c'
       , 1
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-467d-80e0-a1bcad7c9966'
       , '1234abcd-139e-467d-80e0-a1bcad7d9993'
       , 'test_d'
       , 1
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-468e-80e0-a1bcad7c9976'
       , '1234abcd-139e-468e-80e0-a1bcad7d9992'
       , 'test_admin'
       , 4
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-468e-80e0-a1bcad7c9986'
       , '1234abcd-139e-468e-80e0-a1bcad7d9992'
       , 'test_a'
       , 7
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-468e-80e0-a1bcad7c9196'
       , '1234abcd-139e-468e-80e0-a1bcad7d9992'
       , 'test_b'
       , 3
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-468e-80e0-a1bcad7c9296'
       , '1234abcd-139e-468e-80e0-a1bcad7d9992'
       , 'test_c'
       , 5
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-468e-80e0-a1bcad7c9396'
       , '1234abcd-139e-468e-80e0-a1bcad7d9992'
       , 'test_d'
       , 4
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-466c-80e0-a1bcad7c9496'
       , '1234abcd-139e-466c-80e0-a1bcad7d9991'
       , 'test_admin'
       , 3
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-466c-80e0-a1bcad7c5996'
       , '1234abcd-139e-466c-80e0-a1bcad7d9991'
       , 'test_a'
       , 5
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-466c-80e0-a1bcad7c9696'
       , '1234abcd-139e-466c-80e0-a1bcad7d9991'
       , 'test_b'
       , 2
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-466c-80e0-a1bcad7c9796'
       , '1234abcd-139e-466c-80e0-a1bcad7d9991'
       , 'test_c'
       , 1
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1234abcd-139e-466c-80e0-a1bcad7c9896'
       , '1234abcd-139e-466c-80e0-a1bcad7d9991'
       , 'test_d'
       , 4
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1334abcd-139e-469f-8090-a1bcad7c9991'
       , '2234abcd-139e-467d-80e0-a1bcad7d9994'
       , 'test_admin'
       , 3
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1334abcd-139e-469f-8090-a1bcad7c9992'
       , '2234abcd-139e-467d-80e0-a1bcad7d9994'
       , 'test_a'
       , 5
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1334abcd-139e-469f-8090-a1bcad7c9993'
       , '2234abcd-139e-467d-80e0-a1bcad7d9994'
       , 'test_b'
       , 2
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1334abcd-139e-469f-8090-a1bcad7c9994'
       , '2234abcd-139e-467d-80e0-a1bcad7d9994'
       , 'test_c'
       , 1
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1334abcd-139e-469f-8090-a1bcad7c9995'
       , '2234abcd-139e-467d-80e0-a1bcad7d9994'
       , 'test_d'
       , 4
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1434abcd-139e-469f-8090-a1bcad7c9991'
       , '2234abcd-139e-467d-80e0-a1bcad7d9993'
       , 'test_admin'
       , 3.5
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1434abcd-139e-469f-8090-a1bcad7c9992'
       , '2234abcd-139e-467d-80e0-a1bcad7d9993'
       , 'test_a'
       , 5
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1434abcd-139e-469f-8090-a1bcad7c9993'
       , '2234abcd-139e-467d-80e0-a1bcad7d9993'
       , 'test_b'
       , 1.5
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1434abcd-139e-469f-8090-a1bcad7c9994'
       , '2234abcd-139e-467d-80e0-a1bcad7d9993'
       , 'test_c'
       , 1
       , '2022-05-11 11:23:45');

INSERT INTO frischbestellung(id, frischbestand_id, person_id, bestellmenge, datum)
VALUES ( '1434abcd-139e-469f-8090-a1bcad7c9995'
       , '2234abcd-139e-467d-80e0-a1bcad7d9993'
       , 'test_d'
       , 4
       , '2022-05-11 11:23:45');

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

INSERT INTO deadline(id, datum, weekday, time)
VALUES ('28qwe2wer-123f-413z-q2i9-asdfb2kl231a'
       , '2022-05-12 10:00:00'
       , 'Freitag'
       , '10:00:00');
INSERT INTO deadline(id, datum, weekday, time)
VALUES ('28qwe2wer-234g-413z-q2i9-asdfb2kl231a'
       , '2022-05-12 10:00:01'
       , 'Sonntag'
       , '10:00:00');
