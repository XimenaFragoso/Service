
package com.example.ProgromacionNCapasXimena.RestController;

import com.example.ProgromacionNCapasXimena.DAO.MunicipioDAOImplementation;
import com.example.ProgromacionNCapasXimena.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/municipioapi")
public class MunicipioRestController {
    
    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;
    
    @GetMapping("/MunicipioByIdEstado/{IdEstado}")
    public ResponseEntity MunicipioGetByIdEstado(@PathVariable int IdEstado){
        Result result = municipioDAOImplementation.MunicipioByIdEstadoJPA(IdEstado);
        if(result.correct){
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build(); 
        }
    }
    
}
