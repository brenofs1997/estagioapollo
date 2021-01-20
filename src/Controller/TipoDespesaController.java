/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Erros.Erros;
import Models.TipoDespesa;
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
public class TipoDespesaController {

    Erros msg = new Erros();

    public void carregaTabela(String Filtro, TableView<TipoDespesa> tabela) {

        TipoDespesa tip = new TipoDespesa();
        List<TipoDespesa> res = tip.get(Filtro);
        ObservableList<TipoDespesa> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }

    public boolean gravar(int cod, String desc) {
        TipoDespesa tip = new TipoDespesa(cod, desc);
        return tip.gravar(tip);
    }

    public void alterar(JFXTextField txcod, JFXTextField txDescricao, TableView<TipoDespesa> tabela) {

        TipoDespesa tip = tabela.getSelectionModel().getSelectedItem();
        tip = tip.get(tip.getCodigo());
        txcod.setText("" + tip.getCodigo());
        txDescricao.setText("" + tip.getDescricao());
    }

    public void evtTabela(TableView<TipoDespesa> tabela, JFXButton btalterar, JFXButton btapagar) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    public boolean alterar(int cod, String desc) {
        TipoDespesa aux = new TipoDespesa(cod, desc);

        return aux.alterar(aux);
    }

    public void apagar(TableView<TipoDespesa> tabela) {
        TipoDespesa aux = new TipoDespesa();
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

    public void clkPesquisar(String filtro, TableView<TipoDespesa> tabela) {
        carregaTabela(filtro, tabela);
    }

}
