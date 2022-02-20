package com.ammgroup.sep.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Factura.class)
public abstract class Factura_ {

	public static volatile SingularAttribute<Factura, Integer> numero;
	public static volatile SingularAttribute<Factura, Double> importeTipoIVA;
	public static volatile SingularAttribute<Factura, Boolean> facturaFirmada;
	public static volatile SingularAttribute<Factura, EstadoFactura> estadoFactura;
	public static volatile SingularAttribute<Factura, Agencia> agencia;
	public static volatile SingularAttribute<Factura, String> marcador;
	public static volatile SingularAttribute<Factura, Double> importeTotalItems;
	public static volatile SingularAttribute<Factura, Factura> facturaRectificada;
	public static volatile SingularAttribute<Factura, String> anotaciones;
	public static volatile SingularAttribute<Factura, Boolean> facturacionAutomatica;
	public static volatile SingularAttribute<Factura, Double> importeTotal;
	public static volatile SingularAttribute<Factura, String> textoRectificativa;
	public static volatile SingularAttribute<Factura, Date> fechaFactura;
	public static volatile SingularAttribute<Factura, Long> id;
	public static volatile SingularAttribute<Factura, Double> importeGastosEnvio;
	public static volatile SingularAttribute<Factura, TipoIVA> tipoIVA;
	public static volatile SingularAttribute<Factura, String> textoComplementario;
	public static volatile SingularAttribute<Factura, String> textoFormaPago;
	public static volatile SingularAttribute<Factura, Date> ultimaActualizacion;
	public static volatile SingularAttribute<Factura, Socio> socio;
	public static volatile SingularAttribute<Factura, Descuento> descuento;
	public static volatile SingularAttribute<Factura, Double> importeDescuento;
	public static volatile SingularAttribute<Factura, String> direccion;
	public static volatile SingularAttribute<Factura, Date> fechaEmision;
	public static volatile SingularAttribute<Factura, String> cifnif;
	public static volatile SingularAttribute<Factura, Boolean> existeRectificativa;
	public static volatile SingularAttribute<Factura, Double> importeBaseImponible;
	public static volatile SingularAttribute<Factura, String> numeroCompuesto;
	public static volatile SingularAttribute<Factura, Double> porcentajeDescuento;
	public static volatile SingularAttribute<Factura, Double> porcentajeTipoIVA;
	public static volatile SingularAttribute<Factura, SerieFacturas> serie;
	public static volatile SingularAttribute<Factura, FormaPago> formaPago;
	public static volatile SetAttribute<Factura, ItemFactura> itemsFactura;
	public static volatile SingularAttribute<Factura, String> titular;
	public static volatile SingularAttribute<Factura, String> referencia;

	public static final String NUMERO = "numero";
	public static final String IMPORTE_TIPO_IV_A = "importeTipoIVA";
	public static final String FACTURA_FIRMADA = "facturaFirmada";
	public static final String ESTADO_FACTURA = "estadoFactura";
	public static final String AGENCIA = "agencia";
	public static final String MARCADOR = "marcador";
	public static final String IMPORTE_TOTAL_ITEMS = "importeTotalItems";
	public static final String FACTURA_RECTIFICADA = "facturaRectificada";
	public static final String ANOTACIONES = "anotaciones";
	public static final String FACTURACION_AUTOMATICA = "facturacionAutomatica";
	public static final String IMPORTE_TOTAL = "importeTotal";
	public static final String TEXTO_RECTIFICATIVA = "textoRectificativa";
	public static final String FECHA_FACTURA = "fechaFactura";
	public static final String ID = "id";
	public static final String IMPORTE_GASTOS_ENVIO = "importeGastosEnvio";
	public static final String TIPO_IV_A = "tipoIVA";
	public static final String TEXTO_COMPLEMENTARIO = "textoComplementario";
	public static final String TEXTO_FORMA_PAGO = "textoFormaPago";
	public static final String ULTIMA_ACTUALIZACION = "ultimaActualizacion";
	public static final String SOCIO = "socio";
	public static final String DESCUENTO = "descuento";
	public static final String IMPORTE_DESCUENTO = "importeDescuento";
	public static final String DIRECCION = "direccion";
	public static final String FECHA_EMISION = "fechaEmision";
	public static final String CIFNIF = "cifnif";
	public static final String EXISTE_RECTIFICATIVA = "existeRectificativa";
	public static final String IMPORTE_BASE_IMPONIBLE = "importeBaseImponible";
	public static final String NUMERO_COMPUESTO = "numeroCompuesto";
	public static final String PORCENTAJE_DESCUENTO = "porcentajeDescuento";
	public static final String PORCENTAJE_TIPO_IV_A = "porcentajeTipoIVA";
	public static final String SERIE = "serie";
	public static final String FORMA_PAGO = "formaPago";
	public static final String ITEMS_FACTURA = "itemsFactura";
	public static final String TITULAR = "titular";
	public static final String REFERENCIA = "referencia";

}

