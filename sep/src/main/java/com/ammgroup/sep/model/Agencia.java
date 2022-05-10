package com.ammgroup.sep.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "AGENCIAS")
public class Agencia {

    @Id
    @Column(name="AGENCIA_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="NOMBRE", length=95, nullable=false, unique=false)
    private String nombre;
    
    @Column(name="CIFNIF", length=25, nullable=false, unique=true)
    private String cifnif;
    
    @Column(name="DOMICILIO", length=100, nullable=true, unique=false)
    private String domicilio;
    
    @Column(name="CP", length=5, nullable=true, unique=false)
    private String cp;
    
    @Column(name="LOCALIDAD", length=70, nullable=true, unique=false)
    private String localidad;
    
    @ManyToOne
	@JoinColumn(name="PROVINCIA_ID", nullable=true)
    private Provincia provincia;
    
    @ManyToOne
	@JoinColumn(name="PAIS_ID", nullable=true)
    private Pais pais;
    
    @ManyToOne
	@JoinColumn(name="ZONAPOSTAL_ID", nullable=true)
    private ZonaPostal zonaPostal;
    
    @Column(name="TELEFONO", length=12, nullable=true, unique=false)
    private String telefono;
    
    @Column(name="EMAIL", length=80, nullable=true, unique=false)
    private String email;
    
    @Column(name="PERSONA_CONTACTO", length=70, nullable=true, unique=false)
    private String personaContacto;
    
    @Column(name="ANOTACIONES", length=500, nullable=true, unique=false)
    private String anotaciones;

    @OneToMany(targetEntity=Socio.class, mappedBy="agencia", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Socio> socios;
    
    @OneToMany(targetEntity=Factura.class, mappedBy="agencia", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Factura> facturas;
    
	public Agencia() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCifnif() {
		return cifnif;
	}

	public void setCifnif(String cifnif) {
		this.cifnif = cifnif;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPersonaContacto() {
		return personaContacto;
	}

	public void setPersonaContacto(String personaContacto) {
		this.personaContacto = personaContacto;
	}

	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public Set<Socio> getSocios() {
		return socios;
	}

	public Long getId() {
		return id;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agencia )) return false;
        return id != null && id.equals(((Agencia) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
    	return nombre.strip() + " ("+ cifnif.strip() + ")";  
    }  

}
