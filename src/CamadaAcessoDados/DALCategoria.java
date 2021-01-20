/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamadaAcessoDados;

import Models.Categoria;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulo
 */
public class DALCategoria {
     
    public boolean salvar(Categoria d) {
        String sql = "insert into categoria (descricao) values('#1')";
        sql = sql.replace("#1", d.getDescricao());
 
        return Banco.getCon().manipular(sql);
    }

    public boolean alterar(Categoria d) {
        String sql = "update categoria set descricao='#1' where codigo=" + d.getCodigo();
        sql = sql.replace("#1", d.getDescricao());
       
        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(int codigo) {
        
        return Banco.getCon().manipular("delete from categoria where codigo=" + codigo);
    }

    public List<Categoria> get(String filtro) {
        List<Categoria> des = new ArrayList();

        String sql = "select codigo,descricao from categoria";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        sql+=" order by descricao";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                des.add(new Categoria(rs.getInt("codigo"), rs.getString("descricao")));
            }

        } catch (SQLException ex) {

        }
        return des;
    }

    public static Categoria get(int codigo) {
        String SQL = "select * from categoria where codigo = " + codigo;
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {
                return new Categoria(rs.getInt("codigo"), rs.getString("descricao"));
            }
        } catch (SQLException e) {
        }
        return null;
    }
    
}
