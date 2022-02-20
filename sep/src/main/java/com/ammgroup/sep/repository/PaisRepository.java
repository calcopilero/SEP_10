package com.ammgroup.sep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ammgroup.sep.model.Pais;

public interface PaisRepository extends JpaRepository<Pais, Long> {

	//Using wildcards to search by descripcion (starting with)
	@Query(value = "SELECT DISTINCT(i) FROM Pais i WHERE i.descripcion LIKE ?1%")
	List<Pais> findByDescripcion(String desc);
	
	//To search by descripcion (exact match)
	@Query(value = "SELECT COUNT(i) FROM Pais i WHERE i.descripcion LIKE ?1")
	long countExistingPaises(String desc);
	
	//To search by descripcion and id (exact match)
	@Query(value = "SELECT COUNT(i) FROM Pais i WHERE i.descripcion LIKE ?1 AND i.id != ?2")
	long countExistingPaises(String desc, Long id);
	
	//Using this is not necessary to use the list of the entity
	@Query(value = "SELECT COUNT(i) FROM Socio i WHERE i.pais.id = ?1")
	long countRelatedSocios(Long id);
	
	//Using this is not necessary to use the list of the entity
	@Query(value = "SELECT COUNT(i) FROM Agencia i WHERE i.pais.id = ?1")
	long countRelatedAgencias(Long id);
}
