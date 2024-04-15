package de.dhbw.foodcoop.warehouse.domain.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import de.dhbw.foodcoop.warehouse.domain.utils.ConstantsUtils;

@Entity
@Table(name = "AdminConfiguration")
public class ConfigurationEntity {

	@Lob
	@Column
	private String bestellEmailText;

	@Column
	private String emailFromBestellAdmin;
	
	@Column
	private String emailFromEinkaufAdmin;
	
	@Column
	private double deliverycost;
	
	@Id
	private String id;
	
	public ConfigurationEntity() {
		// TODO Auto-generated constructor stub
		this.id = ConstantsUtils.CONFIGURATION_ID;
	}

	public ConfigurationEntity(String bestellEmailText, String emailFromBestellAdmin, String emailFromEinkaufAdmin, double deliverycost) {
		super();
		this.deliverycost = deliverycost;
		this.id = ConstantsUtils.CONFIGURATION_ID;
		this.bestellEmailText = bestellEmailText;
		this.emailFromBestellAdmin = emailFromBestellAdmin;
		this.emailFromEinkaufAdmin = emailFromEinkaufAdmin;
	}

	public String getEinkaufEmailText() {
		if(bestellEmailText == null || bestellEmailText.isBlank()) {
			return ConstantsUtils.EMAIL_TEXT_EINKAUF_UEBERSICHT;
		}
		return bestellEmailText;
	}

	public void setBestellEmailText(String bestellEmailText) {
		this.bestellEmailText = bestellEmailText;
	}

	public String getEmailFromBestellAdmin() {
		return emailFromBestellAdmin;
	}

	public void setEmailFromBestellAdmin(String emailFromBestellAdmin) {
		this.emailFromBestellAdmin = emailFromBestellAdmin;
	}

	public String getEmailFromEinkaufAdmin() {
		return emailFromEinkaufAdmin;
	}

	public void setEmailFromEinkaufAdmin(String emailFromEinkaufAdmin) {
		this.emailFromEinkaufAdmin = emailFromEinkaufAdmin;
	}
	
	public double getDeliverycost() {
		return deliverycost;
	}

	public void setDeliverycost(double deliverycost) {
		this.deliverycost = deliverycost;
	}

	public String getId() {
		return id;
	}
	
	
	
	
}
