package com.ammgroup.sep.runners.data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.EstadoReclamacion;
import com.ammgroup.sep.model.Reclamacion;
import com.ammgroup.sep.model.Socio;
import com.ammgroup.sep.repository.EstadoReclamacionRepository;
import com.ammgroup.sep.repository.ReclamacionRepository;
import com.ammgroup.sep.repository.SocioRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(16)
public class ReclamacionRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ReclamacionRunner.class);
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired 
	private ReclamacionRepository reclRepository;
	
	@Autowired 
	private SocioRepository socioRepository;
	
	@Autowired 
	private EstadoReclamacionRepository estrecRepository;

	@Override
	public void run(String... args) throws Exception {
		
		generateReclamacion("07466575A", "18-10-2021", "Comentario de prueba 1",  "21-10-2021", "Anotaciones de prueba 1");
		generateReclamacion("51462855D", "15-10-2021", "Comentario de prueba 2",  "01-11-2021", "Anotaciones de prueba 2");
		generateReclamacion("51396825K", "17-10-2021", "Comentario de prueba 3",  null, "Anotaciones de prueba 3");

		logger.info("# of reclamaciones: {}", reclRepository.count());
		logger.info("------------------------");
		logger.info("All reclamaciones unsorted:"); 
		List<Reclamacion> reclamaciones = reclRepository.findAll(); 
		logger.info("{}", reclamaciones);
		logger.info("------------------------");
		logger.info("All reclamaciones sorted by numero in descending order");
		List<Reclamacion> reclamacionesSorted = reclRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaReclamacion"));
		logger.info("{}", reclamacionesSorted); 
		logger.info("------------------------");
		
	}
	
	private void generateReclamacion(String pmSocCifnif, String pmFrecl, String pmComent, String pmFresp, String pmAnot) {
		
    	//Obtain the socio who generates the factura
    	List<Socio> sociosl = socioRepository.findByCifnif(pmSocCifnif);
    	
    	//Obtain estado de reclamacion to be set in facturas when created in an automatic way
    	List<EstadoReclamacion> erecl = estrecRepository.findByDescripcion("En curso");
		
    	if ((sociosl.size() > 0) && (erecl.size() > 0)) {
    	
			Reclamacion recl = new Reclamacion();
			
			Date frecl = mutils.getDateFromString(pmFrecl, mutils.DATE_FORMAT);
			
			//Get the year of fecha factura
			int year = mutils.getYearFromDate(frecl);
			
			Optional<Integer> numSf = Optional.ofNullable(reclRepository.getMaximumReclamacionNumber(mutils.getDateFromString("01-01-" + year, mutils.DATE_FORMAT), mutils.getDateFromString("31-12-" + year, mutils.DATE_FORMAT)));
				numSf.ifPresentOrElse((x) -> {
				
					int proxNum = x.intValue() + 1;
					
					recl.setNumero(proxNum);

				}, () -> {
					recl.setNumero(1);
				});
			
			recl.setSocio(sociosl.get(0));
			recl.setAgencia(null);
			recl.setFechaReclamacion(mutils.getDateFromString(pmFrecl, mutils.DATE_FORMAT));
			recl.setReclamacionComentario(pmComent);
			Optional<String> dateOpt = Optional.ofNullable(pmFresp);
				dateOpt.ifPresent((x) -> recl.setFechaRespuesta(mutils.getDateFromString(pmFresp, mutils.DATE_FORMAT)));
			recl.setAnotaciones(pmAnot);
			recl.setEstadoReclamacion(erecl.get(0));
			
			reclRepository.save(recl);
			
    	}
	}

}
