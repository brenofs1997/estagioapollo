/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Erros.Erros;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import CamadaAcessoDados.Banco;
import Models.Funcionario;
import View.TelaFuncionarioCadController;
import View.TelaFuncionarioCadController;
import View.TelaInicialController;
import View.TelaInicialController;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaAutenticacaoFXMLController implements Initializable {

    @FXML
    private JFXButton btnSair;
    @FXML
    private JFXButton btnEntrar;
    @FXML
    private JFXTextField txtNome;
    @FXML
    private JFXPasswordField txtSenha;
    @FXML
    private Button inscrever;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Banco.conectar();
        ResultSet rs = Banco.getCon().consultar("select  * from funcionario");
        int rows = 0;
        try {
            while (rs.next()) {
                rows = rs.getRow();
            }
        } catch (Exception e) {
        }
        if (rows >= 1) {
            inscrever.setVisible(false);
        } else {
            inscrever.setVisible(true);
            TelaFuncionarioCadController.setPrimeiro_acesso(true);
        }

    }

    @FXML
    private void sair(ActionEvent event) {

        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void entrar(ActionEvent event) {
        Entrar();
    }

    @FXML
    private void btnEntrarPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            Entrar();
        }

    }

    private void Entrar() {

        Funcionario Funcionario = new Funcionario();
        Erros erros = new Erros();
        String usuario, senha;

        usuario = txtNome.getText();
        senha = txtSenha.getText();

        if (usuario.equals("")) {
            erros.Error("", "Usuário não digitado.");
            txtNome.requestFocus();
        } else if (senha.equals("")) {
            erros.Error("", "Senha não digitada.");
            txtSenha.requestFocus();
        } else {
            if (Funcionario.getLoginF(usuario, senha) == null) {
                erros.Error("", "Usuário não encontrado.");
                txtSenha.requestFocus();
            } else {
                Banco.conectar();
                ResultSet rs = Banco.getCon().consultar("select  * FROM empresa_parametros");
                int rows = 0;
                try {
                    while (rs.next()) {
                        rows = rs.getRow();
                    }
                    if (rows == 0) {
                        try {
                            Stage stage = new Stage();
                            Parent root = FXMLLoader.load(getClass().getResource("TelaEmpresaParametrosCad.fxml"));
                            Scene scene = new Scene(root);
                            stage.setTitle("Cadastro da Empresa");
                            stage.setScene(scene);
                            stage.initModality(Modality.APPLICATION_MODAL);

                            stage.setResizable(false);
                            stage.showAndWait();
                        } catch (IOException e) {
                            System.out.println(e);
                        }

                    } else {
                        try {
                            Funcionario = Funcionario.getLoginF(usuario, senha);
                            FXMLMenuInicialController.setFuncionario(Funcionario);
                            Stage stage = new Stage();
                            Stage self = new Stage();
                            Parent inicial = FXMLLoader.load(getClass().getResource("FXMLMenuInicial.fxml"));
                            Scene scene = new Scene(inicial);

                            stage.setScene(scene);
                            stage.setTitle("MENU");
                            stage.setMaximized(true);
                            //Close authentication Window
                            self = (Stage) btnSair.getScene().getWindow();

                            self.close();

                            //Show main Window
                            stage.show();

                        } catch (Exception e) {
                            erros.Error("Erro inesperado", e.getMessage());
                        }
                    }
                } catch (SQLException ex) {

                }
            }
        }
    }

    @FXML
    private void btnInscrever(ActionEvent event) throws IOException {
        Parent inicial = FXMLLoader.load(getClass().getResource("/View/TelaFuncionarioCadastro.fxml"));
        Scene scene = new Scene(inicial);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BEM-VINDO: AO CADASTRO DE FUNCIONARIO");
        stage.centerOnScreen();

        //Close authentication Window
        //Show main Window
        stage.show();
        TelaFuncionarioCadController.setPrimeiro_acesso(true);
    }

}
