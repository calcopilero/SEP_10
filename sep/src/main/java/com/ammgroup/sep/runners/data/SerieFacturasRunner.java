package com.ammgroup.sep.runners.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.SerieFacturas;
import com.ammgroup.sep.model.TipoIVA;
import com.ammgroup.sep.repository.SerieFacturasRepository;
import com.ammgroup.sep.repository.TipoIVARepository;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(13)
public class SerieFacturasRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(SerieFacturasRunner.class);
	
	@Autowired 
	private TipoIVARepository tivaRepository;
	
	@Autowired 
	private SerieFacturasRepository sfactRepository;
	
	@Override
	public void run(String... args) throws Exception {

		//Delete all existing records
		sfactRepository.deleteAll();

    	List<TipoIVA> tiva = tivaRepository.findByDescripcion("IVA 0%");
			if (tiva.size() == 1) {sfactRepository.save(new SerieFacturas("Facturas automáticas", "", false, "Factura rectificativa de ", true, "", false, tiva.get(0)));}
	    tiva = tivaRepository.findByDescripcion("IVA 16%");
			if (tiva.size() == 1) {sfactRepository.save(new SerieFacturas("Facturas congresos", "CONG", false, "Factura Congreso rectificativa de ", false, "Factura Cuota asistencia a congreso para ", false, tiva.get(0)));}

		logger.info("# of series de facturas: {}", sfactRepository.count());
		logger.info("------------------------");
		logger.info("All series de facturas unsorted:"); 
		List<SerieFacturas> sfact = sfactRepository.findAll(); 
		logger.info("{}", sfact);
		logger.info("------------------------");
		logger.info("All mseries de facturas sorted by descripcion in descending order");
		List<SerieFacturas> sfactSorted = sfactRepository.findAll(Sort.by(Sort.Direction.DESC, "descripcion"));
		logger.info("{}", sfactSorted); 
		logger.info("------------------------");

	}

}
