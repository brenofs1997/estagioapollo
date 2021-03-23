/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import CamadaAcessoDados.DALCondPgto;
import java.util.List;

/**
 *
 * @author paulo
 */
public class CondicaoPagamento {

    private int codigo;
    private String descricao;
    DALCondPgto dal = new DALCondPgto();

    public CondicaoPagamento() {
    }

    public CondicaoPagamento(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public CondicaoPagamento(int codigo) {
        this.codigo = codigo;

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

    public boolean gravar(CondicaoPagamento c) {

        return dal.salvar(c);
    }

    public List<CondicaoPagamento> get(String filtro) {
        return dal.get(filtro);
    }

    public CondicaoPagamento get(int filtro) {
        return dal.get(filtro);
    }

    public CondicaoPagamento getC(String filtro) {
        return dal.getC(filtro);
    }

    @Override
    public String toString() {
        return descricao;
    }

    public boolean alterar(CondicaoPagamento c) {
        return dal.alterar(c);
    }

    public boolean apagar(int cod) {
        return dal.apagar(cod);
    }
}
