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
import Models.Fornecedor;
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
public class FXMLRelatorioComprasController implements Initializable {

    @FXML
    private JFXButton btConsultar;
    FornecedorController FornecedorController = new FornecedorController();
    ContasPagarController controllerCP = new ContasPagarController();
    @FXML
    private JFXButton btLimpar;
    @FXML
    private JFXComboBox<Fornecedor> cbFiltro;//
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

        cbFiltro.setItems(FXCollections.observableArrayList(FornecedorController.carregaFornecedor("")));
//
    }

    @FXML
    private void Consultar(ActionEvent event) {
        String dtinicial = "";
        String dtfinal = "";
        String sql = "select e.codigo,e.emissao,e.total,f.nomefantasia,pr.descricao,i.qtde,i.total as "
                + "prodtot from entrada_de_produtos e,fornecedor f,item_compra i,produto pr\n"
                + "where f.codigo = e.codigo_fornecedor and i.codigo_compra= e.codigo and i.codigo_produto= pr.codigo";
         if (dtInicial.getValue() != null && dtFinal.getValue() != null) {
            dtinicial = dtInicial.getValue().toString();
            dtfinal = dtFinal.getValue().toString();
             sql += " and e.emissao BETWEEN '" + dtinicial + "' and '" + dtfinal + "' ";
            
        }
        if (cbFiltro.getSelectionModel().getSelectedItem() != null) {
            sql +=" and f.codigo="+cbFiltro.getSelectionModel().getSelectedItem().getCodigo();              
        }
        sql += " group by e.codigo,e.emissao,e.total,f.nomefantasia,pr.descricao,i.qtde,i.total order by e.emissao desc";
        if (gerarRelatorio(sql, "src/relatorios/RelCompra.jasper", "Lista de Produtos") == false) {
            gerarRelatorio(sql, "relatorios/RelCompra.jasper", "Lista de Produtos");
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
        a.Ajuda("RelatoriodeCompras.htm");
    }

}
