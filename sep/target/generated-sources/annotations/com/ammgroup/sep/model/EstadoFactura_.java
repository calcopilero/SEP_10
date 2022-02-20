package com.ammgroup.sep.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EstadoFactura.class)
public abstract class EstadoFactura_ {

	public static volatile SingularAttribute<EstadoFactura, String> descripcion;
	public static volatile SingularAttribute<EstadoFactura, Boolean> estadoPorDefecto;
	public static volatile SetAttribute<EstadoFactura, Factura> facturas;
	public static volatile SingularAttribute<EstadoFactura, Boolean> estadoRectificativas;
	public static volatile SingularAttribute<EstadoFactura, Long> id;

	public static final String DESCRIPCION = "descripcion";
	public static final String ESTADO_POR_DEFECTO = "estadoPorDefecto";
	public static final String FACTURAS = "facturas";
	public static final String ESTADO_RECTIFICATIVAS = "estadoRectificativas";
	public static final String ID = "id";

}

