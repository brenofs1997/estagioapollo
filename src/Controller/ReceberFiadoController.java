/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.ContasReceber;
import com.jfoenix.controls.JFXButton;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 *
 * @author paulo
 */
public class ReceberFiadoController {

    NumberFormat nf = new DecimalFormat("#,###.00");
    DecimalFormat formato = new DecimalFormat("#.##");

    public void consultar(int cliente, String dtinicial, String dtfinal, TableView<ContasReceber> tabelaRec, String status) {
        ContasReceber cr = new ContasReceber();
        List<ContasReceber> ListaFiado = cr.getFiado(cliente, dtinicial, dtfinal, status);//Lista de fiado
        tabelaRec.setItems(FXCollections.observableArrayList(ListaFiado));
    }

    public void pesquisaRadio(String filtro, TableView<ContasReceber> tabelaRec) {
        ContasReceber cr = new ContasReceber();
        List<ContasReceber> ListaFiado = cr.get(filtro);//Lista de fiado
        tabelaRec.setItems(FXCollections.observableArrayList(ListaFiado));
    }

    public boolean baixar(ContasReceber conta, Double valor, DatePicker dtBaixa) {
        ContasReceber contaA = new ContasReceber();
        ContasReceber contaB = new ContasReceber();
        contaA = contaA.get(conta.getCodigo());

        if (valor < contaA.getValor()) {
            contaA.setValor_pago(contaA.getValor_pago() + valor);
            contaA.setData_pago(java.sql.Date.valueOf(dtBaixa.getValue()));
            contaA.setStatus("PR");
            int numparc = Integer.parseInt(contaA.getParcela().charAt(0) + "") + 1;

            contaB = new ContasReceber(0, "" + numparc + "/" + numparc, contaA.getValor() - contaA.getValor_pago(), 0.0,
                    contaA.getEmissaoDate(), null, null,
                    contaA.getFuncionario(), contaA.getCliente(), contaA.getCond_pgto(),
                    "P", conta.getVenda(), 0.0);
            if (contaA.alterar(contaA) && contaB.gravar(contaB)) {
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

    public boolean veriEstorna(ContasReceber conta) {
        int cont = 0;
        List<ContasReceber> lista = new ArrayList();
        lista = conta.getVeri("where cod_venda=" + conta.getVenda().getCodigo() + "");
        cont = lista.size();
        if (lista.get(cont - 1).getCodigo() == conta.getCodigo()) {
            return true;
        }

        return false;

    }

    public boolean estornar(ContasReceber conta, JFXButton btEstornar) {
        int cont = 0, numparc = 0;

        List<ContasReceber> lista = new ArrayList();
        lista = conta.getVeri("where cod_venda=" + conta.getVenda().getCodigo() + "");
        cont = lista.size();
        ContasReceber contaA = new ContasReceber();
        ContasReceber contaB = new ContasReceber();
        contaA = contaA.get(conta.getCodigo());

        if (cont > 1) {
            btEstornar.setDisable(false);
            numparc = Integer.parseInt(conta.getParcela().charAt(0) + "") - 1;

            contaB = conta.getVeri("where cod_venda=" + conta.getVenda().getCodigo() + "AND parcela='" + numparc + "/" + numparc + "'").get(0);
            contaB.setValor_pago(0.0);
            contaB.setData_pago(null);
            contaB.setStatus("P");
            if (contaA.alterar(contaB) && contaA.apagarParcVenda(contaA.getCodigo())) {
                return true;
            }

        } else {
            contaA.setValor_pago(0.0);
            contaA.setData_pago(null);
            contaA.setStatus("A");
            if (contaA.alterar(contaA)) {
                return true;
            }
        }

        return false;
    }

    public boolean estornarR(ContasReceber conta, JFXButton btEstornar) {
        ContasReceber contaA = new ContasReceber();
        contaA = contaA.get(conta.getCodigo());
        contaA.setValor_pago(0.0);
        contaA.setData_pago(null);
        contaA.setStatus("A");

        if (contaA.alterar(contaA)) {
            return true;
        }
        return false;
    }

    public void consultarR(int cliente, String dtinicial, String dtfinal, TableView<ContasReceber> tabelaRec, String status) {
        ContasReceber cr = new ContasReceber();
        List<ContasReceber> ListaFiado = cr.getNaoFiado(cliente, dtinicial, dtfinal, status);//Lista de vendas
        tabelaRec.setItems(FXCollections.observableArrayList(ListaFiado));
    }

    public void pesquisaRadioR(String filtro, TableView<ContasReceber> tabelaRec) {
        ContasReceber cr = new ContasReceber();
        List<ContasReceber> ListaFiado = cr.getNF(filtro);//Lista de vendas
        tabelaRec.setItems(FXCollections.observableArrayList(ListaFiado));
    }

    public boolean baixarR(ContasReceber conta) {
        ContasReceber contaA = new ContasReceber();
        contaA = contaA.get(conta.getCodigo());
        contaA.setValor_pago(conta.getValor());
        contaA.setData_pago(java.sql.Date.valueOf(LocalDate.now()));
        contaA.setStatus("Q");

        if (contaA.alterar(contaA)) {
            return true;
        }
        return false;
    }

}
