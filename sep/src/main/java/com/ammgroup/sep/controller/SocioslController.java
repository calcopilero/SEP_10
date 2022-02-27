package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.config.SEPPropertiesFile;
import com.ammgroup.sep.controller.config.crud.CrudDAOSoc;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.controller.config.filter.SocioFilter;
import com.ammgroup.sep.jreports.config.enums.ReportFormat;
import com.ammgroup.sep.jreports.service.JReportsService;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.FormaPago;
import com.ammgroup.sep.model.ModalidadSocio;
import com.ammgroup.sep.model.ModoAcceso;
import com.ammgroup.sep.model.MotivoBaja;
import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.model.Socio;
import com.ammgroup.sep.repository.SocioRepository;
import com.ammgroup.sep.repository.specifications.SociosSpecifications;
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
public class SocioslController implements Initializable {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private CrudDAOSoc<Socio> socrud;
	
	@Autowired
	SocioFilter sofilter;
	
	@Autowired
	SociosSpecifications sospec;
	
	@Autowired
	SocioRepository socioRepository;
	
	@Autowired
	SEPPropertiesFile sepprop;
	
	@Autowired
	JReportsService jrservice;
	
	@FXML
	private TableView<Socio> tsocios;
	
	@FXML
	private TableColumn<Socio, String> tcnsoc;
	
	@FXML
	private TableColumn<Socio, String> tcnombre;
	
	@FXML
	private TableColumn<Socio, String> tcapellidos;
	
	@FXML
	private TableColumn<Socio, String> tcagencia;
	
	@FXML
	private TableColumn<Socio, String> tcfalta;
	
	@FXML
	private TableColumn<Socio, String> tcmod;
	
	@FXML
	private TableColumn<Socio, String> tcmacces;
	
	@FXML
	private TableColumn<Socio, String> tcfpago;
	
	@FXML
	private TableColumn<Socio, String> tcfact;
	
	@FXML
	private TableColumn<Socio, String> tcldist;
	
	@FXML
	private TableColumn<Socio, String> tcprov;
	
	@FXML
	private TableColumn<Socio, String> tcpais;
	
	@FXML
	private TableColumn<Socio, String> tccjdir;
	
	@FXML
	private TableColumn<Socio, String> tcjdiract;
	
	@FXML
	private TableColumn<Socio, String> tcfbaja;
	
	@FXML
	private TableColumn<Socio, String> tcmbaja;
	
	@FXML
	private TableColumn<Socio, String> tccsep;
	
	@FXML
	private Button badd;
	
	@FXML
	private Button bedit;
	
	@FXML
	private Button bdelete;
	
	@FXML
	private Button bview;
	
	@FXML
	private Button bprint;
	
	@FXML
	private Button bprintlab;

	@FXML
	private Button bcmod;
	
	@FXML
	private Button bclose;

	@FXML
	private Label lbmsg;
	
	@FXML
    void baddOnAction(ActionEvent event) throws Exception {
		
		socrud.setAction(CrudAction.ADD);
		socrud.setIndex(-1);
		
		mutils.loadForm("sociosdt.fxml", "Añadir nuevo socio");
		
		refreshForm();

	}
	
	@FXML
    void beditOnAction(ActionEvent event) {
		
		Optional<Socio> socOpt = Optional.ofNullable(tsocios.getSelectionModel().getSelectedItem());
			socOpt.ifPresentOrElse((x) -> {

				socrud.setAction(CrudAction.EDIT);
				socrud.setIndex(tsocios.getSelectionModel().getSelectedIndex());
				socrud.setDao(tsocios.getSelectionModel().getSelectedItem());
			
	   			try {
	   				mutils.loadForm("sociosdt.fxml", "Editar socio");
				} catch (Exception e) {
					e.printStackTrace();
				}
	    			
	   			refreshForm();
	   			
			}, () -> {
				
				lbmsg.setText("Debe seleccionar un socio");
				
	   		});

	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) {
		
		Optional<Socio> socOpt = Optional.ofNullable(tsocios.getSelectionModel().getSelectedItem());
			socOpt.ifPresentOrElse((x) -> {

				socrud.setAction(CrudAction.DELETE);
				socrud.setIndex(tsocios.getSelectionModel().getSelectedIndex());
				socrud.setDao(tsocios.getSelectionModel().getSelectedItem());
			
	   			try {
	   				mutils.loadForm("sociosdt.fxml", "Borrar socio");
				} catch (Exception e) {
					e.printStackTrace();
				}
	    			
	   			refreshForm();
	   			
			}, () -> {
				
				lbmsg.setText("Debe seleccionar un socio");
				
	   		});
	}
	
	@FXML
    void bviewOnAction(ActionEvent event) {
    	
		Optional<Socio> socOpt = Optional.ofNullable(tsocios.getSelectionModel().getSelectedItem());
			socOpt.ifPresentOrElse((x) -> {

				socrud.setAction(CrudAction.VIEW);
				socrud.setIndex(tsocios.getSelectionModel().getSelectedIndex());
				socrud.setDao(tsocios.getSelectionModel().getSelectedItem());
			
	   			try {
	   				mutils.loadForm("sociosdt.fxml", "Consultar socio");
				} catch (Exception e) {
					e.printStackTrace();
				}
	    			
	   			refreshForm();
	   			
			}, () -> {
				
				lbmsg.setText("Debe seleccionar un socio");
				
	   		});
    }
	
	@FXML
    void bfilterOnAction(ActionEvent event) throws Exception {
		
		mutils.loadForm("sociosfi.fxml", "Filtrar socios");

		refreshForm();
		
	}
	
	@FXML
    void bprintOnAction(ActionEvent event) throws Exception {
		
		if (tsocios.getItems().size() > 0) {
			
			jrservice.generateListReport("sociosl.jrxml", socrud.getItemsList(), ReportFormat.PREVIEW, null, "Listado de socios");
			
		} else {
			
			lbmsg.setText("No hay elementos para generar un listado.");
		}
	}
	
	@FXML
    void bprintlabOnAction(ActionEvent event) throws Exception {
		
		if (tsocios.getItems().size() > 0) {
			
			jrservice.generateListReport("soclab.jrxml", socrud.getItemsList(), ReportFormat.PREVIEW, null, "Etiquetas de socios");

		} else {
			
			lbmsg.setText("No hay elementos para generar un listado.");
		}
	}
	
	@FXML
    void bcmodOnAction(ActionEvent event) throws Exception {
		
		mutils.loadForm("camod.fxml", "Cambiar socios de modalidad");

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
		tcnsoc.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String nsoc; };
			
			Optional<Integer> nsocOpt = Optional.ofNullable(c.getValue().getCodigoSocio());
				nsocOpt.ifPresentOrElse((x) -> {
					strwrapper.nsoc = x.toString();	
				}, () -> {
					strwrapper.nsoc = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.nsoc);
				
		});
		tcnsoc.setStyle("-fx-alignment: CENTER-RIGHT;");
		
		tcnombre.setCellValueFactory(new PropertyValueFactory<Socio, String>("nombre"));
		tcapellidos.setCellValueFactory(new PropertyValueFactory<Socio, String>("apellidos"));
		
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
		
		tcfalta.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String falta; };
			
			Optional<Date> dateOpt = Optional.ofNullable(c.getValue().getFechaAlta());
			dateOpt.ifPresentOrElse((x) -> {
				strwrapper.falta = mutils.getStringFromDate(x, mutils.DATE_FORMATL);	
			}, () -> {
				strwrapper.falta = "";
			} );
			
			return new ReadOnlyStringWrapper(strwrapper.falta);
				
		});
		
		tcmod.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String mod; };
			
			Optional<ModalidadSocio> msocOpt = Optional.ofNullable(c.getValue().getModalidad());
				msocOpt.ifPresentOrElse((x) -> {
					strwrapper.mod = x.toString();	
				}, () -> {
					strwrapper.mod = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.mod);
				
		});
		
		tcmacces.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String macces; };
			
			Optional<ModoAcceso> macOpt = Optional.ofNullable(c.getValue().getModoAcceso());
				macOpt.ifPresentOrElse((x) -> {
					strwrapper.macces = x.toString();	
				}, () -> {
					strwrapper.macces = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.macces);
				
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
		
		tcfact.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String fact; };
			
			Optional<Boolean> fautoOpt = Optional.ofNullable(c.getValue().isFactura());
				fautoOpt.ifPresentOrElse((x) -> {
					if (x) {
						strwrapper.fact = "SI" ;	
					} else {
						strwrapper.fact = "NO" ;	
					}
				}, () -> {
					strwrapper.fact = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.fact);
				
		});
		
		tcldist.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String ldist; };
			
			Optional<Boolean> fautoOpt = Optional.ofNullable(c.getValue().isListaDistribucion());
				fautoOpt.ifPresentOrElse((x) -> {
					if (x) {
						strwrapper.ldist = "SI" ;	
					} else {
						strwrapper.ldist = "NO" ;	
					}
				}, () -> {
					strwrapper.ldist = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.ldist);
				
		});
		
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
		
		tccjdir.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String cjdir; };
			
			Optional<String> strOpt = Optional.ofNullable(c.getValue().getCargosJuntaDirectiva());
				strOpt.ifPresentOrElse((x) -> {
					strwrapper.cjdir = x.toString();	
				}, () -> {
					strwrapper.cjdir = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.cjdir);
				
		});
		
		tcjdiract.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String jdiract; };
			
			Optional<Boolean> fautoOpt = Optional.ofNullable(c.getValue().isJuntaDirectivaActual());
				fautoOpt.ifPresentOrElse((x) -> {
					if (x) {
						strwrapper.jdiract = "SI" ;	
					} else {
						strwrapper.jdiract = "NO" ;	
					}
				}, () -> {
					strwrapper.jdiract = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.jdiract);
				
		});
		
		tcfbaja.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String fbaja; };
			
			Optional<Date> dateOpt = Optional.ofNullable(c.getValue().getFechaBaja());
				dateOpt.ifPresentOrElse((x) -> {
					strwrapper.fbaja = mutils.getStringFromDate(x, mutils.DATE_FORMATL);	
				}, () -> {
					strwrapper.fbaja = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.fbaja);
				
		});
		
		tcmbaja.setCellValueFactory(c-> {
			
			var strwrapper = new Object(){ String mbaja; };
			
			Optional<MotivoBaja> mbajaOpt = Optional.ofNullable(c.getValue().getMotivoBaja());
				mbajaOpt.ifPresentOrElse((x) -> {
					strwrapper.mbaja = x.toString();	
				}, () -> {
					strwrapper.mbaja = "";
				} );
			
			return new ReadOnlyStringWrapper(strwrapper.mbaja);
				
		});
		
		tccsep.setCellValueFactory(new PropertyValueFactory<Socio, String>("contactoSep"));
		
    	// Populating the table automatically
		//tsocios.setItems(FXCollections.observableList(socioRepository.findAll()));
		
		// Populating the table manually
		//ObservableList<Socio> sociosOL = FXCollections.observableList(new ArrayList<Socio>());
		//socioRepository.findAll().forEach((p) -> {sociosOL.add(p);});
		//tsocios.setItems(sociosOL);
		
		//Set the default value in socfilter
		sofilter.setActivo(true);
		
		refreshForm();
		
	}
	
	private void refreshForm() {
		
		var listwrapper = new Object(){ List<Socio> itemslist; int cfilter = 0;};
		
		//Check if there's any filter
		Optional<SocioFilter> optSofilter = Optional.ofNullable(sofilter);
			optSofilter.ifPresentOrElse((x) -> {
			
				listwrapper.cfilter = sofilter.containsFilters();
				
				if (listwrapper.cfilter > 0) {
					listwrapper.itemslist = sospec.getFilteredSocios(sofilter);
				} else {
					listwrapper.itemslist = getAllSocios();
				}
	
			}, () -> {
				listwrapper.itemslist = getAllSocios();
			});

		socrud.setDao(null);
		socrud.setItemsList(listwrapper.itemslist);

		tsocios.setItems(FXCollections.observableList(listwrapper.itemslist));
		tsocios.refresh();
		
		//To count the items in list
		int items = tsocios.getItems().size();
		
		lbmsg.setText("Encontrado" + ((items > 1)?"s" : "") + " " + items + " socio" + ((items > 1)?"s" : "") + "." + ((listwrapper.cfilter > 0)? " (" + listwrapper.cfilter + " filtro" + ((listwrapper.cfilter > 1)?"s" : "") + " aplicado" + ((listwrapper.cfilter > 1)?"s" : "") + ")" : ""));

	}
	
	private List<Socio> getAllSocios() {
		
		var listwrapper = new Object(){ List<Socio> itemslist; };
		
		Optional<Sort> sortOpt = Optional.ofNullable(mutils.getDefaultSociosSort());
			sortOpt.ifPresentOrElse((x) -> {
				listwrapper.itemslist = socioRepository.findAll(x);
			}, () -> {
				listwrapper.itemslist = socioRepository.findAll();
			});
		
		return listwrapper.itemslist;

	}
}
