package de.dhbw.foodcoop.warehouse.plugins.pdf;

import de.dhbw.foodcoop.warehouse.domain.values.Bestellung;
import de.dhbw.foodcoop.warehouse.domain.values.Briefkopf;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PdfService {
    public PdfService() {
    }

    public File createDocument(Briefkopf briefKopf, List<Bestellung> bestellungList) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDFont font = PDType1Font.HELVETICA;
            PDResources resources = new PDResources();
            resources.put(COSName.getPDFName("Helv"), font);

            //initKopf(briefKopf, document, page);

            PDAcroForm acroForm = new PDAcroForm(document);
            document.getDocumentCatalog().setAcroForm(acroForm);

            // Add and set the resources and default appearance at the form level
            acroForm.setDefaultResources(resources);

            // Acrobat sets the font size on the form level to be
            // auto sized as default. This is done by setting the font size to '0'
            String defaultAppearanceString = "/Helv 0 Tf 0 g";
            acroForm.setDefaultAppearance(defaultAppearanceString);

            for (int row = 0; row < bestellungList.size(); row++) {
                Bestellung bestellung = bestellungList.get(row);
                addTextBox(page, acroForm, bestellung.getProdukt(), 0, row);
                addTextBox(page, acroForm, bestellung.getEinheit(), 1, row);
                addTextBox(page, acroForm, String.format("% .2f", bestellung.getMenge()), 2, row);
            }


            document.save(briefKopf.asDocumentName());

        }
        return new File(briefKopf.asDocumentName());
    }

    private void addTextBox(PDPage page, PDAcroForm acroForm, String bestellungsElement, int column, int row) throws IOException {
        PDTextField textBox = new PDTextField(acroForm);
        textBox.setPartialName("Bestellung");

        String defaultAppearanceString = "/Helv 12 Tf 0 g";
        textBox.setDefaultAppearance(defaultAppearanceString);

        acroForm.getFields().add(textBox);

        // Specify the widget annotation associated with the field
        PDAnnotationWidget widget = textBox.getWidgets().get(0);
        PDRectangle rect = new PDRectangle(50 + 150 * column, 750 - 50 * row, 150, 50);
        widget.setRectangle(rect);
        widget.setPage(page);

        // make sure the widget annotation is visible on screen and paper
        widget.setPrinted(true);

        // Add the widget annotation to the page
        page.getAnnotations().add(widget);
        textBox.setQ(PDVariableText.QUADDING_LEFT);
        textBox.setValue(bestellungsElement);
    }

    private void initKopf(Briefkopf briefKopf, PDDocument document, PDPage page) throws IOException {
        try (PDPageContentStream cs = new PDPageContentStream(document, page)) {
            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA, 12);
            cs.newLineAtOffset(PDRectangle.A4.getWidth() * 0.04F, PDRectangle.A4.getHeight() * 0.96F);
            cs.showText(briefKopf.getDatumString());
            cs.endText();
            cs.beginText();
            cs.newLineAtOffset(PDRectangle.A4.getWidth() * 0.04F, PDRectangle.A4.getHeight() * 0.88F);
            cs.showText("Einkäufer");
            cs.endText();
        }
    }
}
