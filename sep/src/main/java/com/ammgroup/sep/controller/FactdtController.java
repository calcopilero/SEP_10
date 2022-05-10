package com.ammgroup.sep.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAOFact;
import com.ammgroup.sep.jreports.config.enums.ReportFormat;
import com.ammgroup.sep.jreports.service.JReportsService;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.Descuento;
import com.ammgroup.sep.model.EstadoFactura;
import com.ammgroup.sep.model.Factura;
import com.ammgroup.sep.model.FormaPago;
import com.ammgroup.sep.model.ItemFactura;
import com.ammgroup.sep.model.SerieFacturas;
import com.ammgroup.sep.model.TipoIVA;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

@Component
public class FactdtController implements Initializable {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAOFact<Factura> factcrud;
	
	@Autowired
	FacturaRepository factRepository;
	
	@Autowired
	DescuentoRepository descRepository;
	
	@Autowired
	TipoIVARepository tivaRepository;
	
	@Autowired
	FormaPagoRepository fpagoRepository;

	@Autowired
	EstadoFacturaRepository efactRepository;
	
	@Autowired 
	private SerieFacturasRepository sfactRepository;
	
	@Autowired
	JReportsService jrserv;
	
	@FXML
	private Label lbnfact;
	
	@FXML
	private Label lbserie;
	
	@FXML
	private Label lbfrect;
	
	@FXML
	private Label lbesrect;
	
	@FXML
	private DatePicker dpffact;
	
	@FXML
	private CheckBox chffirm;
	
	@FXML
	private Label lbcsoc;
	
	@FXML
	private Label lbagenc;
	
	@FXML
	private TextField txrefer;
	
	@FXML
	private Label lbtitlen;
	int maxtitchars = 350;
	
	@FXML
	private TextArea tatitular;
	
	@FXML
	private Label lbdirlen;
	int maxdirchars = 350;
	
	@FXML
	private TextArea tadirec;
	
	@FXML
	private TextField txcifnif;
	
	@FXML
	private TextField txmarc;
	
	@FXML
	private Label lbtfpaglen;
	int maxtfpagchars = 150;
	
	@FXML
	private TextField txfpago;
	
	@FXML
	private Label lbtcomplen;
	int maxtcompchars = 350;
	
	@FXML
	private TextArea tatcomp;
	
	@FXML
	private Label lbconceplen;
	int maxconcepchars = 400;

	@FXML
	private TextArea taconcep;
	
	@FXML
	private TextField tximpitems;

	@FXML
	private ComboBox<Descuento> cbdesc;
	
	@FXML
	private Label lbporcdesc;
	
	@FXML
	private Label lbdesc;
	
	@FXML
	private Label lbbimp;
	
	@FXML
	private ComboBox<TipoIVA> cbtiva;
	
	@FXML
	private Label lbporciva;
	
	@FXML
	private Label lbiva;
	
	@FXML
	private TextField txgenv;
	
	@FXML
	private Label lbtfact;
	
	@FXML
	private ComboBox<FormaPago> cbfpago;
	
	@FXML
	private ComboBox<EstadoFactura> cbefact;
	
	@FXML
	private Label lbanotlen;
	int maxanotchars = 500;
	
	@FXML
	private TextArea taanot;
	
	@FXML
	private Label lbmsg1;
	
	@FXML
	private Label lbmsg2;
	
	@FXML
	private Label lbfcrea;
	
	@FXML
	private Label lbultact;

	@FXML
	private Button bexec;

	@FXML
	private Button brecalc;

	@FXML
	private Button bprint;

	@FXML
	private Button bpdf;
	
	@FXML
	private Button bfrect;

	@FXML
	private Button bclose;    

	@FXML
    void cbdescOnAction(ActionEvent event) {
		
		Optional<Factura> factOpt = Optional.ofNullable(factcrud.getDao());
			factOpt.ifPresent((x) -> {
				
				Optional<Descuento> optDesc = Optional.ofNullable(cbdesc.getValue());
					optDesc.ifPresentOrElse((y) -> {
						x.setDescuento(y);
					}, () -> {
						x.setDescuento(null);
					});
				
				recalculateFromControls();
			});
	}
	
	@FXML
    void cbtivaOnAction(ActionEvent event) {
		
		Optional<Factura> factOpt = Optional.ofNullable(factcrud.getDao());
			factOpt.ifPresent((x) -> {
				
				Optional<TipoIVA> optIva = Optional.ofNullable(cbtiva.getValue());
					optIva.ifPresentOrElse((y) -> {
						x.setTipoIVA(y);
					}, () -> {
						x.setTipoIVA(null);
					});
				
				
				recalculateFromControls();
			});
	}
	
	@FXML
    void bexecOnAction(ActionEvent event) {
		
		var cfwrapper = new Object() { boolean  cform = true; };
		
		Optional<Factura> factOpt = Optional.ofNullable(factcrud.getDao());

		//The ADD option is not valid for Facturas
		switch (factcrud.getAction()) {
        case EDIT:
        	
			factOpt.ifPresent((x) -> {

				x.setUltimaActualizacion(new Date());
        		saveDataToFactura(x);
        		
        		factRepository.save(x);
			});
        	
            break;
        
        case DELETE:
        
			factOpt.ifPresent((x) -> {
				
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Confirma el BORRADO de la factura?", ButtonType.YES, ButtonType.NO);

		        // clicking X also means no
		        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
		        
		        if (ButtonType.YES.equals(result)) {

		        	factRepository.delete(x);
		        
		        } else {
		        	
		        	cfwrapper.cform = false;
		        }
				
			});
        	
            break;
		
        case VIEW:
		
        	break;
		
        default:
		
        	break;
	    }
	    
    	if (cfwrapper.cform) closeForm();

	}
	
	@FXML
    void brecalcOnAction(ActionEvent event) {
		
		recalculateFromControls();
	}
	
	@FXML
    void bprintOnAction(ActionEvent event) {
		
		Optional<Factura> facOpt = Optional.ofNullable(factcrud.getDao());
    	facOpt.ifPresentOrElse((x) -> {
			
			try {
				jrserv.generateFacturaReport("factura.jrxml", x, ReportFormat.PREVIEW, null, "Factura " + x.getNumeroCompuesto());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}, () -> {
			
			lbmsg1.setText("No existe una factura válida.");
		});
	}
	
	@FXML
    void bpdfOnAction(ActionEvent event) {
	
		Optional<Factura> facOpt = Optional.ofNullable(factcrud.getDao());
    	facOpt.ifPresentOrElse((x) -> {
			
			try {
				jrserv.generateFacturaReport("factura.jrxml", x, ReportFormat.PDF, x.getNumeroCompuesto() + ".pdf", null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}, () -> {
			
			lbmsg1.setText("No existe una factura válida.");
		});
	}
	
	@FXML
    void bfrectOnAction(ActionEvent event) {
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea realizar una factura RECTIFICATIVA?", ButtonType.YES, ButtonType.NO);

        // clicking X also means no
        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
        
        if (ButtonType.YES.equals(result)) {
        	
        	long cont = sfactRepository.countExistingSeriesRectificativas();
        	if (cont > 0) {
        	
	        	//Obtain the serie facturas marked as Rectificativas
	        	List<SerieFacturas> sfact = sfactRepository.findByRectificativas();
	        	
	        	//Obtain estado de factura to be set in facturas when created
	        	List<EstadoFactura> efactl = efactRepository.findByEstadoPorDefecto();
	        	
	        	//Check if there's a serie facturas for rectificativas
	        	if ((sfact.size() > 0) && (efactl.size() > 0)) {
	        	
		        	Factura frect = new Factura();
		        	
		        	var numwrapper = new Object(){ String numInic = ""; Integer num = 0; int year = 0; };
		        	
		        	Optional<Factura> optFact = Optional.ofNullable(factcrud.getDao());
		        		optFact.ifPresent((x) -> {
		        			
		        			frect.setFechaFactura(new Date());
		        			frect.setAgencia(x.getAgencia());
		        			frect.setReferencia(x.getReferencia());
		        			frect.setSocio(x.getSocio());
		        			frect.setCifnif(x.getCifnif());
		        			frect.setTitular(x.getTitular());
		        			frect.setDireccion(x.getDireccion());
		        			frect.setDescuento(x.getDescuento());
		        			frect.setTipoIVA(x.getTipoIVA());
		        			frect.setFormaPago(x.getFormaPago());
		        			frect.setEstadoFactura(efactl.get(0));
		        			frect.setTextoComplementario(x.getTextoComplementario());
		        			frect.setTextoFormaPago(x.getTextoFormaPago());
		        			
				    		//There should be only one element in the list	        	
				        	SerieFacturas sf = sfact.get(0);
		
				        	frect.setSerie(sf);
				        	
							Optional<String> strSf = Optional.ofNullable(sf.getTextoInicioNumeracion());
							strSf.ifPresent((y) -> {
								numwrapper.numInic = y;
							});
						
							//By default the fecha factura is the current date
							numwrapper.year = mutils.getYearFromDate(new Date());
							
							Optional<Integer> numSf = Optional.ofNullable(factRepository.getMaximumFacturaNumberBySerie(sf, mutils.getDateFromString("01-01-" + numwrapper.year, mutils.DATE_FORMAT), mutils.getDateFromString("31-12-" + numwrapper.year, mutils.DATE_FORMAT)));
								numSf.ifPresentOrElse((y) -> {
									numwrapper.num = y.intValue() + 1;
								}, () -> {
									numwrapper.num = 1;
								});
						
							frect.setNumero(numwrapper.num);
							frect.setNumeroCompuesto(numwrapper.numInic + "-" + numwrapper.year + "-" + "0".repeat(3-(numwrapper.num).toString().length()) + numwrapper.num);

							strSf = Optional.ofNullable(sf.getTextoRectificativa());
								strSf.ifPresentOrElse((y) -> {
									frect.setTextoRectificativa(y + " " + x.getNumeroCompuesto());
								}, () -> {
									frect.setTextoRectificativa("Rectifica la factura " + x.getNumeroCompuesto());
								});
		        			
		        			//TODO Chek there's a list
		        			//Get the set of items and process them
		        			for (ItemFactura it: x.getItemsFactura()) {
		        				
		        				//Create each item
		            			ItemFactura itfact = new ItemFactura();
		                		itfact.setConcepto(it.getConcepto());
		                		itfact.setImporte(it.getImporte() * (-1));
		            	    	itfact.setFactura(frect);
		            	    	
		            	    	//Add to the factura rectificativa
		            	    	frect.getItemsFactura().add(itfact);
	        				}
		        			
		        			frect.setImpGastosEnvio(x.getImpGastosEnvio() * (-1));
		        			
		        			frect.recalculate();
		        			
		        			frect.setFacturaRectificada(x);
		        			frect.setExisteRectificativa(false);
		        			frect.setMarcador("");
		        			frect.setAnotaciones("");
		        			frect.setFechaEmision(new Date());
		        			frect.setUltimaActualizacion(new Date());
		        			
		        		    //Finally save Factura rectificativa
		        	    	factRepository.save(frect);
		        	    	
		        	    	//Update the factura
		        	    	x.setExisteRectificativa(true);
		        	    	x.setTextoRectificativa("Rectificada en factura " + frect.getNumeroCompuesto());
		        	    	
		        	    	factRepository.save(x);
		        			
		        		});
	
		        		//After a rectificativa is generated both facturas are not modificable
		        		closeForm();
	
	        	} else {
	        		
	        		//Display message to inform there's no serie facturas available
	        		Alert merror = new Alert(Alert.AlertType.WARNING, "Debe configurar al menos una serie de facturas para RECTIFICATIVAS", ButtonType.YES);
	                
	                merror.showAndWait().orElse(ButtonType.NO);
	
	        	}
	        	
	        } else {
	        	
	        	lbmsg1.setText("No existe ninguna serie para facturas rectificativas");
	        	lbmsg2.setText("");
	        }
        }
	}
	
    @FXML
    void bcloseOnAction(ActionEvent event) {

    	closeForm();

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
		cbdesc.setItems(FXCollections.observableList(descRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbdesc.getItems().add(null);
		
		cbtiva.setItems(FXCollections.observableList(tivaRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbtiva.getItems().add(null);
		
		cbfpago.setItems(FXCollections.observableList(fpagoRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbfpago.getItems().add(null);
		
		cbefact.setItems(FXCollections.observableList(efactRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbefact.getItems().add(null);

	    //Set the format of decimal fields
	    tximpitems.setTextFormatter(mutils.getTextFormatterForDecimal());
	    txgenv.setTextFormatter(mutils.getTextFormatterForDecimal());
	    
	    switch (factcrud.getAction()) {
        case ADD :
        	
        	//Facturas should not created using a form
        	//but using an automatic procedure
        	break;
        
        case EDIT:
        	
    		try {
    			
				fillControls(factcrud.getDao());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        	break;
        	
		case DELETE:
			
    		try {
    			
				fillControls(factcrud.getDao());
				
	    		disableControls();
	    		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
		case VIEW:
			
    		try {
    			
				fillControls(factcrud.getDao());
				
	    		disableControls();
	    		
			} catch (Exception e) {
				e.printStackTrace();
			}

		default:
			
			break;
	    }
	    
	    //Setting the maximum number of characters of TextField
	    txrefer.addEventFilter(KeyEvent.KEY_TYPED, maxLength(30));
	    txcifnif.addEventFilter(KeyEvent.KEY_TYPED, maxLength(25));
	    //txfpago.addEventFilter(KeyEvent.KEY_TYPED, maxLength(150));
	    txmarc.addEventFilter(KeyEvent.KEY_TYPED, maxLength(30));
	    tximpitems.addEventFilter(KeyEvent.KEY_TYPED, maxLength(9));
	    txgenv.addEventFilter(KeyEvent.KEY_TYPED, maxLength(9));
	    
	    //Setting the maximum number of characters of TextArea (another way)
//	    tatitular.setTextFormatter(new TextFormatter<String>(change -> 
//    		change.getControlNewText().length() <= 250 ? change : null));
//	    tadirec.setTextFormatter(new TextFormatter<String>(change -> 
//			change.getControlNewText().length() <= 250 ? change : null));
//	    taconcep.setTextFormatter(new TextFormatter<String>(change -> 
//	    	change.getControlNewText().length() <= 180 ? change : null));
//	    tatcomp.setTextFormatter(new TextFormatter<String>(change -> 
//			change.getControlNewText().length() <= 300 ? change : null));
//	    taanot.setTextFormatter(new TextFormatter<String>(change -> 
//			change.getControlNewText().length() <= 250 ? change : null));
	    
		//To check direccion maximum number of chars and show the number of chars
		UnaryOperator<Change> titFilter = (change -> {
			
			if (change.getControlNewText().length() <= maxtitchars) {
		    	
				showTitularChars(change.getControlNewText().length(), maxtitchars);
		    	
		        return change;
		    }
		    return null;
		});
		tatitular.setTextFormatter(new TextFormatter<String>(titFilter));
		
		//To check direccion maximum number of chars and show the number of chars
		UnaryOperator<Change> dirFilter = (change -> {
			
			if (change.getControlNewText().length() <= maxdirchars) {
		    	
				showDireccionChars(change.getControlNewText().length(), maxdirchars);
		    	
		        return change;
		    }
		    return null;
		});
		tadirec.setTextFormatter(new TextFormatter<String>(dirFilter));
	    
		//To check direccion maximum number of chars and show the number of chars
		UnaryOperator<Change> tfpagoFilter = (change -> {
			
			if (change.getControlNewText().length() <= maxtfpagchars) {
		    	
				showFormaPagoChars(change.getControlNewText().length(), maxtfpagchars);
		    	
		        return change;
		    }
		    return null;
		});
		txfpago.setTextFormatter(new TextFormatter<String>(tfpagoFilter));
		
		//To check texto complementario maximum number of chars and show the number of chars
		UnaryOperator<Change> tcompFilter = (change -> {
			
			if (change.getControlNewText().length() <= maxtcompchars) {
		    	
				showTextoComplementarioChars(change.getControlNewText().length(), maxtcompchars);
		    	
		        return change;
		    }
		    return null;
		});
		tatcomp.setTextFormatter(new TextFormatter<String>(tcompFilter));
		
		//To check anotaciones maximum number of chars and show the number of chars
		UnaryOperator<Change> anotFilter = (change -> {
			
			if (change.getControlNewText().length() <= maxanotchars) {
		    	
				showTextoComplementarioChars(change.getControlNewText().length(), maxanotchars);
		    	
		        return change;
		    }
		    return null;
		});
		taanot.setTextFormatter(new TextFormatter<String>(anotFilter));
		
		//To check concepto maximum number of chars and show the number of chars
		UnaryOperator<Change> concepFilter = (change -> {
			
			if (change.getControlNewText().length() <= maxconcepchars) {
		    	
				showConceptoChars(change.getControlNewText().length(), maxconcepchars);
		    	
		        return change;
		    }
		    return null;
		});
		taconcep.setTextFormatter(new TextFormatter<String>(concepFilter));
	    
	    //Check the visible buttons
	    //Facturas rectificadas and rectificativas can not be recalculated
	    bfrect.setVisible(!factcrud.getDao().isExisteRectificativa());
	    brecalc.setVisible(!factcrud.getDao().isExisteRectificativa());
	    Optional<Factura> optFac = Optional.ofNullable(factcrud.getDao().getFacturaRectificada());
	    	optFac.ifPresent((x) -> {
	    		bfrect.setVisible(false);
	    		brecalc.setVisible(false);
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
	
	private void saveDataToFactura(Factura fact) {
		
		Optional<Factura> factOpt = Optional.ofNullable(factcrud.getDao());
			factOpt.ifPresent((x) -> {
			
				Optional<LocalDate> optDate = Optional.ofNullable(dpffact.getValue());
					optDate.ifPresent((y) -> fact.setFechaFactura(Date.from(y.atStartOfDay(mutils.getDefaultZoneId()).toInstant())));
				
				x.setFacturaFirmada(chffirm.isSelected());
				x.setReferencia(obtainText(txrefer));
				x.setTitular(tatitular.getText());
				x.setDireccion(obtainText(tadirec));
				x.setCifnif(obtainText(txcifnif));
				x.setTextoFormaPago(obtainText(txfpago));
				x.setTextoComplementario(obtainText(tatcomp));
				x.setMarcador(obtainText(txmarc));
				
				for (ItemFactura item : x.getItemsFactura()) {
					item.setConcepto(obtainText(taconcep));
					item.setImporte(Double.parseDouble(obtainText(tximpitems)));
				}
				
				x.setDescuento(cbdesc.getValue());
				x.setTipoIVA(cbtiva.getValue());
				
				x.setImpGastosEnvio(Double.parseDouble(obtainText(txgenv)));
				
				//To set the rest of values
				x.recalculate();
				
				x.setFormaPago(cbfpago.getValue());
				x.setEstadoFactura(cbefact.getValue());
				
				x.setAnotaciones(obtainText(taanot));
				
				x.setUltimaActualizacion(new Date());
				
			});
		
	}

	private void fillControls(Factura pfac) throws Exception {
		
		var factwrapper = new Object(){ Factura fact = null; };

		//Optional<Factura> factOpt = Optional.ofNullable(factcrud.getFactura());
		Optional<Factura> factOpt = Optional.ofNullable(pfac);
			factOpt.ifPresentOrElse((x) -> {
				factwrapper.fact = x;
			}, () -> {
				factwrapper.fact = factcrud.getDao();
			});
			
		Optional<String> optStr = Optional.ofNullable(factwrapper.fact.getNumeroCompuesto());
			optStr.ifPresentOrElse((y) -> {
				lbnfact.setText(y);
			}, () -> {
				lbnfact.setText("");
			});

		Optional<SerieFacturas> optSf = Optional.ofNullable(factwrapper.fact.getSerie());
			optSf.ifPresentOrElse((y) -> {
				lbserie.setText(y.getDescripcion());
			}, () -> {
				lbserie.setText("");
			});

		Optional<Factura> optFact = Optional.ofNullable(factwrapper.fact.getFacturaRectificada());
			optFact.ifPresentOrElse((y) -> {
				lbfrect.setText(y.getNumeroCompuesto());
			}, () -> {
				lbfrect.setText("");
			});
			
		Optional<Boolean> optBool = Optional.ofNullable(factwrapper.fact.isExisteRectificativa());
			optBool.ifPresentOrElse((y) -> {
				
				if (y) {
					
					List<Factura> frectl = factRepository.findFacturaRectificativa(factwrapper.fact);
					
					Optional<List<Factura>> frectlOpt = Optional.ofNullable(frectl);
						frectlOpt.ifPresent((z) -> { 
							if (frectl.size() > 0) {
								lbesrect.setText("Rectificada en " + z.get(0).getNumeroCompuesto());
							} else {
								lbesrect.setText("Factura RECTIFICADA");
							}
						});

				} else {
					lbesrect.setText("");
				}
				
			}, () -> {
				lbesrect.setText("");
			});

		//Datepicker is blank if Fecha factura is null 
		Optional<Date> optDate = Optional.ofNullable(factwrapper.fact.getFechaFactura());
			optDate.ifPresent((y) -> dpffact.setValue(mutils.obtainLocalDate(y)));

		optBool = Optional.ofNullable(factwrapper.fact.isFacturaFirmada());
			optBool.ifPresent((y) -> chffirm.setSelected(y));

		Optional<Integer> optInt = Optional.ofNullable(factwrapper.fact.getSocio().getCodigoSocio());
			optInt.ifPresent((y) -> lbcsoc.setText(Integer.toString(y)));
		
		Optional<Agencia> optAge = Optional.ofNullable(factwrapper.fact.getAgencia());
			optAge.ifPresentOrElse((y) -> {
				lbagenc.setText(y.getNombre());
			}, () -> {
				lbagenc.setText("");
			});
		
		optStr = Optional.ofNullable(factwrapper.fact.getReferencia());
			optStr.ifPresentOrElse((y) -> {
				txrefer.setText(y);
			}, () -> {
				txrefer.setText("");
			});

		optStr = Optional.ofNullable(factwrapper.fact.getCifnif());
			optStr.ifPresentOrElse((y) -> {
				txcifnif.setText(y);
			}, () -> {
				txcifnif.setText("");
			});
		
		optStr = Optional.ofNullable(factwrapper.fact.getTitular());
			optStr.ifPresentOrElse((y) -> {
				tatitular.setText(y);
			}, () -> {
				tatitular.setText("");
			});
		
		showTitularChars(tatitular.getLength(), maxtitchars);
			
		optStr = Optional.ofNullable(factwrapper.fact.getDireccion());
			optStr.ifPresentOrElse((y) -> {
				tadirec.setText(y);
			}, () -> {
				tadirec.setText("");
			});
		
		showDireccionChars(tadirec.getLength(), maxdirchars);
			
		optStr = Optional.ofNullable(factwrapper.fact.getTextoFormaPago());
			optStr.ifPresentOrElse((y) -> {
				txfpago.setText(y);
			}, () -> {
				txfpago.setText("");
			});
		
		showFormaPagoChars(txfpago.getLength(), maxtfpagchars);
			
		optStr = Optional.ofNullable(factwrapper.fact.getTextoComplementario());
			optStr.ifPresentOrElse((y) -> {
				tatcomp.setText(y);
			}, () -> {
				tatcomp.setText("");
			});
			
		showTextoComplementarioChars(tatcomp.getLength(), maxtcompchars);
			
		optStr = Optional.ofNullable(factwrapper.fact.getMarcador());
			optStr.ifPresentOrElse((y) -> {
				txmarc.setText(y);
			}, () -> {
				txmarc.setText("");
			});

		List<ItemFactura> items = new ArrayList<>(factwrapper.fact.getItemsFactura());
		
		if (items.size() > 0) {
			optStr = Optional.ofNullable(items.get(0).getConcepto());
				optStr.ifPresentOrElse((y) -> {
					taconcep.setText(y);
				}, () -> {
					taconcep.setText("");
				});
		}
		
		showConceptoChars(taconcep.getLength(), maxconcepchars);

		fillImporteControls(factwrapper.fact);
			
		optStr = Optional.ofNullable(factwrapper.fact.getAnotaciones());
			optStr.ifPresentOrElse((y) -> {
				taanot.setText(y);
			}, () -> {
				taanot.setText("");
			});
			
		showAnotacionesChars(taanot.getLength(), maxanotchars);
		
		Optional<FormaPago> optFpag = Optional.ofNullable(factwrapper.fact.getFormaPago());
		optFpag.ifPresent((y) -> {
				cbfpago.getSelectionModel().select(mutils.searchIdInCombo(cbfpago, y));
			});
			
		Optional<EstadoFactura> optEfac = Optional.ofNullable(factwrapper.fact.getEstadoFactura());
		optEfac.ifPresent((y) -> {
				cbefact.getSelectionModel().select(mutils.searchIdInCombo(cbefact, y));
			});
			
		optDate = Optional.ofNullable(factwrapper.fact.getFechaEmision());
			optDate.ifPresent((y) -> lbfcrea.setText(mutils.getStringFromDate(y, mutils.DATE_FORMAT)));
			
		optDate = Optional.ofNullable(factwrapper.fact.getUltimaActualizacion());
			optDate.ifPresent((y) -> lbultact.setText(mutils.getStringFromDate(y, mutils.DATE_FORMAT)));

	}
	
	private void fillImporteControls(Factura pfac) {
	
		List<ItemFactura> items = new ArrayList<>(pfac.getItemsFactura());
		
		if (items.size() > 0) {
				
			Optional<Double> optDou3 = Optional.ofNullable(items.get(0).getImporte());
				optDou3.ifPresentOrElse((y) -> {
					tximpitems.setText(Double.toString(y));
				}, () -> {
					tximpitems.setText("");
				});
				
		}

		Optional<Descuento> optDesc = Optional.ofNullable(pfac.getDescuento());
			optDesc.ifPresentOrElse((y) -> {
				
				cbdesc.getSelectionModel().select(mutils.searchIdInCombo(cbdesc, y));

				Optional<Double> optDou2 = Optional.ofNullable(y.getPorcentaje());
					optDou2.ifPresentOrElse((z) -> {
						lbporcdesc.setText(String.format("%.2f", z));
					}, () -> {
						lbporcdesc.setText(mutils.CURRENCY_ZERO);
					});
					
				optDou2 = Optional.ofNullable(pfac.getImporteDescuento());
					optDou2.ifPresentOrElse((z) -> {
						lbdesc.setText(String.format("%.2f", z));
					}, () -> {
						lbdesc.setText(mutils.CURRENCY_ZERO);
					});
				
			}, () -> {
				lbporcdesc.setText(mutils.CURRENCY_ZERO);
				lbdesc.setText(mutils.CURRENCY_ZERO);
			});
		
		Optional<Double> optDou = Optional.ofNullable(pfac.getImporteBaseImponible());
			optDou.ifPresentOrElse((y) -> {
				lbbimp.setText(String.format("%.2f", y));
			}, () -> {
				lbbimp.setText(mutils.CURRENCY_ZERO);
			});
			
		Optional<TipoIVA> optTiva = Optional.ofNullable(pfac.getTipoIVA());
			optTiva.ifPresentOrElse((y) -> {
				
				cbtiva.getSelectionModel().select(mutils.searchIdInCombo(cbtiva, y));

				Optional<Double> optDou2 = Optional.ofNullable(y.getPorcentaje());
					optDou2.ifPresentOrElse((z) -> {
						lbporciva.setText(String.format("%.2f", z));
					}, () -> {
						lbporciva.setText(mutils.CURRENCY_ZERO);
					});
				
				optDou2 = Optional.ofNullable(pfac.getImporteTipoIVA());
					optDou2.ifPresentOrElse((z) -> {
						lbiva.setText(String.format("%.2f", z));
					}, () -> {
						lbiva.setText(mutils.CURRENCY_ZERO);
					});
			
			}, () -> {
				lbporciva.setText(mutils.CURRENCY_ZERO);
				lbiva.setText(mutils.CURRENCY_ZERO);
			});
		
		optDou = Optional.ofNullable(pfac.getImpGastosEnvio());
			optDou.ifPresentOrElse((y) -> {
				txgenv.setText(Double.toString(y));
			}, () -> {
				txgenv.setText(mutils.CURRENCY_ZERO);
			});
		
		optDou = Optional.ofNullable(pfac.getImporteTotal());
			optDou.ifPresentOrElse((y) -> {
				lbtfact.setText(String.format("%.2f", y));
			}, () -> {
				lbtfact.setText(mutils.CURRENCY_ZERO);
			});

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
    
    private String obtainText(TextArea tx) {
    	
    	//To check null values we use optional and to avoid the block scope of variables we use a wrapper
    	var strwrapper = new Object(){ String str = ""; };
    	
		Optional<String> strOpt = Optional.ofNullable(tx.getText());
	    	strOpt.ifPresent((x) -> {
	    		strwrapper.str = tx.getText();
	    	});
    		
    	return strwrapper.str;
    }
    
    private void recalculateFromControls() {
    	
		var factwrapper = new Object(){ Factura fact = null; };
		
		//Optional<Factura> factOpt = Optional.ofNullable(factcrud.getFactura());
		Optional<Factura> factOpt = Optional.ofNullable(factcrud.getDao());
			factOpt.ifPresent((x) -> {
				factwrapper.fact = x;
				
				for (ItemFactura item : factwrapper.fact.getItemsFactura()) {
					item.setImporte(Double.parseDouble(obtainText(tximpitems)));
				}
				
				Optional<Descuento> optDesc = Optional.ofNullable(cbdesc.getValue());
					optDesc.ifPresent((y) -> factwrapper.fact.setDescuento(y));
					
				Optional<TipoIVA> optTiva = Optional.ofNullable(cbtiva.getValue());
					optTiva.ifPresent((y) -> factwrapper.fact.setTipoIVA(y));
				
				factwrapper.fact.setImpGastosEnvio(Double.parseDouble(obtainText(txgenv)));
			
				factwrapper.fact.recalculate();
				
				try {
					fillImporteControls(factwrapper.fact);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
    }
	
	private void disableControls() {
		
		disableControl(dpffact);
		dpffact.getEditor().setStyle("-fx-opacity: 1;");
		
		disableControl(chffirm);
		disableControl(txrefer);
		disableControl(tatitular);
		disableControl(tadirec);
		disableControl(txcifnif);
		disableControl(txfpago);
		disableControl(tatcomp);
		disableControl(txmarc);
		disableControl(taconcep);
		disableControl(tximpitems);
		//In comboboxex is needed to set a CSS to disable cells
		disableControl(cbdesc);
		disableControl(cbtiva);
		disableControl(txgenv);
		disableControl(cbfpago);
		disableControl(cbefact);
		disableControl(taanot);
		
	}
	
	private void disableControl(Control fxcontrol) {
		
		fxcontrol.setDisable(true);
		//when disabled controls have 0.4 opacity, but we use 1 to simulate as enabled
		fxcontrol.setStyle("-fx-opacity: 1");
		
		//fxcontrol.setStyle("-fx-opacity: 1;  -fx-background-color: white");
		//fxcontrol.setStyle("-fx-opacity: 1; -fx-text-fill: black;-fx-background-color: white");

		//For combos and tables and so the opacity, wich is responsible of the gray
		//color in text fields when disabled should be modified using a CSS file
		//We can add a CSS file to a Scene in code or using Scenebuilder
		
	}
	
	private void showTextoComplementarioChars(int numchars, int maxchars) {
		
		lbtcomplen.setText("(" + numchars + "/" + maxchars + " caracteres)");
	}
	
	private void showAnotacionesChars(int numchars, int maxchars) {
		
		lbanotlen.setText("(" + numchars + "/" + maxchars + " caracteres)");
	}
	
	private void showConceptoChars(int numchars, int maxchars) {
		
		lbconceplen.setText("(" + numchars + "/" + maxchars + " caracteres)");
	}
	
	private void showTitularChars(int numchars, int maxchars) {
		
		lbtitlen.setText("(" + numchars + "/" + maxchars + " caracteres)");
	}
	
	private void showDireccionChars(int numchars, int maxchars) {
		
		lbdirlen.setText("(" + numchars + "/" + maxchars + " caracteres)");
	}
	
	private void showFormaPagoChars(int numchars, int maxchars) {
		
		lbtfpaglen.setText("(" + numchars + "/" + maxchars + " caracteres)");
	}
}
