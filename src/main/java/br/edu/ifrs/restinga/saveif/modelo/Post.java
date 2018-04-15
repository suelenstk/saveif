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
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * @return the dataPostagem
     */
    public Date getDataPostagem() {
        return dataPostagem;
    }

    /**
     * @param dataPostagem the dataPostagem to set
     */
    public void setDataPostagem(Date dataPostagem) {
        this.dataPostagem = dataPostagem;
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
     * @return the autorPost
     */
    public Usuario getAutorPost() {
        return autorPost;
    }

    /**
     * @param autorPost the autorPost to set
     */
    public void setAutorPost(Usuario autorPost) {
        this.autorPost = autorPost;
    }

    /**
     * @return the anexoPost
     */
    public Anexo getAnexoPost() {
        return anexoPost;
    }

    /**
     * @param anexoPost the anexoPost to set
     */
    public void setAnexoPost(Anexo anexoPost) {
        this.anexoPost = anexoPost;
    }
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
    @JoinColumn(nullable = false)
    private Anexo anexoPost;
    
    
}
