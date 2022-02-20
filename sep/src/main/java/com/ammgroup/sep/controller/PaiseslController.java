package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.repository.PaisRepository;
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
public class PaiseslController implements Initializable {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAO<Pais> paiscrud;
	
	@Autowired
	PaisRepository paisRepository;
	
	@FXML
	private TableView<Pais> tpaises;
	
	@FXML
	private TableColumn<Pais, String> tcdescripcion;
	
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
		
		paiscrud.setAction(CrudAction.ADD);
		paiscrud.setIndex(-1);
				
		mutils.loadForm("paisdt.fxml", "Añadir nuevo pais");
		
		refreshForm();
	}
	
	@FXML
    void beditOnAction(ActionEvent event) throws Exception {
		
		Optional<Pais> paisOpt = Optional.ofNullable(tpaises.getSelectionModel().getSelectedItem());
			paisOpt.ifPresentOrElse((x) -> {
				
				paiscrud.setAction(CrudAction.EDIT);
				paiscrud.setIndex(tpaises.getSelectionModel().getSelectedIndex());
				paiscrud.setDao(x);
			
				try {
					mutils.loadForm("paisdt.fxml", "Editar pais");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un pais");
			});
	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) throws Exception {
		
		Optional<Pais> paisOpt = Optional.ofNullable(tpaises.getSelectionModel().getSelectedItem());
			paisOpt.ifPresentOrElse((x) -> {
				
				paiscrud.setAction(CrudAction.DELETE);
				paiscrud.setIndex(tpaises.getSelectionModel().getSelectedIndex());
				paiscrud.setDao(x);
			
				try {
					mutils.loadForm("paisdt.fxml", "Borrar pais");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un pais");
			});
	}
	
	@FXML
    void bviewOnAction(ActionEvent event) throws Exception {
    	
		Optional<Pais> paisOpt = Optional.ofNullable(tpaises.getSelectionModel().getSelectedItem());
			paisOpt.ifPresentOrElse((x) -> {
				
				paiscrud.setAction(CrudAction.VIEW);
				paiscrud.setIndex(tpaises.getSelectionModel().getSelectedIndex());
				paiscrud.setDao(x);
			
				try {
					mutils.loadForm("paisdt.fxml", "Ver pais");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un pais");
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
		tcdescripcion.setCellValueFactory(new PropertyValueFactory<Pais, String>("descripcion"));
		
		// Accessing repository indirectly through spring application context, not autowiring
		//SocioRepository socioRepository = springContext.getBean(SocioRepository.class);
		
		// Populating the table manually
		//ObservableList<Socio> sociosOL = FXCollections.observableList(new ArrayList<Socio>());
		//socioRepository.findAll().forEach((p) -> {sociosOL.add(p);});
		//tsocios.setItems(sociosOL);
		
		refreshForm();
		
	}
	
	private void refreshForm() {
		
		tpaises.setItems(FXCollections.observableList(paisRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));

		tpaises.refresh();
		
		//To count the items in list
		int items = tpaises.getItems().size();
		
		lbmsg.setText("Encontrado" + ((items > 1)?"s" : "") + " " + items + " pais" + ((items > 1)?"es" : ""));

	}
}
