package com.ammgroup.sep.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.config.SEPPropertiesFile;
import com.ammgroup.sep.service.ModuloUtilidades;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Component
public class SEPMainController implements Initializable {
	
	@Autowired
	private ApplicationContext springContext;
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	SEPPropertiesFile sepprop;
	
	@FXML
	private VBox vb;
	
	@FXML
	private AnchorPane mainapane;
	
	@FXML
	private MenuBar mbSEP;
	
	@FXML
	private Menu mFile;

	@FXML
	private MenuItem mitClose;
	
	@FXML
	private Menu mData;

	@FXML
	private MenuItem mitSocios;
	
	@FXML
	private Menu mAuxData;
	
	@FXML
	private MenuItem mitModSocios;
	
	@FXML
	private ImageView imlogo;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			
			InputStream stream = new FileInputStream(System.getenv("SEP_DIR") + mutils.RESOURCE_IMAGES_DIR + sepprop.getSepImageFilename());
			//InputStream stream = new FileInputStream(sepprop.getImagesLocation() + "SEPHD.jpg");
			//Another way to create an InputStream from resources location
			//InputStream stream = this.getClass().getClassLoader().getResourceAsStream("SEPHD.jpg");
			Image logo = new Image(stream);
			
			imlogo.setImage(logo);
			imlogo.setPreserveRatio(true);
			imlogo.setFitHeight(Integer.parseInt(sepprop.getImageHeight()));
			imlogo.setFitWidth(Integer.parseInt(sepprop.getImageWidth()));
			imlogo.setLayoutX(Integer.parseInt(sepprop.getImageX()));
			imlogo.setLayoutY(Integer.parseInt(sepprop.getImageY()));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		vb.setStyle("-fx-background-color: " + sepprop.getMainformBackcolor());
	}

    @FXML
    void mitModSociosOnAction(ActionEvent event) {
    	
		try {
			mutils.loadForm("msociosl.fxml", "Modalidades de socios");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    @FXML
    void mitDescuentosOnAction(ActionEvent event) {
    	
		try {
			mutils.loadForm("descul.fxml", "Descuentos");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    @FXML
    void mitTiposIVAOnAction(ActionEvent event) {
    	
		try {
			mutils.loadForm("tival.fxml", "Tipos de IVA");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }

    @FXML
    void mitFormasPagoOnAction(ActionEvent event) {
    	
		try {
			mutils.loadForm("fpagol.fxml", "Formas de pago");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }

    @FXML
    void mitModosAccesoOnAction(ActionEvent event) {
    	
		try {
			mutils.loadForm("maccesl.fxml", "Modos de acceso");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }

    @FXML
    void mitMotivosBajasOnAction(ActionEvent event) {
    	
		try {
			mutils.loadForm("mbajal.fxml", "Motivos de las bajas");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    @FXML
    void mitPaisesOnAction(ActionEvent event) {
    	
		try {
			mutils.loadForm("paisesl.fxml", "Paises");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }

    @FXML
    void mitProvinciasOnAction(ActionEvent event) {
    	
		try {
			mutils.loadForm("provsl.fxml", "Provincias");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    @FXML
    void mitZonasPostalesOnAction(ActionEvent event) {
    	
		try {
			mutils.loadForm("zpostl.fxml", "Zonas postales");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    @FXML
    void mitEstFactOnAction(ActionEvent event) {
    	
		try {
			mutils.loadForm("efactl.fxml", "Estados de facturas");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    @FXML
    void mitEstReclOnAction(ActionEvent event) {
    	
		try {
			mutils.loadForm("erecl.fxml", "Estados de reclamaciones");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    @FXML
    void mitSerFactOnAction(ActionEvent event) {
    	
		try {
			mutils.loadForm("sfactl.fxml", "Series de facturas");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }

    @FXML
    void mitSociosOnAction(ActionEvent event) {

		try {
			mutils.loadForm("sociosl.fxml", "Socios");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    @FXML
    void mitAgenciasOnAction(ActionEvent event) {

		try {
			mutils.loadForm("agencl.fxml", "Agencias");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    @FXML
    void mitFacturasOnAction(ActionEvent event) {

		try {
			mutils.loadForm("factl.fxml", "Facturas");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    @FXML
    void mitReclamacionesOnAction(ActionEvent event) {

		try {
			mutils.loadForm("reclaml.fxml", "Reclamaciones");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    @FXML
    void mitCloseOnAction(ActionEvent event) {
    	
        // allow user to decide between yes and no
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea cerrar la aplicación?", ButtonType.YES, ButtonType.NO);

        // clicking X also means no
        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
        
        if (ButtonType.YES.equals(result)) {
        	//To stop the spring application context (wich is the same of stopping spring)
    		((ConfigurableApplicationContext) springContext).close();
    		Platform.exit();
        }
    }
    
    public void setTitleAndIcon( ) {
    	
    	//Get the current stage from a node in the scene
		Stage mstage = (Stage) mainapane.getScene().getWindow();
		
		//Set the image in the window
        //Image sepImg = new Image(mutils.RESOURCE_IMAGES_DIR + sepprop.getSepImageFilename());
        Image sepImg = new Image(sepprop.getIconFile());
        mstage.getIcons().add(sepImg);
        
        //Set the title
		mstage.setTitle(sepprop.getApptitle());
    	
    }
}
