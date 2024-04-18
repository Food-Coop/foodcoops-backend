package de.dhbw.foodcoop.warehouse.plugins.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import de.dhbw.foodcoop.warehouse.application.bestellungsliste.BestellÜbersichtService;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellUebersicht;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;
import de.dhbw.foodcoop.warehouse.plugins.pdf.PdfService;

@RestController
public class BestellUebersichtController {

	@Autowired
	private BestellÜbersichtService bueService;
	
	@Autowired
	private PdfService pdfService;
	
	@GetMapping("/bestellUebersicht/last")
	public BestellUebersicht getLastBestellUebersicht() {
		return bueService.getLastUebersicht();
	}
	
	@GetMapping("/bestellUebersicht/{id}")
	public BestellUebersicht getById(@PathVariable String id) {
		return bueService.findById(id);
	}
	
	@GetMapping("/bestellUebersicht/getByDeadline")
	public BestellUebersicht getByDeadline(@RequestBody Deadline deadline) {
		return bueService.getByDeadline(deadline);
	}
	
	@DeleteMapping("/bestellUebersicht/{id}")
	public void getByDeadline(@PathVariable String id) {
		 bueService.deleteById(id);
	}
}
