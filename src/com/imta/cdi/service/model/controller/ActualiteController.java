package com.imta.cdi.service.model.controller;


import com.imta.cdi.service.model.model.ActualiteEntity;
import com.imta.cdi.service.model.repository.ActualiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActualiteController {
    @Autowired
    private ActualiteRepository actualiteRepository;

    @GetMapping("/actu/all")
    List<ActualiteEntity> all(){
        return actualiteRepository.findAll();
    }


    @RequestMapping(value="/actu/post", method=RequestMethod.POST)
    String postActu(@RequestBody ActualiteEntity article) {
        try{
            System.out.println(article.toString());
            actualiteRepository.save(article);
        }
        catch(Exception e){
            return "Erreur";
        }
        return "Article posté !";
    }

    @DeleteMapping("/actu/delete")
    public ResponseEntity<?> deleteActu(@RequestParam(value = "id") long Id) {
        ActualiteEntity actu = actualiteRepository.findById(Id);
        actualiteRepository.delete(actu);

        return ResponseEntity.ok().build();
    }




}
