/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.CompraController;
import Controller.ContasPagarController;
import Models.CondicaoPagamento;
import Models.ContasPagar;
import Models.Fornecedor;
import Models.Produto;
import apollo.utils.MaskFieldUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class FXMLTelaCompraController implements Initializable {

    @FXML
    private TableColumn<?, ?> coldesc;
    @FXML
    private JFXButton btNovo;
    @FXML
    private JFXButton btConsultar;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private JFXButton btExcluir;
    @FXML
    private JFXButton btFinalizar;
    @FXML
    private Pane pnconteudo;
    @FXML
    private JFXTextField txcodigo;
    @FXML
    private DatePicker dtEmissao;
    @FXML
    private JFXComboBox<Fornecedor> cbFornecedor;
    @FXML
    private JFXButton btForcedor;
    @FXML
    private DatePicker dtDtvenc;
    @FXML
    private JFXComboBox<CondicaoPagamento> cbCondPgto;
    @FXML
    private JFXTextField txQuantParc;
    @FXML
    private JFXButton btGerar;
    @FXML
    private JFXTextField txDias;
    @FXML
    private TableView<ContasPagar> tabelaParcelas;
    @FXML
    private TableColumn<ContasPagar, String> colparc;
    @FXML
    private TableColumn<ContasPagar, String> colvenc;
    @FXML
    private TableColumn<ContasPagar, Double> colvalor;
    @FXML
    private JFXTextField txValor;
    @FXML
    private JFXComboBox<Produto> cbProduto;
    @FXML
    private JFXButton btProduto;
    @FXML
    private JFXTextField txQuant;
    @FXML
    private TableView<?> tabelaProd;
    @FXML
    private TableColumn<?, ?> colquant;
    @FXML
    private TableColumn<?, ?> colunitario;
    @FXML
    private TableColumn<?, ?> coltotal;
    CompraController controller = new CompraController();
    ContasPagarController controllerCP = new ContasPagarController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colparc.setCellValueFactory(new PropertyValueFactory("parcela"));
        colvalor.setCellValueFactory(new PropertyValueFactory("valor"));
        colvenc.setCellValueFactory(new PropertyValueFactory("vencimento"));
        MaskFieldUtil.monetaryField(txValor);
        estadoInicial();
    }

    private void estadoInicial() {

        btFinalizar.setDisable(true);
        btConsultar.setDisable(false);
        btCancelar.setDisable(false);
        btNovo.setDisable(false);
        btExcluir.setDisable(true);

        if (tabelaParcelas != null) {
            tabelaParcelas.getItems().clear();
        }
        if (tabelaProd != null) {
            tabelaProd.getItems().clear();
        }

        ObservableList<Node> componentes = pnconteudo.getChildren();//”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl)//textfield, textarea e htmleditor
            {
                ((TextInputControl) n).setText("");
            }
        }

        cbCondPgto.getItems().clear();
        cbFornecedor.getItems().clear();
        cbProduto.getItems().clear();
        dtEmissao.setValue(LocalDate.now());
        dtDtvenc.setValue(LocalDate.now());
        CarregaCondPgto();
        CarregaFornecedor();

        pnconteudo.setDisable(true);
    }

    private void estadoEdicao() {

        pnconteudo.setDisable(false);
        cbCondPgto.setDisable(false);
        cbFornecedor.setDisable(false);
        btFinalizar.setDisable(false);
        btCancelar.setDisable(false);
        btConsultar.setDisable(false);
        btNovo.setDisable(false);
        dtEmissao.setValue(LocalDate.now());
        dtDtvenc.setValue(LocalDate.now());
        btExcluir.setDisable(true);
        dtEmissao.requestFocus();
        tabelaParcelas.setDisable(false);
        tabelaProd.setDisable(false);

    }

    public void CarregaCondPgto() {
        cbCondPgto.setItems(FXCollections.observableArrayList(controllerCP.CarregaCondPgto()));
    }

    public void CarregaFornecedor() {
        cbFornecedor.setItems(FXCollections.observableArrayList(controller.CarregaFornecedor()));
    }

    public void CarregaProdutos(int codFonecedor) {
         cbProduto.setItems(FXCollections.observableArrayList(controller.CarregarProduto(codFonecedor)));
    }

    @FXML
    private void Novo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void Cosnultar(ActionEvent event) {
    }

    @FXML
    private void Cancelar(ActionEvent event) {
        if (!pnconteudo.isDisabled())//encontra em estado de edição
        {
            estadoInicial();
        } else {
            btCancelar.getScene().getWindow().hide();//fecha a janela
        }

    }

    @FXML
    private void Excluir(ActionEvent event) {
    }

    @FXML
    private void Finalizar(ActionEvent event) {
    }

    @FXML
    private void CarregaTipo(Event event) {
    }

    @FXML
    private void AddForcedor(ActionEvent event) {
    }

    @FXML
    private void habilitaCampos(ActionEvent event) {
    }

    @FXML
    private void Gerar(ActionEvent event) {
    }

    @FXML
    private void AddProduto(ActionEvent event) {
    }

    @FXML
    private void carregarProdutos(ActionEvent event) {
    }

    @FXML
    private void HabilitaProduto(ActionEvent event) {
        if (cbFornecedor.getSelectionModel().getSelectedItem() != null) {
            cbProduto.setDisable(false);
            btProduto.setDisable(false);
            txQuant.setDisable(false);
            txValor.setDisable(false);
            CarregaProdutos(cbFornecedor.getSelectionModel().getSelectedItem().getCodigo());
        }
         

    }

}
