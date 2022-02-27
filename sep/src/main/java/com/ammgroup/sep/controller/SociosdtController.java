package com.ammgroup.sep.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAOSoc;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.Descuento;
import com.ammgroup.sep.model.FormaPago;
import com.ammgroup.sep.model.ModalidadSocio;
import com.ammgroup.sep.model.ModoAcceso;
import com.ammgroup.sep.model.MotivoBaja;
import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.model.Socio;
import com.ammgroup.sep.model.ZonaPostal;
import com.ammgroup.sep.repository.AgenciaRepository;
import com.ammgroup.sep.repository.DescuentoRepository;
import com.ammgroup.sep.repository.FormaPagoRepository;
import com.ammgroup.sep.repository.ModalidadSocioRepository;
import com.ammgroup.sep.repository.ModoAccesoRepository;
import com.ammgroup.sep.repository.MotivoBajaRepository;
import com.ammgroup.sep.repository.PaisRepository;
import com.ammgroup.sep.repository.ProvinciaRepository;
import com.ammgroup.sep.repository.SocioRepository;
import com.ammgroup.sep.repository.ZonaPostalRepository;
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
import javafx.util.converter.IntegerStringConverter;

@Component
public class SociosdtController implements Initializable {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAOSoc<Socio> socrud;

	@Autowired
	ModalidadSocioRepository msocioRepository;
	
	@Autowired
	ModoAccesoRepository maccesoRepository;
	
	@Autowired
	SocioRepository socioRepository;
	
	@Autowired
	AgenciaRepository agenciaRepository;
	
	@Autowired
	PaisRepository paisRepository;
	
	@Autowired
	ProvinciaRepository provRepository;
	
	@Autowired
	ZonaPostalRepository zpostRepository;
	
	@Autowired
	FormaPagoRepository fpagoRepository;
	
	@Autowired
	DescuentoRepository descRepository;
	
	@Autowired
	MotivoBajaRepository mbajaRepository;

	@FXML
	private DatePicker dpfalta;
	
	@FXML
	private TextField txcod;
	
	@FXML
	private ComboBox<ModalidadSocio> cbmod;
	
	@FXML
	private ComboBox<ModoAcceso> cbmacceso;
	
	@FXML
	private TextField txojs;
	
	@FXML
	private TextField txnombre;
	
	@FXML
	private TextField txapell;
	
	@FXML
	private TextField txcifnif;
	
	@FXML
	private TextArea txardomic;
	
	@FXML
	private TextField txcp;

	@FXML
	private TextField txlocal;

	@FXML
	private ComboBox<Provincia> cbprov;

	@FXML
	private ComboBox<Pais> cbpais;
	
	@FXML
	private ComboBox<ZonaPostal> cbzpost;
	
	@FXML
	private TextField txemail;
	
	@FXML
	private TextField txtelefono;
	
	@FXML
	private TextField txtelmovil;
	
	@FXML
	private TextField txctrab;
	
	@FXML
	private TextField txatrab;

	@FXML
	private TextField txtitul;
	
	@FXML
	private TextField txcsep;
	
	@FXML
	private CheckBox chlopd;
	
	@FXML
	private CheckBox chldist;
	
	@FXML
	private TextField txmarc;
		
	@FXML
	private ComboBox<FormaPago> cbfpago;
	
	@FXML
	private TextField txdbanc;
	
	@FXML
	private ComboBox<Descuento> cbdesc;
	
	@FXML
	private CheckBox chfact;
	
	@FXML
	private CheckBox chfirfact;
	
	@FXML
	private ComboBox<Agencia> cbagencia;
	
	@FXML
	private TextField txref;

	@FXML
	private CheckBox chbaja;

	@FXML
	private DatePicker dpfbaja;

	@FXML
	private ComboBox<MotivoBaja> cbmbaja;
	
	@FXML
	private TextField txcjdir;
	
	@FXML
	private CheckBox chjdiract;

	@FXML
	private TextArea txaranot;

	@FXML
	private Label lbmsg1;
	
	@FXML
	private Label lbmsg2;

	@FXML
	private Button bexec;
	
	@FXML
	private Button bfact;
	
	@FXML
	private Button breclam;

	@FXML
	private Button bclose;
	
	@FXML
    void bexecOnAction(ActionEvent event) {

		boolean cform = true;
		Socio s;
		long cont1;
		//long cont2;
		
	    switch (socrud.getAction()) {
        case ADD :
        	
        	if (checkControls()) {
        	
	        	//cont1 = socioRepository.countExistingSociosByCifnif(txcifnif.getText());
	        	//cont2 = socioRepository.countExistingSociosByCodigoSocio(Integer.parseInt(txcod.getText()));
	        	
	        	//if (cont1 == 0) {
	
	        		Socio soc = new Socio();
	        		
	        		socrud.setDao(soc);
	        		
	        		saveDataToSocio(soc);
	        		
	        		socioRepository.save(soc);
	        		
	        	//} else {
	        		
	        		//lbmsg1.setText("Existen " + String.valueOf(cont1) + " socios con ese CIF/NIF.");
	        		//lbmsg2.setText("Existen " + String.valueOf(cont2) + " socioss con ese código.");
	        		
	        		//cform = false;
	        	//}
        	} else {
        		
        		cform = false;
        		
        	}
        	
            break;

        case EDIT:

        	if (checkControls()) {
        		
            	//Check if there are no coincidences
            	cont1 = socioRepository.countExistingSociosByCodigoSocio(Integer.parseInt(txcod.getText()), socrud.getDao().getId());
            	//cont2 = socioRepository.countExistingSociosByCifnif(txcifnif.getText(), socrud.getSocio().getId());
	
        		if (cont1 == 0) {
	
	        		s = socrud.getDao();
	        		
	        		saveDataToSocio(s);
	
	        		socioRepository.save(s);
	
	        	} else {
	        	
	        		lbmsg1.setText("Existen " + String.valueOf(cont1) + " socios con ese código.");
	        		//lbmsg2.setText("Existen " + String.valueOf(cont2) + " socios con ese CIF/NIF.");
	        		cform = false;
	        	}
        	
	    	} else {
	    		
	    		cform = false;
	    		
	    	}
        	
            break;
        
        case DELETE:
        	
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Confirma el BORRADO de la reclamación?", ButtonType.YES, ButtonType.NO);

	        // clicking X also means no
	        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
	        
	        if (ButtonType.YES.equals(result)) {
        
	        	s = socrud.getDao();
	        	socioRepository.delete(s);
        	
	        } else {
	        	
	        	cform = false;
	        }
	        
            break;
		
        case VIEW:
		
        	break;
		
        default:
		
        	break;
	    }
	    
    	if (cform) closeForm();

	}
	
	@FXML
    void bfactOnAction(ActionEvent event) {
		
		//To set the title of the form
		var strwrapper = new Object(){ String str = ""; };
		
		Optional<String> strOpt = Optional.ofNullable(socrud.getDao().getNombre());
	    	strOpt.ifPresent((x) -> {
	    		strwrapper.str = x;
	    	});
	    	
		strOpt = Optional.ofNullable(socrud.getDao().getApellidos());
	    	strOpt.ifPresent((x) -> {
	    		strwrapper.str += " " + x;
	    	});
    	
		try {
			mutils.loadForm("facagesocl.fxml", "Facturas del socio " + strwrapper.str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
    void breclamOnAction(ActionEvent event) {
		
		//To set the title of the form
		var strwrapper = new Object(){ String str = ""; };
		
		Optional<String> strOpt = Optional.ofNullable(socrud.getDao().getNombre());
	    	strOpt.ifPresent((x) -> {
	    		strwrapper.str = x;
	    	});
	    	
		strOpt = Optional.ofNullable(socrud.getDao().getApellidos());
	    	strOpt.ifPresent((x) -> {
	    		strwrapper.str += " " + x;
	    	});
		
		try {
			mutils.loadForm("reclagesocl.fxml", "Reclamaciones del socio " + strwrapper.str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveDataToSocio(Socio soc) {
		
		Optional<Socio> socOpt = Optional.ofNullable(socrud.getDao());
			socOpt.ifPresent((x) -> {
				
				x.setFechaAlta(Date.from(dpfalta.getValue().atStartOfDay(mutils.getDefaultZoneId()).toInstant()));
				x.setCodigoSocio(Integer.parseInt(obtainText(txcod)));
				
				Optional<ModalidadSocio> optMod = Optional.ofNullable(cbmod.getSelectionModel().getSelectedItem());
					optMod.ifPresentOrElse((y) -> {
						x.setModalidad(y);
					}, () -> {
						x.setModalidad(null);
					});

				Optional<ModoAcceso> optMac = Optional.ofNullable(cbmacceso.getSelectionModel().getSelectedItem());
					optMac.ifPresentOrElse((y) -> {
						x.setModoAcceso(y);
					}, () -> {
						x.setModoAcceso(null);
					});
				
				x.setOjs(obtainText(txojs));
				x.setNombre(obtainText(txnombre));
				x.setApellidos(obtainText(txapell));
				x.setCifnif(obtainText(txcifnif));
				x.setDomicilio(obtainText(txardomic));
				x.setCp(obtainText(txcp));
				x.setLocalidad(obtainText(txlocal));
				
				Optional<Provincia> optPro = Optional.ofNullable(cbprov.getSelectionModel().getSelectedItem());
					optPro.ifPresentOrElse((y) -> {
						x.setProvincia(y);
					}, () -> {
						x.setProvincia(null);
					});

				Optional<Pais> optPai = Optional.ofNullable(cbpais.getSelectionModel().getSelectedItem());
					optPai.ifPresentOrElse((y) -> {
						x.setPais(y);
					}, () -> {
						x.setPais(null);
					});

				Optional<ZonaPostal> optZpo = Optional.ofNullable(cbzpost.getSelectionModel().getSelectedItem());
					optZpo.ifPresentOrElse((y) -> {
						x.setZonaPostal(y);
					}, () -> {
						x.setZonaPostal(null);
					});

				x.setEmail(obtainText(txemail));
				x.setTelefono(obtainText(txtelefono));
				x.setMovil(obtainText(txtelmovil));
				x.setCentroTrabajo(obtainText(txctrab));
				x.setAreaTrabajo(obtainText(txatrab));
				x.setTitulacion(obtainText(txtitul));
				x.setContactoSep(obtainText(txcsep));
				x.setLopd(chlopd.isSelected());
				x.setListaDistribucion(chldist.isSelected());
				x.setMarcador(obtainText(txmarc));

				Optional<FormaPago> optFpa = Optional.ofNullable(cbfpago.getSelectionModel().getSelectedItem());
					optFpa.ifPresentOrElse((y) -> {
						x.setFormaPago(y);
					}, () -> {
						x.setFormaPago(null);
					});
					
				x.setIbanccc(obtainText(txdbanc));
				
				Optional<Descuento> optDes = Optional.ofNullable(cbdesc.getSelectionModel().getSelectedItem());
					optDes.ifPresentOrElse((y) -> {
						x.setDescuento(y);
					}, () -> {
						x.setDescuento(null);
					});
				
				x.setFactura(chfact.isSelected());
				x.setFirmarFactura(chfirfact.isSelected());
				
				Optional<Agencia> optAge = Optional.ofNullable(cbagencia.getSelectionModel().getSelectedItem());
					optAge.ifPresentOrElse((y) -> {
						x.setAgencia(y);
					}, () -> {
						x.setAgencia(null);
					});
				
				x.setReferencia(obtainText(txref));
				x.setBaja(chbaja.isSelected());
				
				Optional<LocalDate> optDate = Optional.ofNullable(dpfbaja.getValue());
					optDate.ifPresentOrElse((y) -> {
						x.setFechaBaja(Date.from(y.atStartOfDay(mutils.getDefaultZoneId()).toInstant()));
					}, () -> {
						x.setFechaBaja(null);
					});

				Optional<MotivoBaja> optMba = Optional.ofNullable(cbmbaja.getSelectionModel().getSelectedItem());
					optMba.ifPresentOrElse((y) -> {
						x.setMotivoBaja(y);
					}, () -> {
						x.setMotivoBaja(null);
					});
					
				x.setCargosJuntaDirectiva(obtainText(txcjdir));
				x.setJuntaDirectivaActual(chjdiract.isSelected());

				x.setAnotaciones(obtainText(txaranot));
			
			});
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
	
    private String obtainText(TextArea tx) {
    	
    	//To check null values we use optional and to avoid the block scope of variables we use a wrapper
    	var strwrapper = new Object(){ String str = ""; };
    	
		Optional<String> strOpt = Optional.ofNullable(tx.getText());
    	strOpt.ifPresent((x) -> {
    		strwrapper.str = tx.getText();
    	});
    		
    	return strwrapper.str;
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Populate the combo of modalidades de socio
		cbmod.setItems(FXCollections.observableList(msocioRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbmod.getItems().add(null);
		//Populate the combo of modos de acceso
		cbmacceso.setItems(FXCollections.observableList(maccesoRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbmacceso.getItems().add(null);
		//Populate the combo of provincias
		cbprov.setItems(FXCollections.observableList(provRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbprov.getItems().add(null);
		//Populate the combo of paises
		cbpais.setItems(FXCollections.observableList(paisRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbpais.getItems().add(null);
		//Populate the combo of zonas postales
		cbzpost.setItems(FXCollections.observableList(zpostRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbzpost.getItems().add(null);
		//Populate the combo of formas de pago
		cbfpago.setItems(FXCollections.observableList(fpagoRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbfpago.getItems().add(null);
		//Populate the combo of descuentos
		cbdesc.setItems(FXCollections.observableList(descRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbdesc.getItems().add(null);
		//Populate the combo of agencias
		cbagencia.setItems(FXCollections.observableList(agenciaRepository.findAll()));
		cbagencia.getItems().add(null);
		//Populate the combo of motivos de las bajas
		cbmbaja.setItems(FXCollections.observableList(mbajaRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbmbaja.getItems().add(null);
	    
		final String ipattern = mutils.INTEGER_PATTERN;
	    
		//Setting a filter to allow only numbers
		UnaryOperator<Change> integerFilter = (change -> {
		    String newText = change.getControlNewText();

		    if (newText.matches(ipattern)) {
		        return change;
		    }
		    return null;
		});
		
		//Setting the formatters
		txcod.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
		txcp.setTextFormatter(new TextFormatter<String>(integerFilter));
		txtelefono.setTextFormatter(new TextFormatter<String>(integerFilter));

		switch (socrud.getAction()) {
        case ADD :

        	dpfalta.setValue(LocalDate.now());
    		txcod.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), (socioRepository.getHigherCodigoSocio()+1), integerFilter));
    		txnombre.setText("");
    		txcifnif.setText("");
    		txardomic.setText("");
    		
    		//txcp.setText("0");
    		txlocal.setText("");
    		txemail.setText("");
    		txtelefono.setText("");
    		txaranot.setText("");
    		txmarc.setText("");
    		txcjdir.setText("");
    		
    		disableButtons();
    		
            break;
            
        case EDIT:
        	
    		fillControls();
        	
    		//lbmsg1.setText("Utilizada en " + String.valueOf(agenciaRepository.countRelatedSocios(socrud.getSocio().getId())) + " socios");
    		//lbmsg2.setText("Utilizada en " + String.valueOf(agenciaRepository.countRelatedAgencias(zpcrud.getZonaPostal().getId())) + " agencias");
    		
    		break;
    		
        case DELETE:
    		
    		fillControls();

    		disableControls();
    		
    		//long rsoc = zpostRepository.countRelatedSocios(agcrud.getAgencia().getId()); 
    		//lbmsg1.setText("Utilizada en " + String.valueOf(rsoc) + " socios");

    		//long rage = zpostRepository.countRelatedAgencias(zpcrud.getZonaPostal().getId()); 
    		//lbmsg2.setText("Utilizada en " + String.valueOf(rage) + " agencias");

    		//Disable execute button
    		//if (rsoc > 0) bexec.setDisable(true);
    		//if ((rsoc > 0) || (rage > 0)) bexec.setDisable(true);
            
    		break;
    		
		case VIEW:
    		
    		fillControls();

			disableControls();

    		//lbmsg1.setText("Utilizada en " + String.valueOf(agenciaRepository.countRelatedSocios(agcrud.getAgencia().getId())) + " socios");
    		//lbmsg2.setText("Utilizada en " + String.valueOf(agenciaRepository.countRelatedAgencias(zpcrud.getZonaPostal().getId())) + " agencias");
			
    		break;
    		
		default:
			
			break;
	    }
		
	    //Setting the maximum number of characters of TextField
		txojs.addEventFilter(KeyEvent.KEY_TYPED, maxLength(50));
	    txnombre.addEventFilter(KeyEvent.KEY_TYPED, maxLength(70));
	    txcifnif.addEventFilter(KeyEvent.KEY_TYPED, maxLength(12));
	    txcp.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
	    txlocal.addEventFilter(KeyEvent.KEY_TYPED, maxLength(70));
	    txemail.addEventFilter(KeyEvent.KEY_TYPED, maxLength(80));
	    txtelefono.addEventFilter(KeyEvent.KEY_TYPED, maxLength(12));
	    txtelmovil.addEventFilter(KeyEvent.KEY_TYPED, maxLength(12));
	    txctrab.addEventFilter(KeyEvent.KEY_TYPED, maxLength(70));
	    txatrab.addEventFilter(KeyEvent.KEY_TYPED, maxLength(70));
	    txtitul.addEventFilter(KeyEvent.KEY_TYPED, maxLength(70));
	    txcsep.addEventFilter(KeyEvent.KEY_TYPED, maxLength(70));
	    txmarc.addEventFilter(KeyEvent.KEY_TYPED, maxLength(30));
	    txdbanc.addEventFilter(KeyEvent.KEY_TYPED, maxLength(30));
	    txref.addEventFilter(KeyEvent.KEY_TYPED, maxLength(30));
	    txcjdir.addEventFilter(KeyEvent.KEY_TYPED, maxLength(60));

	    //Setting the maximum number of characters of TextArea
	    txardomic.setTextFormatter(new TextFormatter<String>(change -> 
	    	change.getControlNewText().length() <= 100 ? change : null));
	    txaranot.setTextFormatter(new TextFormatter<String>(change -> 
	    	change.getControlNewText().length() <= 250 ? change : null));
	    
	    
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
	
	private void closeForm() {
		
		// get a handle to the stage
		Stage stage = (Stage) bclose.getScene().getWindow();
		// do what you have to do
		stage.close();
		
	}
	
	private void fillControls() {
		
		Optional<Socio> socOpt = Optional.ofNullable(socrud.getDao());
			socOpt.ifPresent((x) -> {
				
				Optional<Date> optDate = Optional.ofNullable(x.getFechaAlta());
					optDate.ifPresent((y) -> dpfalta.setValue(mutils.obtainLocalDate(y)));

				Optional<Integer> optInt = Optional.ofNullable(x.getCodigoSocio());
					optInt.ifPresentOrElse((z) -> {
						txcod.setText(String.format("%d", z));
					}, () -> {
						txcod.setText("0");
					});
					
				Optional<ModalidadSocio> optMod = Optional.ofNullable(x.getModalidad());
					optMod.ifPresent((y) -> {
						cbmod.getSelectionModel().select(mutils.searchIdInCombo(cbmod, y));
					});
					
				Optional<ModoAcceso> optMac = Optional.ofNullable(x.getModoAcceso());
				optMac.ifPresent((y) -> {
						cbmacceso.getSelectionModel().select(mutils.searchIdInCombo(cbmacceso, y));
					});
					
				Optional<String> optStr = Optional.ofNullable(x.getOjs());
					optStr.ifPresentOrElse((y) -> {
						txojs.setText(y);
					}, () -> {
						txojs.setText("");
					});
					
				optStr = Optional.ofNullable(x.getNombre());
					optStr.ifPresentOrElse((y) -> {
						txnombre.setText(y);
					}, () -> {
						txnombre.setText("");
					});
					
				optStr = Optional.ofNullable(x.getApellidos());
					optStr.ifPresentOrElse((y) -> {
						txapell.setText(y);
					}, () -> {
						txapell.setText("");
					});

				optStr = Optional.ofNullable(x.getCifnif());
					optStr.ifPresentOrElse((y) -> {
						txcifnif.setText(y);
					}, () -> {
						txcifnif.setText("");
					});
					
				optStr = Optional.ofNullable(x.getDomicilio());
					optStr.ifPresentOrElse((y) -> {
						txardomic.setText(y);
					}, () -> {
						txardomic.setText("");
					});
					
				optStr = Optional.ofNullable(x.getCp());
					optStr.ifPresentOrElse((y) -> {
						txcp.setText(y);
					}, () -> {
						txcp.setText("");
					});
					
				optStr = Optional.ofNullable(x.getLocalidad());
					optStr.ifPresentOrElse((y) -> {
						txlocal.setText(y);
					}, () -> {
						txlocal.setText("");
					});
					
		    	Optional<Provincia> provOpt = Optional.ofNullable(x.getProvincia());
		    		provOpt.ifPresent((y) -> cbprov.getSelectionModel().select(mutils.searchIdInCombo(cbprov, y)));

		    	Optional<Pais> paisOpt = Optional.ofNullable(x.getPais());
		    		paisOpt.ifPresent((y) -> cbpais.getSelectionModel().select(mutils.searchIdInCombo(cbpais, y)));
		       	
		    	Optional<ZonaPostal> zpostOpt = Optional.ofNullable(x.getZonaPostal());
		    		zpostOpt.ifPresent((y) -> cbzpost.getSelectionModel().select(mutils.searchIdInCombo(cbzpost, y)));
				
				optStr = Optional.ofNullable(x.getEmail());
					optStr.ifPresentOrElse((y) -> {
						txemail.setText(y);
					}, () -> {
						txemail.setText("");
					});

				optStr = Optional.ofNullable(x.getTelefono());
					optStr.ifPresentOrElse((y) -> {
						txtelefono.setText(y);
					}, () -> {
						txtelefono.setText("");
					});

				optStr = Optional.ofNullable(x.getMovil());
					optStr.ifPresentOrElse((y) -> {
						txtelmovil.setText(y);
					}, () -> {
						txtelmovil.setText("");
					});
					
				optStr = Optional.ofNullable(x.getCentroTrabajo());
					optStr.ifPresentOrElse((y) -> {
						txctrab.setText(y);
					}, () -> {
						txctrab.setText("");
					});
					
				optStr = Optional.ofNullable(x.getAreaTrabajo());
					optStr.ifPresentOrElse((y) -> {
						txatrab.setText(y);
					}, () -> {
						txatrab.setText("");
					});
					
				optStr = Optional.ofNullable(x.getTitulacion());
					optStr.ifPresentOrElse((y) -> {
						txtitul.setText(y);
					}, () -> {
						txtitul.setText("");
					});
					
				optStr = Optional.ofNullable(x.getContactoSep());
					optStr.ifPresentOrElse((y) -> {
						txcsep.setText(y);
					}, () -> {
						txcsep.setText("");
					});

				Optional<Boolean> optBool = Optional.ofNullable(x.isLopd());
					optBool.ifPresent((y) -> {
						chlopd.setSelected(y);
					});
				
				optBool = Optional.ofNullable(x.isListaDistribucion());
					optBool.ifPresent((y) -> {
						chldist.setSelected(y);
					});
					
				optStr = Optional.ofNullable(x.getMarcador());
					optStr.ifPresentOrElse((y) -> {
						txmarc.setText(y);
					}, () -> {
						txmarc.setText("");
					});

				Optional<FormaPago> optFpag = Optional.ofNullable(x.getFormaPago());
					optFpag.ifPresent((y) -> {
						cbfpago.getSelectionModel().select(mutils.searchIdInCombo(cbfpago, y));
					});

				optStr = Optional.ofNullable(x.getIbanccc());
					optStr.ifPresentOrElse((y) -> {
						txdbanc.setText(y);
					}, () -> {
						txdbanc.setText("");
					});
					
				Optional<Descuento> optDesc = Optional.ofNullable(x.getDescuento());
					optDesc.ifPresent((y) -> {
						cbdesc.getSelectionModel().select(mutils.searchIdInCombo(cbdesc, y));
					});
					
				optBool = Optional.ofNullable(x.isFactura());
					optBool.ifPresent((y) -> {
						chfact.setSelected(y);
					});
					
				optBool = Optional.ofNullable(x.isFirmarFactura());
					optBool.ifPresent((y) -> {
						chfirfact.setSelected(y);
					});
				
				Optional<Agencia> optAge = Optional.ofNullable(x.getAgencia());
					optAge.ifPresent((y) -> {
						cbagencia.getSelectionModel().select(mutils.searchIdInCombo(cbagencia, y));
					});
						
				optStr = Optional.ofNullable(x.getReferencia());
					optStr.ifPresentOrElse((y) -> {
						txref.setText(y);
					}, () -> {
						txref.setText("");
					});
				
				optBool = Optional.ofNullable(x.isBaja());
					optBool.ifPresent((y) -> {
						chbaja.setSelected(y);
					});

				optDate = Optional.ofNullable(x.getFechaBaja());
					optDate.ifPresent((y) -> dpfbaja.setValue(mutils.obtainLocalDate(y)));

				Optional<MotivoBaja> optMbaj = Optional.ofNullable(x.getMotivoBaja());
					optMbaj.ifPresent((y) -> {
						cbmbaja.getSelectionModel().select(mutils.searchIdInCombo(cbmbaja, y));
					});
					
				optStr = Optional.ofNullable(x.getCargosJuntaDirectiva());
					optStr.ifPresentOrElse((y) -> {
						txcjdir.setText(y);
					}, () -> {
						txcjdir.setText("");
					});
					
				optBool = Optional.ofNullable(x.isJuntaDirectivaActual());
					optBool.ifPresent((y) -> {
						chjdiract.setSelected(y);
					});
					
				optStr = Optional.ofNullable(x.getAnotaciones());
					optStr.ifPresentOrElse((y) -> {
						txaranot.setText(y);
					}, () -> {
						txaranot.setText("");
					});		
			});

	}
	
	private void disableControls() {

		disableControl(dpfalta);
		dpfalta.getEditor().setStyle("-fx-opacity: 1;");
		
		disableControl(txcod);
		disableControl(cbmod);
		disableControl(cbmacceso);
		disableControl(txojs);
		disableControl(txnombre);
		disableControl(txapell);
		disableControl(txcifnif);
		disableControl(txardomic);
		disableControl(txcp);
		disableControl(txlocal);
		disableControl(cbprov);
		disableControl(cbpais);
		disableControl(cbzpost);
		disableControl(txemail);
		disableControl(txtelefono);
		disableControl(txtelmovil);
		disableControl(txctrab);
		disableControl(txatrab);
		disableControl(txtitul);
		disableControl(txcsep);
		disableControl(chlopd);
		disableControl(chldist);
		disableControl(txmarc);
		disableControl(txdbanc);
		disableControl(cbfpago);
		disableControl(cbdesc);
		disableControl(chfact);
		disableControl(chfirfact);
		disableControl(cbagencia);
		disableControl(txref);
		disableControl(chbaja);
		disableControl(txcjdir);
		disableControl(chjdiract);

		disableControl(dpfbaja);
		dpfalta.getEditor().setStyle("-fx-opacity: 1;");

		disableControl(cbmbaja);
		disableControl(txaranot);

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
	
	private boolean checkControls() {
		
		var checkwrapper = new Object(){ String errorstext = ""; boolean checks = true; };
		
		if (obtainText(txnombre).length() == 0) {
			checkwrapper.errorstext += "El nombre no puede quedar en blanco. ";
			checkwrapper.checks = false;  
		}
		
		if (obtainText(txcifnif).length() == 0) {
			checkwrapper.errorstext += "El CIF/NIF no puede quedar en blanco. ";
			checkwrapper.checks = false;  
		}
		
    	Optional<LocalDate> dtOpt = Optional.ofNullable(dpfalta.getValue());
			if (dtOpt.isEmpty()) {
				checkwrapper.errorstext += "Debe especificar una fecha válida. ";
				checkwrapper.checks = false;  
			}
		
		Optional<ModalidadSocio> msOpt = Optional.ofNullable(cbmod.getValue());
			if (msOpt.isEmpty()) {
				checkwrapper.errorstext += "Debe especificar una modalidad de socio. ";
				checkwrapper.checks = false;
			}
			
		Optional<ModoAcceso> maOpt = Optional.ofNullable(cbmacceso.getValue());
			if (maOpt.isEmpty()) {
				checkwrapper.errorstext += "Debe especificar un modo de acceso. ";
				checkwrapper.checks = false;
			}
			
		Optional<FormaPago> fpOpt = Optional.ofNullable(cbfpago.getValue());
			if (fpOpt.isEmpty()) {
				checkwrapper.errorstext += "Debe especificar una forma de pago. ";
				checkwrapper.checks = false;
			}
		
		lbmsg1.setText(checkwrapper.errorstext);
		
		return checkwrapper.checks;
	}
	
	private void disableButtons() {
		
		bfact.setDisable(true);
		breclam.setDisable(true);
		
	}
}
