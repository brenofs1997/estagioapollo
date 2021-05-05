/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Erros.Erros;
import Models.Cidade;
import Models.Cliente;
import Models.Estado;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

import javafx.scene.control.TableView;

import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author paulo
 */
public class ClienteController {

    Erros msg = new Erros();

    public void carregaTabela(String Filtro, TableView<Cliente> tabela) {

        Cliente c = new Cliente();
        List<Cliente> res = c.get(Filtro);
        ObservableList<Cliente> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }

    public boolean gravar(int codigo, String nome, Date data_cadastro, String cpf, String endereco, String bairro, String email, double limite_fiado, String cep, String telefone, int codcid, boolean ativo, String numero, double saldo_devedor) {
        Cliente c = new Cliente(codigo, nome, data_cadastro, cpf, endereco, bairro, email, limite_fiado, cep, telefone, new Cidade(codcid), ativo, numero, saldo_devedor);
        return c.gravar(c);
    }

    public void alterar(JFXTextField txcod, JFXTextField txnome, JFXTextField txcpf, JFXTextField txendereco, JFXTextField txnum, JFXTextField txbairro, JFXTextField txtelefone, JFXCheckBox chkAtivo, JFXComboBox<Estado> cbUf, JFXComboBox<Cidade> cbCid, JFXTextField txcep, JFXTextField txemail, DatePicker dtCadastro, TableView<Cliente> tabela) {
        Cidade cidade = new Cidade();
        Cliente c = tabela.getSelectionModel().getSelectedItem();
        c = c.get(c.getCodigo());
        txcod.setText("" + c.getCodigo());
       
        cidade = cidade.get(c.getCidade().getCid_cod());

        Estado e = new Estado();
        e = e.getPorCid(cidade.getCid_cod());

        cbUf.getSelectionModel().select(0);
        cbUf.getSelectionModel().select(e);

        cbCid.getSelectionModel().select(0);
        cbCid.getSelectionModel().select(cidade);
        
        txnome.setText("" + c.getNome());
        txcpf.setText("" + c.getCpf());
        txendereco.setText("" + c.getEndereco());
        txtelefone.setText("" + c.getTelefone());
        if (c.isAtivo() == true) {
            chkAtivo.setSelected(true);
            chkAtivo.setText("" + c.isAtivo());
        } else {
            chkAtivo.setSelected(false);
            chkAtivo.setText("" + c.isAtivo());
        }
        txbairro.setText("" + c.getBairro());
        txnum.setText("" + c.getNumero());
       
        txcep.setText("" + c.getCep());
         int ano = c.getData_cadastro().getYear() + 1900;
        int mes = c.getData_cadastro().getMonth() + 1;
        int dia = c.getData_cadastro().getDate();
        dtCadastro.setValue(LocalDate.of(ano, mes, dia));
        dtCadastro.setShowWeekNumbers(true);

        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter
                    = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }//To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                } //To change body of generated methods, choose Tools | Templates.
            }
        };
        dtCadastro.setConverter(converter);
        txemail.setText("" + c.getEmail());
    }

    public void evtTabela(TableView<Cliente> tabela, JFXButton btalterar, JFXButton btapagar) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    public boolean alterar(int codigo, String nome, Date data_cadastro, String cpf, String endereco, String bairro, String email, double limite_fiado, String cep, String telefone, int codcid, boolean ativo, String numero, double saldo_devedor) {

        Cliente aux = new Cliente(codigo, nome, data_cadastro, cpf, endereco, bairro, email, limite_fiado, cep, telefone, new Cidade(codcid), ativo, numero, saldo_devedor);

        return aux.alterar(aux);
    }

    public boolean apagar(TableView<Cliente> tabela) {
        boolean confirma = false;
        Cliente aux = new Cliente();
        aux = tabela.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirmar exclusão?");
        if (a.showAndWait().get() == ButtonType.OK) {

            if (aux.apagar(aux.getCodigo())) {
                msg.Affirmation("Apollo Informa:", "Exclusão feita com sucesso");
                return true;
            } else {
                msg.Error("Erro ", "Exclusão não realizada");
            }
        }
        return confirma;
    }

    public void Pesquisar(JFXTextField txpesquisar, JFXRadioButton rbnome, JFXRadioButton rbcpf, TableView<Cliente> tabela) {
        if (!txpesquisar.getText().isEmpty()) {
            if (rbnome.isSelected()) {
                carregaTabela("upper(nome) like '%" + txpesquisar.getText().toUpperCase() + "%'", tabela);
            } else {
                if (rbcpf.isSelected()) {
                    carregaTabela("upper(cpf) like '%" + txpesquisar.getText().toUpperCase() + "%'", tabela);
                }
            }
        } else {
            carregaTabela("upper(nome) like '%" + txpesquisar.getText().toUpperCase() + "%'", tabela);
        }

    }

    public void Pesquisar(JFXTextField txpesquisar, JFXTextField txcodcid, JFXTextField txcid, int cid_cod) {
        Cidade c = new Cidade();
        c = c.get(cid_cod);
        if (c != null) {
            txcodcid.setText(String.valueOf(c.getCid_cod()));
            txcid.setText(c.getCid_nome());
        } else {
            txcodcid.setText("0");
            txcid.setText("Nenhuma cidade encontrada...");
        }
    }

}
