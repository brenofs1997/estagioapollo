/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamadaAcessoDados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Models.Cidade;

public class DALCidade {

    public boolean gravar(Cidade c) {
        String sql = "insert into cidade (cid_nome, est_cod) values ('#1',#2)";
        sql = sql.replace("#1", c.getCid_nome());
        sql = sql.replace("#2", "" + c.getEst_sgl());
        return Banco.getCon().manipular(sql);
    }

    public Cidade get(int cod) {
        Cidade c = null;
        DALEstado dale = new DALEstado();
        ResultSet rs = Banco.getCon().consultar("select * from cidade where cid_cod=" + cod);
        try {
            if (rs.next()) {
                c = new Cidade(rs.getInt("cid_cod"), rs.getInt("est_cod"), rs.getString("cid_nome"));
            }
        } catch (Exception e) {
            System.out.println("erro ao pesquisar");
        }
        return c;
    }

    public List<Cidade> get(String filtro) {
        List<Cidade> list = new ArrayList();
        String sql = "select * from cidade";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                list.add(new Cidade(rs.getInt("cid_cod"), rs.getInt("est_cod"), rs.getString("cid_nome")));
            }
        } catch (Exception e) {
            System.out.println("erro ao pesquisar");
        }
        return list;
    }

    public Cidade getPorNome(String filtro) {
        Cidade list = new Cidade();
        String sql = "select * from cidade where cid_nome like '" + filtro + "'";

        try (Connection conn = Conexao.open()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(sql)) {
                    if (rs.next()) {
                        list = new Cidade(rs.getInt("cid_cod"), rs.getInt("est_cod"), rs.getString("cid_nome"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(""
            //        DALNivelFuncionario.class.getName()
            )
                    .log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Cidade> getCidUf(int coduf) {
        List<Cidade> list = new ArrayList();
        String sql = "select c.* from cidade c where c.est_cod = " + coduf;

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                list.add(new Cidade(rs.getInt("cid_cod"), rs.getInt("est_cod"), rs.getString("cid_nome")));
            }
        } catch (Exception e) {
            System.out.println("erro ao pesquisar");
        }
        return list;
    }

    public List<Cidade> getPorNomeList(String cid) {
       List<Cidade> list = new ArrayList();
        String sql = "select c.* from cidade c where c.cid_nome ilike  '%" + cid+"%' ";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                list.add(new Cidade(rs.getInt("cid_cod"), rs.getInt("est_cod"), rs.getString("cid_nome")));
            }
        } catch (Exception e) {
            System.out.println("erro ao pesquisar");
        }
        return list;
    }

}
