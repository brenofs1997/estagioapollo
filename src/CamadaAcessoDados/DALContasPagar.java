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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paulo
 */
public class DALContasPagar {

    NumberFormat nf = new DecimalFormat("#,###.00");
    DecimalFormat formatoDecimal = new DecimalFormat("#.##");

    public boolean salvar(ContasPagar c) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
        boolean aux = false;
        int flag_parcial = 0;
        int cod_flag = 0;

        String sql = "insert into contas_pagar (flag_despesa,parcela,valor,"
                + "valor_pago,emissao,data_pago,funcionario,cond_pgto,tipo_despesa,qtde_parcelas,dias_entreparc,status,cod_fornecedor,cod_compra,vencimento) "
                + "values(#A,'#B',#C,#D,'#E','#F',#H,#I,#J,#L,#M,'#N',#O,#P,'#G')";

        sql = sql.replace("#A", "" + c.getFlag_despesa());
        sql = sql.replace("#B", c.getParcela());
        sql = sql.replace("#C", "" + c.getValor());
        sql = sql.replace("#D", "" + c.getValor_pago());
        sql = sql.replace("#E", "" + formato.format(c.getEmissaoDate()));
        if (c.getData_pagoDate() != null) {
            sql = sql.replace("#F", "" + formato.format(c.getData_pagoDate()));
        } else {
            sql = sql.replace("'#F'", "null");
        }

        sql = sql.replace("#G", "" + formato.format(c.getVencimentoDate()));
        sql = sql.replace("#H", "" + c.getFuncionario().getCodigo());
        sql = sql.replace("#I", "" + c.getCond_pgto().getCodigo());

        if (c.getTipo_despesa() == null) {
            sql = sql.replace("#J", "null");
        } else {
            sql = sql.replace("#J", "" + c.getTipo_despesa().getCodigo());
        }

        sql = sql.replace("#L", "" + c.getQtde_parcelas());
        sql = sql.replace("#M", "" + c.getDias_entreparc());
        sql = sql.replace("#N", c.getStatus());

        if (c.getFornecedor() == null) {
            sql = sql.replace("#O", "null");
        } else {
            sql = sql.replace("#O", "" + c.getFornecedor().getCodigo());
        }

        if (c.getCompra() == null) {
            sql = sql.replace("#P", "null");
        } else {
            sql = sql.replace("#P", "" + c.getCompra().getCodigo());
        }
        aux = Banco.getCon().manipular(sql);

        if (aux == true) {
            flag_parcial = Banco.getCon().getMaxPK("contas_pagar", "codigo");
            cod_flag = Banco.getCon().getMaxPK("contas_pagar", "codigo");
            if (c.getFlag_parcial() != 0) {
                flag_parcial = c.getFlag_parcial();
            }

            aux = Banco.getCon().manipular("update contas_pagar set flag_parcial=" + flag_parcial + " where codigo =" + cod_flag + " ");
        }

        return aux;
    }

    public boolean alterar(ContasPagar c) {
        String sql = "update contas_pagar set flag_despesa=#A,parcela='#B',valor=#C,"
                + "valor_pago=#D,emissao='#E',data_pago='#F',"
                + "vencimento='#G',funcionario=#H,cond_pgto=#I,"
                + "tipo_despesa=#J,qtde_parcelas=#L,dias_entreparc=#M,status='#N',cod_fornecedor=#O,cod_compra=#P where codigo=" + c.getCodigo();
        sql = sql.replace("#A", "" + c.getFlag_despesa());
        sql = sql.replace("#B", c.getParcela());
        sql = sql.replace("#C", "" + c.getValor());
        sql = sql.replace("#D", "" + c.getValor_pago());
        sql = sql.replace("#E", "" + c.getEmissaoDate());
        if (c.getData_pagoDate() == null) {
            sql = sql.replace("'#F'", "null");
        } else {
            sql = sql.replace("#F", "" + c.getData_pagoDate());
        }
        sql = sql.replace("#G", "" + c.getVencimentoDate());
        sql = sql.replace("#H", "" + c.getFuncionario().getCodigo());
        sql = sql.replace("#I", "" + c.getCond_pgto().getCodigo());

        if (c.getTipo_despesa() == null) {
            sql = sql.replace("#J", "null");
        } else {
            sql = sql.replace("#J", "" + c.getTipo_despesa().getCodigo());
        }

        sql = sql.replace("#L", "" + c.getQtde_parcelas());
        sql = sql.replace("#M", "" + c.getDias_entreparc());
        sql = sql.replace("#N", c.getStatus());

        if (c.getFornecedor() == null) {
            sql = sql.replace("#O", "null");
        } else {
            sql = sql.replace("#O", "" + c.getFornecedor().getCodigo());
        }

        if (c.getCompra() == null) {
            sql = sql.replace("#P", "null");
        } else {
            sql = sql.replace("#P", "" + c.getCompra().getCodigo());
        }

        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(ContasPagar cp, List<ContasPagar> Receber) {
        boolean aux = false;

        if (Receber != null) {
            for (ContasPagar receber : Receber) {
                if (receber.getFlag_despesa() == cp.getFlag_despesa()) {
                    aux = Banco.getCon().manipular("delete from contas_pagar where flag_despesa=" + cp.getFlag_despesa());
                    aux = true;
                } else {
                    aux = false;
                }
            }

        }
        return aux;
    }

    public boolean apagar(int cod) {
        return Banco.getCon().manipular("delete from contas_pagar where codigo =" + cod);
    }

    public boolean apagarParcCompra(int cod) {
        return Banco.getCon().manipular("delete from contas_pagar where cod_compra =" + cod);
    }

    public ContasPagar get(int filtro) {
        ContasPagar cont = new ContasPagar();
        DALFuncionario dalf = new DALFuncionario();
        DALFornecedor dalfr = new DALFornecedor();
        DALTipoDespesa dalt = new DALTipoDespesa();
        DALCondPgto dalcd = new DALCondPgto();
        DALCompra dalcp = new DALCompra();
        double vr = 0.0;
        String sql = "select * from contas_pagar ";
        if (filtro > 0) {
            sql += " where codigo=" + filtro;
        }
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                try {
                    vr = nf.parse(nf.format(rs.getDouble("valor") - rs.getDouble("valor_pago"))).doubleValue();
                } catch (ParseException ex) {
                    Logger.getLogger(DALContasPagar.class.getName()).log(Level.SEVERE, null, ex);
                }
                cont = new ContasPagar(rs.getInt("codigo"), rs.getInt("flag_despesa"), rs.getString("parcela"), rs.getDouble("valor"),
                        rs.getDouble("valor_pago"), rs.getDate("emissao"), rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("funcionario")),
                        dalcd.get(rs.getInt("cond_pgto")), dalt.get(rs.getInt("tipo_despesa")), rs.getInt("qtde_parcelas"), rs.getInt("dias_entreparc"), rs.getString("parcela"),
                        dalfr.get(rs.getInt("cod_fornecedor")), dalcp.get(rs.getInt("cod_compra")), vr, rs.getInt("flag_parcial"));
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
        double vr = 0.0;
        String sql = "select * from contas_pagar ";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
   
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                try {
                    vr = nf.parse(nf.format(rs.getDouble("valor") - rs.getDouble("valor_pago"))).doubleValue();
                } catch (ParseException ex) {
                    Logger.getLogger(DALContasPagar.class.getName()).log(Level.SEVERE, null, ex);
                }
                cont = new ContasPagar(rs.getInt("codigo"), rs.getInt("flag_despesa"), rs.getString("parcela"), rs.getDouble("valor"),
                        rs.getDouble("valor_pago"), rs.getDate("emissao"), rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("funcionario")),
                        dalcd.get(rs.getInt("cond_pgto")), dalt.get(rs.getInt("tipo_despesa")), rs.getInt("qtde_parcelas"), rs.getInt("dias_entreparc"), rs.getString("parcela"),
                        dalfr.get(rs.getInt("cod_fornecedor")), dalcp.get(rs.getInt("cod_compra")), vr, rs.getInt("flag_parcial"));
                cp.add(cont);
            }
        } catch (SQLException ex) {

        }
        return cp;
    }

    public List<ContasPagar> getContasPagar(int flag_desp, int fornecedor, String dtinicial, String dtfinal, String status) {
        List<ContasPagar> cp = new ArrayList();
        ContasPagar cont = new ContasPagar();
        DALFuncionario dalf = new DALFuncionario();
        DALFornecedor dalfr = new DALFornecedor();
        DALTipoDespesa dalt = new DALTipoDespesa();
        DALCondPgto dalcd = new DALCondPgto();
        DALCompra dalcp = new DALCompra();
        double vr = 0.0;
        String sql = "select * from contas_pagar ";

        if (fornecedor != 0) {
            sql = " select * from contas_pagar where cod_fornecedor =" + fornecedor;
            if (!dtinicial.isEmpty()) {
                sql += " and vencimento BETWEEN '" + dtinicial + "' and '" + dtfinal + "' ";
            }
            if (status.equals("Q")) {
                sql += " and  status='" + status + "'";
            }
            if (status.equals("P")) {
                sql += " and  status='" + status + "'";
            }

            sql += " order by vencimento ASC";
        } else {

            sql = " select * from contas_pagar ";
            if (!dtinicial.isEmpty()) {
                sql += " where emissao BETWEEN '" + dtinicial + "' and '" + dtfinal + "' ";

                if (status.equals("Q")) {
                    sql += " and  status='" + status + "'";
                }
                if (status.equals("P")) {
                    sql += " and  status='" + status + "'";
                }
                if (flag_desp != 0) {
                    sql += "and tipo_despesa=" + flag_desp;
                }
            } else {
                if (status.equals("Q")) {
                    sql += " where status='" + status + "'";
                    if (flag_desp != 0) {
                        sql += "and tipo_despesa=" + flag_desp;
                    }
                } else {
                    if (status.equals("P")) {
                        sql += " where status='" + status + "'";
                        if (flag_desp != 0) {
                            sql += "and tipo_despesa=" + flag_desp;
                        }

                    } else if (flag_desp != 0) {
                        sql += "where tipo_despesa=" + flag_desp;
                    }
                }
                //if (status.equals("P")) {
                //  sql += " and  status= '" + status + "'";
                //}
            }

            sql += " order by emissao ASC";
        }

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                try {
                    vr = nf.parse(nf.format(rs.getDouble("valor") - rs.getDouble("valor_pago"))).doubleValue();
                } catch (ParseException ex) {
                    Logger.getLogger(DALContasPagar.class.getName()).log(Level.SEVERE, null, ex);
                }
                cont = new ContasPagar(rs.getInt("codigo"), rs.getInt("flag_despesa"), rs.getString("parcela"), rs.getDouble("valor"),
                        rs.getDouble("valor_pago"), rs.getDate("emissao"), rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("funcionario")),
                        dalcd.get(rs.getInt("cond_pgto")), dalt.get(rs.getInt("tipo_despesa")), rs.getInt("qtde_parcelas"), rs.getInt("dias_entreparc"), rs.getString("parcela"),
                        dalfr.get(rs.getInt("cod_fornecedor")), dalcp.get(rs.getInt("cod_compra")), vr, rs.getInt("flag_parcial"));
                cp.add(cont);
            }
        } catch (SQLException ex) {

        }
        return cp;
    }

    public List<ContasPagar> getParcDespesas(int codigo) {
        List<ContasPagar> cp = new ArrayList();
        ContasPagar cont = new ContasPagar();
        DALFuncionario dalf = new DALFuncionario();
        DALFornecedor dalfr = new DALFornecedor();
        DALTipoDespesa dalt = new DALTipoDespesa();
        DALCondPgto dalcd = new DALCondPgto();
        DALCompra dalcp = new DALCompra();
        String sql = "select * from contas_pagar where flag_despesa = " + codigo;

        sql += " order by flag_despesa";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                cont = new ContasPagar(rs.getInt("codigo"), rs.getInt("flag_despesa"), rs.getString("parcela"), rs.getDouble("valor"),
                        rs.getDouble("valor_pago"), rs.getDate("emissao"), rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("funcionario")),
                        dalcd.get(rs.getInt("cond_pgto")), dalt.get(rs.getInt("tipo_despesa")), rs.getInt("qtde_parcelas"), rs.getInt("dias_entreparc"), rs.getString("parcela"),
                        dalfr.get(rs.getInt("cod_fornecedor")), dalcp.get(rs.getInt("cod_compra")), rs.getInt("flag_parcial"));
                cp.add(cont);
            }
        } catch (SQLException ex) {

        }
        return cp;
    }

    public List<ContasPagar> getParcCompras(int codigo) {
        List<ContasPagar> cp = new ArrayList();
        ContasPagar cont = new ContasPagar();
        DALFuncionario dalf = new DALFuncionario();
        DALFornecedor dalfr = new DALFornecedor();
        DALTipoDespesa dalt = new DALTipoDespesa();
        DALCondPgto dalcd = new DALCondPgto();
        DALCompra dalcp = new DALCompra();
        String sql = "select * from contas_pagar where cod_compra = " + codigo;

        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                cont = new ContasPagar(rs.getInt("codigo"), rs.getInt("flag_despesa"), rs.getString("parcela"), rs.getDouble("valor"),
                        rs.getDouble("valor_pago"), rs.getDate("emissao"), rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("funcionario")),
                        dalcd.get(rs.getInt("cond_pgto")), dalt.get(rs.getInt("tipo_despesa")), rs.getInt("qtde_parcelas"), rs.getInt("dias_entreparc"), rs.getString("parcela"),
                        dalfr.get(rs.getInt("cod_fornecedor")), dalcp.get(rs.getInt("cod_compra")), rs.getInt("flag_parcial"));
                cp.add(cont);
            }
        } catch (SQLException ex) {

        }
        return cp;
    }

    public boolean verificaPagamento(List<ContasPagar> aux) {
        boolean auxC = false;
        if (aux != null) {
            for (ContasPagar receber : aux) {
                ResultSet rs = Banco.getCon().consultar("select codigo from contas_pagar where codigo= " + receber.getCodigo() + " and data_pago is not null");
                try {
                    while (rs.next()) {
                        return true;
                    }
                } catch (SQLException ex) {

                }
            }

        }
        return auxC;
    }

    public ContasPagar getC(int codCompra) {
        ContasPagar cont = new ContasPagar();
        DALFuncionario dalf = new DALFuncionario();
        DALFornecedor dalfr = new DALFornecedor();
        DALTipoDespesa dalt = new DALTipoDespesa();
        DALCondPgto dalcd = new DALCondPgto();
        DALCompra dalcp = new DALCompra();
        String sql = "select * from contas_pagar ";
        if (codCompra > 0) {
            sql += " where cod_compra=" + codCompra;
        }
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                cont = new ContasPagar(rs.getInt("codigo"), rs.getInt("flag_despesa"), rs.getString("parcela"), rs.getDouble("valor"),
                        rs.getDouble("valor_pago"), rs.getDate("emissao"), rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("funcionario")),
                        dalcd.get(rs.getInt("cond_pgto")), dalt.get(rs.getInt("tipo_despesa")), rs.getInt("qtde_parcelas"), rs.getInt("dias_entreparc"), rs.getString("parcela"),
                        dalfr.get(rs.getInt("cod_fornecedor")), dalcp.get(rs.getInt("cod_compra")), rs.getInt("flag_parcial"));
            }
        } catch (SQLException ex) {

        }
        return cont;
    }

}
