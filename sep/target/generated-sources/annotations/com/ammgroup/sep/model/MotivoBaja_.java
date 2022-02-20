package com.ammgroup.sep.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MotivoBaja.class)
public abstract class MotivoBaja_ {

	public static volatile SingularAttribute<MotivoBaja, String> descripcion;
	public static volatile SingularAttribute<MotivoBaja, Long> id;
	public static volatile SetAttribute<MotivoBaja, Socio> socios;

	public static final String DESCRIPCION = "descripcion";
	public static final String ID = "id";
	public static final String SOCIOS = "socios";

}

