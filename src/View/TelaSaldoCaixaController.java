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
import apollo.utils.MaskFieldUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.ToggleGroup;
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
    @FXML
    private JFXComboBox<Funcionario> cbFun;
    @FXML
    private JFXTextArea txmotivo;
    @FXML
    private JFXRadioButton rbEntrada;
    @FXML
    private ToggleGroup grupo;
    @FXML
    private JFXRadioButton rbSaida;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CarregaFuncionario();
        MaskFieldUtil.monetaryField(txtValor);
    }
    
    public void CarregaFuncionario() {
        cbFun.setItems(FXCollections.observableArrayList(mctrl.CarregaFuncionario()));
    }
    @FXML
    private void atualizarCaixa(ActionEvent event) {
        Double valor = 0.0;
        String motivo="",tipo="E";
        Date emissao=new Date();
        String emissao_horario="";
        if (!txtValor.getText().isEmpty()&&!txmotivo.getText().isEmpty() &&cbFun.getSelectionModel().getSelectedItem()!=null) {
            try {
                valor = nf.parse(txtValor.getText()).doubleValue();
            } catch (ParseException ex) {
                Logger.getLogger(TelaSaldoCaixaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            motivo=txmotivo.getText();
            
            if(rbSaida.isSelected())
                tipo="S";
            emissao=java.sql.Date.valueOf(LocalDate.now());
            LocalDateTime now = LocalDateTime.now();
            emissao_horario=""+ now.getHour()+":"+now.getMinute()+":"+now.getSecond();
            if (mctrl.atualizarCaixa(valor, motivo, cbFun.getSelectionModel().getSelectedItem(), emissao, emissao_horario,tipo)) {
                Stage self = (Stage) btBaixar.getScene().getWindow();
                self.close();
            }
        } else {
            msg.campoVazio(txtValor);
            msg.campoVazio(txmotivo);
            msg.campoVazioCbx(cbFun);

        }
    }

}
