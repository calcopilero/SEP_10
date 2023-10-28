package com.ammgroup.sep.controller.dao;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.SerieFacturas;

@Component
public class PaFactura {

	private Date fechaFactura;
	private SerieFacturas serieFacturas;
	
	public PaFactura() {
		super();
	}
	
	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public SerieFacturas getSerieFacturas() {
		return serieFacturas;
	}

	public void setSerieFacturas(SerieFacturas serieFacturas) {
		this.serieFacturas = serieFacturas;
	}
	
	public void clean() {
		
		this.serieFacturas = null;
		this.fechaFactura = null;
	}
}
