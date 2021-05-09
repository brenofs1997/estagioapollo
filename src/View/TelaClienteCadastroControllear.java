/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Ajuda.Ajuda;
import Controller.ClienteController;
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
import Models.Cidade;
import apollo.utils.MaskFieldUtil;
import Erros.Erros;
import Models.Cliente;
import Models.Estado;
import apollo.utils.ValidarCPF;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
    private JFXTextField txcodcid;
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
        MaskFieldUtil.cepField(txcep);
        MaskFieldUtil.cpfField(txcpf);
        MaskFieldUtil.foneField(txtelefone);
        MaskFieldUtil.monetaryField(txlimiteFiado);
        MaskFieldUtil.maxField(txnome, 70);
        MaskFieldUtil.maxField(txemail, 100);
        MaskFieldUtil.maxField(txendereco, 70);
        MaskFieldUtil.maxField(txbairro, 30);
        MaskFieldUtil.maxField(txlimiteFiado, 11);
        MaskFieldUtil.maxField(txnum, 10);
        MaskFieldUtil.maxField(txpesquisar, 70);
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
        CarregarUf();
        clControl.carregaTabela("", tabela);
        dtCadastro.setValue(LocalDate.now());
    }

    public void CarregarUf() {
        cbUf.setItems(FXCollections.observableArrayList(control.carregarUf()));
    }

    public void CarregarCid(int codcid) {
        cbCid.setItems(FXCollections.observableArrayList(control.CarregarCidUF(codcid)));
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
        cbUf.resetValidation();
        cbCid.resetValidation();
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
                cbUf,
                cbCid,
                txcep,
                txemail,
                dtCadastro,
                txlimiteFiado,
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
        int cod = 0, codcid = 0, erro = 0;
        ValidarCPF valida = new ValidarCPF();
        String ativo, pAcesso;

        Erros msg = new Erros();
        try {
            cod = Integer.parseInt(txcod.getText());

        } catch (Exception e) {
            cod = 0;
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

        if (txnome.getText().isEmpty()) {
            validar(txnome, "Campo não pode estar vazio!");
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

        if (dtCadastro.getValue() == null) {
            dtCadastro.setValue(LocalDate.now());
        }
        NumberFormat nf = new DecimalFormat("#,###.00");
        if (erro == 0) {

            if (cod == 0) {
                try {
                    if (clControl.gravar(cod, txnome.getText(), java.sql.Date.valueOf(dtCadastro.getValue()), txcpf.getText(),
                            txendereco.getText(), txbairro.getText(), txemail.getText(), nf.parse(txlimiteFiado.getText()).doubleValue(),
                            txcep.getText(), txtelefone.getText(), codcid, chkAtivo.isSelected(), txnum.getText(), 0.0)) {
                        msg.Affirmation("Apollo Informa:", "Gravação feita com sucesso");
                        estadoInicial();
                    } else {
                        msg.Error("Erro ", "Cliente não gravado");
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(TelaClienteCadastroControllear.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {

                try {
                    if (clControl.alterar(
                            cod, txnome.getText(), java.sql.Date.valueOf(dtCadastro.getValue()), txcpf.getText(),
                            txendereco.getText(), txbairro.getText(), txemail.getText(), nf.parse(txlimiteFiado.getText()).doubleValue(),
                            txcep.getText(), txtelefone.getText(), codcid, chkAtivo.isSelected(), txnum.getText(), 0.0)) {
                        msg.Affirmation("Apollo Informa:", "Alteração feita com sucesso");
                        estadoInicial();
                    } else {
                        msg.Error("Erro ", "Cliente não alterado");
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(TelaClienteCadastroControllear.class.getName()).log(Level.SEVERE, null, ex);
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
    private void Ajuda(ActionEvent event) {
        Ajuda a = new Ajuda();
        a.Ajuda("CadastrodeCliente.htm");
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

}
