package com.ammgroup.sep.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.filter.ReclamacionFilter;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.EstadoReclamacion;
import com.ammgroup.sep.repository.AgenciaRepository;
import com.ammgroup.sep.repository.EstadoReclamacionRepository;
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
public class ReclamfiController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	ReclamacionFilter recfilter;
	
	@Autowired
	AgenciaRepository agenciaRepository;
	
	@Autowired
	EstadoReclamacionRepository estrecRepository;
	
	@FXML
	private DatePicker dpfrecli;
	
	@FXML
	private DatePicker dpfreclf;
	
	@FXML
	private ComboBox<EstadoReclamacion> cbestrec;
	
	@FXML
	private ComboBox<Agencia> cbagencia;
	
	@FXML
	private Label lbmsg1;
	
	@FXML
	private Label lbmsg2;

	@FXML
	private Button bfilter;
	
	@FXML
	private Button bclean;
	
	@FXML
	private Button bclose;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Populate the combos
		cbestrec.setItems(FXCollections.observableList(estrecRepository.findAll()));
		cbestrec.getItems().add(null);
		cbagencia.setItems(FXCollections.observableList(agenciaRepository.findAll()));
		cbagencia.getItems().add(null);

		//Shove filters from Filter object into controls
		Optional<ReclamacionFilter> optRecfilter = Optional.ofNullable(recfilter);
			optRecfilter.ifPresent((x) -> {
				
				//Datepicker is blank if Fecha is null 
				Optional<Date> optDate = Optional.ofNullable(x.getFechaReclamacionInicial());
					optDate.ifPresent((y) -> dpfrecli.setValue(mutils.obtainLocalDate(y)));
				optDate = Optional.ofNullable(x.getFechaReclamacionFinal());
					optDate.ifPresent((y) -> dpfreclf.setValue(mutils.obtainLocalDate(y)));
				Optional<Agencia> optAge = Optional.ofNullable(recfilter.getAgencia());
					optAge.ifPresent((y) -> { cbagencia.getSelectionModel().select(mutils.searchIdInCombo(cbagencia, y)); });
				Optional<EstadoReclamacion> optErec = Optional.ofNullable(recfilter.getEstadoReclamacion());
					optErec.ifPresent((y) -> { cbestrec.getSelectionModel().select(mutils.searchIdInCombo(cbestrec, y)); });

			});
	}

	@FXML
    void bfilterOnAction(ActionEvent event) {
		
		if (checkControls()) {
			
			//Shove filters into SocioFilter object
			fillFilterObjetct(recfilter);
			
	    	closeForm();
		}
	}
	
	
	@FXML
    void bcleanOnAction(ActionEvent event) {

		dpfrecli.setValue(null);
		dpfreclf.setValue(null);
		cbestrec.getSelectionModel().clearSelection();
		cbagencia.getSelectionModel().clearSelection();
		
		//Shove filters into filter object
		fillFilterObjetct(recfilter);
	}

	private boolean checkControls() {
		
		boolean validControls = false;
		String msg = "";
		
		Optional<LocalDate> optDateIni = Optional.ofNullable(dpfrecli.getValue());
		Optional<LocalDate> optDateFin = Optional.ofNullable(dpfreclf.getValue());
		
		//Both dates should be filled or empty
		if ((optDateIni.isPresent() && optDateFin.isPresent()) || (optDateIni.isEmpty() && optDateFin.isEmpty())) {
			validControls = true;
		} else {
			msg += "Debe especificar dos fechas válidas.";
		}

		lbmsg1.setText(msg);
		return validControls;
	}
	
	private void fillFilterObjetct(ReclamacionFilter rfilt) {
		
		Optional<ReclamacionFilter> optRecfilter = Optional.ofNullable(rfilt);
			optRecfilter.ifPresent((x) -> {
				
				Optional<LocalDate> optDate = Optional.ofNullable(dpfrecli.getValue());
					optDate.ifPresentOrElse((y) -> { 
						x.setFechaReclamacionInicial(Date.from(y.atStartOfDay(mutils.getDefaultZoneId()).toInstant()));
					}, () -> {
						x.setFechaReclamacionInicial(null);
					});

				optDate = Optional.ofNullable(dpfreclf.getValue());
					optDate.ifPresentOrElse((y) -> {
						x.setFechaReclamacionFinal(Date.from(y.atStartOfDay(mutils.getDefaultZoneId()).toInstant()));
					}, () -> {
						x.setFechaReclamacionFinal(null);
					});
				
				x.setEstadoReclamacion(cbestrec.getValue());
				x.setAgencia(cbagencia.getValue());

			});
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
}
