package com.ammgroup.sep.controller.config.filter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.model.ZonaPostal;

@Component
public class AgenciaFilter {
	
	private String nombre;
	private Boolean activa;
	private String cifnif;
	private String localidad;
	private Provincia provincia;
	private Pais pais;
	private ZonaPostal zonaPostal;
	private String email;
	private String telefono;
	private String personaContacto;
	
	public AgenciaFilter() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getActiva() {
		return activa;
	}

	public void setActiva(Boolean activa) {
		this.activa = activa;
	}

	public String getCifnif() {
		return cifnif;
	}

	public void setCifnif(String cifnif) {
		this.cifnif = cifnif;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public ZonaPostal getZonaPostal() {
		return zonaPostal;
	}

	public void setZonaPostal(ZonaPostal zonaPostal) {
		this.zonaPostal = zonaPostal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPersonaContacto() {
		return personaContacto;
	}

	public void setPersonaContacto(String personaContacto) {
		this.personaContacto = personaContacto;
	}

	public int containsFilters() {
		
		var cfwrapper = new Object(){ int cf = 0; };
		
		//In strings we also check it length > 0
		Optional<String> optNombre = Optional.ofNullable(nombre);
			optNombre.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });
		
		Optional<Boolean> optAct = Optional.ofNullable(activa);
			optAct.ifPresent((x) -> { if (x) cfwrapper.cf++; });
			
		Optional<String> optCifnif = Optional.ofNullable(cifnif);
			optCifnif.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });

		Optional<String> optLoc = Optional.ofNullable(localidad);
			optLoc.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });

		Optional<Provincia> optProv = Optional.ofNullable(provincia);
			optProv.ifPresent((x) -> { cfwrapper.cf++; });

		Optional<Pais> optPais = Optional.ofNullable(pais);
			optPais.ifPresent((x) -> { cfwrapper.cf++; });
		
		Optional<ZonaPostal> optZpost = Optional.ofNullable(zonaPostal);
			optZpost.ifPresent((x) -> { cfwrapper.cf++; });
		
		Optional<String> optEmail = Optional.ofNullable(email);
			optEmail.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });
		
		Optional<String> optTelef = Optional.ofNullable(telefono);
			optTelef.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });
		
		Optional<String> optPcont = Optional.ofNullable(personaContacto);
			optPcont.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });

		return cfwrapper.cf; 
		
	}
}
