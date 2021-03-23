package Models;

import CamadaAcessoDados.Banco;
import CamadaAcessoDados.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Caixa {

    private int Id;
    private double Saldo;
    private String Status;
    private boolean aberto;
    private double valor;

    public Caixa() {
    }

    public Caixa(int id, double valor) {
        this.setId(id);
        this.setValor(valor);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public double getSaldo() {
        return Saldo;
    }

    public void setSaldo(double saldo) {
        Saldo = saldo;
    }

    public String isStatus() {
        Conexao conexao = new Conexao();
        Conexao.getInstancia();
        try {
            ResultSet res = conexao.consultar("select status from caixa");
            if (res.next()) {
                if (res.getString(1).equals("A")) {
                    return "Caixa Aberto.";
                } else {
                    return "Caixa Fechado.";
                }
            } else {
                return "Erro";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            conexao.close();
            return "Erro";
        }
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public Double getSaldoInicio() {

        try {
            ResultSet res = Conexao.getInstancia().consultar("select saldo from caixa");
            if (res.next()) {
                return res.getDouble(1);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Conexao.getInstancia().close();
            return null;
        }
    }

    public boolean isAberto() {
        try {
            ResultSet res = Conexao.getInstancia().consultar("select status from caixa");
            if (res.next()) {
                if (res.getString(1).toLowerCase().equals("a")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Conexao.getInstancia().close();
            return false;
        }
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean atualizarSaldo(String tipo) {
        String sql = "";
        if (tipo.equals("E")) {
            sql = "update caixa set saldo = saldo + " + valor + " where id = " + this.getId();
        } else {
            sql = "update caixa set saldo = saldo - " + valor + " where id = " + this.getId();
        }

        return Conexao.getInstancia().manipular(sql);
    }

    public boolean atualizarStatus(String tipo) {
        String sql = "";

        int cod = Conexao.getInstancia().getMaxPK("caixa", "id");
        sql = "update caixa set status = " + "'" + tipo + "'" + " where id = " + cod;
        return Conexao.getInstancia().manipular(sql);
    }

    public boolean verificaCaixa() {
        Conexao conexao = new Conexao();
        Conexao.getInstancia();
        try {
            ResultSet res = conexao.consultar("select * from caixa");
            if (res.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return false;
    }

    public Caixa getCaixa() {
        Conexao conexao = new Conexao();
        Conexao.getInstancia();
        try {
            ResultSet res = conexao.consultar("select * from caixa");
            if (res.next()) {
                return new Caixa(res.getInt("id"), res.getDouble("saldo"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return null;
    }

    public boolean iniciaCaixa() {
        String sql = "insert into caixa(saldo,status) "
                + "values('#A','#B')";
        sql = sql.replace("#A", "0");
        sql = sql.replace("#B", "F");

        return Banco.getCon().manipular(sql);
    }

}
