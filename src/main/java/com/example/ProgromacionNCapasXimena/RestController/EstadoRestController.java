package com.example.ProgromacionNCapasXimena.RestController;

import com.example.ProgromacionNCapasXimena.DAO.EstadoDAOImplementation;
import com.example.ProgromacionNCapasXimena.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estadoapi")
public class EstadoRestController {
    
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
    
    @GetMapping("/EstadoByIdPais/{IdPais}")
    public ResponseEntity EstadoGetByIdPais(@PathVariable int IdPais){
        Result result = estadoDAOImplementation.EstadoByIdPaisJPA(IdPais); 
        if(result.correct){
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
