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
@Table(name = "TIPDS_IVA")
public class TipoIVA {

    @Id
    @Column(name="TIPOIVA_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="DESCRIPCION", length=60, nullable=false, unique=true)
    private String descripcion;
    
    @Column(name="PORCENTAJE", precision=5, scale=2, nullable=false, unique=false)
    private Double porcentaje;
    
    @Column(name="TEXTO_FACTURA", length=70, nullable=false, unique=true)
    private String textoFactura;
    
    @OneToMany(targetEntity=Factura.class, mappedBy="tipoIVA", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Factura> facturas;
    
    @OneToMany(targetEntity=SerieFacturas.class, mappedBy="tipoIVA", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<SerieFacturas> seriesFacturas;
 
	public TipoIVA() {
		super();
	}

	public TipoIVA(String descripcion, Double porcentaje, String textoFactura) {
		super();
		this.descripcion = descripcion;
		this.porcentaje = porcentaje;
		this.textoFactura = textoFactura;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getTextoFactura() {
		return textoFactura;
	}

	public void setTextoFactura(String textoFactura) {
		this.textoFactura = textoFactura;
	}

	public Long getId() {
		return id;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoIVA )) return false;
        return id != null && id.equals(((TipoIVA) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {  
    	  return descripcion.strip() + " ("+ porcentaje.toString() + ")";  
    }  


}
