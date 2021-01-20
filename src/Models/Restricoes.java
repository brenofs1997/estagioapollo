/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import CamadaAcessoDados.DALRestricoes;
import java.util.List;

/**
 *
 * @author paulo
 */
public class Restricoes {

    private int codigo;
    private String descricao;

    public Restricoes() {
    }

    public Restricoes(int codigo) {
        this.codigo = codigo;
    }

    public Restricoes(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
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

    public List<Restricoes> get() {
        DALRestricoes dal = new DALRestricoes();
        return dal.get();
    }

    public Restricoes get(int codigo) {
        DALRestricoes dal = new DALRestricoes();
        return dal.get(codigo);
    }

    @Override
    public String toString() {
        return descricao;
    }

}
