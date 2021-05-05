/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import CamadaAcessoDados.DALContasReceber;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ContasReceber {

    private int codigo;

    private String parcela;
    private double valor;
    private double valor_pago;
    private Date emissao;
    private Date data_pago;
    private Date vencimento;
    private Funcionario funcionario;
    private Cliente cliente;
    private CondicaoPagamento cond_pgto;
    private String status;
    private Venda venda;
    private double valor_restante;
    DALContasReceber dal = new DALContasReceber();

    public ContasReceber() {
    }

    public ContasReceber(int codigo) {
        this.codigo = codigo;
    }

    public ContasReceber(int codigo, String parcela, double valor, double valor_pago, Date emissao, Date data_pago, Date vencimento, Funcionario funcionario, Cliente cliente, CondicaoPagamento cond_pgto, String status, Venda venda) {
        this.codigo = codigo;

        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.cond_pgto = cond_pgto;
        this.status = status;
        this.venda = venda;
    }

    public ContasReceber(int codigo, Venda venda, String parcela, double valor, double valor_pago, Date emissao, Date data_pago, Date vencimento, Funcionario funcionario, Cliente cliente, CondicaoPagamento cond_pgto, String status) {
        this.codigo = codigo;
        this.venda = venda;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.cond_pgto = cond_pgto;
        this.status = status;
    }

    public ContasReceber(Venda venda, String parcela, double valor, double valor_pago, Date emissao, Date data_pago, Date vencimento, Funcionario funcionario, Cliente cliente, CondicaoPagamento cond_pgto, String status) {

        this.venda = venda;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.cond_pgto = cond_pgto;
        this.status = status;

    }

    public ContasReceber(int codigo, String parcela, double valor, double valor_pago, Date emissao, Date data_pago, Date vencimento, Funcionario funcionario, Cliente cliente, CondicaoPagamento cond_pgto, String status, Venda venda, double valor_restante) {
        this.codigo = codigo;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.cond_pgto = cond_pgto;
        this.status = status;
        this.venda = venda;
        this.valor_restante = valor_restante;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getValor_pago() {
        return valor_pago;
    }

    public void setValor_pago(double valor_pago) {
        this.valor_pago = valor_pago;
    }

    public String getEmissao() throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(emissao);

    }

    public Date getEmissaoDate() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public String getData_pago() throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        if (data_pago == null) {
            return "";
        } else {
            return formato.format(data_pago);
        }

    }

    public Date getData_pagoDate() {
        return data_pago;
    }

    public void setData_pago(Date data_pago) {
        this.data_pago = data_pago;
    }

    public String getVencimento() throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        if (vencimento == null) {
            return "";
        } else {
            return formato.format(vencimento);
        }
    }

    public Date getVencimentoDate() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public CondicaoPagamento getCond_pgto() {
        return cond_pgto;
    }

    public void setCond_pgto(CondicaoPagamento cond_pgto) {
        this.cond_pgto = cond_pgto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public double getValor_restante() {
        return valor_restante;
    }

    public void setValor_restante(double valor_restante) {
        this.valor_restante = valor_restante;
    }

    public boolean gravar(ContasReceber c) {

        return dal.salvar(c);
    }

    public boolean apagar(int cod) {
        return dal.apagar(cod);//passar codigo da venda
    }

    public boolean apagarParcVenda(int codigo) {
        return dal.apagarParcVenda(codigo);
    }

    public ContasReceber get(int codigo) {
        return dal.get(codigo);
    }

    public List<ContasReceber> get(String filtro) {
        return dal.get(filtro);
    }

    public List<ContasReceber> getNF(String filtro) {
        return dal.getNF(filtro);
    }

    public List<ContasReceber> getVeri(String filtro) {
        return dal.getVeri(filtro);
    }

    public List<ContasReceber> getFiado(int cliente, String dtinicial, String dtfinal, String status) {
        return dal.getFiado(cliente, dtinicial, dtfinal, status);
    }

    public boolean alterar(ContasReceber conta) {
        return dal.alterar(conta);
    }

    public List<ContasReceber> getNaoFiado(int cliente, String dtinicial, String dtfinal, String status) {
        return dal.getNaoFiado(cliente, dtinicial, dtfinal, status);
    }

}
