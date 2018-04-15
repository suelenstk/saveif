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
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the tipoPrivacidade
     */
    public String getTipoPrivacidade() {
        return tipoPrivacidade;
    }

    /**
     * @param tipoPrivacidade the tipoPrivacidade to set
     */
    public void setTipoPrivacidade(String tipoPrivacidade) {
        this.tipoPrivacidade = tipoPrivacidade;
    }

    /**
     * @return the dataCriacao
     */
    public Date getDataCriacao() {
        return dataCriacao;
    }

    /**
     * @param dataCriacao the dataCriacao to set
     */
    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
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
     * @return the categoria
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the donoGrupo
     */
    public Usuario getDonoGrupo() {
        return donoGrupo;
    }

    /**
     * @param donoGrupo the donoGrupo to set
     */
    public void setDonoGrupo(Usuario donoGrupo) {
        this.donoGrupo = donoGrupo;
    }

    /**
     * @return the coordenadoresGrupo
     */
    public List<Usuario> getCoordenadoresGrupo() {
        return coordenadoresGrupo;
    }

    /**
     * @param coordenadoresGrupo the coordenadoresGrupo to set
     */
    public void setCoordenadoresGrupo(List<Usuario> coordenadoresGrupo) {
        this.coordenadoresGrupo = coordenadoresGrupo;
    }

    /**
     * @return the integrantesGrupo
     */
    public List<Usuario> getIntegrantesGrupo() {
        return integrantesGrupo;
    }

    /**
     * @param integrantesGrupo the integrantesGrupo to set
     */
    public void setIntegrantesGrupo(List<Usuario> integrantesGrupo) {
        this.integrantesGrupo = integrantesGrupo;
    }

    /**
     * @return the solicitantesGrupo
     */
    public List<Usuario> getSolicitantesGrupo() {
        return solicitantesGrupo;
    }

    /**
     * @param solicitantesGrupo the solicitantesGrupo to set
     */
    public void setSolicitantesGrupo(List<Usuario> solicitantesGrupo) {
        this.solicitantesGrupo = solicitantesGrupo;
    }

    /**
     * @return the convitesGrupo
     */
    public List<Usuario> getConvitesGrupo() {
        return convitesGrupo;
    }

    /**
     * @param convitesGrupo the convitesGrupo to set
     */
    public void setConvitesGrupo(List<Usuario> convitesGrupo) {
        this.convitesGrupo = convitesGrupo;
    }

    /**
     * @return the atividades
     */
    public List<Atividade> getAtividades() {
        return atividades;
    }

    /**
     * @param atividades the atividades to set
     */
    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }
    
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
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario donoGrupo;
    
    @ManyToMany
    private List<Usuario> coordenadoresGrupo;   
        
     
    @ManyToMany
    private List<Usuario> integrantesGrupo;
    
    
    @ManyToMany
    private List<Usuario> solicitantesGrupo; 
    
    @ManyToMany
    private List<Usuario> convitesGrupo; 
    
         
    @OneToMany(orphanRemoval=true)
    private List<Atividade> atividades;    
    
    
}
