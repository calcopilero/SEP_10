package com.ammgroup.sep.repository.specifications;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.config.SEPPropertiesFile;
import com.ammgroup.sep.controller.config.filter.AgenciaFilter;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.Agencia_;
import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.model.ZonaPostal;
import com.ammgroup.sep.repository.AgenciaRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

@Component
public class AgenciasSpecifications {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	AgenciaRepository agenciaRepository;
	
	@Autowired
	SEPPropertiesFile sepprop;

	public List<Agencia> getFilteredAgencias(AgenciaFilter agfilter) {
		
		var spwrapper = new Object(){ Specification<Agencia> spage = null; boolean firstsp = true; };
		
		Optional<String> optNombre = Optional.ofNullable(agfilter.getNombre());
			optNombre.ifPresent((x) -> {
				
				if (x.length() > 0) {
				
					if (spwrapper.firstsp) {
						spwrapper.spage = Specification.where(nombreLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spage = (spwrapper.spage).and(nombreLike(x));
					}
				}
			});

		Optional<String> optCifnif = Optional.ofNullable(agfilter.getCifnif());
			optCifnif.ifPresent((x) -> {
				
				if (x.length() > 0) {
	
					if (spwrapper.firstsp) {
						spwrapper.spage = Specification.where(cifnifLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spage = (spwrapper.spage).and(cifnifLike(x));
					}
				}
			});
		
		Optional<String> optLocalidad = Optional.ofNullable(agfilter.getLocalidad());
			optLocalidad.ifPresent((x) -> {
				
				if (x.length() > 0) {
					
					if (spwrapper.firstsp) {
						spwrapper.spage = Specification.where(localidadLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spage = (spwrapper.spage).and(localidadLike(x));
					}
				}
			});
		
		Optional<Provincia> optProv = Optional.ofNullable(agfilter.getProvincia());
			optProv.ifPresent((x) -> {
				
				if (spwrapper.firstsp) {
					spwrapper.spage = Specification.where(agenciaProvinciaIs(x));
					spwrapper.firstsp = false;
				} else {
					spwrapper.spage = (spwrapper.spage).and(agenciaProvinciaIs(x));
				}
			});
		
		Optional<Pais> optPais = Optional.ofNullable(agfilter.getPais());
			optPais.ifPresent((x) -> {
				
				if (spwrapper.firstsp) {
					spwrapper.spage = Specification.where(agenciaPaisIs(x));
					spwrapper.firstsp = false;
				} else {
					spwrapper.spage = (spwrapper.spage).and(agenciaPaisIs(x));
				}
			});
			
		Optional<ZonaPostal> optZPost = Optional.ofNullable(agfilter.getZonaPostal());
			optZPost.ifPresent((x) -> {
				
				if (spwrapper.firstsp) {
					spwrapper.spage = Specification.where(agenciaZonaPostalIs(x));
					spwrapper.firstsp = false;
				} else {
					spwrapper.spage = (spwrapper.spage).and(agenciaZonaPostalIs(x));
				}
			});

		Optional<String> optEmail = Optional.ofNullable(agfilter.getEmail());
			optEmail.ifPresent((x) -> {
				
				if (x.length() > 0) {
				
					if (spwrapper.firstsp) {
						spwrapper.spage = Specification.where(emailLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spage = (spwrapper.spage).and(emailLike(x));
					}
				}
			});
		
		Optional<String> optTelef = Optional.ofNullable(agfilter.getTelefono());
			optTelef.ifPresent((x) -> {
				
				if (x.length() > 0) {
					
					if (spwrapper.firstsp) {
						spwrapper.spage = Specification.where(telefonoLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spage = (spwrapper.spage).and(telefonoLike(x));
					}
				}
			});
		
		Optional<String> optPcont = Optional.ofNullable(agfilter.getPersonaContacto());
			optPcont.ifPresent((x) -> {
				
				if (x.length() > 0) {
					
					if (spwrapper.firstsp) {
						spwrapper.spage = Specification.where(personaContactoLike(x));
						spwrapper.firstsp = false;
					} else {
						spwrapper.spage = (spwrapper.spage).and(personaContactoLike(x));
					}
				}
			});
			
		var listwrapper = new Object(){ List<Agencia> itemslist; };
		
		Optional<Sort> sortOpt = Optional.ofNullable(mutils.getDefaultAgenciasSort());
			sortOpt.ifPresentOrElse((x) -> {
				listwrapper.itemslist = agenciaRepository.findAll(spwrapper.spage, x);
			}, () -> {
				listwrapper.itemslist = agenciaRepository.findAll(spwrapper.spage);
			});

		return listwrapper.itemslist;

	}
	
	private Specification<Agencia> nombreLike(String nombre) {
		
		return (root, query, criteriaBuilder)
				-> criteriaBuilder.like(root.get(Agencia_.NOMBRE), "%"+nombre+"%");
	}
	
	private Specification<Agencia> cifnifLike(String cifnif) {
	
		return (root, query, criteriaBuilder)
				-> criteriaBuilder.like(root.get(Agencia_.CIFNIF), "%"+cifnif+"%");
	}
	
	private Specification<Agencia> localidadLike(String loc) {
		
		return (root, query, criteriaBuilder) 
				-> criteriaBuilder.like(root.get(Agencia_.LOCALIDAD), "%"+loc+"%");
	}

	private Specification<Agencia> agenciaProvinciaIs(Provincia pr) {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.equal(root.get(Agencia_.PROVINCIA), pr);
	}

	private Specification<Agencia> agenciaPaisIs(Pais pa) {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.equal(root.get(Agencia_.PAIS), pa);
	}

	private Specification<Agencia> agenciaZonaPostalIs(ZonaPostal zp) {

		return (root, query, criteriaBuilder)
				-> criteriaBuilder.equal(root.get(Agencia_.ZONA_POSTAL), zp);
	}

	private Specification<Agencia> emailLike(String email) {
		
		return (root, query, criteriaBuilder) 
				-> criteriaBuilder.like(root.get(Agencia_.EMAIL), "%"+email+"%");
	}
	
	private Specification<Agencia> telefonoLike(String tel) {
		
		return (root, query, criteriaBuilder) 
				-> criteriaBuilder.like(root.get(Agencia_.TELEFONO), "%"+tel+"%");
	}

	private Specification<Agencia> personaContactoLike(String pc) {
		
		return (root, query, criteriaBuilder) 
				-> criteriaBuilder.like(root.get(Agencia_.PERSONA_CONTACTO), "%"+pc+"%");
	}

}
