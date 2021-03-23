/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Erros;

import Models.Categoria;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import Models.Cidade;
import Models.Cliente;
import Models.Produto;

/**
 *
 * @author Paulo
 */
public class Erros {

    public Erros() {
    }

    public void campoVazio(JFXTextField txt, String msg) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        txt.resetValidation();
        txt.getValidators().add(validator);
        validator.setMessage("Campo não pode estar vazio!");
        txt.setText(msg);
        txt.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                txt.validate();
            }
        });
    }

    public void campoVazio(JFXTextField campo) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Campo vazio!");
        campo.resetValidation();
        campo.getValidators().add(validator);

        campo.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                campo.validate();
            }
        });
        campo.validate();
    }

    public void campoVazioCbx(JFXComboBox campo) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        campo.resetValidation();
        campo.getValidators().add(validator);
        validator.setMessage("Campo vazio!");
        campo.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                campo.validate();
            }
        });
        campo.validate();

    }

    public boolean campoVazio(JFXTextField txt, String campo, Label msg) {
        if (txt.getText().isEmpty()) {
            msg.setDisable(false);
            msg.setText("O campo " + campo + " esta Vazio");
            return true;
        }
        return false;
    }

    public boolean campoVazio(JFXPasswordField txt, String campo, Label msg) {
        if (txt.getText().isEmpty()) {
            msg.setDisable(false);
            msg.setText("O campo " + campo + " esta Vazio");
            return true;
        }
        return false;
    }
//    public boolean cbVazio(JFXComboBox<NivelFuncionario> cbNivel, String campo, Label msg) {
//        if (cbNivel.getValue()==null) {
//            msg.setDisable(false);
//            msg.setText("O campo " + campo + " esta Vazio");
//            return true;
//        }
//        return false;
//    }

    public boolean cbVazioCidade(JFXComboBox<Cidade> cbCidade, String campo, Label msg) {
        if (cbCidade.getValue() == null) {
            msg.setDisable(false);
            msg.setText("O campo " + campo + " esta Vazio");
            return true;
        }
        return false;
    }

    public boolean cbVazioProduto(JFXComboBox<Produto> cbProduto, String campo, Label msg) {
        if (cbProduto.getValue() == null) {
            msg.setDisable(false);
            msg.setText("O campo " + campo + " esta Vazio");
            return true;
        }
        return false;
    }

    public boolean cbVazioCategoria(JFXComboBox<Categoria> cbCategoria) {
        Label msg = new Label();
        if (cbCategoria.getValue() == null) {
            msg.setDisable(false);
            msg.setText("O campo  esta Vazio");
            return true;
        }
        return false;
    }
     public boolean cbVazioCliente(JFXComboBox<Cliente> cbCliente) {
        Label msg = new Label();
        if (cbCliente.getValue() == null) {
            msg.setDisable(false);
            msg.setText("O campo  esta Vazio");
            return true;
        }
        return false;
    }
//     public boolean cbVazioCond(JFXComboBox<Condicao_pgto> cbCond, String campo, Label msg) {
//        if (cbCond.getValue()==null) {
//            msg.setDisable(false);
//            msg.setText("O campo " + campo + " esta Vazio");
//            return true;
//        }
//        return false;
//    }
//    public boolean cbVazioTpDes(JFXComboBox<Despesas> cbDesp, String campo, Label msg) {
//        if (cbDesp.getValue()==null) {
//            msg.setDisable(false);
//            msg.setText("O campo " + campo + " esta Vazio");
//            return true;
//        }
//        return false;
//    }

    public boolean cbVazioData(DatePicker data, String campo, Label msg) {
        if (data.getValue() == null) {
            msg.setDisable(false);
            msg.setText("O campo " + campo + " esta Vazio");
            return true;
        }
        return false;
    }

    public void Affirmation(String header, String text) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Apollo informa:");
        a.setHeaderText(header);
        a.setContentText(text);
        a.showAndWait();
    }

    public boolean Confirmation(String header, String text) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não");
        a.setTitle("Apollo informa:");
        a.setHeaderText(header);
        a.setContentText(text);
        a.getButtonTypes().setAll(btnSim, btnNao);
        a.showAndWait();
        return a.getResult() == btnSim;
    }

    public void Error(String header, String text) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Apollo informa:");
        a.setHeaderText(header);
        a.setContentText(text);
        a.showAndWait();
    }

    public void getMessage(String text) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("APOLLO13");
        a.setHeaderText("Apollo informa");
        a.setContentText(text);
        a.showAndWait();
    }
}
