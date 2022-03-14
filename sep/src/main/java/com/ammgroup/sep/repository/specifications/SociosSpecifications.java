package com.ammgroup.sep.repository.specifications;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import static org.springframework.data.jpa.domain.Specification.*;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.config.SEPPropertiesFile;
import com.ammgroup.sep.controller.config.filter.SocioFilter;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.ModalidadSocio;
import com.ammgroup.sep.model.ModoAcceso;
import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.model.Socio;
import com.ammgroup.sep.model.Socio_;
import com.ammgroup.sep.model.ZonaPostal;
import com.ammgroup.sep.repository.SocioRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

@Component
public class SociosSpecifications {
	
	@Autowired
	private ModuloUtilidades mutils;

	@Autowired
	SocioRepository socioRepository;
	
	@Autowired
	SEPPropertiesFile sepprop;

	public List<Socio> getFilteredSocios(SocioFilter socfilter) {
		
		var spwrapper = new Object(){ Specification<Socio> spsoc = null; boolean firstsp = true; };
		
		Optional<String> optNombre = Optional.ofNullable(socfilter.getNombre());
			optNombre.ifPresent((x) -> {
				
				if (x.length() > 0) {
				
					if (spwrapper.firstsp) {
						spwrapper.spsoc = where(nombreLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spsoc = (spwrapper.spsoc).and(nombreLike(x));
					}
				}
			});
			
		Optional<String> optApell = Optional.ofNullable(socfilter.getApellidos());
			optApell.ifPresent((x) -> {
				
				if (x.length() > 0) {
				
					if (spwrapper.firstsp) {
						spwrapper.spsoc = where(apellidosLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spsoc = (spwrapper.spsoc).and(apellidosLike(x));
					}
				}
			});

		Optional<String> optCifnif = Optional.ofNullable(socfilter.getCifnif());
			optCifnif.ifPresent((x) -> {
				
				if (x.length() > 0) {
				
					if (spwrapper.firstsp) {
						spwrapper.spsoc = where(cifnifLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spsoc = (spwrapper.spsoc).and(cifnifLike(x));
					}
				}
			});
		
		Optional<String> optLocalidad = Optional.ofNullable(socfilter.getLocalidad());
			optLocalidad.ifPresent((x) -> {
				
				if (x.length() > 0) {
					
					if (spwrapper.firstsp) {
						spwrapper.spsoc = where(localidadLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spsoc = (spwrapper.spsoc).and(localidadLike(x));
					}
				}
			});
		
		Optional<Provincia> optProv = Optional.ofNullable(socfilter.getProvincia());
			optProv.ifPresent((x) -> {
				
				if (spwrapper.firstsp) {
					spwrapper.spsoc = where(socioProvinciaIs(x));
					spwrapper.firstsp = false;
				} else {
					spwrapper.spsoc = (spwrapper.spsoc).and(socioProvinciaIs(x));
				}
			});
		
		Optional<Pais> optPais = Optional.ofNullable(socfilter.getPais());
			optPais.ifPresent((x) -> {
				
				if (spwrapper.firstsp) {
					spwrapper.spsoc = where(socioPaisIs(x));
					spwrapper.firstsp = false;
				} else {
					spwrapper.spsoc = (spwrapper.spsoc).and(socioPaisIs(x));
				}
			});
		
		Optional<ZonaPostal> optZPost = Optional.ofNullable(socfilter.getZonaPostal());
			optZPost.ifPresent((x) -> {
				
				if (spwrapper.firstsp) {
					spwrapper.spsoc = where(socioZonaPostalIs(x));
					spwrapper.firstsp = false;
				} else {
					spwrapper.spsoc = (spwrapper.spsoc).and(socioZonaPostalIs(x));
				}
			});

		Optional<String> optEmail = Optional.ofNullable(socfilter.getEmail());
			optEmail.ifPresent((x) -> {
				
				if (x.length() > 0) {
					
					if (spwrapper.firstsp) {
						spwrapper.spsoc = where(emailLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spsoc = (spwrapper.spsoc).and(emailLike(x));
					}
				}
			});
		
		Optional<String> optTelef = Optional.ofNullable(socfilter.getTelefono());
			optTelef.ifPresent((x) -> {
				
				if (x.length() > 0) {
					
					if (spwrapper.firstsp) {
						spwrapper.spsoc = where(telefonoLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spsoc = (spwrapper.spsoc).and(telefonoLike(x));
					}
				}	
			});
		
		Optional<ModalidadSocio> optMods = Optional.ofNullable(socfilter.getModalidad());
			optMods.ifPresent((x) -> {
				
				if (spwrapper.firstsp) {
					spwrapper.spsoc = where(socioModalidadSocioIs(x));
					spwrapper.firstsp = false;
				} else {
					spwrapper.spsoc = (spwrapper.spsoc).and(socioModalidadSocioIs(x));
				}
			});
		
		Optional<Agencia> optAge = Optional.ofNullable(socfilter.getAgencia());
			optAge.ifPresent((x) -> {
				
				if (spwrapper.firstsp) {
					spwrapper.spsoc = where(socioAgenciaIs(x));
					spwrapper.firstsp = false;
				} else {
					spwrapper.spsoc = (spwrapper.spsoc).and(socioAgenciaIs(x));
				}
			});
		
		Optional<ModoAcceso> optMacc = Optional.ofNullable(socfilter.getModoAcceso());
			optMacc.ifPresent((x) -> {
				
				if (spwrapper.firstsp) {
					spwrapper.spsoc = where(socioModoAccesoIs(x));
					spwrapper.firstsp = false;
				} else {
					spwrapper.spsoc = (spwrapper.spsoc).and(socioModoAccesoIs(x));
				}
			});
			
		Optional<Date> optDateIni = Optional.ofNullable(socfilter.getFechaAltaInicial());
		Optional<Date> optDateFin = Optional.ofNullable(socfilter.getFechaAltaFinal());

			//Check if both dates are set
			if (optDateIni.isPresent() && optDateFin.isPresent()) {

				if (spwrapper.firstsp) {
					spwrapper.spsoc = Specification.where(fechaAltaBetweenDates(socfilter.getFechaAltaInicial(), socfilter.getFechaAltaFinal()));
					spwrapper.firstsp = false;
				} else {
					spwrapper.spsoc = (spwrapper.spsoc).and(fechaAltaBetweenDates(socfilter.getFechaAltaInicial(), socfilter.getFechaAltaFinal()));
				}
			}
		
		Optional<Boolean> optAct = Optional.ofNullable(socfilter.getActivo());
			optAct.ifPresent((x) -> {

				//The filter only applies if true
				if (x) {

					if (spwrapper.firstsp) {
						spwrapper.spsoc = where(socioIsActivo());
						spwrapper.firstsp = false;
					} else {
						spwrapper.spsoc = (spwrapper.spsoc).and(socioIsActivo());
					}
				}
				
			});
		
		Optional<Boolean> optBaja = Optional.ofNullable(socfilter.getBaja());
			optBaja.ifPresent((x) -> {

				//The filter only applies if true
				if (x) {

					if (spwrapper.firstsp) {
						spwrapper.spsoc = where(socioIsBaja());
						spwrapper.firstsp = false;
					} else {
						spwrapper.spsoc = (spwrapper.spsoc).and(socioIsBaja());
					}
				}
			});
			
		Optional<String> optMarc = Optional.ofNullable(socfilter.getMarcador());
			optMarc.ifPresent((x) -> {
				
				if (x.length() > 0) {
					
					if (spwrapper.firstsp) {
						spwrapper.spsoc = where(marcadorLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spsoc = (spwrapper.spsoc).and(marcadorLike(x));
					}
				}	
			});
			
		Optional<Boolean> optFact = Optional.ofNullable(socfilter.getFactura());
			optFact.ifPresent((x) -> {

				//The filter only applies if true
				if (x) {

					if (spwrapper.firstsp) {
						spwrapper.spsoc = where(socioIsFactura());
						spwrapper.firstsp = false;
					} else {
						spwrapper.spsoc = (spwrapper.spsoc).and(socioIsFactura());
					}
				}
			});
			
		Optional<Boolean> optLdis = Optional.ofNullable(socfilter.getListaDistribucion());
			optLdis.ifPresent((x) -> {

				//The filter only applies if true
				if (x) {

					if (spwrapper.firstsp) {
						spwrapper.spsoc = where(socioIsListaDistribucion());
						spwrapper.firstsp = false;
					} else {
						spwrapper.spsoc = (spwrapper.spsoc).and(socioIsListaDistribucion());
					}
				}
			});
			
		Optional<String> optCjdir = Optional.ofNullable(socfilter.getCargosJuntaDirectiva());
			optCjdir.ifPresent((x) -> {
				
				if (x.length() > 0) {
					
					if (spwrapper.firstsp) {
						spwrapper.spsoc = where(cargosJuntaDirectivaLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spsoc = (spwrapper.spsoc).and(cargosJuntaDirectivaLike(x));
					}
				}	
			});
			
		Optional<Boolean> optJdir = Optional.ofNullable(socfilter.getJuntaDirectivaActual());
			optJdir.ifPresent((x) -> {

				//The filter only applies if true
				if (x) {

					if (spwrapper.firstsp) {
						spwrapper.spsoc = where(socioIsJuntaDirectivaActual());
						spwrapper.firstsp = false;
					} else {
						spwrapper.spsoc = (spwrapper.spsoc).and(socioIsJuntaDirectivaActual());
					}
				}
			});
			
		Optional<Date> optDatefbIni = Optional.ofNullable(socfilter.getFechaBajaInicial());
		Optional<Date> optDatefbFin = Optional.ofNullable(socfilter.getFechaBajaFinal());

		//Check if both dates are set
		if (optDatefbIni.isPresent() && optDatefbFin.isPresent()) {

			if (spwrapper.firstsp) {
				spwrapper.spsoc = Specification.where(fechaBajaBetweenDates(socfilter.getFechaBajaInicial(), socfilter.getFechaBajaFinal()));
				spwrapper.firstsp = false;
			} else {
				spwrapper.spsoc = (spwrapper.spsoc).and(fechaBajaBetweenDates(socfilter.getFechaBajaInicial(), socfilter.getFechaBajaFinal()));
			}
		}
		
		var listwrapper = new Object(){ List<Socio> itemslist; };
		
		Optional<Sort> sortOpt = Optional.ofNullable(mutils.getDefaultSociosSort());
			sortOpt.ifPresentOrElse((x) -> {
				listwrapper.itemslist = socioRepository.findAll(spwrapper.spsoc, x);
			}, () -> {
				listwrapper.itemslist = socioRepository.findAll(spwrapper.spsoc);
			});
		
		//return socioRepository.findAll(spwrapper.spsoc, Sort.by(Sort.Direction.DESC, "codigoSocio"));
		return listwrapper.itemslist;

	}
	
	private Specification<Socio> nombreLike(String nombre) {
		
		return (root, query, criteriaBuilder)
				-> criteriaBuilder.like(root.get(Socio_.NOMBRE), "%"+nombre+"%");
	}
	
	private Specification<Socio> apellidosLike(String apellidos) {
		
		return (root, query, criteriaBuilder)
				-> criteriaBuilder.like(root.get(Socio_.APELLIDOS), "%"+apellidos+"%");
	}
	
	private Specification<Socio> cifnifLike(String cifnif) {
	
		return (root, query, criteriaBuilder)
				-> criteriaBuilder.like(root.get(Socio_.CIFNIF), "%"+cifnif+"%");
	}
	
	private Specification<Socio> localidadLike(String loc) {
		
		return (root, query, criteriaBuilder) 
				-> criteriaBuilder.like(root.get(Socio_.LOCALIDAD), "%"+loc+"%");
	}

	private Specification<Socio> socioProvinciaIs(Provincia pr) {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.equal(root.get(Socio_.PROVINCIA), pr);
	}

	private Specification<Socio> socioPaisIs(Pais pa) {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.equal(root.get(Socio_.PAIS), pa);
	}

	private Specification<Socio> socioZonaPostalIs(ZonaPostal zp) {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.equal(root.get(Socio_.ZONA_POSTAL), zp);
	}

	private Specification<Socio> emailLike(String email) {
		
		return (root, query, criteriaBuilder) 
				-> criteriaBuilder.like(root.get(Socio_.EMAIL), "%"+email+"%");
	}
	
	private Specification<Socio> telefonoLike(String tel) {
		
		return (root, query, criteriaBuilder) 
				-> criteriaBuilder.like(root.get(Socio_.TELEFONO), "%"+tel+"%");
	}
	
	private Specification<Socio> socioModalidadSocioIs(ModalidadSocio ms) {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.equal(root.get(Socio_.MODALIDAD), ms);
	}
	
	private Specification<Socio> socioAgenciaIs(Agencia ag) {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.equal(root.get(Socio_.AGENCIA), ag);
	}
	
	private Specification<Socio> socioModoAccesoIs(ModoAcceso ms) {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.equal(root.get(Socio_.MODO_ACCESO), ms);
	}
	
	private Specification<Socio> fechaAltaBetweenDates(Date dtini, Date dtfin) {
		
		return (root, query, criteriaBuilder)
				-> criteriaBuilder.between(root.get(Socio_.FECHA_ALTA), dtini, dtfin);
	}
	
	private Specification<Socio> socioIsActivo() {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.isFalse(root.get(Socio_.BAJA));
				//-> criteriaBuilder.equal(root.get(Socio_.BAJA), false);
	}
	
	private Specification<Socio> socioIsBaja() {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.isTrue(root.get(Socio_.BAJA));
				//-> criteriaBuilder.equal(root.get(Socio_.BAJA), true);
	}
	
	private Specification<Socio> marcadorLike(String marc) {
		
		return (root, query, criteriaBuilder) 
				-> criteriaBuilder.like(root.get(Socio_.MARCADOR), "%"+marc+"%");
	}
	
	private Specification<Socio> socioIsFactura() {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.isTrue(root.get(Socio_.FACTURA));
				//-> criteriaBuilder.equal(root.get(Socio_.BAJA), false);
	}

	private Specification<Socio> socioIsListaDistribucion() {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.isTrue(root.get(Socio_.LISTA_DISTRIBUCION));
				//-> criteriaBuilder.equal(root.get(Socio_.BAJA), false);
	}
	
	private Specification<Socio> cargosJuntaDirectivaLike(String cjdir) {
		
		return (root, query, criteriaBuilder) 
				-> criteriaBuilder.like(root.get(Socio_.CARGOS_JUNTA_DIRECTIVA), "%"+cjdir+"%");
	}
	
	private Specification<Socio> socioIsJuntaDirectivaActual() {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.isTrue(root.get(Socio_.JUNTA_DIRECTIVA_ACTUAL));
				//-> criteriaBuilder.equal(root.get(Socio_.BAJA), false);
	}
	
	private Specification<Socio> fechaBajaBetweenDates(Date dtini, Date dtfin) {
		
		return (root, query, criteriaBuilder)
				-> criteriaBuilder.between(root.get(Socio_.FECHA_BAJA), dtini, dtfin);
	}
}
