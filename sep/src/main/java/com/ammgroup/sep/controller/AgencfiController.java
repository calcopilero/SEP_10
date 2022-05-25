package com.ammgroup.sep.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.filter.AgenciaFilter;
import com.ammgroup.sep.model.Pais;
import com.ammgroup.sep.model.Provincia;
import com.ammgroup.sep.model.ZonaPostal;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

@Component
public class AgencfiController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	AgenciaFilter agfilter;
	
	@Autowired
	PaisRepository paisRepository;
	
	@Autowired
	ProvinciaRepository provRepository;
	
	@Autowired
	ZonaPostalRepository zpostRepository;
	
	@FXML
	private TextField txnombre;
	
    @FXML
    private CheckBox chactivas;
	
	@FXML
	private TextField txcifnif;

	@FXML
	private TextField txlocal;

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
	private TextField txpcont;
	
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

		//Populate the combo of provincias
		cbprov.setItems(FXCollections.observableList(provRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbprov.getItems().add(null);
		//Populate the combo of paises
		cbpais.setItems(FXCollections.observableList(paisRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbpais.getItems().add(null);
		//Populate the combo of zonas postales
		cbzpost.setItems(FXCollections.observableList(zpostRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbzpost.getItems().add(null);
		
	    //Setting the maximum number of characters of TextField
		txcifnif.addEventFilter(KeyEvent.KEY_TYPED, maxLength(12));
		txnombre.addEventFilter(KeyEvent.KEY_TYPED, maxLength(70));
		txlocal.addEventFilter(KeyEvent.KEY_TYPED, maxLength(70));
		txtelefono.addEventFilter(KeyEvent.KEY_TYPED, maxLength(12));
		txemail.addEventFilter(KeyEvent.KEY_TYPED, maxLength(80));
		txpcont.addEventFilter(KeyEvent.KEY_TYPED, maxLength(70));

		//Shove filters from AgencFilter object into controls
		Optional<AgenciaFilter> optAgfilter = Optional.ofNullable(agfilter);
		optAgfilter.ifPresent((x) -> {
			
			if (agfilter.containsFilters() > 0) {
				txnombre.setText(agfilter.getNombre());
				Optional<Boolean> boolOpt = Optional.ofNullable(x.getActiva());
					boolOpt.ifPresent((y) -> chactivas.setSelected(y));
				txcifnif.setText(agfilter.getCifnif());
				txlocal.setText(agfilter.getLocalidad());
				Optional<Provincia> optProv = Optional.ofNullable(agfilter.getProvincia());
					optProv.ifPresent((y) -> { cbprov.getSelectionModel().select(mutils.searchIdInCombo(cbprov,agfilter.getProvincia())); });
				Optional<Pais> optPais = Optional.ofNullable(agfilter.getPais());
					optPais.ifPresent((y) -> { cbpais.getSelectionModel().select(mutils.searchIdInCombo(cbpais, agfilter.getPais())); });
				Optional<ZonaPostal> optZpost = Optional.ofNullable(agfilter.getZonaPostal());
					optZpost.ifPresent((y) -> { cbzpost.getSelectionModel().select(mutils.searchIdInCombo(cbzpost, agfilter.getZonaPostal())); });
				txemail.setText(agfilter.getEmail());
				txtelefono.setText(agfilter.getTelefono());
				txpcont.setText(agfilter.getPersonaContacto());
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
		
		//Shove filters into AgencFilter object
		fillFilterObjetct(agfilter);
		
    	closeForm();
		
	}
	
	@FXML
    void bcleanOnAction(ActionEvent event) {
		
		txnombre.setText("");
		chactivas.setSelected(false);
		txcifnif.setText("");
		txlocal.setText("");
		cbprov.getSelectionModel().clearSelection();
		cbpais.getSelectionModel().clearSelection();
		cbzpost.getSelectionModel().clearSelection();
		txemail.setText("");
		txtelefono.setText("");
		txpcont.setText("");

		//Shove filters into AgencFilter object
		fillFilterObjetct(agfilter);
		
	}
	
	private void fillFilterObjetct(AgenciaFilter agfilter) {
		
		Optional<AgenciaFilter> optAgfilter = Optional.ofNullable(agfilter);
		optAgfilter.ifPresent((x) -> {
			
			x.setNombre(txnombre.getText());
			x.setActiva(chactivas.isSelected());
			x.setCifnif(txcifnif.getText());
			x.setLocalidad(txlocal.getText());
			x.setProvincia(cbprov.getSelectionModel().getSelectedItem());
			x.setPais(cbpais.getSelectionModel().getSelectedItem());
			x.setZonaPostal(cbzpost.getSelectionModel().getSelectedItem());
			x.setEmail(txemail.getText());
			x.setTelefono(txtelefono.getText());
			x.setPersonaContacto(txpcont.getText());
			
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
