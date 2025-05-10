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
    
    @Autowired
    private EntityManager entityManager;
    
    //    ----------

    @Override
    public Result EstadoByIdPaisJPA(int IdPais) {
        
        Result result = new Result();
        
        try {
            TypedQuery<Estado> queryEstado = entityManager.createQuery("FROM Estado WHERE Pais.IdPais = :idpais", Estado.class);
            queryEstado.setParameter("idpais", IdPais);
            List<Estado> listaEstados = queryEstado.getResultList();
            result.objects = new ArrayList<>(); 
            
            for (Estado estadoJPA : listaEstados) {
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
