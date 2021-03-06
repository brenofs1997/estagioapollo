/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.TipoDespesaController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import Erros.Erros;
import Models.TipoDespesa;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TelaTipoDespesaController implements Initializable {

    @FXML
    private Pane pnbtn;
    @FXML
    private JFXButton btnovo;
    @FXML
    private JFXButton btalterar;
    @FXML
    private JFXButton btapagar;
    @FXML
    private JFXButton btconfirmar;
    @FXML
    private JFXButton btcancelar;
    @FXML
    private Pane pnpesquisa;
    @FXML
    private JFXTextField txpesquisar;
    @FXML
    private JFXButton btpesquisar;
    @FXML
    private TableView<TipoDespesa> tabela;
    @FXML
    private TableColumn<TipoDespesa, Integer> colcod;
    @FXML
    private TableColumn<TipoDespesa, String> coldesc;
    @FXML
    private Pane pndados;
    @FXML
    private JFXTextField txcod;
    @FXML
    private JFXTextField txDescricao;
    TipoDespesaController tipCon = new TipoDespesaController();
    Erros msg = new Erros();

    @FXML
    private void clknovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkalterar(ActionEvent event) {
        estadoEdicao();
        tipCon.alterar(txcod, txDescricao, tabela);
    }

    @FXML
    private void clkapagar(ActionEvent event) {
        tipCon.apagar(tabela);
        tipCon.carregaTabela("", tabela);
    }

    @FXML
    private void clkconfirmar(ActionEvent event) {
        int erro = 0, cod = 0;
        String desc = "";
        LimparCampos();

        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (NumberFormatException e) {
            cod = 0;
        }
        if (txDescricao.getText().isEmpty()) {
            msg.campoVazio(txDescricao, "Campo não pode ser vazio");
            erro++;
        } else {
            desc = txDescricao.getText();
        }
        if (erro == 0) {
            if (cod == 0) {
                if (tipCon.gravar(cod, desc)) {
                    msg.Affirmation("Apollo Informa:", "Gravação feita com sucesso");
                    estadoOriginal();
                } else {
                    msg.Error("Erro ", "Despesa não gravada");
                }

            } else {

                if (tipCon.alterar(cod, desc)) {
                    msg.Affirmation("Apollo Informa:", "Alteração feita com sucesso");
                    estadoOriginal();
                } else {
                    msg.Error("Erro ", "Despesa não alterada");
                }
            }
        }

    }

    @FXML
    private void clkcancelar(ActionEvent event) {
        if (!pndados.isDisabled())//encontra em estado de edição
        {
            estadoOriginal();
        } else {
            btnovo.getScene().getWindow().hide();//fecha a janela
        }
    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        String filtro=" descricao ilike '%"+txpesquisar.getText()+"%'";
        tipCon.clkPesquisar(filtro,tabela);
    }

    @FXML
    private void evtTabela(MouseEvent event) {

        tipCon.evtTabela(tabela, btalterar, btapagar);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        coldesc.setCellValueFactory(new PropertyValueFactory("descricao"));
        estadoOriginal();
    }

    public void estadoOriginal() {
        pnpesquisa.setDisable(false);
        pndados.setDisable(true);
        btconfirmar.setDisable(true);
        btcancelar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        btnovo.setDisable(false);
        txDescricao.resetValidation();
        ObservableList<Node> componentes = pndados.getChildren();//”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl)//textfield, textarea e htmleditor
            {
                ((TextInputControl) n).setText("");
            }
            if (n instanceof ComboBox) {
                ((ComboBox) n).getSelectionModel().select(0);
            }
        }
        tipCon.carregaTabela("", tabela);
    }

    public void estadoEdicao() {
        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txDescricao.requestFocus();
        txpesquisar.setText("");
        txDescricao.resetValidation();

    }

    public void LimparCampos() {
        txDescricao.resetValidation();
        txpesquisar.resetValidation();

    }
}
