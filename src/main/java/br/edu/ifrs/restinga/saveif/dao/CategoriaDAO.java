/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.saveif.dao;

import br.edu.ifrs.restinga.saveif.modelo.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tiago
 */
@Repository
public interface CategoriaDAO extends PagingAndSortingRepository<Categoria, Integer>{
    
    @Query( "SELECT categoria FROM Categoria categoria ORDER BY categoria.nome") 
    public List<Categoria> findAllOrderByNome();        
    
    Categoria findById(int id);
}
