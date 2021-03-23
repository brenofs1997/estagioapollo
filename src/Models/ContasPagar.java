/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import CamadaAcessoDados.DALContasPagar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author paulo
 */
public class ContasPagar {

    private int codigo;
    private int flag_despesa;
    private String parcela;
    private double valor;
    private double valor_pago;
    private Date emissao;
    private Date data_pago;
    private Date vencimento;
    private Funcionario funcionario;
    private CondicaoPagamento cond_pgto;
    private TipoDespesa tipo_despesa;
    private int qtde_parcelas;
    private int dias_entreparc;
    private String status;
    private Fornecedor fornecedor;
    private Compra compra;
    DALContasPagar dal = new DALContasPagar();

    public ContasPagar() {
    }

    public ContasPagar(int codigo, int flag_despesa, String parcela, double valor, double valor_pago, Date emissao, Date data_pago, Date vencimento, Funcionario funcionario, CondicaoPagamento cond_pgto, TipoDespesa tipo_despesa, int qtde_parcelas, int dias_entreparc, String status, Fornecedor fornecedor, Compra compra) {
        this.codigo = codigo;
        this.flag_despesa = flag_despesa;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.funcionario = funcionario;
        this.cond_pgto = cond_pgto;
        this.tipo_despesa = tipo_despesa;
        this.qtde_parcelas = qtde_parcelas;
        this.dias_entreparc = dias_entreparc;
        this.status = status;
        this.fornecedor = fornecedor;
        this.compra = compra;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getFlag_despesa() {
        return flag_despesa;
    }

    public void setFlag_despesa(int flag_despesa) {
        this.flag_despesa = flag_despesa;
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

    public Date getData_pago() {
        return data_pago;
    }

    public void setData_pago(Date data_pago) {
        this.data_pago = data_pago;
    }

    public String getVencimento() throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(vencimento);

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

    public CondicaoPagamento getCond_pgto() {
        return cond_pgto;
    }

    public void setCond_pgto(CondicaoPagamento cond_pgto) {
        this.cond_pgto = cond_pgto;
    }

    public TipoDespesa getTipo_despesa() {
        return tipo_despesa;
    }

    public void setTipo_despesa(TipoDespesa tipo_despesa) {
        this.tipo_despesa = tipo_despesa;
    }

    public int getQtde_parcelas() {
        return qtde_parcelas;
    }

    public void setQtde_parcelas(int qtde_parcelas) {
        this.qtde_parcelas = qtde_parcelas;
    }

    public int getDias_entreparc() {
        return dias_entreparc;
    }

    public void setDias_entreparc(int dias_entreparc) {
        this.dias_entreparc = dias_entreparc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public List<ContasPagar> get(String filtro) {
        return dal.get(filtro);
    }

    public ContasPagar get(int codigo) {
        return dal.get(codigo);
    }

    public ContasPagar getC(int codCompra) {
        return dal.getC(codCompra);
    }

    public boolean apagar(int cod) {
        return dal.apagar(cod);
    }
     public boolean apagarParcCompra(int cod) {
        return dal.apagarParcCompra(cod);
    }
    

    public boolean alterar(ContasPagar c) {

        return dal.salvar(c);
    }

    public boolean salvar(ContasPagar cp) {
        return dal.salvar(cp);

    }

    public boolean verificarParcelaPaga(ContasPagar contaspagar) {

        DALContasPagar conta = new DALContasPagar();
        List<ContasPagar> aux = new ArrayList();
        aux = getParcDespesas(contaspagar.getFlag_despesa());
        return conta.verificaPagamento(aux);
    }

    public List<ContasPagar> getParcDespesas(int codigo) {
        return dal.getParcDespesas(codigo);
    }

    public boolean apagar(ContasPagar desp, List<ContasPagar> listaParcela) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<ContasPagar> getParcCompras(int codigo) {
         return dal.getParcCompras(codigo);
    }

}
