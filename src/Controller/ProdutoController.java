/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import CamadaAcessoDados.DALCategoria;
import Erros.Erros;
import Models.Categoria;
import Models.Produto;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;

/**
 *
 * @author paulo
 */
public class ProdutoController {
     Erros msg = new Erros();
     NumberFormat nf = new DecimalFormat("#,###.00");

    public void carregaTabela(String Filtro, TableView<Produto> tabela) {

        Produto tip = new Produto();
        List<Produto> res = tip.get(Filtro);
        ObservableList<Produto> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }

    public boolean gravar(int cod, String desc,Double preco,int qtde,boolean ativo, JFXComboBox<Categoria> cbCategoria) {

        Categoria c = new Categoria();
        c = cbCategoria.getSelectionModel().getSelectedItem();
        Produto p = new Produto(cod, desc, preco,qtde,ativo,c);
        return p.salvar(p);
    }

    public void alterar(JFXTextField txcod, JFXTextField txDescricao, JFXTextField txPreco, JFXTextField txQtde, JFXCheckBox cbAtivo, JFXComboBox<Categoria> cbCategoria, TableView<Produto> tabela) {

        Produto p = tabela.getSelectionModel().getSelectedItem();
        p = p.get(p.getCodigo());
        txcod.setText("" + p.getCodigo());
        txDescricao.setText("" + p.getDescricao());
        cbCategoria.getSelectionModel().select(0);
        cbCategoria.getSelectionModel().select(p.getCategoria());
        txQtde.setText(""+p.getQtde());
        if(p.isAtivo())
        {
            cbAtivo.setSelected(true);
            cbAtivo.setText("Ativo");
        }
        else
        {
            
            cbAtivo.setSelected(false);
            cbAtivo.setText("Não Ativo");
        }
        txPreco.setText(""+p.getPreco());
    }

    public void evtTabela(TableView<Produto> tabela, JFXButton btalterar, JFXButton btapagar) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    

    public void apagar(TableView<Produto> tabela) {
        Produto aux = new Produto();
        aux = tabela.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirmar exclusão?");
        if (a.showAndWait().get() == ButtonType.OK) {

            if (aux.apagar(aux.getCodigo())) {
                msg.Affirmation("Apollo Informa:", "Exclusão feita com sucesso");
            } else {
                msg.Error("Erro ", "Exclusão não realizada");
            }
        }

    }

 
    public void clkPesquisar(String filtro, TableView<Produto> tabela) {
        carregaTabela(filtro, tabela);
    }

    public boolean alterarP(JFXTextField txcod, JFXTextField txDescricao, JFXTextField txPreco, JFXTextField txQtde, JFXCheckBox cbAtivo, JFXComboBox<Categoria> cbCategoria, TableView<Produto> tabela) {
        try {
            Categoria cat= new Categoria();
            cat = cbCategoria.getSelectionModel().getSelectedItem();
            int cod=0;
            try {
                cod=Integer.parseInt(txcod.getText());
            } catch (NumberFormatException e) {
                cod=0;
            }
            Produto aux = new Produto(cod,txDescricao.getText(),nf.parse(txPreco.getText()).doubleValue(),Integer.parseInt(txQtde.getText()), cbAtivo.isSelected(), cat);
            
            return aux.alterar(aux);
        } catch (ParseException ex) {
             Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        }
         return false;
    }

    public void carregarCategoria(JFXComboBox<Categoria> cbCategoria) {
         Categoria cat = new Categoria();
        List<Categoria> lista = cat.get("");
        cbCategoria.setItems(FXCollections.observableArrayList(lista));
        cbCategoria.getSelectionModel().select(0);
    }

}
