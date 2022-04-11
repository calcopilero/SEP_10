package com.ammgroup.sep.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAOSoc;
import com.ammgroup.sep.jreports.config.enums.ReportFormat;
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
import com.ammgroup.sep.repository.AgenciaRepository;
import com.ammgroup.sep.repository.DescuentoRepository;
import com.ammgroup.sep.repository.EstadoFacturaRepository;
import com.ammgroup.sep.repository.FacturaRepository;
import com.ammgroup.sep.repository.FormaPagoRepository;
import com.ammgroup.sep.repository.SerieFacturasRepository;
import com.ammgroup.sep.repository.TipoIVARepository;
import com.ammgroup.sep.service.ModuloUtilidades;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

@Component
public class HfactController implements Initializable {
	
	@Autowired
	private ModuloUtilidades mutils;

	@Autowired
	DescuentoRepository descRepository;
	
	@Autowired
	TipoIVARepository tivaRepository;
	
	@Autowired
	FormaPagoRepository fpagoRepository;
	
	@Autowired 
	private SerieFacturasRepository sfactRepository;
	
	@Autowired 
	private EstadoFacturaRepository efactRepository;
	
	@Autowired 
	private FacturaRepository factRepository;
	
	@Autowired
	AgenciaRepository agenciaRepository;
	
	@Autowired
	private CrudDAOSoc<Socio> socrud;
	
	@Autowired
	JReportsService jrserv;
	
	@FXML
	private Label lbcsoc;
	
	@FXML
	private Label lbnomapell;
	
	@FXML
	private DatePicker dpffact;
	
	@FXML
	private CheckBox chffirm;
	
	@FXML
	private CheckBox chfauto;
	
	@FXML
	private Label lbcbsfact;
	
	@FXML
	private ComboBox<SerieFacturas> cbsfact;
	
	@FXML
	private Label lbagencia_;
	
    @FXML
    private ComboBox<Agencia> cbagencia;
    
	@FXML
	private Label lbrefer_;
	
    @FXML
    private TextField txrefer;
	
	@FXML
	private Label lbtaconcep;
	
	@FXML
	private TextArea taconcep;
	
	@FXML
	private Label lbtximpitems;
	
	@FXML
	private TextField tximpitems;
	
	@FXML
	private Label lbcbdesc;
	
	@FXML
	private ComboBox<Descuento> cbdesc;
	
	@FXML
	private Label lbcbtiva;
	
	@FXML
	private ComboBox<TipoIVA> cbtiva;
	
	@FXML
	private Label lbtxgenv;
	
	@FXML
	private TextField txgenv;
	
	@FXML
	private Label lbcbfpago;
	
	@FXML
	private ComboBox<FormaPago> cbfpago;
	
	@FXML
	private Label lbmarc_;
	
    @FXML
    private TextField txmarc;
    
	@FXML
	private Label lbdafact_;
	
	@FXML
	private TextArea txardafact;
	
    @FXML
    private Label lbmsg1;

    @FXML
    private Label lbmsg2;
    
    @FXML
    private Button bclose;

    @FXML
    private Button bexec;
    
	@FXML
	private CheckBox chvscreen;
	
    @FXML
    void chfautoOnAction(ActionEvent event) {
    	
    	//Hide show controls for manual
		lbcbsfact.setVisible(!chfauto.isSelected());
		cbsfact.setVisible(!chfauto.isSelected());
		lbagencia_.setVisible(!chfauto.isSelected());
		cbagencia.setVisible(!chfauto.isSelected());
		lbrefer_.setVisible(!chfauto.isSelected());
		txrefer.setVisible(!chfauto.isSelected());
		lbtaconcep.setVisible(!chfauto.isSelected());
		taconcep.setVisible(!chfauto.isSelected());
		lbtximpitems.setVisible(!chfauto.isSelected());
		tximpitems.setVisible(!chfauto.isSelected());
		lbcbdesc.setVisible(!chfauto.isSelected());
		cbdesc.setVisible(!chfauto.isSelected());
		lbcbtiva.setVisible(!chfauto.isSelected());
		cbtiva.setVisible(!chfauto.isSelected());
		lbtxgenv.setVisible(!chfauto.isSelected());
		txgenv.setVisible(!chfauto.isSelected());
		lbcbfpago.setVisible(!chfauto.isSelected());
		cbfpago.setVisible(!chfauto.isSelected());
		lbmarc_.setVisible(!chfauto.isSelected());
		txmarc.setVisible(!chfauto.isSelected());
		lbdafact_.setVisible(!chfauto.isSelected());
		txardafact.setVisible(!chfauto.isSelected());
    		
    }
	
	@FXML
    void bexecOnAction(ActionEvent event) {
		
    	var fdatawrapper = new Object(){ Date ffact = new Date(); SerieFacturas sfact = null; Boolean ffirmada = null; Agencia age = null; String refer = ""; String concep = ""; Double impitems = 0D; Descuento desc = null; TipoIVA tiva = null; Double genv = 0D; FormaPago fpago = null; String marc = ""; String dafact = ""; int errors = 0; String errorstext = ""; };

    	Optional<LocalDate> dtOpt = Optional.ofNullable(dpffact.getValue());
			dtOpt.ifPresentOrElse((y) -> {
				fdatawrapper.ffact = Date.from(y.atStartOfDay(mutils.getDefaultZoneId()).toInstant());
			}, () -> {
				fdatawrapper.errorstext += "Debe especificar una fecha válida. ";
				fdatawrapper.errors++;
			});
			
		long cont = efactRepository.countEstadosPorDefecto();
		if (cont == 0) {
			fdatawrapper.errorstext += "No hay ningún estado por defecto para las facturas. ";
			fdatawrapper.errors++;
		}

		fdatawrapper.ffirmada = chffirm.isSelected();
		
		//In automatic mode only ffact and a valid serie facturas is mandatory
		if (chfauto.isSelected()) {
			
			cont = sfactRepository.countExistingSeriesAutomaticas();
			if (cont == 0) {
				fdatawrapper.errorstext += "No hay ninguna serie para facturación automática. ";
				fdatawrapper.errors++;
			}
			
		} else {
			
			Optional<SerieFacturas> sfOpt = Optional.ofNullable(cbsfact.getValue());
				sfOpt.ifPresentOrElse((y) -> {
					fdatawrapper.sfact = y;
				}, () -> {
					fdatawrapper.errorstext += "Debe especificar una serie de facturas. ";
					fdatawrapper.errors++;
				});
				
			Optional<Agencia> agOpt = Optional.ofNullable(cbagencia.getValue());
				agOpt.ifPresent((y) -> {
					fdatawrapper.age = y;
				});
				
			Optional<String> refOpt = Optional.ofNullable(txrefer.getText());
				refOpt.ifPresent((y) -> {
					fdatawrapper.refer = y;
				});
				
			Optional<String> concOpt = Optional.ofNullable(taconcep.getText());
				concOpt.ifPresentOrElse((y) -> {
					fdatawrapper.concep = y;
				}, () -> {
					fdatawrapper.errorstext += "Debe especificar un concepto. ";
					fdatawrapper.errors++;
				});
				
			Optional<Double> impOpt = Optional.ofNullable(Double.parseDouble(obtainText(tximpitems)));
				impOpt.ifPresentOrElse((y) -> {
					fdatawrapper.impitems = y;
				}, () -> {
					fdatawrapper.errorstext += "Debe especificar un importe. ";
					fdatawrapper.errors++;
				});

			Optional<Descuento> descOpt = Optional.ofNullable(cbdesc.getValue());
				descOpt.ifPresent((y) -> {
					fdatawrapper.desc = y;
				});
				
			Optional<TipoIVA> tivaOpt = Optional.ofNullable(cbtiva.getValue());
				tivaOpt.ifPresentOrElse((y) -> {
					fdatawrapper.tiva = y;
				}, () -> {
					fdatawrapper.errorstext += "Debe especificar un tipo de IVA. ";
					fdatawrapper.errors++;
				});
			
			Optional<Double> genOpt = Optional.ofNullable(Double.parseDouble(obtainText(txgenv)));
				genOpt.ifPresent((y) -> {
					fdatawrapper.genv = y;
				});
				
			Optional<FormaPago> fpagOpt = Optional.ofNullable(cbfpago.getValue());
				fpagOpt.ifPresentOrElse((y) -> {
					fdatawrapper.fpago = y;
				}, () -> {
					fdatawrapper.errorstext += "Debe especificar una forma de pago. ";
					fdatawrapper.errors++;
				});
				
			Optional<String> marcOpt = Optional.ofNullable(txmarc.getText());
				marcOpt.ifPresent((y) -> {
					fdatawrapper.marc = y;
				});
				
			Optional<String> dafactOpt = Optional.ofNullable(txardafact.getText());
				dafactOpt.ifPresent((y) -> {
					fdatawrapper.dafact = y;
				});
		}
			
		if (fdatawrapper.errors > 0) {
			
			lbmsg1.setText(fdatawrapper.errorstext);
			
		} else {
			
			if (chfauto.isSelected()) {
				
				//In automatic only ffact is needed
				generateFactura(fdatawrapper.ffact, fdatawrapper.ffirmada, null, null, null, null, 
						null, null, null, 
						null, null, null, null);
			} else {
				
				generateFactura(fdatawrapper.ffact, fdatawrapper.ffirmada, fdatawrapper.sfact, fdatawrapper.age, fdatawrapper.refer, fdatawrapper.concep, 
						fdatawrapper.impitems, fdatawrapper.desc, fdatawrapper.tiva, 
						fdatawrapper.genv, fdatawrapper.fpago, fdatawrapper.marc, fdatawrapper.dafact);
			}
			
			closeForm();
		}
	}
	
    @FXML
    void bcloseOnAction(ActionEvent event) {

    	closeForm();

    }
    
    private String obtainText(TextField tx) {
    	
    	//To check null values we use optional and to avoid the block scope of variables we use a wrapper
    	var strwrapper = new Object(){ String str = ""; };
    	
		Optional<String> strOpt = Optional.ofNullable(tx.getText());
    	strOpt.ifPresent((x) -> {
    		strwrapper.str = tx.getText();
    	});
    		
    	return strwrapper.str;
    }
    
	private void closeForm() {
		
		// get a handle to the stage
		Stage stage = (Stage) bclose.getScene().getWindow();
		// do what you have to do
		stage.close();
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//Populate the combos
		cbsfact.setItems(FXCollections.observableList(sfactRepository.findByNotRectificativas()));
		cbsfact.getItems().add(null);
		cbagencia.setItems(FXCollections.observableList(agenciaRepository.findAll()));
		cbagencia.getItems().add(null);
		cbdesc.setItems(FXCollections.observableList(descRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbdesc.getItems().add(null);
		cbtiva.setItems(FXCollections.observableList(tivaRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbtiva.getItems().add(null);
		cbfpago.setItems(FXCollections.observableList(fpagoRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbfpago.getItems().add(null);
		
	    //Set the format of decimal fields
	    tximpitems.setTextFormatter(mutils.getTextFormatterForDecimal());
	    txgenv.setTextFormatter(mutils.getTextFormatterForDecimal());
	    
	    //Setting the maximum number of characters of TextField
	    tximpitems.addEventFilter(KeyEvent.KEY_TYPED, maxLength(9));
	    txgenv.addEventFilter(KeyEvent.KEY_TYPED, maxLength(9));
	    
	    //Setting the maximum number of characters of TextArea
	    taconcep.setTextFormatter(new TextFormatter<String>(change -> 
    		change.getControlNewText().length() <= 250 ? change : null));
	    txardafact.setTextFormatter(new TextFormatter<String>(change -> 
	    	change.getControlNewText().length() <= 200 ? change : null)); 
	    
		Optional<Socio> socOpt = Optional.ofNullable(socrud.getDao());
			socOpt.ifPresent((x) -> {
				
				Optional<Integer> optInt = Optional.ofNullable(x.getCodigoSocio());
					optInt.ifPresentOrElse((z) -> {
						lbcsoc.setText(String.format("%d", z));
					}, () -> {
						lbcsoc.setText("0");
					});
				
				
		    	//To check null values we use optional and to avoid the block scope of variables we use a wrapper
		    	var strwrapper = new Object(){ String str = ""; };

		    	Optional<String> optStr = Optional.ofNullable(x.getNombre());
					optStr.ifPresent((y) -> strwrapper.str = y.strip());
		    	optStr = Optional.ofNullable(x.getApellidos());
					optStr.ifPresent((y) -> strwrapper.str += " " + y.strip());
					
				lbnomapell.setText(strwrapper.str);

			});

	}
	
	private EventHandler<KeyEvent> maxLength(final Integer i) {
        return new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {

                TextField tx = (TextField) arg0.getSource();
                
            	Optional<String> strOpt = Optional.ofNullable(tx.getText());
            	strOpt.ifPresent((x) -> {
            		if (tx.getText().length() >= i) arg0.consume();
                });

            }

        };

    }
	
	private void generateFactura(Date pffact, boolean pffirm, SerieFacturas psfact, Agencia page, String pref, String pconcep, Double pimpitems, Descuento pdesc, TipoIVA ptiva, Double pgenv, FormaPago pfpago, String pmarc, String dafact) {
		
    	var tfwrapper = new Object(){ String ttitular = ""; String tcifnif = ""; String tdireccion = ""; String tcomp = ""; };
    	var numwrapper = new Object(){ String numInic = ""; Integer num = 0; int year = 0; };
    	var fdatawrapper = new Object(){ Date ffact = new Date(); SerieFacturas sfact = null; Agencia age = null; String refer= ""; String concep = ""; 
    		Double impitems = 0D; Descuento desc = null; TipoIVA tiva = null; Double genv = 0D; FormaPago fpago = null; String marc = ""; };
    	
    	Factura fact = new Factura();
    	ItemFactura itfact = new ItemFactura();
    	
    	//Obtain estado de factura to be set in facturas when created
    	List<EstadoFactura> efactl = efactRepository.findByEstadoPorDefecto();
    	//There should be only one element in the list
    	EstadoFactura efact = efactl.get(0);
    	
		Optional<Socio> socOpt = Optional.ofNullable(socrud.getDao());
			socOpt.ifPresent((x) -> {
			
        	fact.setSocio(x);
        	
			Optional<Date> dtOpt = Optional.ofNullable(pffact);
				dtOpt.ifPresentOrElse((y) -> {
					fdatawrapper.ffact = pffact;
				}, () -> {
					fdatawrapper.ffact = Date.from(dpffact.getValue().atStartOfDay(mutils.getDefaultZoneId()).toInstant());
				});
				
   			fact.setFechaFactura(fdatawrapper.ffact);

			Optional<SerieFacturas> sfOpt = Optional.ofNullable(psfact);
				sfOpt.ifPresentOrElse((y) -> {
					fdatawrapper.sfact = y;
				}, () -> {

					if (chfauto.isSelected()) {
						
			        	//Obtain the serie facturas used to generate facturas in an automatic way
			        	List<SerieFacturas> sfact = sfactRepository.findByFacturacionAutomatica();
			    		//There should be only one element in the list	        	
			        	fdatawrapper.sfact = sfact.get(0);
					}
						
				});
			
	        fact.setSerie(fdatawrapper.sfact);
	        
			Optional<String> strSf = Optional.ofNullable(fdatawrapper.sfact.getTextoInicioNumeracion());
				strSf.ifPresent((y) -> {
					numwrapper.numInic = y;
				});
			
			//Get the year of fecha factura
			numwrapper.year = mutils.getYearFromDate(fdatawrapper.ffact);
				
			Optional<Integer> numSf = Optional.ofNullable(factRepository.getMaximumFacturaNumberBySerie(fdatawrapper.sfact, mutils.getDateFromString("01-01-" + numwrapper.year, mutils.DATE_FORMAT), mutils.getDateFromString("31-12-" + numwrapper.year, mutils.DATE_FORMAT)));
				numSf.ifPresentOrElse((y) -> {
					numwrapper.num = y.intValue() + 1;
				}, () -> {
					numwrapper.num = 1;
				});
				
			fact.setNumero(numwrapper.num);
			fact.setNumeroCompuesto(numwrapper.numInic + "-" + (numwrapper.year - 2000) + "-" + "0".repeat(3-(numwrapper.num).toString().length()) + numwrapper.num);

			//In automatic mode only ffact is mandatory
			if (!chfauto.isSelected()) {
				
				Optional<Agencia> agOpt = Optional.ofNullable(page);
					agOpt.ifPresent((y) -> {
						fdatawrapper.age = y;
					});
				
				Optional<String> optRef = Optional.ofNullable(pref);
					optRef.ifPresent((y) -> {
						fdatawrapper.refer = y;
					});

			} else {
				
	        	Optional<Agencia> optAge = Optional.ofNullable(x.getAgencia());
		        	optAge.ifPresent((y) -> {
	        			
	        			fdatawrapper.age = y;
	                	Optional<String> optRef = Optional.ofNullable(x.getReferencia());
	            			optRef.ifPresent((z) -> fdatawrapper.refer = z);
	
	        		});
			}
			
			fact.setAgencia(fdatawrapper.age);
			fact.setReferencia(fdatawrapper.refer);
			
        	Optional<Agencia> ageOpt = Optional.ofNullable(fdatawrapper.age);
    			ageOpt.ifPresentOrElse((y) -> {
    				
    				Optional<String> strOpt = Optional.ofNullable(y.getNombre());
						strOpt.ifPresent((z) -> tfwrapper.ttitular = z);

    				strOpt = Optional.ofNullable(y.getCifnif());
						strOpt.ifPresent((z) -> tfwrapper.tcifnif = z);
						
	    			strOpt = Optional.ofNullable(y.getDomicilio());
    					strOpt.ifPresent((z) -> tfwrapper.tdireccion = (z + "\n"));
            		strOpt = Optional.ofNullable(y.getCp());
            			strOpt.ifPresent((z) -> tfwrapper.tdireccion += z);
                	strOpt = Optional.ofNullable(y.getLocalidad());
                		strOpt.ifPresent((z) -> tfwrapper.tdireccion += (" " + z));
            		
                	Optional<Provincia> prvOpt = Optional.ofNullable(y.getProvincia());
                		prvOpt.ifPresent((z) -> { 
                			Optional<String> stOpt = Optional.ofNullable(z.getDescripcion());
                				stOpt.ifPresent((w) -> tfwrapper.tdireccion += (" (" + w + ")\n"));
                		});
                		
                	Optional<Pais> paisOpt = Optional.ofNullable(y.getPais());
                		paisOpt.ifPresent((z) -> { 
                			Optional<String> stOpt = Optional.ofNullable(z.getDescripcion());
                				stOpt.ifPresent((w) -> tfwrapper.tdireccion += w);
                		});
                		
                	//Insert text in Texto Complementario (if its set at serie facturas level, wich is the priority level)
                	Optional<String> strPara = Optional.ofNullable(fdatawrapper.sfact.getTextoPara());
    					strPara.ifPresentOrElse((z) -> {
    						
    						if (z.length() > 0) tfwrapper.tcomp = z + "\n";
    						
    					} , () -> {
   						
    	                	//Set the content of Texto Complementario (if its set at modalidad socio level)
    	            		Optional<ModalidadSocio> modOpt = Optional.ofNullable(x.getModalidad());
    	        				modOpt.ifPresent((w) -> {
    	        				
	    	                    	//Insert text in TextoPara (if its set at this level)
	    	            			Optional<String> strParam = Optional.ofNullable(w.getTextoPara());
	    	        					strParam.ifPresent((v) -> { 
	    	        						
	    	        						if (v.length() > 0) tfwrapper.tcomp = v + "\n";
	    	        						
	    	        					});
    	        				});
    					});

                	//Set the content of Texto Complementario with the data of Socio
    				strOpt = Optional.ofNullable(x.getNombre());
    					strOpt.ifPresent((z) ->  tfwrapper.tcomp +=  z);
       				strOpt = Optional.ofNullable(x.getApellidos());
    					strOpt.ifPresent((z) ->  tfwrapper.tcomp +=  " " + z + "\n");
        			strOpt = Optional.ofNullable(x.getDomicilio());
    					strOpt.ifPresent((z) ->  tfwrapper.tcomp +=  z + "\n");
            		strOpt = Optional.ofNullable(x.getCp());
        				strOpt.ifPresent((z) ->  tfwrapper.tcomp +=  z);
                	strOpt = Optional.ofNullable(x.getLocalidad());
            			strOpt.ifPresent((z) ->  tfwrapper.tcomp += " " + z);
                	
            		prvOpt = Optional.ofNullable(x.getProvincia());
                		prvOpt.ifPresent((z) -> { 
                			Optional<String> stOpt = Optional.ofNullable(z.getDescripcion());
                				stOpt.ifPresent((w) -> tfwrapper.tcomp += (" (" + w + ")\n"));
                		});
                		
                	paisOpt = Optional.ofNullable(x.getPais());
                		paisOpt.ifPresent((z) -> { 
                			Optional<String> stOpt = Optional.ofNullable(z.getDescripcion());
                				stOpt.ifPresent((w) -> tfwrapper.tcomp += w);
                		});
    				
    			}, () -> {
    				
					Optional<String> strOpt = Optional.ofNullable(x.getNombre());
						strOpt.ifPresent((y) -> tfwrapper.ttitular = y);
					strOpt = Optional.ofNullable(x.getApellidos());
						strOpt.ifPresent((y) -> tfwrapper.ttitular += (" " + y));
					//Check if Datos adicionales para factura have been passed as parameter
					//wich only happend in facturacion manual 
					strOpt = Optional.ofNullable(dafact);
						strOpt.ifPresentOrElse((y) -> {
									tfwrapper.ttitular += ("\n" + y);
								}, () -> {
									Optional<String> dafOpt = Optional.ofNullable(x.getDatosAdicionalesFactura());
										dafOpt.ifPresent((z) -> tfwrapper.ttitular += ("\n" + z));
								});

					strOpt = Optional.ofNullable(x.getCifnif());
						strOpt.ifPresent((y) -> tfwrapper.tcifnif = y);
						
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
    			});
    			
			fact.setTitular(tfwrapper.ttitular);
			fact.setCifnif(tfwrapper.tcifnif);
        	fact.setDireccion(tfwrapper.tdireccion);
        	fact.setTextoComplementario(tfwrapper.tcomp);
			
        	Optional<String> strPcon = Optional.ofNullable(pconcep);
        		strPcon.ifPresentOrElse((y) -> {
        			
        			fdatawrapper.concep = pconcep;
        			
        		}, () -> {
        			
        			if (chfauto.isSelected()) {
        				
            	    	//Insert the items of the detail in Factura and save the item
                		Optional<ModalidadSocio> modOpt = Optional.ofNullable(x.getModalidad());
                			modOpt.ifPresent((y) -> {
                				
                				Optional<String> strConc = Optional.ofNullable(y.getConcepto());
                					strConc.ifPresent((z) -> fdatawrapper.concep = z);
                			});
        			}
        		});

    		Optional<Double> optIit = Optional.ofNullable(pimpitems);
    			optIit.ifPresentOrElse((y) -> {
        			
        			fdatawrapper.impitems = pimpitems;
        			
        		}, () -> {

        			if (chfauto.isSelected()) {
        				
            	    	//Insert the items of the detail in Factura and save the item
                		Optional<ModalidadSocio> modOpt = Optional.ofNullable(x.getModalidad());
                			modOpt.ifPresent((y) -> {
                				
                    			Optional<Double> douCuot = Optional.ofNullable(y.getCuota());
                					douCuot.ifPresent((z) -> fdatawrapper.impitems = z);
                			});
        			}
        		});
        	
    		itfact.setConcepto(fdatawrapper.concep);
    		itfact.setImporte(fdatawrapper.impitems);
	    	itfact.setFactura(fact);
	    	
	    	//Save the items list to the factura
	    	fact.getItemsFactura().add(itfact);
	    	
	    	Optional<Descuento> opPdesc = Optional.ofNullable(pdesc);
	    		opPdesc.ifPresentOrElse((y) -> { fdatawrapper.desc = pdesc; }
	    		, () -> {

	    			//In manual we can pass a null Descuento to indicate that there's no descount
	    			if (chfauto.isSelected()) {
		    			Optional<Descuento> opDesc = Optional.ofNullable(x.getDescuento());
		    				opDesc.ifPresent((y) -> fdatawrapper.desc = y );
	    			}
	        			
	    		});
	    	
	    	fact.setDescuento(fdatawrapper.desc);

	    	Optional<TipoIVA> opPtiva = Optional.ofNullable(ptiva);
	    		opPtiva.ifPresentOrElse((y) -> { fdatawrapper.tiva = ptiva; }
	    		, () -> {

	    			if (chfauto.isSelected()) {
	    				
		    			Optional<TipoIVA> opTiva = Optional.ofNullable(fdatawrapper.sfact.getTipoIVA());
		    				opTiva.ifPresent((y) -> fdatawrapper.tiva = y);
	    			}
	
	    		});
    	
    		fact.setTipoIVA(fdatawrapper.tiva);
    		
    		Optional<Double> opGenv = Optional.ofNullable(pgenv);
	    		opGenv.ifPresentOrElse((y) -> fdatawrapper.genv = pgenv,
	    		() -> fdatawrapper.genv =  Double.parseDouble(obtainText(txgenv)));
					
	    	//In automatic generation Gastos envio = 0
	    	fact.setImpGastosEnvio(fdatawrapper.genv);
			
	    	Optional<FormaPago> opPfpag = Optional.ofNullable(pfpago);
    			opPfpag.ifPresentOrElse((y) -> { fdatawrapper.fpago = pfpago; },
    			() -> {
	    			if (chfauto.isSelected()) {

	    				Optional<FormaPago> fpagOpt = Optional.ofNullable(x.getFormaPago());
			        		fpagOpt.ifPresent((y) -> fdatawrapper.fpago = y);
	    				
	    			}
    			});

			fact.setFormaPago(fdatawrapper.fpago);

			Optional<String> strTfp = Optional.ofNullable(fdatawrapper.fpago.getTextoFactura());
				strTfp.ifPresentOrElse((z) -> fact.setTextoFormaPago(z), () -> fact.setTextoFormaPago(""));
			
			Optional<String> optMarc = Optional.ofNullable(pmarc);
				optMarc.ifPresent((y) -> {
					fdatawrapper.marc = y;
				});
				
			fact.setMarcador(fdatawrapper.marc);
				
        	fact.setEstadoFactura(efact);

   			fact.setFechaEmision(new Date());
   			fact.setUltimaActualizacion(new Date());
   			
   			fact.setFacturaFirmada(x.isFirmarFactura());
   			
		    fact.setAnotaciones("");
		    
		    fact.setExisteRectificativa(false);
		    fact.setTextoRectificativa("");
   			
    		fact.setFacturacionAutomatica(chfauto.isSelected());
    		fact.setFacturaFirmada(pffirm);
    		
		    fact.recalculate();
		    
		    //Finally save Factura
	    	factRepository.save(fact);
	    	
			try {
				jrserv.generateFacturaReport("factura.jrxml", fact, ReportFormat.PDF, fact.getNumeroCompuesto() + ".pdf", fact.getNumeroCompuesto());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//Check if the factura should be shown in screen
	    	if (chvscreen.isSelected()) {
	    		
				try {
					jrserv.generateFacturaReport("factura.jrxml", fact, ReportFormat.PREVIEW, null, "Factura " + fact.getNumeroCompuesto());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
