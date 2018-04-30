package br.edu.ifrs.restinga.saveif.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    
    @JsonIgnore
    @OneToMany(orphanRemoval=true)
    private List<Post> posts;  
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario criadorTopico;


    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public Date getDataCriacao() {
        return dataCriacao;
    }
    public Date getDataFinalizacao() {
        return dataFinalizacao;
    }
    public Date getDataDelecao() {
        return dataDelecao;
    }
    public List<Post> getPosts() {
        return posts;
    }
    public Usuario getCriadorTopico() {
        return criadorTopico;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataFinalizacao(Date dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    public void setDataDelecao(Date dataDelecao) {
        this.dataDelecao = dataDelecao;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setCriadorTopico(Usuario criadorTopico) {
        this.criadorTopico = criadorTopico;
    }
    
    
    
    
    
    
}
