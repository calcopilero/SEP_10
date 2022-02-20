package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAOSoc;
import com.ammgroup.sep.model.ModalidadSocio;
import com.ammgroup.sep.model.Socio;
import com.ammgroup.sep.repository.ModalidadSocioRepository;
import com.ammgroup.sep.repository.SocioRepository;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

@Component
public class CamodController implements Initializable {
	
	@Autowired
	private CrudDAOSoc<Socio> socrud;
	
	@Autowired
	ModalidadSocioRepository msocRepo;
	
	@Autowired
	SocioRepository socRepo;
	
	@FXML
	private ComboBox<ModalidadSocio> cbmod;
	
	@FXML
	private Label lbmsg;

	@FXML
	private Button bexec;

	@FXML
	private Button bclose;
	
	@FXML
    void bexecOnAction(ActionEvent event) {
		
		if (checkControls()) {
			
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Confirma el cambio de modallidad de los " + socrud.getItemsList().size() + " socios seleccionados??", ButtonType.YES, ButtonType.NO);

	        // clicking X also means no
	        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
	        
	        if (ButtonType.YES.equals(result)) {
        
				Optional<List<Socio>> optLsoc = Optional.ofNullable(socrud.getItemsList());
					optLsoc.ifPresentOrElse((x) -> {
						
						int nsoc = x.size();
						ModalidadSocio mod = cbmod.getValue();
						
						new Thread(new Runnable() {
							
							int cont = 0;

						    @Override 
						    public void run() {
						    	
						    	//Update the modalidad of all socios in list
								for (Socio soc : x) {
									soc.setModalidad(mod);
									socRepo.save(soc);
									
									cont++;
									
							        Platform.runLater(new Runnable() {
							            @Override public void run() {
							            	lbmsg.setText("Procesados " + cont + "/" + nsoc + " socios.");
							            }
							        });
							    }
						    }
						}).start();
					
					}, () -> {
						
						lbmsg.setText("No se encuentran socios para cambiar de modalidad de socio.");
						
					});
	        }
		}
	}
	
	@FXML
    void bcloseOnAction(ActionEvent event) {

		closeForm();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//Populate the combo of modalidades de socio
		cbmod.setItems(FXCollections.observableList(msocRepo.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbmod.getItems().add(null);

	}

	private boolean checkControls() {
		
		var checkwrapper = new Object(){ String errorstext = ""; boolean checks = true; };
		
		Optional<ModalidadSocio> msOpt = Optional.ofNullable(cbmod.getValue());
			if (msOpt.isEmpty()) {
				checkwrapper.errorstext += "Debe especificar una modalidad de socio. ";
				checkwrapper.checks = false;
			}
	
		lbmsg.setText(checkwrapper.errorstext);
		
		return checkwrapper.checks;
	}
	
	private void closeForm() {
		
		// get a handle to the stage
		Stage stage = (Stage) bclose.getScene().getWindow();
		// do what you have to do
		stage.close();
	}
}
