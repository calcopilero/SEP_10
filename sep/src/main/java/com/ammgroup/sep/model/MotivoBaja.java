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
@Table(name = "MOTIVOS_BAJAS")
public class MotivoBaja {

    @Id
    @Column(name="MOTIVOBAJA_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="DESCRIPCION", length=60, nullable=false, unique=true)
    private String descripcion;
    
    @OneToMany(targetEntity=Socio.class, mappedBy="motivoBaja", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Socio> socios;
	
	public MotivoBaja() {
		super();
	}

	public MotivoBaja(String descripcion) {
		super();
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
        if (!(o instanceof MotivoBaja )) return false;
        return id != null && id.equals(((MotivoBaja) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {  
    	  return descripcion.strip();  
    } 
}
