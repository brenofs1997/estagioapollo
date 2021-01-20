/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamadaAcessoDados;

import Models.TipoDespesa;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulo
 */
public class DALTipoDespesa {
    
    public boolean salvar(TipoDespesa d) {
        String sql = "insert into tipo_despesa (descricao) values('#1')";
        sql = sql.replace("#1", d.getDescricao());
 
        return Banco.getCon().manipular(sql);
    }

    public boolean alterar(TipoDespesa d) {
        String sql = "update tipo_despesa set descricao='#1' where codigo=" + d.getCodigo();
        sql = sql.replace("#1", d.getDescricao());
       
        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(int codigo) {
        return Banco.getCon().manipular("delete from tipo_despesa where codigo=" + codigo);
    }

    public List<TipoDespesa> get(String filtro) {
        List<TipoDespesa> des = new ArrayList();

        String sql = "select codigo,descricao from tipo_despesa";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        sql+=" order by descricao";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                des.add(new TipoDespesa(rs.getInt("codigo"), rs.getString("descricao")));
            }

        } catch (SQLException ex) {

        }
        return des;
    }

    public static TipoDespesa get(int codigo) {
        String SQL = "select * from tipo_despesa where codigo = " + codigo;
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {
                return new TipoDespesa(rs.getInt("codigo"), rs.getString("descricao"));
            }
        } catch (SQLException e) {
        }
        return null;
    }
    
}
