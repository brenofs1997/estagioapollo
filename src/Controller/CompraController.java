/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Compra;
import Models.Fornecedor;
import Models.Produto;
import Models.itens_Compra;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

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
    public void atualizarTabela(TableView<itens_Compra> tabela, List<itens_Compra> Produtos) {
        ObservableList<itens_Compra> prod_v = null;
        if (tabela.getItems() != null) {
            tabela.getItems().removeAll();
        }
        prod_v = FXCollections.observableArrayList(Produtos);
        tabela.setItems(prod_v);
    }
  
    public List<Produto> CarregarProduto(int codFornecedor) {
        Produto p = new Produto();
        List<Produto> Lista = p.getCategoriasFornecedor(codFornecedor);//Lista de Tipos de despesa
        return Lista;
    }
    public int procLista(List<itens_Compra> lista, int codProdt) {
        int index = -1;
        for (itens_Compra item : lista) {
            if (item.getProduto().getCodigo() == codProdt) {
                index = lista.indexOf(item);
                return index;
            }

        }
        return index;
    }
    public void addProduto(List<itens_Compra> Produtos,Produto prod, TableView<itens_Compra> tabProd, int qtde,Double uni) {
        int index = 0;
        int qtdeAlter = 0;
        Double total=0.0;

       Produtos = tabProd.getItems();
        
        if (!tabProd.getItems().isEmpty()) {

            index = procLista(tabProd.getItems(), prod.getCodigo());
            if (index >= 0) {
                qtdeAlter = Produtos.get(index).getQuantidade() + qtde;
                Produtos.get(index).setQuantidade(qtdeAlter);
                total=uni*qtdeAlter;
                Produtos.get(index).setTotal(total);
                Produtos.get(index).setUnitario(uni);
                
               

            } else {
                total=uni*qtde;
                tabProd.getItems().add(new itens_Compra(qtde,uni,total, new Compra() , prod));
            }
        } else {
             total=uni*qtde;
            tabProd.getItems().add(new itens_Compra(qtde,uni,total, new Compra(), prod));
        }
         atualizarTabela(tabProd, Produtos);
    }
    
    
}
