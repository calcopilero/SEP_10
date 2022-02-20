package com.ammgroup.sep.runners.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.EstadoFactura;
import com.ammgroup.sep.repository.EstadoFacturaRepository;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(11)
public class EstadoFacturaRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(EstadoFacturaRunner.class);
	
	@Autowired 
	private EstadoFacturaRepository efacturaRepository;
	
	@Override
	public void run(String... args) throws Exception {

		//Delete all existing records
		efacturaRepository.deleteAll();

		efacturaRepository.save(new EstadoFactura("Impagada", true, false));
		efacturaRepository.save(new EstadoFactura("Pagada", false, false));
		efacturaRepository.save(new EstadoFactura("Anulada", false, false));
		efacturaRepository.save(new EstadoFactura("Rectificada", false, true));

		logger.info("# of estados de factura: {}", efacturaRepository.count());
		logger.info("------------------------");
		logger.info("All estados de factura unsorted:"); 
		List<EstadoFactura> efactura = efacturaRepository.findAll(); 
		logger.info("{}", efactura);
		logger.info("------------------------");
		logger.info("All estados de factura sorted by descripcion in descending order");
		List<EstadoFactura> efacturaSorted = efacturaRepository.findAll(Sort.by(Sort.Direction.DESC, "descripcion"));
		logger.info("{}", efacturaSorted); 
		logger.info("------------------------");

	}

}
