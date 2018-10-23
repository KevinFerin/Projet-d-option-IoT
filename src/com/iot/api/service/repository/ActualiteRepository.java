package com.iot.api.service.repository;

import com.iot.api.service.model.ActualiteEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActualiteRepository extends CrudRepository<ActualiteEntity, Long> {
    List<ActualiteEntity> findAll();
    ActualiteEntity findById(long Id);
}


