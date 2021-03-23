/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import CamadaAcessoDados.DALEstado;
import java.util.List;

public class Estado {

    private int cod;
    private String nome, sigla;
    DALEstado dal = new DALEstado();

    public Estado(int cod) {
        this.cod = cod;
    }

    public Estado(int cod, String nome, String sigla) {
        this.cod = cod;
        this.sigla = sigla;
        this.nome = nome;
    }

    public Estado(String nome, String sigla) {
        cod = 0;
        this.sigla = sigla;
        this.nome = nome;
    }

    public Estado() {
        this(0, "", "");
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Estado> get(String filtro) {
        return dal.get(filtro);
    }

    @Override
    public String toString() {
        return sigla;
    }

    public Estado getPorCid(int cid_cod) {
        return dal.getPorCid(cid_cod);
    }

}
