package com.example.ProgromacionNCapasXimena.RestController;

import com.example.ProgromacionNCapasXimena.DAO.ColoniaDAOImplementation;
import com.example.ProgromacionNCapasXimena.DAO.DireccionDAOImplementation;
import com.example.ProgromacionNCapasXimena.DAO.EstadoDAOImplementation;
import com.example.ProgromacionNCapasXimena.DAO.MunicipioDAOImplementation;
import com.example.ProgromacionNCapasXimena.DAO.PaisDAOImplementation;
import com.example.ProgromacionNCapasXimena.DAO.UsuarioDAOImplementation;
import com.example.ProgromacionNCapasXimena.JPA.Colonia;
import com.example.ProgromacionNCapasXimena.JPA.Direccion;
import com.example.ProgromacionNCapasXimena.JPA.Result;
import com.example.ProgromacionNCapasXimena.JPA.ResultFile;
import com.example.ProgromacionNCapasXimena.JPA.Roll;
import com.example.ProgromacionNCapasXimena.JPA.Usuario;
import com.example.ProgromacionNCapasXimena.JPA.UsuarioDireccion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/usuarioapi")
public class RestControllerUsuario {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;

    //    ----------
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

    //agregar usuario direccion
    @PostMapping("/AddJPA")
    public ResponseEntity AddJPA(@RequestBody UsuarioDireccion usuarioDireccion) {
        Result result = usuarioDAOImplementation.AddJPA(usuarioDireccion);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    //agregardireccion  ||revisar
    @PostMapping("DireccionAddJPA")
    public ResponseEntity DireccionAddJPA(@RequestBody UsuarioDireccion usuarioDireccion) {
        Result result = direccionDAOImplementation.DireccionAddJPA(usuarioDireccion);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    //eliminar usuario / direccion con IdUsuario
    @DeleteMapping("/DeleteJPA")
    public ResponseEntity DeleteJPA(@RequestParam int IdUsuario) {
        Result result = usuarioDAOImplementation.UsuarioDeleteJPA(IdUsuario);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    //Eliminar solo una direccion
    @DeleteMapping("/DeleteDireccionJPA")
    public ResponseEntity DeleteDireccionJPA(@RequestParam int IdDireccion) {
        Result result = direccionDAOImplementation.DireccionDeleteJPA(IdDireccion);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //Editar usuario
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

    //revisar || actualizar direccion
    @PutMapping("/UpdateDireccionJPA")
    public ResponseEntity UpdateDireccionJPA(@RequestBody Direccion direccion) {
        Result result = direccionDAOImplementation.UpdateDireccionJPA(direccion);
        if (result.correct) {
            UsuarioDireccion usuarioDireccion = (UsuarioDireccion) result.object;
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();

        }
    }

    @GetMapping("/GetByIdJPA")
    public ResponseEntity GetByIdJPA(@RequestParam int IdUsuario) {
        Result result = usuarioDAOImplementation.GetByIdJPA(IdUsuario);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/GetDireccionById")
    public ResponseEntity DireccionByIdJPA(@RequestParam int IdUsuario) {
        Result result = usuarioDAOImplementation.DireccionByIdJPA(IdUsuario);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/GetByIdDireccionJPA")
    public ResponseEntity GetByIdDireccionJPA(@RequestParam int IdDireccion) {
        Result result = direccionDAOImplementation.GetByIdDireccionJPA(IdDireccion);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/CargaMasiva")
    public ResponseEntity CargaMasiva(@RequestParam("archivo") MultipartFile archivo) {

        Result result = new Result();
        
        if (archivo != null && !archivo.isEmpty()) { //Sirve para que el archivo no este nulo ni esté vacio

            try {

                String tipoArchivo = archivo.getOriginalFilename().split("\\.")[1];

                String root = System.getProperty("user.dir"); //Obtiene la direccion del proyecto de la computadora
                String path = "src/main/resources/static/archivos"; //Guarda internamente la direccion del proyecto
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
                String absolutePath = root + "/" + path + "/" + fecha + archivo.getOriginalFilename();
                //se guarda en AbsolutePath para que no se vaya a perder
                archivo.transferTo(new File(absolutePath));

                //Leer archivo 
                List<UsuarioDireccion> listaUsuarios = new ArrayList();
                if (tipoArchivo.equals("txt")) {
                    listaUsuarios = LecturaArchivoTXT(new File(absolutePath)); //Metodo que sirve para leer la lista                    
                } else {
                    listaUsuarios = LecturaArchivoExcel(new File(absolutePath));
                }

                //Validar Archivo 
                List<ResultFile> listaErrores = new ArrayList<>(); //validarArchivo(listaUsuarios)

                if (listaErrores.isEmpty()) {
                    //Procesar archivo
                    result.correct = true; 
                    result.object = absolutePath;
                    return ResponseEntity.ok(result); 
                } else {
                    //Se muestra tabla de errores 
                    result.correct = false; 
                    result.objects = new ArrayList(); 
                    
                    for(ResultFile error : listaErrores) {
                        result.objects.add(error);
                    }
                    return ResponseEntity.status(400).body(result);
                }
            } catch (Exception ex) { 
                return ResponseEntity.status(500).body("Mal");
            }

        } else {
            result.correct = false; 
            return ResponseEntity.status(400).body(result); 
        }
    }
    
    @PostMapping("/CargaMasiva/Procesar")
    public ResponseEntity Procesar(@RequestBody String absolutePath) {
        Result result = new Result(); 
        try{
            String tipoArchivo = absolutePath.split("\\.")[2];
            
            List<UsuarioDireccion> listaUsuarios = new ArrayList(); 
            if (tipoArchivo.equals("txt")){
                listaUsuarios = LecturaArchivoTXT(new File(absolutePath));
            } else {
                listaUsuarios = LecturaArchivoExcel(new File(absolutePath));
            }
            
            for(UsuarioDireccion usuario : listaUsuarios) {
                usuarioDAOImplementation.AddJPA(usuario);
            }
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false; 
        }
        
        return ResponseEntity.ok(result);
    }

    public List<UsuarioDireccion> LecturaArchivoTXT(File archivo) {
        List<UsuarioDireccion> listaUsuarios = new ArrayList<>();
        try (FileReader fileReader = new FileReader(archivo); BufferedReader bufferedReader = new BufferedReader(fileReader);) {

            String linea;

            while ((linea = bufferedReader.readLine()) != null) {
                String[] campos = linea.split("\\|");

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = new Usuario();
                usuarioDireccion.Usuario.setNombre(campos[0]);
                usuarioDireccion.Usuario.setApellidoPaterno(campos[1]);
                usuarioDireccion.Usuario.setApellidoMaterno(campos[2]);
                //Dar formato a la fecha
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
                usuarioDireccion.Usuario.setFechaNacimiento(formatter.parse(campos[3]));
                usuarioDireccion.Usuario.setUserName(campos[4]);
                usuarioDireccion.Usuario.setEmail(campos[5]);
                usuarioDireccion.Usuario.setPassword(campos[6]);
                usuarioDireccion.Usuario.setSexo(campos[7]);
                usuarioDireccion.Usuario.setTelefono(campos[8]);
                usuarioDireccion.Usuario.setCelular(campos[9]);
                usuarioDireccion.Usuario.setCURP(campos[10]);
                usuarioDireccion.Usuario.setStatus(Integer.parseInt(campos[11]));

                usuarioDireccion.Usuario.Roll = new Roll();
                usuarioDireccion.Usuario.Roll.setIdRoll(Integer.parseInt(campos[12]));
                usuarioDireccion.Usuario.Roll.setNombre(campos[13]);

                usuarioDireccion.Direccion = new Direccion();
                usuarioDireccion.Direccion.setCalle(campos[14]);
                usuarioDireccion.Direccion.setNumeroInterior(campos[15]);
                usuarioDireccion.Direccion.setNumeroExterior(campos[16]);

                usuarioDireccion.Direccion.Colonia = new Colonia();
                usuarioDireccion.Direccion.Colonia.setIdColonia(Integer.parseInt(campos[17]));
                listaUsuarios.add(usuarioDireccion);
            }

        } catch (Exception Ex) {
            listaUsuarios = null;
        }
        return listaUsuarios;
    }

    public List<UsuarioDireccion> LecturaArchivoExcel(File archivo) {
        List<UsuarioDireccion> listaUsuarios = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setNombre(row.getCell(0).toString());
                    usuarioDireccion.Usuario.setApellidoPaterno(row.getCell(1).toString());
                    usuarioDireccion.Usuario.setApellidoMaterno(row.getCell(2).toString());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    usuarioDireccion.Usuario.setFechaNacimiento(simpleDateFormat.parse(simpleDateFormat.format(row.getCell(3).getDateCellValue())));
                    usuarioDireccion.Usuario.setUserName(row.getCell(4).toString());
                    usuarioDireccion.Usuario.setEmail(row.getCell(5).toString());
                    usuarioDireccion.Usuario.setPassword(row.getCell(6).toString());
                    usuarioDireccion.Usuario.setSexo(row.getCell(7).toString());
                    usuarioDireccion.Usuario.setTelefono(row.getCell(8).toString());
                    usuarioDireccion.Usuario.setCelular(row.getCell(9).toString());
                    usuarioDireccion.Usuario.setCURP(row.getCell(10).toString());
                    usuarioDireccion.Usuario.setStatus(row.getCell(11) != null ? (int) row.getCell(11).getNumericCellValue() : 0);
                    usuarioDireccion.Usuario.Roll = new Roll();
                    usuarioDireccion.Usuario.Roll.setIdRoll((int) row.getCell(12).getNumericCellValue());

                    usuarioDireccion.Direccion = new Direccion();
                    usuarioDireccion.Direccion.setCalle(row.getCell(13).toString());
                    usuarioDireccion.Direccion.setNumeroInterior(row.getCell(14).toString());
                    usuarioDireccion.Direccion.setNumeroExterior(row.getCell(15).toString());

                    usuarioDireccion.Direccion.Colonia = new Colonia();
                    usuarioDireccion.Direccion.Colonia.setIdColonia((int) row.getCell(16).getNumericCellValue());

                    listaUsuarios.add(usuarioDireccion);
                }
            }
        } catch (Exception Ex) {
            System.out.println("Error al abrir archivo");
        }

        return listaUsuarios;
    }

    public List<ResultFile> ValidarArchivo(List<UsuarioDireccion> listaUsuarios) {

        List<ResultFile> listaErrores = new ArrayList<>();

        if (listaUsuarios == null) {
            listaErrores.add(new ResultFile(0, "La lista es nula", "La lista es nula"));
        } else if (listaUsuarios.isEmpty()) {
            listaErrores.add(new ResultFile(0, "La lista esta vacia", "La lista esta vacia"));
        } else {
            int fila = 1;
            for (UsuarioDireccion usuarioDireccion : listaUsuarios) {
                if (usuarioDireccion.Usuario.getNombre() == null || usuarioDireccion.Usuario.getNombre().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getNombre(), "El campo nombre es obligratorio"));
                }
                if (usuarioDireccion.Usuario.getApellidoPaterno() == null || usuarioDireccion.Usuario.getApellidoPaterno().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getApellidoMaterno(), "El campo apellido paterno es obligatorio"));
                }
//                if (usuarioDireccion.Usuario.getFechaNacimiento() == null || usuarioDireccion.Usuario.getFechaNacimiento().equals("")){
//                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getFechaNacimiento(), "El campo de fecha de nacimiento es obligatorio"));
//                }
                if (usuarioDireccion.Usuario.getUserName() == null || usuarioDireccion.Usuario.getUserName().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getUserName(), "El campo de username es obligatorio"));
                }
                if (usuarioDireccion.Usuario.getEmail() == null || usuarioDireccion.Usuario.getEmail().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getEmail(), "El campo de email es obligatorio"));
                }
                if (usuarioDireccion.Usuario.getPassword() == null || usuarioDireccion.Usuario.getPassword().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getPassword(), "El campo de password es obligatorio"));
                }
                if (usuarioDireccion.Usuario.getSexo() == null || usuarioDireccion.Usuario.getSexo().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getSexo(), "El campo sexo es obligatorio"));
                }
                if (usuarioDireccion.Usuario.getTelefono() == null || usuarioDireccion.Usuario.getTelefono().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getTelefono(), "El campo telefono es obligatorio"));
                }
                if (usuarioDireccion.Usuario.getCelular() == null || usuarioDireccion.Usuario.getCelular().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getCelular(), "El campo celular es obligatorio"));
                }
                fila++;
            }
        }
        return listaErrores;
    }

}
