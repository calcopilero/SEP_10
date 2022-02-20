package com.ammgroup.sep.runners.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.FormaPago;
import com.ammgroup.sep.repository.FormaPagoRepository;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(7)
public class FormaPagoRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(FormaPagoRunner.class);
	
	@Autowired 
	private FormaPagoRepository fpagoRepository;
	
	@Override
	public void run(String... args) throws Exception {

		//Delete all existing records
		fpagoRepository.deleteAll();

		fpagoRepository.save(new FormaPago("Recibo Domiciliado", "Forma de pago: Recibo domiciliado"));
		fpagoRepository.save(new FormaPago("Transferencia", ""));
		fpagoRepository.save(new FormaPago("Pay-Pal", ""));
		fpagoRepository.save(new FormaPago("PTE. DEFINIR", ""));

		logger.info("# of formas de pago: {}", fpagoRepository.count());
		logger.info("------------------------");
		logger.info("All formas de pago unsorted:"); 
		List<FormaPago> fpago = fpagoRepository.findAll(); 
		logger.info("{}", fpago);
		logger.info("------------------------");
		logger.info("All formas de pago sorted by descripcion in descending order");
		List<FormaPago> fpagoSorted = fpagoRepository.findAll(Sort.by(Sort.Direction.DESC, "descripcion"));
		logger.info("{}", fpagoSorted); 
		logger.info("------------------------");
	}

}
