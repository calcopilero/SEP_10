package com.ammgroup.sep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.ammgroup.sep.controller.SEPMainController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SEPJavaFXApplication extends Application {

	@Autowired
	private ApplicationContext springContext;
	
    //@Override
    public void init() throws Exception {
	
		//obtaining the spring application context 
		springContext = new SpringApplicationBuilder(SEPSpringBootApplication.class).run();
    }
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/jfxforms/sepMain.fxml"));
        //Set Spring to be the controller factory
		loader.setControllerFactory(springContext::getBean);
        Parent root = loader.load();
	    //Parent root = loader.load();
	    //TTTrackerMainController controller = loader.getController();
	    
        primaryStage.setTitle("Gestión SEP 1.1");
	    primaryStage.setMaximized(true);
	    primaryStage.setOnCloseRequest(evt -> {
	        // allow user to decide between yes and no
	        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea cerrar la aplicación?", ButtonType.YES, ButtonType.NO);

	        // clicking X also means no
	        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
	        
	        if (ButtonType.NO.equals(result)) {
	            // consume event i.e. ignore close request 
	            evt.consume();
	        }
	    });
	    
	    primaryStage.setScene(new Scene(root));

	    //Once the scene is set and the controller initialized we can use it to set title and icons
	    SEPMainController sepmc = loader.getController();
	    sepmc.setTitleAndIcon();
	    
	    primaryStage.show();
	    
	}
	
    @Override
    public void stop() throws Exception {

    	//To stop the spring application context (wich is the same of stopping spring)
		((ConfigurableApplicationContext) springContext).close();
		Platform.exit();
		
    }
}
