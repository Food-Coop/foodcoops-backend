package de.dhbw.foodcoop.warehouse.plugins.rest;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.foodcoop.warehouse.application.bestellungsliste.BestellÜbersichtService;
import de.dhbw.foodcoop.warehouse.plugins.email.EmailService;
import de.dhbw.foodcoop.warehouse.plugins.pdf.PdfService;

@RestController
public class EmailController {

	
	@Autowired
	private EmailService service;
	
	@Autowired
	private PdfService pdf;
	
	@Autowired
	private BestellÜbersichtService bueService;
	
	@GetMapping("/email")
	public void sendEmail() {
		try {
			service.sendSimpleMessage("nikolas.gaska.99@gmail.com", "Dies ist ein kleiner Text", "Hallo Niko,\n ich hoffe dir geht es gut! \nIm Anhang ist die aktuelle Bestellübersicht.\n\nViele Grüße \nDeine Foodcoop Karlsruhe Nordstadt", pdf.createUebersicht(bueService.getLastUebersicht()), "BestellÜbersicht.pdf");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
