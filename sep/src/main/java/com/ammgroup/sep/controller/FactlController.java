package com.ammgroup.sep.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.config.SEPPropertiesFile;
import com.ammgroup.sep.controller.config.crud.CrudDAOFact;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.controller.config.filter.FacturaFilter;
import com.ammgroup.sep.jreports.config.enums.ReportFormat;
import com.ammgroup.sep.jreports.service.JReportsService;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.EstadoFactura;
import com.ammgroup.sep.model.Factura;
import com.ammgroup.sep.model.FormaPago;
import com.ammgroup.sep.model.SerieFacturas;
import com.ammgroup.sep.repository.FacturaRepository;
import com.ammgroup.sep.repository.specifications.FacturasSpecifications;
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
public class FactlController implements Initializable {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	CrudDAOFact<Factura> faccrud;
	
	@Autowired
	FacturaFilter facfilter;
	
	@Autowired
	FacturaRepository factRepository;
	
	@Autowired
	FacturasSpecifications facspec;
	
	@Autowired
	SEPPropertiesFile sepprop;
	
	@Autowired
	JReportsService jrserv;
	
	@FXML
	private TableView<Factura> tfacturas;
	
	@FXML
	private TableColumn<Factura, String> tcnumero;
	
	@FXML
	private TableColumn<Factura, String> tctitular;
	
	@FXML
	private TableColumn<Factura, String> tccifnif;
	
	@FXML
	private TableColumn<Factura, Date> tcffactura;
	
	@FXML
	private TableColumn<Factura, Double> tcimporte;
	
	@FXML
	private TableColumn<Factura, String> tcsfact;
	
	@FXML
	private TableColumn<Factura, String> tcfpago;
	
	@FXML
	private TableColumn<Factura, String> tcest;
	
	@FXML
	private TableColumn<Factura, String> tcagencia;

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
	private Button bpdf;
	
	@FXML
	private Button bplist;

	@FXML
	private Button bclose;
	
	@FXML
	private Label lbmsg;
	
	@FXML
    void beditOnAction(ActionEvent event) {
		
		Optional<Factura> facOpt = Optional.ofNullable(tfacturas.getSelectionModel().getSelectedItem());
		facOpt.ifPresentOrElse((x) -> {
			
			//Check if factura is Rectificada or Rectificativa
			if (!(x.isExisteRectificativa() || x.getSerie().isRectificativas())) {

				faccrud.setAction(CrudAction.EDIT);
				faccrud.setIndex(tfacturas.getSelectionModel().getSelectedIndex());
				faccrud.setDao(tfacturas.getSelectionModel().getSelectedItem());
			
				try {
					mutils.loadForm("factdt.fxml", "Editar factura");
				} catch (Exception e) {
					e.printStackTrace();
				}
	    			
	   			refreshForm();
	   		
			} else {
				
				lbmsg.setText("Las facturas rectificativas/rectificadas no pueden editarse ni borrarse");
			}
   			
   		}, () -> {
   			
   			lbmsg.setText("Debe seleccionar una factura");
   			
   		});
	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) {
		
		Optional<Factura> facOpt = Optional.ofNullable(tfacturas.getSelectionModel().getSelectedItem());
		facOpt.ifPresentOrElse((x) -> {
	
			//Check if factura is Rectificada or Rectificativa
			if (!(x.isExisteRectificativa() || x.getSerie().isRectificativas())) {

				faccrud.setAction(CrudAction.DELETE);
				faccrud.setIndex(tfacturas.getSelectionModel().getSelectedIndex());
				faccrud.setDao(tfacturas.getSelectionModel().getSelectedItem());
	
				try {
					mutils.loadForm("factdt.fxml", "Borrar factura");
				} catch (Exception e) {
					e.printStackTrace();
				}
	    			
	   			refreshForm();
	   			
			} else {
				
				lbmsg.setText("Las facturas rectificativas/rectificadas no pueden editarse ni borrarse");
			}
   		}, () -> {
   			
   			lbmsg.setText("Debe seleccionar una factura");
   			
   		});
	}
	
	@FXML
    void bviewOnAction(ActionEvent event) {
    
		Optional<Factura> facOpt = Optional.ofNullable(tfacturas.getSelectionModel().getSelectedItem());
			facOpt.ifPresentOrElse((x) -> {
				
				faccrud.setAction(CrudAction.VIEW);
				faccrud.setIndex(tfacturas.getSelectionModel().getSelectedIndex());
				faccrud.setDao(tfacturas.getSelectionModel().getSelectedItem());
	
				try {
					mutils.loadForm("factdt.fxml", "Ver factura");
				} catch (Exception e) {
					e.printStackTrace();
				}
	    			
	   			refreshForm();
	   		}, () -> {
	   			
	   			lbmsg.setText("Debe seleccionar una factura");
	   			
	   		});
    }
	
	@FXML
    void bfilterOnAction(ActionEvent event) throws Exception {
		
		mutils.loadForm("factfi.fxml", "Filtrar facturas");

		refreshForm();
		
	}
	
	@FXML
    void bprintOnAction(ActionEvent event) {
		
		Optional<Factura> facOpt = Optional.ofNullable(tfacturas.getSelectionModel().getSelectedItem());
			facOpt.ifPresentOrElse((x) -> {
				
				try {
					jrserv.generateFacturaReport("factura.jrxml", x, ReportFormat.PREVIEW, null, "Factura");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			}, () -> {

				lbmsg.setText("Debe seleccionar una factura");
			});
	}
	
	@FXML
    void bpdfOnAction(ActionEvent event) {
		
		Optional<Factura> facOpt = Optional.ofNullable(tfacturas.getSelectionModel().getSelectedItem());
			facOpt.ifPresentOrElse((x) -> {
				
				try {
					jrserv.generateFacturaReport("factura.jrxml", x, ReportFormat.PDF, x.getNumeroCompuesto() + ".pdf", null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			}, () -> {

				lbmsg.setText("Debe seleccionar una factura");
			});
	}
	
	@FXML
    void bplistOnAction(ActionEvent event) throws Exception{
		
		if (tfacturas.getItems().size() > 0) {
			
			jrserv.generateListReport("factl.jrxml", faccrud.getItemsList(), ReportFormat.PREVIEW, null, "Listado de facturas");

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
		tcnumero.setCellValueFactory(new PropertyValueFactory<Factura, String>("numeroCompuesto"));
		tctitular.setCellValueFactory(new PropertyValueFactory<Factura, String>("titular"));
		tccifnif.setCellValueFactory(new PropertyValueFactory<Factura, String>("cifnif"));
		tcffactura.setCellValueFactory(new PropertyValueFactory<Factura, Date>("fechaFactura"));
		mutils.configureColumnForDate(tcffactura);
		tcimporte.setCellValueFactory(new PropertyValueFactory<Factura, Double>("importeTotal"));
		mutils.configureColumnForDecimal(tcimporte);
		
		tcsfact.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String sfact; };
			
			Optional<SerieFacturas> sfacOpt = Optional.ofNullable(c.getValue().getSerie());
				sfacOpt.ifPresentOrElse((x) -> {
					strwrapper.sfact = x.toString();	
				}, () -> {
					strwrapper.sfact = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.sfact);
				
		});
		
		tcfpago.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String fpago; };
			
			Optional<FormaPago> macOpt = Optional.ofNullable(c.getValue().getFormaPago());
				macOpt.ifPresentOrElse((x) -> {
					strwrapper.fpago = x.toString();	
				}, () -> {
					strwrapper.fpago = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.fpago);
				
		});
		
		tcest.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String est; };
			
			Optional<EstadoFactura> macOpt = Optional.ofNullable(c.getValue().getEstadoFactura());
				macOpt.ifPresentOrElse((x) -> {
					strwrapper.est = x.toString();	
				}, () -> {
					strwrapper.est = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.est);
				
		});
		
		tcagencia.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String agencia; };
			
			Optional<Agencia> ageOpt = Optional.ofNullable(c.getValue().getAgencia());
				ageOpt.ifPresentOrElse((x) -> {
					strwrapper.agencia = x.toString();	
				}, () -> {
					strwrapper.agencia = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.agencia);
				
		});
		
		// Accessing repository indirectly through spring application context, not autowiring
		//SocioRepository socioRepository = springContext.getBean(SocioRepository.class);
		
		// Populating the table manually
		//ObservableList<Socio> sociosOL = FXCollections.observableList(new ArrayList<Socio>());
		//socioRepository.findAll().forEach((p) -> {sociosOL.add(p);});
		//tsocios.setItems(sociosOL);

		//Set the default filter
		var numwrapper = new Object(){ int year = 0; };

		numwrapper.year = mutils.getYearFromDate(mutils.getDateFromLocalDate(LocalDate.now()));
		facfilter.setFechaFacturaInicial(mutils.getDateFromString("01-01-" + numwrapper.year, mutils.DATE_FORMAT));
		facfilter.setFechaFacturaFinal(mutils.getDateFromString("31-12-" + numwrapper.year, mutils.DATE_FORMAT));
		
		refreshForm();

	}
	
	private void refreshForm() {

		var listwrapper = new Object(){ List<Factura> itemslist; int cfilter = 0;};
		
		//Check if there's any filter
		Optional<FacturaFilter> optFacfilter = Optional.ofNullable(facfilter);
			optFacfilter.ifPresentOrElse((x) -> {
				
				listwrapper.cfilter = facfilter.containsFilters();
				
				if (listwrapper.cfilter> 0) {
					listwrapper.itemslist = facspec.getFilteredFacturas(facfilter);
				} else {
					listwrapper.itemslist = getAllFacturas();
				}
			}, () -> {
				
				listwrapper.itemslist = getAllFacturas();
			});

		faccrud.setDao(null);
		faccrud.setItemsList(listwrapper.itemslist);
			
		tfacturas.setItems(FXCollections.observableList(listwrapper.itemslist));
		tfacturas.refresh();
		
		//To count the items in list
		int items = tfacturas.getItems().size();
		
		lbmsg.setText("Encontrada" + ((items > 1)?"s" : "") + " " + items + " factura" + ((items > 1)?"s" : "") + "." + ((listwrapper.cfilter > 0)? " (" + listwrapper.cfilter + " filtro" + ((listwrapper.cfilter > 1)?"s" : "") + " aplicado" + ((listwrapper.cfilter > 1)?"s" : "") + ")" : ""));

	}
	
	private List<Factura> getAllFacturas() {
		
		var listwrapper = new Object(){ List<Factura> itemslist; };
		
		Optional<Sort> sortOpt = Optional.ofNullable(mutils.getDefaultFacturasSort());
			sortOpt.ifPresentOrElse((x) -> {
				listwrapper.itemslist = factRepository.findAll(x);
			}, () -> {
				listwrapper.itemslist = factRepository.findAll();
			});
		
		return listwrapper.itemslist;

	}
}
