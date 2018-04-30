/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.saveif.controller;

import br.edu.ifrs.restinga.saveif.dao.GrupoDAO;
import br.edu.ifrs.restinga.saveif.modelo.Grupo;
import br.edu.ifrs.restinga.saveif.modelo.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author gustavo e eduarda
 */
@RestController
@RequestMapping(path = "/api")
public class Grupos {

    @Autowired
    GrupoDAO GrupoDAO;

    @RequestMapping(path = "/grupos", method = RequestMethod.GET)
    public Iterable<Grupo> listar(@RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        return GrupoDAO.findAll(pageRequest);
    }

    @RequestMapping(path = "/grupos/solicitar/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void solicitarInscricao(@PathVariable int id, @RequestBody Grupo grupo) {
        
        List<Usuario> solicitacoes;
        List<Usuario> integrantes;
        
        Grupo busca = GrupoDAO.findById(grupo.getId());
        
        if (busca.getSolicitantesGrupo().isEmpty()) {
            
            grupo.setIntegrantesGrupo (grupo.getSolicitantesGrupo());
            
        } else {
            
            solicitacoes = busca.getSolicitantesGrupo();
            
            integrantes = busca.getIntegrantesGrupo();
            
            solicitacoes.add(grupo.getSolicitantesGrupo().get(0));
            integrantes.add(grupo.getSolicitantesGrupo().get(0));
            
            grupo.setIntegrantesGrupo(integrantes);
            grupo.setSolicitantesGrupo(solicitacoes);
        }
        
        GrupoDAO.save(grupo);
    }

    @RequestMapping(path = "/grupos/integrantes/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Grupo> pesquisaPorIntegrantes(@RequestParam(required = false, defaultValue = "0") int pagina,
            @PathVariable int id) throws Exception {

        PageRequest pageRequest = new PageRequest(pagina, 5);

        Usuario igual = new Usuario();
        igual.setId(id);

        return GrupoDAO.findByIntegrantesGrupo(igual, pageRequest);
    }

    @PreAuthorize("hasAuthority('administrador')")
    @RequestMapping(path = "/grupos", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Grupo criar(@RequestBody Grupo grupo) {
        grupo.setId(0);
        Grupo grupoSalvo = GrupoDAO.save(grupo);
        return grupoSalvo;
    }

}
