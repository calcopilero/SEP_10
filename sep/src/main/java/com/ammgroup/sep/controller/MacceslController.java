package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.ModoAcceso;
import com.ammgroup.sep.repository.ModoAccesoRepository;
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
public class MacceslController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAO<ModoAcceso> macrud;
	
	@Autowired
	ModoAccesoRepository maccesRepository;
	
	@FXML
	private TableView<ModoAcceso> tmacces;
	
	@FXML
	private TableColumn<ModoAcceso, String> tcdescripcion;
	
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
		
		macrud.setAction(CrudAction.ADD);
		macrud.setIndex(-1);
				
		mutils.loadForm("maccesdt.fxml", "Añadir nuevo modo de acceso");
		
		refreshForm();
	}
	
	@FXML
    void beditOnAction(ActionEvent event) throws Exception {
		
		Optional<ModoAcceso> maccOpt = Optional.ofNullable(tmacces.getSelectionModel().getSelectedItem());
			maccOpt.ifPresentOrElse((x) -> {
				
				macrud.setAction(CrudAction.EDIT);
				macrud.setIndex(tmacces.getSelectionModel().getSelectedIndex());
				macrud.setDao(x);
			
				try {
					mutils.loadForm("maccesdt.fxml", "Editar modo de acceso");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un modo de acceso");
			});
	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) throws Exception {
		
		Optional<ModoAcceso> maccOpt = Optional.ofNullable(tmacces.getSelectionModel().getSelectedItem());
			maccOpt.ifPresentOrElse((x) -> {
				
				macrud.setAction(CrudAction.DELETE);
				macrud.setIndex(tmacces.getSelectionModel().getSelectedIndex());
				macrud.setDao(x);
			
				try {
					mutils.loadForm("maccesdt.fxml", "Borrar modo de acceso");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un modo de acceso");
			});
	}
	
	@FXML
    void bviewOnAction(ActionEvent event) throws Exception {
    	
		Optional<ModoAcceso> maccOpt = Optional.ofNullable(tmacces.getSelectionModel().getSelectedItem());
		maccOpt.ifPresentOrElse((x) -> {
			
			macrud.setAction(CrudAction.VIEW);
			macrud.setIndex(tmacces.getSelectionModel().getSelectedIndex());
			macrud.setDao(x);
		
			try {
				mutils.loadForm("maccesdt.fxml", "Ver modo de acceso");
			} catch (Exception e) {
				e.printStackTrace();
			}
				
			refreshForm();
		}, () -> {
			lbmsg.setText("Debe seleccionar un modo de acceso");
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
		tcdescripcion.setCellValueFactory(new PropertyValueFactory<ModoAcceso, String>("descripcion"));
		
		// Accessing repository indirectly through spring application context, not autowiring
		//SocioRepository socioRepository = springContext.getBean(SocioRepository.class);
		
		// Populating the table manually
		//ObservableList<Socio> sociosOL = FXCollections.observableList(new ArrayList<Socio>());
		//socioRepository.findAll().forEach((p) -> {sociosOL.add(p);});
		//tsocios.setItems(sociosOL);
		
		refreshForm();
		
	}
	
	private void refreshForm() {
		
		tmacces.setItems(FXCollections.observableList(maccesRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));

		tmacces.refresh();
		
		//To count the items in list
		int items = tmacces.getItems().size();
		
		lbmsg.setText("Encontrado" + ((items > 1)?"s" : "") + " " + items + " modo" + ((items > 1)?"s" : "") + " de acceso");

	}

}
