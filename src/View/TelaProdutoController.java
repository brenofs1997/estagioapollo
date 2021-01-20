/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.CategoriaController;
import Controller.ProdutoController;
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

import Erros.Erros;
import Models.Categoria;
import Models.Produto;
import apollo.utils.MaskFieldUtil;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

import javafx.scene.Node;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaProdutoController implements Initializable {

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
    private TableView<Produto> tabela;
    @FXML
    private TableColumn<Produto, Integer> colcod;
    @FXML
    private TableColumn<Produto, String> coldesc;
    @FXML
    private Pane pndados;
    @FXML
    private JFXTextField txcod;
    @FXML
    private JFXTextField txDescricao;
    @FXML
    private JFXCheckBox cbAtivo;
    @FXML
    private JFXTextField txPreco;
    @FXML
    private JFXTextField txQtde;
    @FXML
    private JFXComboBox<Categoria> cbCategoria;
    Erros msg = new Erros();
    ProdutoController prodControl = new ProdutoController();
    

   @Override
    public void initialize(URL location, ResourceBundle resources) {
        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        coldesc.setCellValueFactory(new PropertyValueFactory("descricao"));
        MaskFieldUtil.monetaryField(txPreco);
        estadoOriginal();
    }
    @FXML
    private void clknovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkalterar(ActionEvent event) {
        estadoEdicao();
        prodControl.alterar(txcod, txDescricao,txPreco,txQtde,cbAtivo,cbCategoria, tabela);
    }

    @FXML
    private void clkapagar(ActionEvent event) {
        prodControl.apagar(tabela);
        prodControl.carregaTabela("", tabela);
    }

    @FXML
    private void clkconfirmar(ActionEvent event) {
        int erro = 0, cod = 0,qtde=0;
        Double preco=0.0;
        String desc = "";
        Boolean ativo=false;
        LimparCampos();
        NumberFormat nf = new DecimalFormat("#,###.00");

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
        if (txPreco.getText().isEmpty()) {
            msg.campoVazio(txPreco, "Campo não pode ser vazio");
            erro++;
        } else {
            try {
                preco = nf.parse(txPreco.getText()).doubleValue();
            } catch (ParseException ex) {
                Logger.getLogger(TelaProdutoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (txQtde.getText().isEmpty()) {
            msg.campoVazio(txQtde, "Campo não pode ser vazio");
            erro++;
        } else {
            qtde =Integer.parseInt(txQtde.getText());
        }
        if(cbAtivo.isSelected())
            ativo =true;
        
         if (cbCategoria.getSelectionModel().getSelectedItem()==null) {
            msg.cbVazioCategoria(cbCategoria);
            erro++;
        } else {
            qtde =Integer.parseInt(txQtde.getText());
        }
        if (erro == 0) {
            if (cod == 0) {
                if (prodControl.gravar(cod, desc,preco,qtde,ativo, cbCategoria)) {
                    msg.Affirmation("Apollo Informa:", "Gravação feita com sucesso");
                    estadoOriginal();
                } else {
                    msg.Error("Erro ", "Categoria não gravada");
                }

            } else {

                if (prodControl.alterarP(txcod, txDescricao, txPreco, txQtde, cbAtivo, cbCategoria, tabela)) {
                    msg.Affirmation("Apollo Informa:", "Alteração feita com sucesso");
                    estadoOriginal();
                } else {
                    msg.Error("Erro ", "Categoria não alterada");
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
        prodControl.clkPesquisar(filtro,tabela);
    }

    @FXML
    private void evtTabela(MouseEvent event) {

        prodControl.evtTabela(tabela, btalterar, btapagar);
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
        prodControl.carregaTabela("", tabela);
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
        prodControl.carregarCategoria(cbCategoria);

    }

    public void LimparCampos() {
        txDescricao.resetValidation();
        txpesquisar.resetValidation();

    }
}
