package de.dhbw.foodcoop.warehouse.plugins.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import de.dhbw.foodcoop.warehouse.application.bestellungsliste.BestellÜbersichtService;
import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;
import de.dhbw.foodcoop.warehouse.plugins.email.EmailService;
import de.dhbw.foodcoop.warehouse.plugins.helpObjects.PDFInfoObject;
import de.dhbw.foodcoop.warehouse.plugins.pdf.PdfService;

@RestController
public class PdfController {

	
	@Autowired
	private EmailService service;
	
	@Autowired
	private PdfService pdf;
	
	@Autowired
	private DeadlineService deadlineService;
	
	@Autowired
	private BestellÜbersichtService bueService;
	
	//TODO: Es muss alles mit Keycloak abgesichert werden.
	// Wenn dies geschehen ist, kann man sich über das Authentification Objekt den Token ziehen
	// Und damit auch die Email, wenn diese drin steht im Token.
	// Dann muss keine Email im Body übergeben werden.

	
	@PostMapping("/email/send/bestellUebersicht") 
	public void sendTotalBestellÜbersicht(@RequestBody String email) {
		String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		try {
			service.sendSimpleMessage(email, "Foodcoop MIKA - Bestellübersicht vom " + date, "Hallo, \nim Anhang befindet sich die Bestellübersicht vom " + date +".\n\nViele Grüße \nDeine Foodcoop MIKA", pdf.createUebersicht(bueService.getLastUebersicht()), "Bestelluebersicht-" + date + ".pdf");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PostMapping("/email/send/brotBestellungen") 
	public void sendBreadOrder(@RequestBody String email) {
		String date = deadlineService.getByPosition(0).get().getDatum().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		try {
			service.sendSimpleMessage(email, "Foodcoop MIKA - zu bestellende Brote vom " + date, "Hallo, \nim Anhang befindet sich die Liste der zu bestellenden Brote für die Deadline vom " + date +".\n\nViele Grüße \nDeine Foodcoop MIKA", pdf.createBrotUebersicht(), "Brotbestellungen-" + date + ".pdf");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PostMapping("/email/send/brotBestellungenMitPersonen") 
	public void sendBreadOrderWithPersons(@RequestBody String email) {
		String date = deadlineService.getByPosition(0).get().getDatum().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		try {
			service.sendSimpleMessage(email, "Foodcoop MIKA - Brotbestellungen der Mitglieder " + date, "Hallo, \nim Anhang befindet sich die Liste der einzelnen Brotbestellungen für die Deadline vom " + date +".\n\nViele Grüße \nDeine Foodcoop MIKA", pdf.createBrotUebersichtWithPersons(), "BrotbestellungenPersonen-" + date + ".pdf");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@PostMapping("/email/send/lagerbestand/{email}") 
	public void sendInventoryStatus(@RequestBody String base64Pdf, @PathVariable String email ) {
		String date = deadlineService.getByPosition(0).get().getDatum().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		try {
			service.sendSimpleMessage(email, "Foodcoop MIKA - aktueller Lagerbestand " + date, "Hallo, \nim Anhang befindet sich der aktuelle Lagerbestand.\n\nViele Grüße \nDeine Foodcoop MIKA", pdf.createByteArrayFromBase64String(base64Pdf), "Lagerbestand-" + date + ".pdf");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PostMapping("/email/send/frischBestellungen") 
	public void sendFreshOrder(@RequestBody String email) {
		String date = deadlineService.getByPosition(0).get().getDatum().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		try {
			service.sendSimpleMessage(email, "Foodcoop MIKA - zu bestellende Frischware vom " + date, "Hallo, \nim Anhang befindet sich die Liste der zu bestellenden Frischware für die Deadline vom " + date +".\n\nViele Grüße \nDeine Foodcoop MIKA", pdf.createFrischUebersicht(), "Frischbestellungen-" + date + ".pdf");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 	@GetMapping(value = "/pdf/download/frischBestellungen")
    public ResponseEntity<StreamingResponseBody> getUebersichtFrischPDF() throws IOException {
        String fileName = "Frischbestellungen-" + LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY)).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        byte[] pdfInBytes = pdf.createFrischUebersicht(/*getLastBestellUebersicht()*/);
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
 	
 	@GetMapping(value = "/pdf/download/brotBestellungen")
    public ResponseEntity<StreamingResponseBody> getUebersichtBrotPDF() throws IOException {
        String fileName = "Brotbestellungen-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        byte[] pdfInBytes = pdf.createBrotUebersicht(/*getLastBestellUebersicht()*/);
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
	
 	@GetMapping(value = "/pdf/download/bestellUebersicht")
    public ResponseEntity<StreamingResponseBody> getBestellUebersichtPDF() throws IOException {
        String fileName = "Bestelluebersicht-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        byte[] pdfInBytes = pdf.createUebersicht(bueService.getLastUebersicht());
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
 	
 	@GetMapping(value = "/pdf/download/brotBestellungenMitPerson")
    public ResponseEntity<StreamingResponseBody> getBrotBestellungenWithPersonPDF() throws IOException {
        String fileName = "Brotbestellungen-fuer-Personen-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        byte[] pdfInBytes = pdf.createBrotUebersichtWithPersons();
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
 	
 	@GetMapping(value = "/pdf/byte/frischBestellungen")
    public PDFInfoObject getUebersichtFrischPDFasByte() throws IOException {
        return new PDFInfoObject(pdf.createFrischUebersicht(/*getLastBestellUebersicht()*/), "Frischbestellungen-" + LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY)).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }
 	
 	@GetMapping(value = "/pdf/byte/brotBestellungen")
    public PDFInfoObject getUebersichtBrotPDFasByte() throws IOException {
        return new PDFInfoObject(pdf.createBrotUebersicht(/*getLastBestellUebersicht()*/),"Brotbestellungen-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) );
    }	
	
 	@GetMapping(value = "/pdf/byte/bestellUebersicht")
    public PDFInfoObject getBestellUebersichtPDFasByte() throws IOException {
        return new PDFInfoObject(pdf.createUebersicht(bueService.getLastUebersicht()), "Bestelluebersicht-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
 	}
 	
 	@GetMapping(value = "/pdf/byte/brotMitPerson")
    public PDFInfoObject getBreadWithPersonPDFasByte() throws IOException {
        return new PDFInfoObject(pdf.createBrotUebersichtWithPersons(), "Brotbestellungen-fuer-Personen-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
 	}
}
