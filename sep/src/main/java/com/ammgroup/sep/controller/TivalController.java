package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.TipoIVA;
import com.ammgroup.sep.repository.TipoIVARepository;
import com.ammgroup.sep.service.ModuloUtilidades;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

@Component
public class TivalController implements Initializable {
	
	@Autowired
	ApplicationContext springContext;
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	CrudDAO<TipoIVA> tivacrud;
	
	@Autowired
	TipoIVARepository tivaRepo;
	
	@FXML
	private TableView<TipoIVA> ttiposiva;
	
	@FXML
	private TableColumn<TipoIVA, String> tcdescripcion;
	
	@FXML
	private TableColumn<TipoIVA, Double> tcporcentaje;

	@FXML
	private Button badd;
	
	@FXML
	private Button bedit;
	
	@FXML
	private Button bdelete;
	
	@FXML
	private Button bview;

	@FXML
	private Button bclose;
	
	@FXML
	private Label lbmsg;

	@FXML
    void baddOnAction(ActionEvent event) throws Exception {
		
		tivacrud.setAction(CrudAction.ADD);
		tivacrud.setIndex(-1);
		
		mutils.loadForm("tivadt.fxml", "Añadir nuevo tipo de IVA");
		
		refreshForm();
	}
	
	@FXML
    void beditOnAction(ActionEvent event) throws Exception {
		
		Optional<TipoIVA> tivaOpt = Optional.ofNullable(ttiposiva.getSelectionModel().getSelectedItem());
			tivaOpt.ifPresentOrElse((x) -> {
				
				tivacrud.setAction(CrudAction.EDIT);
				tivacrud.setIndex(ttiposiva.getSelectionModel().getSelectedIndex());
				tivacrud.setDao(ttiposiva.getSelectionModel().getSelectedItem());
			
	   			try {
	   				mutils.loadForm("tivadt.fxml", "Editar tipo de iva");
				} catch (Exception e) {
					e.printStackTrace();
				}
	    			
	   			refreshForm();
	   		}, () -> {
	   			lbmsg.setText("Debe seleccionar un tipo de IVA");
	   		});
	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) throws Exception {
		
		Optional<TipoIVA> tivaOpt = Optional.ofNullable(ttiposiva.getSelectionModel().getSelectedItem());
			tivaOpt.ifPresentOrElse((x) -> {
				
				tivacrud.setAction(CrudAction.DELETE);
				tivacrud.setIndex(ttiposiva.getSelectionModel().getSelectedIndex());
				tivacrud.setDao(ttiposiva.getSelectionModel().getSelectedItem());
			
	   			try {
	   				mutils.loadForm("tivadt.fxml", "Borrar tipo de iva");
				} catch (Exception e) {
					e.printStackTrace();
				}
	    			
	   			refreshForm();
	   		}, () -> {
	   			lbmsg.setText("Debe seleccionar un tipo de IVA");
	   		});
	}
	
	@FXML
    void bviewOnAction(ActionEvent event) throws Exception {
    	
		Optional<TipoIVA> tivaOpt = Optional.ofNullable(ttiposiva.getSelectionModel().getSelectedItem());
			tivaOpt.ifPresentOrElse((x) -> {
				
				tivacrud.setAction(CrudAction.VIEW);
				tivacrud.setIndex(ttiposiva.getSelectionModel().getSelectedIndex());
				tivacrud.setDao(ttiposiva.getSelectionModel().getSelectedItem());
			
	   			try {
	   				mutils.loadForm("tivadt.fxml", "Ver tipo de iva");
				} catch (Exception e) {
					e.printStackTrace();
				}
	    			
	   			refreshForm();
	   		}, () -> {
	   			lbmsg.setText("Debe seleccionar un tipo de IVA");
	   		});
    }
	
	
	@FXML
    void bcloseOnAction(ActionEvent event) {

		// get a handle to the stage
		Stage stage = (Stage) bclose.getScene().getWindow();
		// do what you have to do
		stage.close();
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Set up the columns in the table
		tcdescripcion.setCellValueFactory(new PropertyValueFactory<TipoIVA, String>("descripcion"));
		tcporcentaje.setCellValueFactory(new PropertyValueFactory<TipoIVA, Double>("porcentaje"));
		mutils.configureColumnForDecimal(tcporcentaje);
		
		// Populating the table manually
		//ObservableList<Socio> sociosOL = FXCollections.observableList(new ArrayList<Socio>());
		//socioRepository.findAll().forEach((p) -> {sociosOL.add(p);});
		//tsocios.setItems(sociosOL);
		
		refreshForm();
	}
	
	private void refreshForm() {

		ttiposiva.setItems(FXCollections.observableList(tivaRepo.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		
		ttiposiva.refresh();
		
		//To count the items in list
		int items = ttiposiva.getItems().size();
		
		lbmsg.setText("Encontrado" + ((items > 1)?"s" : "") + " " + items + " tipos de IVA" + ((items > 1)?"s" : ""));

	}

}
