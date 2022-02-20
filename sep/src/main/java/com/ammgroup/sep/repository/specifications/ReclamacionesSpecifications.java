package com.ammgroup.sep.repository.specifications;

import static org.springframework.data.jpa.domain.Specification.where;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.config.SEPPropertiesFile;
import com.ammgroup.sep.controller.config.filter.ReclamacionFilter;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.EstadoReclamacion;
import com.ammgroup.sep.model.Reclamacion;
import com.ammgroup.sep.model.Reclamacion_;
import com.ammgroup.sep.repository.ReclamacionRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

@Component
public class ReclamacionesSpecifications {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	ReclamacionRepository reclRepository;
	
	@Autowired
	SEPPropertiesFile sepprop;
	
	public List<Reclamacion> getFilteredReclamaciones(ReclamacionFilter recfilter) {
		
		var spwrapper = new Object(){ Specification<Reclamacion> sprec = null; boolean firstsp = true; };
		
		Optional<Agencia> optAge = Optional.ofNullable(recfilter.getAgencia());
			optAge.ifPresent((x) -> {
				
				if (spwrapper.firstsp) {
					spwrapper.sprec = where(reclamacionAgenciaIs(x));
					spwrapper.firstsp = false;
				} else {
					spwrapper.sprec = (spwrapper.sprec).and(reclamacionAgenciaIs(x));
				}
			});
		
		Optional<EstadoReclamacion> optErec = Optional.ofNullable(recfilter.getEstadoReclamacion());
			optErec.ifPresent((x) -> {
				
				if (spwrapper.firstsp) {
					spwrapper.sprec = where(reclamacionEstadoReclamacionIs(x));
					spwrapper.firstsp = false;
				} else {
					spwrapper.sprec = (spwrapper.sprec).and(reclamacionEstadoReclamacionIs(x));
				}
			});
		
		Optional<Date> optDateIni = Optional.ofNullable(recfilter.getFechaReclamacionInicial());
		Optional<Date> optDateFin = Optional.ofNullable(recfilter.getFechaReclamacionFinal());
	
			//Check if both dates are set
			if (optDateIni.isPresent() && optDateFin.isPresent()) {
	
				if (spwrapper.firstsp) {
					spwrapper.sprec = Specification.where(fechaReclamacionBetweenDates(recfilter.getFechaReclamacionInicial(), recfilter.getFechaReclamacionFinal()));
					spwrapper.firstsp = false;
				} else {
					spwrapper.sprec = (spwrapper.sprec).and(fechaReclamacionBetweenDates(recfilter.getFechaReclamacionInicial(), recfilter.getFechaReclamacionFinal()));
				}
			}
			
		var listwrapper = new Object(){ List<Reclamacion> itemslist; };
		
		Optional<Sort> sortOpt = Optional.ofNullable(mutils.getDefaultReclamacionesSort());
			sortOpt.ifPresentOrElse((x) -> {
				listwrapper.itemslist = reclRepository.findAll(spwrapper.sprec, x);
			}, () -> {
				listwrapper.itemslist = reclRepository.findAll(spwrapper.sprec);
			});

		return listwrapper.itemslist;

	}
	
	private Specification<Reclamacion> reclamacionAgenciaIs(Agencia ag) {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.equal(root.get(Reclamacion_.AGENCIA), ag);
	}
	
	private Specification<Reclamacion> reclamacionEstadoReclamacionIs(EstadoReclamacion er) {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.equal(root.get(Reclamacion_.ESTADO_RECLAMACION), er);
	}
	
	private Specification<Reclamacion> fechaReclamacionBetweenDates(Date dtini, Date dtfin) {
		
		return (root, query, criteriaBuilder)
				-> criteriaBuilder.between(root.get(Reclamacion_.FECHA_RECLAMACION), dtini, dtfin);
	}
}
