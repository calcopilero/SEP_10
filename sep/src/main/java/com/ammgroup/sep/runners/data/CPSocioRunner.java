package com.ammgroup.sep.runners.data;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.FormaPago;
import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.model.Socio;
import com.ammgroup.sep.repository.PaisRepository;
import com.ammgroup.sep.repository.ProvinciaRepository;
import com.ammgroup.sep.repository.SocioRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(19)
public class CPSocioRunner implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(CPSocioRunner.class);
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired 
	private SocioRepository socioRepo;
	
	@Autowired 
	private ProvinciaRepository provRepo;
	
	@Autowired 
	private PaisRepository paisRepo;

	@Override
	public void run(String... args) throws Exception {

		List<Socio> lsoc = socioRepo.findAll();
		
		if (lsoc.size() > 0) {
			
			for (Socio soc: lsoc) {
				
				Optional<String> optStr = Optional.ofNullable(soc.getLocalidad());
				optStr.ifPresent((x) -> {
					
					var socwp = new Object(){ boolean updsoc = false; String cprov = ""; String sprov = "";};
					
					if (x.length() >= 6) {

						String cp = x.substring(0, 6);
						
						if (ModuloUtilidades.isNumeric(cp) && (cp.stripTrailing().length() == 5)) {
							
							socwp.cprov = cp.substring(0, 2);
							socwp.sprov = "";
							
							switch (socwp.cprov) {
							case "01":
								socwp.sprov = "ÁLAVA";
								break;
							case "02":
								socwp.sprov = "Albacete";
								break;
							case "03":
								socwp.sprov = "Alicante";
								break;
							case "04":
								socwp.sprov = "ALMERÍA";
								break;
							case "05":
								socwp.sprov = "Avila";
								break;
							case "06":
								socwp.sprov = "Badajoz";
								break;
							case "07":
								socwp.sprov = "BALEARES";
								break;
							case "08":
								socwp.sprov = "Barcelona";
								break;
							case "09":
								socwp.sprov = "BURGOS";
								break;
							case "10":
								socwp.sprov = "Cáceres";
								break;
							case "11":
								socwp.sprov = "Cádiz";
								break;
							case "12":
								socwp.sprov = "Castellon";
								break;
							case "13":
								socwp.sprov = "Ciudad Real";
								break;
							case "14":
								socwp.sprov = "CÓRDOBA";
								break;
							case "15":
								socwp.sprov = "A Coruña";
								break;
							case "16":
								socwp.sprov = "CUENCA";
								break;
							case "17":
								socwp.sprov = "Gerona";
								break;
							case "18":
								socwp.sprov = "GRANADA";
								break;
							case "19":
								socwp.sprov = "Guadalajara";
								break;
							case "20":
								socwp.sprov = "GUIPUZCOA";
								break;
							case "21":
								socwp.sprov = "Huelva";
								break;
							case "22":
								socwp.sprov = "Huesca";
								break;
							case "23":
								socwp.sprov = "JAEN";
								break;
							case "24":
								socwp.sprov = "León";
								break;
							case "25":
								socwp.sprov = "Lerida";
								break;
							case "26":
								socwp.sprov = "RIOJA";
								break;
							case "27":
								socwp.sprov = "Lugo";
								break;
							case "28":
								socwp.sprov = "Madrid";
								break;
							case "29":
								socwp.sprov = "Málaga";
								break;
							case "30":
								socwp.sprov = "MURCIA";
								break;
							case "31":
								socwp.sprov = "Navarra";
								break;
							case "32":
								socwp.sprov = "Orense";
								break;
							case "33":
								socwp.sprov = "Asturias";
								break;
							case "34":
								socwp.sprov = "Palencia";
								break;
							case "35":
								socwp.sprov = "Las Palmas";
								break;
							case "36":
								socwp.sprov = "PONTEVEDRA";
								break;
							case "37":
								socwp.sprov = "Salamanca";
								break;
							case "38":
								socwp.sprov = "Tenerife";
								break;
							case "39":
								socwp.sprov = "ASTURIAS";
								break;
							case "40":
								socwp.sprov = "SEGOVIA";
								break;
							case "41":
								socwp.sprov = "Sevilla";
								break;
							case "42":
								socwp.sprov = "Soria";
								break;
							case "43":
								socwp.sprov = "Tarragona";
								break;
							case "44":
								socwp.sprov = "Teruel";
								break;
							case "45":
								socwp.sprov = "Toledo";
								break;
							case "46":
								socwp.sprov = "VALENCIA";
								break;
							case "47":
								socwp.sprov = "Valladolid";
								break;
							case "48":
								socwp.sprov = "Vizcaya";
								break;
							case "49":
								socwp.sprov = "Zamora";
								break;
							case "50":
								socwp.sprov = "Zaragoza";
								break;
							case "51":
								socwp.sprov = "Ceuta";
								break;
							case "52":
								socwp.sprov = "Melilla";
								break;
							default:
								socwp.sprov = "";
								break;
							}
							
							if (socwp.sprov.length() > 0) {
							
								Optional<String> optStrCp = Optional.ofNullable(soc.getCp());
								optStrCp.ifPresentOrElse((z) -> {
								
									if (z.length() == 0) {
										soc.setCp(cp.stripTrailing());
										socwp.updsoc = true;
									}
								
								}, () -> {
									
									soc.setCp(cp.stripTrailing());
									socwp.updsoc = true;
								});
								
							}

							Optional<Provincia> optProv = Optional.ofNullable(soc.getProvincia());
							optProv.ifPresentOrElse((z) -> {
							
								if (z.getDescripcion().equalsIgnoreCase("PTE. DEFINIR")) {
									
									if (socwp.sprov.length() > 0) {

										List<Provincia> lprov = provRepo.findByDescripcion(socwp.sprov);
										
										if ((lprov.size() > 0)) {
											
											System.out.println("Prov.: " + lprov.size());
											
											soc.setProvincia(lprov.get(0));
											socwp.updsoc = true;
										}
									}
								}
							
							}, () -> {
								
								if (socwp.sprov.length() > 0) {

									List<Provincia> lprov = provRepo.findByDescripcion(socwp.sprov);
									
									if ((lprov.size() > 0)) {
										
										System.out.println("Prov.: " + lprov.size());
										
										soc.setProvincia(lprov.get(0));
										socwp.updsoc = true;
									}
								}
							});
						}
						
						if (socwp.updsoc) {
							
							Optional<Pais> optPais = Optional.ofNullable(soc.getPais());
							optPais.ifPresentOrElse((z) -> {
								
								if (z.getDescripcion().equalsIgnoreCase("PTE. DEFINIR")) {
							
									if (socwp.sprov.length() > 0) {
										
										List<Pais> lpais = paisRepo.findByDescripcion("España");
										
										if ((lpais.size() > 0)) {
											
											System.out.println("Pais: " + lpais.size());
											
											soc.setPais(lpais.get(0));
										}
									}
								}
							
							}, () -> {
								
								if (socwp.sprov.length() > 0) {
									
									List<Pais> lpais = paisRepo.findByDescripcion("España");
									
									if ((lpais.size() > 0)) {
										
										System.out.println("Pais: " + lpais.size());
										
										soc.setPais(lpais.get(0));
									}
								}
							});
							
							socioRepo.save(soc);
							logger.info("Saved socio " + soc.getCodigoSocio() + " - " + soc.getNombre() + " - CP: " + cp + " - PROV.: " + socwp.sprov);
						}
					}
				});	
			}
		}
	}
}
