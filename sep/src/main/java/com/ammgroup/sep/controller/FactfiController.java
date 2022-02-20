package com.ammgroup.sep.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.filter.FacturaFilter;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.EstadoFactura;
import com.ammgroup.sep.model.FormaPago;
import com.ammgroup.sep.model.SerieFacturas;
import com.ammgroup.sep.repository.AgenciaRepository;
import com.ammgroup.sep.repository.EstadoFacturaRepository;
import com.ammgroup.sep.repository.FormaPagoRepository;
import com.ammgroup.sep.repository.SerieFacturasRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

@Component
public class FactfiController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	private FacturaFilter factfilt;
	
	@Autowired
	AgenciaRepository agenciaRepository;
	
	@Autowired 
	private SerieFacturasRepository sfactRepository;
	
	@Autowired 
	private EstadoFacturaRepository efactRepository;
	
	@Autowired
	FormaPagoRepository fpagoRepository;
	
	@FXML
	private DatePicker dpffacti;
	
	@FXML
	private DatePicker dpffactf;
	
    @FXML
    private TextField txcifnif;

    @FXML
    private TextField txdirec;

    @FXML
    private TextField txtitular;

    @FXML
    private ComboBox<Agencia> cbagencia;
    
    @FXML
    private TextField txrefer;

    @FXML
    private ComboBox<EstadoFactura> cbefact;

    @FXML
    private ComboBox<FormaPago> cbfpago;

    @FXML
    private ComboBox<SerieFacturas> cbsfact;

    @FXML
    private TextField txmarc;
    
	@FXML
	private Label lbmsg1;
	
	@FXML
	private Label lbmsg2;
    
    @FXML
    private Button bclean;

    @FXML
    private Button bclose;

    @FXML
    private Button bfilter;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {

		//Populate the combos
		cbsfact.setItems(FXCollections.observableList(sfactRepository.findByNotRectificativas()));
		cbsfact.getItems().add(null);
		cbagencia.setItems(FXCollections.observableList(agenciaRepository.findAll()));
		cbagencia.getItems().add(null);
		cbfpago.setItems(FXCollections.observableList(fpagoRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbfpago.getItems().add(null);
		cbefact.setItems(FXCollections.observableList(efactRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbefact.getItems().add(null);
		
	    //Setting the maximum number of characters of TextField
		txcifnif.addEventFilter(KeyEvent.KEY_TYPED, maxLength(12));
		txdirec.addEventFilter(KeyEvent.KEY_TYPED, maxLength(250));
		txtitular.addEventFilter(KeyEvent.KEY_TYPED, maxLength(150));
		txrefer.addEventFilter(KeyEvent.KEY_TYPED, maxLength(30));
		txmarc.addEventFilter(KeyEvent.KEY_TYPED, maxLength(30));
		
		//Shove filters from Filter object into controls
		Optional<FacturaFilter> optFacf = Optional.ofNullable(factfilt);
			optFacf.ifPresent((x) -> {
			
			if (x.containsFilters() > 0) {
				//Datepicker is blank if Fecha factura is null 
				Optional<Date> optDate = Optional.ofNullable(x.getFechaFacturaInicial());
					optDate.ifPresent((y) -> dpffacti.setValue(mutils.obtainLocalDate(y)));
				optDate = Optional.ofNullable(x.getFechaFacturaFinal());
					optDate.ifPresent((y) -> dpffactf.setValue(mutils.obtainLocalDate(y)));
				txtitular.setText(x.getTitular());
				txcifnif.setText(x.getCifnif());
				txdirec.setText(x.getDireccion());
				Optional<SerieFacturas> optSfac = Optional.ofNullable(x.getSerieFacturas());
					optSfac.ifPresent((y) -> { cbsfact.getSelectionModel().select(mutils.searchIdInCombo(cbsfact, y)); });
				Optional<Agencia> optAge = Optional.ofNullable(x.getAgencia());
					optAge.ifPresent((y) -> { cbagencia.getSelectionModel().select(mutils.searchIdInCombo(cbagencia, y)); });
				txrefer.setText(x.getReferencia());
				Optional<FormaPago> optFpag = Optional.ofNullable(x.getFormaPago());
					optFpag.ifPresent((y) -> { cbfpago.getSelectionModel().select(mutils.searchIdInCombo(cbfpago, y)); });
				Optional<EstadoFactura> optEfac = Optional.ofNullable(x.getEstadoFactura());
					optEfac.ifPresent((y) -> { cbefact.getSelectionModel().select(mutils.searchIdInCombo(cbefact, y)); });
				txmarc.setText(x.getMarcador());

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
    void bfilterOnAction(ActionEvent event) {
		
		if (checkControls()) {
			
			//Shove filters into FacturaFilter object
			fillFilterObjetct(factfilt);
			
	    	closeForm();
		}
		
	}
	
	@FXML
    void bcleanOnAction(ActionEvent event) {
	
		dpffacti.setValue(null);
		dpffactf.setValue(null);
		txtitular.setText("");;
		txcifnif.setText("");
		txdirec.setText("");
		cbsfact.getSelectionModel().clearSelection();
		cbagencia.getSelectionModel().clearSelection();
		txrefer.setText("");
		cbfpago.getSelectionModel().clearSelection();
		cbefact.getSelectionModel().clearSelection();
		txmarc.setText("");
		
		//Shove filters into AgencFilter object
		fillFilterObjetct(factfilt);
		
	}
	
	@FXML
    void bcloseOnAction(ActionEvent event) {

    	closeForm();

    }

	private boolean checkControls() {
		
		boolean validControls = false;
		String msg = "";
		
		Optional<LocalDate> optDateIni = Optional.ofNullable(dpffacti.getValue());
		Optional<LocalDate> optDateFin = Optional.ofNullable(dpffactf.getValue());
		
		//Both dates should be filled or empty
		if ((optDateIni.isPresent() && optDateFin.isPresent()) || (optDateIni.isEmpty() && optDateFin.isEmpty())) {
			validControls = true;
		} else {
			msg += "Debe especificar dos fechas válidas.";
		}

		lbmsg1.setText(msg);
		return validControls;
	}
	
	private void fillFilterObjetct(FacturaFilter ffilt) {
		
		Optional<FacturaFilter> optFfil = Optional.ofNullable(ffilt);
			optFfil.ifPresent((x) -> {
				
				Optional<LocalDate> optDate = Optional.ofNullable(dpffacti.getValue());
					optDate.ifPresentOrElse((y) -> { 
						x.setFechaFacturaInicial(Date.from(y.atStartOfDay(mutils.getDefaultZoneId()).toInstant()));
					}, () -> {
						x.setFechaFacturaInicial(null);
					});

				optDate = Optional.ofNullable(dpffactf.getValue());
					optDate.ifPresentOrElse((y) -> {
						x.setFechaFacturaFinal(Date.from(y.atStartOfDay(mutils.getDefaultZoneId()).toInstant()));
					}, () -> {
						x.setFechaFacturaFinal(null);
					});

				x.setTitular(txtitular.getText());
				x.setCifnif(txcifnif.getText());
				x.setDireccion(txdirec.getText());
				x.setSerieFacturas(cbsfact.getValue());
				x.setAgencia(cbagencia.getValue());
				x.setReferencia(txrefer.getText());
				x.setFormaPago(cbfpago.getValue());
				x.setEstadoFactura(cbefact.getValue());
				x.setMarcador(txmarc.getText());
				
			});
	}
	
	private void closeForm() {
		
		// get a handle to the stage
		Stage stage = (Stage) bclose.getScene().getWindow();
		// do what you have to do
		stage.close();
		
	}
}
