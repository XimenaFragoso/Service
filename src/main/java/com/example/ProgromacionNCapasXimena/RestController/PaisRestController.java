package com.example.ProgromacionNCapasXimena.RestController;

import com.example.ProgromacionNCapasXimena.DAO.PaisDAOImplementation;
import com.example.ProgromacionNCapasXimena.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paisapi")
public class PaisRestController {
    
    @Autowired //inyectar dependencia
    private PaisDAOImplementation paisDAOImplementation;
    
    @GetMapping
    public ResponseEntity GetAll(){
        
        Result result = paisDAOImplementation.GetAllJPAPais();
        
        if(result.correct){
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
        
    }
}
