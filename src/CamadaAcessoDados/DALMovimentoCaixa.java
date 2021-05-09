/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamadaAcessoDados;

import Models.MovimentoCaixa;


public class DALMovimentoCaixa {
    public boolean salvar(MovimentoCaixa v) {

        String sql = "insert into movimento_caixa(motivo,valor,cod_caixa,cod_funcionario,emissao,emissao_horario,tipo) "
                + "values('#A','#B',#C,#D,'#E','#F','#G')";
        sql = sql.replace("#A", "" + v.getMotivo());
        sql = sql.replace("#B", "" + v.getValor());
        if (v.getCaixa() != null) {
            sql = sql.replace("#C", "" + v.getCaixa().getId());
        } else {
            sql = sql.replace("#C", "null");
        }
        if (v.getFuncionario() != null) {
            sql = sql.replace("#D", "" + v.getFuncionario().getCodigo());
        } else {
            sql = sql.replace("#D", "null");
        }
        sql = sql.replace("#E", "" + v.getEmissao());
        sql = sql.replace("#F",  v.getEmissao_horario());
        sql = sql.replace("#G",  v.getTipo());
        return Banco.getCon().manipular(sql);
    }
    
}
