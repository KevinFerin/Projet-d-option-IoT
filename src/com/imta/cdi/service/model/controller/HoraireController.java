package com.imta.cdi.service.model.controller;


import com.imta.cdi.service.model.model.HoraireEntity;
import com.imta.cdi.service.model.repository.HoraireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HoraireController {
    @Autowired
    private HoraireRepository horaireRepository;

    @GetMapping("/horaires/all")
    List<HoraireEntity> all(){
        return horaireRepository.findAll();
    }

    @RequestMapping(value="/horaires/post", method= RequestMethod.POST)
    String postHoraire(@RequestBody HoraireEntity horaire) {
        try{
            System.out.println(horaire.toString());
            horaireRepository.save(horaire);
        }
        catch(Exception e){
            return "Erreur";
        }
        return "Horaire posté !";
    }

    @DeleteMapping("/horaires/delete")
    public ResponseEntity<?> deleteHoraire(@RequestParam(value = "id") long Id) {
        HoraireEntity horaire = horaireRepository.findById(Id);
        horaireRepository.delete(horaire);

        return ResponseEntity.ok().build();
    }
}
