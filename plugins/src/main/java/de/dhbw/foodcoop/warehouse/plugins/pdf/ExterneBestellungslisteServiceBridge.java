package de.dhbw.foodcoop.warehouse.plugins.pdf;

import de.dhbw.foodcoop.warehouse.adapters.representations.DeadlineRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.DeadlineToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.application.bestellungsliste.ExterneBestellungslisteService;
import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestellungService;
import de.dhbw.foodcoop.warehouse.application.lager.ProduktService;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.values.Bestellung;
import de.dhbw.foodcoop.warehouse.domain.values.Briefkopf;
import de.dhbw.foodcoop.warehouse.plugins.rest.FrischBestellungController;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.DeadlineModelAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        return pdfService.createDocument(getBriefKopf(), bestellungList);
    }

    @Override
    public byte[] createExterneListeGebinde() throws IOException {      
        Timestamp date = getTimestampOfDeadLine(-1);
        List<FrischBestellung> frischBestellungList = frischBestellungSerivce.findByDateAfterAndSum(date);
        return pdfService.createFrischBestellungDocument(getBriefKopf(), frischBestellungList);
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

    public Timestamp getTimestampOfDeadLine(int n) {
        //n = -1 => letzte Deadline, n = -2 => vorletzte Deadline, ..
        List<EntityModel<DeadlineRepresentation>> deadlines = deadlineService.last().stream()
                .map(deadlineToPresentation)
                .map(deadlineAssembler::toModel)
                .collect(Collectors.toList());
        List<EntityModel<DeadlineRepresentation>> lastDeadline = deadlines.subList(deadlines.size()-1, deadlines.size());
        Calendar calendar = Calendar.getInstance();
        switch(lastDeadline.get(0).getContent().getWeekday()){
                case "Montag":
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        break;
                case "Dienstag":
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                        break;
                case "Mittwoch":
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                        break;
                case "Donnerstag":
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                        break;
                case "Freitag":
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                        break;
                case "Samstag":
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                        break;
                case "Sonntag":
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                        break;
        }
        calendar.add(Calendar.WEEK_OF_MONTH, n);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        Time time = lastDeadline.get(0).getContent().getTime();
        calendar.set(year, month, day, time.getHours(), time.getMinutes(), time.getSeconds() );
        Date then = calendar.getTime();
        Timestamp datum = new Timestamp(then.getTime());
        return datum;
    }
}
