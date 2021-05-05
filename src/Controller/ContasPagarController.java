/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import CamadaAcessoDados.Banco;
import Erros.Erros;
import Models.Categoria;
import Models.Compra;
import Models.CondicaoPagamento;
import Models.ContasPagar;
import Models.Fornecedor;
import Models.Funcionario;
import Models.TipoDespesa;
import static View.TelaLancarDespesasController.listaParcela;
import apollo.utils.converteDataPicker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

/**
 *
 * @author paulo
 */
public class ContasPagarController {

    Erros msg = new Erros();
    NumberFormat nf = new DecimalFormat("#,###.00");

    public void carregaTabela(String Filtro, TableView<ContasPagar> tabela) {

        ContasPagar tip = new ContasPagar();
        List<ContasPagar> res = tip.get(Filtro);
        ObservableList<ContasPagar> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);

    }

    public void atualizarReceber(TableView<ContasPagar> tabelaReceber) {
        try {
            tabelaReceber.setVisible(true);
            ObservableList<ContasPagar> contr;
            contr = FXCollections.observableArrayList(listaParcela);
            tabelaReceber.setItems(contr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<CondicaoPagamento> CarregaCondPgto() {
        CondicaoPagamento cp = new CondicaoPagamento();
        List<CondicaoPagamento> Lista = cp.get("");//Lista de CondPgto
        return Lista;
    }

    public List<Categoria> CarregaCategoria() {
        Categoria cp = new Categoria();
        List<Categoria> Lista = cp.get("");//Lista de Categoria
        return Lista;
    }

    public List<TipoDespesa> CarregaTipo() {
        TipoDespesa cp = new TipoDespesa();
        List<TipoDespesa> Lista = cp.get("");//Lista de Tipos de despesa
        return Lista;
    }

    public boolean gravar(List<ContasPagar> cp) {
        boolean aux = false;
        int flag_cod = 1, rows = 0;
        //Gravando as parcelas 
        try {
            Banco.conectar();
            ResultSet rs = Banco.getCon().consultar("select from contas_pagar");
            while (rs.next()) {
                rows = rs.getRow();
            }
            if (rows > 0) {
                flag_cod = Banco.getCon().getMaxPK("contas_pagar", "codigo") + 1;
            }
        } catch (Exception e) {
        }
        for (ContasPagar contasPagar : cp) {
            contasPagar.setFlag_despesa(flag_cod);
            aux = contasPagar.salvar(contasPagar);
        }
        return aux;
    }

    public boolean apagar(TableView<ContasPagar> tabela) {
        ContasPagar aux = new ContasPagar();

        aux = aux.get(tabela.getItems().get(0).getCodigo());
        return aux.apagar(aux.getFlag_despesa());
    }

    public void clkPesquisar(String filtro, TableView<ContasPagar> tabela) {
        carregaTabela(filtro, tabela);
    }

    public void carregarCategoria(JFXComboBox<Categoria> cbCategoria) {
        Categoria cat = new Categoria();
        List<Categoria> lista = cat.get("");
        cbCategoria.setItems(FXCollections.observableArrayList(lista));
        cbCategoria.getSelectionModel().select(0);
    }

    public boolean alterar(List<ContasPagar> cp) {
        boolean aux = false;
        for (ContasPagar contasPagar : cp) {
            aux = contasPagar.salvar(contasPagar);
        }
        return aux;
    }

    public void carregaCampos(Pane pnconteudo, ContasPagar desp, JFXComboBox<CondicaoPagamento> cbCondPgto, JFXComboBox<TipoDespesa> cbTipo,
            JFXTextField txValor, DatePicker dpemissao, DatePicker dtVenc, JFXTextField txDias, JFXTextField txQuant,
            JFXTextField txcodigo, JFXButton btFinalizar, JFXButton btExcluir, JFXButton btNovo, TableView<ContasPagar> tabela) {

        if (desp.verificarParcelaPaga(desp)) {
            btFinalizar.setDisable(true);
            btExcluir.setDisable(true);
            msg.Error("Apollo Informa", "Essa despesa possui parcelas pagas!");
        } else {
            NumberFormat nf = new DecimalFormat("#,###.00");
            converteDataPicker cv = new converteDataPicker();
            cv.converter(desp.getEmissaoDate(), dpemissao);
            cv.converter(desp.getVencimentoDate(), dtVenc);
            txValor.setText(nf.format(desp.getValor()));

            txDias.setText(String.valueOf(desp.getDias_entreparc()));
            txQuant.setText(String.valueOf(desp.getQtde_parcelas()));
            txcodigo.setText(String.valueOf(desp.getCodigo()));

            cbTipo.getSelectionModel().select(0);
            cbTipo.getSelectionModel().select(desp.getTipo_despesa());
            cbCondPgto.getSelectionModel().select(0);
            cbCondPgto.getSelectionModel().select(desp.getCond_pgto());

            listaParcela.clear();
            listaParcela.addAll(desp.getParcDespesas(desp.getFlag_despesa()));
            atualizarReceber(tabela);
            btNovo.setDisable(true);
            btFinalizar.setDisable(false);
            btExcluir.setDisable(false);
            pnconteudo.setDisable(false);
        }
    }

    public List<ContasPagar> gerarParcelas(JFXComboBox<CondicaoPagamento> cbCondPgto,
            JFXComboBox<TipoDespesa> cbTipo, Double total, DatePicker dpemissao,
            DatePicker dpvencimento, int diasentreparc, int qtde, int cod,
            Funcionario func) throws ParseException {

        List<ContasPagar> rec = new ArrayList();
        CondicaoPagamento condpagto = cbCondPgto.getSelectionModel().getSelectedItem();
        TipoDespesa tipo = new TipoDespesa();
        if (cbTipo != null) {
            tipo = cbTipo.getSelectionModel().getSelectedItem();
        }

        ContasPagar parcela;
        Double valor = 0.0, valorpago = 0.0, parcelaResto = 0.0;
        LocalDate emissao = dpemissao.getValue();

        Calendar hoje = Calendar.getInstance();
//        if (dtprazo != null) {
//            hoje.set(dtprazo.getYear(), dtprazo.getMonthValue() - 1, dtprazo.getDayOfMonth());
//        }

        Date vencimento = java.sql.Date.valueOf(dpvencimento.getValue());

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date resultVencimento = formato.parse(formato.format(vencimento));
        String num_boletoaux = "";
        String status = "P";
        Date datapago = new Date();
        datapago = null;
        valor = total / qtde;
        valorpago = 0.0;
        Double valorfinal = 0.00;
        Double valor_pen = 0.00;
        NumberFormat nf = new DecimalFormat("#,###.00");

        if (condpagto.getDescricao().toLowerCase().equals("dinheiro")) {
            valorpago = valor;
            datapago = java.sql.Date.valueOf(emissao);
            resultVencimento = formato.parse(formato.format(vencimento));
            status = "Q";
        } else if (condpagto.getDescricao().toLowerCase().equals("cheque") || condpagto.getDescricao().toLowerCase().equals("boleto")) {
            resultVencimento = formato.parse(formato.format(vencimento));
            valor_pen = valor;

        } else {
            hoje.setTime(vencimento);
//            if (!txdiasentreparc.getText().isEmpty()) {
//                hoje.add(hoje.DAY_OF_MONTH, Integer.parseInt(txdiasentreparc.getText()));
//            }
            resultVencimento = hoje.getTime();
        }
        for (int i = 0; i < qtde; i++) {

            if (i == 0) { // primeira parcela é diferente
                valor = Double.parseDouble(String.format("%.2f", valor).replaceAll(",", "."));
                if (qtde > 1) {
                    valor_pen = valor;
                }

                parcela = new ContasPagar(cod, 0, String.valueOf(i + 1), valor, valorpago, java.sql.Date.valueOf(emissao), datapago, resultVencimento, func, condpagto, tipo, qtde, diasentreparc, status, new Fornecedor(), new Compra());

            } else {
                hoje.add(hoje.DAY_OF_MONTH, diasentreparc);
                resultVencimento = hoje.getTime();
                valor = Double.parseDouble(String.format("%.2f", valor).replaceAll(",", "."));
                if (i == qtde - 1 && (valor * qtde) < total) {
                    parcelaResto = total - valor * qtde;
                    parcelaResto = Double.parseDouble(nf.format(parcelaResto).replaceAll(",", "."));
                    valor += parcelaResto;
                    valor = Double.parseDouble(nf.format(valor).replace(",", "."));
                } else if (i == qtde - 1 && (valor * qtde) > total) {
                    parcelaResto = valor * qtde - total;
                    parcelaResto = Double.parseDouble(nf.format(parcelaResto).replaceAll(",", "."));
                    valor -= parcelaResto;
                    valor = Double.parseDouble(nf.format(valor).replaceAll(",", "."));
                }
                parcela = new ContasPagar(cod, 0, String.valueOf(i + 1), valor, valorpago,
                        java.sql.Date.valueOf(emissao), datapago, resultVencimento,
                        func, condpagto, tipo, qtde, diasentreparc, status,
                        new Fornecedor(), new Compra());

            }
            rec.add(parcela);
        }

        return rec;
    }

    public boolean excluir(ContasPagar desp) {
        if (desp != null) {
            if (desp.apagar(desp.getFlag_despesa())) {
                return true;
            }
        }
        return false;
    }

    public boolean baixar(ContasPagar conta, Double valor, DatePicker dtBaixa) {
        ContasPagar contaA = new ContasPagar();
        ContasPagar contaB = new ContasPagar();
        contaA = contaA.get(conta.getCodigo());

        if (valor < contaA.getValor()) {
            contaA.setValor_pago(contaA.getValor_pago() + valor);
            contaA.setData_pago(java.sql.Date.valueOf(dtBaixa.getValue()));
            contaA.setStatus("PR");
            int numparc = Integer.parseInt(contaA.getParcela().charAt(0) + "") + 1;

            contaB = new ContasPagar(0, contaA.getFlag_despesa(), "" + numparc, contaA.getValor() - contaA.getValor_pago(), 0.0,
                    contaA.getEmissaoDate(), null, contaA.getVencimentoDate(),
                    contaA.getFuncionario(), contaA.getCond_pgto(), contaA.getTipo_despesa(),
                    contaA.getQtde_parcelas(), contaA.getDias_entreparc(), "P", contaA.getFornecedor(), contaA.getCompra(), conta.getFlag_parcial());

            if (contaA.alterar(contaA) && contaB.salvar(contaB)) {
                return true;
            }
        } else {
            contaA.setValor_pago(contaA.getValor_pago() + valor);
            contaA.setData_pago(java.sql.Date.valueOf(dtBaixa.getValue()));
            contaA.setStatus("Q");
            if (contaA.alterar(contaA)) {

                return true;
            }
        }
        return false;
    }

    public boolean estornar(ContasPagar conta, JFXButton btEstornar) {
        int cont = 0, numparc = 0;

        List<ContasPagar> lista = new ArrayList();

        ContasPagar contaA = new ContasPagar();
        ContasPagar contaB = new ContasPagar();
        contaA = contaA.get(conta.getCodigo());
        if (conta.getCompra() != null) {
            lista = conta.get("flag_parcial=" + conta.getFlag_parcial() + "");
            cont = lista.size();
            if (cont > 1) {
                btEstornar.setDisable(false);
                numparc = Integer.parseInt(conta.getParcela().charAt(0) + "") - 1;

                contaB = conta.get("cod_compra=" + conta.getCompra().getCodigo() + " AND parcela='" + numparc + "'").get(0);
                contaB.setValor_pago(0.0);
                contaB.setData_pago(null);
                contaB.setStatus("P");
                if (contaA.alterar(contaB) && contaA.apagar(contaA.getCodigo())) {
                    return true;
                }

            } else {
                contaA.setValor_pago(0.0);
                contaA.setData_pago(null);
                contaA.setStatus("P");
                if (contaA.alterar(contaA)) {
                    return true;
                }
            }

        } else {
            lista = conta.get("flag_parcial=" + conta.getFlag_parcial() + "");
            cont = lista.size();
            if (cont > 1) {
                btEstornar.setDisable(false);
                numparc = Integer.parseInt(conta.getParcela().charAt(0) + "") - 1;

                contaB = conta.get("flag_despesa=" + conta.getFlag_despesa() + "AND parcela='" + numparc + "'").get(0);
                contaB.setValor_pago(0.0);
                contaB.setData_pago(null);
                contaB.setStatus("P");
                if (contaA.alterar(contaB) && contaA.apagar(contaA.getCodigo())) {
                    return true;
                }

            } else {
                contaA.setValor_pago(0.0);
                contaA.setData_pago(null);
                contaA.setStatus("P");
                if (contaA.alterar(contaA)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void pesquisaRadio(String string, TableView<ContasPagar> tabelaRec) {
        ContasPagar cp = new ContasPagar();
        List<ContasPagar> ListaFiado = cp.getContasPagar(0, 0, "", "", string);//Lista de fiado
        tabelaRec.setItems(FXCollections.observableArrayList(ListaFiado));
    }

    public void consultar(int flag_desp, int fornecedor, String dtinicial, String dtfinal, TableView<ContasPagar> tabelaRec, String status) {
        ContasPagar cr = new ContasPagar();
        List<ContasPagar> ListaContas = cr.getContasPagar(flag_desp, fornecedor, dtinicial, dtfinal, status);
        tabelaRec.setItems(FXCollections.observableArrayList(ListaContas));
    }

    public boolean veriEstorna(ContasPagar conta) {
        List<ContasPagar> lista = new ArrayList();
        lista = conta.get("flag_parcial=" + conta.getFlag_parcial() + "");
        int cont = 0;
        cont = lista.size();
        if (lista.get(cont-1).getCodigo() == conta.getCodigo()) {
            return true;
        }
        return false;
    }
}
