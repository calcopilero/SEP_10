package com.ammgroup.sep.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.dao.PaFactura;
import com.ammgroup.sep.model.SerieFacturas;
import com.ammgroup.sep.repository.SerieFacturasRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

@Component
public class PafactController implements Initializable {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	SerieFacturasRepository sfRepository;
	
	@Autowired
	PaFactura pafactdao;
	
	@FXML
	private DatePicker dpffact;
	
	@FXML
	private ComboBox<SerieFacturas> cbsfact;
	
	@FXML
	private Label lbmsg1;

	@FXML
	private Button bselect;
	
	@FXML
	private Button bclose;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//Populate the combos
		cbsfact.setItems(FXCollections.observableList(sfRepository.findByNotProformaNotRectificativaNotAutomatica()));
		cbsfact.getItems().add(null);

	}
	
	@FXML
    void bselectOnAction(ActionEvent event) {

		if (checkSelection()) {
			
			pafactdao.setSerieFacturas(cbsfact.getValue());
			pafactdao.setFechaFactura(Date.from(dpffact.getValue().atStartOfDay(mutils.getDefaultZoneId()).toInstant()));
			
			closeForm();
		}
    }
	
	@FXML
    void bcloseOnAction(ActionEvent event) {

    	closeForm();

    }
	
	private void closeForm() {
		
		// get a handle to the stage
		Stage stage = (Stage) bclose.getScene().getWindow();
		// do what you have to do
		stage.close();
		
	}
	
	private boolean checkSelection() {
		
		var pafactwrapper = new Object(){boolean valuesOk = true; String msgText = "";};
		
		Optional<SerieFacturas> sfactOpt = Optional.ofNullable(cbsfact.getValue());
			sfactOpt.ifPresentOrElse(x -> {}, () -> {
					pafactwrapper.valuesOk = false;
					pafactwrapper.msgText = ((pafactwrapper.msgText.length() > 0) ? " " : "") + "Debe especificar una serie de facturas.";
				});
			
		Optional<LocalDate> optDate = Optional.ofNullable(dpffact.getValue());
			optDate.ifPresentOrElse((y) -> {}, () -> {
				pafactwrapper.valuesOk = false;
				pafactwrapper.msgText = ((pafactwrapper.msgText.length() > 0) ? " " : "") + "Debe especificar una fecha.";
			});
			
		lbmsg1.setText(pafactwrapper.msgText);
			
		return pafactwrapper.valuesOk;
	}

}
