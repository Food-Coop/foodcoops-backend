package de.dhbw.foodcoop.warehouse.plugins.pdf;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.DeadlineToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.application.bestellungsliste.ExterneBestellungslisteService;
import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestellungService;
import de.dhbw.foodcoop.warehouse.application.lager.ProduktService;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.values.Bestellung;
import de.dhbw.foodcoop.warehouse.domain.values.Briefkopf;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.DeadlineModelAssembler;

@Service
public class ExterneBestellungslisteServiceBridge implements ExterneBestellungslisteService {
    private final ProduktService produktService;
    private final FrischBestellungService frischBestellungSerivce;
    private final PdfService pdfService;
    private final DeadlineService deadlineService;
    private final DeadlineToRepresentationMapper deadlineToPresentation;
    private final DeadlineModelAssembler deadlineAssembler;

    @Autowired
    public ExterneBestellungslisteServiceBridge(ProduktService produktService, FrischBestellungService frischBestellungService, PdfService pdfService, DeadlineService deadlineService, DeadlineToRepresentationMapper deadlineToPresentation, DeadlineModelAssembler deadlineAssembler) {
        this.produktService = produktService;
        this.frischBestellungSerivce = frischBestellungService;
        this.pdfService = pdfService;
        this.deadlineService = deadlineService;
        this.deadlineToPresentation = deadlineToPresentation;
        this.deadlineAssembler = deadlineAssembler;
    }

    @Override
    public byte[] createExterneListe() throws IOException {
        List<Produkt> produktList = produktService.all();
        List<Bestellung> bestellungList = extractBestellungen(produktList);
        return pdfService.createDocument( bestellungList);
    }

    @Override
    public byte[] createExterneListeGebinde() throws IOException {   
    	//ANSCHAUEN!
    	LocalDateTime date = deadlineService.calculateDateFromDeadline(deadlineService.getByPosition(1).get());
        List<FrischBestellung> frischBestellungList = frischBestellungSerivce.findByDateAfterAndSum(date);
        return pdfService.createFrischBestellungDocument( frischBestellungList);
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
        return new Briefkopf("Einkaufsliste");
    }

    @Override
    public String getFileName() {
        return getBriefKopf().asDocumentName();
    }

    
}
