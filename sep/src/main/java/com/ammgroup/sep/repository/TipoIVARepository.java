package com.ammgroup.sep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ammgroup.sep.model.TipoIVA;

public interface TipoIVARepository extends JpaRepository<TipoIVA, Long> {

	//Using wildcards to search by descripcion (starting with)
	@Query(value = "SELECT DISTINCT(i) FROM TipoIVA i WHERE i.descripcion LIKE ?1%")
	List<TipoIVA> findByDescripcion(String desc);
	
	//To search by descripcion (exact match)
	@Query(value = "SELECT COUNT(i) FROM TipoIVA i WHERE i.descripcion LIKE ?1")
	long countExistingTiposIVA(String desc);
	
	//To search by descripcion (exact match)
	@Query(value = "SELECT COUNT(i) FROM TipoIVA i WHERE i.descripcion LIKE ?1 AND i.id != ?2")
	long countExistingTiposIVA(String desc, Long id);
	
	//Using this is not necessary to use the list of the entity
	@Query(value = "SELECT COUNT(i) FROM Factura i WHERE i.tipoIVA.id = ?1")
	long countRelatedFacturas(Long id);
	
	//Using this is not necessary to use the list of the entity
	@Query(value = "SELECT COUNT(i) FROM SerieFacturas i WHERE i.tipoIVA.id = ?1")
	long countRelatedSeriesFacturas(Long id);
}
