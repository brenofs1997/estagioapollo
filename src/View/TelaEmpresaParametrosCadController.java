/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Ajuda.Ajuda;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import CamadaAcessoDados.Banco;
import Controller.EmpresaParametrosController;
import Controller.FornecedorController;
import Erros.Erros;
import Models.Cidade;
import Models.EmpresaParametros;
import Models.Estado;
import Models.Funcionario;
import apollo.utils.MaskFieldUtil;
import apollo.utils.ValidarCPF;
import com.jfoenix.controls.JFXComboBox;
//import Models.Funcionario;
//import utils.Erros;
//import utils.ValidarCPF;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaEmpresaParametrosCadController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static Cidade cid;
    public byte[] b = new byte[10000000];
    Image imagem;
    private File arquivo = new File("");
    private File arquivoaux = new File("");
    private FileInputStream arq;

    public static Cidade getCid() {
        return cid;
    }

    public static void setCid(Cidade cid) {
        TelaEmpresaParametrosCadController.cid = cid;
    }

    private JFXRadioButton rbfantasia;
    private JFXRadioButton rbcnpj;
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
    private JFXTextField txpesquisar;
    @FXML
    private TableView<EmpresaParametros> tabela;
    @FXML
    private TableColumn<EmpresaParametros, Integer> colcod;
    @FXML
    private TableColumn<EmpresaParametros, String> colnome;
    @FXML
    private TableColumn<EmpresaParametros, String> colcid;
    @FXML
    private TableColumn<EmpresaParametros, String> colfone;
    @FXML
    private Pane pndados;
    @FXML
    private JFXTextField txcod;
    @FXML
    private JFXTextField txfantasia;
    @FXML
    private JFXTextField txcnpj;
    @FXML
    private JFXTextField txrazasocial;
    @FXML
    private JFXTextField txtelefone;
    @FXML
    private JFXTextField txlogradouro;
    @FXML
    private JFXTextField txbairro;
    @FXML
    private JFXTextField txnum;

    @FXML
    private JFXTextField txcep;
    @FXML
    private JFXTextField txemail;
    @FXML
    private JFXTextField txie;
    @FXML
    private ImageView logo;
    @FXML
    private JFXTextField txsite;
    @FXML
    private Pane pnlogo;
    @FXML
    private FontAwesomeIcon logoIcon;
    private String caminhoLogo = "";
    EmpresaParametrosController emprecontrol = new EmpresaParametrosController();
    Erros msg = new Erros();
    @FXML
    private Label lbErroCNPJ;
    public static Funcionario func;

    public static Funcionario getFunc() {
        return func;
    }

    public static void setFunc(Funcionario func) {
        TelaEmpresaParametrosCadController.func = func;
    }
    @FXML
    private JFXButton btAjuda;
    @FXML
    private JFXTextField txtPesqProd;
    @FXML
    private JFXButton btPesqProd;
    @FXML
    private JFXComboBox<Cidade> cbCid;
    @FXML
    private JFXComboBox<Estado> cbUf;

    FornecedorController control = new FornecedorController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MaskFieldUtil.cpfCnpjField(txcnpj);
        MaskFieldUtil.foneField(txtelefone);
        MaskFieldUtil.cepField(txcep);

        MaskFieldUtil.maxField(txfantasia, 100);
        MaskFieldUtil.maxField(txrazasocial, 100);
        MaskFieldUtil.maxField(txemail, 100);
        MaskFieldUtil.maxField(txlogradouro, 70);
        MaskFieldUtil.maxField(txbairro, 30);

        MaskFieldUtil.maxField(txnum, 10);

        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colnome.setCellValueFactory(new PropertyValueFactory("fantasia"));
        colcid.setCellValueFactory(new PropertyValueFactory("cidade"));
        colfone.setCellValueFactory(new PropertyValueFactory("telefone"));
        CarregarUf();
        emprecontrol.estadoInicial(pnpesquisa,
                pndados,
                pnlogo,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txfantasia,
                txcnpj,
                txrazasocial,
                txtelefone,
                txie,
                txlogradouro,
                txbairro,
                txnum,
                cbUf,
                cbCid,
                txcep,
                txemail,
                txsite,
                logo,
                logoIcon, tabela, lbErroCNPJ);
    }

    public void CarregarUf() {
        cbUf.setItems(FXCollections.observableArrayList(control.carregarUf()));
    }

    private void estadoInicial() {
        CarregarUf();
        emprecontrol.estadoInicial(pnpesquisa,
                pndados,
                pnlogo,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txfantasia,
                txcnpj,
                txrazasocial,
                txtelefone,
                txie,
                txlogradouro,
                txbairro,
                txnum,
                cbUf,
                cbCid,
                txcep,
                txemail,
                txsite,
                logo,
                logoIcon, tabela, lbErroCNPJ);
    }

    private void carregaTabela(String filtro) {
        emprecontrol.carregaTabela(tabela, filtro);
    }

    private void estadoEdicao() {
        emprecontrol.estadoEdicao(pnpesquisa,
                pndados,
                pnlogo,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txfantasia,
                txcnpj,
                txrazasocial,
                txtelefone,
                txie,
                txlogradouro,
                txbairro,
                txnum,
                cbUf,
                cbCid,
                txcep,
                txemail,
                txsite,
                logo,
                logoIcon, tabela);

    }

    @FXML
    private void clknovo(ActionEvent event) {
        CarregarUf();
        Banco.conectar();
        ResultSet rs = Banco.getCon().consultar("SELECT  * FROM empresa_parametros");
        int rows = 0;
        try {
            while (rs.next()) {
                rows = rs.getRow();
            }

        } catch (SQLException ex) {

        }
        if (rows >= 1) {
            msg.Error("Erro:", "Ja existe uma empresa cadastrada!");
        } else {
            emprecontrol.estadoEdicao(pnpesquisa,
                    pndados,
                    pnlogo,
                    btconfirmar,
                    btcancelar,
                    btapagar,
                    btalterar,
                    btnovo,
                    txfantasia,
                    txcnpj,
                    txrazasocial,
                    txtelefone,
                    txie,
                    txlogradouro,
                    txbairro,
                    txnum,
                    cbUf,
                    cbCid,
                    txcep,
                    txemail,
                    txsite,
                    logo,
                    logoIcon, tabela);
        }
    }

    @FXML
    private void clkalterar(ActionEvent event) throws IOException {
        emprecontrol.estadoEdicao(pnpesquisa,
                pndados,
                pnlogo,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txfantasia,
                txcnpj,
                txrazasocial,
                txtelefone,
                txie,
                txlogradouro,
                txbairro,
                txnum,
                cbUf,
                cbCid,
                txcep,
                txemail,
                txsite,
                logo,
                logoIcon, tabela);

        emprecontrol.alterar(txcod, txfantasia,
                txcnpj,
                txrazasocial,
                txtelefone,
                txie,
                txlogradouro,
                txbairro,
                txnum,
                cbUf,
                cbCid,
                txcep,
                txemail,
                txsite,
                logo,
                logoIcon, tabela, arquivoaux);

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
        validator.setMessage("Campo não pode estar vazio!");
        campo.validate();
    }

    @FXML
    private void clkconfirmar(ActionEvent event) throws FileNotFoundException, IOException, SQLException {
        int cod, erro = 0, codcid = 0;
        ValidarCPF valida = new ValidarCPF();
        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }
        if (CampoVazio(txfantasia.getText())) {
            validar(txfantasia, "");
            erro = 1;
        }

        if (txcnpj.getText().isEmpty()) {
            validar(txcnpj, "");
            erro = 1;
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

        if (!txcnpj.getText().isEmpty()) {
            if (!valida.isValid(txcnpj.getText())) {
                lbErroCNPJ.setVisible(true);
                lbErroCNPJ.setText("CNPJ inválido! Digite o CNPJ novamente");
                txcnpj.requestFocus();
                erro = 1;
            }

        }

        Banco.conectar();
        ResultSet rs = Banco.getCon().consultar("SELECT  * FROM empresa_parametros");
        int rows = rs.getRow();
        try {
            while (rs.next()) {
                rows = rs.getRow();
            }

        } catch (SQLException ex) {

        }

        if (erro == 0) {

            emprecontrol.confirmar(rows, cod,
                    pnpesquisa,
                    pndados,
                    pnlogo,
                    btconfirmar,
                    btcancelar,
                    btapagar,
                    btalterar,
                    btnovo,
                    txfantasia,
                    txcnpj,
                    txrazasocial,
                    txtelefone,
                    txie,
                    txlogradouro,
                    txbairro,
                    txnum,
                    cbUf,
                    cbCid,
                    codcid,
                    txcep,
                    txemail,
                    txsite,
                    logo,
                    logoIcon, tabela, lbErroCNPJ, caminhoLogo, arq, arquivo, arquivoaux, imagem);

        }
    }

    @FXML
    private void clkcancelar(ActionEvent event) {

        emprecontrol.cancelar(pnpesquisa,
                pndados,
                pnlogo,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txfantasia,
                txcnpj,
                txrazasocial,
                txtelefone,
                txie,
                txlogradouro,
                txbairro,
                txnum,
                cbUf,
                cbCid,
                txcep,
                txemail,
                txsite,
                logo,
                logoIcon, tabela, lbErroCNPJ, func);

    }

    private void clkPesquisar(ActionEvent event) {
        emprecontrol.Pesquisar(tabela, txpesquisar, rbfantasia, rbcnpj);

    }

    @FXML
    private void evtTabela(MouseEvent event) {
        emprecontrol.evtTabela(tabela, btalterar, btapagar);

    }

    private void exit(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void clkapagar(ActionEvent event) {
        emprecontrol.apagar(tabela);
    }

    @FXML
    private void evtbtnImagem(MouseEvent event) {
        btnImagem();

    }

    private void setImageGarcon(File f) throws FileNotFoundException {
        if (f != null) {
            arq = new FileInputStream(arquivo);
            logo.setPreserveRatio(true);
            imagem = new Image(arquivo.toURI().toString());
            logo.setImage(imagem);

        }
        logo.setFitWidth(300);
        logo.setFitHeight(256);

    }

    public void btnImagem() {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPEG (*.jpeg)", "*.jpeg");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("JPG (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilter3 = new FileChooser.ExtensionFilter("PNG (*.png)", "*.png");
        FileChooser.ExtensionFilter extFilter4 = new FileChooser.ExtensionFilter("Todos os arquivos (*.)", "*");
        fc.getExtensionFilters().add(extFilter3);
        fc.getExtensionFilters().add(extFilter);
        fc.getExtensionFilters().add(extFilter2);
        fc.getExtensionFilters().add(extFilter4);
        arquivo = fc.showOpenDialog(null);

        if (arquivo != null) {
            arquivoaux = arquivo;
            try {
                setImageGarcon(arquivo);
                logoIcon.setVisible(false);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(EmpresaParametrosController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @FXML
    private void validaCNPJ(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcnpj.getText().isEmpty()) {
            ValidarCPF valida = new ValidarCPF();
            if (!valida.isValid(txcnpj.getText())) {
                lbErroCNPJ.setVisible(true);
                lbErroCNPJ.setText("CNPJ inválido! Digite o CNPJ novamente");
                txcnpj.requestFocus();

            } else {
                lbErroCNPJ.setText("");
                lbErroCNPJ.setVisible(false);
            }
        } else {
            lbErroCNPJ.setText("");
            lbErroCNPJ.setVisible(false);
        }
    }

    @FXML
    private void Ajuda(ActionEvent event) {
        Ajuda a = new Ajuda();
        a.Ajuda("CadastroEmpresa.htm");
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
