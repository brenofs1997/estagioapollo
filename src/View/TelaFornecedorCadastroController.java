/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Ajuda.Ajuda;
import Controller.FornecedorController;
import Erros.Erros;
import Models.Categoria;
import Models.Cidade;
import Models.Estado;
import Models.Fornecedor;
import apollo.utils.MaskFieldUtil;
import apollo.utils.ValidarCPF;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author paulo
 */
public class TelaFornecedorCadastroController implements Initializable {

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
    private JFXRadioButton rbnome;
    @FXML
    private ToggleGroup Pesquisa1;
    @FXML
    private JFXRadioButton rbcpf;
    @FXML
    private JFXTextField txpesquisar;
    @FXML
    private JFXButton btpesquisar;
    @FXML
    private TableView<Fornecedor> tabela;
    @FXML
    private TableColumn<Fornecedor, Integer> colcod;
    @FXML
    private TableColumn<Fornecedor, String> colnome;
    @FXML
    private TableColumn<Fornecedor, String> colcnpj;
    @FXML
    private TableColumn<Fornecedor, String> colcidade;
    @FXML
    private TableColumn<Fornecedor, String> colativo;
    @FXML
    private Pane pndados;
    @FXML
    private JFXTextField txcod;
    @FXML
    private JFXCheckBox chkAtivo;
    @FXML
    private JFXTextField txnome;
    @FXML
    private JFXTextField txrazao;
    @FXML
    private JFXTextField txendereco;
    @FXML
    private JFXTextField txnum;
    @FXML
    private JFXTextField txbairro;
    @FXML
    private JFXComboBox<Estado> cbUf;
    @FXML
    private JFXComboBox<Cidade> cbCid;
    @FXML
    private JFXTextField txcep;
    @FXML
    private JFXTextField txtelefone;
    @FXML
    private JFXTextField txcnpj;
    @FXML
    private JFXTextField txemail;
    @FXML
    private JFXButton btRetirar;
    @FXML
    private JFXButton btAdd;
    @FXML
    private TableView<Categoria> tabelaCat;
    @FXML
    private JFXComboBox<Categoria> cbCat;
    @FXML
    private TableColumn<Categoria, String> cbdesc;

    FornecedorController control = new FornecedorController();
    Erros msg = new Erros();

    public static List<Categoria> itensCategoria = new ArrayList();

    public static List<Categoria> getItensCategoria() {
        return itensCategoria;
    }

    public static void setItensCategoria(List<Categoria> itensCategoria) {
        TelaFornecedorCadastroController.itensCategoria = itensCategoria;
    }
    @FXML
    private JFXButton btAjuda;
    @FXML
    private JFXTextField txtPesqProd;
    @FXML
    private JFXButton btPesqProd;
    @FXML
    private Label lbErroCNPJ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colnome.setCellValueFactory(new PropertyValueFactory("nomefantasia"));
        colcnpj.setCellValueFactory(new PropertyValueFactory("cnpj"));
        colcidade.setCellValueFactory(new PropertyValueFactory("cidade"));
        colativo.setCellValueFactory(new PropertyValueFactory("ativo"));

        cbdesc.setCellValueFactory(new PropertyValueFactory("descricao"));
        MaskFieldUtil.cepField(txcep);
        MaskFieldUtil.foneField(txtelefone);
        MaskFieldUtil.cpfCnpjField(txcnpj);

        MaskFieldUtil.maxField(txnome, 100);
        MaskFieldUtil.maxField(txrazao, 100);
        MaskFieldUtil.maxField(txemail, 100);
        MaskFieldUtil.maxField(txendereco, 70);
        MaskFieldUtil.maxField(txbairro, 30);
        MaskFieldUtil.maxField(txnum, 10);
        MaskFieldUtil.maxField(txpesquisar, 100);

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
        cbCid.setDisable(true);
        txnome.resetValidation();
        txcnpj.resetValidation();
        txendereco.resetValidation();
        txnum.resetValidation();
        txbairro.resetValidation();
        txtelefone.resetValidation();
        cbUf.resetValidation();
        cbCid.resetValidation();
        cbCid.getItems().clear();
        cbCat.resetValidation();
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
        tabelaCat.getItems().clear();
        CarregarUf();
        CarregarCategorias();
        control.carregaTabela("", tabela);
    }

    public void CarregarUf() {
        cbUf.setItems(FXCollections.observableArrayList(control.carregarUf()));
    }

    public void CarregarCategorias() {
        cbCat.setItems(FXCollections.observableArrayList(control.CarregarCategorias()));
    }

    private void estadoEdicao() {
        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txnome.requestFocus();
        txnome.resetValidation();
        txrazao.resetValidation();
        txcnpj.resetValidation();
        txendereco.resetValidation();
        txnum.resetValidation();
        txbairro.resetValidation();
        txtelefone.resetValidation();
        cbUf.resetValidation();
        cbCid.resetValidation();
        cbCat.resetValidation();
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
        control.alterar(txcod,
                txnome,
                txrazao,
                txcnpj,
                txendereco,
                txnum,
                txbairro,
                txtelefone,
                chkAtivo,
                cbUf,
                cbCid,
                txcep,
                txemail,
                tabelaCat,
                tabela);
    }

    @FXML
    private void clkapagar(ActionEvent event) {
        if (control.apagar(tabela)) {
            estadoInicial();
        }
    }

    @FXML
    private void clkconfirmar(ActionEvent event) {
        int cod = 0, codcid = 0, erro = 0;
        ValidarCPF valida = new ValidarCPF();
        String ativo = "Não Ativo";
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
        if (chkAtivo.isSelected()) {
            ativo = "Ativo";
        }

        if (txnome.getText().isEmpty()) {
            msg.campoVazio(txnome);
            erro = 1;
        }

        if (txrazao.getText().isEmpty()) {
            msg.campoVazio(txrazao);
            erro = 1;
        }

        if (txcnpj.getText().isEmpty()) {
            msg.campoVazio(txcnpj);
            erro = 1;
        } else {
            if (!valida.isValid(txcnpj.getText())) {
                lbErroCNPJ.setVisible(true);
                lbErroCNPJ.setText("CNPJ inválido! Digite o CNPJ novamente");
                txcnpj.requestFocus();
                erro = 1;
            }
        }

        if (tabelaCat.getItems().isEmpty()) {
            msg.Error("Apollo Informa", "Nenhuma categoria de produto foi selecionada!");
            erro = 1;
        }

        if (erro == 0) {
            if (cod == 0) {
                if (control.gravar(cod, txnome.getText(), txrazao.getText(), txendereco.getText(), txnum.getText(),
                        txbairro.getText(), codcid, txcep.getText(), txtelefone.getText(), txcnpj.getText(), txemail.getText(),
                        ativo, itensCategoria)) {
                    msg.Affirmation("Apollo Informa:", "Gravação feita com sucesso");
                    estadoInicial();
                } else {
                    msg.Error("Erro ", "Fornecedor não gravado");
                }

            } else {
                if (control.alterar(cod, txnome.getText(), txrazao.getText(), txendereco.getText(), txnum.getText(),
                        txbairro.getText(), codcid, txcep.getText(), txtelefone.getText(), txcnpj.getText(), txemail.getText(),
                        ativo, itensCategoria)) {
                    msg.Affirmation("Apollo Informa:", "Alteração feita com sucesso");
                    estadoInicial();
                } else {
                    msg.Error("Erro ", "Fornecedor não alterado");
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
            btcancelar.getScene().getWindow().hide();//fecha a janela
        }
    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        control.Pesquisar(txpesquisar, rbnome, rbcpf, tabela);
    }

    @FXML
    private void evtTabela(MouseEvent event) {
        control.evtTabela(tabela, btalterar, btapagar);
    }

    public void CarregarCid(int codcid) {
        cbCid.setItems(FXCollections.observableArrayList(control.CarregarCidUF(codcid)));
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

    @FXML
    private void validaCPF(KeyEvent event) {
    }

    public void atualizarTabelaItens() {

        ObservableList<Categoria> prod_v = FXCollections.observableArrayList(itensCategoria);
        tabelaCat.getItems().clear();
        tabelaCat.setItems(prod_v);

    }

    @FXML
    private void retirar(ActionEvent event) {
        if (tabelaCat.getSelectionModel().getSelectedItem() != null) {
            itensCategoria.remove(tabelaCat.getSelectionModel().getSelectedIndex());
            atualizarTabelaItens();
        } else {
            msg.Affirmation("Apollo Informa:", "Item não Selecionado!");
        }
    }

    @FXML
    private void Add(ActionEvent event) {
        if (cbCat.getSelectionModel().getSelectedItem() != null) {
            control.AddProduto(cbCat.getSelectionModel().getSelectedItem(), tabelaCat);
            atualizarTabelaItens();
        } else {
            msg.Affirmation("Apollo Informa:", "Item não Selecionado!");
        }
    }

    @FXML
    private void Ajuda(ActionEvent event) {
        Ajuda a = new Ajuda();
        a.Ajuda("CadastrodeFornecedores.htm");
    }

    @FXML
    private void PesqProd(ActionEvent event) {
        cbCid.setItems(FXCollections.observableArrayList(control.CarregarCidUFPesq(txtPesqProd.getText())));
        cbCid.setDisable(false);
        cbCid.getSelectionModel().select(0);
    }

}
