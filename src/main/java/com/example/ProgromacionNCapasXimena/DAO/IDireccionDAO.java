package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.JPA.Direccion;
import com.example.ProgromacionNCapasXimena.JPA.Result;
import com.example.ProgromacionNCapasXimena.JPA.UsuarioDireccion;

public interface IDireccionDAO {

    Result DireccionAddJPA(UsuarioDireccion usuarioDireccion);
    
    Result DireccionDeletJPA(int IdDireccion);

    Result UpdateDireccionJPA(Direccion direccion); 
    
    Result GetByIdDireccionJPA(int IdDireccion);
    
}
