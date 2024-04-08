package de.dhbw.foodcoop.warehouse.plugins.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vandeseer.easytable.RepeatedHeaderTableDrawer;
import org.vandeseer.easytable.TableDrawer;
import org.vandeseer.easytable.settings.HorizontalAlignment;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Row.RowBuilder;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.cell.TextCell;

import de.dhbw.foodcoop.warehouse.application.bestellungsliste.BestellÜbersichtService;
import de.dhbw.foodcoop.warehouse.application.gebindemanagement.GebindemanagementService;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellUebersicht;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.values.Bestellung;

@Service
public class PdfService {

    public PdfService() {
    }

    private final GebindemanagementService gebindemanagementService = new GebindemanagementService();
    
    @Autowired
    private BestellÜbersichtService service;
    
    
    public byte[] createEinkauf(EinkaufEntity einkauf) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Datum oben rechts
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(450, 800);
                contentStream.showText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                contentStream.endText();

                // Titel
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.newLineAtOffset(150, 780);
                contentStream.showText("Foodcoop Einkauf " + einkauf.getPersonId());
                contentStream.endText();
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         // Zeichnen der Frischwaren-Tabelle
            List<FrischBestellung> list = new ArrayList<>();
            
            einkauf.getBestellungsEinkauf().stream().forEach(b -> {
            	if(b.getBestellung() instanceof FrischBestellung) {
            		list.add((FrischBestellung) b.getBestellung());
            	}});
            
            List<BrotBestellung> listBrot = new ArrayList<>();
            
            einkauf.getBestellungsEinkauf().stream().forEach(b -> {
            	if(b.getBestellung() instanceof BrotBestellung) {
            		listBrot.add((BrotBestellung) b.getBestellung());
            	}});
            
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.newLineAtOffset(50, 720); // Position etwas über der Tabelle
                contentStream.showText("Frischwaren Einkauf");
                contentStream.endText();
            }
            int abstandZwischenTabellen = 70; // Abstand zwischen den Tabellen

            // Berechne die tatsächliche StartY-Position für die Brot Bestellungen-Tabelle
          
            
          
            // Tabelle für Frisch Bestellungen
            Table.TableBuilder tableBuilderFrisch = Table.builder()
                    .addColumnsOfWidth(120, 70, 100, 100, 120)
                    .fontSize(12).borderColor(Color.LIGHT_GRAY);

            // Kopfzeile Frisch Bestellungen
            tableBuilderFrisch.addRow(Row.builder()
                    .add(TextCell.builder().text("Produkt").borderWidth(1).build())
                    .add(TextCell.builder().text("Preis in €").borderWidth(1).build())
                    .add(TextCell.builder().text("Einheit").borderWidth(1).build())
                    .add(TextCell.builder().text("Bestellmenge").borderWidth(1).build())
                    .add(TextCell.builder().text("genommene Menge").borderWidth(1).build())
                    .backgroundColor(Color.LIGHT_GRAY)
                    .build());

            // Daten für Frisch Einkauf
            einkauf.getBestellungsEinkauf().stream()
            	.forEach(item -> {
            		if(item.getBestellung() instanceof FrischBestellung) {
            			FrischBestellung item2 = (FrischBestellung) item.getBestellung();
            		  RowBuilder rowBuilder = Row.builder()
            		            .add(TextCell.builder().text(item2.getFrischbestand().getName()).borderWidth(1).build())
            		            .add(TextCell.builder().text(String.valueOf(item2.getFrischbestand().getPreis())).borderWidth(1).build())
            		            .add(TextCell.builder().text(item2.getFrischbestand().getEinheit().getName()).borderWidth(1).build())
            		            .add(TextCell.builder().text(String.valueOf(item2.getBestellmenge())).borderWidth(1).build())
            		            .add(TextCell.builder().text(String.valueOf(item.getAmount())).borderWidth(1).build());
            		  				
            		  tableBuilderFrisch.addRow(rowBuilder.build());
            		}
            		        
            	});
//                	tableBuilderFrisch.addRow(Row.builder()
//                        .add(TextCell.builder().text(item.getBestand().getName()).borderWidth(1).build())
//                        .add(TextCell.builder().text(String.valueOf(item.getZuBestellendeGebinde())).borderWidth(1).build())
//                        .add(TextCell.builder().text(item.getBestand() instanceof FrischBestand ? String.valueOf(((FrischBestand)item.getBestand()).getGebindegroesse()) : "unknown" ).borderWidth(1)
//                        .build())));

            // Tabelle zeichnen
           TableDrawer d =  TableDrawer.builder()
                    .table(tableBuilderFrisch.build())
                    .startX(50)
                    .startY(710)
                    .endY(50) // Margin bottom
                    .build();
                    d.draw(() -> document, () -> new PDPage(PDRectangle.A4), 50);
            
                    int startYBrotEinkaufUeberschrift = (int) (d.getFinalY() - abstandZwischenTabellen);
                    int startYBrotEinkaufTabelle = startYBrotEinkaufUeberschrift - 10; // Etwas Platz für die Überschrift
                 
                 

                    int gesamtpreisFrischPositionY = (int) (d.getFinalY()  - 20);
                    
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.beginText();
                
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(297.5f - (PDType1Font.HELVETICA.getStringWidth("Frisch-Preis: " + Math.round( einkauf.getFreshPriceAtTime() * 100.0) / 100.0 + " €") / 2 / 1000 * 12), gesamtpreisFrischPositionY);
                contentStream.showText("Frisch-Preis: " + Math.round( einkauf.getFreshPriceAtTime() * 100.0) / 100.0 + " €");
                contentStream.endText();
            }

            // Zeichnen der Brot-Tabelle
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.newLineAtOffset(50, startYBrotEinkaufUeberschrift); // Anpassen basierend auf der Höhe der ersten Tabelle
                contentStream.showText("Brot Einkauf");
                contentStream.endText();
            }
         //   .addColumnsOfWidth(150, 50, 100, 50, 50)
            // Tabelle für Brot Bestellungen
            Table.TableBuilder tableBuilderBrot = Table.builder()
                    .addColumnsOfWidth(120, 70, 100, 120)
                    .fontSize(12).borderColor(Color.LIGHT_GRAY);

            // .addColumnsOfWidth(120, 70, 100, 100, 120)
            // Kopfzeile Brot Bestellungen
            tableBuilderBrot.addRow(Row.builder()
                    .add(TextCell.builder().text("Produkt").borderWidth(1).build())
                    .add(TextCell.builder().text("Preis in €").borderWidth(1).build())
                    .add(TextCell.builder().text("Bestellmenge").borderWidth(1).build())
                    .add(TextCell.builder().text("genommene Menge").borderWidth(1).build())
                    .backgroundColor(Color.LIGHT_GRAY)
                    .build());

            // Daten für Brot Einkauf
            einkauf.getBestellungsEinkauf().stream()
            	.forEach(item -> {
            		if(item.getBestellung() instanceof BrotBestellung) {
            			BrotBestellung item2 = (BrotBestellung) item.getBestellung();
            		  RowBuilder rowBuilder = Row.builder()
            		            .add(TextCell.builder().text(item2.getBrotBestand().getName()).borderWidth(1).build())
            		            .add(TextCell.builder().text(String.valueOf(item2.getBrotBestand().getPreis())).borderWidth(1).build())
            		            .add(TextCell.builder().text(String.valueOf(item2.getBestellmenge())).borderWidth(1).build())
            		            .add(TextCell.builder().text(String.valueOf(item.getAmount())).borderWidth(1).build());
            		  				
            		  tableBuilderBrot.addRow(rowBuilder.build());
            		}
            		        
            	});

            // Tabelle zeichnen
            TableDrawer bd = TableDrawer.builder()
                    .table(tableBuilderBrot.build())
                    .startX(50)
                    .startY(startYBrotEinkaufTabelle) //  basierend auf der Position der Überschrift
                    .endY(50) // Margin bottom, könnte angepasst werden basierend auf dem Inhalt
                    .build();
                    bd.draw(() -> document, () -> new PDPage(PDRectangle.A4), 50);

            
            
            
            int startYLagerEinkaufUeberschrift = (int) (bd.getFinalY() - abstandZwischenTabellen);
            int startYLagerEinkaufTabelle = startYLagerEinkaufUeberschrift - 10; // Etwas Platz für die Überschrift
            
            int gesamtpreisBrotPositionY = (int) (bd.getFinalY() - 20);
            
            
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.beginText();
                
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(297.5f - (PDType1Font.HELVETICA.getStringWidth("Brot-Preis: " + Math.round( einkauf.getBreadPriceAtTime() * 100.0) / 100.0 + " €") / 2 / 1000 * 12), gesamtpreisBrotPositionY);
                contentStream.showText("Brot-Preis: " + Math.round( einkauf.getBreadPriceAtTime() * 100.0) / 100.0 + " €");
                contentStream.endText();
            }
            // Zeichnen der Lagerware-Tabelle
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.newLineAtOffset(50, startYLagerEinkaufUeberschrift); // Anpassen basierend auf der Höhe der ersten Tabelle
                contentStream.showText("Lagerware Einkauf");
                contentStream.endText();
            }

            // Tabelle für Lagerware Bestellungen  .addColumnsOfWidth(150, 50, 100, 50, 50)
            Table.TableBuilder tableBuilderLager = Table.builder()
                    .addColumnsOfWidth(120, 70, 100, 120)
                    // .addColumnsOfWidth(120, 70, 100, 100, 120)
                    .fontSize(12).borderColor(Color.LIGHT_GRAY);

            // Kopfzeile Lagerware Bestellungen
            tableBuilderLager.addRow(Row.builder()
                    .add(TextCell.builder().text("Produkt").borderWidth(1).build())
                    .add(TextCell.builder().text("Preis in €").borderWidth(1).build())
                    .add(TextCell.builder().text("Einheit").borderWidth(1).build())
                    .add(TextCell.builder().text("genommene Menge").borderWidth(1).build())
                    .backgroundColor(Color.LIGHT_GRAY)
                    .build());

            // Daten für Lagerware Bestellungen
            
            einkauf.getBestandEinkauf().stream().forEach(item -> {
            	
            	
            	 RowBuilder rowBuilder = Row.builder()
            			 .add(TextCell.builder().text(item.getBestand().getName()).borderWidth(1).build())
                         .add(TextCell.builder().text(String.valueOf(item.getBestand().getPreis())).borderWidth(1).build())
                         .add(TextCell.builder().text(item.getBestand().getLagerbestand().getEinheit().getName()).borderWidth(1).build())
                         .add(TextCell.builder().text(String.valueOf(item.getAmount())).borderWidth(1).build());
                        
            	 tableBuilderLager.addRow(rowBuilder.build());
            });
      
            

            // Tabelle zeichnen
            TableDrawer ld = TableDrawer.builder()
                    .table(tableBuilderLager.build())
                    .startX(50)
                    .startY(startYLagerEinkaufTabelle) //  basierend auf der Position der Überschrift
                    .endY(50) // Margin bottom, könnte angepasst werden basierend auf dem Inhalt
                    .build();
                    ld.draw(() -> document, () -> new PDPage(PDRectangle.A4), 50);
                    int gesamtpreisLagerPositionY = (int) (ld.getFinalY()  - 20);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.beginText();
                
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(297.5f - (PDType1Font.HELVETICA.getStringWidth("Lager-Preis: " + Math.round( einkauf.getBestandPriceAtTime() * 100.0) / 100.0 + " €") / 2 / 1000 * 12), gesamtpreisLagerPositionY);
                contentStream.showText("Lager-Preis: " + Math.round( einkauf.getBestandPriceAtTime() * 100.0) / 100.0 + " €");
                contentStream.endText();
            }
            
            
            int startY = gesamtpreisLagerPositionY - 50; // zum Beispiel 100;

            // Konstanten für das Layout
            float pageWidth = page.getMediaBox().getWidth();
            float leftMargin = (pageWidth - 550) / 2; // um das Ganze zentriert zu halten
            float rightMargin = (pageWidth - 50) / 2; 
            float lineWidth = 550; // die Länge des grauen Strichs
            float startX = 200; // Dies ist der Startpunkt für den Text auf der X-Achse, den Sie anpassen müssen.
            float priceX = 350; // Dies ist der Startpunkt für den Preis auf der X-Achse, den Sie anpassen müssen.
            float lineStartX = 150; // Start der grauen Linie auf der X-Achse
            float lineEndX = 450; // Ende der grauen Linie auf der X-Achse

            String[] labels = {"Frischware:", "Brot:", "Lagerware:", "5 % Lieferkosten:"};
            
            float lieferkosten = (float) (einkauf.getFreshPriceAtTime() * 0.05);
            float gesamt = (float) (lieferkosten + einkauf.getTotalPriceAtTime());
            float[] prices = {(float) (Math.round( einkauf.getFreshPriceAtTime() * 100.0) / 100.0), (float) (Math.round( einkauf.getBreadPriceAtTime() * 100.0) / 100.0), (float) (Math.round( einkauf.getBestandPriceAtTime() * 100.0) / 100.0), lieferkosten, gesamt}; 
            
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                for (int i = 0; i < labels.length; i++) {
                    // Text linksbündig ausrichten
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX, startY);
                    contentStream.showText(labels[i]);
                    contentStream.endText();

                    // Preise rechtsbündig neben dem Text ausrichten
                    String priceString = String.format(Locale.GERMANY, "%,.2f €", prices[i]);
                    float priceWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(priceString) / 1000 * 12;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(priceX + (40 - priceWidth), startY); // 80 ist die angenommene Breite für den Preis
                    contentStream.showText(priceString);
                    contentStream.endText();

                    // Einfügen des grauen Strichs nach den Lieferkosten und vor "Insgesamt"
                    if (i == labels.length - 1) { // Nach dem letzten Item in der Liste (vor "Insgesamt")
                        startY -= 15; // Abstand vor der Linie
                        contentStream.setStrokingColor(Color.GRAY);
                        contentStream.moveTo(lineStartX, startY);
                        contentStream.lineTo(lineEndX, startY);
                        contentStream.stroke();
                        startY -= 15; // Abstand nach der Linie
                    } else {
                        startY -= 15; // Zeilenabstand für die nächste Zeile
                    }
                }

                // "Insgesamt" Text und Preis hinzufügen
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, startY);
                contentStream.showText("Insgesamt:");
                contentStream.endText();

                String totalString = String.format(Locale.GERMANY, "%,.2f €", gesamt); 
                float totalWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(totalString) / 1000 * 12;
                contentStream.beginText();
                contentStream.newLineAtOffset(priceX + (40 - totalWidth), startY);
                contentStream.showText(totalString);
                contentStream.endText();
            }
            // Stream und Dokument schließen
          
           
            
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            document.close();
            return out.toByteArray();

           
        }
    }
    public byte[] createFrischUebersicht() {
    	  try (PDDocument document = new PDDocument()) {
              PDPage page = new PDPage();
              document.addPage(page);
              
              
              
              // Erstelle die Tabelle mit festgelegten Spaltenbreiten
//              Table.TableBuilder tableBuilder = Table.builder()
//            		  // 130px = 3,9cm     => 1 cm = 33,333 px
//            		  // 3,3 cm = 110px     2,8 Höhe cm = 93,3333
//                      .addColumnOfWidth(110) // Breite für "Lieferdatum"
//                      .addColumnOfWidth(110)
//                      .addColumnOfWidth(80) // Breite für "Herkunft"
//                      .addColumnOfWidth(150) // Breite für "Artikel"
//                      // Weitere Spalten hinzufügen nach Bedarf
//                      .fontSize(10)
//                      .font(PDType1Font.HELVETICA_BOLD);
//
//              // Kopfzeile hinzufügen
//              Row row1 = Row.builder()
//            	        .add(TextCell.builder().text("Lieferdatum").rowSpan(2).borderWidth(1).build())
//            	        .add(TextCell.builder().text("Herkunft").colSpan(1).borderWidth(1).build())
//            	        .add(TextCell.builder().text("Preis\nin €").borderWidth(1).build())
//            	        .height(93.33333f) // Höhe für "Lieferdatum"
//            	        .build();
//            	tableBuilder.addRow(row1);

              Table myTable = Table.builder()
                      .addColumnsOfWidth(20, 20, 20, 20, 20, 20)
                      .padding(2)
                      .borderWidth(1)
                      .borderColor(Color.gray)
                      .horizontalAlignment(HorizontalAlignment.CENTER)
                      .addRow(Row.builder()
                              .add(TextCell.builder().text("a").colSpan(8).build())
                              .build())
                      .addRow(Row.builder()
                              .add(TextCell.builder().text("Lieferdatum").rowSpan(5).build())
                              .add(TextCell.builder().text("Herkunft").colSpan(3).build())
                              .add(TextCell.builder().text("Preis in €").build())
                              .add(TextCell.builder().text("gültig für").build())
                              .add(TextCell.builder().text("Gebindegröße").build())
                              .add(TextCell.builder().text("Bestellung").build())
                              .build())
                      .addRow(Row.builder()

                              .add(TextCell.builder().text("h").build())
                              .add(TextCell.builder().text("i").build())
                              .add(TextCell.builder().text("j").build())
                              .build())
                      .build();
            	// Füge eine weitere Zeile für "Gemüse/Obst" hinzu, die die erste Spalte einnimmt
//            	Row row2 = Row.builder()
//            	        .add(TextCell.builder().text("07.05.2019\nKW19-2019").borderWidth(1).build())
//            	        .add(TextCell.builder().text("Gemüse/Obst").borderWidth(1).build())
//            	        .height(20f) // Höhe für "Gemüse/Obst"
//            	        .build();
//            	tableBuilder.addRow(row2);
//            
              // Beispiel für eine Zeile mit Daten (muss für tatsächliche Daten angepasst werden)
//              tableBuilder.addRow(Row.builder()
//                      .add(TextCell.builder().text("07.05.2019").borderWidth(1).build())
//                      .add(TextCell.builder().text("DE").borderWidth(1).build())
//                      .add(TextCell.builder().text("Tomaten").borderWidth(1).build())
//                      // Weitere Zellen für Daten hinzufügen
//                      .build());

              // Tabelle zeichnen
              TableDrawer ld = TableDrawer.builder()
                      .page(page)
                      .table(myTable)
                      .startX(50) // X-Position anpassen
                      .startY(page.getMediaBox().getHeight() - 50) // Y-Position anpassen
                      .build();
              
    
                      ld.draw(() -> document, () -> new PDPage(PDRectangle.A4), 50);
              // Dokument speichern
              
              ByteArrayOutputStream out = new ByteArrayOutputStream();
              document.save(out);
              document.close();
              return out.toByteArray();
          } catch (Exception e) {
              e.printStackTrace();
          }
		return null;
    }
 


	public byte[] createUebersicht(BestellUebersicht bestellungUebersicht) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Datum oben rechts
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(450, 800);
                contentStream.showText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                contentStream.endText();

                // Titel
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.newLineAtOffset(150, 780);
                contentStream.showText("Foodcoop Bestellungs Übersicht");
                contentStream.endText();
            }

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.newLineAtOffset(50, 720); // Position etwas über der Tabelle
                contentStream.showText("Frisch Bestellungen");
                contentStream.endText();
            }
            
            int anzahlFrischBestellungenZeilen = bestellungUebersicht.getDiscrepancy().stream().filter(d -> d.getZuBestellendeGebinde() != 0 ).collect(Collectors.toList()).size() + 1; // +1 für die Kopfzeile
            int startYFrischBestellungen = 720; // Startposition der Frisch Bestellungen Überschrift
            int zeilenHoehe = 20; // Geschätzte Höhe jeder Zeile
            int abstandZwischenTabellen = 50; // Abstand zwischen den Tabellen

            // Berechne die tatsächliche StartY-Position für die Brot Bestellungen-Tabelle
            int startYBrotBestellungenUeberschrift = startYFrischBestellungen - (anzahlFrischBestellungenZeilen * zeilenHoehe) - abstandZwischenTabellen;
            int startYBrotBestellungenTabelle = startYBrotBestellungenUeberschrift - 10; // Etwas Platz für die Überschrift

            
            // Tabelle für Frisch Bestellungen
            Table.TableBuilder tableBuilderFrisch = Table.builder()
                    .addColumnsOfWidth(200, 150, 100)
                    .fontSize(12).borderColor(Color.LIGHT_GRAY);

            // Kopfzeile Frisch Bestellungen
            tableBuilderFrisch.addRow(Row.builder()
                    .add(TextCell.builder().text("Name").borderWidth(1).build())
                    .add(TextCell.builder().text("zu Bestellende Gebinde").borderWidth(1).build())
                    .add(TextCell.builder().text("Gebindegroesse").borderWidth(1).build())
                    .backgroundColor(Color.LIGHT_GRAY)
                    .build());

            // Daten für Frisch Bestellungen
            bestellungUebersicht.getDiscrepancy().stream()
            	.filter(d -> d.getZuBestellendeGebinde() != 0 )
            	.forEach(item -> {
            		
            		  RowBuilder rowBuilder = Row.builder()
            		            .add(TextCell.builder().text(item.getBestand().getName()).borderWidth(1).build())
            		            .add(TextCell.builder().text(String.valueOf(item.getZuBestellendeGebinde())).borderWidth(1).build());
            		  if (item.getBestand() instanceof FrischBestand) {
            	            rowBuilder.add(TextCell.builder().text(String.valueOf(((FrischBestand) item.getBestand()).getGebindegroesse()) + " " + String.valueOf(((FrischBestand) item.getBestand()).getEinheit().getName() )).borderWidth(1).build());
            	        } else {
            	            rowBuilder.add(TextCell.builder().text("unknown").borderWidth(1).build());
            	        }
            		  tableBuilderFrisch.addRow(rowBuilder.build());
            		        
            	});
//                	tableBuilderFrisch.addRow(Row.builder()
//                        .add(TextCell.builder().text(item.getBestand().getName()).borderWidth(1).build())
//                        .add(TextCell.builder().text(String.valueOf(item.getZuBestellendeGebinde())).borderWidth(1).build())
//                        .add(TextCell.builder().text(item.getBestand() instanceof FrischBestand ? String.valueOf(((FrischBestand)item.getBestand()).getGebindegroesse()) : "unknown" ).borderWidth(1)
//                        .build())));

            // Tabelle zeichnen
            TableDrawer.builder()
                    .table(tableBuilderFrisch.build())
                    .startX(50)
                    .startY(710)
                    .endY(50) // Margin bottom
                    .build()
                    .draw(() -> document, () -> new PDPage(PDRectangle.A4), 50);

            //Überschrit Brotbestellung
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.newLineAtOffset(50, startYBrotBestellungenUeberschrift); // Anpassen basierend auf der Höhe der ersten Tabelle
                contentStream.showText("Brot Bestellungen");
                contentStream.endText();
            }

            // Tabelle für Brot Bestellungen
            Table.TableBuilder tableBuilderBrot = Table.builder()
                    .addColumnsOfWidth(200, 150)
                    .fontSize(12).borderColor(Color.LIGHT_GRAY);

            // Kopfzeile Brot Bestellungen
            tableBuilderBrot.addRow(Row.builder()
                    .add(TextCell.builder().text("Brotname").borderWidth(1).build())
                    .add(TextCell.builder().text("Stückzahl").borderWidth(1).build())
                    .backgroundColor(Color.LIGHT_GRAY)
                    .build());

            // Daten für Brot Bestellungen
            bestellungUebersicht.getBrotBestellung().stream().filter(b -> b.getBestellmenge() != 0).forEach(item -> 
                tableBuilderBrot.addRow(Row.builder()
                        .add(TextCell.builder().text(item.getBrotBestand().getName()).borderWidth(1).build())
                        .add(TextCell.builder().text(String.valueOf(item.getBestellmenge())).borderWidth(1).build())
                        .build())
            );

            // Tabelle zeichnen
            TableDrawer.builder()
                    .table(tableBuilderBrot.build())
                    .startX(50)
                    .startY(startYBrotBestellungenTabelle) //  basierend auf der Position der Überschrift
                    .endY(50) // Margin bottom, könnte angepasst werden basierend auf dem Inhalt
                    .build()
                    .draw(() -> document, () -> new PDPage(PDRectangle.A4), 50);
            
            
          
            
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] createDocument(List<Bestellung> bestellungList) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (final PDDocument document = new PDDocument()) {

            RepeatedHeaderTableDrawer.builder()
                    .table(createTableWithThreeHeaderRows(bestellungList))
                    .startX(50)
                    .startY(545F)
                    .endY(50F) // note: if not set, table is drawn over the end of the page
                    .numberOfRowsToRepeat(2)
                    .build()
                    .draw(() -> document, () -> new PDPage(PDRectangle.A4), 50f);


            document.save(outputStream);

        }
        return outputStream.toByteArray();
    }

    public byte[] createFrischBestellungDocument( List<FrischBestellung> frischBestellungList) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (final PDDocument document = new PDDocument()) {

            RepeatedHeaderTableDrawer.builder()
                    .table(createTableWithFourHeaderRows(frischBestellungList))
                    .startX(20)
                    .startY(30F)
                    .endY(50F) // note: if not set, table is drawn over the end of the page
                    .numberOfRowsToRepeat(2)
                    .build()
                    .draw(() -> document, () -> new PDPage(PDRectangle.A4), 50f);


            document.save(outputStream);

        }
        return outputStream.toByteArray();
    }

    private Table createTableWithThreeHeaderRows(List<Bestellung> bestellungList) {
        final Table.TableBuilder tableBuilder = Table.builder()
                .addColumnsOfWidth(200, 100, 100, 50);

        buildTableWithHeader(tableBuilder);

        if (bestellungList.isEmpty()) {
            return addTableIsEmptyMessage(tableBuilder);
        }

        for (Bestellung bestellung : bestellungList) {
            tableBuilder.addRow(
                    Row.builder()
                            .add(getStandardCell(bestellung.getProdukt()))
                            .add(getStandardCell(bestellung.getEinheit()))
                            .add(getStandardCell(String.format("% .2f", bestellung.getMenge())))
                            .add(getStandardCell(""))
                            .build());
        }

        return tableBuilder.build();
    }

    private Table createTableWithFourHeaderRows(List<FrischBestellung> bestellungList) {
        final Table.TableBuilder tableBuilder = Table.builder()
                .addColumnsOfWidth(100, 70, 100, 70, 100, 60, 60);

        buildGebindeTableWithHeader(tableBuilder);

        if (bestellungList.isEmpty()) {
            return addTableIsEmptyMessage(tableBuilder);
        }

        //Liste mit Sublisten von Frischbestellungen nach der Kategorie sortiert
        List<List<FrischBestellung>> bestellungListKategorie = gebindemanagementService.splitBestellungList(bestellungList);
        // System.out.println(bestellungListKategorie);
        // System.out.println(bestellungListKategorie.get(0).get(0).getBestellmenge());

        for (int i = 0; i < bestellungListKategorie.size(); i++) {

            List<FrischBestellung> kategorie = bestellungListKategorie.get(i);
            double[][] matrix = gebindemanagementService.VorschlagBerechnen(kategorie);
            double vgz = 0;
            double gz = 0;

            for (int j = 0; j < kategorie.size(); j++){

                FrischBestellung bestellung = kategorie.get(j);
                String vorschlag = Double.toString(matrix[j][3] * bestellung.getFrischbestand().getGebindegroesse());
                vgz += matrix[j][3] * bestellung.getFrischbestand().getGebindegroesse();
                gz += bestellung.getBestellmenge();
                
                DecimalFormat df = new DecimalFormat("0.##");
                df.setRoundingMode(RoundingMode.DOWN);
                String bestellmengeRounded = df.format(gz);
                bestellmengeRounded = bestellmengeRounded.replace(",", ".");
                gz = Double.valueOf(bestellmengeRounded);

                tableBuilder.addRow(
                        Row.builder()
                                .add(getStandardCell(bestellung.getFrischbestand().getName()))
                                .add(getStandardCell(Double.toString(gz)))
                                .add(getStandardCell(bestellung.getFrischbestand().getGebindegroesse()))
                                .add(getStandardCell(bestellung.getFrischbestand().getPreis()))
                                .add(getStandardCell(bestellung.getFrischbestand().getKategorie().getName()))
                                .add(getStandardCell(vorschlag))
                                .add(getStandardCell(""))
                                .build());
            }
            tableBuilder.addRow(
                    Row.builder()
                            .add(getStandardCell("Gesamt:"))
                            .add(getStandardCell(Double.toString(gz)))
                            .add(getStandardCell(""))
                            .add(getStandardCell(""))
                            .add(getStandardCell(""))
                            .add(getStandardCell(Double.toString(vgz)))
                            .add(getStandardCell(""))
                            .build());

        }
        return tableBuilder.build();
    }



    private Table buildTableWithHeader(Table.TableBuilder tableBuilder) {
        return tableBuilder
                .addRow(Row.builder()
                        .add(createHeaderCell("Produkt"))
                        .add(createHeaderCell("Einheit"))
                        .add(createHeaderCell("Menge"))
                        .add(createHeaderCell("OK"))
                        .build())
                .build();
    }

    private Table buildGebindeTableWithHeader(Table.TableBuilder tableBuilder) {
        return tableBuilder
                .addRow(Row.builder()
                        .add(createHeaderCell("Produkt"))
                        .add(createHeaderCell("Menge"))
                        .add(createHeaderCell("Gebindegröße"))
                        .add(createHeaderCell("Preis"))
                        .add(createHeaderCell("Kategorie"))
                        .add(createHeaderCell("Vorschlag"))
                        .add(createHeaderCell("Korrektur"))
                        .build())
                .build();
    }

    private TextCell getStandardCell(String text) {
        return TextCell.builder()
                .text(text)
                .textColor(Color.BLACK)
                .borderColor(Color.BLACK)
                .borderWidth(2f)
                .padding(12f)
                .build();
    }

    private TextCell getStandardCell(float text) {
        String textToString = Float.toString(text);
        return TextCell.builder()
                .text(textToString)
                .textColor(Color.BLACK)
                .borderColor(Color.BLACK)
                .borderWidth(2f)
                .padding(12f)
                .build();
    }

    private Table addTableIsEmptyMessage(Table.TableBuilder tableBuilder) {
        tableBuilder.addRow(
                Row.builder()
                        .add(getStandardCell("Das Lager"))
                        .add(getStandardCell("ist"))
                        .add(getStandardCell("voll."))
                        .add(getStandardCell(""))
                        .build());
        return tableBuilder.build();
    }

    private TextCell createHeaderCell(String text) {
        return TextCell.builder()
                .font(PDType1Font.HELVETICA_BOLD)
                .text(text.toUpperCase())
                .backgroundColor(Color.BLUE)
                .padding(8f)
                .textColor(Color.WHITE)
                .borderColor(Color.WHITE)
                .borderWidth(2f)
                .build();
    }

}