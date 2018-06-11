package br.edu.ifrs.restinga.saveif.controller;

import br.edu.ifrs.restinga.saveif.dao.GrupoDAO;
import br.edu.ifrs.restinga.saveif.dao.NotificacaoDAO;
import br.edu.ifrs.restinga.saveif.modelo.Notificacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

        return notificacaoDAO.findNotificacaoUsuario(id, pageRequest);

    }

    @RequestMapping(path = "/notificacoes/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (notificacaoDAO.existsById(id)) {
            notificacaoDAO.deleteById(id);
        }
    }

}
