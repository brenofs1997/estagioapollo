/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Ajuda.Ajuda;
import Controller.CompraController;
import Controller.ContasPagarController;
import Controller.FornecedorController;
import Erros.Erros;
import Models.Cliente;
import Models.ContasPagar;
import Models.Fornecedor;
import Models.TipoDespesa;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author paulo
 */
public class FXMLTelaContasPagarController implements Initializable {

    @FXML
    private JFXButton btCancelar;
    @FXML
    private JFXButton btEstornar;
    @FXML
    private JFXButton btBaixar;
    @FXML
    private Pane pnconteudo;
    @FXML
    private DatePicker dtFinal;
    private JFXComboBox<Cliente> cbCliente;
    private JFXTextField txtPesqCli;
    @FXML
    private DatePicker dtInicial;
    @FXML
    private JFXButton btFiltrar;
    @FXML
    private TableView<ContasPagar> tabelaRec;
    private TableColumn<ContasPagar, String> colemissao;
    @FXML
    private TableColumn<ContasPagar, Double> coltotal;
    @FXML
    private TableColumn<ContasPagar, String> coldatapago;
    @FXML
    private TableColumn<ContasPagar, Double> colvalorpago;

    ContasPagarController controller = new ContasPagarController();
    Erros msg = new Erros();
    @FXML
    private JFXRadioButton rbTodos;
    @FXML
    private ToggleGroup grupo;
    @FXML
    private JFXRadioButton rbQuitados;
    @FXML
    private JFXRadioButton rbPendente;
    @FXML
    private TableColumn<ContasPagar, Double> colvalorrestante;
    private Label lbTotal;
    @FXML
    private JFXCheckBox cbAtiperiodo;
    private TableColumn<ContasPagar, String> colnomeCliente;
    @FXML
    private TableColumn<ContasPagar, String> colIdpagamento;
    @FXML
    private TableColumn<ContasPagar, Integer> colcodVenda;
    @FXML
    private JFXComboBox<Fornecedor> cbFornecedor;
    @FXML
    private JFXTextField txtPesqForne;
    @FXML
    private JFXButton btPesqFornecedor;
    CompraController controllerC = new CompraController();
    FornecedorController FornecedorController = new FornecedorController();
    @FXML
    private TableColumn<ContasPagar, String> colnomeFornecedor;
    @FXML
    private TableColumn<ContasPagar, String> coltipodesp;
    @FXML
    private TableColumn<ContasPagar, String> colvencimento;
    @FXML
    private JFXCheckBox cbAtifornecedor;
    @FXML
    private JFXCheckBox cbAtidespesa;
    @FXML
    private JFXComboBox<TipoDespesa> cbDespesa;
    @FXML
    private JFXButton btLimpar;
    @FXML
    private JFXButton btAjuda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        coltipodesp.setCellValueFactory(new PropertyValueFactory("tipo_despesa"));
        colvencimento.setCellValueFactory(new PropertyValueFactory("vencimento"));
        coltotal.setCellValueFactory(new PropertyValueFactory("valor"));
        coldatapago.setCellValueFactory(new PropertyValueFactory("data_pago"));
        colvalorpago.setCellValueFactory(new PropertyValueFactory("valor_pago"));
        colvalorrestante.setCellValueFactory(new PropertyValueFactory("valor_restante"));
        colnomeFornecedor.setCellValueFactory(new PropertyValueFactory("fornecedor"));
        colIdpagamento.setCellValueFactory(new PropertyValueFactory("parcela"));
        colcodVenda.setCellValueFactory(new PropertyValueFactory("compra"));// codigo da compra
        cbFornecedor.setDisable(true);
        CarregaFornecedor();
        CarregaDespesa();
        rbTodos.fire();

    }

    public void CarregaFornecedor() {
        cbFornecedor.setItems(FXCollections.observableArrayList(controllerC.CarregaFornecedor()));
    }

    public void CarregaDespesa() {
        cbDespesa.setItems(FXCollections.observableArrayList(controller.CarregaTipo()));
    }

    @FXML
    private void Cancelar(ActionEvent event) {
        btCancelar.getScene().getWindow().hide();//fecha a janela
    }

    @FXML
    private void Estornar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma o estorno");
        if (a.showAndWait().get() == ButtonType.OK) {
            if (tabelaRec.getSelectionModel().getSelectedItem() != null) {
                controller.estornar(tabelaRec.getSelectionModel().getSelectedItem(), btEstornar);
                consultar();
            } else {
                msg.Affirmation("ERRO!", "Selecione uma Conta na tabela");
            }
        }
    }

    @FXML
    private void Baixar(ActionEvent event) throws IOException {
        if (tabelaRec.getSelectionModel().getSelectedItem() != null) {
            TelaBaixarContasPagarController.setConta(tabelaRec.getSelectionModel().getSelectedItem());
            Parent inicial = FXMLLoader.load(getClass().getResource("TelaBaixarContasPagar.fxml"));
            Scene scene = new Scene(inicial);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BEM-VINDO: TELA DE BAIXA");
            // stage.setMaximized(true);

            //Close authentication Window
            //Show main Window
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
            tabelaRec.getItems().clear();
            controller.pesquisaRadio("", tabelaRec);

        } else {
            msg.Error("ERRO", "Selecione uma  conta");
        }
    }

    @FXML
    private void atualizaFornecedores(MouseEvent event) {
    }

    @FXML
    private void HabilitaProduto(ActionEvent event) {
    }

    @FXML
    private void Consultar(ActionEvent event) {
        consultar();
    }

    public void consultar() {

        String dtinicial = "";
        String dtfinal = "";

        String status = "";

        int Fornecedor = 0;
        int Despesa = 0;
        if (dtInicial.getValue() != null && dtFinal.getValue() != null) {
            dtinicial = dtInicial.getValue().toString();
            dtfinal = dtFinal.getValue().toString();
        }
        if (cbFornecedor.getSelectionModel().getSelectedItem() != null) {
            Fornecedor = cbFornecedor.getSelectionModel().getSelectedItem().getCodigo();
        }
        if (cbDespesa.getSelectionModel().getSelectedItem() != null) {
            Despesa = cbDespesa.getSelectionModel().getSelectedItem().getCodigo();
        }

        if (rbPendente.isSelected()) {
            status = "P";
        } else if (rbQuitados.isSelected()) {
            status = "Q";
        }

        controller.consultar(Despesa, Fornecedor, dtinicial, dtfinal, tabelaRec, status);
    }

    @FXML
    private void VerificarVenda(MouseEvent event) {
        if (tabelaRec.getSelectionModel().getSelectedItem() != null) {
            String status = tabelaRec.getSelectionModel().getSelectedItem().getStatus().toUpperCase();
            if (status.equals("Q") || status.equals("PR")) {
                btBaixar.setDisable(true);
            } else {
                btBaixar.setDisable(false);
            }

            if (controller.veriEstorna(tabelaRec.getSelectionModel().getSelectedItem())) {
                btEstornar.setDisable(false);
            } else {
                btEstornar.setDisable(true);
            }
        }
    }

    @FXML
    private void pesqTodos(ActionEvent event) {
        if (rbTodos.isSelected()) {
            tabelaRec.getItems().clear();
            controller.pesquisaRadio("", tabelaRec);
        }
    }

    @FXML
    private void pesqQuitado(ActionEvent event) {
        if (rbQuitados.isSelected()) {
            tabelaRec.getItems().clear();
            controller.pesquisaRadio("Q", tabelaRec);

        }
    }

    @FXML
    private void pesqParcial(ActionEvent event) {
        if (rbPendente.isSelected()) {
            tabelaRec.getItems().clear();
            controller.pesquisaRadio("P", tabelaRec);
        }
    }

    @FXML
    private void AtivarPeriodo(ActionEvent event) {
        if (cbAtiperiodo.isSelected()) {
            dtInicial.setDisable(false);
            dtFinal.setDisable(false);
            dtInicial.setValue(LocalDate.now());
            dtFinal.setValue(LocalDate.now());
        } else {
            dtInicial.setDisable(true);
            dtFinal.setDisable(true);
        }

    }

    @FXML
    private void PesqFornecedor(ActionEvent event) {

        cbFornecedor.setItems(FXCollections.observableArrayList(FornecedorController.carregaFornecedor(txtPesqForne.getText())));
    }

    @FXML
    private void AtivarFornecedor(ActionEvent event) {
        if (cbAtifornecedor.isSelected()) {
            cbFornecedor.setDisable(false);
            cbDespesa.setDisable(true);
        } else {
            cbFornecedor.setDisable(true);
            cbDespesa.setDisable(false);
        }
    }

    @FXML
    private void AtivarDespesa(ActionEvent event) {
        if (cbAtidespesa.isSelected()) {
            cbDespesa.setDisable(false);
            cbFornecedor.setDisable(true);
        } else {
            cbDespesa.setDisable(true);
            cbFornecedor.setDisable(false);
        }
    }

    @FXML
    private void LimparFiltro(ActionEvent event) {
        cbDespesa.getSelectionModel().clearSelection();
        cbFornecedor.getSelectionModel().clearSelection();
        dtFinal.setValue(null);
        dtInicial.setValue(null);
    }

    @FXML
    private void Ajuda(ActionEvent event) {
        Ajuda a = new Ajuda();
        a.Ajuda("ContasaPagar.htm");
    }

}
