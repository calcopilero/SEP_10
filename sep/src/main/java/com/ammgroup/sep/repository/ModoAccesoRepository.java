package com.ammgroup.sep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ammgroup.sep.model.ModoAcceso;

public interface ModoAccesoRepository extends JpaRepository<ModoAcceso, Long> {

	//Using wildcards to search by descripcion (starting with)
	@Query(value = "SELECT DISTINCT(i) FROM ModoAcceso i WHERE i.descripcion LIKE ?1%")
	List<ModoAcceso> findByDescripcion(String desc);
	
	//To search by descripcion (exact match)
	@Query(value = "SELECT COUNT(i) FROM ModoAcceso i WHERE i.descripcion LIKE ?1")
	long countExistingModosAcceso(String desc);
	
	//To search by descripcion and id (exact match)
	@Query(value = "SELECT COUNT(i) FROM ModoAcceso i WHERE i.descripcion LIKE ?1 AND i.id != ?2")
	long countExistingModosAcceso(String desc, Long id);
	
	//Using this is not necessary to use the Socios list of the entity
	//To count socios by motivo baja
	@Query(value = "SELECT COUNT(i) FROM Socio i WHERE i.modoAcceso.id = ?1")
	long countRelatedSocios(Long id);
}
