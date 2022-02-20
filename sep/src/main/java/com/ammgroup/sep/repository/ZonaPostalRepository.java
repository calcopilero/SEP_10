package com.ammgroup.sep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ammgroup.sep.model.ZonaPostal;

public interface ZonaPostalRepository extends JpaRepository<ZonaPostal, Long> {

	//Using wildcards to search by descripcion (starting with)
	@Query(value = "SELECT DISTINCT(i) FROM ZonaPostal i WHERE i.descripcion LIKE ?1%")
	List<ZonaPostal> findByDescripcion(String desc);
	
	//Using this is not necessary to use the Socios list of the entity
	//To count socios by zona postal
	@Query(value = "SELECT COUNT(i) FROM Socio i WHERE i.zonaPostal.id = ?1")
	long countRelatedSocios(Long id);
	
	//Using this is not necessary to use the Agencias list of the entity
	//To count agencias by zona postal
	@Query(value = "SELECT COUNT(i) FROM Agencia i WHERE i.zonaPostal.id = ?1")
	long countRelatedAgencias(Long id);
	
	//To count by descripcion (exact match)
	@Query(value = "SELECT COUNT(i) FROM ZonaPostal i WHERE i.descripcion LIKE ?1")
	long countExistingZonasPostales(String desc);
	
	//To count by descripcion (exact match) and id
	@Query(value = "SELECT COUNT(i) FROM ZonaPostal i WHERE i.descripcion LIKE ?1 AND i.id != ?2")
	long countExistingZonasPostales(String desc, Long id);
}
