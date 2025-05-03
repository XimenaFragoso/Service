package com.example.ProgromacionNCapasXimena.RestController;

import com.example.ProgromacionNCapasXimena.DAO.DireccionDAOImplementation;
import com.example.ProgromacionNCapasXimena.DAO.UsuarioDAOImplementation;
import com.example.ProgromacionNCapasXimena.JPA.Result;
import com.example.ProgromacionNCapasXimena.JPA.Usuario;
import com.example.ProgromacionNCapasXimena.JPA.UsuarioDireccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarioapi")
public class RestControllerUsuario {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;

    @GetMapping()
    public ResponseEntity GetAllJPA() {
        Result result = usuarioDAOImplementation.GetAllJPA();

        if (result.correct) {
            if (result.objects.isEmpty()) {
                return ResponseEntity.status(204).body(null);
            } else {
                return ResponseEntity.ok(result);
            }
        } else {
            return ResponseEntity.status(404).body(null);
        }

    }

    @PostMapping("/AddJPA")
    public ResponseEntity AddJPA(@RequestBody UsuarioDireccion usuarioDireccion) {
        Result result = usuarioDAOImplementation.AddJPA(usuarioDireccion);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/DeleteJPA")
    public ResponseEntity DeleteJPA(@RequestParam int IdUsuario) {
        Result result = usuarioDAOImplementation.UsuarioDeleteJPA(IdUsuario);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/DeleteDireccionJPA")
    public ResponseEntity DeleteDireccionJPA(@RequestParam int IdDireccion) {
        Result result = direccionDAOImplementation.DireccionDeletJPA(IdDireccion);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/UpdateUsuarioJPA")
//        public ResponseEntity<Result> UpdateUsuarioJPA(@RequestBody Usuario usuario){
    public ResponseEntity UpdateUsuarioJPA(@RequestBody Usuario usuario) {
        Result result = usuarioDAOImplementation.UpdateUsuarioJPA(usuario);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
