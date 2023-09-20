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
@Table(name = "SERIES_FACTURAS")
public class SerieFacturas {

    @Id
    @Column(name="SERIEFACTURAS_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="DESCRIPCION", length=60, nullable=false, unique=true)
    private String descripcion;
    
    @Column(name="TEXTO_INICIO_NUMERACION", length=12, nullable=false, unique=true)
    private String textoInicioNumeracion;
    
    @Column(name="RECTIFICATIVAS", nullable=false, unique=false)
    private boolean rectificativas;
    
    @Column(name="TEXTO_RECTIFICATIVA", length=70, nullable=true, unique=true)
    private String textoRectificativa;
    
    @Column(name="FACTURACION_AUTOMATICA", nullable=false, unique=false)
    private boolean facturacionAutomatica;
    
    @Column(name="TEXTO_PARA", length=70, nullable=true, unique=true)
    private String textoPara;
    
    @Column(name="FACTURAS_PROFORMA", nullable=false, unique=false)
    private boolean facturasProforma;
    
    @ManyToOne
	@JoinColumn(name="TIPOIVA_ID", nullable=true)
    private TipoIVA tipoIVA;
    
    @OneToMany(targetEntity=Factura.class, mappedBy="serie", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Factura> facturas;
    
	public SerieFacturas() {
		super();
	}

	public SerieFacturas(String descripcion, String textoInicioNumeracion, boolean rectificativas,
			String textoRectificativa, boolean facturacionAutomatica, String textoPara, boolean facturasProforma, TipoIVA tipoIVA) {
		super();
		this.descripcion = descripcion;
		this.textoInicioNumeracion = textoInicioNumeracion;
		this.rectificativas = rectificativas;
		this.textoRectificativa = textoRectificativa;
		this.facturacionAutomatica = facturacionAutomatica;
		this.facturasProforma = facturasProforma;
		this.textoPara = textoPara;
		this.tipoIVA = tipoIVA;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTextoInicioNumeracion() {
		return textoInicioNumeracion;
	}

	public void setTextoInicioNumeracion(String textoInicioNumeracion) {
		this.textoInicioNumeracion = textoInicioNumeracion;
	}

	public boolean isRectificativas() {
		return rectificativas;
	}

	public void setRectificativas(boolean rectificativas) {
		this.rectificativas = rectificativas;
	}

	public String getTextoRectificativa() {
		return textoRectificativa;
	}

	public void setTextoRectificativa(String textoRectificativa) {
		this.textoRectificativa = textoRectificativa;
	}

	public boolean isAutomatica() {
		return facturacionAutomatica;
	}

	public void setAutomatica(boolean automatica) {
		this.facturacionAutomatica = automatica;
	}

	public String getTextoPara() {
		return textoPara;
	}

	public void setTextoPara(String textoPara) {
		this.textoPara = textoPara;
	}

	public boolean isFacturasProforma() {
		return facturasProforma;
	}

	public void setFacturasProforma(boolean facturasProforma) {
		this.facturasProforma = facturasProforma;
	}

	public TipoIVA getTipoIVA() {
		return tipoIVA;
	}

	public void setTipoIVA(TipoIVA tipoIVA) {
		this.tipoIVA = tipoIVA;
	}

	public Long getId() {
		return id;
	}

    public Set<Factura> getFacturas() {
		return facturas;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SerieFacturas )) return false;
        return id != null && id.equals(((SerieFacturas) o).getId());
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
