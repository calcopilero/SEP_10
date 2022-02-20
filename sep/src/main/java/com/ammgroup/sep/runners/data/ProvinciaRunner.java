package com.ammgroup.sep.runners.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.repository.ProvinciaRepository;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(3)
public class ProvinciaRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ProvinciaRunner.class);
	
	@Autowired 
	private ProvinciaRepository provinciaRepository;
	
	@Override
	public void run(String... args) throws Exception {

		//Delete all existing records
		provinciaRepository.deleteAll();

		provinciaRepository.save(new Provincia("JAEN"));
		provinciaRepository.save(new Provincia("SALAMANCA"));
		provinciaRepository.save(new Provincia("Tarragona"));
		provinciaRepository.save(new Provincia("A Coruña"));
		provinciaRepository.save(new Provincia("ÁLAVA"));
		provinciaRepository.save(new Provincia("Alicante"));
		provinciaRepository.save(new Provincia("ALMERÍA"));
		provinciaRepository.save(new Provincia("ANDALUCÍA"));
		provinciaRepository.save(new Provincia("ARAGÓN"));
		provinciaRepository.save(new Provincia("ASTURIAS"));
		provinciaRepository.save(new Provincia("BALEARES"));
		provinciaRepository.save(new Provincia("Barcelona"));
		provinciaRepository.save(new Provincia("BURGOS"));
		provinciaRepository.save(new Provincia("Cáceres"));
		provinciaRepository.save(new Provincia("Cádiz"));
		provinciaRepository.save(new Provincia("CANARIAS"));
		provinciaRepository.save(new Provincia("CASTILLA LEÓN"));
		provinciaRepository.save(new Provincia("CEUTA"));
		provinciaRepository.save(new Provincia("CÓRDOBA"));
		provinciaRepository.save(new Provincia("CUENCA"));
		provinciaRepository.save(new Provincia("GRAN CANARIA"));
		provinciaRepository.save(new Provincia("GRANADA"));
		provinciaRepository.save(new Provincia("GUIPUZCOA"));
		provinciaRepository.save(new Provincia("Guipuzkoa"));
		provinciaRepository.save(new Provincia("GALICIA"));
		provinciaRepository.save(new Provincia("EXTREMADURA"));
		provinciaRepository.save(new Provincia("CATALUÑA"));
		provinciaRepository.save(new Provincia("Huelva"));
		provinciaRepository.save(new Provincia("ISLAS BALEARES"));
		provinciaRepository.save(new Provincia("LA CORUÑA"));
		provinciaRepository.save(new Provincia("LA RIOJA"));
		provinciaRepository.save(new Provincia("LAS PALMAS"));
		provinciaRepository.save(new Provincia("León"));
		provinciaRepository.save(new Provincia("Madrid"));
		provinciaRepository.save(new Provincia("Málaga"));
		provinciaRepository.save(new Provincia("Melilla"));
		provinciaRepository.save(new Provincia("MURCIA"));
		provinciaRepository.save(new Provincia("Navarra"));
		provinciaRepository.save(new Provincia("PAÍS VASCO"));
		provinciaRepository.save(new Provincia("Palencia"));
		provinciaRepository.save(new Provincia("PONTEVEDRA"));
		provinciaRepository.save(new Provincia("Salamanca"));
		provinciaRepository.save(new Provincia("SANTA CRUZ DE TENERIFE"));
		provinciaRepository.save(new Provincia("Santiago"));
		provinciaRepository.save(new Provincia("SEGOVIA"));
		provinciaRepository.save(new Provincia("Sevilla"));
		provinciaRepository.save(new Provincia("TENERIFE"));
		provinciaRepository.save(new Provincia("VALENCIA"));
		provinciaRepository.save(new Provincia("Valladolid"));
		provinciaRepository.save(new Provincia("Vizcaya"));
		provinciaRepository.save(new Provincia("Zaragoza"));
		provinciaRepository.save(new Provincia("PTE. DEFINIR"));

		logger.info("# of provincias: {}", provinciaRepository.count());
		logger.info("------------------------");
		logger.info("All provincias unsorted:"); 
		List<Provincia> provincias = provinciaRepository.findAll(); 
		logger.info("{}", provincias);
		logger.info("------------------------");
		logger.info("All provincias sorted by descripcion in descending order");
		List<Provincia> provinciasSorted = provinciaRepository.findAll(Sort.by(Sort.Direction.DESC, "descripcion"));
		logger.info("{}", provinciasSorted); 
		logger.info("------------------------");
	}

}
