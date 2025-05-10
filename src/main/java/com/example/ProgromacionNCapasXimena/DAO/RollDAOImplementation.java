package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RollDAOImplementation implements IRollDAO{
    
    @Autowired 
    private EntityManager entityManager;
    
    @Override
    public Result GetAll() {
        
        Result result = new Result(); 
        
        try {
            //la lista de la consulta se guarda en objet por lo cual ya no es necesario crear un list o iterar los valores
            TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Roll> queryRolles = entityManager.createQuery("FROM Roll", com.example.ProgromacionNCapasXimena.JPA.Roll.class); 
            result.object = queryRolles.getResultList();
            result.correct = true;
           
        } catch (Exception Ex) {
            result.correct = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex; 
        }
        
        return result;
        
    }
     
}
