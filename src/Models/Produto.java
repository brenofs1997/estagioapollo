/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import CamadaAcessoDados.DALProduto;
import java.util.List;

/**
 *
 * @author paulo
 */
public class Produto {
   private int codigo;
    private String descricao;
    private double preco;
    private int qtde;
    private boolean ativo;
    private Categoria categoria;
    DALProduto dal =new DALProduto();
    public Produto() {
    }

    public Produto(int codigo) {
        this.codigo = codigo;
    }
    
    public Produto(int codigo, String descricao, double preco, int qtde, boolean ativo, Categoria categoria) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
        this.qtde = qtde;
        this.ativo = ativo;
        this.categoria = categoria;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public List<Produto> get(String filtro){
        return dal.get(filtro);
    }
    public List<Produto> getCategoriasFornecedor(int codigoFornecedor){
        return dal.getCategoriasFornecedor(codigoFornecedor);
    }
    public Produto get(int codigo){
        return dal.get(codigo);
    }
    @Override
    public String toString() {
        return descricao;
    }

    public boolean apagar(int cod)
    {
        return dal.apagar(cod);
    }

    public boolean alterar(Produto aux) {
       return dal.alterar(aux);
    }

    public boolean salvar(Produto p) {
       return dal.salvar(p);

    }
   
    
}
