package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.model.ZonaPostal;
import com.ammgroup.sep.repository.ZonaPostalRepository;
import com.ammgroup.sep.service.ModuloUtilidades;
import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;

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
public class ZpostlController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;

	@Autowired
	CrudDAO<ZonaPostal> zpcrud;
	
	@Autowired
	ZonaPostalRepository zpostRepository;
	
	@FXML
	private TableView<ZonaPostal> tzpost;
	
	@FXML
	private TableColumn<ZonaPostal, String> tcdescripcion;
	
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
		
		zpcrud.setAction(CrudAction.ADD);
		zpcrud.setIndex(-1);
		
		mutils.loadForm("zpostdt.fxml", "Añadir nueva zona postal");
		
		refreshForm();
		
	}
	
	@FXML
    void beditOnAction(ActionEvent event) throws Exception {
		
		Optional<ZonaPostal> provOpt = Optional.ofNullable(tzpost.getSelectionModel().getSelectedItem());
			provOpt.ifPresentOrElse((x) -> {
				
				zpcrud.setAction(CrudAction.EDIT);
				zpcrud.setIndex(tzpost.getSelectionModel().getSelectedIndex());
				zpcrud.setDao(x);
			
				try {
					mutils.loadForm("zpostdt.fxml", "Editar zona postal");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar una zona postal");
			});
	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) throws Exception {
		
		Optional<ZonaPostal> provOpt = Optional.ofNullable(tzpost.getSelectionModel().getSelectedItem());
			provOpt.ifPresentOrElse((x) -> {
				
				zpcrud.setAction(CrudAction.DELETE);
				zpcrud.setIndex(tzpost.getSelectionModel().getSelectedIndex());
				zpcrud.setDao(x);
			
				try {
					mutils.loadForm("zpostdt.fxml", "Borrar zona postal");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar una zona postal");
			});
	}
	
	@FXML
    void bviewOnAction(ActionEvent event) throws Exception {
		
		Optional<ZonaPostal> provOpt = Optional.ofNullable(tzpost.getSelectionModel().getSelectedItem());
			provOpt.ifPresentOrElse((x) -> {
				
				zpcrud.setAction(CrudAction.VIEW);
				zpcrud.setIndex(tzpost.getSelectionModel().getSelectedIndex());
				zpcrud.setDao(x);
			
				try {
					mutils.loadForm("zpostdt.fxml", "Ver zona postal");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar una zona postal");
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
		tcdescripcion.setCellValueFactory(new PropertyValueFactory<ZonaPostal, String>("descripcion"));
		
		// Accessing repository indirectly through spring application context, not autowiring
		//SocioRepository socioRepository = springContext.getBean(SocioRepository.class);
		
		// Populating the table manually
		//ObservableList<Socio> sociosOL = FXCollections.observableList(new ArrayList<Socio>());
		//socioRepository.findAll().forEach((p) -> {sociosOL.add(p);});
		//tsocios.setItems(sociosOL);
		
		refreshForm();
		
	}
	
	private void refreshForm() {
		
		tzpost.setItems(FXCollections.observableList(zpostRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));

		tzpost.refresh();
		
		//To count the items in list
		int items = tzpost.getItems().size();
		
		lbmsg.setText("Encontrada" + ((items > 1)?"s" : "") + " " + items + " zona" + ((items > 1)?"s" : "") + " " + " postal" + ((items > 1)?"es" : ""));

	}
}
