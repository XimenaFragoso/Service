package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.JPA.Municipio;
import com.example.ProgromacionNCapasXimena.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class MunicipioDAOImplementation implements IMunicipioDAO {

//    @Autowired 
//    private JdbcTemplate jdbcTemplate; 
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result MunicipioByIdEstadoJPA(int IdEstado) {
        Result result = new Result(); 
        
        try {
            TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Municipio> queryMunicipio = entityManager.createQuery("FROM Municipio WHERE Estado.IdEstado = :idestado", com.example.ProgromacionNCapasXimena.JPA.Municipio.class);
            
            queryMunicipio.setParameter("idestado", IdEstado);
            
            List<com.example.ProgromacionNCapasXimena.JPA.Municipio> municipiosJPA = queryMunicipio.getResultList();

            result.objects = new ArrayList<>();
            
            for (com.example.ProgromacionNCapasXimena.JPA.Municipio municipioJPA : municipiosJPA) {
            
                Municipio municipio = new Municipio(); 
              
                municipio = municipioJPA;
                
                result.objects.add(municipio);
            }
            
        }catch(Exception Ex){
            result.object = false; 
            result.errorMessage = Ex.getLocalizedMessage(); 
            result.ex = Ex;
        }
        return result; 
    }
}