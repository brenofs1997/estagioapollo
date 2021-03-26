/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ReceberFiadoController;
import Erros.Erros;
import Models.ContasReceber;
import apollo.utils.MaskFieldUtil;
import apollo.utils.converteDataPicker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class TelaBaixarFiadoController implements Initializable {

    Erros msg = new Erros();
    @FXML
    private DatePicker dtBaixa;
    @FXML
    private JFXTextField txtValor;
    @FXML
    private JFXButton btBaixar;
    ReceberFiadoController controller = new ReceberFiadoController();
    NumberFormat nf = new DecimalFormat("#,###.00");
    DecimalFormat formato = new DecimalFormat("#.##");
    public static ContasReceber conta = new ContasReceber();

    public static ContasReceber getConta() {
        return conta;
    }

    public static void setConta(ContasReceber conta) {
        TelaBaixarFiadoController.conta = conta;
    }
    @FXML
    private DatePicker dtEmissao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dtBaixa.setValue(LocalDate.now());
        MaskFieldUtil.monetaryField(txtValor);
        txtValor.setText(nf.format(conta.getValor_restante()));
        converteDataPicker cv = new converteDataPicker();
        cv.converter(getConta().getEmissaoDate(), dtEmissao);
    }

    @FXML
    private void BaixarVenda(ActionEvent event) {
        Double valor = 0.0;
        //java.sql.Date.valueOf(dtEmissao.getValue()), nf.parse(lbTotal.getText()).doubleValue()
        if (!txtValor.getText().isEmpty()) {
            try {
                valor = nf.parse(txtValor.getText()).doubleValue();
                if (controller.baixar(conta, valor, dtBaixa)) {
                    msg.Affirmation("Apollo informa", "Concluido com sucesso");
                    Stage self = (Stage) btBaixar.getScene().getWindow();
                    self.close();
                } else {
                    msg.Error("ERRO", "Erro ao baixar conta");
                }
            } catch (ParseException ex) {
                Logger.getLogger(TelaBaixarFiadoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            msg.campoVazio(txtValor, "Campo não pode estar vazio ou ser zero");
        }
    }

}
