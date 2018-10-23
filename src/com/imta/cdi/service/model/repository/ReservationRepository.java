package com.imta.cdi.service.model.repository;

import com.imta.cdi.service.model.model.ReservationEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {
    List<ReservationEntity> findAll() ;
    ReservationEntity findById(long idreservation);


}
