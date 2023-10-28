package com.ammgroup.sep.controller;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAOAgen;
import com.ammgroup.sep.controller.config.crud.CrudDAOFact;
import com.ammgroup.sep.controller.dao.PaFactura;
import com.ammgroup.sep.controller.config.crud.CrudDAOSoc;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.jreports.config.enums.ReportFormat;
import com.ammgroup.sep.jreports.service.JReportsService;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.Factura;
import com.ammgroup.sep.model.ItemFactura;
import com.ammgroup.sep.model.SerieFacturas;
import com.ammgroup.sep.model.Socio;
import com.ammgroup.sep.repository.FacturaRepository;
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
public class FacagesoclController implements Initializable {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	CrudDAOSoc<Socio> soccrud;
	
	@Autowired
	CrudDAOAgen<Agencia> agecrud;
	
	@Autowired
	CrudDAOFact<Factura> faccrud;
	
	@Autowired
	PaFactura pafactdao;
	
	@Autowired
	FacturaRepository factRepository;
	
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
	private Button bedit;
	
	@FXML
	private Button bdelete;
	
	@FXML
	private Button bview;

	@FXML
	private Button bplist;
	
	@FXML
	private Button bprint;
	
	@FXML
	private Button bpdf;
	
	@FXML
	private Button bhfact;
	
	@FXML
	private Button pafact;

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
					
					lbmsg.setText("Las facturas rectificativas o rectificadas no pueden editarse");
				}
	   			
	   		}, () -> {
	   			
	   			lbmsg.setText("Debe seleccionar una factura");
	   			
	   		});
	}
	
	@FXML
    void bdeleteOnAction(ActionEvent event) {

		Optional<Factura> facOpt = Optional.ofNullable(tfacturas.getSelectionModel().getSelectedItem());
			facOpt.ifPresentOrElse((x) -> {
				
				faccrud.setAction(CrudAction.DELETE);
				faccrud.setIndex(tfacturas.getSelectionModel().getSelectedIndex());
				faccrud.setDao(tfacturas.getSelectionModel().getSelectedItem());
				
				try {
					mutils.loadForm("factdt.fxml", "Borrar factura");
				} catch (Exception e) {
					e.printStackTrace();
				}
	    			
	   			refreshForm();
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
    void bplistOnAction(ActionEvent event) throws Exception {
		
		if (tfacturas.getItems().size() > 0) {
			
			jrserv.generateListReport("factl.jrxml", faccrud.getItemsList(), ReportFormat.PREVIEW, null, "Listado de facturas");

		} else {
			
			lbmsg.setText("No hay elementos para generar un listado.");
		}
		

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
    void bhfactOnAction(ActionEvent event) throws IOException {
		
		try {
			mutils.loadForm("hfact.fxml", "Hacer factura");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//To refresh the list of Facturas
		refreshForm();
	}
	
	@FXML
    void bpafactOnAction(ActionEvent event) {
		
		//First check if the factura is PROFORMA
		Optional<Factura> facOpt = Optional.ofNullable(tfacturas.getSelectionModel().getSelectedItem());
	    	//0
			facOpt.ifPresentOrElse((x) -> {
				
				try {

					if (x.getSerie().isFacturasProforma()) {
						
						//If the factura is PROFORMA select the serie and fecha to the new factura
						try {
							
							//Clean all previous data
							pafactdao.clean();

							mutils.loadForm("pafact.fxml", "Generar factura desde proforma");
							
							//All data needed is provided and checked in selection form
							
					    	//Generate the new Factura (el método debe devolver una factura)
			    			Optional<Factura> factOpt = Optional.ofNullable(crearFacturaDesdeProforma(x));
			    			
			    			//Finally save Factura (devuelta por el método anterior)
			    			factOpt.ifPresentOrElse((z) -> {factRepository.save(z);}, () -> {lbmsg.setText("Se ha producido un error al generar la factura");});

							//To refresh the list of Facturas
							refreshForm();
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					} else {
						
						//If the factura is not PROFORMA show message
						lbmsg.setText("La factura seleccionada debe ser PROFORMA");
					}
					    		
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			//0
			}, () -> {
				
				lbmsg.setText("Debe seleccionar una factura");
			//0
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
		
		Optional<Agencia> ageOpt = Optional.ofNullable(agecrud.getDao());
			ageOpt.ifPresent((x) -> {
				//Facturas only can be generated when accessing using socios forms
				bhfact.setVisible(false);
			});
		
		refreshForm();

	}
	
	private void refreshForm() {
		
		var listwrapper = new Object(){ List<Factura> itemslist; };

		Optional<Socio> socOpt = Optional.ofNullable(soccrud.getDao());
			socOpt.ifPresent((x) -> { 
				
				Optional<Sort> sortOpt = Optional.ofNullable(mutils.getDefaultFacturasSort());
					sortOpt.ifPresentOrElse((y) -> {
						listwrapper.itemslist = factRepository.findFacturasOfSocio(x, y);
					}, () -> {
						listwrapper.itemslist = factRepository.findFacturasOfSocio(x);
					});
			});

		Optional<Agencia> ageOpt = Optional.ofNullable(agecrud.getDao());
			ageOpt.ifPresent((x) -> { 
				
				Optional<Sort> sortOpt = Optional.ofNullable(mutils.getDefaultFacturasSort());
					sortOpt.ifPresentOrElse((y) -> {
						listwrapper.itemslist = factRepository.findFacturasOfAgencia(x, y);
					}, () -> {
						listwrapper.itemslist = factRepository.findFacturasOfAgencia(x);
					});
			});
		
		faccrud.setDao(null);
		faccrud.setItemsList(listwrapper.itemslist);
		
		tfacturas.setItems(FXCollections.observableList(listwrapper.itemslist));
		tfacturas.refresh();
		
		//To count the items in list
		int items = tfacturas.getItems().size();
		
		lbmsg.setText("Encontrada" + ((items > 1)?"s" : "") + " " + items + " factura" + ((items > 1)?"s" : "") + ".");
		
	}
	
	//TODO se tiene que pasar la fecha de la factura tras comprobar que esté bien
	private Factura crearFacturaDesdeProforma(Factura prof) {

		Factura nf = new Factura();

		try {

	    	//Set the fecha factura, ultima actualizacion and fecha emision 
			Date dt = Date.from(Instant.now());
			nf.setFechaFactura(pafactdao.getFechaFactura());
			nf.setFechaEmision(dt);
			nf.setUltimaActualizacion(dt);
	
			//Set the selected Serie de facturas
			nf.setSerie(pafactdao.getSerieFacturas());
	
			//This variable is to generate the numero compuesto of the new factura
	    	var numwrapper = new Object(){ String numInic = ""; Integer num = 0; int year = 0; };
	    	
			Optional<String> strSf = Optional.ofNullable(pafactdao.getSerieFacturas().getTextoInicioNumeracion());
			strSf.ifPresent((x) -> {
				numwrapper.numInic = x;
			});
		
			numwrapper.year = mutils.getYearFromDate(dt);
			
			Optional<Integer> numSf = Optional.ofNullable(factRepository.getMaximumFacturaNumberBySerie(pafactdao.getSerieFacturas(), mutils.getDateFromString("01-01-" + numwrapper.year, mutils.DATE_FORMAT), mutils.getDateFromString("31-12-" + numwrapper.year, mutils.DATE_FORMAT)));
			numSf.ifPresentOrElse((x) -> {
				numwrapper.num = x.intValue() + 1;
			}, () -> {
				numwrapper.num = 1;
			});
			
			nf.setNumero(numwrapper.num);
			nf.setNumeroCompuesto(numwrapper.numInic + "-" + (numwrapper.year - 2000) + "-" + "0".repeat(3-(numwrapper.num).toString().length()) + numwrapper.num);
	
			//Pasamos de la proforma a la factura generada el resto de datos
			nf.setFacturacionAutomatica(prof.isFacturacionAutomatica());
			nf.setSocio(prof.getSocio());
			nf.setAgencia(prof.getAgencia());
			nf.setCifnif(prof.getCifnif());
			nf.setTitular(prof.getTitular());
			nf.setDireccion(prof.getDireccion());
			nf.setImporteGastosEnvio(prof.getImporteGastosEnvio());
			
			//Get the set of items
			for (ItemFactura item : prof.getItemsFactura()) {
				
				ItemFactura ni = new ItemFactura();
				ni.setFactura(nf);
				ni.setConcepto(item.getConcepto());
				ni.setImporte(item.getImporte());
				
				nf.getItemsFactura().add(ni);
			}
			
			nf.setImporteTotalItems(prof.getImporteTotalItems());
			nf.setDescuento(prof.getDescuento());
			nf.setPorcentajeDescuento(prof.getPorcentajeDescuento());
			nf.setImporteDescuento(prof.getImporteDescuento());
			nf.setImporteBaseImponible(prof.getImporteBaseImponible());
			nf.setTipoIVA(prof.getTipoIVA());
			nf.setPorcentajeTipoIVA(prof.getPorcentajeTipoIVA());
			nf.setImporteTipoIVA(prof.getImporteTipoIVA());
			nf.setImporteTotal(prof.getImporteTotal());
			nf.setExisteRectificativa(prof.isExisteRectificativa());
			nf.setFacturaRectificada(prof.getFacturaRectificada());
			nf.setEstadoFactura(prof.getEstadoFactura());
			nf.setFormaPago(prof.getFormaPago());
			nf.setReferencia(prof.getReferencia());
			nf.setMarcador(prof.getMarcador());
			nf.setTextoComplementario(prof.getTextoComplementario());
			nf.setTextoFormaPago(prof.getTextoFormaPago());
			nf.setFacturaFirmada(prof.isFacturaFirmada());
			nf.setTextoRectificativa(prof.getTextoRectificativa());
			//Anotate in Anotaciones the PROFORMA source of the factura
			nf.setAnotaciones("Factura generada a partir de la factura PROFORMA con numero " + prof.getNumeroCompuesto() + ". " + prof.getAnotaciones());

			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			nf = null;
		}

		return nf;
	}

}
