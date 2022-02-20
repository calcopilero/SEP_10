package com.ammgroup.sep.runners.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.ModoAcceso;
import com.ammgroup.sep.repository.ModoAccesoRepository;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(2)
public class ModoAccesoRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ModoAccesoRunner.class);
	
	@Autowired 
	private ModoAccesoRepository maccesoRepository;
	
	@Override
	public void run(String... args) throws Exception {

		//Delete all existing records
		maccesoRepository.deleteAll();

		maccesoRepository.save(new ModoAcceso("Correo Postal"));
		maccesoRepository.save(new ModoAcceso("Email"));
		maccesoRepository.save(new ModoAcceso("Online"));
		maccesoRepository.save(new ModoAcceso("Online - PDF"));
		maccesoRepository.save(new ModoAcceso("PTE. DEFINIR"));

		logger.info("# of modos de acceso: {}", maccesoRepository.count());
		logger.info("------------------------");
		logger.info("All modos de acceso unsorted:"); 
		List<ModoAcceso> macceso = maccesoRepository.findAll(); 
		logger.info("{}", macceso);
		logger.info("------------------------");
		logger.info("All modos de acceso sorted by descripcion in descending order");
		List<ModoAcceso> maccesoSorted = maccesoRepository.findAll(Sort.by(Sort.Direction.DESC, "descripcion"));
		logger.info("{}", maccesoSorted); 
		logger.info("------------------------");

	}

}
