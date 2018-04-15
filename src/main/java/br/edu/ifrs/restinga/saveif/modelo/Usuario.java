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
    
    @Column(nullable = false)
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
    
    @OneToMany
    private List<Categoria> categorias;
    
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

 
    

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the novaSenha
     */
    public String getNovaSenha() {
        return novaSenha;
    }

    /**
     * @param novaSenha the novaSenha to set
     */
    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the dataNascimento
     */
    public Date getDataNascimento() {
        return dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * @return the tipoVinculo
     */
    public String getTipoVinculo() {
        return tipoVinculo;
    }

    /**
     * @param tipoVinculo the tipoVinculo to set
     */
    public void setTipoVinculo(String tipoVinculo) {
        this.tipoVinculo = tipoVinculo;
    }

    /**
     * @return the sobreUsuario
     */
    public String getSobreUsuario() {
        return sobreUsuario;
    }

    /**
     * @param sobreUsuario the sobreUsuario to set
     */
    public void setSobreUsuario(String sobreUsuario) {
        this.sobreUsuario = sobreUsuario;
    }

    /**
     * @return the imagem
     */
    public byte[] getImagem() {
        return imagem;
    }

    /**
     * @param imagem the imagem to set
     */
    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    /**
     * @return the tipoImagem
     */
    public String getTipoImagem() {
        return tipoImagem;
    }

    /**
     * @param tipoImagem the tipoImagem to set
     */
    public void setTipoImagem(String tipoImagem) {
        this.tipoImagem = tipoImagem;
    }

    /**
     * @return the permissoes
     */
    public List<String> getPermissoes() {
        return permissoes;
    }

    /**
     * @param permissoes the permissoes to set
     */
    public void setPermissoes(List<String> permissoes) {
        this.permissoes = permissoes;
    }

    /**
     * @return the dataInsercao
     */
    public Date getDataInsercao() {
        return dataInsercao;
    }

    /**
     * @param dataInsercao the dataInsercao to set
     */
    public void setDataInsercao(Date dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    /**
     * @return the dataDelecao
     */
    public Date getDataDelecao() {
        return dataDelecao;
    }

    /**
     * @param dataDelecao the dataDelecao to set
     */
    public void setDataDelecao(Date dataDelecao) {
        this.dataDelecao = dataDelecao;
    }

    /**
     * @return the curso
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    /**
     * @return the categorias
     */
    public List<Categoria> getCategorias() {
        return categorias;
    }

    /**
     * @param categorias the categorias to set
     */
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    /**
     * @return the notificacoes
     */
    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    /**
     * @param notificacoes the notificacoes to set
     */
    public void setNotificacoes(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    /**
     * @return the gruposDono
     */
    public List<Grupo> getGruposDono() {
        return gruposDono;
    }

    /**
     * @param gruposDono the gruposDono to set
     */
    public void setGruposDono(List<Grupo> gruposDono) {
        this.gruposDono = gruposDono;
    }

    /**
     * @return the gruposCoordenados
     */
    public List<Grupo> getGruposCoordenados() {
        return gruposCoordenados;
    }

    /**
     * @param gruposCoordenados the gruposCoordenados to set
     */
    public void setGruposCoordenados(List<Grupo> gruposCoordenados) {
        this.gruposCoordenados = gruposCoordenados;
    }

    /**
     * @return the gruposIntegrados
     */
    public List<Grupo> getGruposIntegrados() {
        return gruposIntegrados;
    }

    /**
     * @param gruposIntegrados the gruposIntegrados to set
     */
    public void setGruposIntegrados(List<Grupo> gruposIntegrados) {
        this.gruposIntegrados = gruposIntegrados;
    }

    /**
     * @return the gruposConvidado
     */
    public List<Grupo> getGruposConvidado() {
        return gruposConvidado;
    }

    /**
     * @param gruposConvidado the gruposConvidado to set
     */
    public void setGruposConvidado(List<Grupo> gruposConvidado) {
        this.gruposConvidado = gruposConvidado;
    }
    
    
    
    
    
}
