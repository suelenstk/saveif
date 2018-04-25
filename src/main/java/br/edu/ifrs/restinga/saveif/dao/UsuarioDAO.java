package br.edu.ifrs.restinga.saveif.dao;

import br.edu.ifrs.restinga.saveif.modelo.Usuario;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends PagingAndSortingRepository<Usuario, Integer> {

    Page<Usuario> findByNome(String nome, Pageable pageable);
    
    Iterable<Usuario> findByNomeContaining(String nome); 

    public Usuario findByEmail(String email);
    

}