package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.EstadoFactura;
import com.ammgroup.sep.repository.EstadoFacturaRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

import javafx.beans.property.ReadOnlyStringWrapper;
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
public class EfactlController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAO<EstadoFactura> efaccrud;
	
	@Autowired
	EstadoFacturaRepository efacRepo;
	
	@FXML
	private TableView<EstadoFactura> tefact;
	
	@FXML
	private TableColumn<EstadoFactura, String> tcdescripcion;
	
	@FXML
	private TableColumn<EstadoFactura, String> tcpordef;
	
	@FXML
	private TableColumn<EstadoFactura, String> tcfrect;
	
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
		
		efaccrud.setAction(CrudAction.ADD);
		efaccrud.setIndex(-1);
				
		mutils.loadForm("efactdt.fxml", "Añadir nuevo estado de factura");
		
		refreshForm();
	}
	
	@FXML
    void beditOnAction(ActionEvent event) throws Exception {
		
		Optional<EstadoFactura> efacOpt = Optional.ofNullable(tefact.getSelectionModel().getSelectedItem());
			efacOpt.ifPresentOrElse((x) -> {
				
				efaccrud.setAction(CrudAction.EDIT);
				efaccrud.setIndex(tefact.getSelectionModel().getSelectedIndex());
				efaccrud.setDao(x);
			
				try {
					mutils.loadForm("efactdt.fxml", "Editar estado de factura");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un estado de factura");
			});
	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) throws Exception {
		
		Optional<EstadoFactura> efacOpt = Optional.ofNullable(tefact.getSelectionModel().getSelectedItem());
			efacOpt.ifPresentOrElse((x) -> {
				
				efaccrud.setAction(CrudAction.DELETE);
				efaccrud.setIndex(tefact.getSelectionModel().getSelectedIndex());
				efaccrud.setDao(x);
			
				try {
					mutils.loadForm("efactdt.fxml", "Borrar estado de factura");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un estado de factura");
			});
	}
	
	@FXML
    void bviewOnAction(ActionEvent event) throws Exception {
    	
		Optional<EstadoFactura> efacOpt = Optional.ofNullable(tefact.getSelectionModel().getSelectedItem());
			efacOpt.ifPresentOrElse((x) -> {
				
				efaccrud.setAction(CrudAction.VIEW);
				efaccrud.setIndex(tefact.getSelectionModel().getSelectedIndex());
				efaccrud.setDao(x);
			
				try {
					mutils.loadForm("efactdt.fxml", "Ver estado de factura");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar un estado de factura");
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
		tcdescripcion.setCellValueFactory(new PropertyValueFactory<EstadoFactura, String>("descripcion"));
		
		tcpordef.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String pordef; };
			
			Optional<Boolean> fautoOpt = Optional.ofNullable(c.getValue().isEstadoPorDefecto());
				fautoOpt.ifPresentOrElse((x) -> {
					if (x) {
						strwrapper.pordef = "SI" ;	
					} else {
						strwrapper.pordef = "NO" ;	
					}
				}, () -> {
					strwrapper.pordef = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.pordef);
				
		});
		
		tcfrect.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String frect; };
			
			Optional<Boolean> fautoOpt = Optional.ofNullable(c.getValue().isEstadoRectificativas());
				fautoOpt.ifPresentOrElse((x) -> {
					if (x) {
						strwrapper.frect = "SI" ;	
					} else {
						strwrapper.frect = "NO" ;	
					}
				}, () -> {
					strwrapper.frect = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.frect);
				
		});
		
		// Accessing repository indirectly through spring application context, not autowiring
		//SocioRepository socioRepository = springContext.getBean(SocioRepository.class);
		
		// Populating the table manually
		//ObservableList<Socio> sociosOL = FXCollections.observableList(new ArrayList<Socio>());
		//socioRepository.findAll().forEach((p) -> {sociosOL.add(p);});
		//tsocios.setItems(sociosOL);
		
		refreshForm();
		
	}
	
	private void refreshForm() {
		
		tefact.setItems(FXCollections.observableList(efacRepo.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));

		tefact.refresh();
		
		//To count the items in list
		int items = tefact.getItems().size();
		
		lbmsg.setText("Encontrado" + ((items > 1)?"s" : "") + " " + items + " estado" + ((items > 1)?"s" : "") + " de facturas");

	}
}
