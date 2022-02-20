package com.ammgroup.sep.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "FACTURAS")
public class Factura {

    @Id
    @Column(name="FACTURA_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="FECHA_FACTURA", nullable=true, unique=false)
    @Temporal(TemporalType.DATE)
    private Date fechaFactura;
    
    @ManyToOne
	@JoinColumn(name="SERIEFACTURAS_ID", nullable=true)
    private SerieFacturas serie;
    
    @Column(name="NUMERO", precision=5, nullable=false, unique=false)
    private int numero;
    
    @Column(name="NUMERO_COMPUESTO", length=25, nullable=false, unique=true)
    private String numeroCompuesto;
    
    @Column(name="FACTURACION_AUTOMATICA", nullable=false, unique=false)
    private boolean facturacionAutomatica;
    
    @Column(name="FECHA_EMISION", nullable=true, unique=false)
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;
    
    @Column(name="ULTIMA_ACTUALIZACION", nullable=true, unique=false)
    @Temporal(TemporalType.DATE)
    private Date ultimaActualizacion;
    
	@ManyToOne
	@JoinColumn(name="SOCIO_ID", nullable=true)
    private Socio socio;
    
    @ManyToOne
	@JoinColumn(name="AGENCIA_ID", nullable=true)
    private Agencia agencia;
    
    @Column(name="CIFNIF", length=12, nullable=false, unique=true)
    private String cifnif;
    
    @Column(name="TITULAR", length=150, nullable=false, unique=false)
    private String titular;
    
    @Column(name="DIRECCION", length=250, nullable=false, unique=false)
    private String direccion;
    
    @OneToMany(targetEntity=ItemFactura.class, mappedBy="factura", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<ItemFactura> itemsFactura = new HashSet<ItemFactura>();
    
    @Column(name="IMP_TOTALITEMS", precision=5, scale=2, nullable=false, unique=false)
    private Double importeTotalItems;
    
    @ManyToOne
	@JoinColumn(name="DESCUENTO_ID", nullable=true)
    private Descuento descuento;
    
    @Column(name="PORC_DESCUENTO", precision=5, scale=2, nullable=false, unique=false)
    private Double porcentajeDescuento;
    
    @Column(name="IMP_DESCUENTO", precision=5, scale=2, nullable=false, unique=false)
    private Double importeDescuento;
    
    @Column(name="IMP_BASEIMPONIBLE", precision=5, scale=2, nullable=false, unique=false)
    private Double importeBaseImponible;
    
    @ManyToOne
	@JoinColumn(name="TIPOIVA_ID", nullable=false)
    private TipoIVA tipoIVA;
    
    @Column(name="PORC_TIPOIVA", precision=5, scale=2, nullable=false, unique=false)
    private Double porcentajeTipoIVA;
    
    @Column(name="IMP_TIPOIVA", precision=5, scale=2, nullable=false, unique=false)
    private Double importeTipoIVA;
    
    @Column(name="IMP_GASTOSENVIO", precision=5, scale=2, nullable=false, unique=false)
    private Double importeGastosEnvio;
    
    @Column(name="IMP_TOTAL", precision=5, scale=2, nullable=false, unique=false)
    private Double importeTotal;
    
    @Column(name="EXISTE_RECTIFICATIVA", nullable=false, unique=false)
    private boolean existeRectificativa;
    
    @OneToOne
	@JoinColumn(name="FACTURARECTIFICADA_ID", nullable=true)
    private Factura facturaRectificada;
	
    @ManyToOne
	@JoinColumn(name="ESTADOFACTURA_ID", nullable=true)
    private EstadoFactura estadoFactura;
    
    @ManyToOne
	@JoinColumn(name="FORMAPAGO_ID", nullable=false)
    private FormaPago formaPago;
    
    @Column(name="REFERENCIA", length=30, nullable=true, unique=false)
    private String referencia;
    
    @Column(name="TEXTO_MARCADOR", length=30, nullable=true, unique=false)
    private String marcador;
    
    @Column(name="TEXTO_COMPLEMENTARIO", length=250, nullable=true, unique=true)
    private String textoComplementario;
    
    @Column(name="TEXTO_FORMA_PAGO", length=70, nullable=false, unique=true)
    private String textoFormaPago;
    
    @Column(name="FACTURA_FIRMADA", nullable=false, unique=false)
    private boolean facturaFirmada;
    
    @Column(name="TEXTO_RECTIFICATIVA", length=85, nullable=true, unique=true)
    private String textoRectificativa;
    
    @Column(name="ANOTACIONES", length=250, nullable=false, unique=false)
    private String anotaciones;
    
	public Factura() {
		super();
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getUltimaActualizacion() {
		return ultimaActualizacion;
	}

	public void setUltimaActualizacion(Date ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public boolean isFacturacionAutomatica() {
		return facturacionAutomatica;
	}

	public void setFacturacionAutomatica(boolean facturacionAutomatica) {
		this.facturacionAutomatica = facturacionAutomatica;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNumeroCompuesto() {
		return numeroCompuesto;
	}

	public void setNumeroCompuesto(String numeroCompuesto) {
		this.numeroCompuesto = numeroCompuesto;
	}

	public SerieFacturas getSerie() {
		return serie;
	}

	public void setSerie(SerieFacturas serie) {
		this.serie = serie;
	}

    public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public String getCifnif() {
		return cifnif;
	}

	public void setCifnif(String cifnif) {
		this.cifnif = cifnif;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Double getImporteGastosEnvio() {
		return importeGastosEnvio;
	}

	public void setImporteGastosEnvio(Double importeGastosEnvio) {
		this.importeGastosEnvio = importeGastosEnvio;
	}

	public Double getImporteTotalItems() {
		return importeTotalItems;
	}

	public Descuento getDescuento() {
		return descuento;
	}

	public void setDescuento(Descuento descuento) {
		this.descuento = descuento;
	}

	public Double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	private void setPorcentajeDescuento(Double porcDescuento) {
		this.porcentajeDescuento = porcDescuento;
	}

	public Double getImporteDescuento() {
		return importeDescuento;
	}

	private void setImporteDescuento(Double impDescuento) {
		this.importeDescuento = impDescuento;
	}

	public Double getImporteBaseImponible() {
		return importeBaseImponible;
	}

	public TipoIVA getTipoIVA() {
		return tipoIVA;
	}

	public void setTipoIVA(TipoIVA tipoIVA) {
		this.tipoIVA = tipoIVA;
	}

	public Double getPorcentajeTipoIVA() {
		return porcentajeTipoIVA;
	}

	private void setPorcentajeTipoIVA(Double porcTipoIVA) {
		this.porcentajeTipoIVA = porcTipoIVA;
	}

	public Double getImporteTipoIVA() {
		return importeTipoIVA;
	}

	private void setImporteTipoIVA(Double impTipoIVA) {
		this.importeTipoIVA = impTipoIVA;
	}

	public Double getImpGastosEnvio() {
		return importeGastosEnvio;
	}

	public void setImpGastosEnvio(Double impGastosEnvio) {
		this.importeGastosEnvio = impGastosEnvio;
	}

	public Double getImporteTotal() {
		return importeTotal;
	}

	public boolean isExisteRectificativa() {
		return existeRectificativa;
	}

	public void setExisteRectificativa(boolean existeRectificativa) {
		this.existeRectificativa = existeRectificativa;
	}

	public Factura getFacturaRectificada() {
		return facturaRectificada;
	}

	public void setFacturaRectificada(Factura facturaRectificada) {
		this.facturaRectificada = facturaRectificada;
	}

	public EstadoFactura getEstadoFactura() {
		return estadoFactura;
	}

	public void setEstadoFactura(EstadoFactura estadoFactura) {
		this.estadoFactura = estadoFactura;
	}

	public FormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getMarcador() {
		return marcador;
	}

	public void setMarcador(String marcador) {
		this.marcador = marcador;
	}

	public String getTextoComplementario() {
		return textoComplementario;
	}

	public void setTextoComplementario(String textoComplementario) {
		this.textoComplementario = textoComplementario;
	}

	public String getTextoFormaPago() {
		return textoFormaPago;
	}

	public void setTextoFormaPago(String textoFormaPago) {
		this.textoFormaPago = textoFormaPago;
	}

	public boolean isFacturaFirmada() {
		return facturaFirmada;
	}

	public void setFacturaFirmada(boolean facturaFirmada) {
		this.facturaFirmada = facturaFirmada;
	}

	public String getTextoRectificativa() {
		return textoRectificativa;
	}

	public void setTextoRectificativa(String textoRectificativa) {
		this.textoRectificativa = textoRectificativa;
	}

	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public Long getId() {
		return id;
	}

	public Set<ItemFactura> getItemsFactura() {
		return itemsFactura;
	}
	
	private void resetImportes() {
		
		//Reset the importes
		this.importeTotalItems = 0D;
		this.importeDescuento = 0D;
		this.importeBaseImponible = 0D;
		this.importeTipoIVA = 0D;
		this.importeTotal = 0D;
	}
	
	public void recalculate() {
		
		resetImportes();
		
		//First calculate the sum of the importes of the items in factura
		calculateImporteItems();
		
		//Calculate descuento
		Optional<Descuento> optDesc = Optional.ofNullable(this.descuento);
			optDesc.ifPresentOrElse((x) -> { 
				this.setPorcentajeDescuento(x.getPorcentaje());
				this.setImporteDescuento(applyPercentage(x.getPorcentaje(), this.importeTotalItems));
			}, () -> {
				this.setPorcentajeDescuento(0D);
				this.setImporteDescuento(0D);
			});
		
		//Apply descuento to obtain base imponible
		this.importeBaseImponible =  (this.importeTotalItems - this.importeDescuento) + this.importeGastosEnvio;
		
		//Calculate IVA
		Optional<Double> douTiva = Optional.ofNullable(this.tipoIVA.getPorcentaje());
			douTiva.ifPresent((x) -> {
				this.setPorcentajeTipoIVA(x);
				this.setImporteTipoIVA(applyPercentage(x, this.importeBaseImponible));
			});
		
		//Apply Gastos envio to obtain total factura
		this.importeTotal = this.importeBaseImponible + this.importeTipoIVA;
		
	}
	
	public double applyPercentage(double percent, double total) {
		
        return total * percent / 100;
        
    }
	
	public void calculateImporteItems() {
		
		//Set the default value to be returned
		Double totImpItems = 0D;
		
		//Get the set of items
		for (ItemFactura item : this.itemsFactura) {
			totImpItems += item.getImporte();
		}
		
		this.importeTotalItems = totImpItems;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Factura )) return false;
        return id != null && id.equals(((Factura) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {

    	return "FACTURA: " + id.toString();
    }
}
