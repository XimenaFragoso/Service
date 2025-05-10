package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.JPA.Municipio;
import com.example.ProgromacionNCapasXimena.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class MunicipioDAOImplementation implements IMunicipioDAO {
    
    //    ----------
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result MunicipioByIdEstadoJPA(int IdEstado) {
        Result result = new Result(); 
        
        try {
            TypedQuery<Municipio> queryMunicipio = entityManager.createQuery("FROM Municipio WHERE Estado.IdEstado = :idestado", Municipio.class);
            
            queryMunicipio.setParameter("idestado", IdEstado);
            
            List<Municipio> listaMunicipios = queryMunicipio.getResultList();

            //se guarda la lista de municipios en el object
            result.objects = new ArrayList<>();
            
            for (Municipio municipioJPA : listaMunicipios) {
                Municipio municipio = new Municipio(); 
                municipio = municipioJPA; 
                result.objects.add(municipio);
                
            }
            
            result.correct = true;
            
            
        }catch(Exception Ex){
            result.object = false; 
            result.errorMessage = Ex.getLocalizedMessage(); 
            result.ex = Ex;
        }
        return result; 
    }
}