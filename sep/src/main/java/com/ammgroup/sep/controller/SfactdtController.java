package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.SerieFacturas;
import com.ammgroup.sep.model.TipoIVA;
import com.ammgroup.sep.repository.SerieFacturasRepository;
import com.ammgroup.sep.repository.TipoIVARepository;
import com.ammgroup.sep.service.ModuloUtilidades;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Component
public class SfactdtController implements Initializable {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAO<SerieFacturas> sfaccrud;
	
	@Autowired
	SerieFacturasRepository efacRepo;
	
	@Autowired
	TipoIVARepository tivaRepo;

	@FXML
	private TextField tdesc;
	
	@FXML
	private CheckBox chfauto;
	
	@FXML
	private TextField tininum;
	
	@FXML
	private CheckBox chfrect;
	
	@FXML
	private TextField ttfrect;
	
	@FXML
	private TextField ttpara;
	
	@FXML
	private ComboBox<TipoIVA> cbtiva;
	
	@FXML
	private CheckBox chfprof;
	
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
			SerieFacturas sf;
			long cont1, cont2, cont3, cont4;
			
		    switch (sfaccrud.getAction()) {
	        case ADD :
	        	
	        	cont1 = efacRepo.countExistingSeriesFacturas(mutils.obtainText(tdesc));
	        	
	        	if (chfauto.isSelected()) {
	        		cont2 = efacRepo.countExistingSeriesAutomaticas();
	        	} else {
	        		cont2 = 0;
	        	}
	        	
	        	if (chfrect.isSelected()) {
	        		cont3 = efacRepo.countExistingSeriesRectificativas();
	        	} else {
	        		cont3 = 0;
	        	}
	        	
	        	if (chfprof.isSelected()) {
	        		cont4 = efacRepo.countExistingSeriesProforma();
	        	} else {
	        		cont4 = 0;
	        	}
	        	
	        	if ((cont1 == 0) && (cont2 == 0) && (cont3 == 0) && (cont4 == 0)) {
	        		sf = new SerieFacturas(mutils.obtainText(tdesc), mutils.obtainText(tininum), chfrect.isSelected(), mutils.obtainText(ttfrect), chfauto.isSelected(), mutils.obtainText(ttpara), chfprof.isSelected(), cbtiva.getValue());
	        		efacRepo.save(sf);
	        	} else {
	        		if (cont1 > 0) lbmsg.setText("Existen " + String.valueOf(cont1) + " serie" + ((cont1 > 1)?"s" : "") + " de facturas con esa descripción.");
	        		if ((cont2 > 0) && chfauto.isSelected()) lbmsg.setText("Existen " + String.valueOf(cont2) + " serie" + ((cont2 > 1)?"s" : "") + " de facturas de fact. automática.");
	        		if ((cont3 > 0) && chfrect.isSelected()) lbmsg.setText("Existen " + String.valueOf(cont3) + " serie" + ((cont3 > 1)?"s" : "") + " de facturas rectificativas.");
	        		if ((cont4 > 0) && chfprof.isSelected()) lbmsg.setText("Existen " + String.valueOf(cont4) + " serie" + ((cont4 > 1)?"s" : "") + " de facturas proforma.");
	        		cform = false;
	        	}
	            break;
	
	        case EDIT:
	        	
	        	cont1 = efacRepo.countExistingSeriesFacturas(mutils.obtainText(tdesc), sfaccrud.getDao().getId());
	        	
	        	if (chfauto.isSelected()) {
	        		cont2 = efacRepo.countExistingSeriesAutomaticas(sfaccrud.getDao().getId());
	        	} else {
	        		cont2 = 0;
	        	}
	        	
	        	if (chfrect.isSelected()) {
	        		cont3 = efacRepo.countExistingSeriesRectificativas(sfaccrud.getDao().getId());
	        	} else {
	        		cont3 = 0;
	        	}
	        	
	        	if (chfprof.isSelected()) {
	        		cont4 = efacRepo.countExistingSeriesProforma(sfaccrud.getDao().getId());
	        	} else {
	        		cont4 = 0;
	        	}
	        	
	        	//Check if there are no coincidences
	        	if ((cont1 == 0) && (cont2 == 0) && (cont3 == 0) && (cont4 == 0)) {
	        		sf = sfaccrud.getDao();
	        		sf.setDescripcion(mutils.obtainText(tdesc));
	        		sf.setAutomatica(chfauto.isSelected());
	        		sf.setTextoInicioNumeracion(mutils.obtainText(tininum));
	        		sf.setRectificativas(chfrect.isSelected());
	        		sf.setTextoRectificativa(mutils.obtainText(ttfrect));
	        		sf.setTextoPara(mutils.obtainText(ttpara));
	        		sf.setTipoIVA(cbtiva.getValue());
	        		sf.setFacturasProforma(chfprof.isSelected());
	        		efacRepo.save(sf);
	        	} else {
	        		if (cont1 > 0) lbmsg.setText("Existen " + String.valueOf(cont1) + " serie" + ((cont1 > 1)?"s" : "") + " de facturas con esa descripción.");
	        		if ((cont2 > 0) && chfauto.isSelected()) lbmsg.setText("Existen " + String.valueOf(cont2) + " serie" + ((cont1 > 1)?"s" : "") + " de facturas de fact. automática.");
	        		if ((cont3 > 0) && chfrect.isSelected()) lbmsg.setText("Existen " + String.valueOf(cont3) + " serie" + ((cont1 > 1)?"s" : "") + " de facturas rectificativas.");
	        		if ((cont4 > 0) && chfprof.isSelected()) lbmsg.setText("Existen " + String.valueOf(cont3) + " serie" + ((cont1 > 1)?"s" : "") + " de facturas proforma.");
	        		cform = false;
	        	}
	        	
	            break;
	        
	        case DELETE:
	        	
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Confirma el BORRADO de la serie de facturas?", ButtonType.YES, ButtonType.NO);
	
		        // clicking X also means no
		        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
		        
		        if (ButtonType.YES.equals(result)) {
	        
		        	sf = sfaccrud.getDao();
		        	efacRepo.delete(sf);
	        	
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
		
		//Populate the combos
		cbtiva.setItems(FXCollections.observableList(tivaRepo.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbtiva.getItems().add(null);
		
	    //Configuring TextFields
		mutils.configureTextField(tdesc, 60);
		mutils.configureTextField(tininum, 12);
		mutils.configureTextField(ttfrect, 70);
		mutils.configureTextField(ttpara, 70);

	    switch (sfaccrud.getAction()) {
        case ADD :
        	
    		tdesc.setText("");
    		tininum.setText("");
    		
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
		
		Optional<SerieFacturas> sfacOpt = Optional.ofNullable(sfaccrud.getDao());
			sfacOpt.ifPresent((x) -> {
				
					mutils.fillTextControl(tdesc, x.getDescripcion());
					mutils.fillTextControl(tininum, x.getTextoInicioNumeracion());
					mutils.fillTextControl(ttfrect, x.getTextoRectificativa());
					mutils.fillTextControl(ttpara, x.getTextoPara());
					
					mutils.fillCheckBoxControl(chfauto, x.isAutomatica());
					mutils.fillCheckBoxControl(chfrect, x.isRectificativas());
					mutils.fillCheckBoxControl(chfprof, x.isFacturasProforma());
						
					Optional<TipoIVA> optTiva = Optional.ofNullable(x.getTipoIVA());
						optTiva.ifPresent((y) -> {
							cbtiva.getSelectionModel().select(mutils.searchIdInCombo(cbtiva, y));
						});

				});
		}
	
	private void disableControls() {
		
		disableControl(tdesc);
		disableControl(tininum);
		disableControl(tdesc);
		disableControl(chfrect);
		disableControl(ttfrect);
		disableControl(ttpara);
		disableControl(cbtiva);
		disableControl(chfprof);
		
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

		long cont = efacRepo.countRelatedFacturas(sfaccrud.getDao().getId()); 
		lbmsg.setText("Utilizada en " + String.valueOf(cont) + " factura" + ((cont > 1)?"s" : ""));
		
		//Disable execute button
		if ((cont > 0) && sfaccrud.getAction().equals(CrudAction.DELETE)) bexec.setDisable(true);
			
	}
	
	private void closeForm() {
		
		// get a handle to the stage
		Stage stage = (Stage) bclose.getScene().getWindow();
		// do what you have to do
		stage.close();
		
	}
	
	private boolean checkControls() {
		
		boolean checks = true;
		
		if (mutils.obtainText(tininum).length() == 0) {
			lbmsg.setText("El texto de inicio de numeración no puede quedar en blanco");
			checks = false;  
		}
		if (mutils.obtainText(tdesc).length() == 0) {
			lbmsg.setText("La descripción no puede quedar en blanco");
			checks = false;  
		}
		
		return checks;
	}
}
