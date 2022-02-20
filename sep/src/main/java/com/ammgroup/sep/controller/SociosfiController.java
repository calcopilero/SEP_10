package com.ammgroup.sep.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.filter.SocioFilter;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.ModalidadSocio;
import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.model.ZonaPostal;
import com.ammgroup.sep.repository.AgenciaRepository;
import com.ammgroup.sep.repository.ModalidadSocioRepository;
import com.ammgroup.sep.repository.PaisRepository;
import com.ammgroup.sep.repository.ProvinciaRepository;
import com.ammgroup.sep.repository.ZonaPostalRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

@Component
public class SociosfiController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	SocioFilter socfilter;
	
	@Autowired
	PaisRepository paisRepository;
	
	@Autowired
	ProvinciaRepository provRepository;
	
	@Autowired
	ZonaPostalRepository zpostRepository;
	
	@Autowired
	ModalidadSocioRepository msocRepository;
	
	@Autowired
	AgenciaRepository agenciaRepository;
	
	@FXML
	private DatePicker dpfaltai;
	
	@FXML
	private DatePicker dpfaltaf;
	
    @FXML
    private CheckBox chactivos;
    
    @FXML
    private CheckBox chbajas;
	
	@FXML
	private TextField txapell;
	
	@FXML
	private TextField txnombre;
	
	@FXML
	private TextField txlocal;

	@FXML
	private TextField txcifnif;
	
	@FXML
	private ComboBox<Provincia> cbprov;

	@FXML
	private ComboBox<Pais> cbpais;
	
	@FXML
	private ComboBox<ZonaPostal> cbzpost;
	
	@FXML
	private TextField txemail;
	
	@FXML
	private TextField txtelefono;
	
	@FXML
	private ComboBox<ModalidadSocio> cbmod;
	
	@FXML
	private ComboBox<Agencia> cbagencia;
	
	@FXML
	private TextField txmarc;
	
    @FXML
    private CheckBox chfactura;
    
    @FXML
    private CheckBox chldist;
	
	@FXML
	private TextField txcjdir;
	
	@FXML
	private CheckBox chjdiract;
	
	@FXML
	private DatePicker dpfbajaini;
	
	@FXML
	private DatePicker dpfbajafin;
	
	@FXML
	private Label lbmsg1;
	
	@FXML
	private Label lbmsg2;

	@FXML
	private Button bfilter;
	
	@FXML
	private Button bclean;
	
	@FXML
	private Button bclose;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//Populate the combos
		cbprov.setItems(FXCollections.observableList(provRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbprov.getItems().add(null);
		cbpais.setItems(FXCollections.observableList(paisRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbpais.getItems().add(null);
		cbzpost.setItems(FXCollections.observableList(zpostRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbzpost.getItems().add(null);
		cbmod.setItems(FXCollections.observableList(msocRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbmod.getItems().add(null);
		cbagencia.setItems(FXCollections.observableList(agenciaRepository.findAll()));
		cbagencia.getItems().add(null);
		
	    //Setting the maximum number of characters of TextField
		txcifnif.addEventFilter(KeyEvent.KEY_TYPED, maxLength(12));
		txnombre.addEventFilter(KeyEvent.KEY_TYPED, maxLength(70));
		txapell.addEventFilter(KeyEvent.KEY_TYPED, maxLength(60));
		txlocal.addEventFilter(KeyEvent.KEY_TYPED, maxLength(70));
		txtelefono.addEventFilter(KeyEvent.KEY_TYPED, maxLength(12));
		txemail.addEventFilter(KeyEvent.KEY_TYPED, maxLength(80));
		txmarc.addEventFilter(KeyEvent.KEY_TYPED, maxLength(30));
		txcjdir.addEventFilter(KeyEvent.KEY_TYPED, maxLength(60));

		//Shove filters from Filter object into controls
		Optional<SocioFilter> optSocfilter = Optional.ofNullable(socfilter);
			optSocfilter.ifPresent((x) -> {
			
			if (socfilter.containsFilters() > 0) {
				//Datepicker is blank if Fecha alta is null 
				Optional<Date> optDate = Optional.ofNullable(x.getFechaAltaInicial());
					optDate.ifPresent((y) -> dpfaltai.setValue(mutils.obtainLocalDate(y)));
				optDate = Optional.ofNullable(x.getFechaAltaFinal());
					optDate.ifPresent((y) -> dpfaltaf.setValue(mutils.obtainLocalDate(y)));
				Optional<Boolean> boolOpt = Optional.ofNullable(x.getActivo());
					boolOpt.ifPresent((y) -> chactivos.setSelected(y));
				boolOpt = Optional.ofNullable(x.getBaja());
					boolOpt.ifPresent((y) -> chbajas.setSelected(y));
				txnombre.setText(socfilter.getNombre());
				txapell.setText(socfilter.getApellidos());
				txcifnif.setText(socfilter.getCifnif());
				txlocal.setText(socfilter.getLocalidad());
				Optional<Provincia> optProv = Optional.ofNullable(socfilter.getProvincia());
					optProv.ifPresent((y) -> { cbprov.getSelectionModel().select(mutils.searchIdInCombo(cbprov,y)); });
				Optional<Pais> optPais = Optional.ofNullable(socfilter.getPais());
					optPais.ifPresent((y) -> { cbpais.getSelectionModel().select(mutils.searchIdInCombo(cbpais, y)); });
				Optional<ZonaPostal> optZpost = Optional.ofNullable(socfilter.getZonaPostal());
					optZpost.ifPresent((y) -> { cbzpost.getSelectionModel().select(mutils.searchIdInCombo(cbzpost, y)); });
				txemail.setText(socfilter.getEmail());
				txtelefono.setText(socfilter.getTelefono());
				Optional<ModalidadSocio> optMsoc = Optional.ofNullable(socfilter.getModalidad());
					optMsoc.ifPresent((y) -> { cbmod.getSelectionModel().select(mutils.searchIdInCombo(cbmod, y)); });
				Optional<Agencia> optAge = Optional.ofNullable(socfilter.getAgencia());
					optAge.ifPresent((y) -> { cbagencia.getSelectionModel().select(mutils.searchIdInCombo(cbagencia, y)); });
				txmarc.setText(socfilter.getMarcador());
				boolOpt = Optional.ofNullable(x.getFactura());
					boolOpt.ifPresent((y) -> chfactura.setSelected(y));
				boolOpt = Optional.ofNullable(x.getListaDistribucion());
					boolOpt.ifPresent((y) -> chldist.setSelected(y));
				boolOpt = Optional.ofNullable(x.getJuntaDirectivaActual());
					boolOpt.ifPresent((y) -> chjdiract.setSelected(y));
				//Datepicker is blank if Fecha alta is null 
				optDate = Optional.ofNullable(x.getFechaBajaInicial());
					optDate.ifPresent((y) -> dpfbajaini.setValue(mutils.obtainLocalDate(y)));
				optDate = Optional.ofNullable(x.getFechaBajaFinal());
					optDate.ifPresent((y) -> dpfbajafin.setValue(mutils.obtainLocalDate(y)));
			}
			
		});

	}
	
	private EventHandler<KeyEvent> maxLength(final Integer i) {
        return new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {

                TextField tx = (TextField) arg0.getSource();
                
            	Optional<String> strOpt = Optional.ofNullable(tx.getText());
            	strOpt.ifPresent((x) -> {
            		if (tx.getText().length() >= i) arg0.consume();
                });
            }
        };
    }

    @FXML
    void chactivosOnAction(ActionEvent event) {

    	//Activos and bajas are not compatible
    	chbajas.setSelected(!chactivos.isSelected());
    	
    }
    
    @FXML
    void chbajasOnAction(ActionEvent event) {

    	//Activos and bajas are not compatible
    	chactivos.setSelected(!chbajas.isSelected());

    }
    
	@FXML
    void bfilterOnAction(ActionEvent event) {
		
		if (checkControls()) {
			
			//Shove filters into SocioFilter object
			fillFilterObjetct(socfilter);
			
	    	closeForm();
		}
		
	}
	
	@FXML
    void bcleanOnAction(ActionEvent event) {
		
		dpfaltai.setValue(null);
		dpfaltaf.setValue(null);
		chactivos.setSelected(false);
		chbajas.setSelected(false);
		txnombre.setText("");
		txapell.setText("");
		txcifnif.setText("");
		txlocal.setText("");
		cbprov.getSelectionModel().clearSelection();
		cbpais.getSelectionModel().clearSelection();
		cbzpost.getSelectionModel().clearSelection();
		txemail.setText("");
		txtelefono.setText("");
		txmarc.setText("");
		cbmod.getSelectionModel().clearSelection();
		cbagencia.getSelectionModel().clearSelection();
		chfactura.setSelected(false);
		chldist.setSelected(false);
		txcjdir.setText("");
		chjdiract.setSelected(false);
		dpfbajaini.setValue(null);
		dpfbajafin.setValue(null);

		//Shove filters into filter object
		fillFilterObjetct(socfilter);
		
	}
	
	private boolean checkControls() {
		
		boolean validControls = false;
		String msg = "";
		
		Optional<LocalDate> optDateIni = Optional.ofNullable(dpfaltai.getValue());
		Optional<LocalDate> optDateFin = Optional.ofNullable(dpfaltaf.getValue());
		
		//Both dates should be filled or empty
		if ((optDateIni.isPresent() && optDateFin.isPresent()) || (optDateIni.isEmpty() && optDateFin.isEmpty())) {
			validControls = true;
		} else {
			msg += "Debe especificar dos fechas de alta válidas.";
		}

		lbmsg1.setText(msg);
		
		optDateIni = Optional.ofNullable(dpfbajaini.getValue());
		optDateFin = Optional.ofNullable(dpfbajafin.getValue());
		
		//Both dates should be filled or empty
		if ((optDateIni.isPresent() && optDateFin.isPresent()) || (optDateIni.isEmpty() && optDateFin.isEmpty())) {
			validControls = true;
		} else {
			msg += "Debe especificar dos fechas de baja válidas.";
		}
		
		lbmsg2.setText(msg);
		
		return validControls;
	}
	
	private void fillFilterObjetct(SocioFilter sfilt) {
		
		Optional<SocioFilter> optSocfilter = Optional.ofNullable(sfilt);
			optSocfilter.ifPresent((x) -> {
				
				Optional<LocalDate> optDate = Optional.ofNullable(dpfaltai.getValue());
					optDate.ifPresentOrElse((y) -> { 
						x.setFechaAltaInicial(Date.from(y.atStartOfDay(mutils.getDefaultZoneId()).toInstant()));
					}, () -> {
						x.setFechaAltaInicial(null);
					});

				optDate = Optional.ofNullable(dpfaltaf.getValue());
					optDate.ifPresentOrElse((y) -> {
						x.setFechaAltaFinal(Date.from(y.atStartOfDay(mutils.getDefaultZoneId()).toInstant()));
					}, () -> {
						x.setFechaAltaFinal(null);
					});

				x.setActivo(chactivos.isSelected());
				x.setBaja(chbajas.isSelected());

				x.setNombre(txnombre.getText());
				x.setApellidos(txapell.getText());
				x.setCifnif(txcifnif.getText());
				x.setLocalidad(txlocal.getText());
				x.setProvincia(cbprov.getValue());
				x.setPais(cbpais.getValue());
				//x.setZonaPostal(cbzpost.getSelectionModel().getSelectedItem());
				x.setZonaPostal(cbzpost.getValue());
				x.setEmail(txemail.getText());
				x.setTelefono(txtelefono.getText());
				x.setModalidad(cbmod.getValue());
				x.setAgencia(cbagencia.getValue());
				x.setMarcador(txmarc.getText());
				x.setCargosJuntaDirectiva(txcjdir.getText());
				x.setJuntaDirectivaActual(chjdiract.isSelected());
				
				x.setFactura(chfactura.isSelected());
				x.setListaDistribucion(chldist.isSelected());
				
				optDate = Optional.ofNullable(dpfbajaini.getValue());
					optDate.ifPresentOrElse((y) -> { 
						x.setFechaBajaInicial(Date.from(y.atStartOfDay(mutils.getDefaultZoneId()).toInstant()));
					}, () -> {
						x.setFechaBajaInicial(null);
					});

				optDate = Optional.ofNullable(dpfbajafin.getValue());
					optDate.ifPresentOrElse((y) -> {
						x.setFechaBajaFinal(Date.from(y.atStartOfDay(mutils.getDefaultZoneId()).toInstant()));
					}, () -> {
						x.setFechaBajaFinal(null);
					});
			});
	}
	
	@FXML
    void bcloseOnAction(ActionEvent event) {

    	closeForm();

    }

	private void closeForm() {
		
		// get a handle to the stage
		Stage stage = (Stage) bclose.getScene().getWindow();
		// do what you have to do
		stage.close();
		
	}
}
