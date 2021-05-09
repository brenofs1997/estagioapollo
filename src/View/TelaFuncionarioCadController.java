/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Ajuda.Ajuda;
import Controller.FornecedorController;
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
import Controller.FuncionarioController;
import Models.Cidade;
import Models.Funcionario;
import Models.NivelFuncionario;
import apollo.utils.MaskFieldUtil;
import Erros.Erros;
import Models.Estado;
import apollo.utils.ValidarCPF;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TelaFuncionarioCadController implements Initializable {

    public static Cidade cid;
    public static boolean primeiro_acesso = false;

    public static Cidade getCid() {
        return cid;
    }

    public static void setCid(Cidade cid) {
        TelaFuncionarioCadController.cid = cid;
    }

    public static boolean isPrimeiro_acesso() {
        return primeiro_acesso;
    }

    public static void setPrimeiro_acesso(boolean primeiro_acesso) {
        TelaFuncionarioCadController.primeiro_acesso = primeiro_acesso;
    }

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
    private JFXTextField txcodcid;
    private JFXTextField txcid;
    @FXML
    private JFXTextField txcep;
    @FXML
    private JFXTextField txemail;
    @FXML
    private TableView<Funcionario> tabela;
    @FXML
    private TableColumn<Funcionario, Integer> colcod;
    @FXML
    private TableColumn<Funcionario, String> colnome;
    @FXML
    private TableColumn<Funcionario, String> colcpf;
    @FXML
    private TableColumn<Funcionario, String> colnivel;
    @FXML
    private TableColumn<Funcionario, String> colativo;
    @FXML
    private JFXComboBox<NivelFuncionario> cbnivel;
    @FXML
    private JFXTextField txnome;
    @FXML
    private JFXTextField txcpf;
    private JFXTextField txlendereco;

    @FXML
    private JFXTextField txlogin;
    @FXML
    private JFXPasswordField txsenha;
    @FXML
    private JFXRadioButton rbnome;
    @FXML
    private JFXRadioButton rbcpf;
    @FXML
    private JFXTextField txendereco;
    FuncionarioController cf = new FuncionarioController();
    @FXML
    private Label lbErroCPF;
    @FXML
    private JFXButton btAjuda;
    @FXML
    private JFXTextField txtPesqProd;
    @FXML
    private JFXButton btPesqProd;
    @FXML
    private JFXComboBox<Estado> cbUf;
    @FXML
    private JFXComboBox<Cidade> cbCid;

    FornecedorController control = new FornecedorController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        TelaFuncionarioCadController.setPrimeiro_acesso(false);
        MaskFieldUtil.cepField(txcep);
        MaskFieldUtil.cpfField(txcpf);
        MaskFieldUtil.foneField(txtelefone);

        MaskFieldUtil.maxField(txnome, 70);
        MaskFieldUtil.maxField(txemail, 100);
        MaskFieldUtil.maxField(txendereco, 70);
        MaskFieldUtil.maxField(txbairro, 30);
        MaskFieldUtil.maxField(txnum, 10);
        MaskFieldUtil.maxField(txlogin, 20);
        MaskFieldUtil.maxField(txsenha, 20);
        MaskFieldUtil.maxField(txpesquisar, 70);

        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colnome.setCellValueFactory(new PropertyValueFactory("nome"));
        colcpf.setCellValueFactory(new PropertyValueFactory("cpf"));
        colnivel.setCellValueFactory(new PropertyValueFactory("nivel"));
        colativo.setCellValueFactory(new PropertyValueFactory("ativo"));
        CarregarUf();
        cf.estadoInicial(pnpesquisa,
                pndados,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txnome,
                txcpf,
                txendereco,
                txnum,
                txbairro,
                txtelefone,
                txcep,
                txemail,
                cbnivel,
                txlogin,
                txsenha,
                tabela, lbErroCPF);
    }

    private void estadoInicial() {
        CarregarUf();
        cf.estadoInicial(pnpesquisa,
                pndados,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txnome,
                txcpf,
                txendereco,
                txnum,
                txbairro,
                txtelefone,
                txcep,
                txemail,
                cbnivel,
                txlogin,
                txsenha,
                tabela, lbErroCPF);
    }

    public void CarregarUf() {
        cbUf.setItems(FXCollections.observableArrayList(control.carregarUf()));
    }

    private void estadoEdicao() {
        cf.estadoEdicao(pnpesquisa,
                pndados,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txnome,
                txcpf,
                txendereco,
                txnum,
                txbairro,
                txtelefone,
                cbUf,
                cbCid,
                txcep,
                txemail,
                cbnivel,
                txlogin,
                txsenha,
                tabela, primeiro_acesso, chkAtivo);
    }

    @FXML
    private void clknovo(ActionEvent event) {
        CarregarUf();
        cf.estadoEdicao(pnpesquisa,
                pndados,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txnome,
                txcpf,
                txendereco,
                txnum,
                txbairro,
                txtelefone,
                cbUf,
                cbCid,
                txcep,
                txemail,
                cbnivel,
                txlogin,
                txsenha,
                tabela, primeiro_acesso, chkAtivo);
    }

    @FXML
    private void clkalterar(ActionEvent event) {
        cf.estadoEdicao(pnpesquisa,
                pndados,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txnome,
                txcpf,
                txendereco,
                txnum,
                txbairro,
                txtelefone,
                cbUf,
                cbCid,
                txcep,
                txemail,
                cbnivel,
                txlogin,
                txsenha,
                tabela, primeiro_acesso, chkAtivo);
        cf.alterar(txcod,
                txnome,
                txcpf,
                txendereco,
                txnum,
                txbairro,
                txtelefone,
                chkAtivo,
                txcodcid,
                cbUf,
                cbCid,
                txcep,
                txemail,
                cbnivel,
                txlogin,
                txsenha,
                cbnivel,
                tabela);

    }

    @FXML
    private void clkapagar(ActionEvent event) {
        cf.apagar(tabela);

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
        int cod = 0, erro = 0, codcid = 0;
        ValidarCPF valida = new ValidarCPF();
        String ativo, pAcesso;

        Erros msg = new Erros();
        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }
        if (txnome.getText().isEmpty()) {
            validar(txnome, "Campo não pode estar vazio!");
            erro = 1;
        }

        if (cbnivel.getSelectionModel().isEmpty()) {
            validarCb(cbnivel);
            erro = 1;
        }
        if (txlogin.getText().isEmpty()) {
            validar(txlogin, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txsenha.getText().isEmpty()) {
            validar(txsenha, "Campo não pode estar vazio!");
            erro = 1;
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
        }

        if (cf.validaLogin(txlogin, cod) && cod == 0) {
            txlogin.requestFocus();
            erro = 1;
        }

        if (cod == 0) {
            pAcesso = "S";
        } else {
            pAcesso = "N";
        }

        if (cbCid.getSelectionModel().getSelectedItem() == null) {
            msg.campoVazioCbx(cbCid);
            cbCid.setDisable(false);
            erro = 1;
        } else {
            try {
                codcid = cbCid.getSelectionModel().getSelectedItem().getCid_cod();
            } catch (Exception e) {
                codcid = 0;
            }
        }

        if (erro == 0) {

            cf.confirmar(pnpesquisa,
                    pndados,
                    btconfirmar,
                    btcancelar,
                    btapagar,
                    btalterar,
                    btnovo,
                    cod,
                    txcodcid,
                    txnome,
                    txcpf,
                    txendereco,
                    txnum,
                    txbairro,
                    txtelefone,
                    codcid,
                    txcep,
                    txemail,
                    cbnivel,
                    txlogin,
                    txsenha,
                    ativo,
                    pAcesso,
                    tabela, lbErroCPF);

        }
    }

    @FXML
    private void clkcancelar(ActionEvent event) {

        cf.cancelar(pnpesquisa,
                pndados,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txnome,
                txcpf,
                txendereco,
                txnum,
                txbairro,
                txtelefone,
                txcid,
                txcep,
                txemail,
                cbnivel,
                txlogin,
                txsenha,
                tabela, lbErroCPF);

    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        cf.Pesquisar(txpesquisar, rbnome, rbcpf, tabela);

    }

    @FXML
    private void evtTabela(MouseEvent event) {
        cf.evtTabela(tabela, btalterar, btapagar);

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

    @FXML
    private void validaLogin(KeyEvent event) {
//        if (Integer.parseInt(txcod.getText()) == 0) {
//            cf.validaLogin(txlogin, Integer.parseInt(txcod.getText()), event);
//        }

    }

    @FXML
    private void Ajuda(ActionEvent event) {
        Ajuda a = new Ajuda();
        a.Ajuda("CadastrodeFuncionarios.htm");
    }

    @FXML
    private void PesqProd(ActionEvent event) {
        cbCid.setItems(FXCollections.observableArrayList(control.CarregarCidUFPesq(txtPesqProd.getText())));
        cbCid.setDisable(false);
        cbCid.getSelectionModel().select(0);
    }

    @FXML
    private void carregaCidade(ActionEvent event) {
        if (cbUf.getSelectionModel().getSelectedItem() != null) {
            int codigo = 0;
            codigo = cbUf.getSelectionModel().getSelectedItem().getCod();
            cbCid.setDisable(false);
            CarregarCid(codigo);
        } else {
            cbCid.setDisable(true);
        }
    }

    public void CarregarCid(int codcid) {
        cbCid.setItems(FXCollections.observableArrayList(control.CarregarCidUF(codcid)));
    }

}
