package com.ammgroup.sep.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MODALIDADES_SOCIOS")
public class ModalidadSocio {
	
    @Id
    @Column(name="MODSOCIO_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="DESCRIPCION", length=60, nullable=false, unique=true)
    private String descripcion;
    
    @Column(name="CUOTA", precision=8, scale=2, nullable=false, unique=false)
    private Double cuota;
    
    @Column(name="CONCEPTO", length=180, nullable=false, unique=true)
    private String concepto;
    
    @Column(name="TEXTO_PARA", length=70, nullable=false, unique=true)
    private String textoPara;
    
    @OneToMany(targetEntity=Socio.class, mappedBy="modalidad", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Socio> socios;

	public ModalidadSocio() {
		super();
	}

	public ModalidadSocio(String descripcion, Double cuota, String concepto, String textoPara) {
		super();
		this.descripcion = descripcion;
		this.cuota = cuota;
		this.concepto = concepto;
		this.textoPara = textoPara;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getCuota() {
		return cuota;
	}

	public void setCuota(double cuota) {
		this.cuota = cuota;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getTextoPara() {
		return textoPara;
	}

	public void setTextoPara(String textoPara) {
		this.textoPara = textoPara;
	}

	public Long getId() {
		return id;
	}

	public Set<Socio> getSocios() {
		return socios;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModalidadSocio )) return false;
        return id != null && id.equals(((ModalidadSocio) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {  
    	  return descripcion.strip() + " ("+ cuota.toString() + ")";  
    }  

}
