/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Ajuda.Ajuda;
import CamadaAcessoDados.Banco;
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
public class FXMLRelatorioVendaController implements Initializable {

    @FXML
    private JFXButton btConsultar;
    @FXML
    private JFXButton btLimpar;

    VendaController controller = new VendaController();
    @FXML
    private JFXComboBox<Cliente> cbCliente;
    @FXML
    private JFXButton btAjuda;
    @FXML
    private DatePicker dtInicial;
    @FXML
    private DatePicker dtFinal;
    @FXML
    private JFXCheckBox cbAtiperiodo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CarregarCliente();
    }

    public void CarregarCliente() {

        cbCliente.setItems(FXCollections.observableArrayList(controller.CarregaCliente()));
//setItems
    }

    @FXML
    private void Consultar(ActionEvent event) {
        String sql = "SELECT v.codigo,"
                + "	v.emissao,"
                + "	v.total,"
                + "	c.nome,"
                + "	p.descricao,"
                + "	itv.qtde,"
                + "	itv.total as tot_prod"
                + " FROM venda v,"
                + "	cliente c,"
                + "	produto p,"
                + "	item_venda itv"
                + " WHERE "
                + "	 v.cod_cliente = c.codigo "
                + "	 AND itv.codigo_venda = v.codigo "
                + "	 AND itv.codigo_produto = p.codigo ";
               
        String dtinicial = "";
        String dtfinal = "";
        
         if (dtInicial.getValue() != null && dtFinal.getValue() != null) {
            dtinicial = dtInicial.getValue().toString();
            dtfinal = dtFinal.getValue().toString();
            sql += " and v.emissao BETWEEN '" + dtinicial + "' and '" + dtfinal + "' ";

        }

        if (cbCliente.getSelectionModel().getSelectedItem() != null) {
            sql +=" and v.cod_cliente = " + cbCliente.getSelectionModel().getSelectedItem().getCodigo()+" ";
                 
                   
        }
        
        sql+=  " GROUP BY v.emissao,"
                + "	v.codigo,"
                + "	v.total,"
                + "	c.nome,"
                + "	p.descricao,"
                + "	itv.qtde,"
                + "	itv.total"
                + " ORDER BY v.emissao ASC";

        if (gerarRelatorio(sql, "src/relatorios/RelVendas.jasper", "Lista de Vendas") == false) {
            gerarRelatorio(sql, "relatorios/RelVendas.jasper", "Lista de Vendas");
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
        cbCliente.getSelectionModel().clearSelection();
        dtFinal.setValue(null);
        dtInicial.setValue(null);
    }

    @FXML
    private void Ajuda(ActionEvent event) {
        Ajuda a = new Ajuda();
        a.Ajuda("RelatoriodeVendas.htm");
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

}
