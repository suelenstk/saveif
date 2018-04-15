package br.edu.ifrs.restinga.saveif.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Topico implements Serializable {

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
     * @return the dataFinalizacao
     */
    public Date getDataFinalizacao() {
        return dataFinalizacao;
    }

    /**
     * @param dataFinalizacao the dataFinalizacao to set
     */
    public void setDataFinalizacao(Date dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
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
     * @return the posts
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * @param posts the posts to set
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    /**
     * @return the criadorTopico
     */
    public Usuario getCriadorTopico() {
        return criadorTopico;
    }

    /**
     * @param criadorTopico the criadorTopico to set
     */
    public void setCriadorTopico(Usuario criadorTopico) {
        this.criadorTopico = criadorTopico;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; 
  
    @Column(nullable = false, length=80)
    private String nome; 
    
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataCriacao = new Date(System.currentTimeMillis());;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataFinalizacao;    
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataDelecao;    
    
    @OneToMany(orphanRemoval=true)
    private List<Post> posts;  
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario criadorTopico;
    
    
    
    
}
