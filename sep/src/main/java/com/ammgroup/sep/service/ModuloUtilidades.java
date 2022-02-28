package com.ammgroup.sep.service;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ammgroup.sep.config.SEPPropertiesFile;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

@SuppressWarnings(value = { "unused" })
@Component
public class ModuloUtilidades {
	
	public final String DATE_FORMAT = "dd-MM-yyyy";
	public final String DATE_FORMATL = "yyyy-MM-dd";
	
	public final String CURRENCY_FORMAT = "#,##0.00";
	public final String CURRENCY_ZERO = "0.00";
	public final String CURRENCY_PATTERN = "-?\\d*(\\.\\d{0,2})?";
	public final String INTEGER_PATTERN = "-?([1-9][0-9]*)?";
	
	public final String RESOURCE_REPORTS_DIR = "/jreports/";
	public final String RESOURCE_FXFORMS_DIR = "/jfxforms/";
	public final String RESOURCE_IMAGES_DIR = "/images/";
	
	@Autowired
	ApplicationContext springContext;
	
	@Autowired
	SEPPropertiesFile sepprop;

	public ModuloUtilidades() {
		super();
	}
	
	public Date getDateFromString(String strdate, String strfmt) {
		
		SimpleDateFormat dformat = new SimpleDateFormat(strfmt);
		
		Date pdate = new Date();
		try {
			pdate = dformat.parse(strdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return pdate;
		
	}
	
	public <T> void configureColumnForDate(TableColumn<T, Date> tcol) {
		
		tcol.setCellFactory(tc -> new TableCell<T, Date>() {
			
	        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

	        @Override
	        protected void updateItem(Date item, boolean empty) {
	            super.updateItem(item, empty);
	            if(empty) {
	                setText(null);
	            }
	            else {
	                setText(format.format(item));
	            }
	        }
		});
	}
	
	public <T> void configureColumnForDecimal(TableColumn<T, Double> tcol) {
		
		tcol.setCellFactory(tc -> new TableCell<T, Double>() {
		    @Override
		    protected void updateItem(Double value, boolean empty) {
		        super.updateItem(value, empty) ;
		        if (empty) {
		            setText(null);
		        } else {
		        	Optional<Double> douOpt = Optional.ofNullable(value);
		        		douOpt.ifPresentOrElse((x) -> { 
		        			setText(getStringFromDouble(x, CURRENCY_FORMAT));
		        		}, () -> { 
		        			//Double db = 0.0D;
		        			//setText(String.format("%0.2f", db.doubleValue())); 
		        			setText(CURRENCY_ZERO); 
		        		});
		        }
		    }
		});
		
		tcol.setStyle("-fx-alignment: CENTER-RIGHT;");
	}
	
	public <T> int searchIdInCombo(ComboBox<T> combo, T item) {
		
		var srwrapper = new Object(){ int index = -1; boolean ifound = false; };
		
		ObservableList<T> olist = combo.getItems();
		
		for (int i = 0; i < olist.size(); i++) {
			
			T citem = olist.get(i);
		    
			//Check if ids are the same
			Optional<T> optCitem = Optional.ofNullable(citem);
			optCitem.ifPresent((x) -> {
			
				if (citem.equals(item)) {
			    
					srwrapper.ifound = true;
			    			        
			    }
			});
			
	    	if (srwrapper.ifound) {

	    		//Save index found
				srwrapper.index = i;

	    		break;
	    	}

		}
	
		return (srwrapper.ifound? srwrapper.index: -1);
	}
	
	public ZoneId getDefaultZoneId() {
		
		//default time zone
		return ZoneId.systemDefault();
		
	}
	
	public DateTimeFormatter getDefaultDateTimeFormatter() {
		
		return DateTimeFormatter.ofPattern("dd-MM-yyyy");	
		
	}
	
	public double calculatePercentage(double obtained, double total) {
		
        return obtained * 100 / total;
        
    }
	
	public double applyPercentage(double percent, double total) {
		
        return total * percent / 100;
        
    }
	
	public int getYearFromDate(Date dt) {
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dt);
		
		return calendar.get(Calendar.YEAR);
	}
	
	public String getStringFromDate(Date dt, String strfmt) {
		
		DateFormat dateFormat = new SimpleDateFormat(strfmt);  

		return dateFormat.format(dt);  
	}
	
	public String getStringFromDouble(Double db, String decfmt) {
		
    	Locale locale = new Locale("en", "UK");
    	
    	DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
    	symbols.setDecimalSeparator('.');
    	symbols.setGroupingSeparator(',');
    	
    	DecimalFormat format = new DecimalFormat(decfmt, symbols);
    	
		return format.format(db);
	}
	
	public TextFormatter<Double> getTextFormatterForDecimal() {
		
	    //Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
		//Pattern validEditingState = Pattern.compile("-?\\d+\\.\\d+");
	    Pattern validEditingState = Pattern.compile(CURRENCY_PATTERN);

	    UnaryOperator<TextFormatter.Change> filter = c -> {
	        String text = c.getControlNewText();
	        if (validEditingState.matcher(text).matches()) {
	        	//System.out.println("4" + " - " + c);
	            return c ;
	        } else {
	        	//System.out.println("5");
	            return null ;
	        }
	    };

	    StringConverter<Double> converter = new StringConverter<Double>() {

	        @Override
	        public Double fromString(String s) {
	            if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
	            	//System.out.println("1" + " - " + s);
	                return 0.00D ;
	            } else {
	            	//System.out.println("3" + " - " + Double.valueOf(s));
	                return Double.valueOf(s);
	            }
	        }

	        @Override
	        public String toString(Double db) {
	        	
	            //System.out.println("2" + " - " + format.format(d));
	            
	            return getStringFromDouble(db, CURRENCY_FORMAT);
	        }
	    };

	    TextFormatter<Double> textFormatter = new TextFormatter<>(converter, 0.00D, filter);
	    
	    return textFormatter;
	}
	
	public LocalDate obtainLocalDate(Date dt) {
		
		java.util.Date judate = new Date(dt.getTime());

		return judate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	}
	
	public Date getDateFromLocalDate(LocalDate ld) {
		
		Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		return date;
	}
	
	public void loadForm(String pfname, String ptitle) throws Exception {

		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(RESOURCE_FXFORMS_DIR + pfname));
		// Setting spring as controller factory in static class FXMLLoader
		// is necesary to access other dependences as repositories from FXML controllers
		loader.setControllerFactory(springContext::getBean);
		
		Parent root = loader.load();
		stage.setScene(new Scene(root));
		stage.setTitle(ptitle);
        //stage.getIcons().add(new Image("/icons/" + formIcon));
        stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
        
		//Set the data of the controller
		//PlayersdetController ctrl = loader.getController();
		//ctrl.loadData(pcrud);
		//ctrl.getTfName().setText(pcrud.getPlayer().getFullName());
		//ctrl.getTacro().setText(pcrud.getPlayer().getAcronim());

	}
	
	public Sort getDefaultSociosSort() {
		
		Sort.Direction sortdir = null;
		
		String dir = sepprop.getSociosDefaultSortDirection();
		if (dir.equalsIgnoreCase("ASC")) sortdir = Sort.Direction.ASC;
		if (dir.equalsIgnoreCase("DESC")) sortdir = Sort.Direction.DESC;
		
		String orderfield = null;
		orderfield = sepprop.getSociosDefaultSortField();
		
		Optional<Sort.Direction> sdOpt = Optional.ofNullable(sortdir);
		Optional<String> strOpt = Optional.ofNullable(orderfield);
		
		if (sdOpt.isPresent() && strOpt.isPresent()) {
			return Sort.by(sortdir, orderfield);
		} else {
			return null;
		}
	}
	
	public Sort getDefaultFacturasSort() {
		
		Sort.Direction sortdir = null;
		
		String dir = sepprop.getFacturasDefaultSortDirection();
		if (dir.equalsIgnoreCase("ASC")) sortdir = Sort.Direction.ASC;
		if (dir.equalsIgnoreCase("DESC")) sortdir = Sort.Direction.DESC;
		
		String orderfield = null;
		orderfield = sepprop.getFacturasDefaultSortField();
		
		Optional<Sort.Direction> sdOpt = Optional.ofNullable(sortdir);
		Optional<String> strOpt = Optional.ofNullable(orderfield);
		
		if (sdOpt.isPresent() && strOpt.isPresent()) {
			return Sort.by(sortdir, orderfield);
		} else {
			return null;
		}
	}
	
	public Sort getDefaultAgenciasSort() {
		
		Sort.Direction sortdir = null;
		
		String dir = sepprop.getAgenciasDefaultSortDirection();
		if (dir.equalsIgnoreCase("ASC")) sortdir = Sort.Direction.ASC;
		if (dir.equalsIgnoreCase("DESC")) sortdir = Sort.Direction.DESC;
		
		String orderfield = null;
		orderfield = sepprop.getAgenciasDefaultSortField();
		
		Optional<Sort.Direction> sdOpt = Optional.ofNullable(sortdir);
		Optional<String> strOpt = Optional.ofNullable(orderfield);
		
		if (sdOpt.isPresent() && strOpt.isPresent()) {
			return Sort.by(sortdir, orderfield);
		} else {
			return null;
		}
	}
	
	public Sort getDefaultReclamacionesSort() {
		
		Sort.Direction sortdir = null;
		
		String dir = sepprop.getReclamacionesDefaultSortDirection();
		if (dir.equalsIgnoreCase("ASC")) sortdir = Sort.Direction.ASC;
		if (dir.equalsIgnoreCase("DESC")) sortdir = Sort.Direction.DESC;
		
		String orderfield = null;
		orderfield = sepprop.getReclamacionesDefaultSortField();
		
		Optional<Sort.Direction> sdOpt = Optional.ofNullable(sortdir);
		Optional<String> strOpt = Optional.ofNullable(orderfield);
		
		if (sdOpt.isPresent() && strOpt.isPresent()) {
			return Sort.by(sortdir, orderfield);
		} else {
			return null;
		}
	}
	
	public static boolean isNumeric(String strNum) {
		
	    if (strNum == null) {
	        return false;
	    }
	    
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    
	    return true;
	}
}
