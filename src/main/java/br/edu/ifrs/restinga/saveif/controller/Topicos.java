/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.saveif.controller;

import br.edu.ifrs.restinga.saveif.dao.TopicoDAO;
import br.edu.ifrs.restinga.saveif.modelo.Topico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class Topicos {
    @Autowired
    TopicoDAO topicoDAO;
    
    @RequestMapping(path = "/grupo/{id}/topicos", method = RequestMethod.GET)
    public Iterable<Topico> listar() {
        return topicoDAO.findAll();
    } 
    
    @PreAuthorize("hasAuthority('administrador')")
    @RequestMapping(path = "/grupo/{id}/topicos", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Topico inserir(@RequestBody Topico topico) throws Exception {
        topico.setId(0);
        
        Topico topicoSalvo = topicoDAO.save(topico);
        
        return topicoSalvo;
    }
}
