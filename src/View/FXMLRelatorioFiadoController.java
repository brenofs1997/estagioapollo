/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Ajuda.Ajuda;
import CamadaAcessoDados.Banco;
import Controller.ContasPagarController;
import Controller.VendaController;
import Models.Cliente;
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
public class FXMLRelatorioFiadoController implements Initializable {

    @FXML
    private JFXButton btConsultar;
    VendaController controllerV = new VendaController();
    ContasPagarController controllerCP = new ContasPagarController();
    @FXML
    private JFXButton btLimpar;
    @FXML
    private JFXComboBox<Cliente> cbFiltro;//
    @FXML
    private DatePicker dtInicial;
    @FXML
    private DatePicker dtFinal;
    @FXML
    private JFXCheckBox cbAtiperiodo;
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

        cbFiltro.setItems(FXCollections.observableArrayList(controllerV.PesquisarCliente("")));
//
    }

    @FXML
    private void Consultar(ActionEvent event) {
        String dtinicial = "";
        String dtfinal = "";
        String sql = "select ct.nome,cr.parcela,cr.valor,cr.valor_pago,cr.emissao,cr.valor-cr.valor_pago as valor_restante"
                + " from contas_receber cr,venda v,condicao_pgto cp,cliente ct "
                + " where cr.cod_venda = v.codigo "
                + " and ct.codigo=cr.cod_cliente "
                + " and v.cod_condpagto =cp.codigo "
                + " and cp.descricao like '%Fiado%'";
        if (dtInicial.getValue() != null && dtFinal.getValue() != null) {
            dtinicial = dtInicial.getValue().toString();
            dtfinal = dtFinal.getValue().toString();
            sql += " and cr.emissao BETWEEN '" + dtinicial + "' and '" + dtfinal + "' ";

        }
        if (cbFiltro.getSelectionModel().getSelectedItem() != null) {
            sql += " and ct.codigo=" + cbFiltro.getSelectionModel().getSelectedItem().getCodigo();
        }
        sql += " group by cr.emissao,ct.nome,cr.parcela,cr.valor,cr.valor_pago,valor_restante order by cr.emissao";
        if (gerarRelatorio(sql, "src/relatorios/RelFiado.jasper", "Lista de Fiados") == false) {
            gerarRelatorio(sql, "relatorios/RelFiado.jasper", "Lista de Fiados");
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
        a.Ajuda("RelatoriodeVendasFiado.htm");
    }

}
