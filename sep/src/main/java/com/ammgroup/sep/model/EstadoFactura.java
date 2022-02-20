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
@Table(name = "ESTAODS_FACTURAS")
public class EstadoFactura {

    @Id
    @Column(name="ESTADOFACTURA_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="DESCRIPCION", length=60, nullable=false, unique=true)
    private String descripcion;
    
    @Column(name="ESTADO_POR_DEFECTO", nullable=false, unique=false)
    private boolean estadoPorDefecto;
    
    @Column(name="ESTADO_RECTIFICATIVAS", nullable=false, unique=false)
    private boolean estadoRectificativas;
    
    @OneToMany(targetEntity=Factura.class, mappedBy="estadoFactura", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Factura> facturas;

	public Long getId() {
		return id;
	}

	public EstadoFactura() {
		super();
	}

	public EstadoFactura(String descripcion, boolean estadoPorDefecto, boolean estadoRectificativas) {
		super();
		this.descripcion = descripcion;
		this.estadoPorDefecto = estadoPorDefecto;
		this.estadoRectificativas = estadoRectificativas;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

    public boolean isEstadoPorDefecto() {
		return estadoPorDefecto;
	}

	public void setEstadoPorDefecto(boolean estadoPorDefecto) {
		this.estadoPorDefecto = estadoPorDefecto;
	}

	public boolean isEstadoRectificativas() {
		return estadoRectificativas;
	}

	public void setEstadoRectificativas(boolean estadoRectificativas) {
		this.estadoRectificativas = estadoRectificativas;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstadoFactura )) return false;
        return id != null && id.equals(((EstadoFactura) o).getId());
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
