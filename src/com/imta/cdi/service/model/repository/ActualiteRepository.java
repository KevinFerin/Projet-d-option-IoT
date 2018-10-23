package com.imta.cdi.service.model.repository;

import com.imta.cdi.service.model.model.ActualiteEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActualiteRepository extends CrudRepository<ActualiteEntity, Long> {
    List<ActualiteEntity> findAll();
    ActualiteEntity findById(long Id);
}


