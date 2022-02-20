package com.ammgroup.sep.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Provincia.class)
public abstract class Provincia_ {

	public static volatile SingularAttribute<Provincia, String> descripcion;
	public static volatile SingularAttribute<Provincia, Long> id;
	public static volatile SetAttribute<Provincia, Socio> socios;
	public static volatile SetAttribute<Provincia, Agencia> agencias;

	public static final String DESCRIPCION = "descripcion";
	public static final String ID = "id";
	public static final String SOCIOS = "socios";
	public static final String AGENCIAS = "agencias";

}

