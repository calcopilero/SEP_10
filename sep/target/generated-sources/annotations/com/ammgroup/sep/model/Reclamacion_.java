package com.ammgroup.sep.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Reclamacion.class)
public abstract class Reclamacion_ {

	public static volatile SingularAttribute<Reclamacion, Date> fechaReclamacion;
	public static volatile SingularAttribute<Reclamacion, String> reclamacionComentario;
	public static volatile SingularAttribute<Reclamacion, String> anotaciones;
	public static volatile SingularAttribute<Reclamacion, EstadoReclamacion> estadoReclamacion;
	public static volatile SingularAttribute<Reclamacion, Integer> numero;
	public static volatile SingularAttribute<Reclamacion, Socio> socio;
	public static volatile SingularAttribute<Reclamacion, Long> id;
	public static volatile SingularAttribute<Reclamacion, Agencia> agencia;
	public static volatile SingularAttribute<Reclamacion, Date> fechaRespuesta;

	public static final String FECHA_RECLAMACION = "fechaReclamacion";
	public static final String RECLAMACION_COMENTARIO = "reclamacionComentario";
	public static final String ANOTACIONES = "anotaciones";
	public static final String ESTADO_RECLAMACION = "estadoReclamacion";
	public static final String NUMERO = "numero";
	public static final String SOCIO = "socio";
	public static final String ID = "id";
	public static final String AGENCIA = "agencia";
	public static final String FECHA_RESPUESTA = "fechaRespuesta";

}

