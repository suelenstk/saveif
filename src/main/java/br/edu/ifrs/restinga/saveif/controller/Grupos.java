package br.edu.ifrs.restinga.saveif.controller;

import br.edu.ifrs.restinga.saveif.aut.ForbiddenException;
import br.edu.ifrs.restinga.saveif.aut.UsuarioAut;
import br.edu.ifrs.restinga.saveif.dao.CategoriaDAO;
import br.edu.ifrs.restinga.saveif.dao.GrupoDAO;
import br.edu.ifrs.restinga.saveif.dao.NotificacaoDAO;
import br.edu.ifrs.restinga.saveif.dao.TopicoDAO;
import br.edu.ifrs.restinga.saveif.dao.UsuarioDAO;
import br.edu.ifrs.restinga.saveif.modelo.Categoria;
import br.edu.ifrs.restinga.saveif.modelo.Grupo;
import br.edu.ifrs.restinga.saveif.modelo.Notificacao;
import br.edu.ifrs.restinga.saveif.modelo.Topico;
import br.edu.ifrs.restinga.saveif.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(path = "/api")
public class Grupos {

    @Autowired
    GrupoDAO grupoDAO;

    @Autowired
    TopicoDAO topicoDAO;

    @Autowired
    CategoriaDAO categoriaDAO;

    @Autowired
    UsuarioDAO usuarioDAO;

    @Autowired
    NotificacaoDAO notificacaoDAO;

    @RequestMapping(path = "/grupos", method = RequestMethod.GET)
    public Iterable<Grupo> listar(@RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 10);
        return grupoDAO.findByOrderByIdDesc(pageRequest);
    }

    @RequestMapping(path = "/grupos/{id}/{idCategoria}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Grupo grupo, @PathVariable int idCategoria) {
        if (grupoDAO.existsById(id)) {

            Grupo grupoAtualizado = grupoDAO.findById(id);
            grupoAtualizado.setId(id);
            grupoAtualizado.setNome(grupo.getNome());
            grupoAtualizado.setDescricao(grupo.getDescricao());
            grupoAtualizado.setTipoPrivacidade(grupo.getTipoPrivacidade());

            Categoria categoria = categoriaDAO.findById(idCategoria);

            grupoAtualizado.setCategoria(categoria);
            grupoDAO.save(grupoAtualizado);
        }
    }

    @RequestMapping(path = "/grupos/{id}", method = RequestMethod.GET)
    public Grupo listarGrupoEspecifico(@PathVariable int id) {
        return grupoDAO.findById(id);
    }

    @RequestMapping(path = "/grupos/{idGrupo}/convite/{idUsuario}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void convidarParticipante(@AuthenticationPrincipal UsuarioAut usuarioAut, @PathVariable int idGrupo, @RequestBody List<Integer> idsUsuarios) {

        Usuario logado = usuarioDAO.findByEmail(usuarioAut.getUsername());

        Grupo grupo = grupoDAO.findById(idGrupo);

        if (grupo.getCoordenadoresGrupo().contains(logado)
                || usuarioAut.getUsuario().getPermissoes().contains("administrador")) {

            for (int i = 0; i >= idsUsuarios.size(); i++) {

                Optional<Usuario> findById = usuarioDAO.findById(idsUsuarios.get(i));
                Usuario convidado = findById.get();

                if (grupoDAO.existsById(idGrupo) && findById.isPresent()) {
                    List<Usuario> convites = grupo.getConvitesGrupo();
                    convites.add(convidado);
                    grupo.setConvitesGrupo(convites);

                    grupoDAO.save(grupo);

                    Notificacao notificacao = new Notificacao("Você foi convidado a participar do grupo ", "", "",
                            Integer.toString(grupo.getId()), grupo.getNome(), "convite");

                    notificacao = notificacaoDAO.save(notificacao);

                    List<Notificacao> notificacoesUsuario = convidado.getNotificacoes();
                    notificacoesUsuario.add(notificacao);
                    convidado.setNotificacoes(notificacoesUsuario);

                    usuarioDAO.save(convidado);
                } else throw new ForbiddenException("Usuário ou grupo não encontrado.");
            }
        } else
            throw new ForbiddenException("Além dos administradores do sistema somente coordenadores poderão convidar participantes para o grupo.");

    }

    @RequestMapping(path = "/grupos/{id}/solicitar/{idUsuario}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void solicitarInscricao(@AuthenticationPrincipal UsuarioAut usuarioAut, @PathVariable int id, @PathVariable int idUsuario) throws Exception {

        if (usuarioAut == null ||                   //TESTE
                usuarioAut.getUsuario().getPermissoes().contains("administrador")
                || usuarioAut.getUsuario().getId() == idUsuario) {

            Optional<Usuario> findById = usuarioDAO.findById(idUsuario);

            if (grupoDAO.existsById(id) && findById.isPresent()) {

                Grupo grupo = grupoDAO.findById(id);

                Usuario solicitante = findById.get();

                if (grupo.getTipoPrivacidade().equalsIgnoreCase("aberto")) {
                    List<Usuario> integrantes = grupo.getIntegrantesGrupo();
                    integrantes.add(solicitante);
                    grupo.setIntegrantesGrupo(integrantes);
                    grupoDAO.save(grupo);

                } else if (grupo.getTipoPrivacidade().equalsIgnoreCase("publico") || grupo.getTipoPrivacidade().equalsIgnoreCase("público")) {
                    List<Usuario> solicitacoes = grupo.getSolicitantesGrupo();
                    solicitacoes.add(solicitante);
                    grupo.setSolicitantesGrupo(solicitacoes);

                    Notificacao notificacao = new Notificacao(" solicitou participação no grupo ",
                            Integer.toString(solicitante.getId()), solicitante.getNome(), Integer.toString(grupo.getId()), grupo.getNome(), "solicitacao");

                    notificacao = notificacaoDAO.save(notificacao);


                    List<Notificacao> notificacoes = grupo.getNotificacoes();
                    notificacoes.add(notificacao);
                    grupoDAO.save(grupo);
                    

                } else
                    throw new Exception("Grupos privados não aceitam inscrição.");
                
            } else
                throw new ForbiddenException("Usuário ou grupo não encontrado.");
        } else
            throw new ForbiddenException("Além dos administradores do sistema somente o próprio usuário poderá solicitar inscrição em grupos.");
    }


    @RequestMapping(path = "/grupos/{idGrupo}/inscricao/{idUsuario}/aceite/{idNotificacao}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void aceitarInscricao(@AuthenticationPrincipal UsuarioAut usuarioAut, @PathVariable int idGrupo, @PathVariable int idUsuario, @PathVariable(required = false) Integer idNotificacao) {

        if (idNotificacao != null && notificacaoDAO.existsById(idNotificacao))
            notificacaoDAO.deleteById(idNotificacao);

        Optional<Usuario> usuarioFind = usuarioDAO.findById(idUsuario);
        Usuario logado = usuarioDAO.findByEmail(usuarioAut.getUsername());

        if (grupoDAO.existsById(idGrupo) && usuarioFind.isPresent()) {
            Grupo grupo = grupoDAO.findById(idGrupo);

            if (grupo.getCoordenadoresGrupo().contains(logado)
                    || usuarioAut.getUsuario().getPermissoes().contains("administrador")) {

                Usuario usuario = usuarioFind.get();


                List<Usuario> solicitacoes = grupo.getSolicitantesGrupo();

                if (solicitacoes.contains(usuario)) {

                    List<Usuario> integrantes = grupo.getIntegrantesGrupo();
                    integrantes.add(usuario);
                    grupo.setIntegrantesGrupo(integrantes);

                    solicitacoes.remove(usuario);
                    grupo.setSolicitantesGrupo(solicitacoes);

                    grupoDAO.save(grupo);


                } else
                    throw new ForbiddenException("Grupo não recebeu solicitação de usuário.");
            } else
                throw new ForbiddenException("Além dos administradores do sistema somente coordenadores poderão aceitar participantes para o grupo.");
        } else
            throw new ForbiddenException("Usuário ou grupo não encontrado.");

    }


    @RequestMapping(path = "/grupos/{idGrupo}/inscricao/{idUsuario}/negacao/{idNotificacao}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void negarInscricao(@AuthenticationPrincipal UsuarioAut usuarioAut, @PathVariable int idGrupo, @PathVariable int idUsuario, @PathVariable(required = false) Integer idNotificacao) {

        if (idNotificacao != null && notificacaoDAO.existsById(idNotificacao))
            notificacaoDAO.deleteById(idNotificacao);

        Optional<Usuario> usuarioFind = usuarioDAO.findById(idUsuario);
        Usuario logado = usuarioDAO.findByEmail(usuarioAut.getUsername());

        if (grupoDAO.existsById(idGrupo) && usuarioFind.isPresent()) {
            Grupo grupo = grupoDAO.findById(idGrupo);

            if (grupo.getCoordenadoresGrupo().contains(logado)
                    || usuarioAut.getUsuario().getPermissoes().contains("administrador")) {

                Usuario usuario = usuarioFind.get();

                List<Usuario> solicitacoes = grupo.getSolicitantesGrupo();

                if (solicitacoes.contains(usuario)) {

                    solicitacoes.remove(usuario);
                    grupo.setSolicitantesGrupo(solicitacoes);

                    grupoDAO.save(grupo);


                } else
                    throw new ForbiddenException("Grupo não recebeu solicitação de usuário.");
            } else
                throw new ForbiddenException("Além dos administradores do sistema somente coordenadores poderão negar participantes para o grupo.");


        } else
            throw new ForbiddenException("Usuário ou grupo não encontrado.");

    }


    @RequestMapping(path = "/grupos/integrantes/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Grupo> pesquisaPorIntegrantes(@RequestParam(required = false, defaultValue = "0") int pagina,
                                                  @PathVariable int id) throws Exception {
        PageRequest pageRequest = new PageRequest(pagina, 10);

        Usuario igual = new Usuario();
        igual.setId(id);

        return grupoDAO.findByIntegrantesGrupo(igual, pageRequest);
    }

    @RequestMapping(path = "/grupos/{idCategoria}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Grupo inserir(@RequestBody Grupo grupo, @AuthenticationPrincipal UsuarioAut usuarioAut, @PathVariable int idCategoria) {
        grupo.setId(0);
        grupo.setDonoGrupo(usuarioAut.getUsuario());

        Categoria categoria = categoriaDAO.findById(idCategoria);

        grupo.setCategoria(categoria);

        Topico geral = new Topico(0, "Geral", usuarioAut.getUsuario());
        topicoDAO.save(geral);

        ArrayList<Topico> topicos = new ArrayList<>();
        topicos.add(geral);
        grupo.setTopicos(topicos);

        Grupo grupoSalvo = grupoDAO.save(grupo);
        return grupoSalvo;
    }

    @RequestMapping(path = "/grupos/{id}/imagem", method = RequestMethod.POST)
    public ResponseEntity<InputStreamResource> inserirImagem(@PathVariable int id,
                                                             @RequestParam("arquivo") MultipartFile uploadfiles) throws Exception {
        Grupo grupo = grupoDAO.findById(id);

        try {
            grupo.setTipoImagem(uploadfiles.getContentType());
            grupo.setImagem(uploadfiles.getBytes());
            grupoDAO.save(grupo);
            return recuperarImagem(id);

        } catch (IOException ex) {

            ex.printStackTrace();
            throw new Exception("Erro ao salvar arquivo de imagem");
        }
    }

    @RequestMapping(value = "/grupos/{id}/imagem", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> recuperarImagem(@PathVariable int id)
            throws IOException {
        Grupo grupo = grupoDAO.findById(id);
        if (grupo.getImagem() == null) {
            HttpHeaders respHeaders = new HttpHeaders();
            respHeaders.setContentType(MediaType.valueOf("image/jpeg"));
            InputStreamResource img =
                    new InputStreamResource(new ByteArrayInputStream(Files.readAllBytes(Paths.get("semImagem.jpeg"))));
            return new ResponseEntity<>(img, respHeaders, HttpStatus.OK);
        }
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.valueOf(grupo.getTipoImagem()));
        InputStreamResource img = new InputStreamResource(new ByteArrayInputStream(grupo.getImagem()));
        return new ResponseEntity<>(img, respHeaders, HttpStatus.OK);
    }
          
    @RequestMapping(path = "/grupos/{idGrupo}/coordenador/{idUsuario}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void tornarCoordenador(@AuthenticationPrincipal UsuarioAut usuarioAut, @PathVariable int idGrupo, @PathVariable int idUsuario) {

        Optional<Usuario> usuarioFind = usuarioDAO.findById(idUsuario);
        Usuario logado = usuarioDAO.findByEmail(usuarioAut.getUsername());

        if (grupoDAO.existsById(idGrupo) && usuarioFind.isPresent()) {
            Grupo grupo = grupoDAO.findById(idGrupo);

            if (grupo.getDonoGrupo() == logado
                    || usuarioAut.getUsuario().getPermissoes().contains("administrador")) {

                Usuario usuario = usuarioFind.get();


                List<Usuario> integrantes = grupo.getIntegrantesGrupo();

                if (integrantes.contains(usuario)) {                    

                    List<Usuario> coordenadores = grupo.getCoordenadoresGrupo();
                    coordenadores.add(usuario);
                    grupo.setCoordenadoresGrupo(coordenadores);

                    grupoDAO.save(grupo);
                    
                    Notificacao notificacao = new Notificacao("Você agora é coordenador(a) do grupo ", "", "",
                            Integer.toString(grupo.getId()), grupo.getNome(), "mensagem");

                    notificacao = notificacaoDAO.save(notificacao);

                    List<Notificacao> notificacoesUsuario = usuario.getNotificacoes();
                    notificacoesUsuario.add(notificacao);
                    usuario.setNotificacoes(notificacoesUsuario);

                    usuarioDAO.save(usuario);


                } else
                    throw new ForbiddenException("Usuário não é integrante do grupo.");
            } else
                throw new ForbiddenException("Além dos administradores do sistema somente o dono do grupo  poderá tornar participante um coordenador.");
        } else
            throw new ForbiddenException("Usuário ou grupo não encontrado.");
    }
    
    @RequestMapping(path = "/grupos/{idGrupo}/remover/{idUsuario}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void removerParticipante(@AuthenticationPrincipal UsuarioAut usuarioAut, @PathVariable int idGrupo, @PathVariable int idUsuario) {

        Usuario logado = usuarioDAO.findByEmail(usuarioAut.getUsername());

        Grupo grupo = grupoDAO.findById(idGrupo);

        if (grupo.getCoordenadoresGrupo().contains(logado)
                || usuarioAut.getUsuario().getPermissoes().contains("administrador")) {
            
  
                Optional<Usuario> findById = usuarioDAO.findById(idUsuario);
                Usuario removido = findById.get();
                
                if (!removido.getPermissoes().contains ("administrador")&&removido.getId()!=usuarioAut.getUsuario().getId()) {

                if (grupoDAO.existsById(idGrupo) && findById.isPresent()) {
                    List<Usuario> integrantes = grupo.getIntegrantesGrupo();
                    integrantes.remove(removido);
                    grupo.setIntegrantesGrupo(integrantes);

                    grupoDAO.save(grupo);

                    Notificacao notificacao = new Notificacao("Você foi removido (a) do grupo ", "", "",
                            Integer.toString(grupo.getId()), grupo.getNome(), "remocao");

                    notificacao = notificacaoDAO.save(notificacao);

                    List<Notificacao> notificacoesUsuario = removido.getNotificacoes();
                    notificacoesUsuario.add(notificacao);
                    removido.setNotificacoes(notificacoesUsuario);

                    usuarioDAO.save(removido);
                } else throw new ForbiddenException("Usuário ou grupo não encontrado.");
                } else throw new ForbiddenException("Não é possível remover um administrador.");
        } else
            throw new ForbiddenException("Além dos administradores do sistema somente coordenadores poderão remover participantes do grupo.");

    }
    
    @RequestMapping(path = "/grupos/{id}/coordenadores", method = RequestMethod.GET)
    public List<Usuario> listarCoordenadores(@PathVariable int id) {
        return grupoDAO.findById(id).getCoordenadoresGrupo();
    }
}