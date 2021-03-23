/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ClienteController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import Models.Cidade;
import apollo.utils.MaskFieldUtil;
import Erros.Erros;
import Models.Cliente;
import apollo.utils.ValidarCPF;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TelaClienteCadastroControllear implements Initializable {

    @FXML
    private Pane pnbtn;
    @FXML
    private JFXButton btnovo;
    @FXML
    private JFXButton btalterar;
    @FXML
    private JFXButton btapagar;
    @FXML
    private JFXButton btconfirmar;
    @FXML
    private JFXButton btcancelar;
    @FXML
    private Pane pnpesquisa;
    @FXML
    private JFXTextField txpesquisar;
    @FXML
    private JFXButton btpesquisar;

    @FXML
    private Pane pndados;
    @FXML
    private JFXTextField txcod;
    @FXML
    private JFXCheckBox chkAtivo;
    @FXML
    private ToggleGroup Pesquisa1;

    @FXML
    private JFXTextField txtelefone;
    @FXML
    private JFXTextField txbairro;
    @FXML
    private JFXTextField txnum;
    @FXML
    private JFXTextField txcodcid;
    @FXML
    private JFXButton btnpesquisarcid;
    @FXML
    private JFXTextField txcid;
    @FXML
    private JFXTextField txcep;
    @FXML
    private JFXTextField txemail;
    @FXML
    private TableView<Cliente> tabela;
    @FXML
    private TableColumn<Cliente, Integer> colcod;
    @FXML
    private TableColumn<Cliente, String> colnome;
    @FXML
    private TableColumn<Cliente, String> colcpf;
    private TableColumn<Cliente, String> colnivel;
    @FXML
    private TableColumn<Cliente, String> colativo;

    @FXML
    private JFXTextField txnome;
    @FXML
    private JFXTextField txcpf;

    private JFXTextField txlogin;
    private JFXPasswordField txsenha;
    @FXML
    private JFXRadioButton rbnome;
    @FXML
    private JFXRadioButton rbcpf;
    @FXML
    private JFXTextField txendereco;
    ClienteController clControl = new ClienteController();
    @FXML
    private Label lbErroCPF;
    @FXML
    private TableColumn<Cliente, String> colcidade;
    public static Cidade cid;

    public static Cidade getCid() {
        return cid;
    }

    public static void setCid(Cidade cid) {
        TelaClienteCadastroControllear.cid = cid;
    }
    @FXML
    private DatePicker dtCadastro;
    @FXML
    private JFXTextField txlimiteFiado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        MaskFieldUtil.cepField(txcep);
        MaskFieldUtil.cpfField(txcpf);
        MaskFieldUtil.foneField(txtelefone);
        MaskFieldUtil.numericField(txcodcid);

        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colnome.setCellValueFactory(new PropertyValueFactory("nome"));
        colcpf.setCellValueFactory(new PropertyValueFactory("cpf"));
        colcidade.setCellValueFactory(new PropertyValueFactory("cidade"));
        colativo.setCellValueFactory(new PropertyValueFactory("ativo"));
        estadoInicial();
    }

    public void estadoInicial() {
        pnpesquisa.setDisable(false);
        pndados.setDisable(true);
        btconfirmar.setDisable(true);
        btcancelar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        btnovo.setDisable(false);

        txnome.resetValidation();
        txcpf.resetValidation();
        txendereco.resetValidation();
        txnum.resetValidation();
        txbairro.resetValidation();
        txtelefone.resetValidation();
        txcid.resetValidation();
        txcep.resetValidation();
        txemail.resetValidation();

        ObservableList<Node> componentes = pndados.getChildren();//”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl)//textfield, textarea e htmleditor
            {
                ((TextInputControl) n).setText("");
            }
            if (n instanceof ComboBox) {
                ((ComboBox) n).getSelectionModel().select(0);
            }
        }

        clControl.carregaTabela("", tabela);
    }

    private void estadoEdicao() {

        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txnome.requestFocus();
        txnome.resetValidation();
        txcpf.resetValidation();
        txendereco.resetValidation();
        txnum.resetValidation();
        txbairro.resetValidation();
        txtelefone.resetValidation();
        txcid.resetValidation();
        txcep.resetValidation();
        txemail.resetValidation();

    }

    @FXML
    private void clknovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkalterar(ActionEvent event) {
        estadoEdicao();
        clControl.alterar(txcod,
                txnome,
                txcpf,
                txendereco,
                txnum,
                txbairro,
                txtelefone,
                chkAtivo,
                txcodcid,
                txcid,
                txcep,
                txemail,
                dtCadastro,
                tabela);

    }

    @FXML
    private void clkapagar(ActionEvent event) {
        if (clControl.apagar(tabela)) {
            estadoInicial();
        }

    }

    private boolean CampoVazio(String valor) {
        boolean resultado = (valor.isEmpty() || valor.trim().isEmpty());
        return resultado;
    }

    private int retornaValor(String valor) {
        int res = 0;
        if (!valor.equals("")) {
            res = Integer.parseInt(valor);
        }
        return res;
    }

    public void validar(JFXTextField campo, String texto) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        campo.resetValidation();
        campo.getValidators().add(validator);
        validator.setMessage(texto);
        campo.validate();
    }

    public void validar(JFXPasswordField campo, String texto) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        campo.resetValidation();
        campo.getValidators().add(validator);
        validator.setMessage(texto);
        campo.validate();
    }

    public void validarCb(JFXComboBox campo) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        campo.resetValidation();
        campo.getValidators().add(validator);
        validator.setMessage("Campo não pode estar vazio!");
        campo.validate();
    }

    @FXML
    private void clkconfirmar(ActionEvent event) {
        int cod = 0, codcid, erro = 0;
        ValidarCPF valida = new ValidarCPF();
        String ativo, pAcesso;

        Erros msg = new Erros();
        try {
            cod = Integer.parseInt(txcod.getText());

        } catch (Exception e) {
            cod = 0;
        }
        try {

            codcid = Integer.parseInt(txcodcid.getText());

        } catch (Exception e) {
            codcid = 0;
        }
        if (txnome.getText().isEmpty()) {
            validar(txnome, "Campo não pode estar vazio!");
            erro = 1;
        }

        if (txcid.getText().isEmpty()) {
            validar(txcid, "Campo não pode estar vazio!");
            erro = 1;
        }

        if (txlimiteFiado.getText().isEmpty()) {
            txlimiteFiado.setText("0");
        }
        if (chkAtivo.isSelected()) {
            ativo = "ativo";

        } else {
            ativo = "não ativo";
        }
        if (!txcpf.getText().isEmpty()) {
            if (!valida.isValid(txcpf.getText())) {
                lbErroCPF.setVisible(true);
                lbErroCPF.setText("CPF inválido! Digite o CPF novamente");
                txcpf.requestFocus();
                erro = 1;
            }
        } else {
            validar(txcpf, "Campo não pode estar vazio!");
            erro = 1;
        }

        if (erro == 0) {

            if (cod == 0) {
                if (clControl.gravar(cod, txnome.getText(), java.sql.Date.valueOf(dtCadastro.getValue()), txcpf.getText(),
                        txendereco.getText(), txbairro.getText(), txemail.getText(), Double.parseDouble(txlimiteFiado.getText()),
                        txcep.getText(), txtelefone.getText(), codcid, chkAtivo.isSelected(), txnum.getText(), 0.0)) {
                    msg.Affirmation("Apollo Informa:", "Gravação feita com sucesso");
                    estadoInicial();
                } else {
                    msg.Error("Erro ", "Cliente não gravado");
                }

            } else {

                if (clControl.alterar(
                        cod, txnome.getText(), java.sql.Date.valueOf(dtCadastro.getValue()), txcpf.getText(),
                        txendereco.getText(), txbairro.getText(), txemail.getText(), Double.parseDouble(txlimiteFiado.getText()),
                        txcep.getText(), txtelefone.getText(), codcid, chkAtivo.isSelected(), txnum.getText(), 0.0)) {
                    msg.Affirmation("Apollo Informa:", "Alteração feita com sucesso");
                    estadoInicial();
                } else {
                    msg.Error("Erro ", "Cliente não alterado");
                }
            }

        }
    }

    @FXML
    private void clkcancelar(ActionEvent event) {
        if (!pndados.isDisabled())//encontra em estado de edição
        {
            estadoInicial();
        } else {
            btnovo.getScene().getWindow().hide();//fecha a janela
        }

    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        clControl.Pesquisar(txpesquisar, rbnome, rbcpf, tabela);

    }

    @FXML
    private void evtTabela(MouseEvent event) {
        clControl.evtTabela(tabela, btalterar, btapagar);

    }

    public void pesquisarCidade(int cid_cod) {
        clControl.Pesquisar(txpesquisar, txcodcid, txcid, cid_cod);
    }

    @FXML
    private void evtProcurarCidade(ActionEvent event) {
        Erros msg = new Erros();
        try {
            Stage stage = new Stage();
            Parent pesquisa = FXMLLoader.load(getClass().getResource("Consulta.fxml"));
            Scene scene = new Scene(pesquisa);
            //Show main Window
            stage.setScene(scene);
//            stage.getIcons().add(new Image(getClass().getResourceAsStream("")));
            stage.setResizable(false);
            stage.setTitle("Consulta de cidades");
            stage.showAndWait();
            if (cid != null) {
                pesquisarCidade(cid.getCid_cod());
                txcep.requestFocus();
            }

        } catch (Exception e) {
            msg.Error("", e.getMessage());
        }
    }

    @FXML
    private void onExitCidade(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcodcid.getText().isEmpty()) {
            pesquisarCidade(Integer.parseInt(txcodcid.getText()));
            txcep.requestFocus();
        }
    }

    @FXML
    private void evRbNome(ActionEvent event) {
//        if (rbcpf.isSelected()) {
//            rbcpf.setSelected(false);
//        } else {
//            rbnome.setSelected(true);
//        }
//        MaskFieldUtil.onlyDigitsValue(txpesquisar);
    }

    @FXML
    private void evRbCpf(ActionEvent event) {
//        if (rbnome.isSelected()) {
//            rbnome.setSelected(false);
//        } else {
//            rbcpf.setSelected(true);
//        }
//        MaskFieldUtil.cpfField(txpesquisar);
    }

    @FXML
    private void validaCPF(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcpf.getText().isEmpty()) {
            ValidarCPF valida = new ValidarCPF();
            if (!valida.isValid(txcpf.getText())) {
                lbErroCPF.setVisible(true);
                lbErroCPF.setText("CPF inválido! Digite o CPF novamente");
                txcpf.requestFocus();

            } else {
                lbErroCPF.setVisible(false);
            }
        }
    }

}
