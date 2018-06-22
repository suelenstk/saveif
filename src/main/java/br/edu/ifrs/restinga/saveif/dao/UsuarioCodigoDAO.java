/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.saveif.dao;

import br.edu.ifrs.restinga.saveif.modelo.UsuarioCodigo;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author gustavo
 */
public interface UsuarioCodigoDAO extends CrudRepository<UsuarioCodigo, Integer>{
    
    public UsuarioCodigo findByCodigo(String codigo);
    
}
