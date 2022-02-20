package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.MotivoBaja;
import com.ammgroup.sep.repository.MotivoBajaRepository;
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
public class MbajalController implements Initializable {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	CrudDAO<MotivoBaja> mbcrud;
	
	@Autowired
	MotivoBajaRepository mbajaRepository;
	
	@FXML
	private TableView<MotivoBaja> tmbaja;
	
	@FXML
	private TableColumn<MotivoBaja, String> tcdescripcion;
	
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
		
		mbcrud.setAction(CrudAction.ADD);
		mbcrud.setIndex(-1);
		
		mutils.loadForm("mbajadt.fxml", "Añadir nuevo motivo de baja");
		
		refreshForm();
		
	}
	
	@FXML
    void beditOnAction(ActionEvent event) throws Exception {
		
		Optional<MotivoBaja> mbajaOpt = Optional.ofNullable(tmbaja.getSelectionModel().getSelectedItem());
			mbajaOpt.ifPresentOrElse((x) -> {
				
				mbcrud.setAction(CrudAction.EDIT);
				mbcrud.setIndex(tmbaja.getSelectionModel().getSelectedIndex());
				mbcrud.setDao(x);
			
				try {
					mutils.loadForm("mbajadt.fxml", "Editar motivo de baja");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un motivo de baja");
			});
	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) throws Exception {
	
		Optional<MotivoBaja> mbajaOpt = Optional.ofNullable(tmbaja.getSelectionModel().getSelectedItem());
			mbajaOpt.ifPresentOrElse((x) -> {
				
				mbcrud.setAction(CrudAction.DELETE);
				mbcrud.setIndex(tmbaja.getSelectionModel().getSelectedIndex());
				mbcrud.setDao(x);
			
				try {
					mutils.loadForm("mbajadt.fxml", "Borrar motivo de baja");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un motivo de baja");
			});
	}
	
	@FXML
    void bviewOnAction(ActionEvent event) throws Exception {
    	
		Optional<MotivoBaja> mbajaOpt = Optional.ofNullable(tmbaja.getSelectionModel().getSelectedItem());
			mbajaOpt.ifPresentOrElse((x) -> {
				
				mbcrud.setAction(CrudAction.VIEW);
				mbcrud.setIndex(tmbaja.getSelectionModel().getSelectedIndex());
				mbcrud.setDao(x);
			
				try {
					mutils.loadForm("mbajadt.fxml", "Ver motivo de baja");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un motivo de baja");
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
		tcdescripcion.setCellValueFactory(new PropertyValueFactory<MotivoBaja, String>("descripcion"));
		
		// Accessing repository indirectly through spring application context, not autowiring
		//SocioRepository socioRepository = springContext.getBean(SocioRepository.class);
		
		// Populating the table manually
		//ObservableList<Socio> sociosOL = FXCollections.observableList(new ArrayList<Socio>());
		//socioRepository.findAll().forEach((p) -> {sociosOL.add(p);});
		//tsocios.setItems(sociosOL);
		
		refreshForm();
		
	}
	
	private void refreshForm() {
		
		tmbaja.setItems(FXCollections.observableList(mbajaRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));

		tmbaja.refresh();
		
		//To count the items in list
		int items = tmbaja.getItems().size();
		
		lbmsg.setText("Encontrado" + ((items > 1)?"s" : "") + " " + items + " motivos de baja" + ((items > 1)?"s" : ""));

	}

}
