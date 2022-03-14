package com.ammgroup.sep.controller.config.filter;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.Descuento;
import com.ammgroup.sep.model.FormaPago;
import com.ammgroup.sep.model.ModalidadSocio;
import com.ammgroup.sep.model.ModoAcceso;
import com.ammgroup.sep.model.MotivoBaja;
import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.model.ZonaPostal;

@Component
public class SocioFilter {
	
    private Date fechaAltaInicial;
    private Date fechaAltaFinal;
	private Boolean activo;
	private Boolean baja;
	private long codigoSocio;
    private ModalidadSocio modalidad;
	private String nombre;
	private String apellidos;
	private String cifnif;
	private String localidad;
	private Provincia provincia;
	private Pais pais;
	private ZonaPostal zonaPostal;
	private String telefono;
	private String email;
	private Date fechaBaja;
	private MotivoBaja motivoBaja;
	private ModoAcceso modoAcceso;
	private FormaPago formaPago;
	private Agencia agencia;
	private Descuento descuento;
	private String marcador;
	private Boolean factura;
	private Boolean listaDistribucion;
	private String cargosJuntaDirectiva;
	private Boolean juntaDirectivaActual;
    private Date fechaBajaInicial;
    private Date fechaBajaFinal;
	
	public SocioFilter() {
		super();
	}

	public Date getFechaAltaInicial() {
		return fechaAltaInicial;
	}

	public void setFechaAltaInicial(Date fechaAltaInicial) {
		this.fechaAltaInicial = fechaAltaInicial;
	}

	public Date getFechaAltaFinal() {
		return fechaAltaFinal;
	}

	public void setFechaAltaFinal(Date fechaAltaFinal) {
		this.fechaAltaFinal = fechaAltaFinal;
	}

	public long getCodigoSocio() {
		return codigoSocio;
	}

	public void setCodigoSocio(long codigoSocio) {
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

	public void setActivo(Boolean act) {
		this.activo = act;
	}

	public Boolean getBaja() {
		return baja;
	}

	public void setBaja(Boolean baja) {
		this.baja = baja;
	}

	public Boolean getActivo() {
		return activo;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public MotivoBaja getMotivoBaja() {
		return motivoBaja;
	}

	public void setMotivoBaja(MotivoBaja motivoBaja) {
		this.motivoBaja = motivoBaja;
	}

	public ModoAcceso getModoAcceso() {
		return modoAcceso;
	}

	public void setModoAcceso(ModoAcceso modoAcceso) {
		this.modoAcceso = modoAcceso;
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
	
	public String getMarcador() {
		return marcador;
	}

	public void setMarcador(String marcador) {
		this.marcador = marcador;
	}

	public Boolean getFactura() {
		return factura;
	}

	public void setFactura(Boolean factura) {
		this.factura = factura;
	}

	public Boolean getListaDistribucion() {
		return listaDistribucion;
	}

	public void setListaDistribucion(Boolean listaDistribucion) {
		this.listaDistribucion = listaDistribucion;
	}

	public String getCargosJuntaDirectiva() {
		return cargosJuntaDirectiva;
	}

	public void setCargosJuntaDirectiva(String cargosJuntaDirectiva) {
		this.cargosJuntaDirectiva = cargosJuntaDirectiva;
	}

	public Boolean getJuntaDirectivaActual() {
		return juntaDirectivaActual;
	}

	public void setJuntaDirectivaActual(Boolean juntaDirectivaActual) {
		this.juntaDirectivaActual = juntaDirectivaActual;
	}

	public Date getFechaBajaInicial() {
		return fechaBajaInicial;
	}

	public void setFechaBajaInicial(Date fechaBajaInicial) {
		this.fechaBajaInicial = fechaBajaInicial;
	}

	public Date getFechaBajaFinal() {
		return fechaBajaFinal;
	}

	public void setFechaBajaFinal(Date fechaBajaFinal) {
		this.fechaBajaFinal = fechaBajaFinal;
	}

	public int containsFilters() {
		
		var cfwrapper = new Object(){ int cf = 0; };
		
		Optional<Date> optDateIni = Optional.ofNullable(fechaAltaInicial);
		Optional<Date> optDateFin = Optional.ofNullable(fechaAltaFinal);

		//Check if both dates are set
		if (optDateIni.isPresent() && optDateFin.isPresent()) {
			cfwrapper.cf++;
		}
		
		//Activo and baja filter only applyes if selected (true)
		Optional<Boolean> optAct = Optional.ofNullable(activo);
			optAct.ifPresent((x) -> { if (x) cfwrapper.cf++; });
			
		Optional<Boolean> optBaja = Optional.ofNullable(baja);
			optBaja.ifPresent((x) -> { if (x) cfwrapper.cf++; });
		
		//In strings we also check it length > 0
		Optional<String> optNombre = Optional.ofNullable(nombre);
			optNombre.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });
			
		Optional<String> optApell = Optional.ofNullable(apellidos);
			optApell.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });
		
		Optional<String> optCifnif = Optional.ofNullable(cifnif);
			optCifnif.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });

		Optional<String> optLoc = Optional.ofNullable(localidad);
			optLoc.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });

		Optional<Provincia> optProv = Optional.ofNullable(provincia);
			optProv.ifPresent((x) -> { cfwrapper.cf++; });

		Optional<Pais> optPais = Optional.ofNullable(pais);
			optPais.ifPresent((x) -> { cfwrapper.cf++; });
		
		Optional<ZonaPostal> optZpost = Optional.ofNullable(zonaPostal);
			optZpost.ifPresent((x) -> { cfwrapper.cf++; });
		
		Optional<String> optEmail = Optional.ofNullable(email);
			optEmail.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });
		
		Optional<String> optTelef = Optional.ofNullable(telefono);
			optTelef.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });
		
		Optional<ModalidadSocio> optMsoc = Optional.ofNullable(modalidad);
			optMsoc.ifPresent((x) -> { cfwrapper.cf++; });
		
		Optional<Agencia> optAge = Optional.ofNullable(agencia);
			optAge.ifPresent((x) -> { cfwrapper.cf++; });
		
		Optional<ModoAcceso> optMacc = Optional.ofNullable(modoAcceso);
			optMacc.ifPresent((x) -> { cfwrapper.cf++; });
		
		Optional<String> optMarc = Optional.ofNullable(marcador);
			optMarc.ifPresent((x) -> { if (x.length() > 0) cfwrapper.cf++; });
			
		Optional<Boolean> optFact = Optional.ofNullable(factura);
			optFact.ifPresent((x) -> { if (x) cfwrapper.cf++; });
			
		Optional<Boolean> optLdis = Optional.ofNullable(listaDistribucion);
			optLdis.ifPresent((x) -> { if (x) cfwrapper.cf++; });
			
		Optional<Date> optDatefbIni = Optional.ofNullable(fechaBajaInicial);
		Optional<Date> optDatefbFin = Optional.ofNullable(fechaBajaFinal);

		//Check if both dates are set
		if (optDatefbIni.isPresent() && optDatefbFin.isPresent()) {
			cfwrapper.cf++;
		}
		
		return cfwrapper.cf; 
		
	}
}
