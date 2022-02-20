package com.ammgroup.sep.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Socio.class)
public abstract class Socio_ {

	public static volatile SingularAttribute<Socio, String> centroTrabajo;
	public static volatile SingularAttribute<Socio, Date> fechaBaja;
	public static volatile SingularAttribute<Socio, String> movil;
	public static volatile SingularAttribute<Socio, Boolean> lopd;
	public static volatile SetAttribute<Socio, Factura> facturas;
	public static volatile SingularAttribute<Socio, Boolean> juntaDirectivaActual;
	public static volatile SingularAttribute<Socio, Provincia> provincia;
	public static volatile SingularAttribute<Socio, String> cargosJuntaDirectiva;
	public static volatile SingularAttribute<Socio, String> nombre;
	public static volatile SingularAttribute<Socio, Agencia> agencia;
	public static volatile SingularAttribute<Socio, String> marcador;
	public static volatile SingularAttribute<Socio, String> ibanccc;
	public static volatile SingularAttribute<Socio, Boolean> firmarFactura;
	public static volatile SingularAttribute<Socio, String> anotaciones;
	public static volatile SingularAttribute<Socio, String> domicilio;
	public static volatile SingularAttribute<Socio, Boolean> factura;
	public static volatile SingularAttribute<Socio, Boolean> baja;
	public static volatile SingularAttribute<Socio, MotivoBaja> motivoBaja;
	public static volatile SingularAttribute<Socio, String> localidad;
	public static volatile SingularAttribute<Socio, Long> id;
	public static volatile SingularAttribute<Socio, String> titulacion;
	public static volatile SingularAttribute<Socio, String> telefono;
	public static volatile SingularAttribute<Socio, String> areaTrabajo;
	public static volatile SingularAttribute<Socio, String> email;
	public static volatile SetAttribute<Socio, Reclamacion> reclamaciones;
	public static volatile SingularAttribute<Socio, String> apellidos;
	public static volatile SingularAttribute<Socio, Date> fechaAlta;
	public static volatile SingularAttribute<Socio, Integer> codigoSocio;
	public static volatile SingularAttribute<Socio, Descuento> descuento;
	public static volatile SingularAttribute<Socio, Boolean> listaDistribucion;
	public static volatile SingularAttribute<Socio, ModoAcceso> modoAcceso;
	public static volatile SingularAttribute<Socio, String> cifnif;
	public static volatile SingularAttribute<Socio, ZonaPostal> zonaPostal;
	public static volatile SingularAttribute<Socio, String> cp;
	public static volatile SingularAttribute<Socio, Pais> pais;
	public static volatile SingularAttribute<Socio, String> contactoSep;
	public static volatile SingularAttribute<Socio, ModalidadSocio> modalidad;
	public static volatile SingularAttribute<Socio, String> ojs;
	public static volatile SingularAttribute<Socio, FormaPago> formaPago;
	public static volatile SingularAttribute<Socio, String> referencia;

	public static final String CENTRO_TRABAJO = "centroTrabajo";
	public static final String FECHA_BAJA = "fechaBaja";
	public static final String MOVIL = "movil";
	public static final String LOPD = "lopd";
	public static final String FACTURAS = "facturas";
	public static final String JUNTA_DIRECTIVA_ACTUAL = "juntaDirectivaActual";
	public static final String PROVINCIA = "provincia";
	public static final String CARGOS_JUNTA_DIRECTIVA = "cargosJuntaDirectiva";
	public static final String NOMBRE = "nombre";
	public static final String AGENCIA = "agencia";
	public static final String MARCADOR = "marcador";
	public static final String IBANCCC = "ibanccc";
	public static final String FIRMAR_FACTURA = "firmarFactura";
	public static final String ANOTACIONES = "anotaciones";
	public static final String DOMICILIO = "domicilio";
	public static final String FACTURA = "factura";
	public static final String BAJA = "baja";
	public static final String MOTIVO_BAJA = "motivoBaja";
	public static final String LOCALIDAD = "localidad";
	public static final String ID = "id";
	public static final String TITULACION = "titulacion";
	public static final String TELEFONO = "telefono";
	public static final String AREA_TRABAJO = "areaTrabajo";
	public static final String EMAIL = "email";
	public static final String RECLAMACIONES = "reclamaciones";
	public static final String APELLIDOS = "apellidos";
	public static final String FECHA_ALTA = "fechaAlta";
	public static final String CODIGO_SOCIO = "codigoSocio";
	public static final String DESCUENTO = "descuento";
	public static final String LISTA_DISTRIBUCION = "listaDistribucion";
	public static final String MODO_ACCESO = "modoAcceso";
	public static final String CIFNIF = "cifnif";
	public static final String ZONA_POSTAL = "zonaPostal";
	public static final String CP = "cp";
	public static final String PAIS = "pais";
	public static final String CONTACTO_SEP = "contactoSep";
	public static final String MODALIDAD = "modalidad";
	public static final String OJS = "ojs";
	public static final String FORMA_PAGO = "formaPago";
	public static final String REFERENCIA = "referencia";

}

