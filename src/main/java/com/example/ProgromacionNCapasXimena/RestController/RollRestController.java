package com.example.ProgromacionNCapasXimena.RestController;

import com.example.ProgromacionNCapasXimena.DAO.RollDAOImplementation;
import com.example.ProgromacionNCapasXimena.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rollapi")
public class RollRestController {
    
    //inyeccion de dependencias
    @Autowired
    private RollDAOImplementation rollDAOImplementation;
    
    @GetMapping
    public ResponseEntity GetAll(){
        
        Result result = rollDAOImplementation.GetAll();
        
        if(result.correct){
            return ResponseEntity.ok(result);
        } else { 
            return ResponseEntity.badRequest().build();
        }
    
    }
    
}
