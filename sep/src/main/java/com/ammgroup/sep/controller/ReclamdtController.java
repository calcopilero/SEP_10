package com.ammgroup.sep.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.CrudDAORecl;
import com.ammgroup.sep.controller.config.crud.CrudDAOSoc;
import com.ammgroup.sep.controller.config.crud.enums.CrudAction;
import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.EstadoReclamacion;
import com.ammgroup.sep.model.Reclamacion;
import com.ammgroup.sep.model.Socio;
import com.ammgroup.sep.repository.AgenciaRepository;
import com.ammgroup.sep.repository.EstadoReclamacionRepository;
import com.ammgroup.sep.repository.ReclamacionRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

@Component
public class ReclamdtController implements Initializable {

	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	AgenciaRepository agenciaRepository;
	
	@Autowired
	EstadoReclamacionRepository estrecRepository;
	
	@Autowired 
	private ReclamacionRepository reclRepository;
	
	@Autowired
	CrudDAORecl<Reclamacion> reclamCrud;
	
	@Autowired
	private CrudDAOSoc<Socio> socrud;
	
    @FXML
    private Label lbnreclam;

    @FXML
    private Label lbsocio;

    @FXML
    private DatePicker dpfrecl;
    
    @FXML
    private ComboBox<Agencia> cbagen;

    @FXML
    private Label lbreccomlen;
	
    @FXML
    private TextArea tareccom;

    @FXML
    private DatePicker dpfresp;

    @FXML
    private Label lbanotlen;
    
    @FXML
    private TextArea taanot;
    
    @FXML
    private ComboBox<EstadoReclamacion> cbestrec;

    @FXML
    private Label lbmsg1;

    @FXML
    private Label lbmsg2;

    @FXML
    private Button bexec;
    
    @FXML
    private Button bclose;

    @FXML
    void bexecOnAction(ActionEvent event) {

		boolean cform = true;
		Reclamacion recl;
		//long cont1;
		//long cont2;
		
	    switch (reclamCrud.getAction()) {
        case ADD :
        	
        	if (checkControls()) {
        	
	        	recl = new Reclamacion();
	        	
	        	saveDataToReclamacion(recl);
	        	
	        	reclRepository.save(recl);
	        
        	} else {
        		
        		cform = false;
        	}
        	
            break;

        case EDIT:
        	
        	if (checkControls()) {

	        	recl = reclamCrud.getDao();
	        	
	        	saveDataToReclamacion(recl);
	        	
	        	reclRepository.save(recl);
        	
        	} else {
        		
        		cform = false;
        	}
        	
        	break;
            
        case DELETE:
        	
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Confirma el BORRADO de la reclamación?", ButtonType.YES, ButtonType.NO);

	        // clicking X also means no
	        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
	        
	        if (ButtonType.YES.equals(result)) {
        	
	        	recl = reclamCrud.getDao();
	        	
	        	reclRepository.delete(recl);
	        	
	        } else {
	        	
	        	cform = false;
	        }

        	break;
    		
        case VIEW:
		
        	break;
		
        default:
		
        	break;
	    }
	    
    	if (cform) closeForm();
    }

    @FXML
    void bcloseOnAction(ActionEvent event) {

    	closeForm();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//Populate the combos
		cbagen.setItems(FXCollections.observableList(agenciaRepository.findAll()));
		cbagen.getItems().add(null);
		cbestrec.setItems(FXCollections.observableList(estrecRepository.findAll(Sort.by(Sort.Direction.ASC, "descripcion"))));
		cbestrec.getItems().add(null);
		
		//Configure the TextField and TextArea controls
		mutils.configureTextAreaWithLabel(tareccom, lbreccomlen,250);
		mutils.configureTextAreaWithLabel(taanot, lbanotlen,250);
		
	    switch (reclamCrud.getAction()) {
        case ADD :
        	
			//The numero of reclamacion is not shown when adding a new reclamacion
        	lbnreclam.setText("[SIN DEFINIR]");

			//When adding a reclamacion the socio is obtained from the socios form
			Optional<Socio> socOpt = Optional.ofNullable(socrud.getDao());
				socOpt.ifPresent((y) -> lbsocio.setText(y.toString()));

        	dpfrecl.setValue(mutils.obtainLocalDate(new Date()));
    		tareccom.setText("");
    		taanot.setText("");
        	
            break;
            
        case EDIT:
        	
    		fillControls();
        	
    		break;
    		
        case DELETE:
        	
    		fillControls();

    		disableFormControls();
        	
    		break;
    		
		case VIEW:
			
    		fillControls();

			disableFormControls();
			
    		break;
    		
		default:
			
			break;
	    }

	}
    
	private void saveDataToReclamacion(Reclamacion reclam) {
		
		if (reclamCrud.getAction() == CrudAction.ADD) {
			
			reclam.setSocio(socrud.getDao());
		}
		
		Optional<LocalDate> optDate = Optional.ofNullable(dpfrecl.getValue());
			optDate.ifPresent((y) -> {
				
				Date frecl = Date.from(y.atStartOfDay(mutils.getDefaultZoneId()).toInstant()); 
				
				reclam.setFechaReclamacion(frecl);
				
				if (reclamCrud.getAction() == CrudAction.ADD) {
				
					//The number of reclamaciones is generated automatically
					//Get the year of fecha factura
					int year = mutils.getYearFromDate(frecl);
					
					Optional<Integer> numSf = Optional.ofNullable(reclRepository.getMaximumReclamacionNumber(mutils.getDateFromString("01-01-" + year, mutils.DATE_FORMAT), mutils.getDateFromString("31-12-" + year, mutils.DATE_FORMAT)));
						numSf.ifPresentOrElse((x) -> {
						
							int proxNum = x.intValue() + 1;
							
							reclam.setNumero(proxNum);
	
						}, () -> {
							reclam.setNumero(1);
						});
				}

			});

		Optional<Agencia> ageOpt = Optional.ofNullable(cbagen.getValue());
			ageOpt.ifPresentOrElse((y) -> {
				reclam.setAgencia(y);
			}, () -> {
				reclam.setAgencia(null);
			});

		reclam.setReclamacionComentario(mutils.obtainText(tareccom));
		
		optDate = Optional.ofNullable(dpfresp.getValue());
			optDate.ifPresentOrElse((y) -> {
				
				Date frecl = Date.from(y.atStartOfDay(mutils.getDefaultZoneId()).toInstant()); 
				
				reclam.setFechaRespuesta(frecl);
			}, () -> {
				reclam.setFechaRespuesta(null);
			});
				
		reclam.setAnotaciones(mutils.obtainText(taanot));
		
		Optional<EstadoReclamacion> erecOpt = Optional.ofNullable(cbestrec.getValue());
			erecOpt.ifPresent((y) -> reclam.setEstadoReclamacion(y));

	}
	
	private void closeForm() {
		
		// get a handle to the stage
		Stage stage = (Stage) bclose.getScene().getWindow();
		// do what you have to do
		stage.close();
		
	}
	
	private void fillControls() {

		Optional<Reclamacion> recOpt = Optional.ofNullable(reclamCrud.getDao());
			recOpt.ifPresent((x) -> {
				
				lbnreclam.setText(String.valueOf(x.getNumero()));
			
				Optional<Socio> socOpt = Optional.ofNullable(x.getSocio());
					socOpt.ifPresent((y) -> lbsocio.setText(y.toString()));
			
				Optional<Date> optDate = Optional.ofNullable(x.getFechaReclamacion());
					optDate.ifPresent((y) -> dpfrecl.setValue(mutils.obtainLocalDate(y)));
			
		    	Optional<Agencia> ageOpt = Optional.ofNullable(x.getAgencia());
		    		ageOpt.ifPresent((y) -> cbagen.getSelectionModel().select(mutils.searchIdInCombo(cbagen, y)));

				Optional<String> optStr = Optional.ofNullable(x.getReclamacionComentario());
					optStr.ifPresentOrElse((y) -> {
						tareccom.setText(y);
					}, () -> {
						tareccom.setText("");
					});
		    	
				optDate = Optional.ofNullable(x.getFechaRespuesta());
					optDate.ifPresent((y) -> dpfresp.setValue(mutils.obtainLocalDate(y)));
		    	
		    	Optional<EstadoReclamacion> erecOpt = Optional.ofNullable(x.getEstadoReclamacion());
		    		erecOpt.ifPresent((y) -> cbestrec.getSelectionModel().select(mutils.searchIdInCombo(cbestrec, y)));
				
				optStr = Optional.ofNullable(x.getAnotaciones());
					optStr.ifPresentOrElse((y) -> {
						taanot.setText(y);
					}, () -> {
						taanot.setText("");
					});
				
			});
		
	}
	
	private void disableFormControls() {
		
		//Datepickers need alse to set editors styke
		disableControl(dpfrecl);
		dpfrecl.getEditor().setStyle("-fx-opacity: 1;");
		
		//In comboboxex is needed to set a CSS to disable cells
		disableControl(cbagen);
		disableControl(tareccom);
		
		disableControl(dpfresp);
		dpfresp.getEditor().setStyle("-fx-opacity: 1;");
		
		disableControl(cbestrec);
		disableControl(taanot);
	}
	
	private void disableControl(Control fxcontrol) {
		
		fxcontrol.setDisable(true);
		//when disabled controls have 0.4 opacity, but we use 1 to simulate as enabled
		fxcontrol.setStyle("-fx-opacity: 1");
		//fxcontrol.setStyle("-fx-opacity: 1;  -fx-background-color: white");
		//fxcontrol.setStyle("-fx-opacity: 1; -fx-text-fill: black;-fx-background-color: white");

		//For combos and tables and so the opacity, wich is responsible of the gray
		//color in text fields when disabled should be modified using a CSS file
		//We can add a CSS file to a Scene in code or using Scenebuilder
		
	}
	
	private boolean checkControls() {
		
		var checkwrapper = new Object(){ String errorstext = ""; boolean checks = true; };
		
    	Optional<LocalDate> dtOpt = Optional.ofNullable(dpfrecl.getValue());
			if (dtOpt.isEmpty()) {
				checkwrapper.errorstext += "Debe especificar una fecha de reclamación válida. ";
				checkwrapper.checks = false;  
			}
		
		if (mutils.obtainText(tareccom).length() == 0) {
			checkwrapper.errorstext += "El concepto de la reclamación no puede quedar en blanco. ";
			checkwrapper.checks = false;  
		}

		Optional<EstadoReclamacion> erOpt = Optional.ofNullable(cbestrec.getValue());
			if (erOpt.isEmpty()) {
				checkwrapper.errorstext += "Debe especificar un estado de la reclamación. ";
				checkwrapper.checks = false;
			}
			
		lbmsg1.setText(checkwrapper.errorstext);
		
		return checkwrapper.checks;
	}

}
