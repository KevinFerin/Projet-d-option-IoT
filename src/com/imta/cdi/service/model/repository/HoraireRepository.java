package com.imta.cdi.service.model.repository;

import com.imta.cdi.service.model.model.HoraireEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HoraireRepository extends CrudRepository<HoraireEntity, Long> {

    List<HoraireEntity>findAll();
    HoraireEntity findById(long id);
}
