/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Fornecedor;
import Models.Produto;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class CompraController {
     
    public List<Fornecedor> CarregaFornecedor() {
        Fornecedor f = new Fornecedor();
        List<Fornecedor> Lista = f.get("");//Lista de Tipos de despesa
        return Lista;
    }
      
    public List<Produto> CarregarProduto(int codFornecedor) {
        Produto p = new Produto();
        List<Produto> Lista = p.getCategoriasFornecedor(codFornecedor);//Lista de Tipos de despesa
        return Lista;
    }
    
    
}
