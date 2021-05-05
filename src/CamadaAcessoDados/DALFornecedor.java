/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamadaAcessoDados;

import Models.Categoria;
import Models.Cidade;
import Models.Fornecedor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulo
 */
public class DALFornecedor {

    public boolean salvar(Fornecedor f) {
        String sql = "insert into fornecedor (nomefantasia,cnpj,ativo,endereco,bairro,"
                + "numero,cod_cidade,cep,razaosocial,telefone,email) "
                + "values('#A','#B','#C','#D','#E','#F',#G,'#H','#J','#M','#N')";

        sql = sql.replace("#A", f.getNomefantasia());
        sql = sql.replace("#B", f.getCnpj());
        sql = sql.replace("#C", f.getAtivo());
        sql = sql.replace("#D", f.getEndereco());
        sql = sql.replace("#E", f.getBairro());
        sql = sql.replace("#F", f.getNumero());
        sql = sql.replace("#G", "" + f.getCidade().getCid_cod());
        sql = sql.replace("#H", f.getCep());
        sql = sql.replace("#J", f.getRazaosocial());
        sql = sql.replace("#M", f.getTelefonecontato());
        sql = sql.replace("#N", f.getEmail());

        return Banco.getCon().manipular(sql);
    }

    public boolean alterar(Fornecedor f) {
        String sql = "update fornecedor set nomefantasia='#A',cnpj='#B',ativo='#C',endereco='#D',bairro='#E',"
                + "numero='#F',cod_cidade=#G,cep='#H',razaosocial='#J',telefone='#M',email='#N' where codigo = "+f.getCodigo();

        sql = sql.replace("#A", f.getNomefantasia());
        sql = sql.replace("#B", f.getCnpj());
        sql = sql.replace("#C", f.getAtivo());
        sql = sql.replace("#D", f.getEndereco());
        sql = sql.replace("#E", f.getBairro());
        sql = sql.replace("#F", f.getNumero());
        sql = sql.replace("#G", "" + f.getCidade().getCid_cod());
        sql = sql.replace("#H", f.getCep());
        sql = sql.replace("#J", f.getRazaosocial());
        sql = sql.replace("#M", f.getTelefonecontato());
        sql = sql.replace("#N", f.getEmail());

        return Banco.getCon().manipular(sql);
    }

    public boolean salvarCategoria(int codfor, int codcat) {
        String sql = "insert into categorias_fornecedor (cod_categoria,cod_fornecedor) values(#A,#B)";

        sql = sql.replace("#A", "" + codcat);
        sql = sql.replace("#B", "" + codfor);

        return Banco.getCon().manipular(sql);
    }

    public Fornecedor get(int codigo) {
        String SQL = "select * from fornecedor where codigo = " + codigo;
        Cidade c = new Cidade();
        Categoria cat = new Categoria();
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {
                c = c.get(rs.getInt("cod_cidade"));

                return new Fornecedor(rs.getInt("codigo"), rs.getString("nomefantasia"), rs.getString("cnpj"),
                        rs.getString("ativo"), rs.getString("endereco"), rs.getString("bairro"), rs.getString("numero"),
                        c, rs.getString("email"), rs.getString("razaosocial"), rs.getString("cep"),
                        rs.getString("telefone"));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public List<Fornecedor> get(String filtro) {
        String SQL = "select * from fornecedor ";
        List<Fornecedor> l = new ArrayList();
        if (!filtro.isEmpty()) {
            SQL += "where nomefantasia ILIKE '%"+filtro+"%'" ;
        }
        SQL += " order by nomefantasia";
        Cidade c = new Cidade();
        Categoria cat = new Categoria();
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            while (rs.next()) {
                c = c.get(rs.getInt("cod_cidade"));

                l.add(new Fornecedor(rs.getInt("codigo"), rs.getString("nomefantasia"), rs.getString("cnpj"),
                        rs.getString("ativo"), rs.getString("endereco"), rs.getString("bairro"), rs.getString("numero"),
                        c, rs.getString("email"), rs.getString("razaosocial"), rs.getString("cep"),
                        rs.getString("telefone")));
            }
        } catch (SQLException e) {
        }
        return l;
    }

    public List<Categoria> getCategorias(int codfor) {
        String SQL = "select c.* from categoria c ,categorias_fornecedor cf where c.codigo=cf.cod_categoria and cod_fornecedor = " + codfor;
        List<Categoria> l = new ArrayList();
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            while (rs.next()) {
                l.add(new Categoria(rs.getInt("codigo"), rs.getString("descricao")));
            }
        } catch (SQLException e) {
        }
        return l;
    }

    public boolean apagar(int codigo) {
        boolean aux = false;

        aux = Banco.getCon().manipular("delete from fornecedor where codigo = " + codigo);
        aux = Banco.getCon().manipular("delete from categorias_fornecedor where cod_fornecedor = " + codigo);

        return aux;
    }

    public boolean apagarCats(int cod) {
        return Banco.getCon().manipular("delete from categorias_fornecedor where cod_fornecedor = " + cod);
    }

}
