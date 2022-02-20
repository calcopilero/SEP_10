package com.ammgroup.sep.runners.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.Descuento;
import com.ammgroup.sep.repository.DescuentoRepository;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(8)
public class DescuentoRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(DescuentoRunner.class);
	
	@Autowired 
	private DescuentoRepository descuentoRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		//Delete all existing records
		descuentoRepository.deleteAll();

		descuentoRepository.save(new Descuento("Agencia Impreso", 20.5, "Descuento Agencia Edición impresa"));
		descuentoRepository.save(new Descuento("Agencia Online", 10.0, "Descuento Agencia Acceso online"));

		logger.info("# of descuentos: {}", descuentoRepository.count());
		logger.info("------------------------");
		logger.info("All descuentos unsorted:"); 
		List<Descuento> descuentos = descuentoRepository.findAll(); 
		logger.info("{}", descuentos);
		logger.info("------------------------");
		logger.info("All descuentos sorted by descripcion in descending order");
		List<Descuento> descuentosSorted = descuentoRepository.findAll(Sort.by(Sort.Direction.DESC, "descripcion"));
		logger.info("{}", descuentosSorted); 
		logger.info("------------------------");

	}

}
