/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ContasPagarController;
import Erros.Erros;
import Models.ContasPagar;
import apollo.utils.MaskFieldUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class TelaBaixarContasPagarController implements Initializable {

    Erros msg = new Erros();
    @FXML
    private JFXTextField dtEmissao;
    @FXML
    private DatePicker dtBaixa;
    @FXML
    private JFXTextField txtValor;
    @FXML
    private JFXButton btBaixar;

    ContasPagarController controller = new ContasPagarController();
    NumberFormat nf = new DecimalFormat("#,###.00");
    DecimalFormat formato = new DecimalFormat("#.##");
    public static ContasPagar conta = new ContasPagar();

    public static ContasPagar getConta() {
        return conta;
    }

    public static void setConta(ContasPagar conta) {
        TelaBaixarContasPagarController.conta = conta;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dtBaixa.setValue(LocalDate.now());
        MaskFieldUtil.monetaryField(txtValor);
        txtValor.setText(nf.format(conta.getValor()-conta.getValor_pago()));
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        dtEmissao.setText(formato.format(conta.getEmissaoDate()));

    }

    @FXML
    private void BaixarVenda(ActionEvent event) {
        Double valor = 0.0;
        //java.sql.Date.valueOf(dtEmissao.getValue()), nf.parse(lbTotal.getText()).doubleValue()
        if (!txtValor.getText().isEmpty()) {
            try {
                valor = nf.parse(txtValor.getText()).doubleValue();
                if (valor > conta.getValor()) {
                    msg.Error("ERRO", "Valor maior que o valor da conta");
                } else {
                    if (controller.baixar(conta, valor, dtBaixa)) {
                        msg.Affirmation("Apollo informa", "Concluido com sucesso");
                        Stage self = (Stage) btBaixar.getScene().getWindow();
                        self.close();
                    } else {
                        msg.Error("ERRO", "Erro ao baixar conta");
                    }
                }
            } catch (ParseException ex) {
                Logger.getLogger(TelaBaixarContasPagarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            msg.campoVazio(txtValor, "Campo não pode estar vazio ou ser zero");
        }
    }

}
