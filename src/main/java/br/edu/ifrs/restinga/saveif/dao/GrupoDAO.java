package br.edu.ifrs.restinga.saveif.dao;

import br.edu.ifrs.restinga.saveif.modelo.Grupo;
import br.edu.ifrs.restinga.saveif.modelo.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GrupoDAO extends PagingAndSortingRepository<Grupo, Integer>{
    
    Page<Grupo> findByIntegrantesGrupo(Usuario integrantes, Pageable pageable);
    
    Grupo findById(int id);
    
    Page<Grupo> findByOrderByIdDesc(Pageable pageable);

}
