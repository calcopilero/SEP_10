package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.ModalidadSocio;
import com.ammgroup.sep.repository.ModalidadSocioRepository;
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
public class MsocioslController implements Initializable {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	CrudDAO<ModalidadSocio> msoccrud;
	
	@Autowired
	ModalidadSocioRepository msocRepo;
	
	@FXML
	private TableView<ModalidadSocio> tmsocios;
	
	@FXML
	private TableColumn<ModalidadSocio, String> tcdescripcion;
	
	@FXML
	private TableColumn<ModalidadSocio, String> tcconcepto;
	
	@FXML
	private TableColumn<ModalidadSocio, Double> tccuota;

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
		
		msoccrud.setAction(CrudAction.ADD);
		msoccrud.setIndex(-1);
		
		mutils.loadForm("msociodt.fxml", "Añadir nueva modalidad de socio");
		
		refreshForm();
	}
	
	@FXML
    void beditOnAction(ActionEvent event) throws Exception {
		
		Optional<ModalidadSocio> msocOpt = Optional.ofNullable(tmsocios.getSelectionModel().getSelectedItem());
			msocOpt.ifPresentOrElse((x) -> {
				
				msoccrud.setAction(CrudAction.EDIT);
				msoccrud.setIndex(tmsocios.getSelectionModel().getSelectedIndex());
				msoccrud.setDao(x);
			
				try {
					mutils.loadForm("msociodt.fxml", "Editar modalidad de socio");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar una modalidad de socio");
			});
	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) throws Exception {
		
		Optional<ModalidadSocio> msocOpt = Optional.ofNullable(tmsocios.getSelectionModel().getSelectedItem());
			msocOpt.ifPresentOrElse((x) -> {
				
				msoccrud.setAction(CrudAction.DELETE);
				msoccrud.setIndex(tmsocios.getSelectionModel().getSelectedIndex());
				msoccrud.setDao(x);
			
				try {
					mutils.loadForm("msociodt.fxml", "Borrar modalidad de socio");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar una modalidad de socio");
			});
	}
	
	@FXML
    void bviewOnAction(ActionEvent event) throws Exception {
    	
		Optional<ModalidadSocio> msocOpt = Optional.ofNullable(tmsocios.getSelectionModel().getSelectedItem());
			msocOpt.ifPresentOrElse((x) -> {
				
				msoccrud.setAction(CrudAction.VIEW);
				msoccrud.setIndex(tmsocios.getSelectionModel().getSelectedIndex());
				msoccrud.setDao(x);
			
				try {
					mutils.loadForm("msociodt.fxml", "Ver modalidad de socio");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar una modalidad de socio");
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
		tcdescripcion.setCellValueFactory(new PropertyValueFactory<ModalidadSocio, String>("descripcion"));
		tcconcepto.setCellValueFactory(new PropertyValueFactory<ModalidadSocio, String>("concepto"));
		tccuota.setCellValueFactory(new PropertyValueFactory<ModalidadSocio, Double>("cuota"));
		mutils.configureColumnForDecimal(tccuota);
		
		// Accessing repository indirectly through spring application context, not autowiring
		//SocioRepository socioRepository = springContext.getBean(SocioRepository.class);
		
		// Populating the table manually
		//ObservableList<Socio> sociosOL = FXCollections.observableList(new ArrayList<Socio>());
		//socioRepository.findAll().forEach((p) -> {sociosOL.add(p);});
		//tsocios.setItems(sociosOL);
		
		refreshForm();
		
	}
	
	private void refreshForm() {

		tmsocios.setItems(FXCollections.observableList(msocRepo.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		
		tmsocios.refresh();
		
		//To count the items in list
		int items = tmsocios.getItems().size();
		
		lbmsg.setText("Encontrada" + ((items > 1)?"s" : "") + " " + items + " modalidad" + ((items > 1)?"es" : "") + " de socios");

	}

}
