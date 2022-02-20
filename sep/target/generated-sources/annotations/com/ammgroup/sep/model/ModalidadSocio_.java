package com.ammgroup.sep.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ModalidadSocio.class)
public abstract class ModalidadSocio_ {

	public static volatile SingularAttribute<ModalidadSocio, String> descripcion;
	public static volatile SingularAttribute<ModalidadSocio, Double> cuota;
	public static volatile SingularAttribute<ModalidadSocio, String> concepto;
	public static volatile SingularAttribute<ModalidadSocio, String> textoPara;
	public static volatile SingularAttribute<ModalidadSocio, Long> id;
	public static volatile SetAttribute<ModalidadSocio, Socio> socios;

	public static final String DESCRIPCION = "descripcion";
	public static final String CUOTA = "cuota";
	public static final String CONCEPTO = "concepto";
	public static final String TEXTO_PARA = "textoPara";
	public static final String ID = "id";
	public static final String SOCIOS = "socios";

}

