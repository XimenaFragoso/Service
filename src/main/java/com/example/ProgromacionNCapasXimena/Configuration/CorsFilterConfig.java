package com.example.ProgromacionNCapasXimena.Configuration;

//configuracion para poder realizar el intercambio y la carga de recursos 

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsFilterConfig {
    
    //se coloca la notacion bean ya que es una configuracion 
    //personalizada
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); 
        CorsConfiguration corsconfig = new CorsConfiguration(); 
        
        //acceso a todos los metodos
        corsconfig.addAllowedOrigin("*");
        
        corsconfig.addAllowedMethod("GET");
        corsconfig.addAllowedMethod("POST");
        corsconfig.addAllowedMethod("PUT");
        corsconfig.addAllowedMethod("DELETE");
        
        //cabecerass
        corsconfig.addAllowedHeader("*");
        
        source.registerCorsConfiguration("/**", corsconfig);
        
        return new CorsFilter(source);
    }
    
}
