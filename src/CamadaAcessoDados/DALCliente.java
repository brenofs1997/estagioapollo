/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamadaAcessoDados;

import Models.Cidade;
import Models.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulo
 */
public class DALCliente {

    public boolean salvar(Cliente cliente) {
        String SQL = "insert into cliente (nome,data_cadastro,cpf,endereco,bairro,email,limite_fiado,"
                + "cep,telefone,cod_cidade,ativo,numero,saldo_devedor)"
                + " values ('#A','#B','#C','#D','#E','#F',"
                + "#G,'#H','#I',#J,#K,'#L','#M')";

        SQL = SQL.replace("#A", cliente.getNome());
        SQL = SQL.replace("#B", "" + cliente.getData_cadastro());
        SQL = SQL.replace("#C", cliente.getCpf());
        SQL = SQL.replace("#D", cliente.getEndereco());
        SQL = SQL.replace("#E", cliente.getBairro());
        SQL = SQL.replace("#F", cliente.getEmail());
        SQL = SQL.replace("#G", "" + cliente.getLimite_fiado());
        SQL = SQL.replace("#H", cliente.getCep());
        SQL = SQL.replace("#I", cliente.getTelefone());
        SQL = SQL.replace("#J", "" + cliente.getCidade().getCid_cod());
        SQL = SQL.replace("#K", "" + cliente.isAtivo());
        SQL = SQL.replace("#L", cliente.getNumero());
        SQL = SQL.replace("#M", "" + cliente.getSaldo_devedor());

        return Banco.getCon().manipular(SQL);
    }

    public boolean alterar(Cliente cliente) {
        String SQL = "update cliente set nome = '#A',data_cadastro = '#B',cpf = '#C',"
                + "endereco = '#D',bairro = '#E',email = '#F',limite_fiado = '#G',"
                + "cep = '#H',telefone = '#I',cod_cidade = '#J',ativo = #K,"
                + "numero = '#L',saldo_devedor = '#M' where codigo = " + cliente.getCodigo();

        SQL = SQL.replace("#A", cliente.getNome());
        SQL = SQL.replace("#B", "" + cliente.getData_cadastro());
        SQL = SQL.replace("#C", cliente.getCpf());
        SQL = SQL.replace("#D", cliente.getEndereco());
        SQL = SQL.replace("#E", cliente.getBairro());
        SQL = SQL.replace("#F", cliente.getEmail());
        SQL = SQL.replace("#G", "" + cliente.getLimite_fiado());
        SQL = SQL.replace("#H", cliente.getCep());
        SQL = SQL.replace("#I", cliente.getTelefone());
        SQL = SQL.replace("#J", "" + cliente.getCidade().getCid_cod());
        SQL = SQL.replace("#K", "" + cliente.isAtivo());
        SQL = SQL.replace("#L", cliente.getNumero());
        SQL = SQL.replace("#M", "" + cliente.getSaldo_devedor());

        return Banco.getCon().manipular(SQL);
    }

    public boolean apagar(int codigo) {
        return Banco.getCon().manipular("delete from cliente where codigo = " + codigo);
    }

    public List<Cliente> get(String filtro) {
        List<Cliente> c = new ArrayList();
        Cidade cid = new Cidade();
        String SQL = "select c.codigo,c.nome,c.data_cadastro,c.cpf,c.endereco,c.bairro,c.email,c.limite_fiado,c.cep,c.telefone,c.numero,c.saldo_devedor,c.cod_cidade,c.ativo from cliente c , cidade cid where c.ativo='true' and c.cod_cidade = cid.cid_cod ";

        if (!filtro.isEmpty()) {
            SQL = SQL + " and " + filtro;
        }

        SQL = SQL + " order by c.nome ";

        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            while (rs.next()) {
                cid = cid.get(rs.getInt("cod_cidade"));
                c.add(new Cliente(rs.getInt("codigo"), rs.getString("nome"), rs.getDate("data_cadastro"),
                        rs.getString("cpf"), rs.getString("endereco"), rs.getString("bairro"),
                        rs.getString("email"), rs.getDouble("limite_fiado"), rs.getString("cep"),
                        rs.getString("telefone"), cid, rs.getBoolean("ativo"),
                        rs.getString("numero"),
                        rs.getDouble("saldo_devedor")));
            }
        } catch (SQLException e) {
        }

        return c;
    }

    public Cliente get(int codigo) {
        String SQL = "select * from cliente where codigo = " + codigo;
        Cidade cid = new Cidade();
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {
                cid = cid.get(rs.getInt("cod_cidade"));
                return new Cliente(rs.getInt("codigo"), rs.getString("nome"), rs.getDate("data_cadastro"),
                        rs.getString("cpf"), rs.getString("endereco"), rs.getString("bairro"),
                        rs.getString("email"), rs.getDouble("limite_fiado"), rs.getString("cep"),
                        rs.getString("telefone"),cid, rs.getBoolean("ativo"),
                        rs.getString("numero"),
                        rs.getDouble("saldo_devedor"));

            }
        } catch (SQLException e) {
        }
        return null;
    }
}
