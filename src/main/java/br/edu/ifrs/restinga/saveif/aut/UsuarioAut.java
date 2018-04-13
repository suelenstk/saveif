package br.edu.ifrs.restinga.saveif.aut;

import br.edu.ifrs.restinga.saveif.modelo.Usuario;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;


public class UsuarioAut extends User {
    private Usuario usuario;
    public UsuarioAut(Usuario usuario) {
        super(usuario.getEmail(),
                usuario.getSenha(),
                AuthorityUtils.createAuthorityList(
                        usuario.getPermissoes().toArray(new String[]{})));
        this.usuario=usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
    
}