package br.edu.ifrs.restinga.saveif.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(unique = true, nullable = false, length = 255)
    private String email;
    
    // Senha não deve nunca ficar disponível para a api cliente  
    @JsonIgnore  
    private String senha;
    
    // Campo necessário para cadastrar senha inicial ou atualiza-lá 
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String novaSenha;
    
    @Column(nullable = false, length = 60)
    private String nome;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> permissoes;   


    
    public void setId(int id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setPermissoes(List<String> permissoes) {
        this.permissoes = permissoes;
    }
    
    
    
    
    
    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getSenha() {
        return senha;
    }
    public String getNovaSenha() {
        return novaSenha;
    }
    public String getNome() {
        return nome;
    }
    public List<String> getPermissoes() {
        return permissoes;
    }

  

    
    
    
    
    
    
}
