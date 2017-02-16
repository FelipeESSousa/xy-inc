package br.com.zup.model.dao.entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TB_USUARIO")
@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 9159805420894695448L;

    @Id
    @SequenceGenerator(name = "TB_USUARIO_IDUSUARIO_GENERATOR", sequenceName = "SQ_USUARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_USUARIO_IDUSUARIO_GENERATOR")
    @Column(name = "ID_USUARIO", unique = true, nullable = false, precision = 11)
    private Long idUsuario;

    @Column(name = "NM_EMAIL", nullable = false, length = 100)
    private String nmEmail;

    @Column(name = "NM_LOGIN", nullable = false, length = 50)
    private String nmLogin;

    @Column(name = "NM_PERFIL", nullable = false, length = 150)
    private String nmPerfil;

    @Column(name = "NM_SENHA", nullable = false, length = 80)
    private String nmSenha;

    @Column(name = "NM_USUARIO", nullable = false, length = 150)
    private String nmUsuario;

    @Transient
    private String login = this.nmLogin;

    @Transient
    private String senha = this.nmSenha;

    @Column(name = "NR_CPF", nullable = false, length = 11)
    private String nrCpf;

    @Column(name = "NR_TELEFONE", nullable = false, length = 15)
    private String nrTelefone;

    @Column(name = "ST_USUARIO", nullable = false, length = 1)
    private String stUsuario;


    @Transient
    private String nmSenhaAtual;

    @Transient
    private String nmNovaSenha;

    @Transient
    private String nmConfirmacaoNovaSenha;

    public Usuario() {
        super();
    }

    public Long getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNmEmail() {
        return this.nmEmail;
    }

    public void setNmEmail(String nmEmail) {
        this.nmEmail = nmEmail;
    }

    public String getNmLogin() {
        return this.nmLogin;
    }

    public void setNmLogin(String nmLogin) {
        this.nmLogin = nmLogin;
    }

    public String getNmPerfil() {
        return this.nmPerfil;
    }

    public void setNmPerfil(String nmPerfil) {
        this.nmPerfil = nmPerfil;
    }


    public String getNmSenha() {
        return this.nmSenha;
    }

    public void setNmSenha(String nmSenha) {
        this.nmSenha = nmSenha;
    }

    public String getNmUsuario() {
        return this.nmUsuario;
    }

    public void setNmUsuario(String nmUsuario) {
        this.nmUsuario = nmUsuario;
    }

    public String getNrCpf() {
        return this.nrCpf;
    }

    public void setNrCpf(String nrCpf) {
        this.nrCpf = nrCpf;
    }

    public String getNrTelefone() {
        return this.nrTelefone;
    }

    public void setNrTelefone(String nrTelefone) {
        this.nrTelefone = nrTelefone;
    }

    public String getStUsuario() {
        return this.stUsuario;
    }

    public void setStUsuario(String stUsuario) {
        this.stUsuario = stUsuario;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNmSenhaAtual() {
        return nmSenhaAtual;
    }

    public void setNmSenhaAtual(String nmSenhaAtual) {
        this.nmSenhaAtual = nmSenhaAtual;
    }

    public String getNmNovaSenha() {
        return nmNovaSenha;
    }

    public void setNmNovaSenha(String nmNovaSenha) {
        this.nmNovaSenha = nmNovaSenha;
    }

    public String getNmConfirmacaoNovaSenha() {
        return nmConfirmacaoNovaSenha;
    }

    public void setNmConfirmacaoNovaSenha(String nmConfirmacaoNovaSenha) {
        this.nmConfirmacaoNovaSenha = nmConfirmacaoNovaSenha;
    }

    /**
     * Metodos para evitar o erro de serialização de objetos que não implementão
     * a interface de serialização.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }
}