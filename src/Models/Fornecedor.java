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
    private Cidade cidade;
    private String email;
    private String razaosocial;
    private String cep;
    private String telefonecontato;

    DALFornecedor dal = new DALFornecedor();

    public Fornecedor() {
    }

    public Fornecedor(int codigo, String nomefantasia, String cnpj, String ativo, String endereco, String bairro, String numero, Cidade cidade, String email, String razaosocial, String cep, String telefonecontato) {
        this.codigo = codigo;
        this.nomefantasia = nomefantasia;
        this.cnpj = cnpj;
        this.ativo = ativo;
        this.endereco = endereco;
        this.bairro = bairro;
        this.numero = numero;
        this.cidade = cidade;
        this.email = email;
        this.razaosocial = razaosocial;
        this.cep = cep;
        this.telefonecontato = telefonecontato;
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

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
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

    public boolean salvar(Fornecedor f) {
        return dal.salvar(f);
    }

    public boolean alterar(Fornecedor f) {
         return dal.alterar(f);
    }

    public boolean salvarCategoria(int codfor, int codcat) {
        return dal.salvarCategoria(codfor, codcat);
    }

    public boolean apagarCats(int cod) {
        return dal.apagarCats(cod);
    }

    public boolean apagar(int codigo) {
        return dal.apagar(codigo);
    }

    public Fornecedor get(int codigo) {
        return dal.get(codigo);
    }

    public List<Fornecedor> get(String filtro) {
        return dal.get(filtro);
    }

    public List<Categoria> getCategorias(int codfor) {
        return dal.getCategorias(codfor);
    }

    @Override
    public String toString() {
        return nomefantasia;
    }

}
