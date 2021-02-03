/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import CamadaAcessoDados.DALCompra;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author paulo
 */
public class Compra {

    private int codigo;
    private Date emissao;
    private Fornecedor fornecedor;
    private CondicaoPagamento cond_pgto;
    private double total;

    DALCompra dal = new DALCompra();

    public Compra() {
    }

    public Compra(int codigo) {
        this.codigo = codigo;
    }

    public Compra(int codigo, Date emissao, Fornecedor fornecedor, CondicaoPagamento cond_pgto, double total) {
        this.codigo = codigo;
        this.emissao = emissao;
        this.fornecedor = fornecedor;
        this.cond_pgto = cond_pgto;
        this.total = total;
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

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
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

    public List<Compra> get(String filtro) {
        return dal.get(filtro);
    }

    public Compra get(int codigo) {
        return dal.get(codigo);
    }

    public boolean apagar(int cod) {
        return dal.apagar(cod);
    }

    public boolean alterar(Compra c) {

        return dal.salvar(c);
    }

    public boolean salvar(Compra cp) {
        return dal.salvar(cp);

    }

    public boolean salvarItens(List<itens_Compra> itensCompra) {
        boolean aux = false;
        for (itens_Compra compra : itensCompra) {
            if (dal.salvarItens(compra)) {
                aux = true;
            }

        }

        return aux;
    }

    public boolean verificarParcelaPaga(Compra compra) {
        return false;
    }

   public List<itens_Compra> getProdutos(int codigo) {
         return dal.getItens(codigo);
    }

    

}
