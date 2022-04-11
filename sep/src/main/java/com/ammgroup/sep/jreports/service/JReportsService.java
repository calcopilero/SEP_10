package com.ammgroup.sep.jreports.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.ammgroup.sep.config.SEPPropertiesFile;
import com.ammgroup.sep.jreports.config.enums.ReportFormat;
import com.ammgroup.sep.model.Descuento;
import com.ammgroup.sep.model.Factura;
import com.ammgroup.sep.model.ItemFactura;
import com.ammgroup.sep.model.TipoIVA;
import com.ammgroup.sep.repository.FacturaRepository;
import com.ammgroup.sep.service.ModuloUtilidades;

import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

@Service
public class JReportsService {
	
	@Autowired
	private ModuloUtilidades mutils;
	
	@Autowired
	FacturaRepository factRepository;
	
	@Autowired
	SEPPropertiesFile sepprop;

	public JReportsService() {
		super();
	}
	
	public void generateFacturaReport(String jrxfile, Factura fact, ReportFormat rform, String gfile, String vtitle) throws Exception {
		
		//Set the parameters to the report
		Map<String, Object> map = new HashMap<String, Object>();
		
		Optional<String> optStr = Optional.ofNullable(fact.getNumeroCompuesto());
			optStr.ifPresentOrElse((x) -> map.put("pmNumFactura", x), () -> map.put("pmNumFactura", ""));
		optStr = Optional.ofNullable(fact.getTitular());
			optStr.ifPresentOrElse((x) -> map.put("pmTitular", x), () -> map.put("pmTitular", ""));
		optStr = Optional.ofNullable(fact.getDireccion());
			optStr.ifPresentOrElse((x) -> map.put("pmDireccion", x), () -> map.put("pmDireccion", ""));
		optStr = Optional.ofNullable(fact.getCifnif());
			optStr.ifPresentOrElse((x) -> map.put("pmCifnif", x), () -> map.put("pmCifnif", ""));
		optStr = Optional.ofNullable(fact.getTextoFormaPago());
			optStr.ifPresentOrElse((x) -> map.put("pmFormaPago", x), () -> map.put("pmFormaPago", ""));
		optStr = Optional.ofNullable(fact.getReferencia());
			optStr.ifPresentOrElse((x) -> map.put("pmReferencia", x), () -> map.put("pmReferencia", ""));

		map.put("pmFechaFactura", mutils.getStringFromDate(fact.getFechaFactura(), mutils.DATE_FORMAT));

		Optional<Descuento> optDesc = Optional.ofNullable(fact.getDescuento());
			optDesc.ifPresent((x) -> {
				map.put("pmImporteDescuento", (fact.getImporteDescuento()) * (-1));
				map.put("pmTextoDescuento", x.getTextoFactura());
			});

		map.put("pmGastosEnvio", fact.getImpGastosEnvio());
		
		map.put("pmBaseImponible", fact.getImporteBaseImponible());
		
		Optional<TipoIVA> optTiva = Optional.ofNullable(fact.getTipoIVA());
			optTiva.ifPresent((x) -> {
				map.put("pmImporteIVA", fact.getImporteTipoIVA());
				map.put("pmTextoIVA", fact.getTipoIVA().getTextoFactura());
			});
		
		map.put("pmImporteTotal", fact.getImporteTotal());
		
		//Check if is a Factura Rectificada or Rectificativa
		if (fact.isExisteRectificativa()) {
			
			List<Factura> frectl = factRepository.findFacturaRectificativa(fact);
			
			Optional<List<Factura>> frectlOpt = Optional.ofNullable(frectl);
				frectlOpt.ifPresent((z) -> { 
					if (frectl.size() > 0) {
						map.put("pmTextoRectificativa", "Rectificada en factura " + z.get(0).getNumeroCompuesto());
					} else {
						map.put("pmTextoRectificativa", "Factura RECTIFICADA");
					}
				});
		} else {
			
			Optional<Factura> optFact = Optional.ofNullable(fact.getFacturaRectificada());
				optFact.ifPresent((y) -> map.put("pmTextoRectificativa", "Rectifica la factura " + y.getNumeroCompuesto()));
		}

		File logofile = ResourceUtils.getFile(System.getenv("SEP_DIR") + mutils.RESOURCE_IMAGES_DIR + "SEPHD.jpg");
		BufferedImage logo = ImageIO.read(logofile);
		map.put("pmLogo", logo );
		
		optStr = Optional.ofNullable(sepprop.getAddress());
			optStr.ifPresentOrElse((x) -> map.put("pmSepDireccion", x), () -> map.put("pmSepDireccion", ""));
		optStr = Optional.ofNullable(sepprop.getEmail());
			optStr.ifPresentOrElse((x) -> map.put("pmSepEmail", x), () -> map.put("pmSepEmail", ""));
		optStr = Optional.ofNullable(sepprop.getEmail());
			optStr.ifPresentOrElse((x) -> map.put("pmSepWeb", x), () -> map.put("pmSepWeb", ""));
		optStr = Optional.ofNullable(sepprop.getPhone());
			optStr.ifPresentOrElse((x) -> map.put("pmSepTels", x), () -> map.put("pmSepTels", ""));
			
		if (fact.isFacturaFirmada()) {
			File firmafile = ResourceUtils.getFile(System.getenv("SEP_DIR") + mutils.RESOURCE_IMAGES_DIR + "firma.png");
			BufferedImage firma = ImageIO.read(firmafile);
			//Another way to create a BufferedImage from ImageIO
			//BufferedImage firma = ImageIO.read(getClass().getResource(mutils.RESOURCE_IMAGES_DIR + "firma.png"));
			map.put("pmFirma", firma );
		}
		
		File jrfile = ResourceUtils.getFile(System.getenv("SEP_DIR") + mutils.RESOURCE_REPORTS_DIR + jrxfile);
		JasperReport jreport = JasperCompileManager.compileReport(jrfile.getAbsolutePath());
		
        //InputStream in = this.getClass().getResourceAsStream(mutils.RESOURCE_REPORTS_DIR + jrxfile);
        //JasperDesign jd = JRXmlLoader.load(in);
        //JasperReport jreport = JasperCompileManager.compileReport(jd);
		
		List<ItemFactura> items = factRepository.findItemsOfFactura(fact);
		JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(items);
		
		JasperPrint jprint = JasperFillManager.fillReport(jreport, map, jrds);
		
		Optional<ReportFormat> rfOpt = Optional.ofNullable(rform);
			rfOpt.ifPresentOrElse((x) -> {
				
			    switch (rform) {
		        case PREVIEW:
		        	
			        final SwingNode swingNode = new SwingNode();
			        createSwingContent(swingNode, jprint);
			        //swingNode.setContent(new JLabel("Hello"));
			        //swingNode.setContent(new JButton("Click me!"));
	
			        StackPane pane = new StackPane();
			        pane.getChildren().add(swingNode);
			        
			        //JasperPrintManager.printPages(jprint, 0, 0, true);
					
				    Stage preview = new Stage();
				    //preview.initOwner(owner);
				    //preview.initModality(Modality.APPLICATION_MODAL);
				    preview.setScene(new Scene(pane, 850, 1250));
				    
				    Optional<String> titOpt = Optional.ofNullable(vtitle);
				    	titOpt.ifPresentOrElse((y) -> preview.setTitle(y), () -> preview.setTitle("Report preview"));
				    
				    preview.setTitle(vtitle);
				    preview.show();
				    
		            break;
		        	
		        case PDF :
		        	
		        	try {
						JasperExportManager.exportReportToPdfFile(jprint, sepprop.getPdfLocation() + gfile);
						
						Optional<String> optNum = Optional.ofNullable(fact.getNumeroCompuesto());
							optNum.ifPresentOrElse((y) -> {
								Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha generado el fichero PDF de la factura " + y, ButtonType.YES);
								alert.showAndWait();
							}, () -> {
								Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha generado el fichero PDF de la factura.", ButtonType.YES);
								alert.showAndWait();
							});

					} catch (JRException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		            break;
		            
		        case HTML:
		        	
		        	try {
		        		//gfile should contain the complete path and file name of the generated file
						JasperExportManager.exportReportToHtmlFile(jprint, gfile);
					} catch (JRException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		    		break;
		    		
		        case XML:
		        	
		        	try {
		        		//gfile should contain the complete path and file name of the generated file
						JasperExportManager.exportReportToXmlFile(jprint, gfile, false);
					} catch (JRException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		            break;
		            
		        case EXCEL:
		            break;
				default:
					break;
			    }
				
			}, () -> {
					
				//TODO Show message setting that a format should be set
			});
	}

	public <T> void generateListReport(String jrxfile, List<T> itemslist, ReportFormat rform, String gfile, String vtitle) throws Exception {
		
		File jrfile = ResourceUtils.getFile(System.getenv("SEP_DIR") + mutils.RESOURCE_REPORTS_DIR + jrxfile);
		JasperReport jreport = JasperCompileManager.compileReport(jrfile.getAbsolutePath());
		
        //InputStream in = getClass().getResourceAsStream(mutils.RESOURCE_REPORTS_DIR + jrxfile);
		//Another alternative to get an InputStream using absolute location
		//File jrfile = ResourceUtils.getFile(System.getenv("SEP_DIR") + mutils.RESOURCE_REPORTS_DIR + jrxfile);
		//InputStream in = new FileInputStream(jrfile);
        //JasperDesign jd = JRXmlLoader.load(in);
        //JasperReport jreport = JasperCompileManager.compileReport(jd);

		JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(itemslist);
		
		JasperPrint jprint = JasperFillManager.fillReport(jreport, null, jrds);
		
		Optional<ReportFormat> rfOpt = Optional.ofNullable(rform);
			rfOpt.ifPresentOrElse((x) -> {
				
			    switch (rform) {
		        case PREVIEW:
		        	
			        final SwingNode swingNode = new SwingNode();
			        createSwingContent(swingNode, jprint);
			        //swingNode.setContent(new JLabel("Hello"));
			        //swingNode.setContent(new JButton("Click me!"));
	
			        StackPane pane = new StackPane();
			        pane.getChildren().add(swingNode);
			        
			        //JasperPrintManager.printPages(jprint, 0, 0, true);
					
				    Stage preview = new Stage();
				    //preview.initOwner(owner);
				    //preview.initModality(Modality.APPLICATION_MODAL);
				    preview.setScene(new Scene(pane, 850, 650));
				    
				    Optional<String> titOpt = Optional.ofNullable(vtitle);
				    	titOpt.ifPresentOrElse((y) -> preview.setTitle(y), () -> preview.setTitle("Report preview"));
				    
				    preview.setTitle(vtitle);
				    preview.show();
				    
		            break;
		        	
		        case PDF :
		        	
		        	try {
		        		//gfile should contain the complete path and file name of the generated file
						JasperExportManager.exportReportToPdfFile(jprint, gfile);
					} catch (JRException e) {
						e.printStackTrace();
					}
		        	
		            break;
		            
		        case HTML:
		        	
		        	try {
		        		//gfile should contain the complete path and file name of the generated file
						JasperExportManager.exportReportToHtmlFile(jprint, gfile);
					} catch (JRException e) {
						e.printStackTrace();
					}
		        	
		    		break;
		    		
		        case XML:
		        	
		        	try {
		        		//gfile should contain the complete path and file name of the generated file
						JasperExportManager.exportReportToXmlFile(jprint, gfile, false);
					} catch (JRException e) {
						e.printStackTrace();
					}
		        	
		            break;
		            
		        case EXCEL:
		            break;
				default:
					break;
			    }
				
			}, () -> {
					
				//TODO Show message setting that a format should be set
			});
	}
	
    private void createSwingContent(final SwingNode swingNode, JasperPrint jprint) {
        SwingUtilities.invokeLater(new Runnable() {
        	
            @Override
            public void run() {
            	JRViewer viewer = new JRViewer(jprint);
                swingNode.setContent(viewer);
            }
        });
    }
}
