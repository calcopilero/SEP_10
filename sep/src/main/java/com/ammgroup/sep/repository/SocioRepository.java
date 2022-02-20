package com.ammgroup.sep.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ammgroup.sep.model.Socio;

public interface SocioRepository extends JpaRepository<Socio, Long>, JpaSpecificationExecutor<Socio> {

	//Using wildcards to search by nombre (starting with)
	@Query(value = "SELECT DISTINCT(i) FROM Socio i WHERE i.nombre LIKE ?1%")
	List<Socio> findByNombre(String nombre);

	//Using wildcards to search by apellidos (starting with)
	@Query(value = "SELECT DISTINCT(i) FROM Socio i WHERE i.apellidos LIKE ?1%")
	List<Socio> findByApellidos(String apellidos);
	
	//Using wildcards to search by cifnif (starting with)
	@Query(value = "SELECT DISTINCT(i) FROM Socio i WHERE i.cifnif LIKE ?1%")
	List<Socio> findByCifnif(String cifnif);
	
	//To count by cifnif (exact match)
	@Query(value = "SELECT COUNT(i) FROM Socio i WHERE i.cifnif LIKE ?1")
	long countExistingSociosByCifnif(String cifnif);
	
	//To count by cifnif (exact match)
	@Query(value = "SELECT COUNT(i) FROM Socio i WHERE i.cifnif LIKE ?1 AND i.id != ?2")
	long countExistingSociosByCifnif(String cifnif, Long id);
	
	//To count by codigo socio
	@Query(value = "SELECT COUNT(i) FROM Socio i WHERE i.codigoSocio = ?1")
	long countExistingSociosByCodigoSocio(int cod);
	
	//To count by codigo socio
	@Query(value = "SELECT COUNT(i) FROM Socio i WHERE i.codigoSocio = ?1 AND i.id != ?2")
	long countExistingSociosByCodigoSocio(int cod, Long id);
	
	//To count socios activos
	@Query(value = "SELECT COUNT(i) FROM Socio i WHERE i.baja = false")
	long countExistingSociosActivos();
	
	//Obtain max value of codigo socio
	@Query(value = "SELECT MAX(i.codigoSocio) FROM Socio i")
	int getHigherCodigoSocio();
	
	//To get the facturas between two dates
	@Query(value = "SELECT DISTINCT(i) FROM Socio i WHERE i.fechaAlta BETWEEN ?1 AND ?2 ORDER BY i.fechaAlta DESC")
	List<Socio> findAltasBetweenDates(Date stardDate, Date endDate);
	
}
