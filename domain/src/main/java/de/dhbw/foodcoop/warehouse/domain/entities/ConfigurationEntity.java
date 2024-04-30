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
	private String einkaufEmailText;
	
	@Lob
	@Column
	private String einkaufsmanagementEmailText;
	
	@Lob
	@Column
	private String lagermeisterEmailText;

	@Column
	private double threshold;
	
	@Column
	private double deliverycost;
	
	@Id
	private String id;
	
	public ConfigurationEntity() {
		// TODO Auto-generated constructor stub
		this.id = ConstantsUtils.CONFIGURATION_ID;
	}

	public ConfigurationEntity(String einkaufEmailText, double threshold, double deliverycost, String einkaufsmanagementEmailText, String lagermeisterEmailText) {
		super();
		this.deliverycost = deliverycost;
		this.id = ConstantsUtils.CONFIGURATION_ID;
		this.einkaufEmailText = einkaufEmailText;
		this.threshold = threshold;
		this.lagermeisterEmailText = lagermeisterEmailText;
		this.einkaufsmanagementEmailText = einkaufsmanagementEmailText;
	}

	public String getEinkaufEmailText() {
		if(einkaufEmailText == null || einkaufEmailText.isBlank()) {
			return ConstantsUtils.EMAIL_TEXT_EINKAUF_UEBERSICHT;
		}
		return einkaufEmailText;
	}

	public void setEinkaufEmailText(String einkaufEmailText) {
		this.einkaufEmailText = einkaufEmailText;
	}


	
	public String getEinkaufsmanagementEmailText() {
		if(einkaufsmanagementEmailText == null) {
			return ConstantsUtils.EMAIL_TEXT_EINKAUFSMANAGEMENT;
		}
		return einkaufsmanagementEmailText;
	}

	public void setEinkaufsmanagementEmailText(String einkaufsmanagementEmailText) {
		this.einkaufsmanagementEmailText = einkaufsmanagementEmailText;
	}

	public String getLagermeisterEmailText() {
		if(lagermeisterEmailText == null) {
			return ConstantsUtils.EMAIL_TEXT_LAGERMEISTER;
		}
		return lagermeisterEmailText;
	}

	public void setLagermeisterEmailText(String lagermeisterEmailText) {
		this.lagermeisterEmailText = lagermeisterEmailText;
	}

	public double getThreshold() {
		if(threshold == 0) {
			return ConstantsUtils.THRESHOLD;
		}
		return threshold;
	}

	public void setThreshold(double threshold) {
		
		this.threshold = threshold;
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
