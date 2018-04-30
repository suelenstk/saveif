/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.saveif.controller;

import br.edu.ifrs.restinga.saveif.dao.CategoriaDAO;
import br.edu.ifrs.restinga.saveif.modelo.Categoria;
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

/**
 *
 * @author Tiago
 */
@RestController
@RequestMapping(path = "/api")
public class Categorias {
    @Autowired
    CategoriaDAO categoriaDAO;

    @RequestMapping(path = "/categorias", method = RequestMethod.GET)
    public Iterable<Categoria> listar() {
        return categoriaDAO.findAll();
    }
    
    @RequestMapping(path = "/categorias/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Categoria> recuperar(@PathVariable int id) {

        Optional<Categoria> findById = categoriaDAO.findById(id);
        if (findById.isPresent())
            return ResponseEntity.ok(findById.get());
        else
            return ResponseEntity.notFound().build();
    }    
}
