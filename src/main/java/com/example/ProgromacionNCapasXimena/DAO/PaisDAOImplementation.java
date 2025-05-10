package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.JPA.Pais;
import com.example.ProgromacionNCapasXimena.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;


@Repository
public class PaisDAOImplementation implements IPaisDAO{
    
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    
    @Autowired 
    private EntityManager entityManager;

    @Override
    public Result GetAllJPAPais() {
        
        Result result = new Result(); 
        
        try {
            
        TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Pais> queryPaises = entityManager.createQuery("FROM Pais", com.example.ProgromacionNCapasXimena.JPA.Pais.class);         
        result.object = queryPaises.getResultList();
        
        result.correct = true;

            
        } catch (Exception ex) {
            result.correct = false; 
            result.errorMessage = ex.getLocalizedMessage(); 
            result.ex = ex; 
        }
        return result;
    }
    
}

