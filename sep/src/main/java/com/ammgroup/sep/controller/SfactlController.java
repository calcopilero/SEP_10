package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAO;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.SerieFacturas;
import com.ammgroup.sep.repository.SerieFacturasRepository;
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
public class SfactlController implements Initializable {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAO<SerieFacturas> sfaccrud;
	
	@Autowired
	SerieFacturasRepository sfactRepo;
	
	@FXML
	private TableView<SerieFacturas> tsfact;
	
	@FXML
	private TableColumn<SerieFacturas, String> tcdescripcion;
	
	@FXML
	private TableColumn<SerieFacturas, String> tcfauto;
	
	@FXML
	private TableColumn<SerieFacturas, String> tcrect;
	
	@FXML
	private TableColumn<SerieFacturas, String> tcprof;
	
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
		
		sfaccrud.setAction(CrudAction.ADD);
		sfaccrud.setIndex(-1);
				
		mutils.loadForm("sfactdt.fxml", "Añadir nueva serie de facturas");
		
		refreshForm();
	}
	
	@FXML
    void beditOnAction(ActionEvent event) throws Exception {
		
		Optional<SerieFacturas> sfacOpt = Optional.ofNullable(tsfact.getSelectionModel().getSelectedItem());
			sfacOpt.ifPresentOrElse((x) -> {
				
				sfaccrud.setAction(CrudAction.EDIT);
				sfaccrud.setIndex(tsfact.getSelectionModel().getSelectedIndex());
				sfaccrud.setDao(x);
			
				try {
					mutils.loadForm("sfactdt.fxml", "Editar serie de facturas");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar una serie de facturas");
			});
	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) throws Exception {
		
		Optional<SerieFacturas> sfacOpt = Optional.ofNullable(tsfact.getSelectionModel().getSelectedItem());
			sfacOpt.ifPresentOrElse((x) -> {
				
				sfaccrud.setAction(CrudAction.DELETE);
				sfaccrud.setIndex(tsfact.getSelectionModel().getSelectedIndex());
				sfaccrud.setDao(x);
			
				try {
					mutils.loadForm("sfactdt.fxml", "Borrar serie de facturas");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar una serie de facturas");
			});
	}
	
	@FXML
    void bviewOnAction(ActionEvent event) throws Exception {
		
		Optional<SerieFacturas> sfacOpt = Optional.ofNullable(tsfact.getSelectionModel().getSelectedItem());
			sfacOpt.ifPresentOrElse((x) -> {
				
				sfaccrud.setAction(CrudAction.VIEW);
				sfaccrud.setIndex(tsfact.getSelectionModel().getSelectedIndex());
				sfaccrud.setDao(x);
			
				try {
					mutils.loadForm("sfactdt.fxml", "Ver serie de facturas");
				} catch (Exception e) {
					e.printStackTrace();
				}
					
				refreshForm();
			}, () -> {
				lbmsg.setText("Debe seleccionar una serie de facturas");
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
		tcdescripcion.setCellValueFactory(new PropertyValueFactory<SerieFacturas, String>("descripcion"));
		tcfauto.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String fauto; };
			
			Optional<Boolean> fautoOpt = Optional.ofNullable(c.getValue().isAutomatica());
				fautoOpt.ifPresentOrElse((x) -> {
					if (x) {
						strwrapper.fauto = "SI" ;	
					} else {
						strwrapper.fauto = "NO" ;	
					}
				}, () -> {
					strwrapper.fauto = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.fauto);
				
		});
		
		tcrect.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String rect; };
			
			Optional<Boolean> rectOpt = Optional.ofNullable(c.getValue().isRectificativas());
				rectOpt.ifPresentOrElse((x) -> {
					if (x) {
						strwrapper.rect = "SI" ;	
					} else {
						strwrapper.rect = "NO" ;	
					}
				}, () -> {
					strwrapper.rect = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.rect);
				
		});
		
		tcprof.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String prof; };
			
			Optional<Boolean> profOpt = Optional.ofNullable(c.getValue().isFacturasProforma());
				profOpt.ifPresentOrElse((x) -> {
					if (x) {
						strwrapper.prof = "SI" ;	
					} else {
						strwrapper.prof = "NO" ;	
					}
				}, () -> {
					strwrapper.prof = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.prof);
				
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
		
		tsfact.setItems(FXCollections.observableList(sfactRepo.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));

		tsfact.refresh();
		
		//To count the items in list
		int items = tsfact.getItems().size();
		
		lbmsg.setText("Encontrada" + ((items > 1)?"s" : "") + " " + items + " serie" + ((items > 1)?"s" : "") + " de facturas");

	}

}
