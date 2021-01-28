/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamadaAcessoDados;

import Models.Categoria;
import Models.Produto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulo
 */
public class DALProduto {
    public boolean salvar(Produto d) {
        String sql = "insert into produto (descricao,preco,qtde,ativo,cod_categoria) values('#1',#2,#3,'#4',#5)";
        sql = sql.replace("#1", d.getDescricao());
        sql = sql.replace("#2",""+ d.getPreco());
        sql = sql.replace("#3", ""+ d.getQtde());
        sql = sql.replace("#4",""+ d.isAtivo());
        sql = sql.replace("#5",""+ d.getCategoria().getCodigo());
 
        return Banco.getCon().manipular(sql);
    }

    public boolean alterar(Produto d) {
        String sql = "update produto set descricao='#1',preco=#2,qtde=#3,ativo='#4',cod_categoria=#5 where codigo=" + d.getCodigo();
        sql = sql.replace("#1", d.getDescricao());
        sql = sql.replace("#2",""+ d.getPreco());
        sql = sql.replace("#3", ""+ d.getQtde());
        sql = sql.replace("#4",""+ d.isAtivo());
        sql = sql.replace("#5",""+ d.getCategoria().getCodigo());
        
       
        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(int codigo) {
        
        return Banco.getCon().manipular("delete from produto where codigo=" + codigo);
    }

    public List<Produto> get(String filtro) {
        List<Produto> des = new ArrayList();
        Categoria cat = new Categoria();
        String sql = "select * from produto";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        sql+=" order by descricao";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                cat=cat.get(rs.getInt("cod_categoria"));
                des.add(new Produto(rs.getInt("codigo"), rs.getString("descricao"), rs.getDouble("preco"),rs.getInt("qtde"),rs.getBoolean("ativo"),cat));
            }

        } catch (SQLException ex) {

        }
        return des;
    }
    public List<Produto> getCategoriasFornecedor(int codigo) {
        List<Produto> des = new ArrayList();
        Categoria cat = new Categoria();
        String sql = "select p.* from produto p,categoria c,categorias_fornecedor cf "
                + "where p.cod_categoria=c.codigo and cf.cod_categoria=c.codigo and cf.cod_fornecedor=" + codigo+" and p.ativo=true";
        
        sql+=" order by descricao";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                cat=cat.get(rs.getInt("cod_categoria"));
                des.add(new Produto(rs.getInt("codigo"), rs.getString("descricao"), rs.getDouble("preco"),rs.getInt("qtde"),rs.getBoolean("ativo"),cat));
            }

        } catch (SQLException ex) {

        }
        return des;
    }

    public static Produto get(int codigo) {
        Categoria cat = new Categoria();
        String SQL = "select * from Produto where codigo = " + codigo;
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {
                 cat=cat.get(rs.getInt("cod_categoria"));
                return  new Produto(rs.getInt("codigo"), rs.getString("descricao"), rs.getDouble("preco"),rs.getInt("qtde"),rs.getBoolean("ativo"),cat);
            }
        } catch (SQLException e) {
        }
        return null;
    }
}
