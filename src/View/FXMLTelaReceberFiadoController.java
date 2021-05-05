/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Ajuda.Ajuda;
import Controller.ReceberFiadoController;
import Controller.VendaController;
import Erros.Erros;
import Models.Cliente;
import Models.ContasReceber;
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
public class FXMLTelaReceberFiadoController implements Initializable {

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
    @FXML
    private JFXComboBox<Cliente> cbCliente;
    @FXML
    private JFXTextField txtPesqCli;
    @FXML
    private JFXButton btPesqCliente;
    @FXML
    private DatePicker dtInicial;
    @FXML
    private JFXButton btFiltrar;
    @FXML
    private TableView<ContasReceber> tabelaRec;
    @FXML
    private TableColumn<ContasReceber, String> colemissao;
    @FXML
    private TableColumn<ContasReceber, Double> coltotal;
    @FXML
    private TableColumn<ContasReceber, String> coldatapago;
    @FXML
    private TableColumn<ContasReceber, Double> colvalorpago;

    VendaController controllerV = new VendaController();
    ReceberFiadoController controller = new ReceberFiadoController();
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
    private TableColumn<ContasReceber, Double> colvalorrestante;
    private Label lbTotal;
    @FXML
    private JFXCheckBox cbAtiperiodo;
    @FXML
    private TableColumn<ContasReceber, String> colnomeCliente;
    @FXML
    private TableColumn<ContasReceber, String> colIdpagamento;
    @FXML
    private TableColumn<ContasReceber, Integer> colcodVenda;
    @FXML
    private JFXButton btAjuda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        colemissao.setCellValueFactory(new PropertyValueFactory("emissao"));
        coltotal.setCellValueFactory(new PropertyValueFactory("valor"));
        coldatapago.setCellValueFactory(new PropertyValueFactory("data_pago"));
        colvalorpago.setCellValueFactory(new PropertyValueFactory("valor_pago"));
        colvalorrestante.setCellValueFactory(new PropertyValueFactory("valor_restante"));
        colnomeCliente.setCellValueFactory(new PropertyValueFactory("cliente"));
        colIdpagamento.setCellValueFactory(new PropertyValueFactory("parcela"));
        colcodVenda.setCellValueFactory(new PropertyValueFactory("venda"));
        CarregaCliente();

        rbPendente.fire();
    }

    public void CarregaCliente() {
        cbCliente.setItems(FXCollections.observableArrayList(controllerV.CarregaCliente()));
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
            controller.estornar(tabelaRec.getSelectionModel().getSelectedItem(), btEstornar);
            consultar();

        }
    }

    @FXML
    private void Baixar(ActionEvent event) throws IOException {
        if (tabelaRec.getSelectionModel().getSelectedItem() != null) {
            TelaBaixarFiadoController.setConta(tabelaRec.getSelectionModel().getSelectedItem());
            Parent inicial = FXMLLoader.load(getClass().getResource("TelaBaixarFiado.fxml"));
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
    private void PesqCliente(ActionEvent event) {
        cbCliente.setItems(FXCollections.observableArrayList(controllerV.PesquisarCliente(txtPesqCli.getText())));
    }

    @FXML
    private void Consultar(ActionEvent event) {
        consultar();

    }

    public void consultar() {

        String dtinicial = "";
        String dtfinal = "";

        String status = "";

        int cliente = 0;
        if (dtInicial.getValue() != null && dtFinal.getValue() != null) {
            dtinicial = dtInicial.getValue().toString();
            dtfinal = dtFinal.getValue().toString();
        }
        if (cbCliente.getSelectionModel().getSelectedItem() != null) {
            cliente = cbCliente.getSelectionModel().getSelectedItem().getCodigo();
        }

        if (rbPendente.isSelected()) {
            status = "P";
        } else if (rbQuitados.isSelected()) {
            status = "Q";
        }

        controller.consultar(cliente, dtinicial, dtfinal, tabelaRec, status);
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
            controller.pesquisaRadio("PR", tabelaRec);
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
    private void Ajuda(ActionEvent event) {
        Ajuda a = new Ajuda();
        a.Ajuda("ReceberFiado.htm");
    }

}
