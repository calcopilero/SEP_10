package com.ammgroup.sep.controller.config.filter;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.EstadoFactura;
import com.ammgroup.sep.model.FormaPago;
import com.ammgroup.sep.model.SerieFacturas;
import com.ammgroup.sep.model.Socio;

@Component
public class FacturaFilter {

	private Date fechaFacturaInicial;
	private Date fechaFacturaFinal;
	private String titular;
	private String cifnif;
	private String direccion;
	private String numeroCompuesto;
	private Socio socio;
	private Agencia agencia;
	private SerieFacturas serieFacturas;
	private String referencia;
	private FormaPago formaPago;
	private EstadoFactura estadoFactura;
	private String marcador;
	
	public FacturaFilter() {
		super();
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getCifnif() {
		return cifnif;
	}

	public void setCifnif(String cifnif) {
		this.cifnif = cifnif;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getNumeroCompuesto() {
		return numeroCompuesto;
	}

	public void setNumeroCompuesto(String numeroCompuesto) {
		this.numeroCompuesto = numeroCompuesto;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public SerieFacturas getSerieFacturas() {
		return serieFacturas;
	}

	public void setSerieFacturas(SerieFacturas serieFacturas) {
		this.serieFacturas = serieFacturas;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public FormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

	public EstadoFactura getEstadoFactura() {
		return estadoFactura;
	}

	public void setEstadoFactura(EstadoFactura estadoFactura) {
		this.estadoFactura = estadoFactura;
	}

	public String getMarcador() {
		return marcador;
	}

	public void setMarcador(String marcador) {
		this.marcador = marcador;
	}

	public Date getFechaFacturaInicial() {
		return fechaFacturaInicial;
	}

	public void setFechaFacturaInicial(Date fechaFacturaInicial) {
		this.fechaFacturaInicial = fechaFacturaInicial;
	}

	public Date getFechaFacturaFinal() {
		return fechaFacturaFinal;
	}

	public void setFechaFacturaFinal(Date fechaFacturaFinal) {
		this.fechaFacturaFinal = fechaFacturaFinal;
	}

	public int containsFilters() {
		
		var cfwrapper = new Object(){ int cf = 0; };
		
		Optional<Date> optDateIni = Optional.ofNullable(fechaFacturaInicial);
		Optional<Date> optDateFin = Optional.ofNullable(fechaFacturaFinal);

		//Check if both dates are set
		if (optDateIni.isPresent() && optDateFin.isPresent()) {
			cfwrapper.cf++;
		}
		
		//In strings we also check it length > 0
		Optional<String> optTitular = Optional.ofNullable(titular);
			optTitular.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });
		
		Optional<String> optCifnif = Optional.ofNullable(cifnif);
			optCifnif.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });

		Optional<String> optDir = Optional.ofNullable(direccion);
			optDir.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });
		
		Optional<String> optNcomp = Optional.ofNullable(numeroCompuesto);
			optNcomp.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });
		
		Optional<Socio> optSoc = Optional.ofNullable(socio);
			optSoc.ifPresent((x) -> { cfwrapper.cf++; });
			
		Optional<Agencia> optAge = Optional.ofNullable(agencia);
			optAge.ifPresent((x) -> { cfwrapper.cf++; });
			
		Optional<String> optRef = Optional.ofNullable(referencia);
			optRef.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });
		
		Optional<SerieFacturas> optSfac = Optional.ofNullable(serieFacturas);
			optSfac.ifPresent((x) -> { cfwrapper.cf++; });
			
		Optional<FormaPago> optFpag = Optional.ofNullable(formaPago);
			optFpag.ifPresent((x) -> { cfwrapper.cf++; });
		
		Optional<EstadoFactura> optEfac = Optional.ofNullable(estadoFactura);
			optEfac.ifPresent((x) -> { cfwrapper.cf++; });
		
		Optional<String> optMarc = Optional.ofNullable(marcador);
			optMarc.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });

		return cfwrapper.cf; 
		
	}

}
