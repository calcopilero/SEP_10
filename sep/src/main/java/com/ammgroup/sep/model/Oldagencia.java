package com.ammgroup.sep.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Agencias_old")
public class Oldagencia {

    @Column(name="CIF_Agencia", length=255, nullable=true, unique=false)
    private String cif;
    
    @Id
    @Column(name="Nombre_Agencia", length=255, nullable=true, unique=false)
    private String nombre;
    
    @Column(name="Direccion_Agencia", length=255, nullable=true, unique=false)
    private String direccion;
    
    @Column(name="CP_Agencia", length=255, nullable=true, unique=false)
    private String cp;
    
    @Column(name="Localidad_Agencia", length=255, nullable=true, unique=false)
    private String localidad;
    
    @Column(name="Provincia_Agencia", length=255, nullable=true, unique=false)
    private String provincia;
    
    @Column(name="Pais_Agencia", length=255, nullable=true, unique=false)
    private String pais;
    
    @Column(name="Email_Agencia", length=255, nullable=true, unique=false)
    private String email;
    
    @Column(name="Telefono_Agencia", length=255, nullable=true, unique=false)
    private String telefono;
    
    @Column(name="Persona_Contacto_Agencia", length=255, nullable=true, unique=false)
    private String personaContacto;

	public Oldagencia() {
		super();
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
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
    
    
}
