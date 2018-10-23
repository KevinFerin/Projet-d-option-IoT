package com.imta.cdi.service.model.controller;

import com.imta.cdi.service.model.model.ReservationEntity;
import com.imta.cdi.service.model.repository.ReservationRepository;
import com.imta.cdi.service.model.repository.SalleRepository;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;
    private SalleRepository salleRepository;

    @GetMapping("/reservation/get-all")
    public List<ReservationEntity> getAll() { return reservationRepository.findAll();}

    @GetMapping("reservation/get")
    public ReservationEntity getById(@RequestParam(value="id") long idreservation) {
        ReservationEntity reservation = reservationRepository.findById(idreservation);
        return reservation;
    }

    @RequestMapping(value="reservation/reserver", method = RequestMethod.POST)
    String addReservation (@RequestBody ReservationEntity newReservation) {
        try {
            System.out.println(newReservation.toString());
            reservationRepository.save(newReservation);
        }
        catch (Exception e ) {
            return "Error";
        }
        return "Votre réservation est confirmée";

    }




}
