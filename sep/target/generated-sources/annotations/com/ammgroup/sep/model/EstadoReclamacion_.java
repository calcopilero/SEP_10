package com.ammgroup.sep.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EstadoReclamacion.class)
public abstract class EstadoReclamacion_ {

	public static volatile SingularAttribute<EstadoReclamacion, String> descripcion;
	public static volatile SingularAttribute<EstadoReclamacion, Long> id;
	public static volatile SetAttribute<EstadoReclamacion, Reclamacion> reclamaciones;

	public static final String DESCRIPCION = "descripcion";
	public static final String ID = "id";
	public static final String RECLAMACIONES = "reclamaciones";

}

