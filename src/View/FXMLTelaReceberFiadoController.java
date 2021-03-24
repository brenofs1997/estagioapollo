/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

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
        CarregaCliente();

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
            controller.estornar(tabelaRec.getSelectionModel().getSelectedItem());
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

            consultar();
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
        int cliente = 0;
        if (dtInicial.getValue() != null && dtFinal.getValue() != null) {
            dtinicial = dtInicial.getValue().toString();
            dtfinal = dtFinal.getValue().toString();
        }
        if (cbCliente.getSelectionModel().getSelectedItem() != null) {
            cliente = cbCliente.getSelectionModel().getSelectedItem().getCodigo();
        }

        controller.consultar(cliente, dtinicial, dtfinal, tabelaRec);
    }

    @FXML
    private void VerificarVenda(MouseEvent event) {
        if (tabelaRec.getSelectionModel().getSelectedItem() != null) {
            if (tabelaRec.getSelectionModel().getSelectedItem().getStatus().toUpperCase() == "Q") {
                btBaixar.setDisable(true);
            } else {
                btBaixar.setDisable(false);
            }
        }
    }

    @FXML
    private void pesqTodos(ActionEvent event) {
        if(rbTodos.isSelected())
        {
            controller.pesquisaRadio("", tabelaRec);
            
        }
    }

    @FXML
    private void pesqQuitado(ActionEvent event) {
         if(rbQuitados.isSelected())
        {
             controller.pesquisaRadio("Q", tabelaRec);
           
        }
    }

    @FXML
    private void pesqParcial(ActionEvent event) {
        if(rbPendente.isSelected())
        {
              controller.pesquisaRadio("P", tabelaRec);
       }
    }

}
