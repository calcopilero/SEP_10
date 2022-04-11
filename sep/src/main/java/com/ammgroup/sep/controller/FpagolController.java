package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.FormaPago;
import com.ammgroup.sep.repository.FormaPagoRepository;
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
public class FpagolController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAO<FormaPago> fpagcrud;
	
	@Autowired
	FormaPagoRepository fpagoRepository;
	
	@FXML
	private TableView<FormaPago> tfpago;
	
	@FXML
	private TableColumn<FormaPago, String> tcdescripcion;
	
	@FXML
	private TableColumn<FormaPago, String> tctfactura;

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
		
		fpagcrud.setAction(CrudAction.ADD);
		fpagcrud.setIndex(-1);
				
		mutils.loadForm("fpagodt.fxml", "Añadir nueva forma de pago");
		
		refreshForm();
	}
	
	@FXML
    void beditOnAction(ActionEvent event) throws Exception {
		
		Optional<FormaPago> fpagOpt = Optional.ofNullable(tfpago.getSelectionModel().getSelectedItem());
			fpagOpt.ifPresentOrElse((x) -> {
		
				fpagcrud.setAction(CrudAction.EDIT);
				fpagcrud.setIndex(tfpago.getSelectionModel().getSelectedIndex());
				fpagcrud.setDao(x);
				
				try {
					mutils.loadForm("fpagodt.fxml", "Editar forma de pago");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				refreshForm();
			
			}, () -> {
				lbmsg.setText("Debe seleccionar una forma de pago");
			});
	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) throws Exception {
		
		Optional<FormaPago> fpagOpt = Optional.ofNullable(tfpago.getSelectionModel().getSelectedItem());
		fpagOpt.ifPresentOrElse((x) -> {
	
			fpagcrud.setAction(CrudAction.DELETE);
			fpagcrud.setIndex(tfpago.getSelectionModel().getSelectedIndex());
			fpagcrud.setDao(x);
			
			try {
				mutils.loadForm("fpagodt.fxml", "Borrar forma de pago");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			refreshForm();
		
		}, () -> {
			lbmsg.setText("Debe seleccionar una forma de pago");
		});
	}
	
	@FXML
    void bviewOnAction(ActionEvent event) throws Exception {
    	
		Optional<FormaPago> fpagOpt = Optional.ofNullable(tfpago.getSelectionModel().getSelectedItem());
			fpagOpt.ifPresentOrElse((x) -> {
		
				fpagcrud.setAction(CrudAction.VIEW);
				fpagcrud.setIndex(tfpago.getSelectionModel().getSelectedIndex());
				fpagcrud.setDao(x);
				
				try {
					mutils.loadForm("fpagodt.fxml", "Borrar forma de pago");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				refreshForm();
			
			}, () -> {
				lbmsg.setText("Debe seleccionar una forma de pago");
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
		tcdescripcion.setCellValueFactory(new PropertyValueFactory<FormaPago, String>("descripcion"));
		tctfactura.setCellValueFactory(new PropertyValueFactory<FormaPago, String>("textoFactura"));
		
		// Accessing repository indirectly through spring application context, not autowiring
		//SocioRepository socioRepository = springContext.getBean(SocioRepository.class);
		
		// Populating the table manually
		//ObservableList<Socio> sociosOL = FXCollections.observableList(new ArrayList<Socio>());
		//socioRepository.findAll().forEach((p) -> {sociosOL.add(p);});
		//tsocios.setItems(sociosOL);
		
		refreshForm();
		
	}
	
	private void refreshForm() {
		
		tfpago.setItems(FXCollections.observableList(fpagoRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));

		tfpago.refresh();
		
		//To count the items in list
		int items = tfpago.getItems().size();
		
		lbmsg.setText("Encontrada" + ((items > 1)?"s" : "") + " " + items + " forma" + ((items > 1)?"s de pago" : ""));

	}

}
