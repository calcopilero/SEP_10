package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAOAgen;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.model.ZonaPostal;
import com.ammgroup.sep.repository.AgenciaRepository;
import com.ammgroup.sep.repository.PaisRepository;
import com.ammgroup.sep.repository.ProvinciaRepository;
import com.ammgroup.sep.repository.ZonaPostalRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

@Component
public class AgencdtController implements Initializable {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAOAgen<Agencia> agcrud;

	@Autowired
	AgenciaRepository agenciaRepository;
	
	@Autowired
	PaisRepository paisRepository;
	
	@Autowired
	ProvinciaRepository provRepository;
	
	@Autowired
	ZonaPostalRepository zpostRepository;

	@FXML
	private Label lbnomlen;
	int maxnomchars = 120;
	
	@FXML
	private TextArea txarnombre;
	
	@FXML
	private CheckBox chactiva;
	
	@FXML
	private TextField txcifnif;
	
	@FXML
	private Label lbdomlen;
	int maxdomchars = 100;
	
	@FXML
	private TextArea txardomic;
	
	@FXML
	private TextField txcp;

	@FXML
	private Label lbloclen;
	int maxlocchars = 70;
	
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
	private TextField txpcont;
	
	@FXML
	private Label lbdafactlen;
	int maxdafactchars = 250;
	
	@FXML
	private TextArea txardafact;
	
	@FXML
	private Label lbanotlen;
	int maxanotchars = 500;
	
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
	private Button bclose;
	
	@FXML
    void bexecOnAction(ActionEvent event) {

		boolean cform = true;
		Agencia a;
		long cont1;
		//long cont2;
		
	    switch (agcrud.getAction()) {
        case ADD :
        	
        	if (checkControls()) {
        	
	        	cont1 = agenciaRepository.countExistingAgenciasByNombre(txarnombre.getText());
	        	//cont2 = agenciaRepository.countExistingAgenciasByCifnif(txcifnif.getText());
	        	
	        	if (cont1 == 0) {
	
	        		Agencia age = new Agencia();
	        		
	        		saveDataToAgencia(age);
	        		
	        		agenciaRepository.save(age);
	        		
	        	} else {
	        		
	        		lbmsg1.setText("Existen " + String.valueOf(cont1) + " agencias con ese nombre.");
	        		//lbmsg2.setText("Existen " + String.valueOf(cont2) + " agencias con ese CIF/NIF.");
	        		
	        		cform = false;
	        	}
	        	
        	} else {
        	
        		cform = false;
        	}
        	
            break;

        case EDIT:
        	
        	if (checkControls()) {

        		cont1 = agenciaRepository.countExistingAgenciasByNombre(txarnombre.getText(), agcrud.getDao().getId());
        		//cont2 = agenciaRepository.countExistingAgenciasByCifnif(txcifnif.getText(), agcrud.getDao().getId());
        	
	        	//Check if there are no coincidences of there's one with the previous text is the same in the text box
	        	if (cont1 == 0) {
	
	        		a = agcrud.getDao();
	        		
	        		saveDataToAgencia(a);
	
	        		agenciaRepository.save(a);
	
	        	} else {
	        	
	        		lbmsg1.setText("Existen " + String.valueOf(cont1) + " agencias con ese nombre.");
	        		//lbmsg2.setText("Existen " + String.valueOf(cont2) + " agencias con ese CIF/NIF.");
	        		cform = false;
	        	}

        	} else {
            	
        		cform = false;
        	}

            break;
        
        case DELETE:
        
        	a = agcrud.getDao();
        	agenciaRepository.delete(a);
        	
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
		
		Optional<String> strOpt = Optional.ofNullable(agcrud.getDao().getNombre());
	    	strOpt.ifPresent((x) -> {
	    		strwrapper.str = x;
	    	});
    	
		try {
			mutils.loadForm("facagesocl.fxml", "Facturas de la agencia " + strwrapper.str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
    void breclamOnAction(ActionEvent event) {
		
		//To set the title of the form
		var strwrapper = new Object(){ String str = ""; };
		
		Optional<String> strOpt = Optional.ofNullable(agcrud.getDao().getNombre());
	    	strOpt.ifPresent((x) -> {
	    		strwrapper.str = x;
	    	});
    	
		try {
			mutils.loadForm("reclagesocl.fxml", "Reclamaciones de la agencia " + strwrapper.str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveDataToAgencia(Agencia ag) {
		
		ag.setNombre(obtainText(txarnombre));
		ag.setActiva(chactiva.isSelected());
		ag.setCifnif(obtainText(txcifnif));
		ag.setDomicilio(obtainText(txardomic));
		ag.setCp(obtainText(txcp));
		ag.setLocalidad(obtainText(txlocal));
		ag.setProvincia((Provincia) cbprov.getSelectionModel().getSelectedItem());
		ag.setPais((Pais) cbpais.getSelectionModel().getSelectedItem());
		ag.setZonaPostal((ZonaPostal) cbzpost.getSelectionModel().getSelectedItem());
		ag.setEmail(obtainText(txemail));
		ag.setTelefono(obtainText(txtelefono));
		ag.setPersonaContacto(obtainText(txpcont));
		ag.setDatosAdicionalesFactura(obtainText(txardafact));
		ag.setAnotaciones(obtainText(txaranot));
		
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
		
		//Populate the combos
		cbprov.setItems(FXCollections.observableList(provRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbprov.getItems().add(null);
		cbpais.setItems(FXCollections.observableList(paisRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbpais.getItems().add(null);
		cbzpost.setItems(FXCollections.observableList(zpostRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbzpost.getItems().add(null);
		
	    final String ipattern = mutils.INTEGER_PATTERN;
	    final String cppattern = mutils.CP_PATTERN;
	    
		//Setting a filter to allow only numbers
		UnaryOperator<Change> integerFilter = (change -> {
		    String newText = change.getControlNewText();

		    if (newText.matches(ipattern)) {
		        return change;
		    }
		    return null;
		});
	    
		//Setting a filter to allow only numbers (the first number could be a 0)
		UnaryOperator<Change> cpFilter = (change -> {
		    String newText = change.getControlNewText();

		    if (newText.matches(cppattern)) {
		        return change;
		    }
		    return null;
		});
		
		txcp.setTextFormatter(new TextFormatter<String>(cpFilter));
		txtelefono.setTextFormatter(new TextFormatter<String>(integerFilter));

	    switch (agcrud.getAction()) {
        case ADD :
        	
    		txarnombre.setText("");
    		txcifnif.setText("");
    		txardomic.setText("");
    		txcp.setText("");
    		txlocal.setText("");
    		txemail.setText("");
    		txtelefono.setText("");
    		txpcont.setText("");
    		txaranot.setText("");
    		
            break;
            
        case EDIT:
        	
    		fillControls();
        	
    		showRelatedEntities();
    		
    		break;
    		
        case DELETE:
    		
    		fillControls();

    		disableFormControls();
    		
    		showRelatedEntities();
            
    		break;
    		
		case VIEW:
    		
    		fillControls();

			disableFormControls();

			showRelatedEntities();
			
    		break;
    		
		default:
			
			break;
	    }
	    
	    //Setting the maximum number of characters
	    //txnombre.addEventFilter(KeyEvent.KEY_TYPED, maxLength(95));
	    txcifnif.addEventFilter(KeyEvent.KEY_TYPED, maxLength(25));
	    txcp.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
	    //txlocal.addEventFilter(KeyEvent.KEY_TYPED, maxLength(70));
	    txemail.addEventFilter(KeyEvent.KEY_TYPED, maxLength(80));
	    txtelefono.addEventFilter(KeyEvent.KEY_TYPED, maxLength(12));
	    txpcont.addEventFilter(KeyEvent.KEY_TYPED, maxLength(70));
	    
	    //Setting the maximum number of characters of TextArea (another way)
//	    txardomic.setTextFormatter(new TextFormatter<String>(change -> 
//	    	change.getControlNewText().length() <= 100 ? change : null));
//	    txaranot.setTextFormatter(new TextFormatter<String>(change -> 
//    		change.getControlNewText().length() <= 250 ? change : null));
	    
		//To check nombre maximum number of chars and show the number of chars
		UnaryOperator<Change> nomFilter = (change -> {
			
			if (change.getControlNewText().length() <= maxnomchars) {
		    	
				showNombreChars(change.getControlNewText().length(), maxnomchars);
		    	
		        return change;
		    }
		    return null;
		});
		txarnombre.setTextFormatter(new TextFormatter<String>(nomFilter));
		
		//To check domicilio maximum number of chars and show the number of chars
		UnaryOperator<Change> domicFilter = (change -> {
			
			if (change.getControlNewText().length() <= maxdomchars) {
		    	
				showDomicilioChars(change.getControlNewText().length(), maxdomchars);
		    	
		        return change;
		    }
		    return null;
		});
		txardomic.setTextFormatter(new TextFormatter<String>(domicFilter));
	    
		//To check localidad maximum number of chars and show the number of chars
		UnaryOperator<Change> localFilter = (change -> {
			
			if (change.getControlNewText().length() <= maxlocchars) {
		    	
				showLocalidadChars(change.getControlNewText().length(), maxlocchars);
		    	
		        return change;
		    }
		    return null;
		});
		txlocal.setTextFormatter(new TextFormatter<String>(localFilter));
	    
		//To check datos adicionales facturacion maximum number of chars and show the number of chars
		UnaryOperator<Change> dafactFilter = (change -> {
			
			if (change.getControlNewText().length() <= maxdafactchars) {
		    	
				showDafactChars(change.getControlNewText().length(), maxdafactchars);
		    	
		        return change;
		    }
		    return null;
		});
		txardafact.setTextFormatter(new TextFormatter<String>(dafactFilter));
		
		//To check anotaciones maximum number of chars and show the number of chars
		UnaryOperator<Change> anotFilter = (change -> {
			
			if (change.getControlNewText().length() <= maxanotchars) {
		    	
				showAnotacionesChars(change.getControlNewText().length(), maxanotchars);
		    	
		        return change;
		    }
		    return null;
		});
		txaranot.setTextFormatter(new TextFormatter<String>(anotFilter));

	}
	
	private void showRelatedEntities() {
		
		long rsoc = agenciaRepository.countRelatedSocios(agcrud.getDao().getId()); 
		lbmsg1.setText("Utilizada en " + String.valueOf(rsoc) + " socios");
		
		long rfac = agenciaRepository.countRelatedFacturas(agcrud.getDao().getId()); 
		lbmsg2.setText("Utilizada en " + String.valueOf(rfac) + " facturas");

		//Disable execute button
		if (((rsoc > 0)  || (rfac > 0)) && agcrud.getAction().equals(CrudAction.DELETE)) bexec.setDisable(true);
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
		
		Optional<Agencia> ageOpt = Optional.ofNullable(agcrud.getDao());
			ageOpt.ifPresent((x) -> {
				
				Optional<String> optStr = Optional.ofNullable(x.getNombre());
					optStr.ifPresentOrElse((y) -> {
						txarnombre.setText(y);
					}, () -> {
						txarnombre.setText("");
					});

				showNombreChars(txarnombre.getLength(), maxnomchars);
				
				Optional<Boolean> optBool = Optional.ofNullable(x.isActiva());
					optBool.ifPresent((y) -> {
						chactiva.setSelected(y);
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

				showDomicilioChars(txardomic.getLength(), maxdomchars);
				
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

				showLocalidadChars(txlocal.getLength(), maxlocchars);
				
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

				optStr = Optional.ofNullable(x.getPersonaContacto());
					optStr.ifPresentOrElse((y) -> {
						txpcont.setText(y);
					}, () -> {
						txpcont.setText("");
					});	
				
				optStr = Optional.ofNullable(x.getDatosAdicionalesFactura());
					optStr.ifPresentOrElse((y) -> {
						txardafact.setText(y);
					}, () -> {
						txardafact.setText("");
					});
				
				showDafactChars(txardafact.getLength(), maxdafactchars);
				
				optStr = Optional.ofNullable(x.getAnotaciones());
					optStr.ifPresentOrElse((y) -> {
						txaranot.setText(y);
					}, () -> {
						txaranot.setText("");
					});	
				
				showAnotacionesChars(txaranot.getLength(), maxanotchars);
			});
	}
	
	private void disableFormControls() {
		
		//txnombre.setDisable(true);
		//txnombre.setStyle("-fx-background-color: yellow;");
		//txnombre.setStyle("-fx-opacity: 1.0;");
		disableControl(txarnombre);
		disableControl(chactiva);
		disableControl(txcifnif);
		disableControl(txardomic);
		disableControl(txcp);
		disableControl(txlocal);
		disableControl(cbprov);
		disableControl(cbpais);
		disableControl(cbzpost);
		disableControl(txemail);
		disableControl(txtelefono);
		disableControl(txpcont);
		disableControl(txardafact);
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
		
		if (obtainText(txarnombre).length() == 0) {
			checkwrapper.errorstext += "El nombre no puede quedar en blanco. ";
			checkwrapper.checks = false;  
		}
		
		if (obtainText(txcifnif).length() == 0) {
			checkwrapper.errorstext += "El NIF/VAT no puede quedar en blanco. ";
			checkwrapper.checks = false;  
		}
		
		lbmsg1.setText(checkwrapper.errorstext);
		
		return checkwrapper.checks;
	}

	private void showNombreChars(int numchars, int maxchars) {
		
		lbnomlen.setText("(" + numchars + "/" + maxchars + " caracteres)");
	}
	
	private void showDomicilioChars(int numchars, int maxchars) {
		
		lbdomlen.setText("(" + numchars + "/" + maxchars + " caracteres)");
	}
	
	private void showLocalidadChars(int numchars, int maxchars) {
		
		lbloclen.setText("(" + numchars + "/" + maxchars + " caracteres)");
	}
	
	private void showDafactChars(int numchars, int maxchars) {
		
		lbdafactlen.setText("(" + numchars + "/" + maxchars + " caracteres)");
	}
	
	private void showAnotacionesChars(int numchars, int maxchars) {
		
		lbanotlen.setText("(" + numchars + "/" + maxchars + " caracteres)");
	}
}
