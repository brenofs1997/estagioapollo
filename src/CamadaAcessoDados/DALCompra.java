/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamadaAcessoDados;

import Models.Categoria;
import Models.Cidade;
import Models.Compra;
import Models.CondicaoPagamento;
import Models.ContasPagar;
import Models.Fornecedor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulo
 */
public class DALCompra {

    public Compra get(int cod) {
        String SQL = "select * from entrada_de_produtos where codigo = " + cod;
        Fornecedor f = new Fornecedor();
        CondicaoPagamento cd = new CondicaoPagamento();
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {
                  return new Compra(
                        rs.getInt("codigo"),
                        rs.getDate("emissao"),
                        f.get(rs.getInt("codigo_fornecedor")),
                         cd.get(rs.getInt("codigo_condpagto")),
                         rs.getDouble("total")                        
                );
              
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public boolean apagar(int cod) {
        return Banco.getCon().manipular("delete from entrada_de_produtos where codigo = " + cod);
    }

    public boolean salvar(Compra c) {

        String sql = "insert into entrada_de_produtos(emissao,codigo_fornecedor,codigo_condpagto,total) "
                + "values('#A',#B,#C,#D)";
        sql = sql.replace("#A", "" + c.getEmissao());
        sql = sql.replace("#B", "" + c.getFornecedor().getCodigo());
        sql = sql.replace("#C", "" + c.getCond_pgto().getCodigo());
        sql = sql.replace("#D", "" + c.getTotal());

        return Banco.getCon().manipular(sql);
    }

    public boolean alterar(Compra c) {
        String sql = "update entrada_de_produtos set emissao='#A',codigo_fornecedor=#B,cond_pgto=#C,total=#D where codigo=" + c.getCodigo();
        sql = sql.replace("#A", "" + c.getEmissao());
        sql = sql.replace("#B", "" + c.getFornecedor().getCodigo());
        sql = sql.replace("#C", "" + c.getCond_pgto().getCodigo());
        sql = sql.replace("#D", "" + c.getTotal());

        return Banco.getCon().manipular(sql);

    }

    public List<Compra> get(String filtro) {
        List<Compra> cp = new ArrayList();
        Compra compra = new Compra();
        DALFornecedor dalfr = new DALFornecedor();
        DALCondPgto dalcd = new DALCondPgto();
        DALCompra dalcp = new DALCompra();
        String sql = "select * from entrada_de_produtos ";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                compra = new Compra(
                        rs.getInt("codigo"),
                        rs.getDate("emissao"),
                        dalfr.get(rs.getInt("codigo_fornecedor")),
                         dalcd.get(rs.getInt("codigo_condpagto")),
                         rs.getDouble("total")                        
                );
                cp.add(compra);
            }
        } catch (SQLException ex) {

        }
        return cp;
    }
}
