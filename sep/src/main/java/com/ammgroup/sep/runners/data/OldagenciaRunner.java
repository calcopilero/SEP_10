package com.ammgroup.sep.runners.data;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.Oldagencia;
import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.repository.AgenciaRepository;
import com.ammgroup.sep.repository.OldagenciaRepository;
import com.ammgroup.sep.repository.PaisRepository;
import com.ammgroup.sep.repository.ProvinciaRepository;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(17)
public class OldagenciaRunner implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(OldagenciaRunner.class);
	
	@Autowired 
	private OldagenciaRepository oldageRepo;
	
	@Autowired 
	private AgenciaRepository ageRepo;
	
	@Autowired 
	private ProvinciaRepository provRepo;
	
	@Autowired 
	private PaisRepository paisRepo;

	@Override
	public void run(String... args) throws Exception {
		
		//Delete all existing records
		ageRepo.deleteAll();
		
		int conts = 0; 
		
		List<Oldagencia> oldalist = oldageRepo.findAll();
		if (oldalist.size() > 0) {
			
			 var boolwp = new Object(){ boolean isfound = false; };
			
			List<Provincia> provl = provRepo.findAll();
		    var prwp = new Object(){ Provincia def = null; };
		    boolwp.isfound = false;
			
			for (Provincia it: provl) {
				
				Optional<String >optStr2 = Optional.ofNullable(it.getDescripcion());
					optStr2.ifPresent((x) -> {
					
						if (x.equals("PTE. DEFINIR")) {
							prwp.def = it;
							boolwp.isfound = true;
						}
					});	

				if (boolwp.isfound) break;
			}
			
			List<Pais> paisl = paisRepo.findAll();
		    var pawp = new Object(){ Pais def = null; };
		    boolwp.isfound = false;
			
			for (Pais it: paisl) {
				
				Optional<String >optStr2 = Optional.ofNullable(it.getDescripcion());
					optStr2.ifPresent((x) -> {
					
						if (x.equals("PTE. DEFINIR")) {
							pawp.def = it;
							boolwp.isfound = true;
						}
					});	

				if (boolwp.isfound) break;
			}
			
			for (Oldagencia os: oldalist) {
				
				conts++;
				
				Agencia s = new Agencia();
			
				Optional<String> optStr = Optional.ofNullable(os.getCif());
					optStr.ifPresentOrElse((x) -> {
						
						int maxlength = 12;
						
						if (x.length() >= maxlength) {
							s.setCifnif(x.substring(0, maxlength));
						} else {
							if  (x.length() == 0) {
								s.setCifnif("PTE. DEFINIR");
							} else {
								s.setCifnif(x);
							}
						}
							
					}, () -> {
						s.setCifnif("PTE. DEFINIR");
					});
					
				optStr = Optional.ofNullable(os.getNombre());
					optStr.ifPresent((x) -> {
						
						int maxlength = 70;

						if (x.length() >= maxlength) {
							s.setNombre(x.substring(0, maxlength));
						} else {
							s.setNombre(x);
						}
						
					});
					
				optStr = Optional.ofNullable(os.getEmail());
					optStr.ifPresent((x) -> {
						
						int maxlength = 80;

						if (x.length() >= maxlength) {
							s.setEmail(x.substring(0, maxlength));
						} else {
							s.setEmail(x);
						}
						
					});
					
				optStr = Optional.ofNullable(os.getPersonaContacto());
					optStr.ifPresent((x) -> {
						
						int maxlength = 70;

						if (x.length() >= maxlength) {
							s.setPersonaContacto(x.substring(0, maxlength));
						} else {
							s.setPersonaContacto(x);
						}
						
					});
					
				optStr = Optional.ofNullable(os.getCp());
					optStr.ifPresent((x) -> {
						
						int maxlength = 5;

						if (x.length() >= maxlength) {
							s.setCp(x.substring(0, maxlength));
						} else {
							s.setCp(x);
						}
						
					});
					
				optStr = Optional.ofNullable(os.getDireccion());
					optStr.ifPresent((x) -> {
						
						int maxlength = 100;

						if (x.length() >= maxlength) {
							s.setDomicilio(x.substring(0, maxlength));
						} else {
							s.setDomicilio(x);
						}
						
					});
					
				optStr = Optional.ofNullable(os.getLocalidad());
					optStr.ifPresent((x) -> {
						
						int maxlength = 70;

						if (x.length() >= maxlength) {
							s.setLocalidad(x.substring(0, maxlength));
						} else {
							s.setLocalidad(x);
						}
						
					});
					
				optStr = Optional.ofNullable(os.getTelefono());
					optStr.ifPresent((x) -> {
						
						int maxlength = 12;

						if (x.length() >= maxlength) {
							s.setTelefono(x.substring(0, maxlength));
						} else {
							s.setTelefono(x);
						}
						
					});
					
				optStr = Optional.ofNullable(os.getProvincia());
					optStr.ifPresent((x) -> {
						
						boolwp.isfound = false;
						
						if ((provl.size() > 0) && (x.length() > 0)) {
							
							for (Provincia it: provl) {
								
								Optional<String >optStr2 = Optional.ofNullable(it.getDescripcion());
									optStr2.ifPresent((y) -> {
										if (x.equals(y)) {
											s.setProvincia(it);
											boolwp.isfound = true;
										}
									});		
								
								if (boolwp.isfound) break;
							}
							
							if (!boolwp.isfound) {
								s.setProvincia(prwp.def);
							}
						}

					});
					
				optStr = Optional.ofNullable(os.getPais());
					optStr.ifPresent((x) -> {
						
						boolwp.isfound = false;
						
						if ((paisl.size() > 0) && (x.length() > 0)) {
							
							for (Pais it: paisl) {
								
								Optional<String >optStr2 = Optional.ofNullable(it.getDescripcion());
									optStr2.ifPresent((y) -> {
										if (x.equals(y)) {
											s.setPais(it);
											boolwp.isfound = true;
										}
									});		
								
								if (boolwp.isfound) break;
							}
							
							if (!boolwp.isfound) {
								s.setPais(pawp.def);
							}
						}

					});
					
				ageRepo.save(s);
					
				logger.info("Saved agencia " + conts + " " + s.getNombre());
			}
		}
	}

}
