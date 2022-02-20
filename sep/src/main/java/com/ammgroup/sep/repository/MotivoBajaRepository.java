package com.ammgroup.sep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ammgroup.sep.model.MotivoBaja;

public interface MotivoBajaRepository extends JpaRepository<MotivoBaja, Long> {
	
	//Using wildcards to search by descripcion (starting with)
	@Query(value = "SELECT DISTINCT(i) FROM MotivoBaja i WHERE i.descripcion LIKE ?1%")
	List<MotivoBaja> findByDescripcion(String desc);

	//Using this is not necessary to use the Socios list of the entity
	@Query(value = "SELECT COUNT(i) FROM Socio i WHERE i.motivoBaja.id = ?1")
	long countRelatedSocios(Long id);
	
	//To search by descripcion (exact match)
	@Query(value = "SELECT COUNT(i) FROM MotivoBaja i WHERE i.descripcion LIKE ?1")
	long countExistingMotivosBajas(String desc);
	
	//To search by descripcion (exact match) and id
	@Query(value = "SELECT COUNT(i) FROM MotivoBaja i WHERE i.descripcion LIKE ?1 AND i.id != ?2")
	long countExistingMotivosBajas(String desc, Long id);
}
