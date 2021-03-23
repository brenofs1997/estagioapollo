/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.MenuController;
import Erros.Erros;
import Models.Caixa;
import Models.Funcionario;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
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

    @FXML
    private JFXButton cadProduto;
    @FXML
    private JFXButton CadForc;
    @FXML
    private FontAwesomeIcon iconcaixa = new FontAwesomeIcon();
    @FXML
    private Label lbcaixa = new Label();
    @FXML
    private Label lbcaixasaldo = new Label();
    @FXML
    private FontAwesomeIcon iconcaixaSaldo;

    MenuController mctrl = new MenuController();

    Caixa c = new Caixa();
    Erros msg = new Erros();

    NumberFormat nf = new DecimalFormat("#,###.00");

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (c.verificaCaixa()) {
            if (c.isStatus().equals("Caixa Aberto.")) {
                iconcaixa.setIconName("UNLOCK_ALT");
            } else {
                iconcaixa.setIconName("LOCK");
            }
            lbcaixa.setText(c.isStatus());
            lbcaixasaldo.setText(nf.format(c.getSaldoInicio()));

        } else {
            c.iniciaCaixa();
            if (c.isStatus().equals("Caixa Aberto.")) {
                iconcaixa.setIconName("UNLOCK_ALT");
            } else {
                iconcaixa.setIconName("LOCK");
            }
            lbcaixa.setText(c.isStatus());
            lbcaixasaldo.setText(nf.format(c.getSaldoInicio()));
        }
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
        FXMLTelaCompraController.setFunc(funcionario);
        FXMLTelaVendasProdutoController.setFunc(funcionario);
    }

    @FXML
    private void cadCliente(ActionEvent event) {
        Parent inicial;
        try {
            inicial = FXMLLoader.load(getClass().getResource("TelaClienteCadastro.fxml"));
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
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
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
            stage.setTitle("BEM-VINDO: AO CADASTRO DE NÍVEL DE FUNCIONÁRIO");
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
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
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
            stage.setTitle("BEM-VINDO: AO CADASTRO DE FUNCIONÁRIO");
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
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Sair(ActionEvent event) {
        if (c.isAberto()) {
              msg.Error("Apollo Informa: ", "Caixa Aberto!");
        } else {
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

                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setResizable(false);
                    stage.showAndWait();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
                }
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
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
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

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
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
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void forCad(ActionEvent event) {
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
    private void VendaProdutos(ActionEvent event) {
        if (c.isAberto()) {
            try {
                Parent inicial = FXMLLoader.load(getClass().getResource("FXMLTelaVendasProduto.fxml"));
                Scene scene = new Scene(inicial);

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("BEM-VINDO: A VENDA DE PRODUTOS");
                // stage.setMaximized(true);

                //Close authentication Window
                //Show main Window
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.showAndWait();

            } catch (IOException ex) {
                Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            msg.Error("Apollo Informa: ", "Caixa Fechado!");
        }

    }

    @FXML
    private void ReceberFiado(ActionEvent event
    ) {
        try {
            Parent inicial = FXMLLoader.load(getClass().getResource("FXMLTelaReceberFiado.fxml"));
            Scene scene = new Scene(inicial);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BEM-VINDO: AO RECEBER FIADOS");
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
    private void abreCaixa(MouseEvent event) {
        if (c.isAberto()) {
            c.atualizarStatus("F");
            lbcaixa.setText(c.isStatus());
            iconcaixa.setIconName("LOCK");
        } else {
            c.atualizarStatus("A");
            lbcaixa.setText(c.isStatus());
            iconcaixa.setIconName("UNLOCK_ALT");
        }
    }

    public void atualizaSaldo() {
        lbcaixasaldo.setText(nf.format(c.getSaldoInicio()));
    }

    @FXML
    private void atualizaSaldo(MouseEvent event) {
        if (c.isAberto()) {
            try {
                Parent inicial = FXMLLoader.load(getClass().getResource("TelaSaldoCaixa.fxml"));
                Scene scene = new Scene(inicial);

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("BEM-VINDO: ABRIR CAIXA");
                // stage.setMaximized(true);

                //Close authentication Window
                //Show main Window
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.showAndWait();
                atualizaSaldo();
            } catch (IOException ex) {
                Logger.getLogger(FXMLMenuInicialController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            msg.Error("Apollo Informa: ", "Caixa Fechado!");
        }

    }

}
