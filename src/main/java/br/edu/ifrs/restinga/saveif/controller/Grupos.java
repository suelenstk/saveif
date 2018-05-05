package br.edu.ifrs.restinga.saveif.controller;

import br.edu.ifrs.restinga.saveif.aut.UsuarioAut;
import br.edu.ifrs.restinga.saveif.dao.GrupoDAO;
import br.edu.ifrs.restinga.saveif.dao.TopicoDAO;
import br.edu.ifrs.restinga.saveif.modelo.Grupo;
import br.edu.ifrs.restinga.saveif.modelo.Topico;
import br.edu.ifrs.restinga.saveif.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api")
public class Grupos {

    @Autowired
    GrupoDAO grupoDAO;
    
    @Autowired
    TopicoDAO topicoDAO;

    @RequestMapping(path = "/grupos", method = RequestMethod.GET)
    public Iterable<Grupo> listar(@RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        return grupoDAO.findAll(pageRequest);
    }
    
    @RequestMapping(path = "/grupos/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Grupo grupo) {
        if (grupoDAO.existsById(id)) {
            grupo.setId(id);
            grupoDAO.save(grupo);
        }
    }

    @RequestMapping(path = "/grupos/solicitar/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void solicitarInscricao(@PathVariable int id, @RequestBody Grupo grupo) {
        
        List<Usuario> solicitacoes;
        List<Usuario> integrantes;
        
        Grupo busca = grupoDAO.findById(grupo.getId());
        
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
        
        grupoDAO.save(grupo);
    }

    @RequestMapping(path = "/grupos/integrantes/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Grupo> pesquisaPorIntegrantes(@RequestParam(required = false, defaultValue = "0") int pagina,
            @PathVariable int id) throws Exception {

        PageRequest pageRequest = new PageRequest(pagina, 5);

        Usuario igual = new Usuario();
        igual.setId(id);

        return grupoDAO.findByIntegrantesGrupo(igual, pageRequest);
    }

    @RequestMapping(path="/grupos", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Grupo inserir(@RequestBody Grupo grupo, @AuthenticationPrincipal UsuarioAut usuarioAut)
    {
        grupo.setId(0);
        grupo.setDonoGrupo(usuarioAut.getUsuario());
              
        Topico geral = new Topico(0, "Geral", usuarioAut.getUsuario());
        topicoDAO.save(geral);
        
        ArrayList<Topico> topicos = new ArrayList<>();
        topicos.add(geral);
        grupo.setTopicos(topicos);
        
        Grupo grupoSalvo = grupoDAO.save(grupo);
        return grupoSalvo;
    }

}