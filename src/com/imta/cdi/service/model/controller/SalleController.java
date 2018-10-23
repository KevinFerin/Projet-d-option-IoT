package com.imta.cdi.service.model.controller;
import com.imta.cdi.service.model.model.SalleEntity;
import com.imta.cdi.service.model.repository.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.DecimalMax;
import java.util.List;

@RestController
public class SalleController {

    @Autowired
    private SalleRepository salleRepository;

    @GetMapping("/salle/get-all")
    List<SalleEntity> all() { return salleRepository.findAll();}

    @GetMapping("/salle/get")
    public SalleEntity getById(@RequestParam(value="id") long idsalle){
            SalleEntity salle = salleRepository.findById(idsalle);
            return salle;
    }

    @RequestMapping(value= "salle/add", method= RequestMethod.POST)
    String addSalle( @RequestBody SalleEntity newSalle) {
        try {
            System.out.println(newSalle.toString());
            salleRepository.save(newSalle);
        }
        catch (Exception e){
            return "Erreur !";
        }
        return "Salle bien ajoutée !";
    }

    @DeleteMapping("salle/delete")
    public ResponseEntity<?> deleteSalle(@RequestParam(value="id") long idsalle) {
        SalleEntity salle = salleRepository.findById(idsalle);
        salleRepository.delete(salle);

        return ResponseEntity.ok().build();

    }



}
