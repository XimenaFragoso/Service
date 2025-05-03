package com.example.ProgromacionNCapasXimena.JPA;

import java.util.List;

public class UsuarioDireccion {
    
    public Usuario Usuario;
    public List<Direccion> Direcciones;
    public Direccion Direccion;

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }

}
