package com.imta.cdi.service.model.repository;

import com.imta.cdi.service.model.model.SalleEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface SalleRepository extends CrudRepository<SalleEntity, Long> {

    List<SalleEntity> findAll();

    SalleEntity findById(long idsalle) ;
}
