/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import CamadaAcessoDados.DALMovimentoCaixa;
import java.util.Date;


public class MovimentoCaixa {
    private int codigo;
    private String motivo;
    private double valor;
    private Caixa caixa;
    private Funcionario funcionario;
    private Date emissao;   
    private String emissao_horario;
    private String tipo;
    DALMovimentoCaixa dal= new DALMovimentoCaixa();
    public MovimentoCaixa() {
    }

    public MovimentoCaixa(int codigo, String motivo, double valor, Caixa caixa, Funcionario funcionario, Date emissao, String emissao_horario,String tipo) {
        this.codigo = codigo;
        this.motivo = motivo;
        this.valor = valor;
        this.caixa = caixa;
        this.funcionario = funcionario;
        this.emissao = emissao;
        this.emissao_horario = emissao_horario;
        this.tipo=tipo;
    }

    public MovimentoCaixa(String motivo, double valor, Caixa caixa, Funcionario funcionario, Date emissao, String emissao_horario,String tipo) {
        this.motivo = motivo;
        this.valor = valor;
        this.caixa = caixa;
        this.funcionario = funcionario;
        this.emissao = emissao;
        this.emissao_horario = emissao_horario;
        this.tipo=tipo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public String getEmissao_horario() {
        return emissao_horario;
    }

    public void setEmissao_horario(String emissao_horario) {
        this.emissao_horario = emissao_horario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
      public boolean salvar(MovimentoCaixa v) {
        return dal.salvar(v);

    }
    
    
}
