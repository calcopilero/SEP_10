package com.ammgroup.sep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ammgroup.sep.model.Agencia;

public interface AgenciaRepository extends JpaRepository<Agencia, Long>, JpaSpecificationExecutor<Agencia> {

	//Using wildcards to search by nombre (starting with)
	@Query(value = "SELECT DISTINCT(i) FROM Agencia i WHERE i.nombre LIKE ?1%")
	List<Agencia> findByNombre(String nombre);

	//Using wildcards to search by cifnif (starting with)
	@Query(value = "SELECT DISTINCT(i) FROM Agencia i WHERE i.cifnif LIKE ?1%")
	List<Agencia> findByCifnif(String cifnif);
	
	//Using this is not necessary to use the Socios list of the entity
	//To count socios by agencia
	@Query(value = "SELECT COUNT(i) FROM Socio i WHERE i.agencia.id = ?1")
	long countRelatedSocios(Long id);
	
	//To count facturas by agencia
	@Query(value = "SELECT COUNT(i) FROM Factura i WHERE i.agencia.id = ?1")
	long countRelatedFacturas(Long id);
	
	//To count by nombre (exact match)
	@Query(value = "SELECT COUNT(i) FROM Agencia i WHERE i.nombre LIKE ?1")
	long countExistingAgenciasByNombre(String nombre);
	
	//To count by nombre (exact match) and id
	@Query(value = "SELECT COUNT(i) FROM Agencia i WHERE i.nombre LIKE ?1 AND i.id != ?2")
	long countExistingAgenciasByNombre(String nombre, Long id);
	
	//To count by cifnif (exact match)
	@Query(value = "SELECT COUNT(i) FROM Agencia i WHERE i.cifnif LIKE ?1")
	long countExistingAgenciasByCifnif(String cifnif);
	
	//To count by cifnif (exact match) and id
	@Query(value = "SELECT COUNT(i) FROM Agencia i WHERE i.cifnif LIKE ?1 AND i.id != ?2")
	long countExistingAgenciasByCifnif(String cifnif, Long id);
	
}
