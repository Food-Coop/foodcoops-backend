package de.dhbw.foodcoop.warehouse.plugins.pdf;

import de.dhbw.foodcoop.warehouse.application.gebindemanagement.GebindemanagementService;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.values.Bestellung;
import de.dhbw.foodcoop.warehouse.domain.values.Briefkopf;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import org.vandeseer.easytable.RepeatedHeaderTableDrawer;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.cell.TextCell;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfService {

    public PdfService() {
    }

    private final GebindemanagementService gebindemanagementSerivce = new GebindemanagementService();

    public byte[] createDocument(Briefkopf briefKopf, List<Bestellung> bestellungList) throws IOException {
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

    public byte[] createFrischBestellungDocument(Briefkopf briefKopf, List<FrischBestellung> frischBestellungList) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (final PDDocument document = new PDDocument()) {

            RepeatedHeaderTableDrawer.builder()
                    .table(createTableWithFourHeaderRows(frischBestellungList))
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
                .addColumnsOfWidth(100, 70, 100, 70, 100, 80);

        buildGebindeTableWithHeader(tableBuilder);

        if (bestellungList.isEmpty()) {
            return addTableIsEmptyMessage(tableBuilder);
        }

        //Liste mit Sublisten von Frischbestellungen nach der Kategorie sortiert
        List<List<FrischBestellung>> bestellungListKategorie = gebindemanagementSerivce.splitBestellungList(bestellungList);
        System.out.println(bestellungListKategorie);
        for (int i = 0; i < bestellungList.size(); i ++) {
            FrischBestellung bestellung = bestellungList.get(i);
            String vorschlag = gebindemanagementSerivce.vorschlagBerechnen(bestellungList, i);
            tableBuilder.addRow(
                    Row.builder()
                            .add(getStandardCell(bestellung.getFrischbestand().getName()))
                            .add(getStandardCell(bestellung.getBestellmenge()))
                            .add(getStandardCell(bestellung.getFrischbestand().getGebindegroesse()))
                            .add(getStandardCell(bestellung.getFrischbestand().getPreis()))
                            .add(getStandardCell(bestellung.getFrischbestand().getKategorie().getName()))
                            .add(getStandardCell(vorschlag))
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
                .padding(16f)
                .textColor(Color.WHITE)
                .borderColor(Color.WHITE)
                .borderWidth(2f)
                .build();
    }

}