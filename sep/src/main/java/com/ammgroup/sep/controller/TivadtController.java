package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.TipoIVA;
import com.ammgroup.sep.repository.TipoIVARepository;
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
public class TivadtController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAO<TipoIVA> tivacrud;

	@Autowired
	TipoIVARepository tivaRepo;

	@FXML
	private TextField tdesc;
	
	@FXML
	private TextField tporc;
	
	@FXML
	private TextField ttfact;
	
	@FXML
	private Label lbmsg1;
	
	@FXML
	private Label lbmsg2;
	
	@FXML
	private Button bexec;
	
	@FXML
	private Button bclose;
	
	@FXML
    void bexecOnAction(ActionEvent event) {
		
		if (checkControls()) {

			boolean cform = true;
			TipoIVA ti;
			long cont;
			
		    switch (tivacrud.getAction()) {
	        case ADD :
	        	
	        	cont = tivaRepo.countExistingTiposIVA(mutils.obtainText(tdesc));
	        	
	        	if (cont == 0) {
	        		ti = new TipoIVA(mutils.obtainText(tdesc), mutils.getDoubleFromCurrency(mutils.obtainText(tporc)), mutils.obtainText(ttfact));
	        		tivaRepo.save(ti);
	        	} else {
	        		lbmsg1.setText("Existen " + String.valueOf(cont) + " tipos de IVA con esa descripción.");
	        		cform = false;
	        	}
	            break;
	
	        case EDIT:
	        	
	        	cont = tivaRepo.countExistingTiposIVA(mutils.obtainText(tdesc), tivacrud.getDao().getId());
	        	
	        	//Check if there are no coincidences
	        	if (cont == 0) {
	        		ti = tivacrud.getDao();
	        		ti.setDescripcion(mutils.obtainText(tdesc));
	        		ti.setPorcentaje(mutils.getDoubleFromCurrency(mutils.obtainText(tporc)));
	        		ti.setTextoFactura(mutils.obtainText(ttfact));
	        		tivaRepo.save(ti);
	        	} else {
	        		lbmsg1.setText("Existen " + String.valueOf(cont) + " tipos de IVA con esa descripción.");
	        		cform = false;
	        	}
	        	
	            break;
	        
	        case DELETE:
	        	
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Confirma el BORRADO del tipo de IVA?", ButtonType.YES, ButtonType.NO);
	
		        // clicking X also means no
		        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
		        
		        if (ButtonType.YES.equals(result)) {
	        
		        	ti = tivacrud.getDao();
		        	tivaRepo.delete(ti);
	        	
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
		
	    //Setting the maximum number of characters
	    mutils.configureTextField(tdesc, 60);
	    mutils.configureTextField(ttfact, 70);
	    
	    //Setting the formatter for numbers
	    mutils.configCurrencyTextField(tporc, 6);

	    switch (tivacrud.getAction()) {
        case ADD :
        	
    		tdesc.setText("");
    		tporc.setText(mutils.getStringFromDouble(0D, mutils.CURRENCY_FORMAT));
    		ttfact.setText("");
    		
            break;
            
        case EDIT:
        	
    		fillControls();
        	
			showRelatedFacturas();
			
			showRelatedSeriesFacturas();
   		
    		break;
    		
        case DELETE:
        	
    		fillControls();

			disableControls();
    		
			showRelatedFacturas();
			
			showRelatedSeriesFacturas();

            
    		break;
    		
		case VIEW:
			
    		fillControls();

			disableControls();
			
			showRelatedFacturas();
			
			showRelatedSeriesFacturas();
			
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
		
		Optional<TipoIVA> descOpt = Optional.ofNullable(tivacrud.getDao());
		descOpt.ifPresent((x) -> {
	
			Optional<String> optStr = Optional.ofNullable(x.getDescripcion());
				optStr.ifPresentOrElse((y) -> {
					tdesc.setText(y);
				}, () -> {
					tdesc.setText("");
				});
				
			Optional<Double> optDou = Optional.ofNullable(x.getPorcentaje());
				optDou.ifPresentOrElse((y) -> {
					tporc.setText(mutils.getStringFromDouble(y, mutils.CURRENCY_FORMAT));
				}, () -> {
					tporc.setText(mutils.getStringFromDouble(0D, mutils.CURRENCY_FORMAT));
				});
			
			optStr = Optional.ofNullable(x.getTextoFactura());
				optStr.ifPresentOrElse((y) -> {
					ttfact.setText(y);
				}, () -> {
					ttfact.setText("");
				});
		});
	}
	
	private void disableControls() {
		
		disableControl(tdesc);
		disableControl(tporc);
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
	
	private void showRelatedFacturas() {

		long cont = tivaRepo.countRelatedFacturas(tivacrud.getDao().getId()); 
		lbmsg1.setText("Utilizado en " + String.valueOf(cont) + " factura" + ((cont > 1)?"s" : ""));
		
		//Disable execute button
		if ((cont > 0) && tivacrud.getAction().equals(CrudAction.DELETE)) bexec.setDisable(true);
			
	}
	
	private void showRelatedSeriesFacturas() {

		long cont = tivaRepo.countRelatedSeriesFacturas(tivacrud.getDao().getId()); 
		lbmsg2.setText("Utilizado en " + String.valueOf(cont) + " serie" + ((cont > 1)?"s" : "") + " de facturas");
		
		//Disable execute button
		if ((cont > 0) && tivacrud.getAction().equals(CrudAction.DELETE)) bexec.setDisable(true);
			
	}
	
	private boolean checkControls() {
		
		var boolwrapper = new Object(){ boolean checks = true; };
		
		if (mutils.obtainText(tdesc).length() == 0) {
			lbmsg1.setText("La descripción no puede quedar en blanco");
			boolwrapper.checks = false;  
		}
		
		Optional<Double> genOpt = Optional.ofNullable(mutils.getDoubleFromCurrency(mutils.obtainText(tporc)));
			genOpt.ifPresent((y) -> {
				
				if (y > 100) {
					lbmsg2.setText("El porcentaje no puede ser mayor de 100");
					boolwrapper.checks = false;
				}
			});
		
		return boolwrapper.checks;
	}
}
