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
	
	 	@GetMapping(value = "/bestellUebersicht/pdf/frisch")
	    public ResponseEntity<StreamingResponseBody> getUebersichtFrischPDF() throws IOException {
	        String fileName = "Frischbestellungen-" + LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY)).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	        byte[] pdfInBytes = pdfService.createFrischUebersicht(/*getLastBestellUebersicht()*/);
	        ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfInBytes);
	        StreamingResponseBody responseBody = outputStream -> {

	            int numberOfBytesToWrite;
	            byte[] data = new byte[1024];
	            while ((numberOfBytesToWrite = inputStream.read(data, 0, data.length)) != -1) {
	                outputStream.write(data, 0, numberOfBytesToWrite);
	            }

	            inputStream.close();
	        };

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".pdf")
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(responseBody);
	    }
	 	
	 	@GetMapping(value = "/bestellUebersicht/pdf/brot")
	    public ResponseEntity<StreamingResponseBody> getUebersichtBrotPDF() throws IOException {
	        String fileName = "Brotbestellungen-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	        byte[] pdfInBytes = pdfService.createBrotUebersicht(/*getLastBestellUebersicht()*/);
	        ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfInBytes);
	        StreamingResponseBody responseBody = outputStream -> {

	            int numberOfBytesToWrite;
	            byte[] data = new byte[1024];
	            while ((numberOfBytesToWrite = inputStream.read(data, 0, data.length)) != -1) {
	                outputStream.write(data, 0, numberOfBytesToWrite);
	            }

	            inputStream.close();
	        };

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".pdf")
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(responseBody);
	    }
}
