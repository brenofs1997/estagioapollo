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


/**
 *
 * @author paulo
 */
public class DALFornecedor {
   
        public Fornecedor get(int codigo) {
        String SQL = "select * from categoria where codigo = " + codigo;
        Cidade c = new Cidade();
        Categoria cat = new Categoria();
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {
                c=c.get(rs.getInt("cid_cod"));
                cat=cat.get(rs.getInt("cid_cod"));
                return new Fornecedor(rs.getInt("codigo"), rs.getString("nomefantasia"), rs.getString("cnpj"),
                        rs.getString("ativo"), rs.getString("endereco"), rs.getString("bairro"), rs.getString("numero"),
                        rs.getString("email"), rs.getString("razaosocial"), rs.getString("cep"),
                        rs.getString("telefonecontato"), cat, c);
            }
        } catch (SQLException e) {
        }
        return null;
    }
}
