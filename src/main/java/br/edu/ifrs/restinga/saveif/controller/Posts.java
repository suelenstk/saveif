/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.saveif.controller;

import br.edu.ifrs.restinga.saveif.dao.PostDAO;
import br.edu.ifrs.restinga.saveif.modelo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author gustavo e eduarda
 */
@RestController
@RequestMapping(path = "/api")
public class Posts {

    @Autowired
    PostDAO PostDAO;

    @RequestMapping(path = "/posts", method = RequestMethod.GET)
    public Iterable<Post> listar(@RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        return PostDAO.findAll(pageRequest);
    }

}
