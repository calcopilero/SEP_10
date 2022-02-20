package com.ammgroup.sep.runners.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.TipoIVA;
import com.ammgroup.sep.repository.TipoIVARepository;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(12)
public class TipoIVARunner implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(TipoIVARunner.class);
	
	@Autowired 
	private TipoIVARepository tivaRepository;

	@Override
	public void run(String... args) throws Exception {

		//Delete all existing records
		tivaRepository.deleteAll();

		tivaRepository.save(new TipoIVA("IVA 0% (Exento IVA)", 0D, "Exento IVA (Real decreto 26/1988)"));
		tivaRepository.save(new TipoIVA("IVA 16%", 16D, "IVA 16%"));

		logger.info("# of tipos de iva: {}", tivaRepository.count());
		logger.info("------------------------");
		logger.info("All tipos de iva unsorted:"); 
		List<TipoIVA> tiva = tivaRepository.findAll(); 
		logger.info("{}", tiva);
		logger.info("------------------------");
		logger.info("All tipos de iva sorted by descripcion in descending order");
		List<TipoIVA> tivaSorted = tivaRepository.findAll(Sort.by(Sort.Direction.DESC, "descripcion"));
		logger.info("{}", tivaSorted); 
		logger.info("------------------------");

	}
}
