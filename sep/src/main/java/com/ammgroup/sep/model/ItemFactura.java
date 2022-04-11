package com.ammgroup.sep.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ITEMS_FACTURAS")
public class ItemFactura {

    @Id
    @Column(name="ITEMFACTURA_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
	@JoinColumn(name="FACTURA_ID", nullable=true)
    private Factura factura;
    
    @Column(name="CONCEPTO", length=250, nullable=false, unique=true)
    private String concepto;
    
    @Column(name="IMPORTE", precision=8, scale=2, nullable=false, unique=false)
    private Double importe;
    
	public ItemFactura() {
		super();
	}

	public ItemFactura(Factura factura, String concepto, Double importe) {
		super();
		this.factura = factura;
		this.concepto = concepto;
		this.importe = importe;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Long getId() {
		return id;
	}

}
