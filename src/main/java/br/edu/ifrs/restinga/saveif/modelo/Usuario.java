package br.edu.ifrs.restinga.saveif.modelo;

import br.edu.ifrs.restinga.saveif.util.Utilitarios;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true, nullable = false, length = 255)
    private String email;

    // Senha não deve nunca ficar disponível para a api cliente  
    @JsonIgnore
    private String senha;

    // Campo necessário para cadastrar senha inicial ou atualiza-lá 
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String novaSenha;

    @Column(nullable = false, length = 60)
    private String nome;

    @Column(unique = true, length = 14)
    private String cpf;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Column(nullable = false, length = 20)
    private String tipoVinculo;

    @Column(length = 80)
    private String sobreUsuario;

    @Lob()
    @Basic(fetch = FetchType.EAGER)
    @JsonIgnore
    private byte[] imagem;

    @JsonIgnore
    private String tipoImagem;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> permissoes;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataInsercao = new Date(System.currentTimeMillis());

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataDelecao;


    @ManyToOne
    private Curso curso;

    @JsonIgnore
    @OneToMany
    private List<Categoria> categorias;

    @JsonIgnore
    @OneToMany
    private List<Notificacao> notificacoes;

    @JsonIgnore
    @OneToMany(mappedBy = "donoGrupo")
    private List<Grupo> gruposDono;

    @JsonIgnore
    @ManyToMany(mappedBy = "coordenadoresGrupo")
    private List<Grupo> gruposCoordenados;

    @JsonIgnore
    @ManyToMany(mappedBy = "integrantesGrupo")
    private List<Grupo> gruposIntegrados;

    @JsonIgnore
    @ManyToMany(mappedBy = "convitesGrupo")
    private List<Grupo> gruposConvidado;


    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public String getTipoVinculo() {
        return tipoVinculo;
    }

    public String getSobreUsuario() {
        return sobreUsuario;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public String getTipoImagem() {
        return tipoImagem;
    }

    public List<String> getPermissoes() {
        return permissoes;
    }

    public Date getDataInsercao() {
        return dataInsercao;
    }

    public Date getDataDelecao() {
        return dataDelecao;
    }

    public Curso getCurso() {
        return curso;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public List<Grupo> getGruposDono() {
        return gruposDono;
    }

    public List<Grupo> getGruposCoordenados() {
        return gruposCoordenados;
    }

    public List<Grupo> getGruposIntegrados() {
        return gruposIntegrados;
    }

    public List<Grupo> getGruposConvidado() {
        return gruposConvidado;
    }

    
    
    

    public void setId(int id) {
        this.id = id;
    }
    public void setEmail(String prefixo) throws Exception {
        
        if (prefixo == null || prefixo.isEmpty())
            throw new Exception("O campo E-mail é de preenchimento obrigatório!");
        else if (!new Utilitarios().validaEmail(email+"@restinga.ifrs.edu.br"))
            throw new Exception("O E-mail digitado não é válido!");

        this.email = prefixo + "@restinga.ifrs.edu.br";   //Guarda e-mail completo no servidor
        
//        this.email = prefixo;   //Guarda somente prefixo no servidor
 
    }
    public void setSenha(String senha) throws Exception {
        if (senha == null || senha.isEmpty())
            throw new Exception("O campo senha é de preenchimento obrigatório.");
        else
            this.senha = senha;
    }
    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
    public void setNome(String nome) throws Exception {
        if (nome == null || nome.isEmpty())
            throw new Exception("O campo nome é de preenchimento obrigatório.");
        else if (nome.length() > 60)
            throw new Exception("Excedido o tamanho máximo para o campo nome");
        
        this.nome = nome;
    }
    public void setCpf(String cpf) throws Exception {
        if (!new Utilitarios().validaCPF(cpf))
            throw new Exception("O cpf informado não é válido!");
        
        this.cpf = cpf;
    }
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public void setTipoVinculo(String tipoVinculo) throws Exception {
         if (tipoVinculo == null || tipoVinculo.isEmpty())
            throw new Exception("O campo Tipo de Vinculo é de preenchimento obrigatório.");
        else if (tipoVinculo.length() > 20)
            throw new Exception("Excedido o tamanho máximo para o campo Tipo de Vinculo");

         this.tipoVinculo = tipoVinculo;
    }
    public void setSobreUsuario(String sobreUsuario) {
        this.sobreUsuario = sobreUsuario;
    }
    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
    public void setTipoImagem(String tipoImagem) {
        this.tipoImagem = tipoImagem;
    }
    public void setPermissoes(List<String> permissoes) {
        this.permissoes = permissoes;
    }
    public void setDataInsercao(Date dataInsercao) {
        this.dataInsercao = dataInsercao;
    }
    public void setDataDelecao(Date dataDelecao) {
        this.dataDelecao = dataDelecao;
    }
    public void setCurso(Curso curso) throws Exception {
        if (tipoVinculo.equalsIgnoreCase("aluno")) {
            if (curso == null)
                throw new Exception("Curso é de preenchimento obrigatório.");
        }
        this.curso = curso;
    }
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
    public void setNotificacoes(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }
    public void setGruposDono(List<Grupo> gruposDono) {
        this.gruposDono = gruposDono;
    }
    public void setGruposCoordenados(List<Grupo> gruposCoordenados) {
        this.gruposCoordenados = gruposCoordenados;
    }
    public void setGruposIntegrados(List<Grupo> gruposIntegrados) {
        this.gruposIntegrados = gruposIntegrados;
    }
    public void setGruposConvidado(List<Grupo> gruposConvidado) {
        this.gruposConvidado = gruposConvidado;
    }

  
}
