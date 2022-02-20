package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.Descuento;
import com.ammgroup.sep.repository.DescuentoRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

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
public class DescudtController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAO<Descuento> descrud;

	@Autowired
	DescuentoRepository descRepository;

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
			Descuento d;
			long cont;
			
		    switch (descrud.getAction()) {
	        case ADD :
	        	
	        	cont = descRepository.countExistingDescuentos(obtainText(tdesc));
	        	
	        	if (cont == 0) {
	        		d = new Descuento(obtainText(tdesc), Double.parseDouble(obtainText(tporc)), obtainText(ttfact));
	        		descRepository.save(d);
	        	} else {
	        		lbmsg1.setText("Existen " + String.valueOf(cont) + " descuentos con esa descripción.");
	        		cform = false;
	        	}
	            break;
	
	        case EDIT:
	        	
	        	cont = descRepository.countExistingDescuentos(obtainText(tdesc), descrud.getDao().getId());
	        	
	        	//Check if there are no coincidences
	        	if (cont == 0) {
	        		d = descrud.getDao();
	        		d.setDescripcion(obtainText(tdesc));
	        		d.setPorcentaje(Double.parseDouble(obtainText(tporc)));
	        		d.setTextoFactura(obtainText(ttfact));
	        		descRepository.save(d);
	        	} else {
	        		lbmsg1.setText("Existen " + String.valueOf(cont) + " descuentos con esa descripción.");
	        		cform = false;
	        	}
	        	
	            break;
	        
	        case DELETE:
	        	
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Confirma el BORRADO del descuento?", ButtonType.YES, ButtonType.NO);
	
		        // clicking X also means no
		        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
		        
		        if (ButtonType.YES.equals(result)) {
	        
		        	d = descrud.getDao();
		        	descRepository.delete(d);
	        	
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
	    tdesc.addEventFilter(KeyEvent.KEY_TYPED, maxLength(60));
	    ttfact.addEventFilter(KeyEvent.KEY_TYPED, maxLength(70));
	    
	    //Setting the formatter for numbers
	    tporc.addEventFilter(KeyEvent.KEY_TYPED, maxLength(6));
	    tporc.setTextFormatter(mutils.getTextFormatterForDecimal());
		
	    switch (descrud.getAction()) {
        case ADD :
        	
    		tdesc.setText("");
    		tporc.setText("0.00");
    		ttfact.setText("");
    		
            break;
            
        case EDIT:
        	
    		fillControls();
        	
    		showRelatedSocios();
    		
    		showRelatedFacturas();
    		
    		break;
    		
        case DELETE:
        	
    		fillControls();

			disableControls();
    		
			showRelatedSocios();
			
			showRelatedFacturas();
    		
    		break;
    		
		case VIEW:
			
    		fillControls();

			disableControls();
    		
			showRelatedSocios();
			
			showRelatedFacturas();
			
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

	private void closeForm() {
		
		// get a handle to the stage
		Stage stage = (Stage) bclose.getScene().getWindow();
		// do what you have to do
		stage.close();
		
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
		
		Optional<Descuento> descOpt = Optional.ofNullable(descrud.getDao());
			descOpt.ifPresent((x) -> {
		
				Optional<String> optStr = Optional.ofNullable(x.getDescripcion());
					optStr.ifPresentOrElse((y) -> {
						tdesc.setText(y);
					}, () -> {
						tdesc.setText("");
					});
					
				Optional<Double> optDou = Optional.ofNullable(x.getPorcentaje());
					optDou.ifPresentOrElse((y) -> {
						tporc.setText(Double.toString(y));
					}, () -> {
						tporc.setText(mutils.CURRENCY_ZERO);
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
	
	private void showRelatedSocios() {

		long cont = descRepository.countRelatedSocios(descrud.getDao().getId()); 
		lbmsg1.setText("Utilizado en " + String.valueOf(cont) + " socio" + ((cont > 1)?"s" : ""));
		
		//Disable execute button
		if ((cont > 0) && descrud.getAction().equals(CrudAction.DELETE)) bexec.setDisable(true);
			
	}
	
	private void showRelatedFacturas() {

		long cont = descRepository.countRelatedFacturas(descrud.getDao().getId()); 
		lbmsg2.setText("Utilizado en " + String.valueOf(cont) + " factura" + ((cont > 1)?"s" : ""));
		
		//Disable execute button
		if ((cont > 0) && descrud.getAction().equals(CrudAction.DELETE)) bexec.setDisable(true);
			
	}
	
	private boolean checkControls() {
		
		var boolwrapper = new Object(){ boolean checks = true; };
		
		if (obtainText(tdesc).length() == 0) {
			lbmsg1.setText("La descripción no puede quedar en blanco");
			boolwrapper.checks = false;  
		}
		
		Optional<Double> genOpt = Optional.ofNullable(Double.parseDouble(obtainText(tporc)));
			genOpt.ifPresent((y) -> {
				
				if (y > 100) {
					lbmsg2.setText("El porcentaje no puede ser mayor de 100");
					boolwrapper.checks = false;
				}
			});
		
		return boolwrapper.checks;
	}
}
