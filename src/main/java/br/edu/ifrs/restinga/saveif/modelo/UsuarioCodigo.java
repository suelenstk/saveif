/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.saveif.modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author gustavo
 */
@Entity
public class UsuarioCodigo implements Serializable{
    
    @Id
    private int id;   
       
    @ManyToOne
    Usuario usuarioCodigo;   
   
    @Column(nullable = false)
    String  codigo;

    public UsuarioCodigo() {    
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuarioCodigo() {
        return usuarioCodigo;
    }

    public void setUsuarioCodigo(Usuario usarioCodigo) {
        this.usuarioCodigo = usarioCodigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }      
       
}
