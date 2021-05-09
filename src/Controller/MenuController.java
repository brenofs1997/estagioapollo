/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Erros.Erros;
import Models.Caixa;
import Models.Funcionario;
import Models.MovimentoCaixa;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.util.Date;
import java.util.List;
import javafx.scene.control.Label;

/**
 *
 * @author paulo
 */
public class MenuController {

    Erros msg = new Erros();

    public void verificaRestricoes(Funcionario funcionario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Funcionario> CarregaFuncionario() {
        Funcionario f = new Funcionario();
        List<Funcionario> Lista = f.get("");//Lista de Funcionario
        return Lista;
    }

    public boolean atualizarCaixa(Double valor, String motivo, Funcionario funcionario, Date emissao, String emissao_horario, String tipo) {
        Caixa c = new Caixa();
        c = c.getCaixa();
        c.setValor(valor);
        MovimentoCaixa mov = new MovimentoCaixa(motivo, valor, c, funcionario, emissao, emissao_horario, tipo);

        if (c.atualizarSaldo(tipo) && c.atualizarStatus("A") && mov.salvar(mov)) {
            msg.Affirmation("Apollo Informa: ", "Caixa aberto e saldo atualizado!");
            return true;
        } else {
            msg.Error("Apollo Informa: ", "Erro ao alterar!");
            return false;
        }

    }

    public boolean abrirCaixa(String senha) {
        Caixa c = new Caixa();
        c = c.getCaixa();
        Funcionario f = new Funcionario();
        f = f.getporSenha(senha);
        String tipo = "";
        if (c.isAberto()) {
            tipo = "F";
        } else {
            tipo = "A";
        }
        if (f != null && c.atualizarStatus(tipo)) {
            c = c.getCaixa();
            msg.Confirmation("Apollo informa", "" + c.isStatus());
            return true;
        } else {

            return false;
        }
    }

    public void atualizaTextoCaixa(Label lbcaixa, FontAwesomeIcon iconcaixa) {
        Caixa c = new Caixa();
        c = c.getCaixa();
        if (c.isAberto()) {
            lbcaixa.setText(c.isStatus());
            iconcaixa.setIconName("UNLOCK_ALT");

        } else {
            lbcaixa.setText(c.isStatus());
            iconcaixa.setIconName("LOCK");
        }
    }

}
