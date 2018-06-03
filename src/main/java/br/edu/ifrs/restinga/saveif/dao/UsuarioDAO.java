package br.edu.ifrs.restinga.saveif.dao;

import br.edu.ifrs.restinga.saveif.modelo.Grupo;
import br.edu.ifrs.restinga.saveif.modelo.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends PagingAndSortingRepository<Usuario, Integer> {

    Page<Usuario> findByNome(String nome, Pageable pageable);

    Iterable<Usuario> findByNomeContaining(String nome);

    Page<Usuario> findByNomeContainingOrderByNome(String nome, Pageable pageable);

    Page<Usuario> findByGruposIntegrados(Grupo integrantes, Pageable pageable);

    public Usuario findByEmail(String email);
    
}