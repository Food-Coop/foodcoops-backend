package de.dhbw.foodcoop.warehouse.plugins.pdf;

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
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class PdfService {

    public PdfService() {
    }

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

    private Table createTableWithThreeHeaderRows(List<Bestellung> bestellungList) {
        final Table.TableBuilder tableBuilder = Table.builder()
                .addColumnsOfWidth(200, 100, 100, 50);

        tableBuilder
                .addRow(Row.builder()
                        .add(createHeaderCell("Produkt"))
                        .add(createHeaderCell("Einheit"))
                        .add(createHeaderCell("Menge"))
                        .add(createHeaderCell("OK"))
                        .build())
                .build();

        for (Bestellung bestellung : bestellungList) {
            tableBuilder.addRow(
                    Row.builder()
                            .add(TextCell.builder()
                                    .text(bestellung.getProdukt())
                                    .textColor(Color.BLACK)
                                    .borderColor(Color.BLACK)
                                    .borderWidth(2f)
                                    .padding(12f)
                                    .build())
                            .add(TextCell.builder()
                                    .text(bestellung.getEinheit())
                                    .textColor(Color.BLACK)
                                    .borderColor(Color.BLACK)
                                    .borderWidth(2f)
                                    .padding(12f)
                                    .build())
                            .add(TextCell.builder()
                                    .text(String.format("% .2f", bestellung.getMenge()))
                                    .textColor(Color.BLACK)
                                    .borderColor(Color.BLACK)
                                    .borderWidth(2f)
                                    .padding(12f)
                                    .build())
                            .add(TextCell.builder()
                                    .text("")
                                    .textColor(Color.BLACK)
                                    .borderColor(Color.BLACK)
                                    .borderWidth(2f)
                                    .padding(12f)
                                    .build())
                            .build());
        }

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
