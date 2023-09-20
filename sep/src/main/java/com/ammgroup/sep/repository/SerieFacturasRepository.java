package com.ammgroup.sep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ammgroup.sep.model.SerieFacturas;

public interface SerieFacturasRepository extends JpaRepository<SerieFacturas, Long> {

	//Using wildcards to search by descripcion (starting with)
	@Query(value = "SELECT DISTINCT(i) FROM SerieFacturas i WHERE i.descripcion LIKE ?1%")
	List<SerieFacturas> findByDescripcion(String desc);
	
	//To search for serie facturas used to generate facturas in an automatic way
	@Query(value = "SELECT DISTINCT(i) FROM SerieFacturas i WHERE i.facturacionAutomatica = true")
	List<SerieFacturas> findByFacturacionAutomatica();
	
	//To search for serie facturas used to generate facturas rectificativas
	@Query(value = "SELECT DISTINCT(i) FROM SerieFacturas i WHERE i.rectificativas = true")
	List<SerieFacturas> findByRectificativas();
	
	//To search for serie facturas used to generate facturas rectificativas
	@Query(value = "SELECT DISTINCT(i) FROM SerieFacturas i WHERE i.rectificativas = false ORDER BY descripcion")
	List<SerieFacturas> findByNotRectificativas();
	
	//To search by descripcion (exact match)
	@Query(value = "SELECT COUNT(i) FROM SerieFacturas i WHERE i.descripcion LIKE ?1")
	long countExistingSeriesFacturas(String desc);
	
	//To search by descripcion and id (exact match)
	@Query(value = "SELECT COUNT(i) FROM SerieFacturas i WHERE i.descripcion LIKE ?1 AND i.id != ?2")
	long countExistingSeriesFacturas(String desc, Long id);
	
	//To count series for facturacion automatica
	@Query(value = "SELECT COUNT(i) FROM SerieFacturas i WHERE i.facturacionAutomatica = true AND i.id != ?1")
	long countExistingSeriesAutomaticas(Long id);
	
	//To count series for facturacion automatica
	@Query(value = "SELECT COUNT(i) FROM SerieFacturas i WHERE i.facturacionAutomatica = true")
	long countExistingSeriesAutomaticas();
	
	//To count series for facturas rectificativas
	@Query(value = "SELECT COUNT(i) FROM SerieFacturas i WHERE i.rectificativas = true AND i.id != ?1")
	long countExistingSeriesRectificativas(Long id);
	
	//To count series for facturas rectificativas
	@Query(value = "SELECT COUNT(i) FROM SerieFacturas i WHERE i.rectificativas = true")
	long countExistingSeriesRectificativas();
	
	//To count series for facturas proforma
	@Query(value = "SELECT COUNT(i) FROM SerieFacturas i WHERE i.facturasProforma = true AND i.id != ?1")
	long countExistingSeriesProforma(Long id);
	
	//To count series for facturas rectificativas
	@Query(value = "SELECT COUNT(i) FROM SerieFacturas i WHERE i.facturasProforma = true")
	long countExistingSeriesProforma();
	
	//Using this is not necessary to use the list of the entity
	@Query(value = "SELECT COUNT(i) FROM Factura i WHERE i.serie.id = ?1")
	long countRelatedFacturas(Long id);
	
}
