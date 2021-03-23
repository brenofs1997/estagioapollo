/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import CamadaAcessoDados.DALVenda;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author paulo
 */
public class Venda {

    private int codigo;
    private Date emissao;
    private Cliente cliente;
    private CondicaoPagamento cond_pgto;
    private double total;
    DALVenda dal = new DALVenda();

    public Venda() {
    }

    public Venda(int codigo, Date emissao, Cliente cliente, CondicaoPagamento cond_pgto, double total) {
        this.codigo = codigo;
        this.emissao = emissao;
        this.cliente = cliente;
        this.cond_pgto = cond_pgto;
        this.total = total;
    }

    public Venda(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getEmissao() throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(emissao);

    }

    public Date getEmissaoDate() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public CondicaoPagamento getCond_pgto() {
        return cond_pgto;
    }

    public void setCond_pgto(CondicaoPagamento cond_pgto) {
        this.cond_pgto = cond_pgto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean salvar(Venda v) {
        return dal.salvar(v);

    }

    public boolean salvarItens(List<itens_Venda> itensVenda) {
        boolean aux = false;
        for (itens_Venda venda : itensVenda) {
            if (dal.salvarItens(venda)) {
                aux = true;
            }

        }

        return aux;
    }
    
     public boolean alterar(Venda v) {
         return dal.alterar(v);
    }


    public List<Venda> get(String filtro) {
        return dal.get(filtro);
    }

    public boolean verificarParcelaPaga(Venda venda) {
        return dal.verificarParcelaPaga(venda.getCodigo());
    }

    public List<itens_Venda> getProdutos(int codigo) {
        return dal.getItens(codigo);
    }

    public boolean apagar(int codigo) {
        return dal.apagar(codigo);
    }

   
}
