package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.EstadoFactura;
import com.ammgroup.sep.repository.EstadoFacturaRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Component
public class EfactdtController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAO<EstadoFactura> efaccrud;
	
	@Autowired
	EstadoFacturaRepository efacRepo;

	@FXML
	private TextField tdesc;
	
	@FXML
	private CheckBox chedef;
	
	@FXML
	private CheckBox cherect;
	
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
			EstadoFactura ef;
			long cont;
			
		    switch (efaccrud.getAction()) {
	        case ADD :
	        	
	        	cont = efacRepo.countExistingEstadosFactura(mutils.obtainText(tdesc));
	        	
	        	if (cont == 0) {
	        		ef = new EstadoFactura(mutils.obtainText(tdesc), chedef.isSelected(), cherect.isSelected());
	        		efacRepo.save(ef);
	        	} else {
	        		lbmsg.setText("Existen " + String.valueOf(cont) + " estados de factura con esa descripción.");
	        		cform = false;
	        	}
	            break;
	
	        case EDIT:
	        	
	        	cont = efacRepo.countExistingEstadosFactura(mutils.obtainText(tdesc), efaccrud.getDao().getId());
	        	
	        	//Check if there are no coincidences
	        	if (cont == 0) {
	        		ef = efaccrud.getDao();
	        		ef.setDescripcion(mutils.obtainText(tdesc));
	        		ef.setEstadoPorDefecto(chedef.isSelected());
	        		ef.setEstadoRectificativas(cherect.isSelected());
	        		efacRepo.save(ef);
	        	} else {
	        		lbmsg.setText("Existen " + String.valueOf(cont) + " estados de factura con esa descripción.");
	        		cform = false;
	        	}
	        	
	            break;
	        
	        case DELETE:
	        	
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Confirma el BORRADO del estado de factura?", ButtonType.YES, ButtonType.NO);
	
		        // clicking X also means no
		        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
		        
		        if (ButtonType.YES.equals(result)) {
	        
		        	ef = efaccrud.getDao();
		        	efacRepo.delete(ef);
	        	
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

	    switch (efaccrud.getAction()) {
        case ADD :
        	
    		tdesc.setText("");
    		
            break;
            
        case EDIT:
        	
        	fillControls();
    		
    		showRelatedFacturas();
    		
    		break;
    		
        case DELETE:
    		
        	fillControls();
    		
        	disableControls();
    		
    		showRelatedFacturas();
    		
    		break;
    		
		case VIEW:
    		
			fillControls();
    		
			disableControls();
    		
    		showRelatedFacturas();
			
    		break;
    		
		default:
			
			break;
	    }

	}
    
	private void fillControls() {
		
		Optional<EstadoFactura> efacOpt = Optional.ofNullable(efaccrud.getDao());
			efacOpt.ifPresent((x) -> {
				
					Optional<String> optStr = Optional.ofNullable(x.getDescripcion());
						optStr.ifPresentOrElse((y) -> {
							tdesc.setText(y);
						}, () -> {
							tdesc.setText("");
						});
						
					Optional<Boolean> optBool = Optional.ofNullable(x.isEstadoPorDefecto());
						optBool.ifPresent((y) -> {
							chedef.setSelected(y);
						});
						
					optBool = Optional.ofNullable(x.isEstadoRectificativas());
						optBool.ifPresent((y) -> {
							cherect.setSelected(y);
						});
				});
		}
	
	private void disableControls() {
		
		disableControl(tdesc);
		disableControl(chedef);
		disableControl(cherect);
		
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

	private void showRelatedFacturas() {

		long cont = efacRepo.countRelatedFacturas(efaccrud.getDao().getId()); 
		lbmsg.setText("Utilizado en " + String.valueOf(cont) + " factura" + ((cont > 1)?"s" : ""));
		
		//Disable execute button
		if ((cont > 0) && efaccrud.getAction().equals(CrudAction.DELETE)) bexec.setDisable(true);
			
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
