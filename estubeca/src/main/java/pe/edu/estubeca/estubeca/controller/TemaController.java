package pe.edu.estubeca.estubeca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.estubeca.estubeca.entities.Curso;
import pe.edu.estubeca.estubeca.entities.Tema;
import pe.edu.estubeca.estubeca.exception.ResourceNotFoundException;
import pe.edu.estubeca.estubeca.repository.TemaRepository;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class TemaController {
    @Autowired
    private TemaRepository temaRepository;

    @GetMapping("/temas")
    public ResponseEntity<List<Tema>> getAllTemas(){
        List<Tema> temas=temaRepository.findAll();
        return new ResponseEntity<List<Tema>>(temas, HttpStatus.OK);
    }

    @GetMapping("/temas/{id}")
    public ResponseEntity<Tema> getTemaById(@PathVariable("id") Long id){
        Tema tema=temaRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Not found tema with id=" + id));
        return new ResponseEntity<Tema>(tema, HttpStatus.OK);
    }
    @GetMapping("/cursos/{id}/temas")
    public ResponseEntity<List<Tema>> getAllTemasPostId(@PathVariable("id") Long id){
        List<Tema> temas= temaRepository.findAllTemasCursoId(id);
        return new ResponseEntity<List<Tema>>(temas,HttpStatus.OK);
    }
    @PostMapping("/temas")
    public ResponseEntity<Tema> createTema(@RequestBody Tema tema){
        Tema newTema = temaRepository.save(
                new Tema(tema.getPosition(),
                        tema.getTitle(),
                        tema.getDescription(),
                        tema.getBody(),
                        tema.getVideo(),
                        tema.getCurso())
        );
        return new ResponseEntity<Tema>(newTema, HttpStatus.CREATED);
    }

    @PutMapping("/temas/{id}")
    public ResponseEntity<Tema> updateTema(
            @PathVariable("id") Long id,
            @RequestBody Tema tema){
        Tema temaUpdate= temaRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Not found tema with id="+id));
        temaUpdate.setPosition(tema.getPosition());
        temaUpdate.setTitle(tema.getTitle());
        temaUpdate.setDescription(tema.getDescription());
        temaUpdate.setBody(tema.getBody());
        temaUpdate.setVideo(tema.getVideo());
        temaUpdate.setCurso(tema.getCurso());
        return new ResponseEntity<Tema>(temaRepository.save(temaUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/temas/{id}")
    public ResponseEntity<HttpStatus> deleteTema(@PathVariable("id") Long id){
        temaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
