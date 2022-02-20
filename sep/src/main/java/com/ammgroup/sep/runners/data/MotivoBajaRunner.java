package com.ammgroup.sep.runners.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.MotivoBaja;
import com.ammgroup.sep.repository.MotivoBajaRepository;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(6)
public class MotivoBajaRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(MotivoBajaRunner.class);
	
	@Autowired 
	private MotivoBajaRepository mbajaRepository;
	
	@Override
	public void run(String... args) throws Exception {

		//Delete all existing records
		mbajaRepository.deleteAll();

		mbajaRepository.save(new MotivoBaja("FALTA DE RESPUESTA"));
		mbajaRepository.save(new MotivoBaja("MOTIVOS ECONÓMICOS"));
		mbajaRepository.save(new MotivoBaja("FALLECIMIENTO"));
		mbajaRepository.save(new MotivoBaja("JUBILACIÓN"));
		mbajaRepository.save(new MotivoBaja("OTROS"));
		mbajaRepository.save(new MotivoBaja("RECIBO DEVUELTO"));

		logger.info("# of motivos de baja: {}", mbajaRepository.count());
		logger.info("------------------------");
		logger.info("All motivos de baja unsorted:"); 
		List<MotivoBaja> mbaja = mbajaRepository.findAll(); 
		logger.info("{}", mbaja);
		logger.info("------------------------");
		logger.info("All motivos de baja sorted by descripcion in descending order");
		List<MotivoBaja> mbajaSorted = mbajaRepository.findAll(Sort.by(Sort.Direction.DESC, "descripcion"));
		logger.info("{}", mbajaSorted); 
		logger.info("------------------------");

	}

}
