/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Ajuda.Ajuda;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

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
    private TableColumn<itens_Compra, Produto> coldesc;
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
    DecimalFormat formato = new DecimalFormat("#.##");
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
    @FXML
    private JFXButton btRetirarProd;
    @FXML
    private JFXButton btAjuda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabelaProd.setEditable(true);
        colquant.setCellValueFactory(new PropertyValueFactory("quantidade"));
        colunitario.setCellValueFactory(new PropertyValueFactory("unitario"));
        coltotal.setCellValueFactory(new PropertyValueFactory("total"));
        coldesc.setCellValueFactory(new PropertyValueFactory("produto"));
        colparc.setCellValueFactory(new PropertyValueFactory("parcela"));
        colvalor.setCellValueFactory(new PropertyValueFactory("valor"));
        colvenc.setCellValueFactory(new PropertyValueFactory("vencimento"));

        colquant.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colunitario.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        coltotal.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        //coldesc.setCellFactory(TextFieldTableCell.forTableColumn());
        colparc.setCellFactory(TextFieldTableCell.forTableColumn());
        colvalor.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colvenc.setCellFactory(TextFieldTableCell.forTableColumn());
        MaskFieldUtil.monetaryField(txValor);
        MaskFieldUtil.numericField(txQuant);
        MaskFieldUtil.numericField(txQuantParc);
        MaskFieldUtil.numericField(txDias);
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
            listaParcela.clear();
        }
        if (tabelaProd != null) {
            tabelaProd.getItems().clear();
            itensCompra.clear();
        }

        ObservableList<Node> componentes = pnconteudo.getChildren();//”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl)//textfield, textarea e htmleditor
            {
                ((TextInputControl) n).setText("");
            }
        }
        txcodigo.setText("");
        txQuant.setText("");
        txValor.setText("");
        txDias.setText("");
        txQuantParc.setText("");
        lbTotal.setText("0,0");
        cbCondPgto.getItems().clear();
        cbFornecedor.getItems().clear();
        cbProduto.getItems().clear();
        dtEmissao.setValue(LocalDate.now());
        dtDtvenc.setValue(LocalDate.now());
        cbFornecedor.resetValidation();
        txQuant.resetValidation();
        txQuantParc.resetValidation();
        txDias.resetValidation();
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
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            if (!compra.verificarParcelaPaga(compra)) {
                if (controller.excluir(compra)) {
                    msg.Affirmation("Excluido com sucesso", "Compra excluida!");
                    compra = null;
                    estadoInicial();
                } else {
                    msg.Error("Erro ao excluir!", "Problemas ao excluir");

                }
            } else {
                msg.Error("Erro ao excluir!", "Há parcelas pagas!");
            }
        }
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

        if (tabelaParcelas.getItems().isEmpty()) {
            erro = 1;
            msg.Affirmation("Apollo Informa:", "Não há parcelas Geradas");
        }

        if (tabelaProd.getItems().isEmpty()) {
            erro = 1;
            msg.Affirmation("Apollo Informa:", "Adicione produtos na lista");
        }

        if (cbFornecedor.getSelectionModel().getSelectedItem() == null) {
            erro = 1;
            msg.campoVazioCbx(cbFornecedor);
        }

        if (erro == 0) {
            if (cod == 0) {
                try {
                    if (controller.gravar(listaParcela, itensCompra, func, cod, cbFornecedor, java.sql.Date.valueOf(dtEmissao.getValue()), cbCondPgto, nf.parse(lbTotal.getText()).doubleValue())) {
                        msg.Affirmation("Apollo Informa:", "Gravação feita com sucesso");
                        estadoInicial();
                    } else {
                        msg.Error("Erro ", "Gravação não realizada");

                    }
                } catch (ParseException ex) {
                    Logger.getLogger(FXMLTelaCompraController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

                if (controller.excluirAlt(tabelaProd.getSelectionModel().getTableView().getItems().get(0).getCompra())) {

                    try {
                        if (controller.alterar(listaParcela, itensCompra, func, cod, cbFornecedor, java.sql.Date.valueOf(dtEmissao.getValue()), cbCondPgto, nf.parse(lbTotal.getText()).doubleValue())) {
                            msg.Affirmation("Apollo Informa:", "Alteração feita com sucesso");
                            estadoInicial();
                        } else {
                            msg.Error("Erro ", "Alteração não realizada");
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(FXMLTelaCompraController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
    }

    @FXML
    private void CarregaTipo(Event event) {
    }

    @FXML
    private void AddForcedor(ActionEvent event) {
        try {
            Parent inicial = FXMLLoader.load(getClass().getResource("TelaFornecedorCadastro.fxml"));
            Scene scene = new Scene(inicial);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BEM-VINDO: AO CADASTRO DE FORNECEDOR");
            // stage.setMaximized(true);

            //Close authentication Window
            //Show main Window
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void habilitaCampos(ActionEvent event) {
        String cond = "";

        if (cbCondPgto.getSelectionModel().getSelectedItem() != null) {
            txDias.setDisable(false);
            txQuantParc.setDisable(false);
            cond = cbCondPgto.getSelectionModel().getSelectedItem().getDescricao();
            if (cond.toUpperCase().equals("DINHEIRO") || cond.toUpperCase().equals("DÉBITO")) {
                txDias.setDisable(true);
                txQuantParc.setDisable(true);
                txDias.setText("");
                txQuantParc.setText("");
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
        int dias = 0, quant = 1, cod = 0, erro = 0;
        String cond = "";
        NumberFormat nf = new DecimalFormat("#,###.00");
        try {
            cod = Integer.parseInt(txcodigo.getText());

        } catch (NumberFormatException e) {
            cod = 0;

        }
        if (tabelaProd.getItems().isEmpty()) {
            erro = 1;
            msg.Affirmation("Apollo Informa:", "Adicione produtos na lista");
        } else {
            if (cbCondPgto.getSelectionModel().getSelectedItem() != null) {

                cond = cbCondPgto.getSelectionModel().getSelectedItem().getDescricao();

                try {
                    dias = Integer.parseInt(txDias.getText());
                } catch (NumberFormatException e) {
                    if (cond.toUpperCase().equals("DINHEIRO") || cond.toUpperCase().equals("DÉBITO")) {
                        dias = 0;
                        txDias.setText("");
                    }
                }

                try {
                    quant = Integer.parseInt(txQuantParc.getText());
                } catch (NumberFormatException e) {

                    if (cond.toUpperCase().equals("DINHEIRO") || cond.toUpperCase().equals("DÉBITO")) {
                        quant = 1;
                        txQuantParc.setText("");
                    }
                }

                if (!cond.toUpperCase().equals("DINHEIRO") || !cond.toUpperCase().equals("DÉBITO")) {
                    msg.campoVazio(txDias);
                    msg.campoVazio(txQuantParc);

                    if (txDias.getText().isEmpty() || txQuantParc.getText().isEmpty()) {
                        erro = 1;
                    }

                    if (dias == 0 && !txDias.getText().isEmpty()) {
                        msg.Error("Apollo Informa:", "Campo dias não pode ser 0!");
                        erro = 1;
                    } else if (dias > 31) {
                        msg.Error("Apollo Informa:", "Campo dias não pode mais que 31!");
                        erro = 1;
                    } else if (dias < 0) {
                        msg.Error("Apollo Informa:", "Campo dias não pode ser negativa!");
                        erro = 1;
                    }

                    if (quant == 0 && !txQuantParc.getText().isEmpty()) {
                        msg.Error("Apollo Informa:", "quantidade não pode ser 0!");
                        erro = 1;
                    } else if (quant < 0) {
                        msg.Error("Apollo Informa:", "quantidade não pode ser negativa!");
                        erro = 1;
                    }
                }

                if (erro == 0) {
                    try {

                        total = nf.parse(lbTotal.getText()).doubleValue();

                        listaParcela.clear();
                        listaParcela = controllerCP.gerarParcelas(cbCondPgto, null, total, dtEmissao, dtDtvenc, dias, quant, cod, func);
                        atualizarTabela();
                    } catch (ParseException ex) {
                        Logger.getLogger(TelaLancarDespesasController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                msg.Error("Apollo Informa:", "Forma de pagamento não Selecionado!");
            }
        }
    }

    public void atualizarTabelaItens() {

        ObservableList<itens_Compra> prod_v = FXCollections.observableArrayList(itensCompra);
        tabelaProd.getItems().clear();
        tabelaProd.setItems(prod_v);
        itensCompra.addAll(prod_v);

    }

    @FXML
    private void AddProduto(ActionEvent event) {
        Double uni = 0.0;
        int quant = 0;
        if (cbProduto.getSelectionModel().getSelectedItem() != null) {
            if (!txValor.getText().isEmpty()) {
                try {
                    uni = nf.parse(txValor.getText()).doubleValue();

                } catch (ParseException ex) {
                    Logger.getLogger(FXMLTelaCompraController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!txQuant.getText().isEmpty()) {
                quant = Integer.parseInt(txQuant.getText());
                if (quant > 0) {
                    itensCompra = controller.addProduto(cbProduto.getSelectionModel().getSelectedItem(), tabelaProd, quant, uni, lbTotal);
                    atualizarTabelaItens();
                } else if (quant == 0) {
                    msg.Error("Apollo Informa:", "quantidade não pode ser 0!");
                } else if (quant < 0) {
                    msg.Error("Apollo Informa:", "quantidade não pode ser negativa!");
                }
            } else {
                msg.campoVazio(txQuant);
            }
        } else {
            msg.Error("Apollo Informa:", "Produto não Selecionado!");
        }

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
            btRetirarProd.setDisable(false);

            CarregaProdutos(cbFornecedor.getSelectionModel().getSelectedItem().getCodigo());
        }

    }

    @FXML
    private void Consultar(ActionEvent event) throws IOException {
        Parent inicial = FXMLLoader.load(getClass().getResource("CompraConsulta.fxml"));
        Scene scene = new Scene(inicial);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BEM-VINDO: A BUSCA DE COMPRAS");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        if (getCompra() != null) {
            controller.carregaCampos(pnconteudo, getCompra(), cbCondPgto, cbFornecedor, dtEmissao, dtDtvenc, txDias, txQuantParc, txcodigo, btFinalizar, btExcluir, btNovo, tabelaProd, tabelaParcelas, lbTotal, btRetirarProd);

        }
    }

    @FXML
    private void editarTabela(TableColumn.CellEditEvent<itens_Compra, Integer> event) {
        int id = 0;
        itens_Compra it = new itens_Compra();

        it = tabelaProd.getSelectionModel().getSelectedItem();

        if (it != null) {
            id = itensCompra.indexOf(it);
            itensCompra.get(id).setQuantidade(event.getNewValue());
            atualizarTabelaItens();
        }

    }

    @FXML
    private void tirarProduto(ActionEvent event) {
        double total = 0.0;

        if (tabelaProd.getSelectionModel().getSelectedItem() != null) {
            try {
                total = nf.parse(lbTotal.getText()).doubleValue();
                total -= tabelaProd.getSelectionModel().getSelectedItem().getTotal();
                total = nf.parse(formato.format(total)).doubleValue();
                lbTotal.setText(nf.format(total));

            } catch (ParseException ex) {
                Logger.getLogger(FXMLTelaCompraController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            itensCompra.remove(tabelaProd.getSelectionModel().getSelectedIndex());
            atualizarTabelaItens();

        } else {
            msg.Affirmation("Apollo Informa:", "Item não Selecionado!");
        }

    }

    @FXML
    private void atualizaFornecedores(MouseEvent event) {
        CarregaFornecedor();
    }

    @FXML
    private void Ajuda(ActionEvent event) {
        Ajuda a = new Ajuda();
        a.Ajuda("Compra.htm");
    }

}
