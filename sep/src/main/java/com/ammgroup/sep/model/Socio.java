package com.ammgroup.sep.model;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SOCIOS")
public class Socio {
	
    @Id
    @Column(name="SOCIO_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="FECHA_ALTA", nullable=false, unique=false)
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    
    @Column(name="CODIGO_SOCIO", nullable=false, unique=true)
    private int codigoSocio;

    @ManyToOne
	@JoinColumn(name="MODSOCIO_ID", nullable=true)
    private ModalidadSocio modalidad;

    @Column(name="NOMBRE", length=150, nullable=false, unique=false)
    private String nombre;
    
    @Column(name="APELLIDOS", length=60, nullable=true, unique=false)
    private String apellidos;

    @Column(name="CIFNIF", length=25, nullable=true, unique=false)
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
    
    @Column(name="CENTRO_TRABAJO", length=70, nullable=true, unique=false)
    private String centroTrabajo;

    @Column(name="AREA_TRABAJO", length=70, nullable=true, unique=false)
    private String areaTrabajo;
    
    @Column(name="TITULACION", length=70, nullable=true, unique=false)
    private String titulacion;
    
    @Column(name="BAJA", nullable=false, unique=false)
    private boolean baja;
    
    @Column(name="FECHA_BAJA", nullable=true, unique=false)
    @Temporal(TemporalType.DATE)
    private Date fechaBaja;
    
    @ManyToOne
	@JoinColumn(name="MOTIVOBAJA_ID", nullable=true)
    private MotivoBaja motivoBaja;
    
    @Column(name="NOTAS_MBAJA", length=250, nullable=true, unique=false)
    private String notasmbaja;
    
    @Column(name="CONTACTO_SEP", length=70, nullable=true, unique=false)
    private String contactoSep;
    
    @Column(name="LOPD", nullable=false, unique=false)
    private boolean lopd;
    
    @Column(name="LISTA_DISTRIBUCION", nullable=false, unique=false)
    private boolean listaDistribucion;
    
    @ManyToOne
	@JoinColumn(name="MODOACCESO_ID", nullable=true)
    private ModoAcceso modoAcceso;
    
    @Column(name="OJS", length=50, nullable=true, unique=false)
    private String ojs;
    
    @Column(name="FACTURA", nullable=false, unique=false)
    private boolean factura;

    @ManyToOne
	@JoinColumn(name="FORMAPAGO_ID", nullable=true)
    private FormaPago formaPago;
    
    @ManyToOne
	@JoinColumn(name="AGENCIA_ID", nullable=true)
    private Agencia agencia;
    
    @ManyToOne
	@JoinColumn(name="DESCUENTO_ID", nullable=true)
    private Descuento descuento;
    
    @Column(name="REFERENCIA", length=30, nullable=true, unique=false)
    private String referencia;
    
    @Column(name="FIRMAR_FACTURA", nullable=false, unique=false)
    private boolean firmarFactura;
    
    @Column(name="ANOTACIONES", length=250, nullable=true, unique=false)
    private String anotaciones;
    
    @Column(name="TEXTO_MARCADOR", length=50, nullable=true, unique=false)
    private String marcador;
    
    @Column(name="IBAN_CCC", length=30, nullable=true, unique=false)
    private String ibanccc;
    
    @Column(name="MOVIL", length=12, nullable=true, unique=false)
    private String movil;
    
    @Column(name="CARGOS_JUNTADIR", length=60, nullable=true, unique=false)
    private String cargosJuntaDirectiva;
    
    @Column(name="JUNTADIR_ACTUAL", nullable=false, unique=false)
    private boolean juntaDirectivaActual;
    
    @Column(name="ULTIMA_ACTUALIZACION", nullable=true, unique=false)
    @Temporal(TemporalType.DATE)
    private Date ultimaActualizacion;
    
    @Column(name="DATOS_ADIC_FACTURA", length=200, nullable=true, unique=false)
    private String datosAdicionalesFactura;
    
    @OneToMany(targetEntity=Factura.class, mappedBy="socio", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Factura> facturas;
    
    @OneToMany(targetEntity=Reclamacion.class, mappedBy="socio", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Reclamacion> reclamaciones;
    
    public Socio() {
		super();
	}

	public Long getId() {
		return id;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public int getCodigoSocio() {
		return codigoSocio;
	}

	public void setCodigoSocio(int codigoSocio) {
		this.codigoSocio = codigoSocio;
	}

	public ModalidadSocio getModalidad() {
		return modalidad;
	}

	public void setModalidad(ModalidadSocio modalidad) {
		this.modalidad = modalidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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

	public String getCentroTrabajo() {
		return centroTrabajo;
	}

	public void setCentroTrabajo(String centroTrabajo) {
		this.centroTrabajo = centroTrabajo;
	}

	public String getAreaTrabajo() {
		return areaTrabajo;
	}

	public void setAreaTrabajo(String areaTrabajo) {
		this.areaTrabajo = areaTrabajo;
	}

	public String getTitulacion() {
		return titulacion;
	}

	public void setTitulacion(String titulacion) {
		this.titulacion = titulacion;
	}

	public boolean isBaja() {
		return baja;
	}

	public void setBaja(boolean baja) {
		this.baja = baja;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getContactoSep() {
		return contactoSep;
	}

	public void setContactoSep(String contactoSep) {
		this.contactoSep = contactoSep;
	}

	public MotivoBaja getMotivoBaja() {
		return motivoBaja;
	}

	public void setMotivoBaja(MotivoBaja motivoBaja) {
		this.motivoBaja = motivoBaja;
	}

	public String getNotasmbaja() {
		return notasmbaja;
	}

	public void setNotasmbaja(String notasmbaja) {
		this.notasmbaja = notasmbaja;
	}

	public boolean isLopd() {
		return lopd;
	}

	public void setLopd(boolean lopd) {
		this.lopd = lopd;
	}

	public boolean isListaDistribucion() {
		return listaDistribucion;
	}

	public void setListaDistribucion(boolean listaDistribucion) {
		this.listaDistribucion = listaDistribucion;
	}

	public ModoAcceso getModoAcceso() {
		return modoAcceso;
	}

	public void setModoAcceso(ModoAcceso modoAcceso) {
		this.modoAcceso = modoAcceso;
	}

	public String getOjs() {
		return ojs;
	}

	public void setOjs(String ojs) {
		this.ojs = ojs;
	}

	public boolean isFactura() {
		return factura;
	}

	public void setFactura(boolean factura) {
		this.factura = factura;
	}

	public FormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Descuento getDescuento() {
		return descuento;
	}

	public void setDescuento(Descuento descuento) {
		this.descuento = descuento;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public boolean isFirmarFactura() {
		return firmarFactura;
	}

	public void setFirmarFactura(boolean firmarFactura) {
		this.firmarFactura = firmarFactura;
	}

	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

    public String getMarcador() {
		return marcador;
	}

	public void setMarcador(String marcador) {
		this.marcador = marcador;
	}

	public String getIbanccc() {
		return ibanccc;
	}

	public void setIbanccc(String ibanccc) {
		this.ibanccc = ibanccc;
	}

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

	public String getCargosJuntaDirectiva() {
		return cargosJuntaDirectiva;
	}

	public void setCargosJuntaDirectiva(String cargosJuntaDirectiva) {
		this.cargosJuntaDirectiva = cargosJuntaDirectiva;
	}

	public boolean isJuntaDirectivaActual() {
		return juntaDirectivaActual;
	}

	public void setJuntaDirectivaActual(boolean juntaDirectivaActual) {
		this.juntaDirectivaActual = juntaDirectivaActual;
	}
	
	public Date getUltimaActualizacion() {
		return ultimaActualizacion;
	}

	public void setUltimaActualizacion(Date ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}

	public String getDatosAdicionalesFactura() {
		return datosAdicionalesFactura;
	}

	public void setDatosAdicionalesFactura(String datosAdicionalesFactura) {
		this.datosAdicionalesFactura = datosAdicionalesFactura;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Socio )) return false;
        return id != null && id.equals(((Socio) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
    	
//    	var strwrapper = new Object(){ String strpais = ""; String strprov = ""; String strzpos = "";};
//    	
//    	Optional<Pais> paisOpt = Optional.ofNullable(this.pais);
//    	paisOpt.ifPresent((x) -> strwrapper.strpais = x.getDescripcion());
//
//    	Optional<Provincia> provOpt = Optional.ofNullable(this.provincia);
//    	provOpt.ifPresent((x) -> strwrapper.strprov = x.getDescripcion());
//    	
//    	Optional<ZonaPostal> zposOpt = Optional.ofNullable(this.zonaPostal);
//    	zposOpt.ifPresent((x) -> strwrapper.strzpos = x.getDescripcion());

    	//return "SOCIO: " + id.toString() + " - " + nombre.strip() + " " + apellidos.strip() + " ("+ cifnif.strip() + ") " + domicilio.strip() + " >> " + cp.strip() + " >> " + localidad.strip() + " >> " + strwrapper.strprov.strip() + " >> " + strwrapper.strpais.strip() + " >> " + strwrapper.strzpos.strip();  
    	return nombre.strip() + " " + apellidos.strip() + " ("+ cifnif.strip() + ")";
    }  
}
