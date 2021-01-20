/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Erros.Erros;
import Models.NivelFuncionario;
import Models.Restricoes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
public class NivelFuncionarioController {

    Erros msg = new Erros();

    public void carregaTabela(String Filtro, TableView<NivelFuncionario> tabela) {

        NivelFuncionario tip = new NivelFuncionario();
        List<NivelFuncionario> res = tip.get(Filtro);
        ObservableList<NivelFuncionario> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }

    public boolean gravar(int cod, String desc, JFXComboBox<Restricoes> cbRestricao) {

        Restricoes r = new Restricoes();
        r = cbRestricao.getSelectionModel().getSelectedItem();
        NivelFuncionario tip = new NivelFuncionario(cod, desc, r);
        return tip.salvar(tip);
    }

    public void alterar(JFXTextField txcod, JFXTextField txDescricao, TableView<NivelFuncionario> tabela,JFXComboBox<Restricoes> cbRestricao) {

        NivelFuncionario nv = tabela.getSelectionModel().getSelectedItem();
        nv = nv.get(nv.getCodigo());
        txcod.setText("" + nv.getCodigo());
        txDescricao.setText("" + nv.getDescricao());
        cbRestricao.getSelectionModel().select(0);
        cbRestricao.getSelectionModel().select(nv.getRestricoes());
    }

    public void evtTabela(TableView<NivelFuncionario> tabela, JFXButton btalterar, JFXButton btapagar) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    public boolean alterar(int cod, String desc, JFXComboBox<Restricoes> cbRestricao) {
        Restricoes r = new Restricoes();
        r = cbRestricao.getSelectionModel().getSelectedItem();
        NivelFuncionario aux = new NivelFuncionario(cod, desc, r);

        return aux.alterar(aux);
    }

    public void apagar(TableView<NivelFuncionario> tabela) {
        NivelFuncionario aux = new NivelFuncionario();
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

    public void carregaRestricoes(JFXComboBox<Restricoes> cbRestricao) {
        Restricoes r = new Restricoes();
        List<Restricoes> res = r.get();
        cbRestricao.setItems(FXCollections.observableArrayList(res));
    }

    public void clkPesquisar(String filtro, TableView<NivelFuncionario> tabela) {
        carregaTabela(filtro, tabela);
    }

}
