package br.edu.ifrs.restinga.saveif.aut;


import br.edu.ifrs.restinga.saveif.dao.UsuarioDAO;
import br.edu.ifrs.restinga.saveif.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DetailsService implements UserDetailsService {
    @Autowired
    UsuarioDAO usuarioDAO;
    @Override
    public UserDetails loadUserByUsername(String email) 
            throws UsernameNotFoundException {
        Usuario usuario = usuarioDAO.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException(email + " não existe!");
        }
        return new UsuarioAut(usuario);
    }
}