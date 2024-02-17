package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.ModoAcceso;
import com.ammgroup.sep.repository.ModoAccesoRepository;
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
public class MaccesdtController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAO<ModoAcceso> macrud;
	
	@Autowired
	ModoAccesoRepository maRepo;

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
			ModoAcceso ma;
			long cont;
			
		    switch (macrud.getAction()) {
	        case ADD :
	        	
	        	cont = maRepo.countExistingModosAcceso(mutils.obtainText(tdesc));
	        	
	        	if (cont == 0) {
	        		ma = new ModoAcceso(mutils.obtainText(tdesc));
	        		maRepo.save(ma);
	        	} else {
	        		lbmsg.setText("Existen " + String.valueOf(cont) + " modos de acceso con esa descripción.");
	        		cform = false;
	        	}
	            break;
	
	        case EDIT:
	        	
	        	cont = maRepo.countExistingModosAcceso(mutils.obtainText(tdesc), macrud.getDao().getId());
	        	
	        	//Check if there are no coincidences
	        	if (cont == 0) {
	        		ma = macrud.getDao();
	        		ma.setDescripcion(mutils.obtainText(tdesc));
	        		maRepo.save(ma);
	        	} else {
	        		lbmsg.setText("Existen " + String.valueOf(cont) + " modos de acceso con esa descripción.");
	        		cform = false;
	        	}
	        	
	            break;
	        
	        case DELETE:
	        	
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Confirma el BORRADO del modo de acceso?", ButtonType.YES, ButtonType.NO);
	
		        // clicking X also means no
		        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
		        
		        if (ButtonType.YES.equals(result)) {
	        
		        	ma = macrud.getDao();
		        	maRepo.delete(ma);
	        	
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

	    switch (macrud.getAction()) {
        case ADD :
        	
    		tdesc.setText("");
    		
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
		
		Optional<ModoAcceso> maccOpt = Optional.ofNullable(macrud.getDao());
			maccOpt.ifPresent((x) -> {
			
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

	private void showRelatedSocios() {

		long cont = maRepo.countRelatedSocios(macrud.getDao().getId()); 
		lbmsg.setText("Utilizado en " + String.valueOf(cont) + " socio" + ((cont > 1)?"s" : ""));
		
		//Disable execute button
		if ((cont > 0) && macrud.getAction().equals(CrudAction.DELETE)) bexec.setDisable(true);
			
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
