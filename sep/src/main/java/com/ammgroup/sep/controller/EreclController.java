package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.EstadoReclamacion;
import com.ammgroup.sep.repository.EstadoReclamacionRepository;
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
public class EreclController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAO<EstadoReclamacion> ereccrud;
	
	@Autowired
	EstadoReclamacionRepository erecRepo;
	
	@FXML
	private TableView<EstadoReclamacion> tereclam;
	
	@FXML
	private TableColumn<EstadoReclamacion, String> tcdescripcion;
	
	@FXML
	private Label lbmsg;

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
    void baddOnAction(ActionEvent event) throws Exception {
		
		ereccrud.setAction(CrudAction.ADD);
		ereccrud.setIndex(-1);
				
		mutils.loadForm("erecdt.fxml", "Añadir nuevo estado de reclamación");
		
		refreshForm();
	}
	
	@FXML
    void beditOnAction(ActionEvent event) throws Exception {
		
		Optional<EstadoReclamacion> erecOpt = Optional.ofNullable(tereclam.getSelectionModel().getSelectedItem());
			erecOpt.ifPresentOrElse((x) -> {
				
				ereccrud.setAction(CrudAction.EDIT);
				ereccrud.setIndex(tereclam.getSelectionModel().getSelectedIndex());
				ereccrud.setDao(x);
			
				try {
					mutils.loadForm("erecdt.fxml", "Editar estado de reclamación");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un estado de reclamación");
			});
	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) throws Exception {
		
		Optional<EstadoReclamacion> erecOpt = Optional.ofNullable(tereclam.getSelectionModel().getSelectedItem());
			erecOpt.ifPresentOrElse((x) -> {
				
				ereccrud.setAction(CrudAction.DELETE);
				ereccrud.setIndex(tereclam.getSelectionModel().getSelectedIndex());
				ereccrud.setDao(x);
			
				try {
					mutils.loadForm("erecdt.fxml", "Borrar estado de reclamación");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un estado de reclamación");
			});
	}
	
	@FXML
    void bviewOnAction(ActionEvent event) throws Exception {
    	
		Optional<EstadoReclamacion> erecOpt = Optional.ofNullable(tereclam.getSelectionModel().getSelectedItem());
			erecOpt.ifPresentOrElse((x) -> {
				
				ereccrud.setAction(CrudAction.VIEW);
				ereccrud.setIndex(tereclam.getSelectionModel().getSelectedIndex());
				ereccrud.setDao(x);
			
				try {
					mutils.loadForm("erecdt.fxml", "Ver estado de reclamación");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un estado de reclamación");
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
		tcdescripcion.setCellValueFactory(new PropertyValueFactory<EstadoReclamacion, String>("descripcion"));
		
		// Accessing repository indirectly through spring application context, not autowiring
		//SocioRepository socioRepository = springContext.getBean(SocioRepository.class);
		
		// Populating the table manually
		//ObservableList<Socio> sociosOL = FXCollections.observableList(new ArrayList<Socio>());
		//socioRepository.findAll().forEach((p) -> {sociosOL.add(p);});
		//tsocios.setItems(sociosOL);
		
		refreshForm();
	}
	
	private void refreshForm() {
		
		tereclam.setItems(FXCollections.observableList(erecRepo.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));

		tereclam.refresh();
		
		//To count the items in list
		int items = tereclam.getItems().size();
		
		lbmsg.setText("Encontrado" + ((items > 1)?"s" : "") + " " + items + " estado"  + ((items > 1)?"s" : "") + " de reclamacion" + ((items > 1)?"es" : ""));

	}

}
