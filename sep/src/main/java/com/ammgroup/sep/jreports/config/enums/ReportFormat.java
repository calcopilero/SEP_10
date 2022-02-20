package com.ammgroup.sep.jreports.config.enums;

public enum ReportFormat {
	
		PREVIEW("preview"),
		PDF("pdf"),
		EXCEL("excel"),
		XML("xml"),
		HTML("html");

    public final String format;

    private ReportFormat(String format) {
        this.format = format;
    }
}
