package com.ammgroup.sep.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ModoAcceso.class)
public abstract class ModoAcceso_ {

	public static volatile SingularAttribute<ModoAcceso, String> descripcion;
	public static volatile SingularAttribute<ModoAcceso, Long> id;
	public static volatile SetAttribute<ModoAcceso, Socio> socios;

	public static final String DESCRIPCION = "descripcion";
	public static final String ID = "id";
	public static final String SOCIOS = "socios";

}

