/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import CamadaAcessoDados.Banco;
import CamadaAcessoDados.Conexao;
import CamadaAcessoDados.DALEmpresaParametros;
import Erros.Erros;
import Models.Cidade;
import Models.EmpresaParametros;
import Models.Estado;
import Models.Funcionario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author paulo
 */
public class EmpresaParametrosController {

    Erros msg = new Erros();

    public void carregaTabela(TableView<EmpresaParametros> tabela, String filtro) {
        DALEmpresaParametros dal = new DALEmpresaParametros();
        List<EmpresaParametros> res = dal.get(filtro);
        ObservableList<EmpresaParametros> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);

    }

    public void estadoInicial(Pane pnpesquisa, Pane pndados, Pane pnlogo, JFXButton btconfirmar,
            JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo,
            JFXTextField txfantasia, JFXTextField txcnpj, JFXTextField txrazasocial,
            JFXTextField txtelefone, JFXTextField txie, JFXTextField txlogradouro,
            JFXTextField txbairro, JFXTextField txnum, JFXComboBox<Estado> cbUf, JFXComboBox<Cidade> cbCid, JFXTextField txcep, JFXTextField txemail,
            JFXTextField txsite, ImageView logo, FontAwesomeIcon logoIcon, TableView<EmpresaParametros> tabela, Label lbErroCNPJ) {
        pnpesquisa.setDisable(false);
        pndados.setDisable(true);
        pnlogo.setDisable(true);
        btconfirmar.setDisable(true);
        btcancelar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        btnovo.setDisable(false);
        txfantasia.resetValidation();
        txcnpj.resetValidation();
        txrazasocial.resetValidation();
        txtelefone.resetValidation();
        txie.resetValidation();
        txlogradouro.resetValidation();
        txbairro.resetValidation();
        txnum.resetValidation();
        txcep.resetValidation();
        txemail.resetValidation();
        txsite.resetValidation();
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
        lbErroCNPJ.setText("");
        logo.setImage(null);
        logoIcon.setVisible(true);
        carregaTabela(tabela, "");
    }

    public void estadoEdicao(Pane pnpesquisa, Pane pndados, Pane pnlogo, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo,
            JFXTextField txfantasia, JFXTextField txcnpj, JFXTextField txrazasocial, JFXTextField txtelefone, JFXTextField txie,
            JFXTextField txlogradouro, JFXTextField txbairro, JFXTextField txnum, JFXComboBox<Estado> cbUf, JFXComboBox<Cidade> cbCid, JFXTextField txcep, JFXTextField txemail, JFXTextField txsite, ImageView logo, FontAwesomeIcon logoIcon, TableView<EmpresaParametros> tabela) {
        Erros msg = new Erros();
        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        pnlogo.setDisable(false);
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txfantasia.requestFocus();
        txfantasia.resetValidation();
        txcnpj.resetValidation();
        txrazasocial.resetValidation();
        txtelefone.resetValidation();
        txie.resetValidation();
        txlogradouro.resetValidation();
        txbairro.resetValidation();
        txnum.resetValidation();
        cbUf.resetValidation();
        cbCid.resetValidation();
        txcep.resetValidation();
        txemail.resetValidation();
        txsite.resetValidation();
    }

    public void alterar(JFXTextField txcod, JFXTextField txfantasia, JFXTextField txcnpj, JFXTextField txrazasocial, JFXTextField txtelefone, JFXTextField txie, JFXTextField txlogradouro, JFXTextField txbairro, JFXTextField txnum,
            JFXComboBox<Estado> cbUf, JFXComboBox<Cidade> cbCid, JFXTextField txcep, JFXTextField txemail, JFXTextField txsite,
            ImageView logo, FontAwesomeIcon logoIcon, TableView<EmpresaParametros> tabela, File arquivoaux) {
        EmpresaParametros ep = (EmpresaParametros) tabela.getSelectionModel().getSelectedItem();
        txcod.setText("" + ep.getCodigo());
        txfantasia.setText("" + ep.getFantasia());
        txcnpj.setText("" + ep.getCnpj());
        txrazasocial.setText("" + ep.getRazao_social());
        txtelefone.setText("" + ep.getTelefone());
        txlogradouro.setText("" + ep.getEndereco());
        txbairro.setText("" + ep.getBairro());
        txnum.setText("" + ep.getNumero());
        Cidade c = new Cidade();
        c = c.get(ep.getCidade().getCid_cod());

        Estado e = new Estado();
        e = e.getPorCid(c.getCid_cod());

        cbUf.getSelectionModel().select(0);
        cbUf.getSelectionModel().select(e);

        cbCid.getSelectionModel().select(0);
        cbCid.getSelectionModel().select(c);
        txie.setText("" + ep.getIe());
        txcep.setText("" + ep.getCep());
        txemail.setText("" + ep.getEmail());
        txsite.setText("" + ep.getSite());
        logoIcon.setVisible(false);
        InputStream is = ep.getFoto(ep);
        Image imgaux = null;
        if (is != null) {

            try {

                BufferedImage bimage = ImageIO.read(is);
                logo.setImage(SwingFXUtils.toFXImage(bimage, null));
                try {
                    setImageGarcon(null, null, arquivoaux, logo, null, null);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EmpresaParametrosController.class.getName()).log(Level.SEVERE, null, ex);
                }
//                arquivoaux = new File("");//alterar
//                arquivoaux.createNewFile();
                ImageIO.write(bimage, "jpg", arquivoaux);

            } catch (IOException ex) {
                Logger.getLogger(EmpresaParametrosController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            logo.setImage(imgaux);
            arquivoaux = new File("");//alterar
        }

    }

    public void cancelar(Pane pnpesquisa, Pane pndados, Pane pnlogo, JFXButton btconfirmar, JFXButton btcancelar,
            JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txfantasia,
            JFXTextField txcnpj, JFXTextField txrazasocial, JFXTextField txtelefone,
            JFXTextField txie, JFXTextField txlogradouro, JFXTextField txbairro, JFXTextField txnum,
            JFXComboBox<Estado> cbUf, JFXComboBox<Cidade> cbCid, JFXTextField txcep, JFXTextField txemail, JFXTextField txsite, ImageView logo, FontAwesomeIcon logoIcon, TableView<EmpresaParametros> tabela, Label lbErroCNPJ, Funcionario func) {
        if (!pndados.isDisabled())//encontra em estado de edição
        {
            estadoInicial(pnpesquisa,
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
        } else {
            btnovo.getScene().getWindow().hide();//fecha a janela
        }
    }

    public void Pesquisar(TableView<EmpresaParametros> tabela, JFXTextField txpesquisar, JFXRadioButton rbfantasia, JFXRadioButton rbcnpj) {
        if (!txpesquisar.getText().isEmpty()) {
            if (rbfantasia.isSelected()) {
                carregaTabela(tabela, "upper(nomefantasia) like '%" + txpesquisar.getText().toUpperCase() + "%'");
            } else {
                if (rbcnpj.isSelected()) {
                    carregaTabela(tabela, "upper(cnpj) like '%" + txpesquisar.getText().toUpperCase() + "%'");
                }
            }
        } else {
            carregaTabela(tabela, "upper(nomefantasia) like '%" + txpesquisar.getText().toUpperCase() + "%'");
        }
    }

    public void evtTabela(TableView<EmpresaParametros> tabela, JFXButton btalterar, JFXButton btapagar) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    public void pesquisarCidade(int cid_cod, JFXTextField txcodcid, JFXTextField txcid) {
        Cidade cidade = new Cidade();
        cidade = cidade.get(cid_cod);
        if (cidade != null) {
            txcodcid.setText(String.valueOf(cidade.getCid_cod()));
            txcid.setText(cidade.getCid_nome());
        } else {
            txcodcid.setText("0");
            txcid.setText("Valor digitado não encontrado...");
        }
    }

    private void setImageGarcon(File f, File arquivo, File arquivoaux, ImageView logo, Image imagem, FileInputStream arq) throws FileNotFoundException {
        if (f != null) {
            arq = new FileInputStream(arquivo);
            logo.setPreserveRatio(true);
            imagem = new Image(arquivo.toURI().toString());
            logo.setImage(imagem);

        }
        logo.setFitWidth(300);
        logo.setFitHeight(256);

    }

    public void btnImagem(File arquivo, File arquivoaux, ImageView logo, Image imagem, FileInputStream arq) {
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
                setImageGarcon(arquivo, arquivo, arquivoaux, logo, imagem, arq);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(EmpresaParametrosController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void apagar(TableView<EmpresaParametros> tabela) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            EmpresaParametros ep = new EmpresaParametros();
            ep = tabela.getSelectionModel().getSelectedItem();
            ep.apagar(ep.getCodigo());
            carregaTabela(tabela, "");
        }
    }

    public void confirmar(int rows, int cod, Pane pnpesquisa, Pane pndados, Pane pnlogo, JFXButton btconfirmar,
            JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txfantasia,
            JFXTextField txcnpj, JFXTextField txrazasocial, JFXTextField txtelefone, JFXTextField txie, JFXTextField txlogradouro,
            JFXTextField txbairro, JFXTextField txnum, JFXComboBox<Estado> cbUf, JFXComboBox<Cidade> cbCid, int codcid, JFXTextField txcep, JFXTextField txemail,
            JFXTextField txsite, ImageView logo, FontAwesomeIcon logoIcon, TableView<EmpresaParametros> tabela, Label lbErroCNPJ,
            String caminhoLogo, FileInputStream arq, File arquivo, File arquivoaux, Image imagem) {
        EmpresaParametros ep = new EmpresaParametros();

        if (cod == 0) {
            ep = new EmpresaParametros(cod, txfantasia.getText(), txrazasocial.getText(), txlogradouro.getText(),
                    txnum.getText(), new Cidade(codcid), txtelefone.getText(), txcnpj.getText(),
                    txsite.getText(), txie.getText(), txemail.getText(), txcep.getText(), txbairro.getText());
        } else {
            EmpresaParametros aux = new EmpresaParametros();
            aux = aux.get(cod);
            if (caminhoLogo == null) {
                caminhoLogo = aux.getLogo_grande();
            }
            ep = new EmpresaParametros(cod, txfantasia.getText(), txrazasocial.getText(), txlogradouro.getText(),
                    txnum.getText(), new Cidade(codcid), txtelefone.getText(), txcnpj.getText(),
                    txsite.getText(), txie.getText(), txemail.getText(), txcep.getText(), txbairro.getText());
        }

        if (ep.getCodigo() == 0) {
            if (rows >= 1) {
                msg.Error("Erro ao gravar!", "A empresa apenas pode ser alterada");
            } else if (ep.salvar(ep)) {
                Conexao con = Banco.getCon();
                cod = con.getMaxPK("empresa_parametros", "codigo");
                if (arq != null) {
                    if (ep.gravarFoto(new EmpresaParametros(cod), arq, arquivo)) {
                        msg.Confirmation("Gravação concluida", "Gravado com Sucesso");
                        arquivoaux = new File("");//alterar
                        try {
                            setImageGarcon(arquivo, arquivo, arquivoaux, logo, imagem, arq);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(EmpresaParametrosController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        estadoInicial(pnpesquisa,
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
                } else {
                    msg.Confirmation("Gravação concluida", "Gravado com Sucesso");
                    estadoInicial(pnpesquisa,
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

            } else {
                msg.Error("Erro ao gravar!", "Problemas ao Gravar");

            }

        } else if (ep.alterar(ep)) {
            if (arq != null) {
                if (ep.gravarFoto(ep, arq, arquivo)) {
                    try {
                        setImageGarcon(arquivo, arquivo, arquivoaux, logo, imagem, arq);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(EmpresaParametrosController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    msg.Confirmation("Gravação concluida", "Alterado com Sucesso");
                    estadoInicial(pnpesquisa,
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
            } else {
                msg.Confirmation("Gravação concluida", "Alterado com Sucesso");
                estadoInicial(pnpesquisa,
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

        } else {
            msg.Error("Erro ao alterar!", "Problemas ao Alterar");
        }
    }

}
