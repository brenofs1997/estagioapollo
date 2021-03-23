/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.VendaController;
import Models.Venda;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class VendaConsultaController implements Initializable {

    @FXML
    private JFXButton btPesquisar;
    @FXML
    private JFXTextField txPesquisa;
    @FXML
    private Pane pnconteudo;
    @FXML
    private TableView<Venda> tabela;
    @FXML
    private TableColumn<Venda, String> colcliente;

    @FXML
    private TableColumn<Venda, Double> colvalor;
    @FXML
    private TableColumn<Venda, String> colemissao;
    VendaController vControl = new VendaController();
    public static Venda venda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colcliente.setCellValueFactory(new PropertyValueFactory("cliente"));
        colemissao.setCellValueFactory(new PropertyValueFactory("emissao"));
        colvalor.setCellValueFactory(new PropertyValueFactory("total"));
        vControl.carregarConsulta("", tabela);
    }

    @FXML
    private void Pesquisar(ActionEvent event) {
    }

    @FXML
    private void doubleClick(MouseEvent event) {
        venda = tabela.getSelectionModel().getSelectedItem();
        FXMLTelaVendasProdutoController.setVenda(venda);
        Stage self = (Stage) tabela.getScene().getWindow();
        self.close();
    }

}
