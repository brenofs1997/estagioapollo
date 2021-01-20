/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamadaAcessoDados;

import Models.CondicaoPagamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulo
 */
public class DALCondPgto {
    

    public boolean salvar(CondicaoPagamento c) {
         String sql = "insert into condicao_pgto (descricao) values('#1')";
        sql = sql.replace("#1", c.getDescricao());
 
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(CondicaoPagamento c) {
       String sql = "update condicao_pgto set descricao='#1' where codigo=" + c.getCodigo();
        sql = sql.replace("#1", c.getDescricao());
       
        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(int cod) {
        return Banco.getCon().manipular("delete from condicao_pgto where codigo=" + cod);
    }
    public List<CondicaoPagamento> get(String filtro) {
         List<CondicaoPagamento> cp = new ArrayList();

        String sql = "select codigo,descricao from condicao_pgto";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        sql+=" order by descricao";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                cp.add(new CondicaoPagamento(rs.getInt("codigo"), rs.getString("descricao")));
            }

        } catch (SQLException ex) {

        }
        return cp;
    }
    public CondicaoPagamento get(int cod) {
        String SQL = "select * from condicao_pgto where codigo = " + cod;
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {
                return new CondicaoPagamento(rs.getInt("codigo"), rs.getString("descricao"));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    
}
