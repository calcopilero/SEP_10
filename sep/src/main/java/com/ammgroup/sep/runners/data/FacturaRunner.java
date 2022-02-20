package com.ammgroup.sep.runners.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.jreports.service.JReportsService;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.Descuento;
import com.ammgroup.sep.model.EstadoFactura;
import com.ammgroup.sep.model.Factura;
import com.ammgroup.sep.model.FormaPago;
import com.ammgroup.sep.model.ItemFactura;
import com.ammgroup.sep.model.ModalidadSocio;
import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.model.SerieFacturas;
import com.ammgroup.sep.model.Socio;
import com.ammgroup.sep.model.TipoIVA;
import com.ammgroup.sep.repository.EstadoFacturaRepository;
import com.ammgroup.sep.repository.FacturaRepository;
import com.ammgroup.sep.repository.FormaPagoRepository;
import com.ammgroup.sep.repository.SerieFacturasRepository;
import com.ammgroup.sep.repository.SocioRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SuppressWarnings(value = { "unused" })
//@Component
//@Order(14)
public class FacturaRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(FacturaRunner.class);
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired 
	private SocioRepository socioRepository;
	
	@Autowired 
	private SerieFacturasRepository sfactRepository;
	
	@Autowired 
	private EstadoFacturaRepository efactRepository;
	
	@Autowired 
	private FacturaRepository factRepository;
	
	@Autowired 
	private FormaPagoRepository fpagoRepository;
	
	@Autowired
	JReportsService jrserv;
	
	//@Autowired 
	//private ItemFacturaRepository itfactRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		//Delete all existing records
    	//factRepository.deleteAll();
    	
    	//Generate new facturas
    	generateFactura("07466575A", "18-10-2021", "15-10-2021", 4.21D);
    	generateFactura("51462855D", "19-10-2021", "16-10-2021", 3.75D);
    	generateFactura("51396825K", "21-10-2021", "20-10-2021", 5.00D);
    	
		logger.info("# of facturas: {}", factRepository.count());
		logger.info("------------------------");
		logger.info("All facturas unsorted:"); 
		List<Factura> facturas = factRepository.findAll(); 
		logger.info("{}", facturas);
		logger.info("------------------------");
		logger.info("All facturas sorted by numero in descending order");
		List<Factura> facturasSorted = factRepository.findAll(Sort.by(Sort.Direction.DESC, "numeroCompuesto"));
		logger.info("{}", facturasSorted); 
		logger.info("------------------------");
		
	}
	
	private void generateFactura(String socCifnif, String dEmis, String dFact, Double gEnv) {
		
    	var tfwrapper = new Object(){ String ttitular = ""; String tdireccion = ""; String tcomp = ""; };
    	var numwrapper = new Object(){ String numInic = ""; Integer num = 0; int year = 0; };
    	
    	Factura fact = new Factura();
    	ItemFactura itfact = new ItemFactura();
    	
    	//Obtain the socio who generates the factura
    	List<Socio> socios = socioRepository.findByCifnif(socCifnif);
    	//Obtain estado de factura to be set in facturas when created in an automatic way
    	List<EstadoFactura> efact = efactRepository.findByEstadoPorDefecto();
    	//Obtain the serie facturas used to generate facturas in an automatic way
    	List<SerieFacturas> sfact = sfactRepository.findByFacturacionAutomatica();
    	//Obtain the socio who generates the factura
    	List<FormaPago> fpago = fpagoRepository.findByDescripcion("Transferencia");
    	
    	//Check if the factura can be created
    	if ((socios.size() >= 1) && (efact.size() >= 1) && (sfact.size() == 1)) {
    		
    		Socio soc = socios.get(0);
    		
    		SerieFacturas sf = sfact.get(0);
    		
    		fact.setSocio(soc);

        	Optional<Agencia> ageOpt = Optional.ofNullable(soc.getAgencia());
        	
    			ageOpt.ifPresentOrElse((x) -> {
    				
    				fact.setAgencia(x);
    				
    				Optional<String> strOpt;
    				strOpt = Optional.ofNullable(x.getNombre());
    					strOpt.ifPresent((y) -> tfwrapper.ttitular = y);
    					
    				fact.setTitular(tfwrapper.ttitular);
    					
    				strOpt = Optional.ofNullable(x.getCifnif());
    					strOpt.ifPresentOrElse((y) -> fact.setCifnif(y), () -> fact.setCifnif(""));
       				strOpt = Optional.ofNullable(x.getDomicilio());
    					strOpt.ifPresent((y) -> tfwrapper.tdireccion += (y + "\n"));
            		strOpt = Optional.ofNullable(x.getCp());
            			strOpt.ifPresent((y) -> tfwrapper.tdireccion += y);
                	strOpt = Optional.ofNullable(x.getLocalidad());
                		strOpt.ifPresent((y) -> tfwrapper.tdireccion += (" " + y));
            		
                	Optional<Provincia> prvOpt = Optional.ofNullable(x.getProvincia());
                		prvOpt.ifPresent((y) -> { 
                			Optional<String> stOpt = Optional.ofNullable(y.getDescripcion());
                				stOpt.ifPresent((z) -> tfwrapper.tdireccion += (" (" + z + ")\n"));
                		});
                	Optional<Pais> paisOpt = Optional.ofNullable(x.getPais());
                		paisOpt.ifPresent((y) -> { 
                			Optional<String> stOpt = Optional.ofNullable(y.getDescripcion());
                				stOpt.ifPresent((z) -> tfwrapper.tdireccion += z);
            		});

                    fact.setDireccion(tfwrapper.tdireccion);
                    	
                	//Insert text in Texto Complementario (if its set at serie facturas level, wich is the priority level)
                	Optional<String> strPara = Optional.ofNullable(sf.getTextoPara());
    					strPara.ifPresentOrElse((y) -> {
    						
    						if (y.length() > 0) tfwrapper.tcomp = y + "\n";
    						
    					} , () -> {
   						
    	                	//Set the content of Texto Complementario (if its set at modalidad socio level)
    	            		Optional<ModalidadSocio> modOpt = Optional.ofNullable(soc.getModalidad());
    	        				modOpt.ifPresent((z) -> {
    	        				
    	                    	//Insert text in TextoPara (if its set at this level)
    	            			Optional<String> strParam = Optional.ofNullable(z.getTextoPara());
    	        					strParam.ifPresent((w) -> { 
    	        						
    	        						if (w.length() > 0) tfwrapper.tcomp = w + "\n";
    	        						
    	        					});
    	        			});
    					});

                	//Set the content of Texto Complementario with the data of Socio
    				strOpt = Optional.ofNullable(soc.getNombre());
    					strOpt.ifPresent((y) ->  tfwrapper.tcomp +=  y);
       				strOpt = Optional.ofNullable(soc.getApellidos());
    					strOpt.ifPresent((y) ->  tfwrapper.tcomp +=  " " + y + "\n");
        			strOpt = Optional.ofNullable(soc.getDomicilio());
    					strOpt.ifPresent((y) ->  tfwrapper.tcomp +=  y + "\n");
            		strOpt = Optional.ofNullable(soc.getCp());
        				strOpt.ifPresent((y) ->  tfwrapper.tcomp +=  y);
                	strOpt = Optional.ofNullable(soc.getLocalidad());
            			strOpt.ifPresent((y) ->  tfwrapper.tcomp += " " + y);
                	
            		prvOpt = Optional.ofNullable(soc.getProvincia());
                		prvOpt.ifPresent((y) -> { 
                			Optional<String> stOpt = Optional.ofNullable(y.getDescripcion());
                				stOpt.ifPresent((z) -> tfwrapper.tcomp += (" (" + z + ")\n"));
                		});
                	paisOpt = Optional.ofNullable(soc.getPais());
                		paisOpt.ifPresent((y) -> { 
                			Optional<String> stOpt = Optional.ofNullable(y.getDescripcion());
                				stOpt.ifPresent((z) -> tfwrapper.tcomp += z);
            		});
            			
                	fact.setTextoComplementario(tfwrapper.tcomp);
                	
                	//Set the Referencia
                	strOpt = Optional.ofNullable(soc.getReferencia());
            			strOpt.ifPresentOrElse((y) -> fact.setReferencia(y), () -> fact.setReferencia(""));

                //End of ifPresent and start of OrElse
    			}, () -> {
    				
    				Optional<String> strOpt;
    				strOpt = Optional.ofNullable(soc.getNombre());
    					strOpt.ifPresent((y) -> tfwrapper.ttitular = y);
    				strOpt = Optional.ofNullable(soc.getApellidos());
    					strOpt.ifPresent((y) -> tfwrapper.ttitular += (" " + y));
    					
    				fact.setTitular(tfwrapper.ttitular);

    				strOpt = Optional.ofNullable(soc.getCifnif());
    					strOpt.ifPresentOrElse((y) -> fact.setCifnif(y), () -> fact.setCifnif(""));
    					
        			strOpt = Optional.ofNullable(soc.getDomicilio());
    					strOpt.ifPresent((y) -> tfwrapper.tdireccion += (y + "\n"));
            		strOpt = Optional.ofNullable(soc.getCp());
            			strOpt.ifPresent((y) -> tfwrapper.tdireccion += y);
                	strOpt = Optional.ofNullable(soc.getLocalidad());
                		strOpt.ifPresent((y) -> tfwrapper.tdireccion += (" " + y));
            		
                	Optional<Provincia> prvOpt = Optional.ofNullable(soc.getProvincia());
                		prvOpt.ifPresent((y) -> { 
                			Optional<String> stOpt = Optional.ofNullable(y.getDescripcion());
                				stOpt.ifPresent((z) -> tfwrapper.tdireccion += (" (" + z + ")\n"));
                		});
                	Optional<Pais> paisOpt = Optional.ofNullable(soc.getPais());
                		paisOpt.ifPresent((y) -> { 
                			Optional<String> stOpt = Optional.ofNullable(y.getDescripcion());
                				stOpt.ifPresent((z) -> tfwrapper.tdireccion += z);
                		});
                	
                	fact.setDireccion(tfwrapper.tdireccion);
                	
                	fact.setTextoComplementario("");
                	fact.setReferencia("");
                		
    			}	//End of OrElse
    			
    		);
    		
			Optional<Descuento> descOpt = Optional.ofNullable(soc.getDescuento());
        		descOpt.ifPresent((x) -> fact.setDescuento(x));
    			
        	Optional<FormaPago> fpagOpt = Optional.ofNullable(soc.getFormaPago());
        		fpagOpt.ifPresent((x) -> {
        			
    				fact.setFormaPago(x);

        			Optional<String> strFp = Optional.ofNullable(x.getTextoFactura());
    					strFp.ifPresentOrElse((y) -> fact.setTextoFormaPago(y), () -> fact.setTextoFormaPago(""));
    			});
        	
        	fact.setEstadoFactura(efact.get(0));
        	
   			fact.setFechaEmision(mutils.getDateFromString(dEmis, mutils.DATE_FORMAT));
   			fact.setUltimaActualizacion(new Date());
   			
   			Date fFact = mutils.getDateFromString(dFact, mutils.DATE_FORMAT);
   			fact.setFechaFactura(fFact);
    			
    		fact.setFacturacionAutomatica(true);
    		
			//Set in factura data related with serie facturas
			fact.setSerie(sf);
			
			Optional<String> strSf = Optional.ofNullable(sf.getTextoInicioNumeracion());
				strSf.ifPresent((x) -> {
					numwrapper.numInic = x;
				});
				
			//Get the year of fecha factura
			numwrapper.year = mutils.getYearFromDate(fFact);
				
			Optional<Integer> numSf = Optional.ofNullable(factRepository.getMaximumFacturaNumberBySerie(sf, mutils.getDateFromString("01-01-" + numwrapper.year, mutils.DATE_FORMAT), mutils.getDateFromString("31-12-" + numwrapper.year, mutils.DATE_FORMAT)));
				numSf.ifPresentOrElse((x) -> {
					
					int proxNum = x.intValue() + 1;
					
					fact.setNumero(proxNum);
					numwrapper.num = proxNum;
				}, () -> {
					
					int proxNum = 1;
					
					fact.setNumero(proxNum);
					numwrapper.num = proxNum;
				});
				
			fact.setNumeroCompuesto(numwrapper.numInic + "-" + numwrapper.year + "-" + "0".repeat(3-(numwrapper.num).toString().length()) + numwrapper.num);
				
   			Optional<TipoIVA> tiSf = Optional.ofNullable(sf.getTipoIVA());
				tiSf.ifPresent((x) -> fact.setTipoIVA(x));
			
		    fact.setImpGastosEnvio(gEnv);
		    
   			Optional<List<FormaPago>> optFpag = Optional.ofNullable(fpago);
   				optFpag.ifPresent((x) -> {
				
					if (fpago.size() > 0) {
						fact.setFormaPago(fpago.get(0));
					}
   				});
		    
   			Optional<List<EstadoFactura>> optEfac = Optional.ofNullable(efact);
   				optEfac.ifPresent((x) -> {
					
   					if (efact.size() > 0) {
   						fact.setEstadoFactura(efact.get(0));
   					}
				});
		    
		    fact.setAnotaciones("Anotaciones:");
		    
		    fact.setExisteRectificativa(false);
		    fact.setTextoRectificativa("");
		    
	    	//Insert the items of the detail in Factura and save the item
    		Optional<ModalidadSocio> modOpt = Optional.ofNullable(soc.getModalidad());
			modOpt.ifPresent((x) -> {
				
				Optional<String> strConc = Optional.ofNullable(x.getConcepto());
					strConc.ifPresentOrElse((z) -> itfact.setConcepto(z), () -> itfact.setConcepto(""));
    			Optional<Double> douCuot = Optional.ofNullable(x.getCuota());
					douCuot.ifPresentOrElse((z) -> itfact.setImporte(z), () -> itfact.setImporte(0D));
			});
	    	itfact.setFactura(fact);
	    	
		    fact.getItemsFactura().add(itfact);
		    
		    fact.recalculate();
		    
	    	factRepository.save(fact);
	    	
    	}
	}
}
