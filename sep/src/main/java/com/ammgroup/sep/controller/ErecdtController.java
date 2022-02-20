package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.EstadoReclamacion;
import com.ammgroup.sep.repository.EstadoReclamacionRepository;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

@Component
public class ErecdtController implements Initializable {
	
	@Autowired
	private CrudDAO<EstadoReclamacion> ereccrud;
	
	@Autowired
	EstadoReclamacionRepository erecRepo;

	@FXML
	private TextField tdesc;
	
	@FXML
	private Label lbmsg;
	
	@FXML
	private Button bexec;
	
	@FXML
	private Button bclose;
	
	@FXML
    void bexecOnAction(ActionEvent event) {
		
		if (checkControls()) {

			boolean cform = true;
			EstadoReclamacion er;
			long cont;
			
		    switch (ereccrud.getAction()) {
	        case ADD :
	        	
	        	cont = erecRepo.countExistingEstadosReclamaciones(obtainText(tdesc));
	        	
	        	if (cont == 0) {
	        		er = new EstadoReclamacion(obtainText(tdesc));
	        		erecRepo.save(er);
	        	} else {
	        		lbmsg.setText("Existen " + String.valueOf(cont) + " estados de reclamaciones con esa descripción.");
	        		cform = false;
	        	}
	            break;
	
	        case EDIT:
	        	
	        	cont = erecRepo.countExistingEstadosReclamaciones(obtainText(tdesc), ereccrud.getDao().getId());
	        	
	        	//Check if there are no coincidences
	        	if (cont == 0) {
	        		er = ereccrud.getDao();
	        		er.setDescripcion(obtainText(tdesc));
	        		erecRepo.save(er);
	        	} else {
	        		lbmsg.setText("Existen " + String.valueOf(cont) + " estados de reclamaciones con esa descripción.");
	        		cform = false;
	        	}
	        	
	            break;
	        
	        case DELETE:
	        	
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Confirma el BORRADO del estado de reclamación?", ButtonType.YES, ButtonType.NO);
	
		        // clicking X also means no
		        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
		        
		        if (ButtonType.YES.equals(result)) {
	        
		        	er = ereccrud.getDao();
		        	erecRepo.delete(er);
	        	
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
	}
	
    @FXML
    void bcloseOnAction(ActionEvent event) {

    	closeForm();

    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	    //Setting the maximum number of characters of TextField
	    tdesc.addEventFilter(KeyEvent.KEY_TYPED, maxLength(60));

	    switch (ereccrud.getAction()) {
        case ADD :
        	
    		tdesc.setText("");
    		
            break;
            
        case EDIT:
        	
        	fillControls();
    		
    		showRelatedReclamaciones();
    		
    		break;
    		
        case DELETE:
    		
        	fillControls();
    		
        	disableControls();
    		
    		showRelatedReclamaciones();
    		
    		break;
    		
		case VIEW:
    		
			fillControls();
    		
			disableControls();
    		
    		showRelatedReclamaciones();
			
    		break;
    		
		default:
			
			break;
	    }

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
	
    private String obtainText(TextField tx) {
    	
    	//To check null values we use optional and to avoid the block scope of variables we use a wrapper
    	var strwrapper = new Object(){ String str = ""; };
    	
		Optional<String> strOpt = Optional.ofNullable(tx.getText());
	    	strOpt.ifPresent((x) -> {
	    		strwrapper.str = tx.getText();
	    	});
    		
    	return strwrapper.str;
    }
    
	private void fillControls() {
		
		Optional<EstadoReclamacion> erecOpt = Optional.ofNullable(ereccrud.getDao());
			erecOpt.ifPresent((x) -> {
			
				Optional<String> optStr = Optional.ofNullable(x.getDescripcion());
					optStr.ifPresentOrElse((y) -> {
						tdesc.setText(y);
					}, () -> {
						tdesc.setText("");
					});
			});
	}
	
	private void disableControls() {
		
		disableControl(tdesc);
		
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

	private void showRelatedReclamaciones() {

		long cont = erecRepo.countRelatedReclamaciones(ereccrud.getDao().getId()); 
		lbmsg.setText("Utilizado en " + String.valueOf(cont) + " reclamaci"  + ((cont > 1)?"ones" : "ón"));
		
		//Disable execute button
		if ((cont > 0) && ereccrud.getAction().equals(CrudAction.DELETE)) bexec.setDisable(true);
			
	}
	
	private void closeForm() {
		
		// get a handle to the stage
		Stage stage = (Stage) bclose.getScene().getWindow();
		// do what you have to do
		stage.close();
		
	}

	private boolean checkControls() {
		
		boolean checks = true;
		
		if (obtainText(tdesc).length() == 0) {
			lbmsg.setText("La descripción no puede quedar en blanco");
			checks = false;  
		}
		
		return checks;
	}
}
