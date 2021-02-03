/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import CamadaAcessoDados.Banco;
import Erros.Erros;
import Models.Compra;
import Models.CondicaoPagamento;
import Models.ContasPagar;
import Models.Fornecedor;
import Models.Funcionario;
import Models.Produto;
import Models.itens_Compra;
import View.FXMLTelaCompraController;
import static View.FXMLTelaCompraController.itensCompra;
import static View.FXMLTelaCompraController.listaParcela;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import apollo.utils.converteDataPicker;
/**
 *
 * @author Paulo
 */
public class CompraController {
    Erros msg = new Erros();
      NumberFormat nf = new DecimalFormat("#,###.00");
    public List<Fornecedor> CarregaFornecedor() {
        Fornecedor f = new Fornecedor();
        List<Fornecedor> Lista = f.get("");//Lista de Tipos de despesa
        return Lista;
    }

    public void atualizarTabela(TableView<itens_Compra> tabela, List<itens_Compra> Produtos) {
        ObservableList<itens_Compra> prod_v = null;
        if (tabela.getItems() != null) {
            tabela.getItems().removeAll();
        }
        prod_v = FXCollections.observableArrayList(Produtos);
        tabela.setItems(prod_v);
    }
     public void atualizarTabelaParcelas(TableView<ContasPagar> tabela, List<ContasPagar> Parcelas) {
        ObservableList<ContasPagar> parc_v = null;
        if (tabela.getItems() != null) {
            tabela.getItems().removeAll();
        }
        parc_v = FXCollections.observableArrayList(Parcelas);
        tabela.setItems(parc_v);
    }

    public List<Produto> CarregarProduto(int codFornecedor) {
        Produto p = new Produto();
        List<Produto> Lista = p.getCategoriasFornecedor(codFornecedor);//Lista de Tipos de despesa
        return Lista;
    }

    public int procLista(List<itens_Compra> lista, int codProdt) {
        int index = -1;
        for (itens_Compra item : lista) {
            if (item.getProduto().getCodigo() == codProdt) {
                index = lista.indexOf(item);
                return index;
            }

        }
        return index;
    }

    public void addProduto(Produto prod, TableView<itens_Compra> tabProd, int qtde, Double uni, Label lbTotal) {
        int index = 0;
        int qtdeAlter = 0;
        Double total = 0.0, totalProdutos = 0.0;
        NumberFormat nf = new DecimalFormat("#,###.00");
        DecimalFormat formato = new DecimalFormat("#.##");
        FXMLTelaCompraController.setItensCompra(tabProd.getItems());

        if (!tabProd.getItems().isEmpty()) {

            index = procLista(tabProd.getItems(), prod.getCodigo());
            if (index >= 0) {
                qtdeAlter = FXMLTelaCompraController.getItensCompra().get(index).getQuantidade() + qtde;
                FXMLTelaCompraController.getItensCompra().get(index).setQuantidade(qtdeAlter);
                total = uni * qtdeAlter;
                try {
                    total = nf.parse(formato.format(total)).doubleValue();
                } catch (ParseException ex) {
                    Logger.getLogger(CompraController.class.getName()).log(Level.SEVERE, null, ex);
                }
                FXMLTelaCompraController.getItensCompra().get(index).setTotal(total);
                FXMLTelaCompraController.getItensCompra().get(index).setUnitario(uni);

            } else {
                total = uni * qtde;
                try {
                    total = nf.parse(formato.format(total)).doubleValue();
                } catch (ParseException ex) {
                    Logger.getLogger(CompraController.class.getName()).log(Level.SEVERE, null, ex);
                }
                tabProd.getItems().add(new itens_Compra(qtde, uni, total, new Compra(), prod));
            }
        } else {
            total = uni * qtde;
            try {
                total = nf.parse(formato.format(total)).doubleValue();
            } catch (ParseException ex) {
                Logger.getLogger(CompraController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tabProd.getItems().add(new itens_Compra(qtde, uni, total, new Compra(), prod));
        }
        for (itens_Compra Produto : FXMLTelaCompraController.getItensCompra()) {
            totalProdutos += Produto.getTotal();
        }

        atualizarTabela(tabProd, FXMLTelaCompraController.getItensCompra());
        lbTotal.setText(nf.format(totalProdutos));
    }

    public boolean gravar(List<ContasPagar> listaParcela, List<itens_Compra> itensCompra, Funcionario func, int cod, JFXComboBox<Fornecedor> cbFornecedor, Date emissao, JFXComboBox<CondicaoPagamento> cbCond, Double total) {
        boolean aux = false;
        ContasPagar cp = new ContasPagar();
        Compra c = new Compra(cod, emissao, cbFornecedor.getSelectionModel().getSelectedItem(), cbCond.getSelectionModel().getSelectedItem(), total);

        if (c.salvar(c) && c.salvarItens(itensCompra)) {

            int codCompra = 0;
            codCompra = Banco.getCon().getMaxPK("entrada_de_produtos", "codigo");
            for (ContasPagar parc : listaParcela) {
                parc.setFornecedor(cbFornecedor.getSelectionModel().getSelectedItem());
                parc.setCompra(new Compra(codCompra));
                if (parc.salvar(parc)) {
                    aux = true;
                }
            }

            return true;
        }
        return false;
    }

    public void carregarConsulta(String Filtro, TableView<Compra> tabela) {
         Compra tip = new Compra();
        List<Compra> res = tip.get(Filtro);
        ObservableList<Compra> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }

    public void carregaCampos(Pane pnconteudo, Compra compra, JFXComboBox<CondicaoPagamento> cbCondPgto, JFXComboBox<Fornecedor> cbFornecedor, DatePicker dtemissao, DatePicker dtvenc, JFXTextField txDias, JFXTextField txQuantParc, JFXTextField txcodigo, JFXButton btFinalizar, JFXButton btExcluir, JFXButton btNovo, TableView<itens_Compra> tabelaProd, TableView<ContasPagar> tabelaParcelas) {
         if (compra.verificarParcelaPaga(compra)) {
            btFinalizar.setDisable(true);
            btExcluir.setDisable(true);
            msg.Error("Apollo Informa", "Essa compra possui parcelas pagas!");
        } else {
            NumberFormat nf = new DecimalFormat("#,###.00");
            ContasPagar cp = new ContasPagar();
            cp= cp.getC(compra.getCodigo());
            converteDataPicker cv = new converteDataPicker();
            cv.converter(compra.getEmissaoDate(), dtemissao);
            cv.converter(cp.getVencimentoDate(), dtvenc);
            if(!compra.getCond_pgto().getDescricao().equalsIgnoreCase("DINHEIRO") || !compra.getCond_pgto().getDescricao().equalsIgnoreCase("débito"))
            {
                 txDias.setText(String.valueOf(cp.getDias_entreparc()));
                txQuantParc.setText(String.valueOf(cp.getQtde_parcelas()));
            
            }
           
            txcodigo.setText(String.valueOf(compra.getCodigo()));
            
            cbFornecedor.getSelectionModel().select(0);
            cbFornecedor.getSelectionModel().select(compra.getFornecedor());
            cbCondPgto.getSelectionModel().select(0);
            cbCondPgto.getSelectionModel().select(compra.getCond_pgto());
             
            itensCompra.clear();
            itensCompra.addAll(compra.getProdutos(compra.getCodigo()));
            atualizarTabela(tabelaProd,itensCompra);
            listaParcela.clear();
            listaParcela.addAll(cp.getParcCompras(compra.getCodigo()));
            atualizarTabelaParcelas(tabelaParcelas,listaParcela);
            btNovo.setDisable(true);
            btFinalizar.setDisable(false);
            btExcluir.setDisable(false);
            pnconteudo.setDisable(false);
        }
    }

}
