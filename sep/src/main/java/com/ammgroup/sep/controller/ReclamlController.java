package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAORecl;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.controller.config.filter.ReclamacionFilter;
import com.ammgroup.sep.jreports.config.enums.ReportFormat;
import com.ammgroup.sep.jreports.service.JReportsService;
import com.ammgroup.sep.model.EstadoReclamacion;
import com.ammgroup.sep.model.Reclamacion;
import com.ammgroup.sep.repository.ReclamacionRepository;
import com.ammgroup.sep.repository.specifications.ReclamacionesSpecifications;
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
public class ReclamlController implements Initializable {

	@Autowired
	ApplicationContext springContext;
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	ReclamacionFilter recfilter;
	
	@Autowired
	private ReclamacionRepository reclamRepository;
	
	@Autowired
	CrudDAORecl<Reclamacion> reclamCrud;
	
	@Autowired
	ReclamacionesSpecifications recspec;
	
	@Autowired
	JReportsService jrservice;
	
	@FXML
	private TableView<Reclamacion> treclams;
	
	@FXML
	private TableColumn<Reclamacion, Integer> tcnumero;
	
	@FXML
	private TableColumn<Reclamacion, Date> tcfreclam;
	
	@FXML
	private TableColumn<Reclamacion, String> tcsocio;
	
	@FXML
	private TableColumn<Reclamacion, String> tcfresp;
	
	@FXML
	private TableColumn<Reclamacion, String> tcestado;
	
	@FXML
	private TableColumn<Reclamacion, String> tccoment;
	
    @FXML
    private Label lbmsg;
	
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
    void beditOnAction(ActionEvent event) {
		
		Optional<Reclamacion> reclOpt = Optional.ofNullable(treclams.getSelectionModel().getSelectedItem());
			reclOpt.ifPresentOrElse((x) -> {
				
				reclamCrud.setAction(CrudAction.EDIT);
				reclamCrud.setIndex(treclams.getSelectionModel().getSelectedIndex());
				reclamCrud.setDao(treclams.getSelectionModel().getSelectedItem());
			
	   			try {
	   				mutils.loadForm("reclamdt.fxml", "Editar reclamación");
				} catch (Exception e) {
					e.printStackTrace();
				}
	    			
	   			refreshForm();
	   			
			}, () -> {
				
				lbmsg.setText("Debe seleccionar una reclamación");
				
	   		});
	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) {
		
		Optional<Reclamacion> reclOpt = Optional.ofNullable(treclams.getSelectionModel().getSelectedItem());
			reclOpt.ifPresentOrElse((x) -> {
				
				reclamCrud.setAction(CrudAction.DELETE);
				reclamCrud.setIndex(treclams.getSelectionModel().getSelectedIndex());
				reclamCrud.setDao(treclams.getSelectionModel().getSelectedItem());
			
	   			try {
	   				mutils.loadForm("reclamdt.fxml", "Borrar reclamación");
				} catch (Exception e) {
					e.printStackTrace();
				}
	    			
	   			refreshForm();
	   			
			}, () -> {
				
				lbmsg.setText("Debe seleccionar una reclamación");
				
	   		});
	}
	
	@FXML
    void bviewOnAction(ActionEvent event) throws Exception {
		
		Optional<Reclamacion> reclOpt = Optional.ofNullable(treclams.getSelectionModel().getSelectedItem());
			reclOpt.ifPresentOrElse((x) -> {
				
				reclamCrud.setAction(CrudAction.VIEW);
				reclamCrud.setIndex(treclams.getSelectionModel().getSelectedIndex());
				reclamCrud.setDao(treclams.getSelectionModel().getSelectedItem());
			
	   			try {
	   				mutils.loadForm("reclamdt.fxml", "Ver reclamación");
				} catch (Exception e) {
					e.printStackTrace();
				}
	    			
	   			refreshForm();
	   			
			}, () -> {
				
				lbmsg.setText("Debe seleccionar una reclamación");
				
	   		});
	}
	
	@FXML
    void bfilterOnAction(ActionEvent event) throws Exception {
		
		mutils.loadForm("reclamfi.fxml", "Filtrar reclamaciones");

		refreshForm();
	}
	
	@FXML
    void bprintOnAction(ActionEvent event) throws Exception {
		
		if (treclams.getItems().size() > 0) {
			
			jrservice.generateListReport("reclaml.jrxml", reclamCrud.getItemsList(), ReportFormat.PREVIEW, null, "Listado de reclamaciones");

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
		tcnumero.setCellValueFactory(new PropertyValueFactory<Reclamacion, Integer>("numero"));
		tcfreclam.setCellValueFactory(new PropertyValueFactory<Reclamacion, Date>("fechaReclamacion"));
		mutils.configureColumnForDate(tcfreclam);
		tcsocio.setCellValueFactory(c-> new ReadOnlyStringWrapper(c.getValue().getSocio().toString()));
		tcfresp.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String fecha; };
			
			Optional<Date> dateOpt = Optional.ofNullable(c.getValue().getFechaRespuesta());
			dateOpt.ifPresentOrElse((x) -> {
				strwrapper.fecha = mutils.getStringFromDate(x, mutils.DATE_FORMAT);	
			}, () -> {
				strwrapper.fecha = "";
			} );
			
			return new ReadOnlyStringWrapper(strwrapper.fecha);
				
		});
		//tcfresp.setCellValueFactory(new PropertyValueFactory<Reclamacion, Date>("fechaRespuesta"));
		//mutils.configureColumnForDate(tcfresp);
		
		tcestado.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String estado; };
			
			Optional<EstadoReclamacion> erecOpt = Optional.ofNullable(c.getValue().getEstadoReclamacion());
				erecOpt.ifPresentOrElse((x) -> {
					strwrapper.estado = x.getDescripcion() ;	
				}, () -> {
					strwrapper.estado = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.estado);
				
		});
		tccoment.setCellValueFactory(new PropertyValueFactory<Reclamacion, String>("reclamacionComentario"));
		
		refreshForm();

	}
	
	private void refreshForm() {
		
		var listwrapper = new Object(){ List<Reclamacion> itemslist; int cfilter = 0;};
		
		//Check if there's any filter
		Optional<ReclamacionFilter> optRecfilter = Optional.ofNullable(recfilter);
			optRecfilter.ifPresentOrElse((x) -> {
				
				listwrapper.cfilter = recfilter.containsFilters();
			
				if (listwrapper.cfilter > 0) {
					listwrapper.itemslist = recspec.getFilteredReclamaciones(recfilter);
				} else {
					listwrapper.itemslist = getAllReclamaciones();
				}
	
			}, () -> {
				listwrapper.itemslist = getAllReclamaciones();
			});

		reclamCrud.setDao(null);
		reclamCrud.setItemsList(listwrapper.itemslist);
			
		treclams.setItems(FXCollections.observableList(listwrapper.itemslist));
		treclams.refresh();
		
		//To count the items in list
		int items = treclams.getItems().size();
		
		lbmsg.setText("Encontrada" + ((items > 1)?"s" : "") + " " + items + " reclamación" + ((items > 1)?"es" : "") + "." + ((listwrapper.cfilter > 0)? " (" + listwrapper.cfilter + " filtro" + ((listwrapper.cfilter > 1)?"s" : "") + " aplicado" + ((listwrapper.cfilter > 1)?"s" : "") + ")" : ""));

	}
	
	private List<Reclamacion> getAllReclamaciones() {
		
		var listwrapper = new Object(){ List<Reclamacion> itemslist; };
		
		Optional<Sort> sortOpt = Optional.ofNullable(mutils.getDefaultReclamacionesSort());
			sortOpt.ifPresentOrElse((x) -> {
				listwrapper.itemslist = reclamRepository.findAll(x);
			}, () -> {
				listwrapper.itemslist = reclamRepository.findAll();
			});
		
		return listwrapper.itemslist;

	}
}
