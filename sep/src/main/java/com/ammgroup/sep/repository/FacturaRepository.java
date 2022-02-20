package com.ammgroup.sep.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.Factura;
import com.ammgroup.sep.model.ItemFactura;
import com.ammgroup.sep.model.SerieFacturas;
import com.ammgroup.sep.model.Socio;

public interface FacturaRepository extends JpaRepository<Factura, Long>, JpaSpecificationExecutor<Factura> {
	
	//To get the max number of a serie de facturas in a certain time interval
	@Query(value = "SELECT MAX(i.numero) FROM Factura i WHERE i.serie = ?1 AND (i.fechaFactura BETWEEN ?2 AND ?3)")
	Integer getMaximumFacturaNumberBySerie(SerieFacturas sfact, Date fdateStart, Date fdateEnd);

	//To get the items in a Factura
	@Query(value = "SELECT DISTINCT(i) FROM ItemFactura i WHERE i.factura = ?1")
	List<ItemFactura> findItemsOfFactura(Factura fact);
	
	//To count by nombre (exact match)
	@Query(value = "SELECT COUNT(i) FROM Factura i WHERE i.titular LIKE %?1%")
	long countExistingFacturasByTitular(String titular);
	
	//To count by cifnif (exact match)
	@Query(value = "SELECT COUNT(i) FROM Factura i WHERE i.cifnif LIKE ?1")
	long countExistingFacturasByCifnif(String cifnif);
	
	//To get the facturas associated with a Socio
	@Query(value = "SELECT DISTINCT(i) FROM Factura i WHERE i.socio = ?1 ORDER BY i.fechaFactura DESC")
	List<Factura> findFacturasOfSocio(Socio soc);
	
	//To get the facturas associated with a Socio
	@Query(value = "SELECT DISTINCT(i) FROM Factura i WHERE i.socio = ?1 ORDER BY i.fechaFactura DESC")
	List<Factura> findFacturasOfSocio(Socio soc, Sort sort);
	
	//To get the facturas associated with a Agencia
	@Query(value = "SELECT DISTINCT(i) FROM Factura i WHERE i.agencia = ?1 ORDER BY i.fechaFactura DESC")
	List<Factura> findFacturasOfAgencia(Agencia age);
	
	//To get the facturas associated with a Agencia
	@Query(value = "SELECT DISTINCT(i) FROM Factura i WHERE i.agencia = ?1 ORDER BY i.fechaFactura DESC")
	List<Factura> findFacturasOfAgencia(Agencia age, Sort sort);
	
	//To get the facturas associated with a factura rectificativa
	@Query(value = "SELECT DISTINCT(i) FROM Factura i WHERE i.facturaRectificada = ?1")
	List<Factura> findFacturaRectificativa(Factura fac);
	
	//To get the facturas between two dates
	@Query(value = "SELECT DISTINCT(i) FROM Factura i WHERE i.fechaFactura BETWEEN ?1 AND ?2 ORDER BY i.fechaFactura DESC")
	List<Factura> findFacturasBetweenDates(Date stardDate, Date endDate);
}
