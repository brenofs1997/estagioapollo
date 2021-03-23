/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ContasPagarController;
import Erros.Erros;
import Models.CondicaoPagamento;
import Models.ContasPagar;
import Models.Funcionario;
import Models.TipoDespesa;
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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author paulo
 */
public class TelaLancarDespesasController implements Initializable {

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
    private JFXTextField txcodigo;
    @FXML
    private DatePicker dtEmissao;
    @FXML
    private JFXTextField txValor;
    @FXML
    private DatePicker dtDtvenc;
    @FXML
    private JFXComboBox<CondicaoPagamento> cbCondPgto;
    @FXML
    private JFXTextField txQuant;
    @FXML
    private TableView<ContasPagar> tabela;
    @FXML
    private TableColumn<ContasPagar, String> colparc;
    @FXML
    private TableColumn<ContasPagar, Date> colvenc;
    @FXML
    private TableColumn<ContasPagar, Double> colvalor;
    public static List<ContasPagar> listaParcela = new ArrayList();
    Erros msg = new Erros();

    public static ContasPagar desp;

    public static ContasPagar getDesp() {
        return desp;
    }

    public static void setDesp(ContasPagar desp) {
        TelaLancarDespesasController.desp = desp;
    }

    public static List<ContasPagar> getListaParcrlad() {
        return listaParcela;
    }

    public static void setListaParcrlad(List<ContasPagar> listaParcrlad) {
        TelaLancarDespesasController.listaParcela = listaParcrlad;
    }
    ContasPagarController controller = new ContasPagarController();
    @FXML
    private JFXButton btGerar;
    @FXML
    private JFXComboBox<TipoDespesa> cbTipo;
    public static Funcionario func;

    public static Funcionario getFunc() {
        return func;
    }

    public static void setFunc(Funcionario func) {
        TelaLancarDespesasController.func = func;
    }
    @FXML
    private JFXTextField txDias;
    @FXML
    private Pane pnconteudo;
    @FXML
    private Label lbTipodesp;
    @FXML
    private Label lbValor;
    @FXML
    private Label lbCondPagamento;
    @FXML
    private Label lbEntreParc;
    @FXML
    private Label lbQtdeParc;

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
        pnconteudo.setDisable(true);
        btFinalizar.setDisable(true);
        btConsultar.setDisable(false);
        btCancelar.setDisable(false);
        btNovo.setDisable(false);
        btExcluir.setDisable(true);

        if (tabela != null) {
            tabela.getItems().clear();
        }

        ObservableList<Node> componentes = pnconteudo.getChildren();//”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl)//textfield, textarea e htmleditor
            {
                ((TextInputControl) n).setText("");
            }
        }
        limparLabel();
        cbCondPgto.getItems().clear();
        cbTipo.getItems().clear();
        dtEmissao.setValue(LocalDate.now());
        dtDtvenc.setValue(LocalDate.now());
        CarregaCondPgto();
        CarregaTipo();

    }

    public void CarregaCondPgto() {
        cbCondPgto.setItems(FXCollections.observableArrayList(controller.CarregaCondPgto()));
    }

    @FXML
    private void CarregaTipo() {
        cbTipo.setItems(FXCollections.observableArrayList(controller.CarregaTipo()));
    }

    public void limparLabel() {
        txcodigo.setText("");
        txDias.setText("");
        txQuant.setText("");
        txValor.setText("");
        cbCondPgto.resetValidation();
        cbTipo.resetValidation();
        lbTipodesp.setText("");
        lbValor.setText("");
        lbCondPagamento.setText("");
        lbEntreParc.setText("");
        lbQtdeParc.setText("");

    }

    private void estadoEdicao() {
        limparLabel();
        pnconteudo.setDisable(false);
        cbCondPgto.setDisable(false);
        btFinalizar.setDisable(false);
        btCancelar.setDisable(false);
        btConsultar.setDisable(false);
        btNovo.setDisable(false);
        dtEmissao.setValue(LocalDate.now());
        dtDtvenc.setValue(LocalDate.now());
        btExcluir.setDisable(true);
        dtEmissao.requestFocus();
        tabela.setDisable(false);

    }

    @FXML
    private void Novo(ActionEvent event) {
        estadoEdicao();

    }

    @FXML
    private void Cosnultar(ActionEvent event) throws IOException {
        Parent inicial = FXMLLoader.load(getClass().getResource("DespesasConsulta.fxml"));
        Scene scene = new Scene(inicial);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BEM-VINDO: A BUSCA DE DESPESAS");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        if (getDesp() != null) {
            controller.carregaCampos(pnconteudo, getDesp(), cbCondPgto, cbTipo, txValor, dtEmissao, dtDtvenc, txDias, txQuant, txcodigo, btFinalizar, btExcluir, btNovo, tabela);
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
            if (!desp.verificarParcelaPaga(desp)) {
                if (controller.excluir(desp)) {
                    msg.Affirmation("Excluido com sucesso", "Despesa excluida!");
                    desp = null;
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
        int erro = 0, cod = 0;
        try {
            cod = Integer.parseInt(txcodigo.getText());
        } catch (NumberFormatException e) {
            cod = 0;
        }
        if (listaParcela.isEmpty()) {
            erro++;
        }
        if (erro == 0) {
            if (cod == 0) {
                if (controller.gravar(listaParcela)) {
                    msg.Affirmation("Apollo Informa:", "Gravação feita com sucesso");
                    estadoInicial();
                } else {
                    msg.Error("Erro ", "Gravação não realizada");

                }
            } else {
                if (controller.apagar(tabela)) {
                    if (controller.gravar(listaParcela)) {
                        msg.Affirmation("Apollo Informa:", "Alteração feita com sucesso");
                        estadoInicial();
                    } else {
                        msg.Error("Erro ", "Alteração não realizada");

                    }
                }
            }
        }

    }

    private void atualizarTabela() {
        try {
            ObservableList<ContasPagar> contr;
            contr = FXCollections.observableArrayList(listaParcela);
            tabela.setItems(contr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void Gerar(ActionEvent event) {
        Double total = 0.0;
        int dias = 0, quant = 0, cod = 0;
        NumberFormat nf = new DecimalFormat("#,###.00");
        String cond = "";

        try {
            cod = Integer.parseInt(txcodigo.getText());
        } catch (NumberFormatException e) {
            cod = 0;

        }

        try {

            quant = Integer.parseInt(txQuant.getText());
        } catch (NumberFormatException e) {

            quant = 1;

            txQuant.setText("");
        }

        try {
            if (cbTipo.getSelectionModel().getSelectedItem() != null && !txValor.getText().isEmpty()) {
                if (cbCondPgto.getSelectionModel().getSelectedItem() != null) {
                    cond = cbCondPgto.getSelectionModel().getSelectedItem().getDescricao();
                    if (!cond.toUpperCase().equals("DINHEIRO") || !cond.toUpperCase().equals("DÉBITO")) {
                        if (txQuant.getText().isEmpty()) {
                            msg.campoVazio(txQuant);
                        }

                    }
                    try {
                        dias = Integer.parseInt(txDias.getText());
                    } catch (NumberFormatException e) {
                        if (cond.toUpperCase().equals("DINHEIRO") || cond.toUpperCase().equals("DÉBITO")) {
                            dias = 0;
                            txDias.setText("");
                        }
                    }
                    if (dias == 0 || dias > 31) {
                        msg.Affirmation("", "Numero de dias não pode ser 0 ou maior que 31");
                    } else {
                        total = nf.parse(txValor.getText()).doubleValue();

                        listaParcela.clear();
                        listaParcela = controller.gerarParcelas(cbCondPgto, cbTipo, total, dtEmissao, dtDtvenc, dias, quant, cod, func);
                        atualizarTabela();
                    }

                } else {
                    msg.campoVazioCbx(cbCondPgto);
                }

            } else {
                msg.campoVazioCbx(cbTipo);
                msg.campoVazio(txValor);
            }

        } catch (ParseException ex) {
            Logger.getLogger(TelaLancarDespesasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void habilitaCampos(ActionEvent event) {
        String cond = "";

        if (cbCondPgto.getSelectionModel().getSelectedItem() != null) {
            txDias.setDisable(false);
            txQuant.setDisable(false);
            cond = cbCondPgto.getSelectionModel().getSelectedItem().getDescricao();
            if (cond.toUpperCase().equals("DINHEIRO") || cond.toUpperCase().equals("DÉBITO")) {
                txDias.setDisable(true);
                txQuant.setDisable(true);
                txDias.setText("");
                txQuant.setText("");
            }

        }
    }

}
