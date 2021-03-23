/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.MenuController;
import Erros.Erros;
import apollo.utils.MaskFieldUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author paulo
 */
public class TelaSaldoCaixaController implements Initializable {

    @FXML
    private JFXButton btBaixar;
    @FXML
    private JFXTextField txtValor;

    MenuController mctrl = new MenuController();
    Erros msg = new Erros();
    NumberFormat nf = new DecimalFormat("#,###.00");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MaskFieldUtil.monetaryField(txtValor);
    }

    @FXML
    private void atualizarCaixa(ActionEvent event) {
        Double valor = 0.0;
        if (!txtValor.getText().isEmpty()) {
            try {
                valor = nf.parse(txtValor.getText()).doubleValue();
            } catch (ParseException ex) {
                Logger.getLogger(TelaSaldoCaixaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (mctrl.atualizarCaixa(valor)) {
                Stage self = (Stage) btBaixar.getScene().getWindow();
                self.close();
            }
        } else {
            msg.campoVazio(txtValor);
        }
    }

}
