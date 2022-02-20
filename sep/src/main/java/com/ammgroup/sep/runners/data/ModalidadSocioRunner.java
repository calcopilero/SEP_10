package com.ammgroup.sep.runners.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.ModalidadSocio;
import com.ammgroup.sep.repository.ModalidadSocioRepository;

@SuppressWarnings(value = { "unused" })
//Component
//@Order(1)
public class ModalidadSocioRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ModalidadSocioRunner.class);
	
	@Autowired 
	private ModalidadSocioRepository msocioRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		//Delete all existing records
		msocioRepository.deleteAll();

		msocioRepository.save(new ModalidadSocio("Base de Indexación", 0.0D, "", ""));
		msocioRepository.save(new ModalidadSocio("INTERCAMBIO", 0.0D, "", ""));
		msocioRepository.save(new ModalidadSocio("INTERCAMBIO", 0.0D, "", ""));
		msocioRepository.save(new ModalidadSocio("Mejor comunicación Post-Doctoral", 0.0D, "", ""));
		msocioRepository.save(new ModalidadSocio("Otros", 0.0D, "", ""));
		msocioRepository.save(new ModalidadSocio("Premio a Investigadores Novel", 0.0D, "", ""));
		msocioRepository.save(new ModalidadSocio("Socio Emérito", 0.0D, "", ""));
		msocioRepository.save(new ModalidadSocio("Socio Estudiante", 0.0D, "", ""));
		msocioRepository.save(new ModalidadSocio("Socio Extranjero", 0.0D, "", ""));
		msocioRepository.save(new ModalidadSocio("Socio Extranjero Estudiante", 0.0D, "", ""));
		msocioRepository.save(new ModalidadSocio("Socio Nacional", 0.0D, "", ""));
		msocioRepository.save(new ModalidadSocio("Suscriptor Extranjero", 0.0D, "", ""));
		msocioRepository.save(new ModalidadSocio("Suscriptor Extranjero con Descuento", 0.0D, "", ""));
		msocioRepository.save(new ModalidadSocio("Suscriptor Nacional", 0.0D, "", ""));
		msocioRepository.save(new ModalidadSocio("Suscriptor Nacional con Descuento", 0.0D, "", ""));
		msocioRepository.save(new ModalidadSocio("PTE. DEFINIR", 0.0D, "", ""));
		
		logger.info("# of modalidades de socios: {}", msocioRepository.count());
		logger.info("------------------------");
		logger.info("All modalidades de socios unsorted:"); 
		List<ModalidadSocio> msocios = msocioRepository.findAll(); 
		logger.info("{}", msocios);
		logger.info("------------------------");
		logger.info("All modalidades de socios sorted by descripcion in descending order");
		List<ModalidadSocio> msociosSorted = msocioRepository.findAll(Sort.by(Sort.Direction.DESC, "descripcion"));
		logger.info("{}", msociosSorted); 
		logger.info("------------------------");

	}

}
