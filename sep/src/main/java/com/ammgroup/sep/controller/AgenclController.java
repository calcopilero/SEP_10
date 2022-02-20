package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAOAgen;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.controller.config.filter.AgenciaFilter;
import com.ammgroup.sep.jreports.config.enums.ReportFormat;
import com.ammgroup.sep.jreports.service.JReportsService;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.repository.AgenciaRepository;
import com.ammgroup.sep.repository.specifications.AgenciasSpecifications;
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
public class AgenclController implements Initializable {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	CrudDAOAgen<Agencia> agcrud;
	
	@Autowired
	AgenciaFilter agfilter;
	
	@Autowired
	AgenciaRepository agenciaRepository;
	
	@Autowired
	AgenciasSpecifications agspec;
	
	@Autowired
	JReportsService jrservice;
	
	@FXML
	private TableView<Agencia> tagencias;
	
	@FXML
	private TableColumn<Agencia, String> tcnombre;
	
	@FXML
	private TableColumn<Agencia, String> tccifnif;
	
	@FXML
	private TableColumn<Agencia, String> tcprov;
	
	@FXML
	private TableColumn<Agencia, String> tcpais;
	
	@FXML
	private Button badd;
	
	@FXML
	private Button bedit;
	
	@FXML
	private Button bdelete;
	
	@FXML
	private Button bview;
	
	@FXML
	private Button bfilter;
	
	@FXML
	private Button bprint;

	@FXML
	private Button bclose;
	
	@FXML
	private Label lbmsg;
	
	@FXML
    void baddOnAction(ActionEvent event) throws Exception {
		
		agcrud.setAction(CrudAction.ADD);
		agcrud.setIndex(-1);
		
		mutils.loadForm("agencdt.fxml", "Añadir nueva agencia");
		
		refreshForm();
	}
	
	@FXML
    void beditOnAction(ActionEvent event) throws Exception {
		
		Optional<Agencia> ageOpt = Optional.ofNullable(tagencias.getSelectionModel().getSelectedItem());
			ageOpt.ifPresentOrElse((x) -> {

				agcrud.setAction(CrudAction.EDIT);
				agcrud.setIndex(tagencias.getSelectionModel().getSelectedIndex());
				agcrud.setDao(tagencias.getSelectionModel().getSelectedItem());
			
	   			try {
	   				mutils.loadForm("agencdt.fxml", "Editar agencia");
				} catch (Exception e) {
					e.printStackTrace();
				}
	    			
	   			refreshForm();
	   			
			}, () -> {
				
				lbmsg.setText("Debe seleccionar una agencia");
				
	   		});
	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) throws Exception {

		Optional<Agencia> ageOpt = Optional.ofNullable(tagencias.getSelectionModel().getSelectedItem());
			ageOpt.ifPresentOrElse((x) -> {
	
				agcrud.setAction(CrudAction.DELETE);
				agcrud.setIndex(tagencias.getSelectionModel().getSelectedIndex());
				agcrud.setDao(tagencias.getSelectionModel().getSelectedItem());
			
	   			try {
	   				mutils.loadForm("agencdt.fxml", "Borrar agencia");
				} catch (Exception e) {
					e.printStackTrace();
				}
	    			
	   			refreshForm();
	   			
			}, () -> {
				
				lbmsg.setText("Debe seleccionar una agencia");
				
	   		});
	}
	
	@FXML
    void bviewOnAction(ActionEvent event) throws Exception {
    
		Optional<Agencia> ageOpt = Optional.ofNullable(tagencias.getSelectionModel().getSelectedItem());
			ageOpt.ifPresentOrElse((x) -> {
	
				agcrud.setAction(CrudAction.VIEW);
				agcrud.setIndex(tagencias.getSelectionModel().getSelectedIndex());
				agcrud.setDao(tagencias.getSelectionModel().getSelectedItem());
			
	   			try {
	   				mutils.loadForm("agencdt.fxml", "Ver agencia");
				} catch (Exception e) {
					e.printStackTrace();
				}
	    			
	   			refreshForm();
	   			
			}, () -> {
				
				lbmsg.setText("Debe seleccionar una agencia");
				
	   		});
    }
	
	@FXML
    void bfilterOnAction(ActionEvent event) throws Exception {
		
		try {
			mutils.loadForm("agencfi.fxml", "Filtar agencias");
		} catch (Exception e) {
			e.printStackTrace();
		}

		refreshForm();
		
	}
	
	@FXML
    void bprintOnAction(ActionEvent event) throws Exception {
		
		if (tagencias.getItems().size() > 0) {
			
			jrservice.generateListReport("agencl.jrxml", agcrud.getItemsList(), ReportFormat.PREVIEW, null, "Listado de agencias");
			
		} else {
			
			lbmsg.setText("No hay elementos para generar un listado.");
		}
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
		tcnombre.setCellValueFactory(new PropertyValueFactory<Agencia, String>("nombre"));
		tccifnif.setCellValueFactory(new PropertyValueFactory<Agencia, String>("cifnif"));
		
		// Accessing repository indirectly through spring application context, not autowiring
		//SocioRepository socioRepository = springContext.getBean(SocioRepository.class);
		
		tcprov.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String prov; };
			
			Optional<Provincia> provOpt = Optional.ofNullable(c.getValue().getProvincia());
				provOpt.ifPresentOrElse((x) -> {
					strwrapper.prov = x.toString();	
				}, () -> {
					strwrapper.prov = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.prov);
				
		});
		
		tcpais.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String pais; };
			
			Optional<Pais> provOpt = Optional.ofNullable(c.getValue().getPais());
				provOpt.ifPresentOrElse((x) -> {
					strwrapper.pais = x.toString();	
				}, () -> {
					strwrapper.pais = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.pais);
				
		});
		
		// Populating the table manually
		//ObservableList<Socio> sociosOL = FXCollections.observableList(new ArrayList<Socio>());
		//socioRepository.findAll().forEach((p) -> {sociosOL.add(p);});
		//tsocios.setItems(sociosOL);
		
		refreshForm();

	}
	
	private void refreshForm() {
		
		var listwrapper = new Object(){ List<Agencia> itemslist; int cfilter = 0;};
		
		//Check if there's any filter
		Optional<AgenciaFilter> optAgfilter = Optional.ofNullable(agfilter);
			optAgfilter.ifPresentOrElse((x) -> {
				
				listwrapper.cfilter = agfilter.containsFilters();
				
				if (listwrapper.cfilter > 0) {
					listwrapper.itemslist = agspec.getFilteredAgencias(agfilter);
				} else {
					listwrapper.itemslist = getAllAgencias();
				}
	
			}, () -> {
				listwrapper.itemslist = getAllAgencias();
			});

		agcrud.setDao(null);
		agcrud.setItemsList(listwrapper.itemslist);

		tagencias.setItems(FXCollections.observableList(listwrapper.itemslist));
		tagencias.refresh();
		
		//To count the items in list
		int items = tagencias.getItems().size();
		
		lbmsg.setText("Encontrada" + ((items > 1)?"s" : "") + " " + items + " agencia" + ((items > 1)?"s" : "") + "." + ((listwrapper.cfilter > 0)? " (" + listwrapper.cfilter + " filtro" + ((listwrapper.cfilter > 1)?"s" : "") + " aplicado" + ((listwrapper.cfilter > 1)?"s" : "") + ")" : ""));

	}
	
	private List<Agencia> getAllAgencias() {
		
		var listwrapper = new Object(){ List<Agencia> itemslist; };
		
		Optional<Sort> sortOpt = Optional.ofNullable(mutils.getDefaultAgenciasSort());
			sortOpt.ifPresentOrElse((x) -> {
				listwrapper.itemslist = agenciaRepository.findAll(x);
			}, () -> {
				listwrapper.itemslist = agenciaRepository.findAll();
			});
		
		return listwrapper.itemslist;

	}
}
