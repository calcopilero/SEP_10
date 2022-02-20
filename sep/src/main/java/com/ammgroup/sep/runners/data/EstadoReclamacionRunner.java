package com.ammgroup.sep.runners.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.EstadoReclamacion;
import com.ammgroup.sep.repository.EstadoReclamacionRepository;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(15)
public class EstadoReclamacionRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(MotivoBajaRunner.class);
	
	@Autowired 
	private EstadoReclamacionRepository estrecRepository;
	
	@Override
	public void run(String... args) throws Exception {

		//Delete all existing records
		estrecRepository.deleteAll();

		estrecRepository.save(new EstadoReclamacion("En curso"));
		estrecRepository.save(new EstadoReclamacion("Pendiente de aclaraciones"));
		estrecRepository.save(new EstadoReclamacion("Cerrada"));

		logger.info("# of estados de reclamación: {}", estrecRepository.count());
		logger.info("------------------------");
		logger.info("All estados de reclamación unsorted:"); 
		List<EstadoReclamacion> estrec = estrecRepository.findAll(); 
		logger.info("{}", estrec);
		logger.info("------------------------");
		logger.info("All estados de reclamación sorted by descripcion in descending order");
		List<EstadoReclamacion> estrecSorted = estrecRepository.findAll(Sort.by(Sort.Direction.DESC, "descripcion"));
		logger.info("{}", estrecSorted); 
		logger.info("------------------------");

	}

}
