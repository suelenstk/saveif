package br.edu.ifrs.restinga.saveif.dao;

import br.edu.ifrs.restinga.saveif.modelo.Grupo;
import br.edu.ifrs.restinga.saveif.modelo.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends PagingAndSortingRepository<Usuario, Integer> {

    Page<Usuario> findByNome(String nome, Pageable pageable);

    Iterable<Usuario> findByNomeContaining(String nome);

    Page<Usuario> findByNomeContainingOrderByNome(String nome, Pageable pageable);
    
    //@Query("SELECT usuario FROM Grupo grupo JOIN grupo.integrantesGrupo usuario WHERE grupo.id = :idGrupo AND usuario.id")
    Page<Usuario> findByNomeAndGruposIntegradosNotIn(String nome, Grupo grupo, Pageable pageable);
    //Grupo grupo JOIN grupo.integrantesGrupo usuario WHERE grupo.id = :idGrupo
    @Query("SELECT usuarios FROM Usuario usuarios JOIN usuarios.gruposCoordenados grupoCoordenados JOIN usuarios.gruposIntegrados grupoIntegrados WHERE grupoCoordenados.id != :idGrupo AND grupoIntegrados.id != :idGrupo")
    Page<Usuario> findByNomeContainingAndGruposIntegradosNotInOrderByNome(@Param("idGrupo") int id, Pageable pageable);
    
    Page<Usuario> findByGruposIntegrados(Grupo integrantes, Pageable pageable);

    public Usuario findByEmail(String email);
    
    
}