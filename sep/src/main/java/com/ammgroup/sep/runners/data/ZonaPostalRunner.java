package com.ammgroup.sep.runners.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.ZonaPostal;
import com.ammgroup.sep.repository.ZonaPostalRepository;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(5)
public class ZonaPostalRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ZonaPostalRunner.class);
	
	@Autowired 
	private ZonaPostalRepository zpostalRepository;
	
	@Override
	public void run(String... args) throws Exception {

		//Delete all existing records
		zpostalRepository.deleteAll();

		zpostalRepository.save(new ZonaPostal("Zona 1"));
		zpostalRepository.save(new ZonaPostal("Zona 2"));
		zpostalRepository.save(new ZonaPostal("PTE. DEFINIR"));

		logger.info("# of zonas postales: {}", zpostalRepository.count());
		logger.info("------------------------");
		logger.info("All zonas postales unsorted:"); 
		List<ZonaPostal> zpostales = zpostalRepository.findAll(); 
		logger.info("{}", zpostales);
		logger.info("------------------------");
		logger.info("All zonas postales sorted by descripcion in descending order");
		List<ZonaPostal> zpostalesSorted = zpostalRepository.findAll(Sort.by(Sort.Direction.DESC, "descripcion"));
		logger.info("{}", zpostalesSorted); 
		logger.info("------------------------");
		}

}
