package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.FormaPago;
import com.ammgroup.sep.repository.FormaPagoRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Component
public class FpagodtController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAO<FormaPago> fpagcrud;
	
	@Autowired
	FormaPagoRepository fpagRepo;

	@FXML
	private TextField tdesc;
	
	@FXML
	private TextField ttfact;
	
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
			FormaPago fp;
			long cont;
			
		    switch (fpagcrud.getAction()) {
	        case ADD :
	        	
	        	cont = fpagRepo.countExistingFormasPago(mutils.obtainText(tdesc));
	        	
	        	if (cont == 0) {
	        		fp = new FormaPago(mutils.obtainText(tdesc), mutils.obtainText(ttfact));
	        		fpagRepo.save(fp);
	        	} else {
	        		lbmsg.setText("Existen " + String.valueOf(cont) + " formas de pago con esa descripción.");
	        		cform = false;
	        	}
	            break;
	
	        case EDIT:
	        	
	        	cont = fpagRepo.countExistingFormasPago(mutils.obtainText(tdesc), fpagcrud.getDao().getId());
	        	
	        	//Check if there are no coincidences
	        	if (cont == 0) {
	        		fp = fpagcrud.getDao();
	        		fp.setDescripcion(mutils.obtainText(tdesc));
	        		fp.setTextoFactura(mutils.obtainText(ttfact));
	        		fpagRepo.save(fp);
	        	} else {
	        		lbmsg.setText("Existen " + String.valueOf(cont) + " formas de pago con esa descripción.");
	        		cform = false;
	        	}
	        	
	            break;
	        
	        case DELETE:
	        	
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Confirma el BORRADO de la forma de pago?", ButtonType.YES, ButtonType.NO);
	
		        // clicking X also means no
		        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
		        
		        if (ButtonType.YES.equals(result)) {
	        
		        	fp = fpagcrud.getDao();
		        	fpagRepo.delete(fp);
	        	
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
		
	    //Configuring TextFields
		mutils.configureTextField(tdesc, 60);
		mutils.configureTextField(ttfact, 150);

	    switch (fpagcrud.getAction()) {
        case ADD :
        	
    		tdesc.setText("");
    		ttfact.setText("");
    		
            break;
            
        case EDIT:
        	
        	fillControls();
    		
    		showRelatedSocios();
    		
    		break;
    		
        case DELETE:
    		
        	fillControls();
    		
        	disableControls();
    		
    		showRelatedSocios();
    		
    		break;
    		
		case VIEW:
    		
			fillControls();
    		
			disableControls();
    		
    		showRelatedSocios();
			
    		break;
    		
		default:
			
			break;
	    }
	}
	
	private void fillControls() {
		
		Optional<FormaPago> fpagOpt = Optional.ofNullable(fpagcrud.getDao());
			fpagOpt.ifPresent((x) -> {
			
				mutils.fillTextControl(tdesc, x.getDescripcion());
				mutils.fillTextControl(ttfact, x.getTextoFactura());
			
			});
	}
	
	private void disableControls() {
		
		disableControl(tdesc);
		disableControl(ttfact);
		
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

	private void showRelatedSocios() {

		long cont = fpagRepo.countRelatedSocios(fpagcrud.getDao().getId()); 
		lbmsg.setText("Utilizada en " + String.valueOf(cont) + " socios");
		
		//Disable execute button
		if ((cont > 0) && fpagcrud.getAction().equals(CrudAction.DELETE)) bexec.setDisable(true);
			
	}
	
	private void closeForm() {
		
		// get a handle to the stage
		Stage stage = (Stage) bclose.getScene().getWindow();
		// do what you have to do
		stage.close();
		
	}
	
	private boolean checkControls() {
		
		boolean checks = true;
		
		if (mutils.obtainText(tdesc).length() == 0) {
			lbmsg.setText("La descripción no puede quedar en blanco");
			checks = false;  
		}
		
		return checks;
	}
}
