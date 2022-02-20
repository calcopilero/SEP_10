package com.ammgroup.sep.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TipoIVA.class)
public abstract class TipoIVA_ {

	public static volatile SingularAttribute<TipoIVA, String> descripcion;
	public static volatile SetAttribute<TipoIVA, Factura> facturas;
	public static volatile SingularAttribute<TipoIVA, String> textoFactura;
	public static volatile SetAttribute<TipoIVA, SerieFacturas> seriesFacturas;
	public static volatile SingularAttribute<TipoIVA, Long> id;
	public static volatile SingularAttribute<TipoIVA, Double> porcentaje;

	public static final String DESCRIPCION = "descripcion";
	public static final String FACTURAS = "facturas";
	public static final String TEXTO_FACTURA = "textoFactura";
	public static final String SERIES_FACTURAS = "seriesFacturas";
	public static final String ID = "id";
	public static final String PORCENTAJE = "porcentaje";

}

