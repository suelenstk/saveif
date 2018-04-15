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
    
    @Column(nullable = false, columnDefinition="TEXT")
    private String descricao;
    
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataNotificacao = new Date(System.currentTimeMillis());


    public int getId() {
        return id;
    }
    public String getDescricao() {
        return descricao;
    }
    public Date getDataNotificacao() {
        return dataNotificacao;
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

    
    
    
}
