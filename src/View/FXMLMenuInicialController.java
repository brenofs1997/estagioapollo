/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.MenuController;
import Models.Funcionario;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

public class FXMLMenuInicialController implements Initializable {

    @FXML
    private TitledPane tpCadastro;
    @FXML
    private TitledPane tpCompras;
    @FXML
    private TitledPane tpFluxo;
    @FXML
    private TitledPane tpVendas;
    @FXML
    private TitledPane tpBack;
    @FXML
    private TitledPane tpAjuda;
    @FXML
    private TitledPane tpRelatorio;
    @FXML
    private JFXButton cadCliente;
    @FXML
    private JFXButton catCategoria;
    @FXML
    private JFXButton CadnivFunc;
    @FXML
    private JFXButton CadtpDesp;
    @FXML
    private JFXButton CadFunc;
    @FXML
    private JFXButton CadEmpresa;
    @FXML
    private JFXButton CadCompra;
    @FXML
    private JFXButton CadContasPagar;
    @FXML
    private JFXButton CadContasReceber;
    @FXML
    private JFXButton CadLancarDespesas;
    @FXML
    private JFXButton CadReceberFiado;
    @FXML
    private JFXButton CadVendas;
    @FXML
    private JFXButton CadBackup;
    @FXML
    private JFXButton CadRestore;
    @FXML
    private TitledPane tpRelatorio1;
    @FXML
    private JFXButton btnsair;

    private static Funcionario funcionario;

    public static Funcionario getFuncionario() {
        return funcionario;
    }

    public static void setFuncionario(Funcionario funcionario) {
        FXMLMenuInicialController.funcionario = funcionario;
    }

    MenuController mctrl = new MenuController();
    @FXML
    private JFXButton cadProduto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        verificaRestricao();
    }

    public void verificaRestricao() {
        String restricao = funcionario.getNivel().getRestricoes().getDescricao();

        if (restricao.equals("CadastroCliente/Venda/Backup")) {
            catCategoria.setDisable(true);
            CadnivFunc.setDisable(true);
            CadtpDesp.setDisable(true);
            CadFunc.setDisable(true);
            CadEmpresa.setDisable(true);
            tpCompras.setDisable(true);
            tpFluxo.setDisable(true);
            tpRelatorio.setDisable(true);

        }
        if (restricao.equals("Cadastros/Venda/FluxoCaixa/Backup")) {
            tpCompras.setDisable(true);
            tpRelatorio.setDisable(true);
        }
        TelaLancarDespesasController.setFunc(funcionario);
    }

    @FXML
    private void cadCliente(ActionEvent event) {
        Parent inicial;
        try {
            inicial = FXMLLoader.load(getClass().getResource("TelaClienteCadastro.fxml"));
            Scene scene = new Scene(inicial);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BEM-VINDO: AO CADASTRO DE CATEGORIAS");
            // stage.setMaximized(true);

            //Close authentication Window
            //Show main Window
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void cadCat(ActionEvent event) {
        try {
            Parent inicial = FXMLLoader.load(getClass().getResource("TelaCategoria.fxml"));
            Scene scene = new Scene(inicial);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BEM-VINDO: AO CADASTRO DE CATEGORIAS");
            // stage.setMaximized(true);

            //Close authentication Window
            //Show main Window
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cadNivelFunc(ActionEvent event) {
        try {
            Parent inicial = FXMLLoader.load(getClass().getResource("TelaNivelFuncionario.fxml"));
            Scene scene = new Scene(inicial);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BEM-VINDO: AO CADASTRO DE NÍVEL DE FUNCIONARIO");
            // stage.setMaximized(true);
            //Close authentication Window
            //Show main Window
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cadDesp(ActionEvent event) {
        try {
            Parent inicial = FXMLLoader.load(getClass().getResource("TelaTipoDespesa.fxml"));
            Scene scene = new Scene(inicial);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BEM-VINDO: AO CADASTRO DE TIPOS DE DESPESA");
            // stage.setMaximized(true);

            //Close authentication Window
            //Show main Window
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void funCad(ActionEvent event) {
        try {
            Parent inicial = FXMLLoader.load(getClass().getResource("/View/TelaFuncionarioCadastro.fxml"));
            Scene scene = new Scene(inicial);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BEM-VINDO: AO CADASTRO DE FUNCIONARIO");
            // stage.setMaximized(true);

            //Close authentication Window
            //Show main Window
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Empresa(ActionEvent event) {
        try {
            Parent inicial = FXMLLoader.load(getClass().getResource("TelaEmpresaParametrosCad.fxml"));
            Scene scene = new Scene(inicial);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BEM-VINDO: AO CADASTRO DE EMPRESA");
            // stage.setMaximized(true);

            //Close authentication Window
            //Show main Window
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Sair(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Deseja sair?");
        a.showAndWait();
        if (a.getResult() == ButtonType.OK) {
            try {
                btnsair.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("TelaAutenticacaoFXML.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Autenticação de Usuário");
                
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void Produto(ActionEvent event) {
         try {
            Parent inicial = FXMLLoader.load(getClass().getResource("TelaProduto.fxml"));
            Scene scene = new Scene(inicial);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BEM-VINDO: AO CADASTRO DE PRODUTO");
            // stage.setMaximized(true);

            //Close authentication Window
            //Show main Window
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void lancarDespesas(ActionEvent event) {
         try {
            Parent inicial = FXMLLoader.load(getClass().getResource("TelaLancarDespesas.fxml"));
            Scene scene = new Scene(inicial);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BEM-VINDO: AO LANÇAMENTO DE DESPESAS");
            
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void comprarProdutos(ActionEvent event) {
        try {
            Parent inicial = FXMLLoader.load(getClass().getResource("FXMLTelaCompra2.fxml"));
            Scene scene = new Scene(inicial);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BEM-VINDO: AO COMPRA DE PRODUTOS");
            // stage.setMaximized(true);

            //Close authentication Window
            //Show main Window
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
