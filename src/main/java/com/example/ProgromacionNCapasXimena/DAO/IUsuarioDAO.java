package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.JPA.Result;
import com.example.ProgromacionNCapasXimena.JPA.Usuario;
import com.example.ProgromacionNCapasXimena.JPA.UsuarioDireccion;

public interface IUsuarioDAO {

    Result GetAllJPA();
    
    Result AddJPA(UsuarioDireccion usuarioDireccion);
    
    Result UsuarioDeleteJPA(int IdUsuario);
    
    Result UpdateUsuarioJPA(Usuario usuario);
    
    Result GetAllDinamicoJPA(Usuario usuario); 
    
    Result GetByIdJPA(int IdUsuario); 
    
    Result DireccionByIdJPA(int IdUsuario); 
    
}
