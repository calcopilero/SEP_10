package com.ammgroup.sep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ammgroup.sep.model.EstadoFactura;

public interface EstadoFacturaRepository extends JpaRepository<EstadoFactura, Long> {

	//Using wildcards to search by descripcion (starting with)
	@Query(value = "SELECT DISTINCT(i) FROM EstadoFactura i WHERE i.descripcion LIKE ?1%")
	List<EstadoFactura> findByDescripcion(String desc);
	
	//To search for estado facturas used by default when creating new facturas
	@Query(value = "SELECT DISTINCT(i) FROM EstadoFactura i WHERE i.estadoPorDefecto = true")
	List<EstadoFactura> findByEstadoPorDefecto();
	
	//To search for estado facturas used by default when creating new facturas
	@Query(value = "SELECT COUNT(i) FROM EstadoFactura i WHERE i.estadoPorDefecto = true")
	long countEstadosPorDefecto();
	
	//To search by descripcion (exact match)
	@Query(value = "SELECT COUNT(i) FROM EstadoFactura i WHERE i.descripcion LIKE ?1")
	long countExistingEstadosFactura(String desc);
	
	//To search by descripcion and id (exact match)
	@Query(value = "SELECT COUNT(i) FROM EstadoFactura i WHERE i.descripcion LIKE ?1 AND i.id != ?2")
	long countExistingEstadosFactura(String desc, Long id);
	
	//Using this is not necessary to use the list of the entity
	@Query(value = "SELECT COUNT(i) FROM Factura i WHERE i.estadoFactura.id = ?1")
	long countRelatedFacturas(Long id);
}
