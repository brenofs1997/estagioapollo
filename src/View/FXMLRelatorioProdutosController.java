/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Ajuda.Ajuda;
import CamadaAcessoDados.Banco;
import Controller.ContasPagarController;
import Models.Categoria;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author paulo
 */
public class FXMLRelatorioProdutosController implements Initializable {

    @FXML
    private JFXComboBox<Categoria> cbCategoria;
    @FXML
    private JFXButton btConsultar;

    ContasPagarController controllerCP = new ContasPagarController();
    @FXML
    private JFXButton btLimpar;
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

        cbCategoria.setItems(FXCollections.observableArrayList(controllerCP.CarregaCategoria()));
//
    }

    @FXML
    private void Consultar(ActionEvent event) {
        String sql = "SELECT p.codigo,p.descricao,p.qtde,c.descricao as categoria,p.ativo "
                + "FROM produto p, categoria c"
                + " where p.cod_categoria = c.codigo "
                + " GROUP BY categoria,p.codigo,p.descricao,p.qtde,p.ativo";

        if (cbCategoria.getSelectionModel().getSelectedItem() != null) {
            sql = "SELECT p.codigo,p.descricao,p.qtde,c.descricao as categoria,p.ativo "
                    + "FROM produto p, categoria c"
                    + " where p.cod_categoria = c.codigo  and c.codigo  = " + cbCategoria.getSelectionModel().getSelectedItem().getCodigo() + " "
                    + " GROUP BY categoria,p.codigo,p.descricao,p.qtde,p.ativo";
        }

        if (gerarRelatorio(sql, "src/relatorios/RelProduto.jasper", "Lista de Produtos") == false) {
            gerarRelatorio(sql, "relatorios/RelProduto.jasper", "Lista de Produtos");
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
        cbCategoria.getSelectionModel().clearSelection();
    }

    @FXML
    private void Ajuda(ActionEvent event) {
        Ajuda a = new Ajuda();
        a.Ajuda("RelatoriodeProdutos.htm");
    }

}
