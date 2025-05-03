package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.JPA.Direccion;
import com.example.ProgromacionNCapasXimena.JPA.Result;
import com.example.ProgromacionNCapasXimena.JPA.Usuario;
import com.example.ProgromacionNCapasXimena.JPA.UsuarioDireccion;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository //Logica de Base de Datos
public class UsuarioDAOImplementation implements IUsuarioDAO {

//    @Autowired //Inyeccion de depencias  
//    private JdbcTemplate jdbcTemplate; //conexión directa a la BD 
    @Autowired //conexion de JPA
    private EntityManager entityManager;

    @Override
    public Result GetAllJPA() {

        Result result = new Result();

        try {
            //Lenguaje JPQL
            TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Usuario> queryUsuarios = entityManager.createQuery("FROM Usuario", com.example.ProgromacionNCapasXimena.JPA.Usuario.class);
            List<com.example.ProgromacionNCapasXimena.JPA.Usuario> usuarios = queryUsuarios.getResultList();

            result.objects = new ArrayList<>();

            for (com.example.ProgromacionNCapasXimena.JPA.Usuario usuario : usuarios) {

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

                usuarioDireccion.Usuario = usuario;

                //obtener direcciones por idusuario
                TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Direccion> queryDireccion = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", com.example.ProgromacionNCapasXimena.JPA.Direccion.class);
                queryDireccion.setParameter("idusuario", usuario.getIdUsuario());

                List<com.example.ProgromacionNCapasXimena.JPA.Direccion> direccionesJPA = queryDireccion.getResultList();
                usuarioDireccion.Direcciones = direccionesJPA;
                
                result.objects.add(usuarioDireccion);
            }
            

            result.correct = true;
        } catch (Exception Ex) {
            result.correct = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result AddJPA(UsuarioDireccion usuarioDireccion) {

        Result result = new Result();

        try {

            entityManager.persist(usuarioDireccion.Usuario);
            
            usuarioDireccion.Direccion.Usuario = usuarioDireccion.Usuario; 
            entityManager.persist(usuarioDireccion.Direccion);

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
    public Result UsuarioDeleteJPA(int IdUsuario) {

        Result result = new Result();

        try {
            //Se recuperan las direcciones delos usuarios para asi poder eliminarlas y consecutivamente eliminar el usuario
            TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Direccion> queryDireccionesUsuario = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", com.example.ProgromacionNCapasXimena.JPA.Direccion.class);
            queryDireccionesUsuario.setParameter("idusuario", IdUsuario);
            List<com.example.ProgromacionNCapasXimena.JPA.Direccion> direccionesUsuario = queryDireccionesUsuario.getResultList();
            //Obtener direcciones por IdUsuario

            for (com.example.ProgromacionNCapasXimena.JPA.Direccion direccionJPA : direccionesUsuario) {

                //Ya no se obtienen los valores de direccion ya que como tal en direccionJPA se encuentra todo
                entityManager.remove(direccionJPA);
            }

            //se recupera el ID del Usuario
            com.example.ProgromacionNCapasXimena.JPA.Usuario usuarioJPA
                    = entityManager.find(com.example.ProgromacionNCapasXimena.JPA.Usuario.class, IdUsuario);

            entityManager.remove(usuarioJPA);

            return result;

        } catch (Exception Ex) {
            result.object = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;

        }
        return result;

    }

    @Transactional
    @Override
    public Result UpdateUsuarioJPA(Usuario usuario) {

        Result result = new Result();

        try {

            entityManager.merge(usuario);

        } catch (Exception Ex) {
            result.object = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;
        }
        return result;
    }

    //busqueda dinamica
    @Override
    public Result GetAllDinamicoJPA(Usuario usuario) {
        Result result = new Result();
        try {
            //nombre, apellido paterno, apellido materno, roll, status

            String queryDinamico = "FROM Usuario";

            queryDinamico = queryDinamico + "WHERE Nombre = :nombre ";
            queryDinamico = queryDinamico + "AND ApellidoPaterno = :apellidopaterno";
            queryDinamico = queryDinamico + "AND ApellidoMaterno = :apellidomaterno";

            TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Usuario> queryUsuario = entityManager.createQuery(queryDinamico, com.example.ProgromacionNCapasXimena.JPA.Usuario.class);
            queryUsuario.setParameter(":nombre", usuario.getNombre());
            queryUsuario.setParameter(":apellidopaterno", usuario.getApellidoPaterno());
            queryUsuario.setParameter(":apellidomaterno", usuario.getApellidoMaterno());

            List<com.example.ProgromacionNCapasXimena.JPA.Usuario> usuarios = queryUsuario.getResultList();

            result.correct = true;

        } catch (Exception Ex) {
            result.object = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;

        }

        return result;
    }

    @Override
    public Result GetByIdJPA(int IdUsuario) {

        Result result = new Result();

        try {
            com.example.ProgromacionNCapasXimena.JPA.Usuario usuario = new com.example.ProgromacionNCapasXimena.JPA.Usuario();
            
            usuario = entityManager.find(com.example.ProgromacionNCapasXimena.JPA.Usuario.class, IdUsuario);
            
            result.object = usuario;
            
            result.correct = true; 
            
        } catch (Exception Ex) {
            result.object = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;
        }
        return result;
    }

    @Override
    public Result DireccionByIdJPA(int IdUsuario) {
        Result result = new Result();

        try {
            TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Direccion> querydireccionByIdUsuario = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", com.example.ProgromacionNCapasXimena.JPA.Direccion.class);
            querydireccionByIdUsuario.setParameter("idusuario", IdUsuario);
            
            //lista que sale de la consulta "typedquery"
            List<com.example.ProgromacionNCapasXimena.JPA.Direccion> direcciones = querydireccionByIdUsuario.getResultList();
            
            result.objects = new ArrayList<>(); 
            
            
            for (Direccion direccionJPA : direcciones) {
                
                Direccion direccion = new Direccion(); 
                
                direccion = direccionJPA;
                
                result.objects.add(direccion);
                
            }
        } catch (Exception Ex) {
            result.object = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;
        }
        return result;

    }

}
