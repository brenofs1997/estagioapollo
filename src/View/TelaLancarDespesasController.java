/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ContasPagarController;
import Erros.Erros;
import Models.CondicaoPagamento;
import Models.ContasPagar;
import Models.Funcionario;
import Models.TipoDespesa;
import apollo.utils.MaskFieldUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author paulo
 */
public class TelaLancarDespesasController implements Initializable {

    @FXML
    private JFXButton btNovo;
    @FXML
    private JFXButton btConsultar;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private JFXButton btExcluir;
    @FXML
    private JFXButton btFinalizar;
    @FXML
    private JFXTextField txcodigo;
    @FXML
    private DatePicker dtEmissao;
    @FXML
    private JFXTextField txValor;
    @FXML
    private DatePicker dtDtvenc;
    @FXML
    private JFXComboBox<CondicaoPagamento> cbCondPgto;
    @FXML
    private JFXTextField txQuant;
    @FXML
    private TableView<ContasPagar> tabela;
    @FXML
    private TableColumn<ContasPagar, String> colparc;
    @FXML
    private TableColumn<ContasPagar, Date> colvenc;
    @FXML
    private TableColumn<ContasPagar, Double> colvalor;
    public static List<ContasPagar> listaParcrlad=new ArrayList();
    Erros msg = new Erros();

    public static ContasPagar desp;
     public static ContasPagar getDesp() {
        return desp;
    }

    public static void setDesp(ContasPagar desp) {
        DespesasConsultaController.desp = desp;
    }
    
    
    public static List<ContasPagar> getListaParcrlad() {
        return listaParcrlad;
    }

    public static void setListaParcrlad(List<ContasPagar> listaParcrlad) {
        TelaLancarDespesasController.listaParcrlad = listaParcrlad;
    }
    ContasPagarController controller = new ContasPagarController();
    @FXML
    private JFXButton btGerar;
    @FXML
    private JFXComboBox<TipoDespesa> cbTipo;
     public static Funcionario func;

    public static Funcionario getFunc() {
        return func;
    }

    public static void setFunc(Funcionario func) {
        TelaLancarDespesasController.func = func;
    }
    @FXML
    private JFXTextField txDias;
    @FXML
    private Pane pnconteudo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colparc.setCellValueFactory(new PropertyValueFactory("parcela"));
        colvalor.setCellValueFactory(new PropertyValueFactory("valor"));
        colvenc.setCellValueFactory(new PropertyValueFactory("vencimento"));
        MaskFieldUtil.monetaryField(txValor);
         CarregaCondPgto();
        
    }
    public void CarregaCondPgto() {
        cbCondPgto.setItems(FXCollections.observableArrayList(controller.CarregaCondPgto()));
    }
    @FXML
    private void CarregaTipo() {
        cbTipo.setItems(FXCollections.observableArrayList(controller.CarregaTipo()));
    }
    public void limparLabel() {
        txDias.setText("");
        txQuant.setText("");
        txValor.setText("");
        cbCondPgto.resetValidation();
        cbTipo.resetValidation();
       
    }
    private void estadoEdicao() {
        limparLabel();
        pnconteudo.setDisable(false);
        cbCondPgto.setDisable(false);
        btFinalizar.setDisable(false);
        btCancelar.setDisable(false);
        btConsultar.setDisable(false);
        btNovo.setDisable(false);
        dtEmissao.setValue(LocalDate.now());
        dtDtvenc.setValue(LocalDate.now());
        btExcluir.setDisable(true);
        dtEmissao.requestFocus();
        tabela.setDisable(false);

    }
    @FXML
    private void Novo(ActionEvent event) {
        estadoEdicao();
        
    }

    @FXML
    private void Cosnultar(ActionEvent event) throws IOException {
         Parent inicial = FXMLLoader.load(getClass().getResource("DespesasConsulta.fxml"));
        Scene scene = new Scene(inicial);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BEM-VINDO: A BUSCA DE DESPESAS");
       
        stage.showAndWait();
         try {
            ObservableList<ContasPagar> contr;
            listaParcrlad.clear();
            listaParcrlad.add(desp);
            contr = FXCollections.observableArrayList(listaParcrlad);
            tabela.setItems(contr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
    }

    @FXML
    private void Cancelar(ActionEvent event) {
         btCancelar.getScene().getWindow().hide();//fecha a janela
    }

    @FXML
    private void Excluir(ActionEvent event) {
      Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
//        if (a.showAndWait().get() == ButtonType.OK) {
//            if (!contaspagar.verificaPagamento(contaspagar)) {
//                if (crllancardes.excluir(contaspagar)) {
//                    msg.Affirmation("Excluido com sucesso", "Despesa excluida!");
//                    contaspagar = null;
//                    estadoInicial();
//                } else {
//                    msg.Error("Erro ao excluir!", "Problemas ao excluir");
//
//                }
//            } else {
//                msg.Error("Erro ao excluir!", "Há parcelas pagas!");
//            }
//        }
        
    }

    @FXML
    private void Finalizar(ActionEvent event) {
        int erro = 0, cod = 0;
        try {
            cod = Integer.parseInt(txcodigo.getText());
        } catch (NumberFormatException e) {
            cod = 0;
        }
        if (listaParcrlad.isEmpty()) {
            erro++;
        }
        if (erro == 0) {
            if (cod == 0) {
                if (controller.gravar(listaParcrlad)) {
                     msg.Affirmation("Apollo Informa:", "Gravação feita com sucesso");
                } else {
                   msg.Error("Erro ", "Gravação não realizada");
                        
                       
                }
            } else {
                if (controller.apagar(tabela)) {
                    if (controller.gravar(listaParcrlad)) {
                         msg.Affirmation("Apollo Informa:", "Alteração feita com sucesso");
                    } else {
                        msg.Error("Erro ", "Alteração não realizada");
                    }
                }
            }
        }

    }
     private void atualizarTabela() {
        try {
            ObservableList<ContasPagar> contr;
            contr = FXCollections.observableArrayList(listaParcrlad);
            tabela.setItems(contr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private void Gerar(ActionEvent event) {
        Double total=0.0;
        int dias=0,quant=0,cod=0;
        NumberFormat nf = new DecimalFormat("#,###.00");
        try {
            cod=Integer.parseInt(txcodigo.getText());
        } catch (NumberFormatException e) {
            cod=0;
        }
        
        try {
             total = nf.parse(txValor.getText()).doubleValue();
             dias =Integer.parseInt(txDias.getText());
             quant =Integer.parseInt(txQuant.getText());
             
             
            listaParcrlad.clear();
            listaParcrlad=controller.gerarParcelas(cbCondPgto, cbTipo,total, dtEmissao, dtDtvenc,dias , quant, cod, func);
             atualizarTabela();
        } catch (ParseException ex) {
            Logger.getLogger(TelaLancarDespesasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
   

}
