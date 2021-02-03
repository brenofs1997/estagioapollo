/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.CompraController;
import Models.Compra;
import Models.ContasPagar;
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
public class CompraConsultaController implements Initializable {

    @FXML
    private JFXButton btPesquisar;
    @FXML
    private JFXTextField txPesquisa;
    @FXML
    private Pane pnconteudo;
    @FXML
    private TableView<Compra> tabela;
    @FXML
    private TableColumn<Compra, String> colfornecedor;
 
    @FXML
    private TableColumn<Compra, Double> colvalor;
    @FXML
    private TableColumn<Compra, String> colemissao;
    CompraController cpControl= new CompraController();
    public static Compra compra;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colfornecedor.setCellValueFactory(new PropertyValueFactory("fornecedor"));
        colemissao.setCellValueFactory(new PropertyValueFactory("emissao"));
        colvalor.setCellValueFactory(new PropertyValueFactory("total"));
        cpControl.carregarConsulta( "",tabela);
    }    

    @FXML
    private void Pesquisar(ActionEvent event) {
    }

    @FXML
    private void doubleClick(MouseEvent event) {
        compra = tabela.getSelectionModel().getSelectedItem();
        FXMLTelaCompraController.setCompra(compra);
        Stage self = (Stage) tabela.getScene().getWindow();
        self.close();
    }
    
}
