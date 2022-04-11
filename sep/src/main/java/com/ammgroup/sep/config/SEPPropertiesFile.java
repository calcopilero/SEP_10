package com.ammgroup.sep.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:${SEP_DIR}/config/sep.properties")
public class SEPPropertiesFile {
	
	@Value( "${db.file}" )
	private String dbFile;
	
	@Value( "${pdf.location}" )
	private String pdfLocation;
	
//	@Value( "${images.location}" )
//	private String imagesLocation;
	
	@Value( "${socios.default.sort.field}" )
	private String sociosDefaultSortField;
	
	@Value( "${socios.default.sort.direction}" )
	private String sociosDefaultSortDirection;
	
	@Value( "${facturas.default.sort.field}" )
	private String facturasDefaultSortField;
	
	@Value( "${facturas.default.sort.direction}" )
	private String facturasDefaultSortDirection;
	
	@Value( "${agencias.default.sort.field}" )
	private String agenciasDefaultSortField;
	
	@Value( "${agencias.default.sort.direction}" )
	private String agenciasDefaultSortDirection;

	@Value( "${reclamaciones.default.sort.field}" )
	private String reclamacionesDefaultSortField;
	
	@Value( "${reclamaciones.default.sort.direction}" )
	private String reclamacionesDefaultSortDirection;
	
	@Value( "${mainform.backcolor}" )
	private String mainformBackcolor;
	
	@Value( "${sep.address}" )
	private String address;
	
	@Value( "${sep.phone}" )
	private String phone;
	
	@Value( "${sep.email}" )
	private String email;
	
	@Value( "${sep.web}" )
	private String web;
	
	public SEPPropertiesFile() {
		super();
	}

	public String getDbFile() {
		return dbFile;
	}

	public void setDbFile(String dbFile) {
		this.dbFile = dbFile;
	}
	
	public String getPdfLocation() {
		return pdfLocation;
	}

	public void setPdfLocation(String pdfLocation) {
		this.pdfLocation = pdfLocation;
	}

//	public String getImagesLocation() {
//		return imagesLocation;
//	}
//
//	public void setImagesLocation(String imagesLocation) {
//		this.imagesLocation = imagesLocation;
//	}

	public String getSociosDefaultSortField() {
		return sociosDefaultSortField;
	}

	public void setSociosDefaultSortField(String sociosDefaultSortField) {
		this.sociosDefaultSortField = sociosDefaultSortField;
	}

	public String getSociosDefaultSortDirection() {
		return sociosDefaultSortDirection;
	}

	public void setSociosDefaultSortDirection(String sociosDefaultSortDirection) {
		this.sociosDefaultSortDirection = sociosDefaultSortDirection;
	}

	public String getFacturasDefaultSortField() {
		return facturasDefaultSortField;
	}

	public void setFacturasDefaultSortField(String facturasDefaultSortField) {
		this.facturasDefaultSortField = facturasDefaultSortField;
	}

	public String getFacturasDefaultSortDirection() {
		return facturasDefaultSortDirection;
	}

	public void setFacturasDefaultSortDirection(String facturasDefaultSortDirection) {
		this.facturasDefaultSortDirection = facturasDefaultSortDirection;
	}

	public String getAgenciasDefaultSortField() {
		return agenciasDefaultSortField;
	}

	public void setAgenciasDefaultSortField(String agenciasDefaultSortField) {
		this.agenciasDefaultSortField = agenciasDefaultSortField;
	}

	public String getAgenciasDefaultSortDirection() {
		return agenciasDefaultSortDirection;
	}

	public void setAgenciasDefaultSortDirection(String agenciasDefaultSortDirection) {
		this.agenciasDefaultSortDirection = agenciasDefaultSortDirection;
	}

	public String getReclamacionesDefaultSortField() {
		return reclamacionesDefaultSortField;
	}

	public void setReclamacionesDefaultSortField(String reclamacionesDefaultSortField) {
		this.reclamacionesDefaultSortField = reclamacionesDefaultSortField;
	}

	public String getReclamacionesDefaultSortDirection() {
		return reclamacionesDefaultSortDirection;
	}

	public void setReclamacionesDefaultSortDirection(String reclamacionesDefaultSortDirection) {
		this.reclamacionesDefaultSortDirection = reclamacionesDefaultSortDirection;
	}

	public String getMainformBackcolor() {
		return mainformBackcolor;
	}

	public void setMainformBackcolor(String mainformBackcolor) {
		this.mainformBackcolor = mainformBackcolor;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}
}
