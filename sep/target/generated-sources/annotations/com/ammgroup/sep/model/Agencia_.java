package com.ammgroup.sep.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Agencia.class)
public abstract class Agencia_ {

	public static volatile SingularAttribute<Agencia, String> datosAdicionalesFactura;
	public static volatile SetAttribute<Agencia, Factura> facturas;
	public static volatile SingularAttribute<Agencia, String> personaContacto;
	public static volatile SingularAttribute<Agencia, String> cifnif;
	public static volatile SingularAttribute<Agencia, Provincia> provincia;
	public static volatile SingularAttribute<Agencia, ZonaPostal> zonaPostal;
	public static volatile SingularAttribute<Agencia, String> nombre;
	public static volatile SingularAttribute<Agencia, String> cp;
	public static volatile SingularAttribute<Agencia, Boolean> activa;
	public static volatile SetAttribute<Agencia, Socio> socios;
	public static volatile SingularAttribute<Agencia, Pais> pais;
	public static volatile SingularAttribute<Agencia, String> anotaciones;
	public static volatile SingularAttribute<Agencia, String> domicilio;
	public static volatile SingularAttribute<Agencia, String> localidad;
	public static volatile SingularAttribute<Agencia, Long> id;
	public static volatile SingularAttribute<Agencia, String> telefono;
	public static volatile SingularAttribute<Agencia, String> email;

	public static final String DATOS_ADICIONALES_FACTURA = "datosAdicionalesFactura";
	public static final String FACTURAS = "facturas";
	public static final String PERSONA_CONTACTO = "personaContacto";
	public static final String CIFNIF = "cifnif";
	public static final String PROVINCIA = "provincia";
	public static final String ZONA_POSTAL = "zonaPostal";
	public static final String NOMBRE = "nombre";
	public static final String CP = "cp";
	public static final String ACTIVA = "activa";
	public static final String SOCIOS = "socios";
	public static final String PAIS = "pais";
	public static final String ANOTACIONES = "anotaciones";
	public static final String DOMICILIO = "domicilio";
	public static final String LOCALIDAD = "localidad";
	public static final String ID = "id";
	public static final String TELEFONO = "telefono";
	public static final String EMAIL = "email";

}

