/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import CamadaAcessoDados.DALCompra;

/**
 *
 * @author Paulo
 */
public class itens_Compra {

    private int quantidade;

    private double unitario;
    
    private double total;

    private Compra compra;

    private Produto produto;
    DALCompra dal = new DALCompra();
    public itens_Compra() {
        
    }

    public itens_Compra(int quantidade, double unitario, double total, Compra compra, Produto produto) {
        this.quantidade = quantidade;
        this.unitario = unitario;
        this.total = total;
        this.compra = compra;
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

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
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
