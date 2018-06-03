package br.edu.ifrs.restinga.saveif.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Notificacao implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; 
    
    @Column(nullable = false)
    private String descricao;
    
    @Column(nullable = false, length = 80)
    private String link;
    
    @Column(nullable = false, length = 60)
    private String textoLink;
    
    @Column(nullable = false, length = 20)
    private String tipo;
     
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataNotificacao = new Date(System.currentTimeMillis());

    public Notificacao() {
    }

    public Notificacao(int id, String descricao, String link, String textoLink, String tipo) {
        this.id = id;
        this.descricao = descricao;
        this.link = link;
        this.textoLink = textoLink;
        this.tipo = tipo;
    }
    
    public int getId() {
        return id;
    }
    public String getDescricao() {
        return descricao;
    }
    public String getLink() {
        return link;
    }
    public Date getDataNotificacao() {
        return dataNotificacao;
    }    
    public String getTextoLink() {
        return textoLink;
    }
    public String getTipo() {
        return tipo;
    }
    

    public void setId(int id) {
        this.id = id;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setDataNotificacao(Date dataNotificacao) {
        this.dataNotificacao = dataNotificacao;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public void setTextoLink(String textoLink) {
        this.textoLink = textoLink;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


      
}
