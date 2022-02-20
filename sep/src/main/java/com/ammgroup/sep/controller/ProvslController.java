package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.repository.ProvinciaRepository;
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
public class ProvslController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAO<Provincia> provcrud;
	
	@Autowired
	ProvinciaRepository provRepo;
	
	@FXML
	private TableView<Provincia> tprovs;
	
	@FXML
	private TableColumn<Provincia, String> tcdescripcion;
	
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
		
		provcrud.setAction(CrudAction.ADD);
		provcrud.setIndex(-1);
		
		mutils.loadForm("provdt.fxml", "Añadir nueva provincia");
		
		refreshForm();
	}
	
	@FXML
    void beditOnAction(ActionEvent event) throws Exception {
		
		Optional<Provincia> provOpt = Optional.ofNullable(tprovs.getSelectionModel().getSelectedItem());
			provOpt.ifPresentOrElse((x) -> {
				
				provcrud.setAction(CrudAction.EDIT);
				provcrud.setIndex(tprovs.getSelectionModel().getSelectedIndex());
				provcrud.setDao(x);
			
				try {
					mutils.loadForm("provdt.fxml", "Editar provincia");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar una provincia");
			});
	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) throws Exception {
		
		Optional<Provincia> provOpt = Optional.ofNullable(tprovs.getSelectionModel().getSelectedItem());
			provOpt.ifPresentOrElse((x) -> {
				
				provcrud.setAction(CrudAction.DELETE);
				provcrud.setIndex(tprovs.getSelectionModel().getSelectedIndex());
				provcrud.setDao(x);
			
				try {
					mutils.loadForm("provdt.fxml", "Borrar provincia");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar una provincia");
			});

	}
	
	@FXML
    void bviewOnAction(ActionEvent event) throws Exception {
    	
		Optional<Provincia> provOpt = Optional.ofNullable(tprovs.getSelectionModel().getSelectedItem());
			provOpt.ifPresentOrElse((x) -> {
				
				provcrud.setAction(CrudAction.VIEW);
				provcrud.setIndex(tprovs.getSelectionModel().getSelectedIndex());
				provcrud.setDao(x);
			
				try {
					mutils.loadForm("provdt.fxml", "Ver provincia");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar una provincia");
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
		tcdescripcion.setCellValueFactory(new PropertyValueFactory<Provincia, String>("descripcion"));
		
		// Accessing repository indirectly through spring application context, not autowiring
		//SocioRepository socioRepository = springContext.getBean(SocioRepository.class);
		
		// Populating the table manually
		//ObservableList<Socio> sociosOL = FXCollections.observableList(new ArrayList<Socio>());
		//socioRepository.findAll().forEach((p) -> {sociosOL.add(p);});
		//tsocios.setItems(sociosOL);
		
		refreshForm();
		
	}
	
	private void refreshForm() {
		
		tprovs.setItems(FXCollections.observableList(provRepo.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));

		tprovs.refresh();
		
		//To count the items in list
		int items = tprovs.getItems().size();
		
		lbmsg.setText("Encontrada" + ((items > 1)?"s" : "") + " " + items + " provincia" + ((items > 1)?"s" : ""));

	}
}
