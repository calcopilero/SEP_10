package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.Descuento;
import com.ammgroup.sep.repository.DescuentoRepository;
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
public class DesculController implements Initializable {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	CrudDAO<Descuento> descrud;
	
	@Autowired
	DescuentoRepository descRepository;
	
	@FXML
	private TableView<Descuento> tdescuentos;
	
	@FXML
	private TableColumn<Descuento, String> tcdescripcion;
	
	@FXML
	private TableColumn<Descuento, Double> tcporcentaje;
	
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
		
		descrud.setAction(CrudAction.ADD);
		descrud.setIndex(-1);
		
		mutils.loadForm("descudt.fxml", "Añadir nuevo descuento");
		
		refreshForm();
	}
	
	@FXML
    void beditOnAction(ActionEvent event) throws Exception {
		
		Optional<Descuento> desOpt = Optional.ofNullable(tdescuentos.getSelectionModel().getSelectedItem());
			desOpt.ifPresentOrElse((x) -> {
				
				descrud.setAction(CrudAction.EDIT);
				descrud.setIndex(tdescuentos.getSelectionModel().getSelectedIndex());
				descrud.setDao(x);
			
				try {
					mutils.loadForm("descudt.fxml", "Editar descuento");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un descuento");
			});
	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) throws Exception {
		
		Optional<Descuento> desOpt = Optional.ofNullable(tdescuentos.getSelectionModel().getSelectedItem());
			desOpt.ifPresentOrElse((x) -> {
				
				descrud.setAction(CrudAction.DELETE);
				descrud.setIndex(tdescuentos.getSelectionModel().getSelectedIndex());
				descrud.setDao(x);
			
				try {
					mutils.loadForm("descudt.fxml", "Borrar descuento");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un descuento");
			});
	}
	
	@FXML
    void bviewOnAction(ActionEvent event) throws Exception {
		
		Optional<Descuento> desOpt = Optional.ofNullable(tdescuentos.getSelectionModel().getSelectedItem());
			desOpt.ifPresentOrElse((x) -> {
				
				descrud.setAction(CrudAction.VIEW);
				descrud.setIndex(tdescuentos.getSelectionModel().getSelectedIndex());
				descrud.setDao(x);
			
				try {
					mutils.loadForm("descudt.fxml", "Ver descuento");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un descuento");
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
		tcdescripcion.setCellValueFactory(new PropertyValueFactory<Descuento, String>("descripcion"));
		tcporcentaje.setCellValueFactory(new PropertyValueFactory<Descuento, Double>("porcentaje"));
		mutils.configureColumnForDecimal(tcporcentaje);
		
		// Populating the table manually
		//ObservableList<Socio> sociosOL = FXCollections.observableList(new ArrayList<Socio>());
		//socioRepository.findAll().forEach((p) -> {sociosOL.add(p);});
		//tsocios.setItems(sociosOL);
		
		refreshForm();
		
	}
	
	private void refreshForm() {

		tdescuentos.setItems(FXCollections.observableList(descRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		
		tdescuentos.refresh();
		
		//To count the items in list
		int items = tdescuentos.getItems().size();
		
		lbmsg.setText("Encontrado" + ((items > 1)?"s" : "") + " " + items + " descuento" + ((items > 1)?"s" : ""));

	}

}
