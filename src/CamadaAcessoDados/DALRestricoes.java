/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamadaAcessoDados;

import Models.Restricoes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulo
 */
public class DALRestricoes {

    public List<Restricoes> get() {
        List<Restricoes> res = new ArrayList();

        String sql = "select codigo,descricao from restricoes order by descricao";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                res.add(new Restricoes(rs.getInt("codigo"), rs.getString("descricao")));
            }

        } catch (SQLException ex) {

        }
        return res;
    }

    public Restricoes get(int codigo) {
        String sql = "select codigo,descricao from restricoes where codigo=" + codigo;

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                return new Restricoes(rs.getInt("codigo"), rs.getString("descricao"));
            }

        } catch (SQLException ex) {

        }
        return null;
    }
}
