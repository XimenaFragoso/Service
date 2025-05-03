package com.example.ProgromacionNCapasXimena.DAO;
import com.example.ProgromacionNCapasXimena.JPA.Estado;
import com.example.ProgromacionNCapasXimena.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


//implementa la logica de la BD
@Repository
public class EstadoDAOImplementation implements IEstadoDAO{
    
//    @Autowired 
//    private JdbcTemplate jdbcTemplate; 
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result EstadoByIdPaisJPA(int IdPais) {
        
        Result result = new Result();
        
        try {
            TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Estado> queryEstado = entityManager.createQuery("FROM Estado WHERE Pais.IdPais = :idpais", com.example.ProgromacionNCapasXimena.JPA.Estado.class);
            
            queryEstado.setParameter("idpais", IdPais);
            
            List<com.example.ProgromacionNCapasXimena.JPA.Estado> estadosJPA = queryEstado.getResultList();
            
            result.objects = new ArrayList(); 
            
            for (com.example.ProgromacionNCapasXimena.JPA.Estado estadoJPA : estadosJPA) {
                
                Estado estado = new Estado(); 
                
                estado = estadoJPA;
                
                result.objects.add(estado);
            }
            
            result.correct = true;
            
        } catch (Exception Ex) {
            result.object = false; 
            result.errorMessage = Ex.getLocalizedMessage(); 
            result.ex = Ex; 
            
        }
        return result;
    }
    
    
}
