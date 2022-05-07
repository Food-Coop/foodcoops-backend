package de.dhbw.foodcoop.warehouse.application.gebindemanagement;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.kerberos.KeyTab;

import java.util.List;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.exceptions.KategorieNotFoundException;

public class GebindemanagementService {
    
    public String vorschlagBerechnen(List<FrischBestellung> bestellung, int i) {
        //Wenn es keine Gebindegroesse gibt
        String vorschlag = Long.toString(bestellung.get(i).getBestellmenge());
        if(bestellung.get(i).getBestellmenge() == bestellung.get(i).getFrischbestand().getGebindegroesse()){
            vorschlag = Long.toString(bestellung.get(i).getBestellmenge());
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
        List<FrischBestellung> kategorie = new ArrayList<FrischBestellung>();
        kategorie.add(bestellList.get(0));
        for(int i = 1; i < bestellList.size(); i++){
            if(bestellList.get(i).getFrischbestand().getKategorie().getName() == bestellList.get(i-1).getFrischbestand().getKategorie().getName()){
                kategorie.add(bestellList.get(i));
            }
            else{
                bestellungListKategorie.add(kategorie);
                for(int j = 0; j < kategorie.size(); j++){
                    System.out.println(kategorie.get(j).getFrischbestand().getName());
                }
                kategorie.clear();
            }
        }
        bestellungListKategorie.add(kategorie);
        

        System.out.println("[");
  
        for (List<FrischBestellung> list : bestellungListKategorie) {
            System.out.print("  [");
  
            for (FrischBestellung item : list) {
                System.out.print("  "
                                 + item.toString()
                                 + ", ");
            }
            System.out.println("], ");
        }
        System.out.println("]");
        
        return bestellungListKategorie;
    }
}
