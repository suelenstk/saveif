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
    
    
    @Query( "SELECT COUNT (notificacao) FROM  Usuario usuario JOIN usuario.notificacoes notificacao WHERE usuario.id = :idusuario")  
    public int countNotificacaoUsuario(@Param("idusuario")int id);       
    @Query( "SELECT COUNT (notificacao) FROM Grupo grupo JOIN grupo.notificacoes notificacao JOIN grupo.coordenadoresGrupo coordenador WHERE coordenador.id = :idusuario")  
    public int countNotificacaoGrupos(@Param("idusuario")int id); 
    
    
    // AINDA ESTAMOS ESTUDANDO A POSSIBILIDADE DE UNIR AS DUAS SEGUINTES CONSULTAS -->
    
    @Query( "SELECT notificacao FROM  Usuario usuario JOIN usuario.notificacoes notificacao WHERE usuario.id = :idusuario")   
    public Page<Notificacao> findNotificacaoUsuario(@Param("idusuario")int id,  Pageable pageable);   
    
    @Query( "SELECT notificacao FROM  Grupo grupo JOIN grupo.notificacoes notificacao JOIN grupo.coordenadoresGrupo coordenador WHERE coordenador.id = :idusuario")   
    public Page<Notificacao> findNotificacaoGrupos(@Param("idusuario")int id,  Pageable pageable); 
    
    // <--
}