package br.edu.ifrs.restinga.saveif.modelo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Curso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, length = 80)
    private String nome;

    @Column(nullable = false, length = 80)
    private String nivel;


    // Contrutores que permitem o cadastro e exclusao de usuarios.
    // Verificar validade dos mesmos.

    Curso(String id) {
        if (!id.equals("")) {
            this.id = Integer.parseInt(id);
        }
    }

    Curso() {

    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getNivel() {
        return nivel;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }


}
