package com.ammgroup.sep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ammgroup.sep.model.EstadoReclamacion;

public interface EstadoReclamacionRepository extends JpaRepository<EstadoReclamacion, Long>, JpaSpecificationExecutor<EstadoReclamacion> {

	//Using wildcards to search by descripcion (starting with)
	@Query(value = "SELECT DISTINCT(i) FROM EstadoReclamacion i WHERE i.descripcion LIKE ?1%")
	List<EstadoReclamacion> findByDescripcion(String desc);
	
	//To search by descripcion (exact match)
	@Query(value = "SELECT COUNT(i) FROM EstadoReclamacion i WHERE i.descripcion LIKE ?1")
	long countExistingEstadosReclamaciones(String desc);
	
	//To search by descripcion and id (exact match)
	@Query(value = "SELECT COUNT(i) FROM EstadoReclamacion i WHERE i.descripcion LIKE ?1 AND i.id != ?2")
	long countExistingEstadosReclamaciones(String desc, Long id);
	
	//Using this is not necessary to use the Socios list of the entity
	@Query(value = "SELECT COUNT(i) FROM Reclamacion i WHERE i.estadoReclamacion.id = ?1")
	long countRelatedReclamaciones(Long id);
}
