package br.edu.ifrs.restinga.saveif.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; 
  
    @Column(nullable = false, length=60)
    private String titulo;
    
    @Column(nullable = false, columnDefinition="TEXT")
    private String texto;
    
    
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataPostagem = new Date(System.currentTimeMillis());
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataDelecao;   
    
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario autorPost;
    
        
    @ManyToOne
    private Anexo anexoPost;

    
    public int getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getTexto() {
        return texto;
    }
    public Date getDataPostagem() {
        return dataPostagem;
    }
    public Date getDataDelecao() {
        return dataDelecao;
    }
    public Usuario getAutorPost() {
        return autorPost;
    }
    public Anexo getAnexoPost() {
        return anexoPost;
    }


    public void setId(int id) {
        this.id = id;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
    public void setDataPostagem(Date dataPostagem) {
        this.dataPostagem = dataPostagem;
    }
    public void setDataDelecao(Date dataDelecao) {
        this.dataDelecao = dataDelecao;
    }
    public void setAutorPost(Usuario autorPost) {
        this.autorPost = autorPost;
    }
    public void setAnexoPost(Anexo anexoPost) {
        this.anexoPost = anexoPost;
    }
    
    
    
    
    
    
    
    
}
