package CamadaAcessoDados;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Models.Estado;

public class DALEstado {

    public boolean gravar(Estado e) {
        String sql = "insert into estado (est_cod,est_nome, est_sgl) values (#0,'#1','#2')";
        sql = sql.replace("#0", "" + (Banco.getCon().getMaxPK("estado", "est_codigo") + 1));
        sql = sql.replace("#1", e.getNome());
        sql = sql.replace("#2", e.getSigla());
        return Banco.getCon().manipular(sql);
    }

    public boolean alterar(Estado e) {
        String sql = "update estado set est_nome='#1', est_sgl='#2' where est_cod=#3";
        sql = sql.replace("#1", e.getNome());
        sql = sql.replace("#2", e.getSigla());
        sql = sql.replace("#3", "" + e.getCod());
        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(Estado e) {
        return Banco.getCon().manipular("delete from estado where est_codigo=" + e.getCod());
    }

    public Estado get(int cod) {
        Estado e = null;
        ResultSet rs = Banco.getCon().consultar("select * from estado where est_codigo=" + cod);
        try {
            if (rs.next()) {
                e = new Estado(rs.getInt("est_cod"), rs.getString("est_nome"),
                        rs.getString("est_sgl"));
            }
        } catch (Exception ex) {
            System.out.println("erro ao pesquisar");
        }
        return e;
    }

    public List<Estado> get(String filtro) {
        List<Estado> list = new ArrayList();

        String sql = "select * from estado";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                list.add(new Estado(rs.getInt("est_cod"), rs.getString("est_nome"),
                        rs.getString("est_sgl")));
            }
        } catch (Exception e) {
            System.out.println("erro ao pesquisar");
        }
        return list;
    }

    public Estado getPorCid(int cid_cod) {
       Estado e = null;
        ResultSet rs = Banco.getCon().consultar("select e.* from estado e,cidade c where e.est_cod= c.est_cod and c.cid_cod=" + cid_cod);
        try {
            if (rs.next()) {
                e = new Estado(rs.getInt("est_cod"), rs.getString("est_nome"),
                        rs.getString("est_sgl"));
            }
        } catch (Exception ex) {
            System.out.println("erro ao pesquisar");
        }
        return e;
    }

}
