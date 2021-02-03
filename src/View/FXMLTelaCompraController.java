/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.CompraController;
import Controller.ContasPagarController;
import Erros.Erros;
import Models.Compra;
import Models.CondicaoPagamento;
import Models.ContasPagar;
import Models.Fornecedor;
import Models.Funcionario;
import Models.Produto;
import Models.itens_Compra;
import apollo.utils.MaskFieldUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    public static List<itens_Compra> itensCompra = new ArrayList();

    public static List<itens_Compra> getItensCompra() {
        return itensCompra;
    }

    public static void setItensCompra(List<itens_Compra> itensCompra) {
        FXMLTelaCompraController.itensCompra = itensCompra;
    }
    CompraController controller = new CompraController();
    ContasPagarController controllerCP = new ContasPagarController();
    NumberFormat nf = new DecimalFormat("#,###.00");
    public static List<ContasPagar> listaParcela = new ArrayList();
    public static Funcionario func;
    Erros msg = new Erros();
    public static Funcionario getFunc() {
        return func;
    }

    public static void setFunc(Funcionario func) {
        FXMLTelaCompraController.func = func;
    }
    @FXML
    private Label lbTotal;
     public static Compra compra;

    public static Compra getCompra() {
        return compra;
    }

    public static void setCompra(Compra compra) {
        FXMLTelaCompraController.compra = compra;
    }
     
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
        int dias = 0, quant = 1, cod = 0, erro = 0;
        NumberFormat nf = new DecimalFormat("#,###.00");
        try {
            cod = Integer.parseInt(txcodigo.getText());

        } catch (NumberFormatException e) {
            cod = 0;

        }
        if (erro == 0) {
            if (cod == 0) {
                try {
                    if (controller.gravar(listaParcela,itensCompra,func,cod,cbFornecedor,java.sql.Date.valueOf(dtEmissao.getValue()),cbCondPgto,nf.parse(lbTotal.getText()).doubleValue())) {
                        msg.Affirmation("Apollo Informa:", "Gravação feita com sucesso");
                        estadoInicial();
                    } else {
                        msg.Error("Erro ", "Gravação não realizada");
                        
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(FXMLTelaCompraController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
               // if (controller.apagar(tabela)) {
                //    if (controller.gravar(listaParcela)) {
                 //       msg.Affirmation("Apollo Informa:", "Alteração feita com sucesso");
                 //       estadoInicial();
                  //  } else {
                 //       msg.Error("Erro ", "Alteração não realizada");
                        
                   //}
               
            }
        }
    }

    @FXML
    private void CarregaTipo(Event event) {
    }

    @FXML
    private void AddForcedor(ActionEvent event) {
    }

    @FXML
    private void habilitaCampos(ActionEvent event) {
        String cond = "";

        if (cbCondPgto.getSelectionModel().getSelectedItem() != null) {
            cond = cbCondPgto.getSelectionModel().getSelectedItem().getDescricao();
            if (cond.toUpperCase().equals("DINHEIRO") || cond.toUpperCase().equals("DÉBITO")) {
                txDias.setDisable(true);
                txQuantParc.setDisable(true);
            }

        }
    }

    private void atualizarTabela() {
        try {
            ObservableList<ContasPagar> contr;
            contr = FXCollections.observableArrayList(listaParcela);
            tabelaParcelas.setItems(contr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void Gerar(ActionEvent event) {
        Double total = 0.0;
        int dias = 0, quant = 1, cod = 0;
        NumberFormat nf = new DecimalFormat("#,###.00");
        try {
            cod = Integer.parseInt(txcodigo.getText());

        } catch (NumberFormatException e) {
            cod = 0;

        }
        try {
            dias = Integer.parseInt(txDias.getText());
            quant = Integer.parseInt(txQuantParc.getText());
        } catch (NumberFormatException e) {

            dias = 0;
            quant = 1;
            txDias.setText("");
            txQuantParc.setText("");
        }

        try {

            total = nf.parse(lbTotal.getText()).doubleValue();

            listaParcela.clear();
            listaParcela = controllerCP.gerarParcelas(cbCondPgto, null, total, dtEmissao, dtDtvenc, dias, quant, cod, func);
            atualizarTabela();
        } catch (ParseException ex) {
            Logger.getLogger(TelaLancarDespesasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void AddProduto(ActionEvent event) {
        Double uni = 0.0;
        int quant = 0;
        if (!txValor.getText().isEmpty()) {
            try {
                uni = nf.parse(txValor.getText()).doubleValue();
            } catch (ParseException ex) {
                Logger.getLogger(FXMLTelaCompraController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!txQuant.getText().isEmpty()) {
            quant = Integer.parseInt(txQuant.getText());
        }
        controller.addProduto( cbProduto.getSelectionModel().getSelectedItem(), tabelaProd, quant, uni, lbTotal);

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

    @FXML
    private void Consultar(ActionEvent event) throws IOException {
         Parent inicial = FXMLLoader.load(getClass().getResource("CompraConsulta.fxml"));
        Scene scene = new Scene(inicial);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BEM-VINDO: A BUSCA DE DESPESAS");

        stage.showAndWait();
        if (getCompra() != null) {
            controller.carregaCampos(pnconteudo, getCompra(), cbCondPgto, cbFornecedor, dtEmissao, dtDtvenc, txDias, txQuantParc, txcodigo, btFinalizar, btExcluir, btNovo, tabelaProd,tabelaParcelas);
        }
    }

}
