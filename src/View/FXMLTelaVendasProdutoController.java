/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Ajuda.Ajuda;
import Controller.ContasPagarController;
import Controller.VendaController;
import Erros.Erros;
import Models.Categoria;
import Models.Cliente;
import Models.CondicaoPagamento;
import Models.Funcionario;
import Models.Produto;
import Models.Venda;
import Models.itens_Venda;
import static View.FXMLTelaCompraController.itensCompra;
import apollo.utils.MaskFieldUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
 * @author paulo
 */
public class FXMLTelaVendasProdutoController implements Initializable {

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
    private JFXComboBox<CondicaoPagamento> cbCondPgto;
    @FXML
    private JFXTextField txValor;
    @FXML
    private JFXComboBox<Produto> cbProduto;
    @FXML
    private JFXButton btProduto;
    @FXML
    private JFXTextField txQuant;
    @FXML
    private TableView<itens_Venda> tabelaProd;
    @FXML
    private TableColumn<itens_Venda, Integer> colquant;
    @FXML
    private TableColumn<itens_Venda, Double> colunitario;
    @FXML
    private TableColumn<itens_Venda, Double> coltotal;
    @FXML
    private TableColumn<itens_Venda, Produto> coldesc;
    public static List<itens_Venda> itensVenda = new ArrayList();

    public static List<itens_Venda> getItensVenda() {
        return itensVenda;
    }

    public static void setItensCompra(List<itens_Venda> itensCompra) {
        FXMLTelaVendasProdutoController.itensVenda = itensCompra;
    }
    VendaController controller = new VendaController();
    ContasPagarController controllerCP = new ContasPagarController();

    NumberFormat nf = new DecimalFormat("#,###.00");
    DecimalFormat formato = new DecimalFormat("#.##");

    public static Funcionario func;
    Erros msg = new Erros();

    public static Funcionario getFunc() {
        return func;
    }

    public static void setFunc(Funcionario func) {
        FXMLTelaVendasProdutoController.func = func;
    }
    @FXML
    private Label lbTotal;
    public static Venda venda;

    public static Venda getVenda() {
        return venda;
    }

    public static void setVenda(Venda venda) {
        FXMLTelaVendasProdutoController.venda = venda;
    }
    @FXML
    private JFXButton btRetirarProd;

    @FXML
    private JFXComboBox<Cliente> cbCliente;
    @FXML
    private JFXButton btCliente;
    @FXML
    private JFXComboBox<Categoria> cbCategoria;
    @FXML
    private JFXCheckBox chkFiado;
    @FXML
    private JFXTextField txtPesqCli;
    @FXML
    private JFXButton btPesqCliente;
    @FXML
    private JFXTextField txtPesqProd;
    @FXML
    private JFXButton btPesqProd;
    @FXML
    private JFXButton btAjuda;

    /**
     * Initializes the controller class.
     */
    @Override
    @SuppressWarnings("empty-statement")
    public void initialize(URL url, ResourceBundle rb) {
        tabelaProd.setEditable(true);
        colquant.setCellValueFactory(new PropertyValueFactory("quantidade"));
        colunitario.setCellValueFactory(new PropertyValueFactory("unitario"));
        coltotal.setCellValueFactory(new PropertyValueFactory("total"));
        coldesc.setCellValueFactory(new PropertyValueFactory("produto"));

        colquant.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colunitario.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        coltotal.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        MaskFieldUtil.monetaryField(txValor);
        MaskFieldUtil.numericField(txQuant);

        estadoInicial();
    }

    @FXML
    private void Novo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void Consultar(ActionEvent event) {
        try {
            Parent inicial = FXMLLoader.load(getClass().getResource("VendaConsulta.fxml"));
            Scene scene = new Scene(inicial);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BEM-VINDO: A CONSULTA DE VENDAS");
            // stage.setMaximized(true);

            //Close authentication Window
            //Show main Window
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
            if (getVenda() != null) {
                controller.carregaCampos(pnconteudo, getVenda(), cbCondPgto, cbCliente, dtEmissao, txcodigo, btFinalizar, btExcluir, btNovo, chkFiado, tabelaProd, lbTotal, btRetirarProd);

            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            if (!venda.verificarParcelaPaga(venda)) {
                if (controller.excluir(venda)) {
                    msg.Affirmation("Excluido com sucesso", "Venda excluida!");
                    venda = null;
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
        int cod = 0, erro = 0;
        NumberFormat nf = new DecimalFormat("#,###.00");
        try {
            cod = Integer.parseInt(txcodigo.getText());

        } catch (NumberFormatException e) {
            cod = 0;

        }
        if (chkFiado.isSelected() && cbCliente.getSelectionModel().getSelectedItem() == null) {
            erro = 1;
            msg.campoVazioCbx(cbCliente);
        }

        if (tabelaProd.getItems().isEmpty()) {
            erro = 1;
            msg.Affirmation("Apollo Informa:", "Adicione produtos na lista");
        }

        if (erro == 0) {
            if (cod == 0) {
                try {
                    if (controller.gravar(itensVenda, getFunc(), cod, cbCliente, java.sql.Date.valueOf(dtEmissao.getValue()), nf.parse(lbTotal.getText()).doubleValue(), chkFiado.isSelected())) {
                        msg.Affirmation("Apollo Informa:", "Gravação feita com sucesso");
                        estadoInicial();
                    } else {
                        msg.Error("Erro ", "Gravação não realizada");

                    }
                } catch (ParseException ex) {
                    Logger.getLogger(FXMLTelaCompraController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

                if (controller.excluirAlt(tabelaProd.getSelectionModel().getTableView().getItems().get(0).getVenda())) {

                    try {
                        if (controller.alterar(itensVenda, func, cod, cbCliente, java.sql.Date.valueOf(dtEmissao.getValue()), nf.parse(lbTotal.getText()).doubleValue(), chkFiado.isSelected())) {
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
    private void atualizaFornecedores(MouseEvent event) {
    }

    private void estadoInicial() {

        btFinalizar.setDisable(true);
        btConsultar.setDisable(false);
        btCancelar.setDisable(false);
        btNovo.setDisable(false);
        btExcluir.setDisable(true);

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

        lbTotal.setText("0,0");
        cbCategoria.getItems().clear();

        cbProduto.getItems().clear();
        dtEmissao.setValue(LocalDate.now());

        txQuant.resetValidation();

        CarregaCategoria();
        CarregaCliente();
        CarregaProdutos(0);
        pnconteudo.setDisable(true);

    }

    private void estadoEdicao() {

        pnconteudo.setDisable(false);
        cbCategoria.setDisable(false);
        cbCliente.setDisable(false);
        btFinalizar.setDisable(false);
        btCancelar.setDisable(false);
        btConsultar.setDisable(false);
        btNovo.setDisable(false);
        dtEmissao.setValue(LocalDate.now());

        btExcluir.setDisable(true);
        dtEmissao.requestFocus();

        tabelaProd.setDisable(false);

    }

    @FXML
    private void HabilitaProduto(ActionEvent event) {
        if (cbCliente.getSelectionModel().getSelectedItem() != null) {
            cbProduto.setDisable(false);
            btProduto.setDisable(false);
            txQuant.setDisable(false);
            txValor.setDisable(false);
            btRetirarProd.setDisable(false);

            CarregaProdutos(0);
        }
    }

    public void CarregaCategoria() {

        cbCategoria.setItems(FXCollections.observableArrayList(controllerCP.CarregaCategoria()));

    }

    public void CarregaCliente() {

        cbCliente.setItems(FXCollections.observableArrayList(controller.CarregaCliente()));

    }

    public void CarregaProdutos(int cod) {

        cbProduto.setItems(FXCollections.observableArrayList(controller.CarregarProduto(cod)));

    }

    @FXML
    private void carregarProdutos(ActionEvent event) {
        if (cbProduto.getSelectionModel().getSelectedItem() != null) {
            txValor.setText(nf.format(cbProduto.getSelectionModel().getSelectedItem().getPreco()));

        }

    }

    @FXML
    private void CarregaTipo(Event event) {
    }

    public void atualizarTabelaItens() {

        ObservableList<itens_Venda> prod_v = FXCollections.observableArrayList(itensVenda);
        itensVenda.clear();
        tabelaProd.getItems().clear();
        tabelaProd.setItems(prod_v);
        itensVenda.addAll(prod_v);

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
            } else {
                msg.campoVazio(txValor);
            }
            if (!txQuant.getText().isEmpty()) {
                quant = Integer.parseInt(txQuant.getText());
                if (quant > 0) {
                    if (controller.verificaEstoque(quant, cbProduto.getSelectionModel().getSelectedItem(),txQuant)) {
                        itensVenda = controller.addProduto(cbProduto.getSelectionModel().getSelectedItem(), tabelaProd, quant, uni, lbTotal);
                        atualizarTabelaItens();
                    } else {
                        msg.Error("Apollo Informa:", "quantidade insuficiente no estoque!");
                    }
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
    private void editarTabela(TableColumn.CellEditEvent<itens_Venda, Integer> event) {
        int id = 0;
        Double total = 0.0;
        itens_Venda it = new itens_Venda();

        it = tabelaProd.getSelectionModel().getSelectedItem();

        if (it != null) {
            id = itensVenda.indexOf(it);
            itensVenda.get(id).setQuantidade(event.getNewValue());
            itensVenda.get(id).setTotal(itensVenda.get(id).getQuantidade() * itensVenda.get(id).getUnitario());

            lbTotal.setText(nf.format(controller.calcTotal()));
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

            itensVenda.remove(tabelaProd.getSelectionModel().getSelectedIndex());
            atualizarTabelaItens();

        } else {
            msg.Affirmation("Apollo Informa:", "Item não Selecionado!");
        }

    }

    @FXML
    private void AddCliente(ActionEvent event) {
        try {
            Parent inicial = FXMLLoader.load(getClass().getResource("TelaClienteCadastro.fxml"));
            Scene scene = new Scene(inicial);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BEM-VINDO: AO CADASTRO DE CLIENTE");
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
    private void carregaProdCategoria(ActionEvent event) {
        if (cbCategoria.getSelectionModel().getSelectedItem() != null) {
            CarregaProdutos(cbCategoria.getSelectionModel().getSelectedItem().getCodigo());
        }
    }

    @FXML
    private void PesqCliente(ActionEvent event) {

        cbCliente.setItems(FXCollections.observableArrayList(controller.PesquisarCliente(txtPesqCli.getText())));
    }

    @FXML
    private void PesqProd(ActionEvent event) {

        cbProduto.setItems(FXCollections.observableArrayList(controller.PesquisarProduto(txtPesqProd.getText())));
    }

    @FXML
    private void Ajuda(ActionEvent event) {
        Ajuda a = new Ajuda();
        a.Ajuda("Venda.htm");
    }

}
