package br.edu.ifrs.restinga.saveif.controller;

import br.edu.ifrs.restinga.saveif.aut.ForbiddenException;
import br.edu.ifrs.restinga.saveif.aut.UsuarioAut;
import br.edu.ifrs.restinga.saveif.dao.GrupoDAO;
import br.edu.ifrs.restinga.saveif.dao.NotificacaoDAO;
import br.edu.ifrs.restinga.saveif.dao.UsuarioCodigoDAO;
import br.edu.ifrs.restinga.saveif.dao.UsuarioDAO;
import br.edu.ifrs.restinga.saveif.modelo.Grupo;
import br.edu.ifrs.restinga.saveif.modelo.Usuario;
import br.edu.ifrs.restinga.saveif.modelo.UsuarioCodigo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api")
public class Usuarios {

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping(path = "/usuarios", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario inserir(@AuthenticationPrincipal UsuarioAut usuarioAut, @RequestBody Usuario usuario) throws Exception {
        if (usuarioDAO.findByEmail(usuario.getEmail()) != null) {
            throw new Exception("E-mail já cadastrado no sistema. Por favor, tente novamente.");
        }

        usuario.setId(0);
        usuario.setSenha(PASSWORD_ENCODER.encode(usuario.getNovaSenha()));

        if (usuarioAut == null || !usuarioAut.getUsuario().getPermissoes().contains("administrador")) {
            ArrayList<String> permissao = new ArrayList<String>();
            permissao.add("usuario");
            usuario.setPermissoes(permissao);
        }
        Usuario usuarioSalvo = usuarioDAO.save(usuario);
        return usuarioSalvo;
    }

    @Autowired
    UsuarioDAO usuarioDAO;

    @Autowired
    UsuarioCodigoDAO usuarioCodigoDAO;

    @Autowired
    GrupoDAO grupoDAO;

    @Autowired
    NotificacaoDAO notificacaoDAO;

    @RequestMapping(path = "/usuarios/listar", method = RequestMethod.GET)
    public Iterable<Usuario> listarSemPaginacao(@AuthenticationPrincipal UsuarioAut usuarioAut, @RequestParam(required = false) String nome) {
        if (nome != null && !nome.isEmpty()) {
            return usuarioDAO.findByNomeContaining(nome);
        } else {
            return usuarioDAO.findAll();
        }
    }

    @RequestMapping(path = "/usuarios/pesquisar/nome", method = RequestMethod.GET)
    public Iterable<Usuario> pesquisaPorNome(
            @RequestParam(required = false) String igual,
            @RequestParam(required = false) String contem,
            @RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 8);
        if (igual != null) {
            return usuarioDAO.findByNome(igual, pageRequest);
        } else {
            return usuarioDAO.findByNomeContainingOrderByNome(contem, pageRequest);
        }
    }

    /*
    @RequestMapping(path = "/usuarios/pesquisar/nome/{idGrupo}", method = RequestMethod.GET)
    public Iterable<Usuario> pesquisaPorNomeNaoEstaGrupo(
            @RequestParam(required = false) String igual,
            @RequestParam(required = false) String contem,
            @RequestParam(required = false, defaultValue = "0") int pagina, @PathVariable int idGrupo) {
        PageRequest pageRequest = new PageRequest(pagina, 8);
        
        Grupo integrantes = new Grupo();
        integrantes.setId(idGrupo);
        
        if (igual != null) {
            return usuarioDAO.findByNomeAndGruposIntegradosNotIn(igual, integrantes, pageRequest);
        } else {
            return usuarioDAO.findByNomeContainingOrderByNomeAndGruposIntegradosNotIn(contem, integrantes, pageRequest);
        }
    }*/
    @RequestMapping(path = "/usuarios/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Usuario> recuperar(@AuthenticationPrincipal UsuarioAut usuarioAut, @PathVariable int id) {

        if (usuarioAut.getUsuario().getId() == id
                || usuarioAut.getUsuario().getPermissoes().contains("administrador")) {

            Optional<Usuario> findById = usuarioDAO.findById(id);

            if (findById.isPresent()) {
                return ResponseEntity.ok(findById.get());
            } else {
                return ResponseEntity.notFound().build();
            }

        } else {
            throw new ForbiddenException("Não é permitido acessar dados de outros usuários");
        }

    }

    @RequestMapping(path = "/usuarios/{id}", method = RequestMethod.PUT)
    public void atualizar(@PathVariable int id, @RequestBody Usuario usuario) throws Exception {

        if (usuarioDAO.existsById(id)) {

            usuario.setId(id);
            Optional<Usuario> findById = usuarioDAO.findById(id);
            Usuario alt = findById.get();
            System.out.println(usuario);
            alt.setNome(usuario.getNome());
            alt.setTipoVinculo(usuario.getTipoVinculo());
            alt.setCurso(usuario.getCurso());
            alt.setSobreUsuario(usuario.getSobreUsuario());

            usuarioDAO.save(alt);

        }
    }

    @RequestMapping(path = "/usuarios/recuperar", method = RequestMethod.PUT)
    public void alterarSenha(@RequestParam(required = false) String codigo, 
            @RequestBody Usuario usuario) throws Exception {

        UsuarioCodigo codigoUsuario = usuarioCodigoDAO.findByCodigo(codigo);
        //System.out.println("dd: " + codigoUsuario);
        try {
            
            if (usuarioDAO.existsById(codigoUsuario.getUsuarioCodigo().getId())) {

                Optional<Usuario> findById
                        = usuarioDAO.findById(codigoUsuario.getUsuarioCodigo().getId());

                Usuario alt = findById.get();

                alt.setSenha(PASSWORD_ENCODER.encode(usuario.getNovaSenha()));

                usuarioDAO.save(alt);

            } else {
                
                throw new Exception("Usuário não encontrado");
                
            }

        } catch (NullPointerException npe) {

            throw new Exception("Não existe nenhum usuário para o código informado!");

        }
    }

    @RequestMapping(path = "/usuarios/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (usuarioDAO.existsById(id)) {
            usuarioDAO.deleteById(id);
        }
    }

    public static final String SEGREDO
            = "string grande para c*, usada como chave para assinatura! Queijo!";

    @RequestMapping(path = "/usuarios/login", method = RequestMethod.GET)
    public ResponseEntity<Usuario> login(@AuthenticationPrincipal UsuarioAut usuarioAut)
            throws IllegalArgumentException, UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(SEGREDO);
        Calendar agora = Calendar.getInstance();
        agora.add(Calendar.HOUR, 24);
        Date expira = agora.getTime();

        String token = JWT.create()
                .withClaim("id", usuarioAut.getUsuario().getId()).
                //                withExpiresAt(expira).
                sign(algorithm);
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.set("token", token);

        return new ResponseEntity<>(usuarioAut.getUsuario(), respHeaders, HttpStatus.OK);
    }

    @RequestMapping(path = "/usuarios/validarLogin", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Usuario> validarLogin(@AuthenticationPrincipal UsuarioAut usuarioAut) {

        Optional<Usuario> findById = usuarioDAO.findById(usuarioAut.getUsuario().getId());

        if (findById.isPresent()) {
            return ResponseEntity.ok(findById.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @RequestMapping(path = "/usuario/participantes/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Usuario> listarParticipantes(@RequestParam(required = false,
            defaultValue = "0") int pagina, @PathVariable int id) throws Exception {

        PageRequest pageRequest = new PageRequest(pagina, 5);

        Grupo igual = grupoDAO.findById(id);

        return usuarioDAO.findByGruposIntegrados(igual, pageRequest);
    }

    @RequestMapping(path = "/usuarios/consultar", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public boolean consultarExistencia(@RequestParam(required = false) String email) {
        return usuarioDAO.findByEmail(email + "@restinga.ifrs.edu.br") != null;
    }

    @RequestMapping(path = "/usuarios/email", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Usuario consultarEmail(@RequestParam(required = false) String email) throws Exception {

        Usuario usuario = usuarioDAO.findByEmail(email);
        if (usuario != null) {

            return usuario;

        } else {
            throw new Exception("Não existe nenhum usuário com esse Email!");
        }
    }

    @RequestMapping(path = "/usuarios/code", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioCodigo inserirCodigo(@RequestBody UsuarioCodigo usuarioCodigo) throws Exception {

        Optional<Usuario> usuario = usuarioDAO.findById(usuarioCodigo.getId());
        usuarioCodigo.setUsuarioCodigo(usuario.get());
        
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("Código de para alterar senha!");
        message.setText("Seu código é: " + usuarioCodigo.getCodigo());
        message.setFrom("desenvolvimento.sistemas2@gmail.com");
        //System.out.println(usuarioCodigo.getUsuarioCodigo().getEmail());
        message.setTo(usuarioCodigo.getUsuarioCodigo().getEmail());
        //message.setFrom("gstvcamargo@gmail.com");

        try {
            mailSender.send(message);
            return usuarioCodigoDAO.save(usuarioCodigo);

        } catch (Exception e) {
            throw new Exception("Erro ao enviar Email! " + e.getMessage());
        }

    }

    @RequestMapping(path = "/usuarios/{id}/imagem", method = RequestMethod.PUT)
    public ResponseEntity<InputStreamResource> inserirImagem(@PathVariable int id,
            @RequestParam("arquivo") MultipartFile uploadfiles) throws Exception {

        Optional<Usuario> findById = usuarioDAO.findById(id);
        Usuario alt = findById.get();

        try {

            if (!uploadfiles.getContentType().equals("application/octet-stream")) {

                System.out.println(uploadfiles.getContentType());
                alt.setTipoImagem(uploadfiles.getContentType());
                alt.setImagem(uploadfiles.getBytes());
                usuarioDAO.save(alt);

                return recuperarImagem(id);

            } else {
                return recuperarImagem(id);

            }

        } catch (IOException ex) {

            ex.printStackTrace();
            throw new Exception("Erro ao salvar arquivo de imagem");
        }
    }

    @RequestMapping(value = "/usuarios/{id}/imagem", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> recuperarImagem(@PathVariable int id)
            throws IOException {
        Optional<Usuario> findById = usuarioDAO.findById(id);
        Usuario usuario = findById.get();

        if (usuario.getImagem() == null) {
            HttpHeaders respHeaders = new HttpHeaders();
            respHeaders.setContentType(MediaType.valueOf("image/jpeg"));
            InputStreamResource img
                    = new InputStreamResource(new ByteArrayInputStream(Files.readAllBytes(Paths.get("semFoto.png"))));
            return new ResponseEntity<>(img, respHeaders, HttpStatus.OK);
        }
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.valueOf(usuario.getTipoImagem()));
        InputStreamResource img = new InputStreamResource(new ByteArrayInputStream(usuario.getImagem()));
        return new ResponseEntity<>(img, respHeaders, HttpStatus.OK);
    }

    @RequestMapping(path = "/usuarios/{idUsuario}/convite/{idGrupo}/aceite/{idNotificacao}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void aceitarConvite(@AuthenticationPrincipal UsuarioAut usuarioAut, @PathVariable int idUsuario, @PathVariable int idGrupo, @PathVariable(required = false) Integer idNotificacao) throws Exception {

        Usuario logado = usuarioDAO.findByEmail(usuarioAut.getUsername());

        if (usuarioAut.getUsuario().getPermissoes().contains("administrador")
                || logado.getId() == idUsuario) {

            Optional<Usuario> usuarioFind = usuarioDAO.findById(idUsuario);

            if (grupoDAO.existsById(idGrupo) && usuarioFind.isPresent()) {

                if (idNotificacao != null && notificacaoDAO.existsById(idNotificacao)) {
                    notificacaoDAO.deleteById(idNotificacao);
                }

                Grupo grupo = grupoDAO.findById(idGrupo);
                Usuario convidado = usuarioFind.get();

                List<Grupo> convitesUsuario = convidado.getGruposConvidado();

                if (convitesUsuario.contains(grupo)) {
                    List<Usuario> integrantes = grupo.getIntegrantesGrupo();
                    integrantes.add(convidado);
                    grupo.setIntegrantesGrupo(integrantes);

                    List<Usuario> convitesGrupo = grupo.getConvitesGrupo();
                    convitesGrupo.remove(convidado);
                    grupo.setConvitesGrupo(convitesGrupo);

                    grupoDAO.save(grupo);

                } else {
                    throw new ForbiddenException("Usuário não possui convite para grupo.");
                }

            } else {
                throw new Exception("Usuário ou grupo não encontrado.");
            }

        } else {
            throw new ForbiddenException("Além dos administradores do sistema somente o próprio usuário poderá aceitar participação em grupos.");
        }

    }

    @RequestMapping(path = "/usuarios/{idUsuario}/convite/{idGrupo}/negacao/{idNotificacao}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void negarConvite(@AuthenticationPrincipal UsuarioAut usuarioAut, @PathVariable int idUsuario, @PathVariable int idGrupo, @PathVariable(required = false) Integer idNotificacao) throws Exception {

        Usuario logado = usuarioDAO.findByEmail(usuarioAut.getUsername());

        if (usuarioAut.getUsuario().getPermissoes().contains("administrador")
                || logado.getId() == idUsuario) {

            Optional<Usuario> usuarioFind = usuarioDAO.findById(idUsuario);

            if (grupoDAO.existsById(idGrupo) && usuarioFind.isPresent()) {

                if (idNotificacao != null && notificacaoDAO.existsById(idNotificacao)) {
                    notificacaoDAO.deleteById(idNotificacao);
                }

                Grupo grupo = grupoDAO.findById(idGrupo);
                Usuario convidado = usuarioFind.get();

                List<Grupo> convitesUsuario = convidado.getGruposConvidado();

                if (convitesUsuario.contains(grupo)) {

                    List<Usuario> convitesGrupo = grupo.getConvitesGrupo();
                    convitesGrupo.remove(convidado);
                    grupo.setConvitesGrupo(convitesGrupo);

                    grupoDAO.save(grupo);

                } else {
                    throw new ForbiddenException("Usuário não possui convite para grupo.");
                }

            } else {
                throw new Exception("Usuário ou grupo não encontrado.");
            }

        } else {
            throw new ForbiddenException("Além dos administradores do sistema somente o próprio usuário poderá negar participação em grupos.");
        }
    }

}
