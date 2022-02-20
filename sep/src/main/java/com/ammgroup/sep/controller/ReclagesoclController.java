package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAOAgen;
import com.ammgroup.sep.controller.config.crud.CrudDAORecl;
import com.ammgroup.sep.controller.config.crud.CrudDAOSoc;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.jreports.config.enums.ReportFormat;
import com.ammgroup.sep.jreports.service.JReportsService;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.EstadoReclamacion;
import com.ammgroup.sep.model.Reclamacion;
import com.ammgroup.sep.model.Socio;
import com.ammgroup.sep.repository.ReclamacionRepository;
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
public class ReclagesoclController implements Initializable {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	CrudDAOSoc<Socio> soccrud;
	
	@Autowired
	CrudDAOAgen<Agencia> agecrud;
	
	@Autowired
	CrudDAORecl<Reclamacion> reclamCrud;
	
	@Autowired
	private ReclamacionRepository reclRepository;
	
	@Autowired
	JReportsService jrserv;
	
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
	private Button bedit;
	
	@FXML
	private Button bdelete;
	
	@FXML
	private Button bview;
	
	@FXML
	private Button bprint;
	
	@FXML
	private Button bhrecl;

	@FXML
	private Button bclose;
	
	@FXML
	private Label lbmsg;
	
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
    void bviewOnAction(ActionEvent event) {
		
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
    void bprintOnAction(ActionEvent event) throws Exception {
		
		jrserv.generateListReport("reclaml.jrxml", reclamCrud.getItemsList(), ReportFormat.PREVIEW, null, "Listado de reclamaciones");

	}
	
    @FXML
    void bhreclOnAction(ActionEvent event) {

		reclamCrud.setAction(CrudAction.ADD);
		reclamCrud.setIndex(-1);
		
		try {
			mutils.loadForm("reclamdt.fxml", "Añadir nueva reclamación");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		refreshForm();
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

		try {
			Optional<Agencia> ageOpt = Optional.ofNullable(agecrud.getDao());
				ageOpt.ifPresent((x) -> {
					//Facturas only can be generated when accessing using socios forms
				});
		} catch (Exception e) {
			//When is called from Socios
		}

		refreshForm();
	}
	
	private void refreshForm() {
		
		var listwrapper = new Object(){ List<Reclamacion> itemslist; };

		try {
			Optional<Socio> socOpt = Optional.ofNullable(soccrud.getDao());
				socOpt.ifPresent((x) -> {
					Optional<Sort> sortOpt = Optional.ofNullable(mutils.getDefaultReclamacionesSort());
						sortOpt.ifPresentOrElse((y) -> {
							listwrapper.itemslist = reclRepository.findBySocio(x, y);
						}, () -> {
							listwrapper.itemslist = reclRepository.findBySocio(x);
						});
				});
		} catch (Exception e) {
			//When is called from Agencias
		}
		
		try {
			Optional<Agencia> ageOpt = Optional.ofNullable(agecrud.getDao());
				ageOpt.ifPresent((x) -> {
					Optional<Sort> sortOpt = Optional.ofNullable(mutils.getDefaultReclamacionesSort());
						sortOpt.ifPresentOrElse((y) -> {
							listwrapper.itemslist = reclRepository.findByAgencia(x, y);
						}, () -> {
							listwrapper.itemslist = reclRepository.findByAgencia(x);
						});
			});
		} catch (Exception e) {
			//When is called from Socios
		}
		
		reclamCrud.setDao(null);
		reclamCrud.setItemsList(listwrapper.itemslist);
			
		treclams.setItems(FXCollections.observableList(listwrapper.itemslist));
		treclams.refresh();
		
		//To count the items in list
		int items = treclams.getItems().size();
		
		lbmsg.setText("Encontrada" + ((items > 1)?"s" : "") + " " + items + " reclamación" + ((items > 1)?"es" : "") + ".");

	}
}
