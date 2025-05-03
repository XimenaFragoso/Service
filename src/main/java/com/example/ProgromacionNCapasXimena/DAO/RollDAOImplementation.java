package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.JPA.Result;
import com.example.ProgromacionNCapasXimena.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RollDAOImplementation implements IRollDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired 
    private EntityManager entityManager;
    
    @Override
    public Result GetAllJPA() {
        
        Result result = new Result(); 
        
        try {
            TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Roll> queryRolles = entityManager.createQuery("FROM Roll", com.example.ProgromacionNCapasXimena.JPA.Roll.class); 
            List<com.example.ProgromacionNCapasXimena.JPA.Roll> rolles = queryRolles.getResultList();
            
            result.objects = new ArrayList<>();
            
            for (com.example.ProgromacionNCapasXimena.JPA.Roll rollJPA : rolles) {
                
                Usuario usuario = new Usuario();
                
                usuario.Roll.setIdRoll(usuario.Roll.getIdRoll());
                usuario.Roll.setNombre(usuario.Roll.getNombre());
                
                result.objects.add(usuario); 
            }
            
        } catch (Exception Ex) {
            result.correct = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex; 
        }
        return result;
        

    }
    
      
    
    
}
