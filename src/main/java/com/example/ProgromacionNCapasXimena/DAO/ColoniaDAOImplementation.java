package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.JPA.Colonia;
import com.example.ProgromacionNCapasXimena.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOImplementation implements IColoniaDAO {

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;


    @Override
    public Result ColoniaByIdMunicipioJPA(int IdMunicipio) {

        Result result = new Result();

        try {

            TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Colonia> queryColonia = entityManager.createQuery("FROM Colonia WHERE Municipio.IdMunicipio = :idmunicipio", com.example.ProgromacionNCapasXimena.JPA.Colonia.class);
            
            queryColonia.setParameter("idmunicipio", IdMunicipio);
            
            List<com.example.ProgromacionNCapasXimena.JPA.Colonia> coloniasJPA = queryColonia.getResultList();

            result.objects = new ArrayList<>();
            
            for (com.example.ProgromacionNCapasXimena.JPA.Colonia coloniaJPA : coloniasJPA) {
                
                Colonia colonia = new Colonia();
                
                colonia = coloniaJPA;
                
                result.objects.add(colonia);
            }

            result.correct = true;

        } catch (Exception Ex) {
            result.correct = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;
        }

        return result;
    }

}
