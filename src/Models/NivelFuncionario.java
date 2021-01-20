package Models;

import CamadaAcessoDados.DALNivelFuncionario;
import java.util.List;

public class NivelFuncionario {

    private int codigo;
    private String descricao;
    private Restricoes restricoes;

    public NivelFuncionario() {
    }

    public NivelFuncionario(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public NivelFuncionario(int codigo, String descricao, Restricoes restricoes) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.restricoes = restricoes;
    }

    public NivelFuncionario(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public Restricoes getRestricoes() {
        return restricoes;
    }

    public void setRestricoes(Restricoes restricoes) {
        this.restricoes = restricoes;
    }

    public boolean salvar(NivelFuncionario nf) {
        DALNivelFuncionario dal = new DALNivelFuncionario();
        return dal.salvar(nf);
    }

    public boolean alterar(NivelFuncionario nf) {
        DALNivelFuncionario dal = new DALNivelFuncionario();
        return dal.alterar(nf);
    }

    public boolean apagar(int cod) {
        DALNivelFuncionario dal = new DALNivelFuncionario();
        return dal.apagar(cod);
    }

    public List<NivelFuncionario> get(String filtro) {
        DALNivelFuncionario dal = new DALNivelFuncionario();
        return dal.get(filtro);
    }

    public NivelFuncionario get(int filtro) {
        DALNivelFuncionario dal = new DALNivelFuncionario();
        return dal.get(filtro);
    }

    public NivelFuncionario getA(String filtro) {
        DALNivelFuncionario dal = new DALNivelFuncionario();
        return dal.getA(filtro);
    }

}
