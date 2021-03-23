/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamadaAcessoDados;

import Models.Produto;
import Models.Venda;
import Models.itens_Venda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulo
 */
public class DALVenda {

    public boolean apagarItens(int cod) {
        return Banco.getCon().manipular("delete from item_venda where codigo_venda = " + cod);
    }

    public boolean salvarItens(itens_Venda venda) {
        int codVenda = 0;
        codVenda = Banco.getCon().getMaxPK("venda", "codigo");
        String sql = "insert into item_venda(codigo_produto,qtde,unitario,total,codigo_venda) "
                + "values(#A,#B,#C,#D,#E)";
        sql = sql.replace("#A", "" + venda.getProduto().getCodigo());
        sql = sql.replace("#B", "" + venda.getQuantidade());
        sql = sql.replace("#C", "" + venda.getUnitario());
        sql = sql.replace("#D", "" + venda.getTotal());
        sql = sql.replace("#E", "" + codVenda);

        return Banco.getCon().manipular(sql);
    }

    public boolean salvar(Venda v) {

        String sql = "insert into venda(emissao,total,cod_cliente,cod_condpagto) "
                + "values('#A',#B,#C,#E)";
        sql = sql.replace("#A", "" + v.getEmissaoDate());
        sql = sql.replace("#B", "" + v.getTotal());
        if (v.getCliente() != null) {
            sql = sql.replace("#C", "" + v.getCliente().getCodigo());
        } else {
            sql = sql.replace("#C", "null");
        }
        sql = sql.replace("#E", "" + v.getCond_pgto().getCodigo());

        return Banco.getCon().manipular(sql);
    }

    public List<Venda> get(String filtro) {
        List<Venda> cp = new ArrayList();
        Venda venda = new Venda();
        DALCliente dalc = new DALCliente();
        DALCondPgto dalcd = new DALCondPgto();
        DALCompra dalcp = new DALCompra();
        String sql = "select * from venda ";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        sql += " order by emissao";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                venda = new Venda(
                        rs.getInt("codigo"),
                        rs.getDate("emissao"),
                        dalc.get(rs.getInt("cod_cliente")),
                        dalcd.get(rs.getInt("cod_condpagto")),
                        rs.getDouble("total")
                );
                cp.add(venda);
            }
        } catch (SQLException ex) {

        }
        return cp;
    }

    public List<itens_Venda> getItens(int codigo) {
        List<itens_Venda> cp = new ArrayList();
        itens_Venda item = new itens_Venda();
        String sql = "select * from item_venda  where codigo_venda=" + codigo;
        DALProduto dalp = new DALProduto();
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                cp.add(new itens_Venda(rs.getInt("qtde"), rs.getDouble("unitario"), rs.getDouble("total"),
                        new Venda(rs.getInt("codigo_venda")), dalp.get(rs.getInt("codigo_produto"))));
            }
        } catch (SQLException ex) {

        }
        return cp;
    }

    public boolean apagar(int codigo) {
        return Banco.getCon().manipular("delete from venda where codigo = " + codigo);

    }

    public boolean verificarParcelaPaga(int codigo) {
        String sql = "select codigo from contas_receber where cod_venda = " + codigo + " and data_pago is not null";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        }

        return false;

    }

    public boolean alterar(Venda v) {
        String sql = "update venda set emissao='#A',total=#B,cod_cliente=#C,cod_condpagto=#D  where codigo= " + v.getCodigo();
        sql = sql.replace("#A", "" + v.getEmissaoDate());
        sql = sql.replace("#B", "" + v.getTotal());
        if (v.getCliente() != null) {
            sql = sql.replace("#C", "" + v.getCliente().getCodigo());
        } else {
            sql = sql.replace("#C", "null");
        }
        sql = sql.replace("#D", "" + v.getCond_pgto().getCodigo());

        return Banco.getCon().manipular(sql);
    }
}
