package com.ammgroup.sep.runners.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.repository.AgenciaRepository;
import com.ammgroup.sep.repository.DescuentoRepository;
import com.ammgroup.sep.repository.FormaPagoRepository;
import com.ammgroup.sep.repository.ModalidadSocioRepository;
import com.ammgroup.sep.repository.ModoAccesoRepository;
import com.ammgroup.sep.repository.MotivoBajaRepository;
import com.ammgroup.sep.repository.PaisRepository;
import com.ammgroup.sep.repository.ProvinciaRepository;
import com.ammgroup.sep.repository.SocioRepository;
import com.ammgroup.sep.repository.ZonaPostalRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.Descuento;
import com.ammgroup.sep.model.FormaPago;
import com.ammgroup.sep.model.ModalidadSocio;
import com.ammgroup.sep.model.ModoAcceso;
import com.ammgroup.sep.model.MotivoBaja;
import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.model.Socio;
import com.ammgroup.sep.model.ZonaPostal;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(10)
public class SocioRunner  implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(SocioRunner.class);
	
	@Autowired 
	private ModuloUtilidades mutils;
	
	@Autowired 
	private SocioRepository socioRepository;
	
	@Autowired 
	private ProvinciaRepository provinciaRepository;
	
	@Autowired 
	private PaisRepository paisRepository;
	
	@Autowired 
	private ZonaPostalRepository zpostalRepository;
	
	@Autowired 
	private ModalidadSocioRepository msocioRepository;
	
	@Autowired 
	private MotivoBajaRepository mbajaRepository;
	
	@Autowired 
	private ModoAccesoRepository maccesoRepository;
	
	@Autowired 
	private FormaPagoRepository fpagoRepository;
	
	@Autowired 
	private AgenciaRepository agenciaRepository;
	
	@Autowired 
	private DescuentoRepository descuentoRepository;
	
    @Override
    public void run(String... args) throws Exception {

    	//Setting the date format to be used
    	String strfmt = "dd-M-yyyy";
    	String strdate;
		
		//Delete all existing records
		socioRepository.deleteAll();
    	
    	Socio soc1 = new Socio();
    	
    	soc1.setCodigoSocio(1001);
    	List<ModalidadSocio> msocios = msocioRepository.findByDescripcion("Ordinario");
			if (msocios.size() == 1) {soc1.setModalidad(msocios.get(0));}
		strdate = "12-11-2015";
			soc1.setFechaAlta(mutils.getDateFromString(strdate, strfmt));
		soc1.setNombre("Alfonso");
    	soc1.setApellidos("MArtínez Díez");
    	soc1.setCifnif("51396825K");
    	soc1.setDomicilio("C/ Marques de Viana, 3");
    	soc1.setCp("28039");
    	soc1.setLocalidad("Madrid");
    	List<Provincia> provs = provinciaRepository.findByDescripcion("Madrid");
    		if (provs.size() == 1) {soc1.setProvincia(provs.get(0));}
    	List<Pais> paises = paisRepository.findByDescripcion("España");
    		if (paises.size() == 1) {soc1.setPais(paises.get(0));}
    	List<ZonaPostal> zpostales = zpostalRepository.findByDescripcion("Zona 1");
    		if (zpostales.size() == 1) {soc1.setZonaPostal(zpostales.get(0));}
    	soc1.setTelefono("656389574");
    	soc1.setEmail("ammgroup@yahoo.com");
    	soc1.setCentroTrabajo("Universidad Autónoma");
    	soc1.setAreaTrabajo("Desarrollo aplicaciones");
    	soc1.setTitulacion("COU");
    	soc1.setBaja(false);
    	soc1.setContactoSep("Congreso de trabajo");
    	soc1.setLopd(true);
    	soc1.setListaDistribucion(true);
    	List<ModoAcceso> macceso = maccesoRepository.findByDescripcion("Online");
			if (macceso.size() == 1) {soc1.setModoAcceso(macceso.get(0));}
    	soc1.setOjs("clave1");
    	soc1.setFactura(true);
    	List<FormaPago> fpago = fpagoRepository.findByDescripcion("Transferencia");
			if (fpago.size() == 1) {soc1.setFormaPago(fpago.get(0));}
	    List<Agencia> agencias = agenciaRepository.findByNombre("Marcial Pons");
			if (agencias.size() == 1) {soc1.setAgencia(agencias.get(0));}
	    List<Descuento> descuentos = descuentoRepository.findByDescripcion("Agencia Online");
			if (descuentos.size() == 1) {soc1.setDescuento(descuentos.get(0));}
    	soc1.setReferencia("REF001");
    	soc1.setAnotaciones("Socio de prueba 1");
    	soc1.setMarcador("");
    	
		socioRepository.save(soc1);
		
    	Socio soc2 = new Socio();
    	
    	soc2.setCodigoSocio(1002);
    	msocios = msocioRepository.findByDescripcion("Ordinario");
			if (msocios.size() == 1) {soc2.setModalidad(msocios.get(0));}
		strdate = "20-09-2016";
			soc2.setFechaAlta(mutils.getDateFromString(strdate, strfmt));
		soc2.setNombre("David");
    	soc2.setApellidos("Díaz Romero");
    	soc2.setCifnif("51462855D");
    	soc2.setDomicilio("C/ Puerta de Toledo, 4");
    	soc2.setCp("28023");
    	soc2.setLocalidad("Getafe");
    	provs = provinciaRepository.findByDescripcion("Madrid");
    		if (provs.size() == 1) {soc2.setProvincia(provs.get(0));}
    	paises = paisRepository.findByDescripcion("España");
    		if (paises.size() == 1) {soc2.setPais(paises.get(0));}
    	zpostales = zpostalRepository.findByDescripcion("Zona 2");
    		if (zpostales.size() == 1) {soc2.setZonaPostal(zpostales.get(0));}
    	soc2.setTelefono("62337456");
    	soc2.setEmail("ddiaz@yahoo.es");
    	soc2.setCentroTrabajo("Universidad Complutense");
    	soc2.setAreaTrabajo("Departamento de Pedagogía");
    	soc2.setTitulacion("Doctor en Pedagogía");
    	soc2.setBaja(false);
    	soc2.setContactoSep("Otro socio");
    	soc2.setLopd(true);
    	soc2.setListaDistribucion(true);
    	macceso = maccesoRepository.findByDescripcion("Impreso");
			if (macceso.size() == 1) {soc2.setModoAcceso(macceso.get(0));}
    	soc2.setOjs("clave2");
    	soc2.setFactura(true);
    	fpago = fpagoRepository.findByDescripcion("Transferencia");
			if (fpago.size() == 1) {soc2.setFormaPago(fpago.get(0));}
	    agencias = agenciaRepository.findByNombre("Marcial Pons");
			if (agencias.size() == 1) {soc2.setAgencia(agencias.get(0));}
		descuentos = descuentoRepository.findByDescripcion("Agencia Impreso");
			if (descuentos.size() == 1) {soc2.setDescuento(descuentos.get(0));}
    	soc2.setReferencia("REF002");
    	soc2.setAnotaciones("Socio de prueba 2");
    	soc2.setMarcador("");
    	
		socioRepository.save(soc2);

    	Socio soc3 = new Socio();
    	
    	soc3.setCodigoSocio(1003);
    	msocios = msocioRepository.findByDescripcion("Emerito");
			if (msocios.size() == 1) {soc3.setModalidad(msocios.get(0));}
		strdate = "01-03-2011";
			soc3.setFechaAlta(mutils.getDateFromString(strdate, strfmt));
		soc3.setNombre("Luis");
    	soc3.setApellidos("Gómez García");
    	soc3.setCifnif("07466575A");
    	soc3.setDomicilio("Avda. Diagonal, 64");
    	soc3.setCp("08022");
    	soc3.setLocalidad("Barcelona");
    	provs = provinciaRepository.findByDescripcion("Barcelona");
    		if (provs.size() == 1) {soc3.setProvincia(provs.get(0));}
    	paises = paisRepository.findByDescripcion("España");
    		if (paises.size() == 1) {soc3.setPais(paises.get(0));}
    	zpostales = zpostalRepository.findByDescripcion("Zona 2");
    		if (zpostales.size() == 1) {soc3.setZonaPostal(zpostales.get(0));}
    	soc3.setTelefono("614759986");
    	soc3.setEmail("lgomez@gmail.com");
    	soc3.setCentroTrabajo("Universidad Autónoma de Barcelona");
    	soc3.setAreaTrabajo("Departamento de Pedagogía");
    	soc3.setTitulacion("Doctor en Filosofía");
    	soc3.setBaja(true);
    	List<MotivoBaja> mbajas = mbajaRepository.findByDescripcion("Voluntaria");
			if (mbajas.size() == 1) {soc3.setMotivoBaja(mbajas.get(0));}
		strdate = "02-04-2018";
			soc3.setFechaBaja(mutils.getDateFromString(strdate, strfmt));		
    	soc3.setContactoSep("Otro socio");
    	soc3.setLopd(true);
    	soc3.setListaDistribucion(true);
    	macceso = maccesoRepository.findByDescripcion("Online");
			if (macceso.size() == 1) {soc3.setModoAcceso(macceso.get(0));}
    	soc3.setOjs("clave3");
    	soc3.setFactura(false);
    	fpago = fpagoRepository.findByDescripcion("Paypal");
			if (fpago.size() == 1) {soc3.setFormaPago(fpago.get(0));}

    	soc3.setAnotaciones("Socio de prueba 3");
    	soc3.setMarcador("");
    	
		socioRepository.save(soc3);
		
		logger.info("# of socios: {}", socioRepository.count());
		logger.info("------------------------");
		logger.info("All socios unsorted:"); 
		List<Socio> socios = socioRepository.findAll(); 
		logger.info("{}", socios);
		logger.info("------------------------");
		logger.info("All socios sorted by nombre in descending order");
		List<Socio> sociosSorted = socioRepository.findAll(Sort.by(Sort.Direction.DESC, "nombre"));
		logger.info("{}", sociosSorted); 
		logger.info("------------------------");
		 		 		 		  
	}

}
