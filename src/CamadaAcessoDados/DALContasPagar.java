/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamadaAcessoDados;

import Models.ContasPagar;
import Models.EmpresaParametros;
import Models.Produto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulo
 */
public class DALContasPagar {

    public boolean salvar(ContasPagar c) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
        
        String sql = "insert into contas_pagar (flag_despesa,parcela,valor,"
                + "valor_pago,emissao,data_pago,funcionario,cond_pgto,tipo_despesa,qtde_parcelas,dias_entreparc,status,fornecedor,compra,vencimento) "
                + "values(#A,'#B',#C,#D,'#E','#F',#H,#I,#J,#L,#M,'#N',#O,#P,'#G')";
        sql = sql.replace("#A", ""+c.getFlag_despesa());
        sql = sql.replace("#B", c.getParcela());
        sql = sql.replace("#C", ""+c.getValor());
        sql = sql.replace("#D", "" + c.getValor_pago());
        sql = sql.replace("#E",""+formato.format( c.getEmissao()));
          if (c.getData_pago() != null) {
            sql = sql.replace("#F",""+formato.format( c.getData_pago()));
          } else 
            sql = sql.replace("'#F'", "null");
        
        sql = sql.replace("#G",""+ formato.format(c.getVencimento()));
        sql = sql.replace("#H", ""+c.getFuncionario().getCodigo());
        sql = sql.replace("#I",""+ c.getCond_pgto().getCodigo());
        sql = sql.replace("#J", ""+c.getTipo_despesa().getCodigo());
        sql = sql.replace("#L", ""+c.getQtde_parcelas());
        sql = sql.replace("#M", ""+c.getDias_entreparc());
        sql = sql.replace("#N", c.getStatus());
        sql = sql.replace("#O", ""+c.getFornecedor().getCodigo());
        sql = sql.replace("#P", ""+c.getCompra().getCodigo());
        return Banco.getCon().manipular(sql);
    }

    public boolean alterar(ContasPagar c) {
   String sql = "update contas_pagar set flag_despesa=#A,parcela='#B',valor=#C,"
                + "valor_pago=#D,emissao='#E',data_pago='#F',"
                + "vencimento='#G',funcionario=#H,cond_pgto=#I,"
                + "tipo_despesa=#J,qtde_parcelas=#L,dias_entreparc=#M,status='#N',fornecedor=#O,compra=#P where codigo=" + c.getCodigo();
        sql = sql.replace("#A", ""+c.getFlag_despesa());
        sql = sql.replace("#B", c.getParcela());
        sql = sql.replace("#C", ""+c.getValor());
        sql = sql.replace("#D", "" + c.getValor_pago());
        sql = sql.replace("#E",""+ c.getEmissao());
        if(c.getData_pago()==null)
           sql = sql.replace("'#F'", "null");
        else
           sql = sql.replace("#F",""+ c.getData_pago());
        sql = sql.replace("#G",""+ c.getVencimento());
        sql = sql.replace("#H", ""+c.getFuncionario().getCodigo());
        sql = sql.replace("#I",""+c.getCond_pgto().getCodigo());
        sql = sql.replace("#J", ""+c.getTipo_despesa().getCodigo());
        sql = sql.replace("#L", ""+c.getQtde_parcelas());
        sql = sql.replace("#M", ""+c.getDias_entreparc());
        sql = sql.replace("#N", c.getStatus());
        sql = sql.replace("#O", ""+c.getFornecedor().getCodigo());
        sql = sql.replace("#P", ""+c.getCompra().getCodigo());
        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(int cod) {
        return Banco.getCon().manipular("delete from contas_pagar where flag_despesa = " + cod);
    }

    public ContasPagar get(int filtro) {
        ContasPagar cont = new ContasPagar();
        DALFuncionario dalf = new DALFuncionario();
        DALFornecedor dalfr = new DALFornecedor();
        DALTipoDespesa dalt = new DALTipoDespesa();
        DALCondPgto dalcd = new DALCondPgto();
        DALCompra dalcp = new DALCompra();
        String sql = "select * from contas_pagar ";
        if (filtro > 0) {
            sql += " where codigo=" + filtro;
        }
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                cont = new ContasPagar(rs.getInt("codigo"), rs.getInt("flag_despesa"), rs.getString("parcela"), rs.getDouble("valor"),
                        rs.getDouble("valor_pago"), rs.getDate("emissao"),rs.getDate("data_pago") ,rs.getDate("vencimento") , dalf.get(rs.getInt("funcionario")),
                        dalcd.get(rs.getInt("cond_pgto")),dalt.get(rs.getInt("tipo_despesa")), rs.getInt("qtde_parcelas"), rs.getInt("dias_entreparc"), rs.getString("parcela"), 
                        dalfr.get(rs.getInt("fornecedor")), dalcp.get(rs.getInt("compra")));
            }
        } catch (SQLException ex) {

        }
        return cont;
    }

    public List<ContasPagar> get(String filtro) {
         List<ContasPagar> cp = new ArrayList();
         ContasPagar cont = new ContasPagar();
        DALFuncionario dalf = new DALFuncionario();
        DALFornecedor dalfr = new DALFornecedor();
        DALTipoDespesa dalt = new DALTipoDespesa();
        DALCondPgto dalcd = new DALCondPgto();
        DALCompra dalcp = new DALCompra();
        String sql = "select * from contas_pagar ";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
         sql+=" order by flag_despesa";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

               cont = new ContasPagar(rs.getInt("codigo"), rs.getInt("flag_despesa"), rs.getString("parcela"), rs.getDouble("valor"),
                        rs.getDouble("valor_pago"), rs.getDate("emissao"),rs.getDate("data_pago") ,rs.getDate("vencimento") , dalf.get(rs.getInt("funcionario")),
                        dalcd.get(rs.getInt("cond_pgto")),dalt.get(rs.getInt("tipo_despesa")), rs.getInt("qtde_parcelas"), rs.getInt("dias_entreparc"), rs.getString("parcela"), 
                        dalfr.get(rs.getInt("fornecedor")), dalcp.get(rs.getInt("compra")));
               cp.add(cont);
            }
        } catch (SQLException ex) {

        }
        return cp;
    }
    
}