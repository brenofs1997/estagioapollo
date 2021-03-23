/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import CamadaAcessoDados.DALCliente;
import java.util.Date;
import java.util.List;

/**
 *
 * @author paulo
 */
public class Cliente {
     private int codigo;
    private String nome;
    private Date data_cadastro;
    private String cpf;
    private String endereco;
    private String bairro ;
    private String email;
    private double limite_fiado;
    private String cep;
    private String telefone;
    private Cidade cidade;
    private boolean ativo;
    private String numero;
    private double saldo_devedor;
    DALCliente dal =new DALCliente();
    public Cliente() {
    }

    public Cliente(int codigo) {
        this.codigo = codigo;
    }

    public Cliente(int codigo, String nome, Date data_cadastro, String cpf, String endereco, String bairro, String email, double limite_fiado, String cep, String telefone, Cidade cidade, boolean ativo, String numero, double saldo_devedor) {
        this.codigo = codigo;
        this.nome = nome;
        this.data_cadastro = data_cadastro;
        this.cpf = cpf;
        this.endereco = endereco;
        this.bairro = bairro;
        this.email = email;
        this.limite_fiado = limite_fiado;
        this.cep = cep;
        this.telefone = telefone;
        this.cidade = cidade;
        this.ativo = ativo;
        this.numero = numero;
        this.saldo_devedor = saldo_devedor;
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

    public Date getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(Date data_cadastro) {
        this.data_cadastro = data_cadastro;
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

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLimite_fiado() {
        return limite_fiado;
    }

    public void setLimite_fiado(double limite_fiado) {
        this.limite_fiado = limite_fiado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getSaldo_devedor() {
        return saldo_devedor;
    }

    public void setSaldo_devedor(double saldo_devedor) {
        this.saldo_devedor = saldo_devedor;
    }

    @Override
    public String toString() {
        return nome;
    }
    public boolean gravar(Cliente c) {

        return dal.salvar(c);
    }
     public List<Cliente> get(String filtro){
        return dal.get(filtro);
    }
    public Cliente get(int filtro){
        return dal.get(filtro);
    }
   

    public boolean alterar(Cliente c) {
       return dal.alterar(c);
    }
    public boolean apagar(int cod)
    {
        return dal.apagar(cod);
    }
    
}
