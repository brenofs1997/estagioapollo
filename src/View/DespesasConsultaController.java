/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ContasPagarController;
import Models.ContasPagar;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class DespesasConsultaController implements Initializable {

   public static ContasPagar desp;
    @FXML
    private TextField txPesquisa;
    @FXML
    private JFXButton btPesquisar;
    @FXML
    private Pane pnconteudo;
     @FXML
    private TableView<ContasPagar> tabela;
    @FXML
    private TableColumn<ContasPagar, String> colparc;
    @FXML
    private TableColumn<ContasPagar, Date> colvenc;
    @FXML
    private TableColumn<ContasPagar, Double> colvalor;
    @FXML
    private TableColumn<ContasPagar, String> coldesc;
    
    ContasPagarController controller = new ContasPagarController();

    public static ContasPagar getDesp() {
        return desp;
    }

    public static void setDesp(ContasPagar desp) {
        DespesasConsultaController.desp = desp;
    }
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colparc.setCellValueFactory(new PropertyValueFactory("parcela"));
        colvalor.setCellValueFactory(new PropertyValueFactory("valor"));
        colvenc.setCellValueFactory(new PropertyValueFactory("vencimento"));
        coldesc.setCellValueFactory(new PropertyValueFactory("tipo_despesa"));
         controller.carregaTabela("", tabela);
    }    

    @FXML
    private void Pesquisar(ActionEvent event) {  
        controller.carregaTabela(txPesquisa.getText(), tabela);
    }

    @FXML
    private void doubleClick(MouseEvent event) {
         desp = tabela.getSelectionModel().getSelectedItem();
        TelaLancarDespesasController.setDesp(desp);
        Stage self = (Stage) tabela.getScene().getWindow();
        self.close();
    }

    
}
