package com.ammgroup.sep.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ammgroup.sep.model.Agencia;
import com.ammgroup.sep.model.Reclamacion;
import com.ammgroup.sep.model.Socio;

public interface ReclamacionRepository extends JpaRepository<Reclamacion, Long>, JpaSpecificationExecutor<Reclamacion> {

	//To search by socio
	@Query(value = "SELECT DISTINCT(i) FROM Reclamacion i WHERE i.socio = ?1")
	List<Reclamacion> findBySocio(Socio soc);
	
	//To search by socio
	@Query(value = "SELECT DISTINCT(i) FROM Reclamacion i WHERE i.socio = ?1")
	List<Reclamacion> findBySocio(Socio soc, Sort sort);
	
	//To search by agencia
	@Query(value = "SELECT DISTINCT(i) FROM Reclamacion i WHERE i.agencia = ?1")
	List<Reclamacion> findByAgencia(Agencia age);
	
	//To search by agencia
	@Query(value = "SELECT DISTINCT(i) FROM Reclamacion i WHERE i.agencia = ?1")
	List<Reclamacion> findByAgencia(Agencia age, Sort sort);
	
	//To get the max number of reclamacion in a certain time interval
	@Query(value = "SELECT MAX(i.numero) FROM Reclamacion i WHERE (i.fechaReclamacion BETWEEN ?1 AND ?2)")
	Integer getMaximumReclamacionNumber(Date fdateStart, Date fdateEnd);
}
