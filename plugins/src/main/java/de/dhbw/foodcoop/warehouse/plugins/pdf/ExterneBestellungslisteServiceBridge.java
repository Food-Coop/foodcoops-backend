package de.dhbw.foodcoop.warehouse.plugins.pdf;

import de.dhbw.foodcoop.warehouse.application.bestellungsliste.ExterneBestellungslisteService;
import de.dhbw.foodcoop.warehouse.application.lager.ProduktService;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.values.Bestellung;
import de.dhbw.foodcoop.warehouse.domain.values.Briefkopf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExterneBestellungslisteServiceBridge implements ExterneBestellungslisteService {
    private final ProduktService produktService;
    private final PdfService pdfService;

    @Autowired
    public ExterneBestellungslisteServiceBridge(ProduktService produktService, PdfService pdfService) {
        this.produktService = produktService;
        this.pdfService = pdfService;
    }

    @Override
    public File createExterneListe() throws IOException {
        List<Produkt> produktList = produktService.all();
        List<Bestellung> bestellungList = extractBestellungen(produktList);
        return pdfService.createDocument(getBriefKopf(), bestellungList);
    }

    private List<Bestellung> extractBestellungen(List<Produkt> produktList) {
        return produktList.stream()
                .filter(produkt -> produkt.getLagerbestand().nachbestellen())
                .map(p -> new Bestellung(p.getName()
                        , p.getLagerbestand().getEinheit().getName()
                        , p.getLagerbestand().differenz()))
                .collect(Collectors.toList());
    }

    @Override
    public Briefkopf getBriefKopf() {
        return new Briefkopf("Einkäufer");
    }

    @Override
    public String getFileName() {
        return getBriefKopf().asDocumentName();
    }
}
