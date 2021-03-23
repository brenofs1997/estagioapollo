/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import CamadaAcessoDados.DALVenda;

/**
 *
 * @author Paulo
 */
public class itens_Venda {

    private int quantidade;

    private double unitario;

    private double total;

    private Venda venda;

    private Produto produto;
    DALVenda dal = new DALVenda();

    public itens_Venda() {

    }

    public itens_Venda(int quantidade, double unitario, double total, Venda venda, Produto produto) {
        this.quantidade = quantidade;
        this.unitario = unitario;
        this.total = total;
        this.venda = venda;
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getUnitario() {
        return unitario;
    }

    public void setUnitario(double unitario) {
        this.unitario = unitario;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public boolean apagar(int cod) {
        return dal.apagarItens(cod);
    }

}
