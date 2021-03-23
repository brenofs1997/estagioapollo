/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import CamadaAcessoDados.Banco;
import Erros.Erros;
import Models.Cliente;
import Models.CondicaoPagamento;
import Models.ContasReceber;
import Models.Funcionario;
import Models.Produto;
import Models.Venda;
import Models.itens_Venda;
import View.FXMLTelaVendasProdutoController;
import static View.FXMLTelaVendasProdutoController.itensVenda;
import apollo.utils.converteDataPicker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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

/**
 *
 * @author paulo
 */
public class VendaController {

    Erros msg = new Erros();
    NumberFormat nf = new DecimalFormat("#,###.00");

    public List<Cliente> CarregaCliente() {
        Cliente c = new Cliente();
        List<Cliente> Lista = c.get("");//Lista de Tipos de despesa
        return Lista;
    }

    public void atualizarTabela(TableView<itens_Venda> tabela, List<itens_Venda> Produtos) {
        ObservableList<itens_Venda> prod_v = null;
        if (tabela.getItems() != null) {
            tabela.getItems().removeAll();
        }
        prod_v = FXCollections.observableArrayList(Produtos);
        tabela.setItems(prod_v);
    }

    public List<Produto> CarregarProduto(int cod) {

        Produto p = new Produto();
        List<Produto> Lista = null;
        if (cod == 0) {
            Lista = p.get("");
        } else {
            Lista = p.getCategoria(cod);
        }

        return Lista;
    }

    public int procLista(List<itens_Venda> lista, int codProdt) {
        int index = -1;
        for (itens_Venda item : lista) {
            if (item.getProduto().getCodigo() == codProdt) {
                index = lista.indexOf(item);
                return index;
            }

        }
        return index;
    }

    public double calcTotal() {
        Double totalProdutos = 0.0;
        for (itens_Venda Produto : FXMLTelaVendasProdutoController.getItensVenda()) {
            totalProdutos += Produto.getTotal();
        }

        return totalProdutos;
    }

    public List<itens_Venda> addProduto(Produto prod, TableView<itens_Venda> tabProd, int qtde, Double uni, Label lbTotal) {
        int index = 0;
        int qtdeAlter = 0;
        Double total = 0.0;
        NumberFormat nf = new DecimalFormat("#,###.00");
        DecimalFormat formato = new DecimalFormat("#.##");
        FXMLTelaVendasProdutoController.setItensCompra(tabProd.getItems());

        if (!tabProd.getItems().isEmpty()) {

            index = procLista(tabProd.getItems(), prod.getCodigo());
            if (index >= 0) {
                qtdeAlter = FXMLTelaVendasProdutoController.getItensVenda().get(index).getQuantidade() + qtde;
                FXMLTelaVendasProdutoController.getItensVenda().get(index).setQuantidade(qtdeAlter);
                total = uni * qtdeAlter;
                try {
                    total = nf.parse(formato.format(total)).doubleValue();
                } catch (ParseException ex) {
                    Logger.getLogger(VendaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                FXMLTelaVendasProdutoController.getItensVenda().get(index).setTotal(total);
                FXMLTelaVendasProdutoController.getItensVenda().get(index).setUnitario(uni);

            } else {
                total = uni * qtde;
                try {
                    total = nf.parse(formato.format(total)).doubleValue();
                } catch (ParseException ex) {
                    Logger.getLogger(VendaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                FXMLTelaVendasProdutoController.getItensVenda().add(new itens_Venda(qtde, uni, total, new Venda(), prod));
            }
        } else {
            total = uni * qtde;
            try {
                total = nf.parse(formato.format(total)).doubleValue();
            } catch (ParseException ex) {
                Logger.getLogger(VendaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            FXMLTelaVendasProdutoController.getItensVenda().add(new itens_Venda(qtde, uni, total, new Venda(), prod));
        }

        lbTotal.setText(nf.format(calcTotal()));

        return FXMLTelaVendasProdutoController.getItensVenda();
    }

    public boolean gravar(List<itens_Venda> itensVenda, Funcionario func, int cod, JFXComboBox<Cliente> cbCliente, Date emissao, double total, boolean cond) {
        boolean aux = false;
        ContasReceber cp = new ContasReceber();
        CondicaoPagamento cd = new CondicaoPagamento();
        Cliente c = new Cliente();
        if (cond == false) {
            cd = cd.getC("Dinheiro");
            cp = new ContasReceber(cod, "1/1", total, total, emissao, emissao, emissao, func, cbCliente.getSelectionModel().getSelectedItem(), cd, "Q");
            if (cbCliente.getSelectionModel().getSelectedItem() == null) {
                c = c.get(1);
            }
        } else {
            cd = cd.getC("Fiado");
            cp = new ContasReceber(cod, "1/1", total, 0.0, emissao, null, null, func, cbCliente.getSelectionModel().getSelectedItem(), cd, "A");
            c = cbCliente.getSelectionModel().getSelectedItem();
        }
        Venda v = new Venda(cod, emissao, c, cd, total);

        if (v.salvar(v) && v.salvarItens(itensVenda) && cp.gravar(cp)) {

            return true;
        }
        return false;
    }

    public boolean excluirAlt(Venda venda) {
        itens_Venda it = new itens_Venda();
        ContasReceber cp = new ContasReceber();
        if (venda != null) {

            if (cp.apagarParcVenda(venda.getCodigo()) && it.apagar(venda.getCodigo())) {
                return true;
            }
        }
        return false;
    }

    public boolean alterar(List<itens_Venda> itensVenda, Funcionario func, int cod, JFXComboBox<Cliente> cbCliente, Date emissao, double total, boolean cond) {
        boolean aux = false;
        ContasReceber cp = new ContasReceber();
        CondicaoPagamento cd = new CondicaoPagamento();
        Cliente c = new Cliente();
        if (cond == false) {
            cd = cd.getC("Dinheiro");
            cp = new ContasReceber(cod, "1/1", total, total, emissao, emissao, emissao, func, cbCliente.getSelectionModel().getSelectedItem(), cd, "Q");
            c = c.get(1);

        } else {
            cd = cd.getC("Fiado");
            cp = new ContasReceber(cod, "1/1", total, 0.0, emissao, null, null, func, cbCliente.getSelectionModel().getSelectedItem(), cd, "A");
            c = cbCliente.getSelectionModel().getSelectedItem();
        }
        Venda v = new Venda(cod, emissao, c, cd, total);

        if (v.alterar(v) && v.salvarItens(itensVenda) && cp.gravar(cp)) {

            return true;
        }
        return false;
    }

    public void carregarConsulta(String Filtro, TableView<Venda> tabela) {
        Venda v = new Venda();
        List<Venda> res = v.get(Filtro);
        ObservableList<Venda> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }

    public void carregaCampos(Pane pnconteudo, Venda venda, JFXComboBox<CondicaoPagamento> cbCondPgto, JFXComboBox<Cliente> cbCliente, DatePicker dtEmissao, JFXTextField txcodigo, JFXButton btFinalizar, JFXButton btExcluir, JFXButton btNovo, JFXCheckBox chkFiado, TableView<itens_Venda> tabelaProd, Label lbTotal, JFXButton btRetirarProd) {
        if (venda.verificarParcelaPaga(venda)) {
            btFinalizar.setDisable(true);
            btExcluir.setDisable(true);
            msg.Error("Apollo Informa", "Essa venda possui parcelas pagas!");
        } else {
            NumberFormat nf = new DecimalFormat("#,###.00");
            converteDataPicker cv = new converteDataPicker();
            cv.converter(venda.getEmissaoDate(), dtEmissao);
            txcodigo.setText(String.valueOf(venda.getCodigo()));

            CondicaoPagamento cdp = new CondicaoPagamento();
            cdp = cdp.get(venda.getCond_pgto().getCodigo());
            chkFiado.setSelected(true);

            if (!cdp.getDescricao().equals("Fiado")) {
                chkFiado.setSelected(false);
            }
            cbCliente.getSelectionModel().select(0);
            cbCliente.getSelectionModel().select(venda.getCliente());
            itensVenda.clear();
            itensVenda.addAll(venda.getProdutos(venda.getCodigo()));
            atualizarTabela(tabelaProd, itensVenda);

            lbTotal.setText(nf.format(calcTotal()));
            btNovo.setDisable(true);
            btFinalizar.setDisable(false);
            btExcluir.setDisable(false);
            pnconteudo.setDisable(false);
            btRetirarProd.setDisable(false);
        }
    }

    public boolean excluir(Venda venda) {
        itens_Venda it = new itens_Venda();
        ContasReceber cp = new ContasReceber();
        if (venda != null) {

            if (cp.apagarParcVenda(venda.getCodigo()) &&  it.apagar(venda.getCodigo()) && venda.apagar(venda.getCodigo())) {
                return true;
            }
        }
        return false;
    }

    public List<Cliente> PesquisarCliente(String valor) {
        Cliente c = new Cliente();
        List<Cliente> Lista = c.get("c.nome ILIKE '%"+valor+"%'");//Lista de Tipos de despesa
        return Lista;
    }
    public List<Produto> PesquisarProduto(String valor) {
        Produto p = new Produto();
        List<Produto> Lista = p.get("descricao ILIKE '%"+valor+"%'");//Lista de Tipos de despesa
        return Lista;
    }

}
