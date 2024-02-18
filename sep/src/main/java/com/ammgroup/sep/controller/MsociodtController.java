package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.ModalidadSocio;
import com.ammgroup.sep.repository.ModalidadSocioRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Component
public class MsociodtController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAO<ModalidadSocio> msoccrud;

	@Autowired
	ModalidadSocioRepository msocRepo;

	@FXML
	private TextField tdesc;
	
	@FXML
	private TextField tcuota;

	@FXML
	private Label lbconceplen;
	
	@FXML
	private TextArea taconcep;
	
	@FXML
	private TextField ttpara;
	
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
			ModalidadSocio ms;
			long cont;
			
		    switch (msoccrud.getAction()) {
	        case ADD :
	        	
	        	cont = msocRepo.countExistingModalidadesSocios(mutils.obtainText(tdesc));
	        	
	        	if (cont == 0) {
	        		ms = new ModalidadSocio(mutils.obtainText(tdesc), mutils.getDoubleFromCurrency(mutils.obtainText(tcuota)), mutils.obtainText(taconcep), mutils.obtainText(ttpara));
	        		msocRepo.save(ms);
	        	} else {
	        		lbmsg.setText("Existen " + String.valueOf(cont) + " modalidades de socios con esa descripción.");
	        		cform = false;
	        	}
	            break;
	
	        case EDIT:
	        	
	        	cont = msocRepo.countExistingModalidadesSocios(tdesc.getText(), msoccrud.getDao().getId());
	        	
	        	//Check if there are no coincidences
	        	if (cont == 0) {        		
	        		ms = msoccrud.getDao();
	        		ms.setDescripcion(mutils.obtainText(tdesc));
	        		ms.setCuota(mutils.getDoubleFromCurrency(mutils.obtainText(tcuota)));
	        		ms.setConcepto(mutils.obtainText(taconcep));
	        		ms.setTextoPara(mutils.obtainText(ttpara));
	        		msocRepo.save(ms);
	        	} else {
	        		lbmsg.setText("Existen " + String.valueOf(cont) + " modalidades de socios con esa descripción.");
	        		cform = false;
	        	}
	        	
	            break;
	        
	        case DELETE:
	        	
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Confirma el BORRADO de la modalidad de socio?", ButtonType.YES, ButtonType.NO);
	
		        // clicking X also means no
		        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
		        
		        if (ButtonType.YES.equals(result)) {
	        
		        	ms = msoccrud.getDao();
		        	msocRepo.delete(ms);
	        	
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
		
	    //Configure TextField and TextArea controls
	    mutils.configureTextField(tdesc, 60);
	    mutils.configureTextField(ttpara, 95);
	    mutils.configureTextAreaWithLabel(taconcep, lbconceplen, 180);
	    
	    //Configure Currency controls
	    mutils.configCurrencyTextField(tcuota, 6);
		
	    switch (msoccrud.getAction()) {
        case ADD :
        	
    		tdesc.setText("");
    		tcuota.setText(mutils.getStringFromDouble(0D, mutils.CURRENCY_FORMAT));
    		ttpara.setText("");
    		taconcep.setText("");
    		
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

	private void closeForm() {
		
		// get a handle to the stage
		Stage stage = (Stage) bclose.getScene().getWindow();
		// do what you have to do
		stage.close();
		
	}

	private void fillControls() {
		
		Optional<ModalidadSocio> msocOpt = Optional.ofNullable(msoccrud.getDao());
			msocOpt.ifPresent((x) -> {
		
				mutils.fillTextControl(tdesc, x.getDescripcion());
				mutils.fillTextControl(taconcep, x.getConcepto());
				mutils.fillCurrencyControl(tcuota, x.getCuota());
				mutils.fillTextControl(ttpara, x.getTextoPara());
					
			});
	}

	private void disableControls() {
		
		disableControl(tdesc);
		disableControl(tcuota);
		disableControl(taconcep);
		disableControl(ttpara);
		
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

		long cont = msocRepo.countRelatedSocios(msoccrud.getDao().getId()); 
		lbmsg.setText("Utilizada en " + String.valueOf(cont) + " socios");
		
		//Disable execute button
		if ((cont > 0) && msoccrud.getAction().equals(CrudAction.DELETE)) bexec.setDisable(true);
			
	}
	
	private boolean checkControls() {
		
		var boolwrapper = new Object(){ boolean checks = true; };
		
		if (mutils.obtainText(tdesc).length() == 0) {
			lbmsg.setText("La descripción no puede quedar en blanco");
			boolwrapper.checks = false;  
		}
		
		return boolwrapper.checks;
	}
}
