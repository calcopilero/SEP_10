package com.ammgroup.sep.jreports.service;

import javafx.scene.control.Control;
import net.sf.jasperreports.engine.JasperPrint;

@SuppressWarnings(value = { "unused" })
public class JRViewerFX extends Control {
	
	private JasperPrint jprint;

	public JRViewerFX(JasperPrint jprint) {
		super();
		
		this.jprint = jprint;
	}

}
