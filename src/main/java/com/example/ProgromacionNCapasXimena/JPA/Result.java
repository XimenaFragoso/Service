package com.example.ProgromacionNCapasXimena.JPA;

import java.io.Serializable;
import java.util.List;
import org.apache.poi.ss.formula.functions.T;

public class Result {
    
    public boolean correct; //se ejecta o noic String errorMessage;      
    public String errorMessage; //error de mensaje con su descripcion
    public Exception ex; //excepciones
    public Object object;
    public List <Object> objects; 
    
}
