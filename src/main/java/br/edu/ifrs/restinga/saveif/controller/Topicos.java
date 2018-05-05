/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.saveif.controller;

import br.edu.ifrs.restinga.saveif.aut.UsuarioAut;
import br.edu.ifrs.restinga.saveif.dao.GrupoDAO;
import br.edu.ifrs.restinga.saveif.dao.TopicoDAO;
import br.edu.ifrs.restinga.saveif.modelo.Grupo;
import br.edu.ifrs.restinga.saveif.modelo.Topico;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    
    @Autowired
    GrupoDAO grupoDAO;
    
    @RequestMapping(path = "/topicos", method = RequestMethod.GET)
    public Iterable<Topico> listar() {
        return topicoDAO.findAll();
    } 
    
    @RequestMapping(path="/topicos", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Topico inserir(@RequestBody Topico topico, @AuthenticationPrincipal UsuarioAut usuarioAut) throws Exception
    {
        if (topico.getNome().equalsIgnoreCase("Geral"))
            throw new Exception("Nome de tópico inválido");  
        topico.setId(0);
        topico.setCriadorTopico(usuarioAut.getUsuario());
             
        Topico topicoSalvo = topicoDAO.save(topico);
        
        Grupo grupoAtual = grupoDAO.findById(1);
        
          
                List<Topico> topicos = new ArrayList<>();
                topicos = grupoAtual.getTopicos();
                topicos.add (topico);
                grupoAtual.setTopicos(topicos);
      
        
        return topicoSalvo;
    }
}
