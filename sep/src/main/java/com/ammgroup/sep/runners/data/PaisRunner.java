package com.ammgroup.sep.runners.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.repository.PaisRepository;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(4)
public class PaisRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(PaisRunner.class);
	
	@Autowired 
	private PaisRepository paisRepository;

	@Override
	public void run(String... args) throws Exception {

		//Delete all existing records
		paisRepository.deleteAll();

		paisRepository.save(new Pais("ESPAÑA"));
		paisRepository.save(new Pais("Argentina"));
		paisRepository.save(new Pais("Brasil"));
		paisRepository.save(new Pais("Chile"));
		paisRepository.save(new Pais("Colombia"));
		paisRepository.save(new Pais("ITALIA"));
		paisRepository.save(new Pais("México"));
		paisRepository.save(new Pais("Perú"));
		paisRepository.save(new Pais("PTE. DEFINIR"));

		logger.info("# of paises: {}", paisRepository.count());
		logger.info("------------------------");
		logger.info("All paises unsorted:"); 
		List<Pais> paises = paisRepository.findAll(); 
		logger.info("{}", paises);
		logger.info("------------------------");
		logger.info("All paises sorted by descripcion in descending order");
		List<Pais> paisesSorted = paisRepository.findAll(Sort.by(Sort.Direction.DESC, "descripcion"));
		logger.info("{}", paisesSorted); 
		logger.info("------------------------");

	}

}
