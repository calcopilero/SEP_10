package com.ammgroup.sep.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FormaPago.class)
public abstract class FormaPago_ {

	public static volatile SingularAttribute<FormaPago, String> descripcion;
	public static volatile SetAttribute<FormaPago, Factura> facturas;
	public static volatile SingularAttribute<FormaPago, String> textoFactura;
	public static volatile SingularAttribute<FormaPago, Long> id;
	public static volatile SetAttribute<FormaPago, Socio> socios;

	public static final String DESCRIPCION = "descripcion";
	public static final String FACTURAS = "facturas";
	public static final String TEXTO_FACTURA = "textoFactura";
	public static final String ID = "id";
	public static final String SOCIOS = "socios";

}

