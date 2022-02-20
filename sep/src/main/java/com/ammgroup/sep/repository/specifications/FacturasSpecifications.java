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
import com.ammgroup.sep.controller.config.filter.FacturaFilter;
import com.ammgroup.sep.model.Factura_;
import com.ammgroup.sep.model.FormaPago;
import com.ammgroup.sep.model.SerieFacturas;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.EstadoFactura;
import com.ammgroup.sep.model.Factura;
import com.ammgroup.sep.repository.FacturaRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

@Component
public class FacturasSpecifications {
	
	@Autowired
	private ModuloUtilidades mutils;

	@Autowired
	FacturaRepository factRepository;
	
	@Autowired
	SEPPropertiesFile sepprop;

	public List<Factura> getFilteredFacturas(FacturaFilter facfilter) {
		
		var spwrapper = new Object(){ Specification<Factura> spfac = null; boolean firstsp = true; };
		
		Optional<String> optTit = Optional.ofNullable(facfilter.getTitular());
			optTit.ifPresent((x) -> {
				
				if (x.length() > 0) {

					if (spwrapper.firstsp) {
						spwrapper.spfac = Specification.where(titularLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spfac = (spwrapper.spfac).and(titularLike(x));
					}
				}
			});

		Optional<String> optCifnif = Optional.ofNullable(facfilter.getCifnif());
			optCifnif.ifPresent((x) -> {
				
				if (x.length() > 0) {				

					if (spwrapper.firstsp) {
						spwrapper.spfac = Specification.where(cifnifLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spfac = (spwrapper.spfac).and(cifnifLike(x));
					}
				}
			});
		
		Optional<String> optDir = Optional.ofNullable(facfilter.getDireccion());
			optDir.ifPresent((x) -> {
				
				if (x.length() > 0) {	
				
					if (spwrapper.firstsp) {
						spwrapper.spfac = Specification.where(direccionLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spfac = (spwrapper.spfac).and(direccionLike(x));
					}
				}
			});
			
		Optional<SerieFacturas> optSfac = Optional.ofNullable(facfilter.getSerieFacturas());
			optSfac.ifPresent((x) -> {
				
				if (spwrapper.firstsp) {
					spwrapper.spfac = where(serieFacturasIs(x));
					spwrapper.firstsp = false;
				} else {
					spwrapper.spfac = (spwrapper.spfac).and(serieFacturasIs(x));
				}
			});
			
		Optional<Agencia> optAge = Optional.ofNullable(facfilter.getAgencia());
			optAge.ifPresent((x) -> {
				
				if (spwrapper.firstsp) {
					spwrapper.spfac = where(agenciaIs(x));
					spwrapper.firstsp = false;
				} else {
					spwrapper.spfac = (spwrapper.spfac).and(agenciaIs(x));
				}
			});
			
		Optional<String> optRef = Optional.ofNullable(facfilter.getReferencia());
			optRef.ifPresent((x) -> {
				
				if (x.length() > 0) {
				
					if (spwrapper.firstsp) {
						spwrapper.spfac = Specification.where(referenciaLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spfac = (spwrapper.spfac).and(referenciaLike(x));
					}
				}
			});
		
		Optional<FormaPago> optFpag = Optional.ofNullable(facfilter.getFormaPago());
			optFpag.ifPresent((x) -> {
				
				if (spwrapper.firstsp) {
					spwrapper.spfac = where(formaPagoIs(x));
					spwrapper.firstsp = false;
				} else {
					spwrapper.spfac = (spwrapper.spfac).and(formaPagoIs(x));
				}
			});
			
		Optional<EstadoFactura> optEfac = Optional.ofNullable(facfilter.getEstadoFactura());
			optEfac.ifPresent((x) -> {
				
				if (spwrapper.firstsp) {
					spwrapper.spfac = where(estadoFacturaIs(x));
					spwrapper.firstsp = false;
				} else {
					spwrapper.spfac = (spwrapper.spfac).and(estadoFacturaIs(x));
				}
			});
			
		Optional<String> optMar = Optional.ofNullable(facfilter.getMarcador());
			optMar.ifPresent((x) -> {
				
				if (x.length() > 0) {
				
					if (spwrapper.firstsp) {
						spwrapper.spfac = Specification.where(marcadorLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spfac = (spwrapper.spfac).and(marcadorLike(x));
					}
				}
			});
			
		Optional<Date> optDateIni = Optional.ofNullable(facfilter.getFechaFacturaInicial());
		Optional<Date> optDateFin = Optional.ofNullable(facfilter.getFechaFacturaFinal());

			//Check if both dates are set
			if (optDateIni.isPresent() && optDateFin.isPresent()) {

				if (spwrapper.firstsp) {
					spwrapper.spfac = Specification.where(fechaFacturaBetweenDates(facfilter.getFechaFacturaInicial(), facfilter.getFechaFacturaFinal()));
					spwrapper.firstsp = false;
				} else {
					spwrapper.spfac = (spwrapper.spfac).and(fechaFacturaBetweenDates(facfilter.getFechaFacturaInicial(), facfilter.getFechaFacturaFinal()));
				}
			}

		var listwrapper = new Object(){ List<Factura> itemslist; };
		
		Optional<Sort> sortOpt = Optional.ofNullable(mutils.getDefaultFacturasSort());
			sortOpt.ifPresentOrElse((x) -> {
				listwrapper.itemslist = factRepository.findAll(spwrapper.spfac, x);
			}, () -> {
				listwrapper.itemslist = factRepository.findAll(spwrapper.spfac);
			});

		return listwrapper.itemslist;

	}
	
	private Specification<Factura> titularLike(String nombre) {
		
		return (root, query, criteriaBuilder)
				-> criteriaBuilder.like(root.get(Factura_.TITULAR), "%"+nombre+"%");
	}
	
	private Specification<Factura> cifnifLike(String cifnif) {
	
		return (root, query, criteriaBuilder)
				-> criteriaBuilder.like(root.get(Factura_.CIFNIF), "%"+cifnif+"%");
	}
	
	private Specification<Factura> direccionLike(String loc) {
		
		return (root, query, criteriaBuilder) 
				-> criteriaBuilder.like(root.get(Factura_.DIRECCION), "%"+loc+"%");
	}
	
	private Specification<Factura> serieFacturasIs(SerieFacturas sf) {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.equal(root.get(Factura_.SERIE), sf);
	}
	
	private Specification<Factura> agenciaIs(Agencia ag) {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.equal(root.get(Factura_.AGENCIA), ag);
	}
	
	private Specification<Factura> referenciaLike(String ref) {
		
		return (root, query, criteriaBuilder) 
				-> criteriaBuilder.like(root.get(Factura_.REFERENCIA), "%"+ref+"%");
	}

	private Specification<Factura> formaPagoIs(FormaPago fp) {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.equal(root.get(Factura_.FORMA_PAGO), fp);
	}
	
	private Specification<Factura> estadoFacturaIs(EstadoFactura ef) {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.equal(root.get(Factura_.ESTADO_FACTURA), ef);
	}
	
	private Specification<Factura> marcadorLike(String mar) {
		
		return (root, query, criteriaBuilder) 
				-> criteriaBuilder.like(root.get(Factura_.MARCADOR), "%"+mar+"%");
	}
	
	private Specification<Factura> fechaFacturaBetweenDates(Date dtini, Date dtfin) {
		
		return (root, query, criteriaBuilder) 
				-> criteriaBuilder.between(root.get(Factura_.FECHA_FACTURA), dtini, dtfin);
	}
}
