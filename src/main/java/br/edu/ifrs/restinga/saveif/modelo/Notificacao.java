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
public class Notificacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false, length = 80)
    private String linkUsuario;

    @Column(nullable = false, length = 60)
    private String textoUsuario;

    @Column(nullable = false, length = 80)
    private String linkGrupo;

    @Column(nullable = false, length = 60)
    private String textoGrupo;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataNotificacao = new Date(System.currentTimeMillis());

    public Notificacao() {
    }

    public Notificacao(String descricao, String linkUsuario, String textoUsuario, String linkGrupo, String textoGrupo, String tipo) {
        this.descricao = descricao;
        this.linkUsuario = linkUsuario;
        this.textoUsuario = textoUsuario;
        this.linkGrupo = linkGrupo;
        this.textoGrupo = textoGrupo;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getLinkUsuario() {
        return linkUsuario;
    }

    public Date getDataNotificacao() {
        return dataNotificacao;
    }

    public String getTextoUsuario() {
        return textoUsuario;
    }

    public String getTipo() {
        return tipo;
    }

    public String getLinkGrupo() {
        return linkGrupo;
    }

    public String getTextoGrupo() {
        return textoGrupo;
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

    public void setLinkUsuario(String linkUsuario) {
        this.linkUsuario = linkUsuario;
    }

    public void setTextoUsuario(String textoUsuario) {
        this.textoUsuario = textoUsuario;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setLinkGrupo(String linkGrupo) {
        this.linkGrupo = linkGrupo;
    }

    public void setTextoGrupo(String textoGrupo) {
        this.textoGrupo = textoGrupo;
    }
}
