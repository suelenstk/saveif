/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.saveif.controller;

import br.edu.ifrs.restinga.saveif.aut.UsuarioAut;
import br.edu.ifrs.restinga.saveif.dao.GrupoDAO;
import br.edu.ifrs.restinga.saveif.dao.PostDAO;
import br.edu.ifrs.restinga.saveif.dao.TopicoDAO;
import br.edu.ifrs.restinga.saveif.modelo.Anexo;
import br.edu.ifrs.restinga.saveif.modelo.Post;
import br.edu.ifrs.restinga.saveif.modelo.Topico;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityExistsException;
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
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author gustavo e eduarda
 */
@RestController
@RequestMapping(path = "/api")
public class Posts {

    @Autowired
    PostDAO postDAO;
    
    @Autowired
    TopicoDAO topicoDAO;

    @RequestMapping(path = "/grupos/{id}/geral", method = RequestMethod.GET)
    public Iterable<Post> listarGeral(@RequestParam(required = false, defaultValue = "0") int pagina, @PathVariable int id) {
        PageRequest pageRequest = new PageRequest(pagina, 20);
        
        return postDAO.findGeral(id);
    }
    
    @RequestMapping(path = "/grupos/{id}/posts/{idt}", method = RequestMethod.GET)
    public Iterable<Post> listarPorTopico(@RequestParam(required = false, defaultValue = "0") int pagina, @PathVariable int id, @PathVariable int idt) {
        PageRequest pageRequest = new PageRequest(pagina, 20);
      
        return postDAO.findPorTopico(id, idt);
    }
    
    
    
    @RequestMapping(path = "/posts", method = RequestMethod.GET)
    public Iterable<Post> listar(@RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 20);
        return postDAO.findAll(pageRequest);
    }
    
    @RequestMapping(path="/posts", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Post inserir(@RequestBody Post post, @AuthenticationPrincipal UsuarioAut usuarioAut)
    {
        post.setId(0);
        post.setAutorPost(usuarioAut.getUsuario());
        
        Post postSalvo = postDAO.save(post);
        
        Optional<Topico> findById = topicoDAO.findById(1);
        
            if (findById.isPresent()){
                List<Post> posts = new ArrayList<>();
                posts = findById.get().getPosts();
                posts.add (post);
                findById.get().setPosts(posts);
        }
                
        return postSalvo;
    }
    
    @RequestMapping(path = "/posts/{id}/arquivo", method = RequestMethod.POST)
    public void inserirArquivo(@PathVariable int id,
            @RequestParam("arquivo") MultipartFile arquivo) {
        Anexo anexo = new Anexo();
        Optional<Post> findById = postDAO.findById(id);
        
            if (findById.isPresent()){
                try {
            anexo.setId(0);
            anexo.setTipoAnexo(arquivo.getContentType());
            anexo.setDocumentoAnexo(arquivo.getBytes());
                       
            findById.get().setAnexoPost(anexo);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }else throw new EntityExistsException("Não é possível encontrar este post");
    }

}
