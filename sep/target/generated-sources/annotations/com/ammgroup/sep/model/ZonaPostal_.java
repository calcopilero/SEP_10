package com.ammgroup.sep.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ZonaPostal.class)
public abstract class ZonaPostal_ {

	public static volatile SingularAttribute<ZonaPostal, String> descripcion;
	public static volatile SingularAttribute<ZonaPostal, Long> id;
	public static volatile SetAttribute<ZonaPostal, Socio> socios;
	public static volatile SetAttribute<ZonaPostal, Agencia> agencias;

	public static final String DESCRIPCION = "descripcion";
	public static final String ID = "id";
	public static final String SOCIOS = "socios";
	public static final String AGENCIAS = "agencias";

}

