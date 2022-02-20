package com.ammgroup.sep.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Descuento.class)
public abstract class Descuento_ {

	public static volatile SingularAttribute<Descuento, String> descripcion;
	public static volatile SetAttribute<Descuento, Factura> facturas;
	public static volatile SingularAttribute<Descuento, String> textoFactura;
	public static volatile SingularAttribute<Descuento, Long> id;
	public static volatile SingularAttribute<Descuento, Double> porcentaje;
	public static volatile SetAttribute<Descuento, Socio> socios;

	public static final String DESCRIPCION = "descripcion";
	public static final String FACTURAS = "facturas";
	public static final String TEXTO_FACTURA = "textoFactura";
	public static final String ID = "id";
	public static final String PORCENTAJE = "porcentaje";
	public static final String SOCIOS = "socios";

}

