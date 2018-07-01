package br.edu.ifrs.restinga.saveif.controller;

import br.edu.ifrs.restinga.saveif.dao.GrupoDAO;
import br.edu.ifrs.restinga.saveif.dao.NotificacaoDAO;
import br.edu.ifrs.restinga.saveif.modelo.Notificacao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class Notificacoes {

    @Autowired
    NotificacaoDAO notificacaoDAO;

    @Autowired
    GrupoDAO grupoDAO;

    @RequestMapping(path = "/grupos/{id}/notificacoes", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Notificacao> pesquisaNotificacoesGrupo(@RequestParam(required = false, defaultValue = "0") int pagina, @PathVariable int id) throws Exception {
        PageRequest pageRequest = new PageRequest(pagina, 20);

        return notificacaoDAO.findNotificacaoGrupo(id, pageRequest);
    }
    
    
    @RequestMapping(path = "/usuarios/{id}/notificacoes", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Notificacao> pesquisaNotificacoesUsuario(@RequestParam(required = false, defaultValue = "0") int pagina, @PathVariable int id) throws Exception {
        PageRequest pageRequest = new PageRequest(pagina, 20);

        List lstNotificacoes = notificacaoDAO.findNotificacoes(id);
        int start = (int) pageRequest.getOffset();
        int end = (int)((start + pageRequest.getPageSize()) > lstNotificacoes.size() ? lstNotificacoes.size() : (start + pageRequest.getPageSize()));
        
        return new PageImpl<>(lstNotificacoes.subList(start, end), pageRequest, lstNotificacoes.size());

        
    }

    @RequestMapping(path = "/usuarios/{id}/numeronotificacoes", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public int numeroNotificacoesUsuario(@PathVariable int id) throws Exception {
        return  notificacaoDAO.countNotificacaoUsuario(id) + notificacaoDAO.countNotificacaoGrupos(id) ;
    }

    @RequestMapping(path = "/notificacoes/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (notificacaoDAO.existsById(id)) {
            notificacaoDAO.deleteById(id);
        }
    }

}