/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Erros.Erros;
import Models.Categoria;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;

/**
 *
 * @author paulo
 */
public class CategoriaController {
    
    Erros msg = new Erros();

    public void carregaTabela(String Filtro, TableView<Categoria> tabela) {

        Categoria c = new Categoria();
        List<Categoria> res = c.get(Filtro);
        ObservableList<Categoria> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }

    public boolean gravar(int cod, String desc) {
        Categoria c = new Categoria(cod, desc);
        return c.gravar(c);
    }

    public void alterar(JFXTextField txcod, JFXTextField txDescricao, TableView<Categoria> tabela) {

        Categoria c = tabela.getSelectionModel().getSelectedItem();
        c = c.get(c.getCodigo());
        txcod.setText("" + c.getCodigo());
        txDescricao.setText("" + c.getDescricao());
    }

    public void evtTabela(TableView<Categoria> tabela, JFXButton btalterar, JFXButton btapagar) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    public boolean alterar(int cod, String desc) {
        Categoria aux = new Categoria(cod, desc);

        return aux.alterar(aux);
    }

    public void apagar(TableView<Categoria> tabela) {
        Categoria aux = new Categoria();
        aux = tabela.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirmar exclusão?");
        if (a.showAndWait().get() == ButtonType.OK) {

            if (aux.apagar(aux.getCodigo())) {
                msg.Affirmation("Apollo Informa:", "Exclusão feita com sucesso");              
            } else {
                msg.Error("Erro ", "Exclusão não realizada, existem produtos nesta categoria");
            }
        }

    }

    public void clkPesquisar(String filtro, TableView<Categoria> tabela) {
       carregaTabela(filtro, tabela);
    }
}
