package br.edu.ifrs.restinga.saveif.dao;

import br.edu.ifrs.restinga.saveif.modelo.Notificacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface NotificacaoDAO extends PagingAndSortingRepository<Notificacao, Integer>{
    
    @Query( "SELECT notificacao FROM  Grupo grupo JOIN grupo.notificacoes notificacao WHERE grupo.id = :idgrupo")   
    public Page<Notificacao> findNotificacaoGrupo(@Param("idgrupo")int id,  Pageable pageable);
    
    
    @Query( "SELECT notificacao FROM  Usuario usuario JOIN usuario.notificacoes notificacao WHERE usuario.id = :idusuario")   
    public Page<Notificacao> findNotificacaoUsuario(@Param("idusuario")int id,  Pageable pageable);    
    
    
    
}
