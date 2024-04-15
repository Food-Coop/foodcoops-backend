package de.dhbw.foodcoop.warehouse.plugins.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.vandeseer.easytable.OverflowOnSamePageTableDrawer;
import org.vandeseer.easytable.RepeatedHeaderTableDrawer;
import org.vandeseer.easytable.TableDrawer;
import org.vandeseer.easytable.settings.HorizontalAlignment;
import org.vandeseer.easytable.settings.VerticalAlignment;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Row.RowBuilder;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.cell.TextCell;

import de.dhbw.foodcoop.warehouse.application.admin.ConfigurationService;
import de.dhbw.foodcoop.warehouse.application.bestellungsliste.BestellÜbersichtService;
import de.dhbw.foodcoop.warehouse.application.brot.BrotBestandService;
import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestandService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestellungService;
import de.dhbw.foodcoop.warehouse.application.gebindemanagement.GebindemanagementService;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellUebersicht;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;
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
    
    @Autowired
    private ResourceLoader resourceLoader;
    
    @Autowired
    private FrischBestandService frischBestandService;

    @Autowired
	private BrotBestandService brotBestandService;

    @Autowired
	private ConfigurationService configService;
    
    @Autowired
	private FrischBestellungService frischService;
    
    @Autowired
	private DeadlineService deadService;
    
    public byte[] createEinkauf(EinkaufEntity einkauf) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Datum oben rechts
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(450, 800);
                contentStream.showText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                contentStream.endText();

                // Titel
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
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
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
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
                
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(297.5f - (new PDType1Font(Standard14Fonts.FontName.HELVETICA).getStringWidth("Frisch-Preis: " + Math.round( einkauf.getFreshPriceAtTime() * 100.0) / 100.0 + " €") / 2 / 1000 * 12), gesamtpreisFrischPositionY);
                contentStream.showText("Frisch-Preis: " + Math.round( einkauf.getFreshPriceAtTime() * 100.0) / 100.0 + " €");
                contentStream.endText();
            }

            // Zeichnen der Brot-Tabelle
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
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
                
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(297.5f - (new PDType1Font(Standard14Fonts.FontName.HELVETICA).getStringWidth("Brot-Preis: " + Math.round( einkauf.getBreadPriceAtTime() * 100.0) / 100.0 + " €") / 2 / 1000 * 12), gesamtpreisBrotPositionY);
                contentStream.showText("Brot-Preis: " + Math.round( einkauf.getBreadPriceAtTime() * 100.0) / 100.0 + " €");
                contentStream.endText();
            }
            // Zeichnen der Lagerware-Tabelle
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
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
                
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(297.5f - (new PDType1Font(Standard14Fonts.FontName.HELVETICA).getStringWidth("Lager-Preis: " + Math.round( einkauf.getBestandPriceAtTime() * 100.0) / 100.0 + " €") / 2 / 1000 * 12), gesamtpreisLagerPositionY);
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
            
            float lieferkosten = (float) (Math.round((einkauf.getFreshPriceAtTime() * (configService.getConfig().get().getDeliverycost() /100) * 100.0) / 100.0)  );
            float gesamt = (float) (lieferkosten + einkauf.getTotalPriceAtTime());
            float[] prices = {(float) (Math.round( einkauf.getFreshPriceAtTime() * 100.0) / 100.0), (float) (Math.round( einkauf.getBreadPriceAtTime() * 100.0) / 100.0), (float) (Math.round( einkauf.getBestandPriceAtTime() * 100.0) / 100.0), lieferkosten, gesamt}; 
            
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);

                for (int i = 0; i < labels.length; i++) {
                    // Text linksbündig ausrichten
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX, startY);
                    contentStream.showText(labels[i]);
                    contentStream.endText();

                    // Preise rechtsbündig neben dem Text ausrichten
                    String priceString = String.format(Locale.GERMANY, "%,.2f €", prices[i]);
                    float priceWidth = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD).getStringWidth(priceString) / 1000 * 12;
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
                float totalWidth = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD).getStringWidth(totalString) / 1000 * 12;
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
    
    public byte[] createBrotUebersicht() {
    	  try (PDDocument document = new PDDocument()) {
              PDPage page = new PDPage(PDRectangle.A4);
              document.addPage(page);
              
              float toPx = 33.3333333333f;
              
              PDType0Font arial = PDType0Font.load(document,resourceLoader.getResource("classpath:Arial.ttf").getFile());
              PDType0Font arialBold = PDType0Font.load(document, resourceLoader.getResource("classpath:Arial_Bold.ttf").getFile());
              
              PDPageContentStream contentStream = new PDPageContentStream(document, page);
                  // Datum oben rechts
                  contentStream.beginText();
                  contentStream.setFont(arialBold, 11);
                  contentStream.newLineAtOffset(2f * toPx + 8f * toPx + 0.5f*toPx, page.getBBox().getHeight() - 1.8f - (1.6f*toPx)/2);
                  contentStream.setNonStrokingColor(Color.black);
                  contentStream.showText("Name:");
                  contentStream.endText();
                  
                  contentStream.setStrokingColor(Color.black);
                  contentStream.moveTo(2f * toPx + 8f * toPx + 0.5f*toPx + toPx * 1.2f , page.getBBox().getHeight() - 1.8f - (1.6f*toPx)/2);
                  contentStream.lineTo(2f * toPx + 8f * toPx + 0.5f*toPx  + toPx * 5.0f, page.getBBox().getHeight() - 1.8f - (1.6f*toPx)/2 );
                  contentStream.stroke();
                  
                  
                  contentStream.beginText();
                  contentStream.setFont(arial, 14);
                  contentStream.newLineAtOffset(2f * toPx + 8f * toPx + 0.5f*toPx + toPx * 1.2f, page.getBBox().getHeight() - 1.8f - (1.6f*toPx)/2 + 0.1f * toPx);
                  contentStream.setNonStrokingColor(Color.black);
                  contentStream.showText("MIKA-FOODCOOP");
                  contentStream.endText();
                  
                  contentStream.beginText();
                  contentStream.setFont(arialBold, 11);
                  contentStream.newLineAtOffset(2f * toPx + 8f * toPx + 0.5f*toPx, page.getBBox().getHeight() - 1.8f - (1.6f*toPx)/2 - (0.7f* toPx));
                  contentStream.setNonStrokingColor(Color.black);
                  contentStream.showText("Kunden-Nr.:");
                  contentStream.endText();
                  
                  contentStream.setStrokingColor(Color.black);
                  contentStream.moveTo(2f * toPx + 8f * toPx + 0.5f*toPx + toPx * 2.1f , page.getBBox().getHeight() - 1.8f - (1.6f*toPx)/2 - (0.7f* toPx));
                  contentStream.lineTo(2f * toPx + 8f * toPx + 0.5f*toPx  + toPx * 5.0f,page.getBBox().getHeight() - 1.8f - (1.6f*toPx)/2 - (0.7f* toPx) );
                  contentStream.stroke();
                  
                  contentStream.beginText();
                  contentStream.setFont(arial, 14);
                  contentStream.setNonStrokingColor(Color.decode("#7874c9"));
                  contentStream.newLineAtOffset(2f * toPx + 8f * toPx + 0.5f*toPx + toPx * 2.1f,page.getBBox().getHeight() - 1.8f - (1.6f*toPx)/2 - (0.7f* toPx) + 0.1f *toPx);
                  contentStream.setNonStrokingColor(Color.decode("#7874c9"));
                  contentStream.showText("12306");
                  contentStream.endText();
                  // Titel
                  File picture = resourceLoader.getResource("classpath:fasanbaecker.png").getFile();
                  
                  PDImageXObject pdImage = PDImageXObject.createFromFileByContent(picture,document);  
                  contentStream.drawImage(pdImage, 	2f *toPx, page.getBBox().getHeight() - 1.8f * toPx, 8f * toPx, 1.6f*toPx);
                  
                  
                  final Table.TableBuilder myTableBuilder = Table.builder()
                          .addColumnsOfWidth(1.2f * toPx, 4f * toPx, 1.2f * toPx)
                          .padding(2)
                          .borderColor(Color.gray)
                          .horizontalAlignment(HorizontalAlignment.LEFT)
                          .verticalAlignment(VerticalAlignment.MIDDLE)
                          .addRow(Row.builder()
                        		  .font(arial)
                                  .add(TextCell.builder().borderWidthBottom(1).fontSize(10).borderWidthTop(0).borderWidthLeft(0).borderWidthRight(0).font(arialBold).horizontalAlignment(HorizontalAlignment.CENTER).text("Nr.").build())
                                  .add(TextCell.builder().borderWidthBottom(1).font(arialBold).font(arialBold).borderWidthTop(0).borderWidthLeft(0).borderWidthRight(0).fontSize(10).text("Artikel          Datum:").borderWidthRight(1).build())
                                  .add(TextCell.builder().borderWidthBottom(1).font(arialBold).borderWidth(1).fontSize(10).text(LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY)).format(DateTimeFormatter.ofPattern("dd.MM")) + "").build())

                                  .height(0.4f * toPx)
                                  // 2.3f
                                  .build());
                  

                  
                  
                  
                  brotBestandService.allOrdered().forEach(t -> {
                	  int id = Integer.parseInt(t.getId());
                	  String idString;
                	  if(id <= 100) {
                		  idString = "0" + id;
                	  } else {
                		  idString = t.getId();
                	  }
                	  String nameBuilder = t.getName();
                	  if(t.getGewicht() != 0) {
                		  nameBuilder = (int)(t.getGewicht()) + "g " + t.getName();
                	  }
                  	myTableBuilder
                  		.addRow(Row.builder()
      	        			 .add(TextCell.builder().borderWidth(1).text(idString).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(8).font(arial).build())
      	        			 .add(TextCell.builder().borderWidth(1).text(nameBuilder).horizontalAlignment(HorizontalAlignment.LEFT).fontSize(6).font(arialBold).build())
      	        			 .add(TextCell.builder().borderWidth(1).text("").horizontalAlignment(HorizontalAlignment.LEFT).fontSize(8).font(arialBold).build())

      	        			 .height(0.35f * toPx)
              			 .build());
                   });
              OverflowOnSamePageTableDrawer ld = OverflowOnSamePageTableDrawer.builder()
                      .page(page)
                      .lanesPerPage(2)
                      
                      .table(myTableBuilder.build())
                      .startX(1.8f*toPx) // X-Position anpassen
                    //  .startY(page.getMediaBox().getHeight() - 2.4f*toPx) // Y-Position anpassen
                      .startY(page.getMediaBox().getHeight() - 2.4f*toPx)
                      .endY(1.7f*toPx)
                      .build();
              
    
                      ld.draw(() -> document, () ->  new PDPage(PDRectangle.A4), 2.4f*toPx);
                      
                      
              contentStream.close();  
              ByteArrayOutputStream out = new ByteArrayOutputStream();
              document.save(out);
              document.close();
              return out.toByteArray();
         
    	  } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
    
    }
    
    public static String formatDouble(double value) {
        // Runde den Wert auf eine Nachkommastelle
        double rounded = Math.round(value * 10.0) / 10.0;
        if (rounded == (long) rounded) {
            return String.format("%d", (long) rounded);
        } else {
            return String.format("%.1f", rounded);
        }
    }
    int pageCounter;
    public byte[] createFrischUebersicht() {
    	  try (PDDocument document = new PDDocument()) {
    		  pageCounter = 2;
              PDPage page = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
              document.addPage(page);
              
              
              
              float toPx = 33.3333333333f;
              String hexColorHeader = "#8b918f";
              String hexColorLieferdatum = "#6b7680";
              String hexCodeBestellung = "#ea8a99";
              String hexCodeGebindegroesse = "#585e5c";
              String hexCodeTable = "#394a44";
              
              Color header = Color.decode(hexColorHeader);
              Color lieferdatum = Color.decode(hexColorLieferdatum);
              Color bestellung = Color.decode(hexCodeBestellung);
              Color gebinde = Color.decode(hexCodeGebindegroesse);
              Color table = Color.decode(hexCodeTable);
              
              PDType0Font arial = PDType0Font.load(document,resourceLoader.getResource("classpath:Arial.ttf").getFile());
              PDType0Font arialBold = PDType0Font.load(document, resourceLoader.getResource("classpath:Arial_Bold.ttf").getFile());
              
              try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                  // Datum oben rechts
                  contentStream.beginText();
                  contentStream.setFont(arialBold, 11);
                  contentStream.newLineAtOffset(page.getBBox().getWidth() - 4.7f * toPx , page.getBBox().getHeight() - 1.1f * toPx);
                  contentStream.setNonStrokingColor(table);
                  contentStream.showText("Fax Petrik: 465442");
                  contentStream.endText();

                  // Titel
                  contentStream.beginText();
                  contentStream.setNonStrokingColor(table);
                  contentStream.setFont(arialBold, 11);
                  contentStream.newLineAtOffset(2.2f * toPx, page.getBBox().getHeight() - 1.1f * toPx);
                  
                  contentStream.showText("Gemüsebestellung Food-Coop Mika");
                  contentStream.endText();
                  
                  contentStream.beginText();
                  contentStream.setNonStrokingColor(table);
                  contentStream.setFont(arialBold, 11);
                  contentStream.newLineAtOffset(page.getBBox().getWidth()/2, page.getBBox().getHeight() - 1.1f * toPx);
                  
                  contentStream.showText("Seite 1");
                  contentStream.endText();
              } catch (IOException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}

            		 
            	 
          //    Queue<String> person = new  ConcurrentLinkedQueue<>();
          	Optional<Deadline> date1 = deadService.getByPosition(0);
        	Optional<Deadline> date2 = deadService.getByPosition(1);
        	HashMap<Integer, HashMap<String, Double>> map= new HashMap<>();
        	String[] personNames = new String[16];
        	Arrays.fill(personNames, " ");
        	if(!date1.isEmpty()) {
        		if(!date2.isEmpty()) {
        			LocalDateTime datum1 = date1.get().getDatum();
                	LocalDateTime datum2 = date2.get().getDatum();
                 	List<FrischBestellung> frischBestellungen = frischService.findByDateBetween(datum1, datum2);
                 	Set<String> personNamesSet = new HashSet<>();
                 	frischBestellungen.forEach(t -> {personNamesSet.add(t.getPersonId());});
                 //	person.addAll(personNames);
                 	int counterForSet = 0;
                 	for(String pName : personNamesSet) {
                 		personNames[counterForSet++] = pName;
                 	}
                 	for(int i = 0; i < personNames.length ; i++) {
                 		if(personNames[i].equalsIgnoreCase(" ")) continue;
                 		HashMap<String, Double> idAmountMap= new HashMap<>();
                 		frischService.findByDateBetween(datum1, datum2, personNames[i]).forEach(t -> {idAmountMap.put(t.getFrischbestand().getId(), t.getBestellmenge());});
                 		map.put(i, idAmountMap);
                 	}
        		}
        	}
        	
        	System.err.println(personNames.length);
              int counterForHeader = 0;
             final Table.TableBuilder myTableBuilder = Table.builder()
            		 
                      .addColumnsOfWidth(4.2f * toPx, 0.6f * toPx, 0.8f * toPx, 1.4f * toPx, 0.7f * toPx, 1.5f * toPx, 1.4f * toPx, 0.7f * toPx, 0.7f * toPx, 0.7f * toPx, 0.7f * toPx, 0.7f * toPx, 0.7f * toPx, 0.7f * toPx, 0.7f * toPx, 0.7f * toPx, 0.7f * toPx, 0.7f * toPx, 0.7f * toPx, 0.7f * toPx, 0.7f * toPx, 0.7f * toPx, 0.7f * toPx, toPx * 1.6f)
                      .padding(2)
                      .borderColor(Color.gray)
                      .horizontalAlignment(HorizontalAlignment.CENTER)
                      .verticalAlignment(VerticalAlignment.MIDDLE)
                      .addRow(Row.builder()
                    		  .font(arial)
                              .add(TextCell.builder().borderWidthTop(1).borderWidthLeft(1).borderWidthBottom(0).horizontalAlignment(HorizontalAlignment.LEFT).textColor(lieferdatum).font(arialBold).text("Lieferdatum\n" + LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY)).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "\nKW" + LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).get(WeekFields.of(Locale.GERMANY).weekOfYear()) + " - " + LocalDateTime.now().getYear() + "\n ").borderWidthBottom(1f).build())
                              .add(TextCell.builder().borderWidth(1).minHeight(0.7f*toPx).fontSize(10).borderWidthBottom(0).textColor(lieferdatum).verticalAlignment(VerticalAlignment.TOP).text("Herkunft").borderWidthRight(1).rowSpan(1).colSpan(2).build())
                              .add(CustomVerticalTextCell.builder().font(arialBold).textColor(gebinde).borderWidth(1).fontSize(10).text("Preis in €").borderWidthRight(1).rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).fontSize(8).text("gültig für kg St. Bd.").rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().font(arialBold).fontSize(10).textColor(gebinde).borderWidth(1).text("Gebindegröße").rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(bestellung).borderWidth(1).font(arialBold).text("Bestellung").rowSpan(2).build())
                              
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).font(arialBold).text(personNames[counterForHeader++]).rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).font(arialBold).text(personNames[counterForHeader++]).rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).font(arialBold).text(personNames[counterForHeader++]).rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).font(arialBold).text(personNames[counterForHeader++]).rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).font(arialBold).text(personNames[counterForHeader++]).rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).font(arialBold).text(personNames[counterForHeader++]).rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).font(arialBold).text(personNames[counterForHeader++]).rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).font(arialBold).text(personNames[counterForHeader++]).rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).font(arialBold).text(personNames[counterForHeader++]).rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).font(arialBold).text(personNames[counterForHeader++]).rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).font(arialBold).text(personNames[counterForHeader++]).rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).font(arialBold).text(personNames[counterForHeader++]).rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).font(arialBold).text(personNames[counterForHeader++]).rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).font(arialBold).text(personNames[counterForHeader++]).rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).font(arialBold).text(personNames[counterForHeader++]).rowSpan(2).build())
                              .add(CustomVerticalTextCell.builder().textColor(gebinde).borderWidth(1).font(arialBold).text(personNames[counterForHeader]).rowSpan(2).build())
                              .add(TextCell.builder().borderWidth(1).textColor(lieferdatum).font(arialBold).text("Summe").rowSpan(2).build())
                              .height(0.4f * toPx)
                              // 2.3f
                              .build())
                      .addRow(Row.builder()

                              .add(TextCell.builder().borderWidth(1).borderWidthTop(0).fontSize(8).horizontalAlignment(HorizontalAlignment.LEFT).verticalAlignment(VerticalAlignment.BOTTOM).text("Gemüse/Obst\n").build())
                              .add(CustomVerticalTextCell.builder().borderWidthRight(1).fontSize(8).verticalAlignment(VerticalAlignment.TOP).minHeight(1.9f*toPx).textColor(lieferdatum).borderWidthTop(0).text("Land").build())
                              .add(CustomVerticalTextCell.builder().borderWidthLeft(1).fontSize(8).verticalAlignment(VerticalAlignment.TOP).minHeight(1.9f*toPx).borderWidthTop(0).textColor(lieferdatum).text("Verband").build())
                              .height(1.9f * toPx)
                              .build())
                      .addRow(Row.builder()
                    		  .add(TextCell.builder().horizontalAlignment(HorizontalAlignment.LEFT).borderWidth(1).textColor(table).fontSize(8).font(arialBold).text("Bestelleinheit (kg/Stück) beachten! 'x' für Stück hinzufügen, falls Einheit 'Kg' (z.B. Blumenkohl)").colSpan(24).build())
                    		  .height(0.5f*toPx)
                    		  .build());
            
             frischBestandService.allOrdered().forEach(t -> {
            	 if(t.getVerfuegbarkeit() ) {
            		 
                int counter = 0;
            	myTableBuilder
            		.addRow(Row.builder()
	        			 .add(TextCell.builder().borderWidth(1).text(t.getName()).horizontalAlignment(HorizontalAlignment.LEFT).fontSize(10).font(arialBold).build())
	        			 .add(TextCell.builder().borderWidth(1).text(t.getHerkunftsland()).horizontalAlignment(HorizontalAlignment.LEFT).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text("").horizontalAlignment(HorizontalAlignment.LEFT).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text(t.getPreis() + "").horizontalAlignment(HorizontalAlignment.RIGHT).fontSize(10).font(arialBold).build())
	        			 .add(TextCell.builder().borderWidth(1).text(t.getEinheit().getName().equalsIgnoreCase("Stück") ? "St" : t.getEinheit().getName()).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text(t.getGebindegroesse() + "").horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text("").horizontalAlignment(HorizontalAlignment.LEFT).fontSize(10).font(arialBold).build())
	        			 
	        			 .add(TextCell.builder().borderWidth(1).text(map.get(counter++) == null ? "" : map.get(counter-1).get(t.getId()) == null ? "" : formatDouble( map.get(counter-1).get(t.getId())) + "" ).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text(map.get(counter++) == null ? "" : map.get(counter-1).get(t.getId()) == null ? "" : formatDouble(map.get(counter-1).get(t.getId())) + "" ).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text(map.get(counter++) == null ? "" : map.get(counter-1).get(t.getId()) == null ? "" : formatDouble(map.get(counter-1).get(t.getId())) + "" ).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text(map.get(counter++) == null ? "" : map.get(counter-1).get(t.getId()) == null ? "" : formatDouble(map.get(counter-1).get(t.getId())) + "" ).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text(map.get(counter++) == null ? "" : map.get(counter-1).get(t.getId()) == null ? "" : formatDouble(map.get(counter-1).get(t.getId())) + "" ).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text(map.get(counter++) == null ? "" : map.get(counter-1).get(t.getId()) == null ? "" : formatDouble(map.get(counter-1).get(t.getId())) + "" ).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text(map.get(counter++) == null ? "" : map.get(counter-1).get(t.getId()) == null ? "" : formatDouble(map.get(counter-1).get(t.getId())) + "" ).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text(map.get(counter++) == null ? "" : map.get(counter-1).get(t.getId()) == null ? "" : formatDouble(map.get(counter-1).get(t.getId())) + "" ).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())

	        			 .add(TextCell.builder().borderWidth(1).text(map.get(counter++) == null ? "" : map.get(counter-1).get(t.getId()) == null ? "" : formatDouble( map.get(counter-1).get(t.getId())) + "" ).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text(map.get(counter++) == null ? "" : map.get(counter-1).get(t.getId()) == null ? "" : formatDouble(map.get(counter-1).get(t.getId())) + "" ).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text(map.get(counter++) == null ? "" : map.get(counter-1).get(t.getId()) == null ? "" : formatDouble(map.get(counter-1).get(t.getId())) + "" ).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text(map.get(counter++) == null ? "" : map.get(counter-1).get(t.getId()) == null ? "" : formatDouble(map.get(counter-1).get(t.getId())) + "" ).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text(map.get(counter++) == null ? "" : map.get(counter-1).get(t.getId()) == null ? "" : formatDouble(map.get(counter-1).get(t.getId())) + "" ).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text(map.get(counter++) == null ? "" : map.get(counter-1).get(t.getId()) == null ? "" : formatDouble(map.get(counter-1).get(t.getId())) + "" ).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text(map.get(counter++) == null ? "" : map.get(counter-1).get(t.getId()) == null ? "" : formatDouble(map.get(counter-1).get(t.getId())) + "" ).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())
	        			 .add(TextCell.builder().borderWidth(1).text(map.get(counter++) == null ? "" : map.get(counter-1).get(t.getId()) == null ? "" : formatDouble(map.get(counter-1).get(t.getId())) + "" ).horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arial).build())

	        			 .add(TextCell.builder().borderWidth(1).text("-   €").horizontalAlignment(HorizontalAlignment.CENTER).fontSize(10).font(arialBold).build())
	        			 .height(0.4f * toPx)
        			 .build());
            	 }
             });

             
             myTableBuilder
             			.borderColor(Color.gray)
             			.addRow(Row.builder()
			            		 .add(TextCell.builder().borderWidth(1).text("Reklamationen").borderColorTop(Color.BLACK).borderWidthTop(2).horizontalAlignment(HorizontalAlignment.LEFT).font(arialBold).fontSize(10).build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .add(TextCell.builder().borderWidth(1).borderColorTop(Color.BLACK).borderWidthTop(2).text("").build())
			            		 .height(0.4f * toPx)
			            		 .build())
             			.addRow(Row.builder()
             					 .add(TextCell.builder().borderWidth(1).text("Pfand (Napf)").horizontalAlignment(HorizontalAlignment.LEFT).font(arialBold).fontSize(10).build())
             					 .add(TextCell.builder().borderWidth(1).text("").build())
             					 .add(TextCell.builder().borderWidth(1).text("").build())
             					 .add(TextCell.builder().borderWidth(1).text("").build())
             					 .add(TextCell.builder().borderWidth(1).text("St").build())
             					 .add(TextCell.builder().borderWidth(1).text("").build())
             					 .add(TextCell.builder().borderWidth(1).text("").build())
             					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
             					 
             					.height(0.4f * toPx)
             					 .build())
             			.addRow(Row.builder()
            					 .add(TextCell.builder().borderWidth(1).text("zurück").horizontalAlignment(HorizontalAlignment.LEFT).font(arialBold).fontSize(10).build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("St").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .add(TextCell.builder().borderWidth(1).text("").build())
            					 .height(0.4f * toPx)
            					 .build())
             			.addRow(Row.builder()
           					 .add(TextCell.builder().borderWidth(1).text("Gesamtsumme").colSpan(5).horizontalAlignment(HorizontalAlignment.LEFT).font(arialBold).fontSize(12).build())
           					 .add(TextCell.builder().borderWidth(1).text("").build())
           					 .add(TextCell.builder().borderWidth(1).text("").build())
           					.add(TextCell.builder().borderWidth(1).text("").colSpan(16).build())
           					 .add(TextCell.builder().borderWidth(1).text("").build())
           					 .height(0.5f * toPx)
           					 .build());

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
              RepeatedHeaderTableDrawer ld = RepeatedHeaderTableDrawer.builder()
                      .page(page)
                      .table(myTableBuilder.build())
                      .headerHeight(2.3f * toPx)
                      .numberOfRowsToRepeat(2)
                      .startX(toPx * 1.5f) // X-Position anpassen
                      .endY(toPx*2.6f)
                      .startY(page.getMediaBox().getHeight() - (1.7f * toPx)) // Y-Position anpassen
                      .build();
              
    
                      ld.draw(() -> document, () -> {
                    	  PDPage p = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
                          try (PDPageContentStream contentStream = new PDPageContentStream(document, p)) {
                              // Datum oben rechts
                              contentStream.beginText();
                              contentStream.setFont(arialBold, 11);
                              contentStream.newLineAtOffset(page.getBBox().getWidth() - 4.7f * toPx , page.getBBox().getHeight() - 1.1f * toPx);
                              contentStream.setNonStrokingColor(table);
                              contentStream.showText("Fax Petrik: 465442");
                              contentStream.endText();

                              // Titel
                              contentStream.beginText();
                              contentStream.setNonStrokingColor(table);
                              contentStream.setFont(arialBold, 11);
                              contentStream.newLineAtOffset(2.2f*toPx, page.getBBox().getHeight() - 1.1f * toPx);
                              
                              contentStream.showText("Gemüsebestellung Food-Coop Mika");
                              contentStream.endText();
                              
                              contentStream.beginText();
                              contentStream.setNonStrokingColor(table);
                              contentStream.setFont(arialBold, 11);
                              contentStream.newLineAtOffset(p.getBBox().getWidth()/2, page.getBBox().getHeight() - 1.1f * toPx);
                              
                              contentStream.showText("Seite " + (pageCounter - 1));
                              contentStream.endText();
                              pageCounter++;
                          } catch (IOException e) {
              				// TODO Auto-generated catch block
              				e.printStackTrace();
              			}
                    	  return p;
                    	  }, 50);
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
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(450, 800);
                contentStream.showText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                contentStream.endText();

                // Titel
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
                contentStream.newLineAtOffset(150, 780);
                contentStream.showText("Foodcoop Bestellungs Übersicht");
                contentStream.endText();
            }

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
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
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
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
        		.font(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD))
                .text(text.toUpperCase())
                .backgroundColor(Color.BLUE)
                .padding(8f)
                .textColor(Color.WHITE)
                .borderColor(Color.WHITE)
                .borderWidth(2f)
                .build();
    }

}