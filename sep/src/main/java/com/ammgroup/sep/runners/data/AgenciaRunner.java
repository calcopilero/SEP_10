package com.ammgroup.sep.runners.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.repository.AgenciaRepository;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(9)
public class AgenciaRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(AgenciaRunner.class);
	
	@Autowired 
	private AgenciaRepository agenciaRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		//Delete all existing records
		agenciaRepository.deleteAll();
		
		createAgencia("ALIBRI LIBRERIA, S.L.");
		createAgencia("EBSCO Information Services S.L.U.");
		createAgencia("EBSCO INTERNATIONAL, INC. - DEPARTMENT DB - USA");
		createAgencia("EBSCO INTERNATIONAL, INC. - HOLLAND");
		createAgencia("LIBRERÍA CÁMARA, S.L.");
		createAgencia("MARCIAL PONS LIBRERO S.L. - DPTO DE REVISTAS");
		createAgencia("Sustec Outsourcing, S.L.");
		createAgencia("Universidad de Córdoba");
		createAgencia("Universidad de Santiago de Compostela");
		createAgencia("Universitat de les Illes Balears");
		createAgencia("PTE. DEFINIR");

		logger.info("# of agencias: {}", agenciaRepository.count());
		logger.info("------------------------");
		logger.info("All agencias unsorted:"); 
		List<Agencia> agencias = agenciaRepository.findAll(); 
		logger.info("{}", agencias);
		logger.info("------------------------");
		logger.info("All agencias sorted by nombre in descending order");
		List<Agencia> agenciasSorted = agenciaRepository.findAll(Sort.by(Sort.Direction.DESC, "nombre"));
		logger.info("{}", agenciasSorted); 
		logger.info("------------------------");

	}
	
	private void createAgencia(String pnom) {
		
		Agencia age = new Agencia();
		
		age.setNombre(pnom);
		age.setCifnif("");
		age.setDomicilio("");
		age.setCp("");
		age.setLocalidad("");
		
		agenciaRepository.save(age);
	}

}

