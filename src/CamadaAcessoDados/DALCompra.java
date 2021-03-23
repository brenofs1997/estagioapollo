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
import Models.Produto;
import Models.itens_Compra;
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
     public boolean salvarItens(itens_Compra c) {
         int codCompra=0;
         codCompra = Banco.getCon().getMaxPK("entrada_de_produtos", "codigo");
        String sql = "insert into item_compra(codigo_compra,codigo_produto,qtde,unitario,total) "
                + "values(#A,#B,#C,#D,#E)";
        sql = sql.replace("#A", "" + codCompra);
        sql = sql.replace("#B", "" + c.getProduto().getCodigo());
        sql = sql.replace("#C", "" + c.getQuantidade());
        sql = sql.replace("#D", "" + c.getUnitario());
        sql = sql.replace("#E", "" + c.getTotal());

        return Banco.getCon().manipular(sql);
    }
     public boolean apagarItens(int cod) {
        return Banco.getCon().manipular("delete from item_compra where codigo_compra = " + cod);
    }
    public boolean salvar(Compra c) {

        String sql = "insert into entrada_de_produtos(emissao,codigo_fornecedor,codigo_condpagto,total) "
                + "values('#A',#B,#C,#D)";
        sql = sql.replace("#A", "" + c.getEmissaoDate());
        sql = sql.replace("#B", "" + c.getFornecedor().getCodigo());
        sql = sql.replace("#C", "" + c.getCond_pgto().getCodigo());
        sql = sql.replace("#D", "" + c.getTotal());

        return Banco.getCon().manipular(sql);
    }

    public boolean alterar(Compra c) {
        String sql = "update entrada_de_produtos set emissao='#A',codigo_fornecedor=#B,codigo_condpagto=#C,total=#D where codigo=" + c.getCodigo();
        sql = sql.replace("#A", "" + c.getEmissaoDate());
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
        sql += " order by emissao";
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

    public List<itens_Compra> getItens(int codCompra) {
        List<itens_Compra> cp = new ArrayList();
        itens_Compra item = new itens_Compra();
        DALFornecedor dalfr = new DALFornecedor();
        DALCondPgto dalcd = new DALCondPgto();
        DALCompra dalcp = new DALCompra();
        String sql = "select * from item_compra  where codigo_compra=" + codCompra ;
        
      
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                                   
                
                cp.add(new itens_Compra(rs.getInt("qtde"), rs.getDouble("unitario"), rs.getDouble("total"),
                        new Compra(rs.getInt("codigo_compra")), new Produto(rs.getInt("codigo_produto"))));
            }
        } catch (SQLException ex) {

        }
        return cp;
    }
    
    public boolean verificarParcelaPaga(int codigo) {
        String sql = "select codigo from contas_pagar where cod_compra = " + codigo + " and data_pago is not null";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        }

        return false;

    }
}
