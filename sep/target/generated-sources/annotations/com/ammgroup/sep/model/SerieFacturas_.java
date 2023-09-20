package com.ammgroup.sep.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SerieFacturas.class)
public abstract class SerieFacturas_ {

	public static volatile SingularAttribute<SerieFacturas, String> descripcion;
	public static volatile SingularAttribute<SerieFacturas, Boolean> facturacionAutomatica;
	public static volatile SingularAttribute<SerieFacturas, Boolean> facturasProforma;
	public static volatile SingularAttribute<SerieFacturas, String> textoRectificativa;
	public static volatile SingularAttribute<SerieFacturas, String> textoInicioNumeracion;
	public static volatile SingularAttribute<SerieFacturas, Boolean> rectificativas;
	public static volatile SetAttribute<SerieFacturas, Factura> facturas;
	public static volatile SingularAttribute<SerieFacturas, String> textoPara;
	public static volatile SingularAttribute<SerieFacturas, Long> id;
	public static volatile SingularAttribute<SerieFacturas, TipoIVA> tipoIVA;

	public static final String DESCRIPCION = "descripcion";
	public static final String FACTURACION_AUTOMATICA = "facturacionAutomatica";
	public static final String FACTURAS_PROFORMA = "facturasProforma";
	public static final String TEXTO_RECTIFICATIVA = "textoRectificativa";
	public static final String TEXTO_INICIO_NUMERACION = "textoInicioNumeracion";
	public static final String RECTIFICATIVAS = "rectificativas";
	public static final String FACTURAS = "facturas";
	public static final String TEXTO_PARA = "textoPara";
	public static final String ID = "id";
	public static final String TIPO_IV_A = "tipoIVA";

}

