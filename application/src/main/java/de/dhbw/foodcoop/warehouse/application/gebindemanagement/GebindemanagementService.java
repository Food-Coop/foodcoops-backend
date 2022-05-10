package de.dhbw.foodcoop.warehouse.application.gebindemanagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;

public class GebindemanagementService {
    
    public String vorschlagBerechnen(List<FrischBestellung> bestellung, int i) {
        //Wenn es keine Gebindegroesse gibt
        String vorschlag = Double.toString(bestellung.get(i).getBestellmenge());
        if(bestellung.get(i).getBestellmenge() == bestellung.get(i).getFrischbestand().getGebindegroesse()){
            vorschlag = Double.toString(bestellung.get(i).getBestellmenge());
        }
        //Wenn es eine Gebindegroesse gibt
        //Bestellmenge insgesamt zu klein
            if(bestellung.get(i).getBestellmenge() < 5 && bestellung.get(i).getFrischbestand().getGebindegroesse() != 0){
                vorschlag = "0";
            }
        //Bestellmenge groß genug, um aufzurunden
            else if(bestellung.get(i).getBestellmenge() > bestellung.get(i).getFrischbestand().getGebindegroesse() - 5){
                vorschlag = Integer.toString(bestellung.get(i).getFrischbestand().getGebindegroesse());
            }
        //Fall dazwischen abdecken
            // else{
                // String kategorie = bestellung.get(i).getFrischbestand().getKategorie().getName();
                // for(int j = 0; j < bestellung.size(); j++){
                //     if(bestellung.get(j).getFrischbestand().getKategorie().getName() == kategorie){
                //         vorschlag += bestellung.get(j).getBestellmenge();
                //     }
                // }   
            //}
        return vorschlag;
    }

    public List<List<FrischBestellung>> splitBestellungList(List<FrischBestellung> bestellList){
        List<List<FrischBestellung>> bestellungListKategorie = new ArrayList<List<FrischBestellung>>();
//        int length = bestellList.size();
        List<FrischBestellung> kategorie = new ArrayList<FrischBestellung>();
        kategorie.add(bestellList.get(0));
//        double [][] kategorie = new double [length][5];

//        int arraysize = 1;
        for(int i = 1; i < bestellList.size(); i++){
            if(bestellList.get(i).getFrischbestand().getKategorie().getName() == bestellList.get(i-1).getFrischbestand().getKategorie().getName()){
//                arraysize++;

                kategorie.add(bestellList.get(i));
            }
            else{
//                arraysize = 1;
                bestellungListKategorie.add(kategorie);
                for(int j = 0; j < kategorie.size(); j++){
                    System.out.println(kategorie.get(j).getFrischbestand().getName());
                }
                kategorie = new ArrayList<FrischBestellung>();
                kategorie.add(bestellList.get(i));
            }
        }

//        for(int i = 1; i < bestellList.size(); i++){
//            if(bestellList.get(i).getFrischbestand().getKategorie().getName() == bestellList.get(i-1).getFrischbestand().getKategorie().getName()){
//                kategorie.add(bestellList.get(i));
//            }
//            else{
//                bestellungListKategorie.add(kategorie);
//                for(int j = 0; j < kategorie.size(); j++){
//                    System.out.println(kategorie.get(j).getFrischbestand().getName());
//                }
//                kategorie.clear();
//            }
//        }
        bestellungListKategorie.add(kategorie);
        

        System.out.println("[");
  
        for (List<FrischBestellung> list : bestellungListKategorie) {
            System.out.print("  [");
  
            for (FrischBestellung item : list) {
                System.out.print("  "
                                 + item.getFrischbestand().getName()
                                 + ", ");
            }
            System.out.println("], ");
        }
        System.out.println("]");
        
        return bestellungListKategorie;
    }

    public double[][] VorschlagBerechnen(List<FrischBestellung> kategorie) {
        double[][] liste = new double[kategorie.size()][5];
        double gesamt = 0;
        for (int i = 0; i < kategorie.size(); i++){
            FrischBestellung bestellung = kategorie.get(i);
            liste[i][0] = bestellung.getBestellmenge();
            liste[i][1] = bestellung.getFrischbestand().getGebindegroesse();
            liste[i][2] = 0;
            liste[i][3] = 0;
            liste[i][4] = i;
        }

        //liste[i][0] = Menge
        //liste[i][1] = Gebindegröße
        //liste[i][2] = fehlende menge pro gebinde
        //liste[i][3] = anzahl der gebinde die bestellt werden können
        //liste[i][4] = index
        //gesamt = anzahl aller übrigen nicht im gebinde eingebundenen

        for (int i = 0; i < liste.length; i++){
            // ist ein gebinde voll? ############Noch schauen ob es mehrere volle Gebinde gibnt##########
            if((liste[i][1] - liste[i][0]) <= 0){
                //Menge zieh ich gebindegröße ab
                liste[i][0] -= liste[i][1];
                //Stelle 3 sagt anzahl der gebinde die bestellt werden sollen
                liste[i][3] += 1;
            }
            double uebrig = liste[i][1] - liste [i][0];
            //[i][3] = liste[i][0]
            System.out.println(uebrig);
            liste[i][2] = uebrig;
            gesamt += liste[i][0];

        }
        //sortieren nach übrigen
        Arrays.sort(liste, (a, b) -> Double.compare(a[2], b[2]));
        System.out.println(gesamt);
        double optimiert = 0;
        for (int i = 0; i < liste.length; i++){
            if(gesamt - liste[i][1] >= 0){
                liste[i][3]++;
                liste[i][0] = 0;
                liste[i][2] = 1000;
                gesamt -= liste[i][1];
            }
        }
        Arrays.sort(liste, (a, b) -> Double.compare(a[2], b[2]));
        for(double j = 0.05; j<= 0.2; j += 0.05) {
            for (int i = 0; i < liste.length; i++) {
                if (liste[i][1] == gesamt) {
                    gesamt = 0;
                    liste[i][3]++;
                    break;
                }

                //Gebinde größer als gesamt
                if (liste[i][1] - gesamt > 0) {
                    double gesamtP = gesamt + gesamt * j;
                    if (liste[i][1] - gesamtP <= 0) {
                        optimiert = liste[i][1] - gesamt;
                        gesamt = 0;
                        liste[i][3]++;
                        break;
                    }
                }
                //Gebinde kleiner als gesamt
                else {
                    double gesamtP = gesamt - gesamt * j;
                    if (liste[i][1] - gesamtP <= 0) {
                        optimiert = gesamt - liste[i][1];
                        gesamt = 0;
                        liste[i][3]++;
                        break;
                    }
                }
            }
        }
        Arrays.sort(liste, (a, b) -> Double.compare(a[4], b[4]));
        System.out.println(optimiert);
        System.out.println(gesamt);


        return liste;
        //##ENDE

    }

}
