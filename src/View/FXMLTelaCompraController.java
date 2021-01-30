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
import Models.itens_Compra;
import apollo.utils.MaskFieldUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private TableView<itens_Compra> tabelaProd;
    @FXML
    private TableColumn<itens_Compra, Integer> colquant;
    @FXML
    private TableColumn<itens_Compra, Double> colunitario;
    @FXML
    private TableColumn<itens_Compra, Double> coltotal;
    @FXML
    private TableColumn<itens_Compra, String> coldesc;
    private static List<itens_Compra> itensCompra = new ArrayList();
    CompraController controller = new CompraController();
    ContasPagarController controllerCP = new ContasPagarController();
    NumberFormat nf = new DecimalFormat("#,###.00");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colquant.setCellValueFactory(new PropertyValueFactory("quantidade"));
        colunitario.setCellValueFactory(new PropertyValueFactory("unitario"));
        coltotal.setCellValueFactory(new PropertyValueFactory("total"));
        coldesc.setCellValueFactory(new PropertyValueFactory("produto"));
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
        Double uni = 0.0;
        int quant=0;
        if (!txValor.getText().isEmpty()) {
            try {
                uni = nf.parse(txValor.getText()).doubleValue();
            } catch (ParseException ex) {
                Logger.getLogger(FXMLTelaCompraController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!txQuant.getText().isEmpty()) {
          quant=Integer.parseInt(txQuant.getText());
        }
        controller.addProduto(itensCompra, cbProduto.getSelectionModel().getSelectedItem(), tabelaProd, quant, uni);
        
    }

    @FXML
    private void carregarProdutos(ActionEvent event) {

        if (cbProduto.getSelectionModel().getSelectedItem() != null) {
            txValor.setText(nf.format(cbProduto.getSelectionModel().getSelectedItem().getPreco()));

        }
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
