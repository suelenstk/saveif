package br.edu.ifrs.restinga.saveif.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Grupo implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; 
  
    @Column(nullable = false, length=60)
    private String nome;     
    
    @Lob()
    @Basic(fetch = FetchType.EAGER)
    @JsonIgnore
    private byte[] imagem;
    
    @JsonIgnore
    private String tipoImagem;
    
    
    @Column(nullable = false, columnDefinition="TEXT")
    private String descricao;
    
    
    @Column(nullable = false, length = 20)
    private String tipoPrivacidade;
   
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataCriacao = new Date(System.currentTimeMillis());;

    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataDelecao;     

    
    
    
    @ManyToOne
    @JoinColumn(nullable = false)    
    private Categoria categoria;    
   
    @JsonIgnore
    @OneToMany(orphanRemoval=true)
    private List<Atividade> atividades;  
    
    @JsonIgnore
    @OneToMany(orphanRemoval=true)
    private List<Topico> topicos;  
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario donoGrupo;
    
    @JsonIgnore
    @ManyToMany
    private List<Usuario> coordenadoresGrupo;   
        
    
    @JsonIgnore
    @ManyToMany
    private List<Usuario> integrantesGrupo;
    
    @JsonIgnore
    @ManyToMany
    private List<Usuario> solicitantesGrupo; 
    
    @JsonIgnore
    @ManyToMany
    private List<Usuario> convitesGrupo; 

 
    
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public byte[] getImagem() {
        return imagem;
    }
    public String getTipoImagem() {
        return tipoImagem;
    }
    public String getDescricao() {
        return descricao;
    }
    public String getTipoPrivacidade() {
        return tipoPrivacidade;
    }
    public Date getDataCriacao() {
        return dataCriacao;
    }
    public Date getDataDelecao() {
        return dataDelecao;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public List<Atividade> getAtividades() {
        return atividades;
    }
    public List<Topico> getTopicos() {
        return topicos;
    }
    public Usuario getDonoGrupo() {
        return donoGrupo;
    }
    public List<Usuario> getCoordenadoresGrupo() {
        return coordenadoresGrupo;
    }
    public List<Usuario> getIntegrantesGrupo() {
        return integrantesGrupo;
    }
    public List<Usuario> getSolicitantesGrupo() {
        return solicitantesGrupo;
    }
    public List<Usuario> getConvitesGrupo() {
        return convitesGrupo;
    }

 
    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
    public void setTipoImagem(String tipoImagem) {
        this.tipoImagem = tipoImagem;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setTipoPrivacidade(String tipoPrivacidade) {
        this.tipoPrivacidade = tipoPrivacidade;
    }
    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public void setDataDelecao(Date dataDelecao) {
        this.dataDelecao = dataDelecao;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }
    public void setTopicos(List<Topico> topicos) {
        this.topicos = topicos;
    }
    public void setDonoGrupo(Usuario donoGrupo) {
        this.donoGrupo = donoGrupo;
    }
    public void setCoordenadoresGrupo(List<Usuario> coordenadoresGrupo) {
        this.coordenadoresGrupo = coordenadoresGrupo;
    }
    public void setIntegrantesGrupo(List<Usuario> integrantesGrupo) {
        this.integrantesGrupo = integrantesGrupo;
    }
    public void setSolicitantesGrupo(List<Usuario> solicitantesGrupo) {
        this.solicitantesGrupo = solicitantesGrupo;
    }
    public void setConvitesGrupo(List<Usuario> convitesGrupo) {
        this.convitesGrupo = convitesGrupo;
    }
    
         
  
    
    
    
    
    
}
