package com.example.ProgromacionNCapasXimena.RestController;

import com.example.ProgromacionNCapasXimena.DAO.ColoniaDAOImplementation;
import com.example.ProgromacionNCapasXimena.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coloniaapi")
public class ColoniaRestController {

    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;
    
    @GetMapping("/ColoniaByIdMunicipio/{IdMunicipio}")
    public ResponseEntity ColoniaGetByIdMunicipio(@PathVariable int IdMunicipio) {
        Result result = coloniaDAOImplementation.ColoniaByIdMunicipioJPA(IdMunicipio);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
