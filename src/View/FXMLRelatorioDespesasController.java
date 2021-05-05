/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Ajuda.Ajuda;
import CamadaAcessoDados.Banco;
import Controller.ContasPagarController;
import Controller.FornecedorController;
import Controller.VendaController;
import Models.Cliente;
import Models.Fornecedor;
import Models.TipoDespesa;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author paulo
 */
public class FXMLRelatorioDespesasController implements Initializable {

    @FXML
    private JFXButton btConsultar;
    @FXML
    private JFXButton btLimpar;
    @FXML
    private JFXComboBox<TipoDespesa> cbFiltro;//
    @FXML
    private DatePicker dtInicial;
    @FXML
    private DatePicker dtFinal;
    @FXML
    private JFXCheckBox cbAtiperiodo;

    ContasPagarController controllerCP = new ContasPagarController();
    @FXML
    private JFXButton btAjuda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CarregaCategoria();
    }

    public void CarregaCategoria() {

        cbFiltro.setItems(FXCollections.observableArrayList(controllerCP.CarregaTipo()));
//
    }

    @FXML
    private void Consultar(ActionEvent event) {
        String dtinicial = "";
        String dtfinal = "";
        String sql = "select tp.descricao,cr.parcela,cr.valor,cr.valor_pago,cr.emissao,cr.valor-cr.valor_pago as valor_restante "
                + "from contas_pagar cr,tipo_despesa tp where cr.flag_despesa<>0";
        if (dtInicial.getValue() != null && dtFinal.getValue() != null) {
            dtinicial = dtInicial.getValue().toString();
            dtfinal = dtFinal.getValue().toString();
            sql += " and cr.emissao BETWEEN '" + dtinicial + "' and '" + dtfinal + "' ";

        }
        if (cbFiltro.getSelectionModel().getSelectedItem() != null) {

            sql += " and tp.codigo=" + cbFiltro.getSelectionModel().getSelectedItem().getCodigo();
        }

        sql += "group by cr.emissao,tp.descricao,cr.parcela,cr.valor,cr.valor_pago,valor_restante order by cr.emissao";
        if (gerarRelatorio(sql, "src/relatorios/RelDespesas.jasper", "Lista de Despesas") == false) {
            gerarRelatorio(sql, "relatorios/RelDespesas.jasper", "Lista de Despesas");
        }
    }

    public boolean gerarRelatorio(String sql, String relat, String titulotela) {
        try {  //sql para obter os dados para o relatorio
            ResultSet rs = Banco.getCon().consultar(sql);
            //implementação da interface JRDataSource para DataSource ResultSet
            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
            //preenchendo e chamando o relatório

            String jasperPrint = JasperFillManager.fillReportToFile(relat, null, jrRS);
            JasperViewer viewer = new JasperViewer(jasperPrint, false, false);

            viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);//maximizado
            viewer.setTitle(titulotela);
            viewer.setVisible(true);
            return true;
        } catch (JRException erro) {
            System.out.println(erro);
            return false;
        }
    }

    @FXML
    private void LimparFiltros(ActionEvent event) {
        cbFiltro.getSelectionModel().clearSelection();
        dtFinal.setValue(null);
        dtInicial.setValue(null);
    }

    @FXML
    private void AtivarPeriodo(ActionEvent event) {
        if (cbAtiperiodo.isSelected()) {
            dtInicial.setDisable(false);
            dtFinal.setDisable(false);
            dtInicial.setValue(LocalDate.now());
            dtFinal.setValue(LocalDate.now());
        } else {
            dtInicial.setDisable(true);
            dtFinal.setDisable(true);
        }
    }

    @FXML
    private void Ajuda(ActionEvent event) {
        Ajuda a = new Ajuda();
        a.Ajuda("RelatoriodeDespesas.htm");
    }

}
