package com.ammgroup.sep.runners.data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.FormaPago;
import com.ammgroup.sep.model.ModalidadSocio;
import com.ammgroup.sep.model.ModoAcceso;
import com.ammgroup.sep.model.MotivoBaja;
import com.ammgroup.sep.model.Oldsocio;
import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.model.Socio;
import com.ammgroup.sep.model.ZonaPostal;
import com.ammgroup.sep.repository.AgenciaRepository;
import com.ammgroup.sep.repository.FormaPagoRepository;
import com.ammgroup.sep.repository.ModalidadSocioRepository;
import com.ammgroup.sep.repository.ModoAccesoRepository;
import com.ammgroup.sep.repository.MotivoBajaRepository;
import com.ammgroup.sep.repository.OldsocioRepository;
import com.ammgroup.sep.repository.PaisRepository;
import com.ammgroup.sep.repository.ProvinciaRepository;
import com.ammgroup.sep.repository.SocioRepository;
import com.ammgroup.sep.repository.ZonaPostalRepository;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(18)
public class OldsocioRunner implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(OldsocioRunner.class);
	
	@Autowired 
	private OldsocioRepository oldsocRepo;

	@Autowired 
	private SocioRepository socioRepo;
	
	@Autowired 
	private ModalidadSocioRepository modsocRepo;
	
	@Autowired 
	private ModoAccesoRepository maccesoRepo;
	
	@Autowired 
	private FormaPagoRepository fpagoRepo;
	
	@Autowired 
	private AgenciaRepository agenciaRepo;
	
	@Autowired 
	private MotivoBajaRepository mbajaRepo;
	
	@Autowired 
	private ProvinciaRepository provRepo;
	
	@Autowired 
	private PaisRepository paisRepo;
	
	@Autowired 
	private ZonaPostalRepository zpostRepo;
	
	@Override
	public void run(String... args) throws Exception {
		
		//Delete all existing records
		socioRepo.deleteAll();
		
		int conts = 0; 

		List<Oldsocio> oldslist = oldsocRepo.findAll();
		if (oldslist.size() > 0) {
			
		    var boolwp = new Object(){ boolean isfound = false; };

			List<ModalidadSocio> modsocl = modsocRepo.findAll();
		    var mswp = new Object(){ ModalidadSocio def = null; };
		    boolwp.isfound = false;
			
			for (ModalidadSocio it: modsocl) {
				
				Optional<String >optStr2 = Optional.ofNullable(it.getDescripcion());
					optStr2.ifPresent((x) -> {
					
						if (x.equals("PTE. DEFINIR")) {
							mswp.def = it;
							boolwp.isfound = true;
						}
					});	

				if (boolwp.isfound) break;
			}
			
			if (boolwp.isfound) System.out.println("Modalidad Socio def: " + mswp.def.getDescripcion());
			
			List<ModoAcceso> maccesol = maccesoRepo.findAll();
		    var mawp = new Object(){ ModoAcceso def = null; };
		    boolwp.isfound = false;
			
			for (ModoAcceso it: maccesol) {
				
				Optional<String >optStr2 = Optional.ofNullable(it.getDescripcion());
					optStr2.ifPresent((x) -> {
					
						if (x.equals("PTE. DEFINIR")) {
							mawp.def = it;
							boolwp.isfound = true;
						}
					});	

				if (boolwp.isfound) break;
			}
			
			if (boolwp.isfound) System.out.println("Modo acceso def: " + mawp.def.getDescripcion());

			List<FormaPago> fpagol = fpagoRepo.findAll();
		    var fpwp = new Object(){ FormaPago def = null; };
		    boolwp.isfound = false;
			
			for (FormaPago it: fpagol) {
				
				Optional<String >optStr2 = Optional.ofNullable(it.getDescripcion());
					optStr2.ifPresent((x) -> {
					
						if (x.equals("PTE. DEFINIR")) {
							fpwp.def = it;
							boolwp.isfound = true;
						}
					});	

				if (boolwp.isfound) break;
			}
			
			if (boolwp.isfound) System.out.println("Forma pago def: " + fpwp.def.getDescripcion());

			List<Agencia> agencial = agenciaRepo.findAll();
		    var agwp = new Object(){ Agencia def = null; };
		    boolwp.isfound = false;
			
			for (Agencia it: agencial) {
				
				Optional<String >optStr2 = Optional.ofNullable(it.getNombre());
					optStr2.ifPresent((x) -> {
					
						if (x.equals("PTE. DEFINIR")) {
							agwp.def = it;
							boolwp.isfound = true;
						}
					});	

				if (boolwp.isfound) break;
			}	
			
			if (boolwp.isfound) System.out.println("Agencia def: " + agwp.def.getNombre());
			
			List<MotivoBaja> mbajal = mbajaRepo.findAll();
		    var mbwp = new Object(){ MotivoBaja def = null; };
		    boolwp.isfound = false;
			
			for (MotivoBaja it: mbajal) {
				
				Optional<String >optStr2 = Optional.ofNullable(it.getDescripcion());
					optStr2.ifPresent((x) -> {
					
						if (x.equals("PTE. DEFINIR")) {
							mbwp.def = it;
							boolwp.isfound = true;
						}
					});	

				if (boolwp.isfound) break;
			}	
			
			if (boolwp.isfound) System.out.println("Motivo baja def: " + mbwp.def.getDescripcion());
			
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
			
			if (boolwp.isfound) System.out.println("Provincia def: " + prwp.def.getDescripcion());
			
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
			
			if (boolwp.isfound) System.out.println("Pais def: " + pawp.def.getDescripcion());

			List<ZonaPostal> zpostl = zpostRepo.findAll();
		    var zpwp = new Object(){ ZonaPostal def = null; };
		    boolwp.isfound = false;
			
			for (ZonaPostal it: zpostl) {
				
				Optional<String >optStr2 = Optional.ofNullable(it.getDescripcion());
					optStr2.ifPresent((x) -> {
					
						if (x.equals("PTE. DEFINIR")) {
							zpwp.def = it;
							boolwp.isfound = true;
						}
					});	

				if (boolwp.isfound) break;
			}	
			
			if (boolwp.isfound) System.out.println("Zona postal def: " + zpwp.def.getDescripcion());
			
			var strwp = new Object(){ String euros = ""; };

			for (Oldsocio os: oldslist) {
				
				conts++;
				
				Socio s = new Socio();
				
				Optional<Integer> optInt = Optional.ofNullable(os.getNumSocio());
					optInt.ifPresent((x) -> {
						s.setCodigoSocio(x);
					});

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
					
				optStr = Optional.ofNullable(os.getNumeroPedido());
					optStr.ifPresent((x) -> {

						int maxlength = 30;

						if (x.length() >= maxlength) {
							s.setReferencia(x.substring(0, maxlength));
						} else {
							s.setReferencia(x);
						}
						
					});
				
				optStr = Optional.ofNullable(os.getNombreSocio());
					optStr.ifPresent((x) -> {
						
						int maxlength = 70;

						if (x.length() >= maxlength) {
							s.setNombre(x.substring(0, maxlength));
						} else {
							s.setNombre(x);
						}
						
					});
					
				optStr = Optional.ofNullable(os.getNumCuenta());
					optStr.ifPresent((x) -> {
						
						int maxlength = 30;

						if (x.length() >= maxlength) {
							s.setIbanccc(x.substring(0, maxlength));
						} else {
							s.setIbanccc(x);
						}
						
					});
				
				optStr = Optional.ofNullable(os.getRecyt());
					optStr.ifPresent((x) -> {
						
						int maxlength = 50;

						if (x.length() >= maxlength) {
							s.setOjs(x.substring(0, maxlength));
						} else {
							s.setOjs(x);
						}
						
					});
					
				optStr = Optional.ofNullable(os.getEmailSocio());
					optStr.ifPresent((x) -> {
						
						int maxlength = 80;

						if (x.length() >= maxlength) {
							s.setEmail(x.substring(0, maxlength));
						} else {
							s.setEmail(x);
						}
						
					});
					
				strwp.euros = "";
				
				optStr = Optional.ofNullable(os.getEuros());
					optStr.ifPresent((x) -> {
						
						int maxlength = 20;

						if (x.length() >= maxlength) {
							strwp.euros = "Euros: " + x.substring(0, maxlength);
						} else {
							strwp.euros = "Euros: " + x;
						}
						
					});
					
				optStr = Optional.ofNullable(os.getComentario());
					optStr.ifPresent((x) -> {
						
						int maxlength = 245;

						if ((x + strwp.euros).length() >= maxlength) {
							s.setAnotaciones((strwp.euros.strip() + " / " + x).substring(0, maxlength));
						} else {
							s.setAnotaciones((strwp.euros.strip() + " / " + x));
						}
						
					});

				optStr = Optional.ofNullable(os.getContactoSEP());
					optStr.ifPresent((x) -> {
						
						int maxlength = 70;

						if (x.length() >= maxlength) {
							s.setContactoSep(x.substring(0, maxlength));
						} else {
							s.setContactoSep(x);
						}
						
					});

				optStr = Optional.ofNullable(os.getDomicilioSocio());
					optStr.ifPresent((x) -> {
						
						int maxlength = 100;

						if (x.length() >= maxlength) {
							s.setDomicilio(x.substring(0, maxlength));
						} else {
							s.setDomicilio(x);
						}
						
					});

				optStr = Optional.ofNullable(os.getLocalidadSocio());
					optStr.ifPresent((x) -> {
						
						int maxlength = 70;

						if (x.length() >= maxlength) {
							s.setLocalidad(x.substring(0, maxlength));
						} else {
							s.setLocalidad(x);
						}
						
					});

				optStr = Optional.ofNullable(os.getTelefonoFijo());
					optStr.ifPresent((x) -> {
						
						int maxlength = 12;

						if (x.length() >= maxlength) {
							s.setTelefono(x.substring(0, maxlength));
						} else {
							s.setTelefono(x);
						}
						
					});

				optStr = Optional.ofNullable(os.getTelefonoMovil());
					optStr.ifPresent((x) -> {
						
						int maxlength = 12;

						if (x.length() >= maxlength) {
							s.setMovil(x.substring(0, maxlength));
						} else {
							s.setMovil(x);
						}

					});

				optStr = Optional.ofNullable(os.getTitulacion());
					optStr.ifPresent((x) -> {
						
						int maxlength = 70;

						if (x.length() >= maxlength) {
							s.setTitulacion(x.substring(0, maxlength));
						} else {
							s.setTitulacion(x);
						}
						
					});
			
				optStr = Optional.ofNullable(os.getCentroTrabajo());
					optStr.ifPresent((x) -> {
						
						int maxlength = 70;

						if (x.length() >= maxlength) {
							s.setCentroTrabajo(x.substring(0, maxlength));
						} else {
							s.setCentroTrabajo(x);
						}
						
					});
					
				optStr = Optional.ofNullable(os.getAreaTrabajo());
					optStr.ifPresent((x) -> {
						
						int maxlength = 70;

						if (x.length() >= maxlength) {
							s.setAreaTrabajo(x.substring(0, maxlength));
						} else {
							s.setAreaTrabajo(x);
						}
						
					});
					
				optStr = Optional.ofNullable(os.getListaDistribucion());
					optStr.ifPresent((x) -> {
						s.setListaDistribucion(x.contains("SI"));
					});

				optStr = Optional.ofNullable(os.getLopd());
					optStr.ifPresent((x) -> {
						s.setLopd(x.contains("SI"));
					});

				optStr = Optional.ofNullable(os.getFacturaSocio());
					optStr.ifPresent((x) -> {
						s.setFactura(x.contains("SI"));
					});
				
				optStr = Optional.ofNullable(os.getActivo());
					optStr.ifPresent((x) -> {
						s.setBaja(x.contains("NO"));
					});
					
				
				optStr = Optional.ofNullable(os.getModalidad());
					optStr.ifPresentOrElse((x) -> {
						
						boolwp.isfound = false;
						
						if ((modsocl.size() > 0) && (x.length() > 0)) {
							
							for (ModalidadSocio it: modsocl) {
								
								Optional<String >optStr2 = Optional.ofNullable(it.getDescripcion());
									optStr2.ifPresent((y) -> {
										if (x.equals(y)) {
											s.setModalidad(it);
											boolwp.isfound = true;
										}
									});	
								
								if (boolwp.isfound) break;
							}
							
							if (!boolwp.isfound) s.setModalidad(mswp.def);
							
						} else {
								
							 s.setModalidad(mswp.def);
						}

					}, () -> {
						s.setModalidad(mswp.def);
					});

				optStr = Optional.ofNullable(os.getModoEnviod());
					optStr.ifPresentOrElse((x) -> {
						
						boolwp.isfound = false;
						
						if ((maccesol.size() > 0) && (x.length() > 0)) {
							
							for (ModoAcceso it: maccesol) {
								
								Optional<String >optStr2 = Optional.ofNullable(it.getDescripcion());
									optStr2.ifPresent((y) -> {
										if (x.equals(y)) {
											s.setModoAcceso(it);
											boolwp.isfound = true;
										}
									});	
								
								if (boolwp.isfound) break;
							}
							
							if (!boolwp.isfound) s.setModoAcceso(mawp.def);
							
						} else {
							
							s.setModoAcceso(mawp.def);
						}

					}, () -> {
						s.setModoAcceso(mawp.def);
					});
			    
				optStr = Optional.ofNullable(os.getModoPago());
					optStr.ifPresentOrElse((x) -> {
						
						boolwp.isfound = false;
						
						if ((maccesol.size() > 0) && (x.length() > 0)) {
							
							for (FormaPago it: fpagol) {
								
								Optional<String >optStr2 = Optional.ofNullable(it.getDescripcion());
									optStr2.ifPresent((y) -> {
										if (x.equals(y)) {
											s.setFormaPago(it);
											boolwp.isfound = true;
										}
									});
									
								if (boolwp.isfound) break;
							}
							
							if (!boolwp.isfound) s.setFormaPago(fpwp.def);
							
						} else {
							
							s.setFormaPago(fpwp.def);
						}

					}, () -> {
						s.setFormaPago(fpwp.def);
					});
			    
				optStr = Optional.ofNullable(os.getAgencia());
					optStr.ifPresent((x) -> {
						
						boolwp.isfound = false;
						
						if ((agencial.size() > 0) && (x.length() > 0)) {
							
							for (Agencia it: agencial) {
								
								Optional<String >optStr2 = Optional.ofNullable(it.getNombre());
									optStr2.ifPresent((y) -> {
										if (x.equals(y)) {
											s.setAgencia(it);
											boolwp.isfound = true;
										}
									});		
								
								if (boolwp.isfound) break;
							}
							
							if (!boolwp.isfound) {
								s.setAgencia(agwp.def);
							}
						}

					});

				optStr = Optional.ofNullable(os.getMotivoBaja());
					optStr.ifPresent((x) -> {
						
						boolwp.isfound = false;
						
						if ((mbajal.size() > 0) && (x.length() > 0)) {
							
							for (MotivoBaja it: mbajal) {
								
								Optional<String >optStr2 = Optional.ofNullable(it.getDescripcion());
									optStr2.ifPresent((y) -> {
										if (x.equals(y)) {
											s.setMotivoBaja(it);
											boolwp.isfound = true;
										}
									});		
								
								if (boolwp.isfound) break;
							}
							
							if (!boolwp.isfound) {
								s.setMotivoBaja(mbwp.def);
							}
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
					
				optStr = Optional.ofNullable(os.getCorreosZona());
					optStr.ifPresent((x) -> {
						
						boolwp.isfound = false;
						
						if ((zpostl.size() > 0) && (x.length() > 0)) {
							
							for (ZonaPostal it: zpostl) {
								
								Optional<String >optStr2 = Optional.ofNullable(it.getDescripcion());
									optStr2.ifPresent((y) -> {
										if (x.equals(y)) {
											s.setZonaPostal(it);
											boolwp.isfound = true;
										}
									});		
								
								if (boolwp.isfound) break;
							}
							
							if (!boolwp.isfound) {
								s.setZonaPostal(zpwp.def);
							}
						}

					});

				Optional<Date> optDate = Optional.ofNullable(os.getAlta());
					optDate.ifPresent((x) -> {
						s.setFechaAlta(x);
					});
					
				optDate = Optional.ofNullable(os.getBaja());
					optDate.ifPresent((x) -> {
						s.setFechaBaja(x);
					});
					
				s.setMarcador("#REV");
				
				socioRepo.save(s);
				
				logger.info("Saved socio " + conts + " " + s.getCodigoSocio() + " " + s.getNombre());
					
			}
		}
	}
}
