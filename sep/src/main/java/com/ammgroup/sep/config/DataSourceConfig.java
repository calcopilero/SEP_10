package com.ammgroup.sep.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
	
	@Autowired
	SEPPropertiesFile sepprop;
	
    @Bean
    public DataSource datasource() {

    	return DataSourceBuilder.create()
    	          .driverClassName("net.ucanaccess.jdbc.UcanaccessDriver")
    	          .url("jdbc:ucanaccess://" + sepprop.getDbFile())
    	          //.url("jdbc:ucanaccess://C:/Users/ammog/git/SEP_10/sep/src/main/resources/db/SEP.accdb")
    	          .build();	
    }
}
