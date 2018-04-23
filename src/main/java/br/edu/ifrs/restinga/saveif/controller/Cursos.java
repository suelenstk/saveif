
package br.edu.ifrs.restinga.saveif.controller;


import br.edu.ifrs.restinga.saveif.dao.CursoDAO;
import br.edu.ifrs.restinga.saveif.modelo.Curso;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class Cursos {
    @Autowired
    CursoDAO cursoDAO;

    @RequestMapping(path = "/cursos", method = RequestMethod.GET)
    public Iterable<Curso> listar() {
        return cursoDAO.findAll();
    }
    
    @RequestMapping(path = "/cursos/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Curso> recuperar(@PathVariable int id) {

        Optional<Curso> findById = cursoDAO.findById(id);
        if (findById.isPresent())
            return ResponseEntity.ok(findById.get());
        else
            return ResponseEntity.notFound().build();

    }
    
    @PreAuthorize("hasAuthority('administrador')")
    @RequestMapping(path = "/cursos", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Curso inserir(@RequestBody Curso curso) throws Exception {
        curso.setId(0);
        
        Curso cursoSalvo = cursoDAO.save(curso);
        return cursoSalvo;
    }

    @PreAuthorize("hasAuthority('administrador')")
    @RequestMapping(path = "/cursos/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Curso curso) throws Exception {
        if (cursoDAO.existsById(id)) {
            curso.setId(id);
            cursoDAO.save(curso);
        }
    }

    @PreAuthorize("hasAuthority('administrador')")
    @RequestMapping(path = "/cursos/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (cursoDAO.existsById(id)) {
            cursoDAO.deleteById(id);
        }

    }
    
    
    
    
}
