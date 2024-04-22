package de.dhbw.foodcoop.warehouse.plugins.helpObjects;

public class PDFInfoObject {

	private byte[] pdf;
	private String filename;
	public PDFInfoObject(byte[] pdf, String filename) {
		super();
		this.pdf = pdf;
		this.filename = filename;
	}
	public byte[] getPdf() {
		return pdf;
	}
	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	
}
