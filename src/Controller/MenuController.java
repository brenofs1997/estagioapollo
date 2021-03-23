/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Erros.Erros;
import Models.Caixa;
import Models.Funcionario;

/**
 *
 * @author paulo
 */
public class MenuController {

    Erros msg = new Erros();

    public void verificaRestricoes(Funcionario funcionario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean atualizarCaixa(Double valor) {
        Caixa c = new Caixa();
        c = c.getCaixa();
        c.setValor(valor);
        if (c.atualizarSaldo("E") && c.atualizarStatus("A")) {
            msg.Affirmation("Apollo Informa: ", "Caixa aberto e saldo atualizado!");
            return true;
        } else {
            msg.Error("Apollo Informa: ", "Erro ao alterar!");
            return false;
        }

    }

}
