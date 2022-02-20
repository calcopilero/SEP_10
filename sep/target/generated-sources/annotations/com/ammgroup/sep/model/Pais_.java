package com.ammgroup.sep.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pais.class)
public abstract class Pais_ {

	public static volatile SingularAttribute<Pais, String> descripcion;
	public static volatile SingularAttribute<Pais, Long> id;
	public static volatile SetAttribute<Pais, Socio> socios;
	public static volatile SetAttribute<Pais, Agencia> agencias;

	public static final String DESCRIPCION = "descripcion";
	public static final String ID = "id";
	public static final String SOCIOS = "socios";
	public static final String AGENCIAS = "agencias";

}

