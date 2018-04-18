package br.edu.ifrs.restinga.saveif.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    
    @Column(unique = true, length=14)
    private String cpf;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento; 
    
    @Column(nullable = false, length = 20)
    private String tipoVinculo;
    
    @Column(length = 80)
    private String sobreUsuario;
        
    @Lob()
    @Basic(fetch = FetchType.EAGER)
    @JsonIgnore
    private byte[] imagem;
    
    @JsonIgnore
    private String tipoImagem;    
            
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> permissoes;   

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataInsercao = new Date(System.currentTimeMillis());;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataDelecao;
    
    
    @ManyToOne
    private Curso curso;    
    
    @JsonIgnore
    @OneToMany
    private List<Categoria> categorias;
    
    @JsonIgnore
    @OneToMany
    private List<Notificacao> notificacoes;
    
    @JsonIgnore 
    @OneToMany(mappedBy = "donoGrupo")
    private List<Grupo> gruposDono;

    @JsonIgnore 
    @ManyToMany(mappedBy = "coordenadoresGrupo")
    private List<Grupo> gruposCoordenados;
    
    @JsonIgnore 
    @ManyToMany(mappedBy = "integrantesGrupo")
    private List<Grupo> gruposIntegrados;
    
    @JsonIgnore 
    @ManyToMany(mappedBy = "convitesGrupo")
    private List<Grupo> gruposConvidado;


    
    
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
    public String getCpf() {
        return cpf;
    }
    public Date getDataNascimento() {
        return dataNascimento;
    }
    public String getTipoVinculo() {
        return tipoVinculo;
    }
    public String getSobreUsuario() {
        return sobreUsuario;
    }
    public byte[] getImagem() {
        return imagem;
    }
    public String getTipoImagem() {
        return tipoImagem;
    }
    public List<String> getPermissoes() {
        return permissoes;
    }
    public Date getDataInsercao() {
        return dataInsercao;
    }
    public Date getDataDelecao() {
        return dataDelecao;
    }
    public Curso getCurso() {
        return curso;
    }
    public List<Categoria> getCategorias() {
        return categorias;
    }
    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }
    public List<Grupo> getGruposDono() {
        return gruposDono;
    }
    public List<Grupo> getGruposCoordenados() {
        return gruposCoordenados;
    }
    public List<Grupo> getGruposIntegrados() {
        return gruposIntegrados;
    }
    public List<Grupo> getGruposConvidado() {
        return gruposConvidado;
    }

    
    
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
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public void setTipoVinculo(String tipoVinculo) {
        this.tipoVinculo = tipoVinculo;
    }
    public void setSobreUsuario(String sobreUsuario) {
        this.sobreUsuario = sobreUsuario;
    }
    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
    public void setTipoImagem(String tipoImagem) {
        this.tipoImagem = tipoImagem;
    }
    public void setPermissoes(List<String> permissoes) {
        this.permissoes = permissoes;
    }
    public void setDataInsercao(Date dataInsercao) {
        this.dataInsercao = dataInsercao;
    }
    public void setDataDelecao(Date dataDelecao) {
        this.dataDelecao = dataDelecao;
    }
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
    public void setNotificacoes(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }
    public void setGruposDono(List<Grupo> gruposDono) {
        this.gruposDono = gruposDono;
    }
    public void setGruposCoordenados(List<Grupo> gruposCoordenados) {
        this.gruposCoordenados = gruposCoordenados;
    }
    public void setGruposIntegrados(List<Grupo> gruposIntegrados) {
        this.gruposIntegrados = gruposIntegrados;
    }
    public void setGruposConvidado(List<Grupo> gruposConvidado) {
        this.gruposConvidado = gruposConvidado;
    }

  
    
    
    
}
