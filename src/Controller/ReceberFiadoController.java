/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.ContasReceber;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
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
    
    public void consultar(int cliente, String dtinicial, String dtfinal, TableView<ContasReceber> tabelaRec) {
        ContasReceber cr = new ContasReceber();
        List<ContasReceber> ListaFiado = cr.getFiado(cliente, dtinicial, dtfinal);//Lista de fiado
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
            int numparc= Integer.parseInt(contaA.getParcela().charAt(0)+"")+1;
       
            
            contaB = new ContasReceber(0,""+numparc+"/"+numparc, contaA.getValor()-contaA.getValor_pago(), 0.0,
                    contaA.getData_pagoDate(), null, null,
                    contaA.getFuncionario(), contaA.getCliente(),contaA.getCond_pgto(),
                    "P", contaA.getVenda(),0.0);
            if (contaA.alterar(contaA)&&contaB.gravar(contaB)) {
                return true;
            }
        } else {
            contaA.setValor_pago(contaA.getValor_pago() + valor);
            contaA.setData_pago(java.sql.Date.valueOf(dtBaixa.getValue()));
            contaA.setStatus("Q");
            if (contaA.alterar(contaA) ) {
                
                return true;
            }
        }
        return false;
    }
    
    public boolean estornar(ContasReceber conta) {
        ContasReceber contaA = new ContasReceber();
        contaA = contaA.get(conta.getCodigo());
        
        contaA.setValor_pago(0.0);
        contaA.setData_pago(null);
        contaA.setStatus("E");
        if (contaA.alterar(contaA)) {
            return true;
        }
        return false;
    }
    
    public void calcTotal(TableView<ContasReceber> tabelaRec, Label lbTotal) {
        List<ContasReceber> ListaFiado = tabelaRec.getItems();
        Double total = 0.0;
        for (ContasReceber contasReceber : ListaFiado) {
            if(contasReceber.getStatus().equals("A")||contasReceber.getStatus().equals("Q"))
             total += contasReceber.getValor();
        }
        
        lbTotal.setText(nf.format(total));     
    }
}
