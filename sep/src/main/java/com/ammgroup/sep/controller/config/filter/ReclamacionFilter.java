package com.ammgroup.sep.controller.config.filter;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.EstadoReclamacion;

@Component
public class ReclamacionFilter {
	
	private Date fechaReclamacionInicial;
	private Date fechaReclamacionFinal;
	private Agencia agencia;
	private EstadoReclamacion estadoReclamacion;
	
	public ReclamacionFilter() {
		super();
	}

	public Date getFechaReclamacionInicial() {
		return fechaReclamacionInicial;
	}

	public void setFechaReclamacionInicial(Date fechaReclamacionInicial) {
		this.fechaReclamacionInicial = fechaReclamacionInicial;
	}

	public Date getFechaReclamacionFinal() {
		return fechaReclamacionFinal;
	}

	public void setFechaReclamacionFinal(Date fechaReclamacionFinal) {
		this.fechaReclamacionFinal = fechaReclamacionFinal;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public EstadoReclamacion getEstadoReclamacion() {
		return estadoReclamacion;
	}

	public void setEstadoReclamacion(EstadoReclamacion estadoReclamacion) {
		this.estadoReclamacion = estadoReclamacion;
	}
	
	public int containsFilters() {
		
		var cfwrapper = new Object(){ int cf = 0; };
		
		Optional<Date> optDateIni = Optional.ofNullable(fechaReclamacionInicial);
		Optional<Date> optDateFin = Optional.ofNullable(fechaReclamacionFinal);

		//Check if both dates are set
		if (optDateIni.isPresent() && optDateFin.isPresent()) {
			cfwrapper.cf++;
		}
		
		Optional<EstadoReclamacion> optErec = Optional.ofNullable(estadoReclamacion);
			optErec.ifPresent((x) -> { cfwrapper.cf++; });
			
		Optional<Agencia> optAge = Optional.ofNullable(agencia);
			optAge.ifPresent((x) -> { cfwrapper.cf++; });
			
		return cfwrapper.cf; 
	}
}
