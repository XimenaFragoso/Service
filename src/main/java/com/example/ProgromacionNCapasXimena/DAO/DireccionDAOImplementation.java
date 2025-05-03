package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.JPA.Direccion;
import com.example.ProgromacionNCapasXimena.JPA.Result;
import com.example.ProgromacionNCapasXimena.JPA.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements IDireccionDAO {

//    @Autowired
//    JdbcTemplate jdbcTemplate;

    @Autowired //conexion de JPA
    private EntityManager entityManager;

    @Transactional
    @Override
    public Result DireccionAddJPA(UsuarioDireccion usuarioDireccion) {

        Result result = new Result();

        try {
            
            entityManager.persist(usuarioDireccion.Direccion);
            
            usuarioDireccion.Direccion = usuarioDireccion.Direccion; 
            
            result.correct = true;

        } catch (Exception Ex) {
            result.object = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;

        }
        return result;

    }

    @Transactional
    @Override
    //borrar direccion con iddirecion vista detailDireccion
    public Result DireccionDeletJPA(int IdDireccion) {
        Result result = new Result();

        try {
            com.example.ProgromacionNCapasXimena.JPA.Direccion Direccion = entityManager.find(com.example.ProgromacionNCapasXimena.JPA.Direccion.class, IdDireccion);

            entityManager.remove(Direccion);

            return result;

        } catch (Exception Ex) {
            result.correct = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;
        }

        return result;

    }

    @Transactional
    @Override   
    public Result UpdateDireccionJPA(Direccion direccion) {
        
        Result result = new Result();
        
        try{
            
            entityManager.merge(direccion);
            
        }catch (Exception Ex) {
            result.correct = false; 
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;
            
        }
        
        return result;
        
    }
 
    @Override
    public Result GetByIdDireccionJPA(int IdDireccion) {
        Result result = new Result(); 
        try{
            
            com.example.ProgromacionNCapasXimena.JPA.Direccion direccion = new com.example.ProgromacionNCapasXimena.JPA.Direccion();
            
            direccion =  entityManager.find(com.example.ProgromacionNCapasXimena.JPA.Direccion.class, IdDireccion);
            
            result.object = direccion; 
            
            result.correct = true;
            
            //recuperar las direcciones
        }catch(Exception Ex){
            result.correct = false; 
            result.errorMessage = Ex.getLocalizedMessage(); 
            result.ex = Ex;
            
        }
        return result;
    }

}
