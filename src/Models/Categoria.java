/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import CamadaAcessoDados.DALCategoria;
import java.util.List;

/**
 *
 * @author paulo
 */
public class Categoria {
     private int codigo;
    private String descricao;
    DALCategoria dal = new DALCategoria();

    public Categoria(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Categoria() {
        
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
    public boolean gravar(Categoria c) {

        return dal.salvar(c);
    }
     public List<Categoria> get(String filtro){
        return dal.get(filtro);
    }
    public Categoria get(int filtro){
        return dal.get(filtro);
    }
    @Override
    public String toString() {
        return descricao;
    }

    public boolean alterar(Categoria c) {
       return dal.alterar(c);
    }
    public boolean apagar(int cod)
    {
        return dal.apagar(cod);
    }
    
}
