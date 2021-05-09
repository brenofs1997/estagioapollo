package Models;

import CamadaAcessoDados.DALFuncionario;
import Models.Cidade;
import Models.NivelFuncionario;
import java.util.List;

public class Funcionario {

    private int codigo;
    private String nome;
    private String cpf;
    private String endereco;
    private String numero;
    private String telefone;
    private String email;
    private String login;
    private String senha;
    private String ativo;
    private String primeiro_acesso;
    private Cidade Cidade;
    private NivelFuncionario nivel;
    private String bairro;
    private String cep;

    public Funcionario() {
    }

    public Funcionario(int codigo) {
        this.codigo = codigo;
    }

    public Funcionario(int codigo, String nome, String cpf, String endereco, String numero, String telefone, String email, String login, String senha, String ativo, String primeiro_acesso, Cidade Cidade, NivelFuncionario nivel, String bairro, String cep) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.numero = numero;
        this.telefone = telefone;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.ativo = ativo;
        this.primeiro_acesso = primeiro_acesso;
        this.Cidade = Cidade;
        this.nivel = nivel;
        this.bairro = bairro;
        this.cep = cep;
    }

    public Funcionario(int codigo, String nome, String cpf, String ativo, NivelFuncionario nivel) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.ativo = ativo;
        this.nivel = nivel;
    }

    public Funcionario(int codigo, String login, String senha) {
        this.codigo = codigo;
        this.login = login;
        this.senha = senha;
    }

    public Funcionario(int codigo, String login, String senha, NivelFuncionario nivel) {
        this.codigo = codigo;
        this.login = login;
        this.senha = senha;
        this.nivel = nivel;
    }

    public Funcionario(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getPrimeiro_acesso() {
        return primeiro_acesso;
    }

    public void setPrimeiro_acesso(String primeiro_acesso) {
        this.primeiro_acesso = primeiro_acesso;
    }

    public Cidade getCidade() {
        return Cidade;
    }

    public void setCidade(Cidade Cidade) {
        this.Cidade = Cidade;
    }

    public NivelFuncionario getNivel() {
        return nivel;
    }

    public void setNivel(NivelFuncionario nivel) {
        this.nivel = nivel;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return nome;
    }

    public boolean salvar(Funcionario f) {
        DALFuncionario dal = new DALFuncionario();
        return dal.salvar(f);
    }

    public boolean alterar(Funcionario f) {
        DALFuncionario dal = new DALFuncionario();
        return dal.alterar(f);
    }

    public boolean apagar(int cod) {
        DALFuncionario dal = new DALFuncionario();
        return dal.apagar(cod);
    }

    public Funcionario get(int cod) {
        DALFuncionario dal = new DALFuncionario();
        return dal.get(cod);
    }
    public List<Funcionario> get(String filtro) {
        DALFuncionario dal = new DALFuncionario();
        return dal.get(filtro);
    }
    public List<Funcionario> getDiferenteFunc(int cod) {
        DALFuncionario dal = new DALFuncionario();
        return dal.getDiferenteFunc(cod);
    }
     public List<Funcionario> getFuncPorNivel(int cod) {
        DALFuncionario dal = new DALFuncionario();
        return dal.getFuncPorNivel(cod);
    }
    public Funcionario getLoginF(String usuario,String senha) {
        DALFuncionario dal = new DALFuncionario();
        return dal.getLogin(usuario, senha);
    }
    public Funcionario getloginF(String usuario) {
        DALFuncionario dal = new DALFuncionario();
        return dal.getLogin(usuario);
    }

    public Funcionario getporSenha(String senha) {
        DALFuncionario dal = new DALFuncionario();
        return dal.getporSenha(senha);
    }
    
}
