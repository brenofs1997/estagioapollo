/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import CamadaAcessoDados.DALTipoDespesa;
import com.jfoenix.controls.JFXTextField;
import java.util.List;

public class TipoDespesa {

    private int codigo;
    private String descricao;
    DALTipoDespesa dal = new DALTipoDespesa();

    public TipoDespesa() {
    }

    public TipoDespesa(int codigo, String descricao) {
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

    public boolean gravar(TipoDespesa tip) {

        return dal.salvar(tip);
    }
     public List<TipoDespesa> get(String filtro){
        return dal.get(filtro);
    }
    public TipoDespesa get(int filtro){
        return dal.get(filtro);
    }
    @Override
    public String toString() {
        return descricao;
    }

    public boolean alterar(TipoDespesa tp) {
       return dal.alterar(tp);
    }
    public boolean apagar(int cod)
    {
        return dal.apagar(cod);
    }

}
