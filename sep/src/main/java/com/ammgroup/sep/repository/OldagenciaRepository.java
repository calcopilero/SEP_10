package com.ammgroup.sep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ammgroup.sep.model.Oldagencia;

public interface OldagenciaRepository extends JpaRepository<Oldagencia, Long>, JpaSpecificationExecutor<Oldagencia> {

}
