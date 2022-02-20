package com.ammgroup.sep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ammgroup.sep.model.Oldsocio;

public interface OldsocioRepository extends JpaRepository<Oldsocio, Long>, JpaSpecificationExecutor<Oldsocio> {

}
