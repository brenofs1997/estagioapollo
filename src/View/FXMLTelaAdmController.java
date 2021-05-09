/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.MenuController;
import Erros.Erros;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author paulo
 */
public class FXMLTelaAdmController implements Initializable {

    @FXML
    private JFXButton btconfirmar;

    MenuController mctrl = new MenuController();
    @FXML
    private JFXPasswordField txsenha;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clkconfirmar(ActionEvent event) {
        int erro = 0;
        if (txsenha.getText().isEmpty()) {
            erro = 1;
        }
        Erros msg = new Erros();
        if (erro == 0) {
            if (mctrl.abrirCaixa(txsenha.getText())) {
                
                Stage self = (Stage) btconfirmar.getScene().getWindow();
                self.close();
            } else {
                 msg.Error("Apollo informa", "Digite a senha novamente!");
            }
        }
    }

}
