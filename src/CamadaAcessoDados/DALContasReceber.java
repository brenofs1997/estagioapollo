/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamadaAcessoDados;

import Models.Cliente;
import Models.CondicaoPagamento;
import Models.ContasReceber;
import Models.Funcionario;
import Models.Venda;
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
public class DALContasReceber {

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");

    public boolean salvar(ContasReceber c) {

        int codVenda = 0;
        codVenda = Banco.getCon().getMaxPK("venda", "codigo");
        String sql = "insert into contas_receber (cod_venda,parcela,valor,"
                + "valor_pago,emissao,data_pago,vencimento,cod_funcionario,cod_cliente,cod_condicaopagamento,status) "
                + "values(#V,'#A',#B,#C,'#D','#E','#F',#H,#I,#J,'#L')";
        if (c.getCod_venda() == 0) {
            c.setCod_venda(codVenda);
        }
        sql = sql.replace("#V", "" + c.getCod_venda());
        sql = sql.replace("#A", c.getParcela());
        sql = sql.replace("#B", "" + c.getValor());
        sql = sql.replace("#C", "" + c.getValor_pago());
        sql = sql.replace("#D", "" + formato.format(c.getEmissaoDate()));
        if (c.getData_pagoDate() != null) {
            sql = sql.replace("#E", "" + formato.format(c.getData_pagoDate()));
        } else {
            sql = sql.replace("'#E'", "null");
        }
        if (c.getVencimentoDate() != null) {
            sql = sql.replace("#F", "" + formato.format(c.getVencimentoDate()));
        } else {
            sql = sql.replace("'#F'", "null");
        }

        sql = sql.replace("#H", "" + c.getFuncionario().getCodigo());
        sql = sql.replace("#I", "" + c.getCliente().getCodigo());
        sql = sql.replace("#J", "" + c.getCond_pgto().getCodigo());
        sql = sql.replace("#L", c.getStatus());
        return Banco.getCon().manipular(sql);
    }

    public boolean apagarParcVenda(int codigo) {
        return Banco.getCon().manipular("delete from contas_receber where cod_venda = " + codigo);
    }

    public boolean apagar(int cod) {
        return Banco.getCon().manipular("delete from contas_receber where cod_venda = " + cod);
    }

    public ContasReceber get(int codigo) {
        String sql = " select * from contas_receber where codigo =" + codigo;

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                return new ContasReceber(rs.getInt("codigo"), new Venda(rs.getInt("cod_venda")), rs.getString("parcela"),
                        rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"), rs.getDate("data_pago"),
                        rs.getDate("vencimento"), new Funcionario(rs.getInt("cod_funcionario")), new Cliente(rs.getInt("cod_cliente")),
                        new CondicaoPagamento(rs.getInt("cod_condicaopagamento")), rs.getString("status"));

            }
        } catch (SQLException ex) {

        }
        return null;
    }
     public List<ContasReceber> get(String filtro) {
         
        String sql = " select * from contas_receber where status= '"+filtro+"'" ;
        List<ContasReceber> cr = new ArrayList();
        ContasReceber cont = new ContasReceber();
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                

                 cont=new ContasReceber(rs.getInt("codigo"), new Venda(rs.getInt("cod_venda")), rs.getString("parcela"),
                        rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"), rs.getDate("data_pago"),
                        rs.getDate("vencimento"), new Funcionario(rs.getInt("cod_funcionario")), new Cliente(rs.getInt("cod_cliente")),
                        new CondicaoPagamento(rs.getInt("cod_condicaopagamento")), rs.getString("status"));
                 cr.add(cont);
            }
        } catch (SQLException ex) {

        }
        return cr;
    }
    public List<ContasReceber> getFiado(int cliente, String dtinicial, String dtfinal) {
        List<ContasReceber> cr = new ArrayList();
        ContasReceber cont = new ContasReceber();
        String sql = " select cr.* from contas_receber cr,venda v,condicao_pgto cp where cr.cod_venda = v.codigo "
                + " and  v.cod_cliente =" + cliente + " and v.cod_condpagto =cp.codigo and cp.descricao like '%Fiado%' ";

        if (!dtinicial.isEmpty()) {
            sql += " and cr.emissao BETWEEN '" + dtfinal + "' and '" + dtfinal + "' ";
        }

        sql += " order by cr.emissao ASC";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                cont = new ContasReceber(rs.getInt("codigo"), new Venda(rs.getInt("cod_venda")), rs.getString("parcela"),
                        rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"), rs.getDate("data_pago"),
                        rs.getDate("vencimento"), new Funcionario(rs.getInt("cod_funcionario")), new Cliente(rs.getInt("cod_cliente")),
                        new CondicaoPagamento(rs.getInt("cod_condicaopagamento")), rs.getString("status"));
                cr.add(cont);
            }
        } catch (SQLException ex) {

        }
        return cr;
    }

    public boolean alterar(ContasReceber conta) {
        String sql = "update contas_receber set  status= '#B',valor_pago = '#C',"
                + "data_pago = '#E' where codigo = " + conta.getCodigo();
        sql = sql.replace("#B", "" + conta.getStatus());
        sql = sql.replace("#C", "" + conta.getValor_pago());
        if (conta.getData_pagoDate() != null) {
            sql = sql.replace("#E", "" + formato.format(conta.getData_pagoDate()));
        } else {
            sql = sql.replace("'#E'", "null");
        }

        return Banco.getCon().manipular(sql);
    }
}
