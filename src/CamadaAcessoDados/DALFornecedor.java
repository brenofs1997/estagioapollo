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

    public Fornecedor get(int codigo) {
        String SQL = "select * from fornecedor where codigo = " + codigo;
        Cidade c = new Cidade();
        Categoria cat = new Categoria();
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {
                c = c.get(rs.getInt("cid_cod"));
                
                return new Fornecedor(rs.getInt("codigo"), rs.getString("nomefantasia"), rs.getString("cnpj"),
                        rs.getString("ativo"), rs.getString("endereco"), rs.getString("bairro"), rs.getString("numero"),
                        rs.getString("email"), rs.getString("razaosocial"), rs.getString("cep"),
                        rs.getString("telefonecontato"),  c);
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public List<Fornecedor> get(String filtro) {
        String SQL = "select * from fornecedor ";
        List<Fornecedor> l = new ArrayList();
        if (!filtro.isEmpty()) {
            SQL += " where " + filtro;
        }
        SQL += " order by nomefantasia";
        Cidade c = new Cidade();
        Categoria cat = new Categoria();
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            while (rs.next()) {
                c = c.get(rs.getInt("cid_cod"));
               

                l.add(new Fornecedor(rs.getInt("codigo"), rs.getString("nomefantasia"), rs.getString("cnpj"),
                        rs.getString("ativo"), rs.getString("endereco"), rs.getString("bairro"), rs.getString("numero"),
                        rs.getString("email"), rs.getString("razaosocial"), rs.getString("cep"),
                        rs.getString("telefonecontato"), c));
            }
        } catch (SQLException e) {
        }
        return l;
    }
}
