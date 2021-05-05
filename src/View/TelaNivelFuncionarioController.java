/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Ajuda.Ajuda;
import Controller.NivelFuncionarioController;
import Erros.Erros;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import Models.NivelFuncionario;
import Models.Restricoes;
import apollo.utils.MaskFieldUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.ObservableList;

import javafx.scene.Node;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaNivelFuncionarioController implements Initializable {

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
    private TableView<NivelFuncionario> tabela;
    @FXML
    private TableColumn<NivelFuncionario, Integer> colcod;
    @FXML
    private TableColumn<NivelFuncionario, String> coldesc;
    @FXML
    private Pane pndados;
    @FXML
    private JFXTextField txcod;
    @FXML
    private JFXTextField txDescricao;
    @FXML
    private JFXComboBox<Restricoes> cbRestricao;
    NivelFuncionarioController tpControl = new NivelFuncionarioController();
    Erros msg = new Erros();
    @FXML
    private JFXButton btAjuda;

    @FXML
    private void clknovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkalterar(ActionEvent event) {
        estadoEdicao();
        tpControl.alterar(txcod, txDescricao, tabela, cbRestricao);
    }

    @FXML
    private void clkapagar(ActionEvent event) {
        tpControl.apagar(tabela);
        tpControl.carregaTabela("", tabela);
    }

    public void validarCb(JFXComboBox campo) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        campo.resetValidation();
        campo.getValidators().add(validator);
        validator.setMessage("Campo não pode estar vazio!");
        campo.validate();
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
        if (cbRestricao.getSelectionModel().isEmpty()) {
            validarCb(cbRestricao);
            erro = 1;
        }

        if (erro == 0) {
            if (cod == 0) {
                if (tpControl.gravar(cod, desc, cbRestricao)) {
                    msg.Affirmation("Apollo Informa:", "Gravação feita com sucesso");
                    estadoOriginal();
                } else {
                    msg.Error("Erro ", "Nivel de Funcionario não gravado");
                }

            } else {

                if (tpControl.alterar(cod, desc, cbRestricao)) {
                    msg.Affirmation("Apollo Informa:", "Alteração feita com sucesso");
                    estadoOriginal();
                } else {
                    msg.Error("Erro ", "Nivel de Funcionario não alterado");
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
        String filtro = " descricao ilike '%" + txpesquisar.getText() + "%'";
        tpControl.clkPesquisar(filtro, tabela);
    }

    @FXML
    private void evtTabela(MouseEvent event) {
        tpControl.evtTabela(tabela, btalterar, btapagar);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MaskFieldUtil.maxField(txDescricao, 30);
        MaskFieldUtil.maxField(txpesquisar, 30);
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
        tpControl.carregaTabela("", tabela);
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
        tpControl.carregaRestricoes(cbRestricao);

    }

    public void LimparCampos() {
        txDescricao.resetValidation();
        txpesquisar.resetValidation();

    }

    @FXML
    private void Ajuda(ActionEvent event) {
        Ajuda a = new Ajuda();
        a.Ajuda("CadastrodeNiveldeFuncionario.htm");
    }

}
