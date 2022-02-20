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
@Table(name = "FORMAS_PAGO")
public class FormaPago {

    @Id
    @Column(name="FORMAPAGO_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="DESCRIPCION", length=60, nullable=false, unique=true)
    private String descripcion;
    
    @Column(name="TEXTO_FACTURA", length=70, nullable=false, unique=true)
    private String textoFactura;
    
    @OneToMany(targetEntity=Socio.class, mappedBy="formaPago", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Socio> socios;
    
    @OneToMany(targetEntity=Factura.class, mappedBy="formaPago", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Factura> facturas;
	
	public FormaPago() {
		super();
	}

	public FormaPago(String descripcion, String textoFactura) {
		super();
		this.descripcion = descripcion;
		this.textoFactura = textoFactura;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Set<Socio> getSocios() {
		return socios;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormaPago )) return false;
        return id != null && id.equals(((FormaPago) o).getId());
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
