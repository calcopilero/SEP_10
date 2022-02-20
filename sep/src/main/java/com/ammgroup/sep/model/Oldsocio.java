package com.ammgroup.sep.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Socios_old")
public class Oldsocio {

    @Column(name="CIF_Socio", length=255, nullable=true, unique=false)
    private String cif;
    
    @Column(name="Numero_de_pedido", length=255, nullable=true, unique=false)
    private String numeroPedido;

    @Column(name="Alta", nullable=true, unique=false)
    @Temporal(TemporalType.DATE)
    private Date alta;
    
    @Column(name="Activo", length=255, nullable=true, unique=false)
    private String activo;
    
    @Column(name="Nombre_Socio", length=255, nullable=true, unique=false)
    private String nombreSocio;

    @Id
    @Column(name="NSocio", nullable=true, unique=true)
    private Integer numSocio;
    
    @Column(name="NCuenta", length=255, nullable=true, unique=false)
    private String numCuenta;
    
    @Column(name="Recyt", length=255, nullable=true, unique=false)
    private String recyt;
    
    @Column(name="Email_Socio", length=255, nullable=true, unique=false)
    private String emailSocio;
    
    @Column(name="Baja", nullable=true, unique=false)
    @Temporal(TemporalType.DATE)
    private Date baja;
    
    @Column(name="Comentario", length=255, nullable=true, unique=false)
    private String comentario;
    
    @Column(name="Contacto_SEP", length=255, nullable=true, unique=false)
    private String contactoSEP;
    
    @Column(name="Domicilio_Socio", length=255, nullable=true, unique=false)
    private String domicilioSocio;
    
    @Column(name="Localidad_Socio", length=255, nullable=true, unique=false)
    private String localidadSocio;
    
    @Column(name="Telefono_Fijo_Socio", length=255, nullable=true, unique=false)
    private String telefonoFijo;
    
    @Column(name="Telefono_movil_Socio", length=255, nullable=true, unique=false)
    private String telefonoMovil;
    
    @Column(name="Titulación", length=255, nullable=true, unique=false)
    private String titulacion;
    
    @Column(name="Centro_Trabajo", length=255, nullable=true, unique=false)
    private String centroTrabajo;
    
    @Column(name="Area_Trabajo", length=255, nullable=true, unique=false)
    private String areaTrabajo;
    
    @Column(name="Lista_Distribucion", length=255, nullable=true, unique=false)
    private String listaDistribucion;
    
    @Column(name="LOPD", length=255, nullable=true, unique=false)
    private String lopd;
    
    @Column(name="Factura_Socio", length=255, nullable=true, unique=false)
    private String facturaSocio;
    
    @Column(name="Modalidad", length=255, nullable=true, unique=false)
    private String modalidad;
    
    @Column(name="Modo_Envio_Revista", length=50, nullable=true, unique=false)
    private String modoEnviod;
    
    @Column(name="Agencia", length=255, nullable=true, unique=false)
    private String agencia;
    
    @Column(name="Modo_de_pago", length=255, nullable=true, unique=false)
    private String modoPago;
    
    @Column(name="Motivo_Baja", length=255, nullable=true, unique=false)
    private String motivoBaja;
    
    @Column(name="Provincia_Socio", length=255, nullable=true, unique=false)
    private String provincia;
    
    @Column(name="Pais_Socio", length=255, nullable=true, unique=false)
    private String pais;
    
    @Column(name="Correos_zona", length=255, nullable=true, unique=false)
    private String correosZona;
    
    @Column(name="Euros", length=20, nullable=true, unique=false)
    private String euros;
    
	public Oldsocio() {
		super();
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public Date getAlta() {
		return alta;
	}

	public void setAlta(Date alta) {
		this.alta = alta;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getNombreSocio() {
		return nombreSocio;
	}

	public void setNombreSocio(String nombreSocio) {
		this.nombreSocio = nombreSocio;
	}

	public Integer getNumSocio() {
		return numSocio;
	}

	public void setNumSocio(Integer numSocio) {
		this.numSocio = numSocio;
	}

	public String getNumCuenta() {
		return numCuenta;
	}

	public void setNumCuenta(String numCuenta) {
		this.numCuenta = numCuenta;
	}

	public String getRecyt() {
		return recyt;
	}

	public void setRecyt(String recyt) {
		this.recyt = recyt;
	}

	public String getEmailSocio() {
		return emailSocio;
	}

	public void setEmailSocio(String emailSocio) {
		this.emailSocio = emailSocio;
	}

	public Date getBaja() {
		return baja;
	}

	public void setBaja(Date baja) {
		this.baja = baja;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getContactoSEP() {
		return contactoSEP;
	}

	public void setContactoSEP(String contactoSEP) {
		this.contactoSEP = contactoSEP;
	}

	public String getDomicilioSocio() {
		return domicilioSocio;
	}

	public void setDomicilioSocio(String domicilioSocio) {
		this.domicilioSocio = domicilioSocio;
	}

	public String getLocalidadSocio() {
		return localidadSocio;
	}

	public void setLocalidadSocio(String localidadSocio) {
		this.localidadSocio = localidadSocio;
	}

	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public String getTitulacion() {
		return titulacion;
	}

	public void setTitulacion(String titulacion) {
		this.titulacion = titulacion;
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

	public String getListaDistribucion() {
		return listaDistribucion;
	}

	public void setListaDistribucion(String listaDistribucion) {
		this.listaDistribucion = listaDistribucion;
	}

	public String getLopd() {
		return lopd;
	}

	public void setLopd(String lopd) {
		this.lopd = lopd;
	}

	public String getFacturaSocio() {
		return facturaSocio;
	}

	public void setFacturaSocio(String facturaSocio) {
		this.facturaSocio = facturaSocio;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getModoEnviod() {
		return modoEnviod;
	}

	public void setModoEnviod(String modoEnviod) {
		this.modoEnviod = modoEnviod;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getModoPago() {
		return modoPago;
	}

	public void setModoPago(String modoPago) {
		this.modoPago = modoPago;
	}

	public String getMotivoBaja() {
		return motivoBaja;
	}

	public void setMotivoBaja(String motivoBaja) {
		this.motivoBaja = motivoBaja;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCorreosZona() {
		return correosZona;
	}

	public void setCorreosZona(String correosZona) {
		this.correosZona = correosZona;
	}

	public String getEuros() {
		return euros;
	}

	public void setEuros(String euros) {
		this.euros = euros;
	}
}
