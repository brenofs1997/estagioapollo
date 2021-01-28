/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import CamadaAcessoDados.DALFornecedor;
import java.util.List;

/**
 *
 * @author paulo
 */
public class Fornecedor {
    
    private int codigo;
    private String nomefantasia;
    private String cnpj;
    private String ativo;
    private String endereco;
    private String bairro;
    private String numero;
    private String email;
    private String razaosocial;
    private String cep;
    private String telefonecontato;
   
    private Cidade Cidade;
    DALFornecedor dal= new DALFornecedor();

    public Fornecedor() {
    }

    public Fornecedor(int codigo, String nomefantasia, String cnpj, String ativo, String endereco, String bairro, String numero, String email, String razaosocial, String cep, String telefonecontato, Cidade Cidade) {
        this.codigo = codigo;
        this.nomefantasia = nomefantasia;
        this.cnpj = cnpj;
        this.ativo = ativo;
        this.endereco = endereco;
        this.bairro = bairro;
        this.numero = numero;
        this.email = email;
        this.razaosocial = razaosocial;
        this.cep = cep;
        this.telefonecontato = telefonecontato;
       
        this.Cidade = Cidade;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNomefantasia() {
        return nomefantasia;
    }

    public void setNomefantasia(String nomefantasia) {
        this.nomefantasia = nomefantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRazaosocial() {
        return razaosocial;
    }

    public void setRazaosocial(String razaosocial) {
        this.razaosocial = razaosocial;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefonecontato() {
        return telefonecontato;
    }

    public void setTelefonecontato(String telefonecontato) {
        this.telefonecontato = telefonecontato;
    }

    public Cidade getCidade() {
        return Cidade;
    }

    public void setCidade(Cidade Cidade) {
        this.Cidade = Cidade;
    }
     public Fornecedor get(int codigo) {
        return dal.get(codigo);
    }
       public List<Fornecedor> get(String filtro) {
        return dal.get(filtro);
    }

    @Override
    public String toString() {
        return nomefantasia;
    }
      
    
   
    
}
