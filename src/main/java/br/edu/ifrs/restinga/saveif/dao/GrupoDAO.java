/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.saveif.dao;

import br.edu.ifrs.restinga.saveif.modelo.Grupo;
import br.edu.ifrs.restinga.saveif.modelo.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author gustavo e eduarda
 */
@Repository
public interface GrupoDAO extends PagingAndSortingRepository<Grupo, Integer>{
    
    Page<Grupo> findByIntegrantesGrupo(Usuario integrantes, Pageable pageable);
    
    Grupo findById(int id);
    
    Page<Grupo> findByOrderByIdDesc(Pageable pageable);

}
