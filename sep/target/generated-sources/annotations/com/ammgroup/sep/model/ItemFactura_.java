package com.ammgroup.sep.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ItemFactura.class)
public abstract class ItemFactura_ {

	public static volatile SingularAttribute<ItemFactura, Factura> factura;
	public static volatile SingularAttribute<ItemFactura, String> concepto;
	public static volatile SingularAttribute<ItemFactura, Long> id;
	public static volatile SingularAttribute<ItemFactura, Double> importe;

	public static final String FACTURA = "factura";
	public static final String CONCEPTO = "concepto";
	public static final String ID = "id";
	public static final String IMPORTE = "importe";

}

