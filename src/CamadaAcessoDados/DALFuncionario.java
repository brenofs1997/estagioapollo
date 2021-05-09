package CamadaAcessoDados;

import Models.Cidade;
import Models.Funcionario;
import Models.NivelFuncionario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DALFuncionario {

    public Funcionario getLogin(String usuario, String senha) {
        String SQL = "select codigo,nome,login,senha,cod_nivel,ativo"
                + " from funcionario"
                + " where login = '" + usuario.trim() + "'"
                + " and senha = '" + senha.trim() + "'";
        NivelFuncionario nf = new NivelFuncionario();
        try (Connection conn = Conexao.open()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(SQL)) {
                    if (rs.next()) {
                        nf = nf.get(rs.getInt("cod_nivel"));
                        return new Funcionario(rs.getInt("codigo"),
                                rs.getString("login"),
                                rs.getString("senha"),
                                rs.getString("ativo"),
                                nf);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALFuncionario.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean salvar(Funcionario funcionario) {
        String SQL = "insert into funcionario (nome,cpf,endereco,numero,telefone,"
                + "email,login,senha,ativo,primeiro_acesso,cod_cidade,cod_nivel,"
                + "bairro,cep) values ('#A','#B','#C','#D','#E','#F',"
                + "'#G','#H','#I','#J',#K,#L,'#M','#N')";

        SQL = SQL.replace("#A", funcionario.getNome());
        SQL = SQL.replace("#B", funcionario.getCpf());
        SQL = SQL.replace("#C", funcionario.getEndereco());
        SQL = SQL.replace("#D", funcionario.getNumero());
        SQL = SQL.replace("#E", funcionario.getTelefone());
        SQL = SQL.replace("#F", funcionario.getEmail());
        SQL = SQL.replace("#G", funcionario.getLogin());
        SQL = SQL.replace("#H", funcionario.getSenha());
        SQL = SQL.replace("#I", funcionario.getAtivo());
        SQL = SQL.replace("#J", funcionario.getPrimeiro_acesso());
        SQL = SQL.replace("#K", "" + funcionario.getCidade().getCid_cod());
        SQL = SQL.replace("#L", "" + funcionario.getNivel().getCodigo());
        SQL = SQL.replace("#M", funcionario.getBairro());
        SQL = SQL.replace("#N", funcionario.getCep());

        return Banco.getCon().manipular(SQL);
    }

    public int getCodigoFuncionario() {
        return Banco.getCon().getMaxPK("funcionario", "codigo");
    }

    public boolean alterar(Funcionario funcionario) {
        String SQL = "update funcionario set nome = '#A',cpf = '#B',endereco = '#C',"
                + "numero = '#D',telefone = '#E',email = '#F',login = '#G',"
                + "senha = '#H',ativo = '#I',primeiro_acesso = '#J',cod_cidade = #K,"
                + "cod_nivel = #L,bairro = '#M',cep = '#N' where codigo = " + funcionario.getCodigo();

        SQL = SQL.replace("#A", funcionario.getNome());
        SQL = SQL.replace("#B", funcionario.getCpf());
        SQL = SQL.replace("#C", funcionario.getEndereco());
        SQL = SQL.replace("#D", funcionario.getNumero());
        SQL = SQL.replace("#E", funcionario.getTelefone());
        SQL = SQL.replace("#F", "" + funcionario.getEmail());
        SQL = SQL.replace("#G", funcionario.getLogin());
        SQL = SQL.replace("#H", funcionario.getSenha());
        SQL = SQL.replace("#I", funcionario.getAtivo());
        SQL = SQL.replace("#J", funcionario.getPrimeiro_acesso());
        SQL = SQL.replace("#K", "" + funcionario.getCidade().getCid_cod());
        SQL = SQL.replace("#L", "" + funcionario.getNivel().getCodigo());
        SQL = SQL.replace("#M", funcionario.getBairro());
        SQL = SQL.replace("#N", funcionario.getCep());

        return Banco.getCon().manipular(SQL);
    }

    public boolean apagar(int codigo) {
        return Banco.getCon().manipular("delete from funcionario where codigo = " + codigo);
    }

    public List<Funcionario> get(String filtro) {
        List<Funcionario> func = new ArrayList();
        String SQL = "select f.codigo,f.nome,f.cpf, f.cod_nivel,nf.descricao nivel,case when f.ativo = 'ativo' then 'ativo' when f.ativo = 'não ativo'"
                + " then 'não ativo' end ativo from funcionario f"
                + " inner join nivel_funcionario nf on nf.codigo = f.cod_nivel";

        if (!filtro.isEmpty()) {
            SQL = SQL + " where " + filtro;
        }

        SQL = SQL + " order by f.nome ";

        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            while (rs.next()) {
                func.add(new Funcionario(rs.getInt("codigo"), rs.getString("nome"), rs.getString("cpf"),
                        rs.getString("ativo"), new NivelFuncionario().get(rs.getInt("cod_nivel"))));
            }
        } catch (SQLException e) {
        }

        return func;
    }

    public Funcionario get(int codigo) {
        String SQL = "select * from funcionario where codigo = " + codigo;
        DALNivelFuncionario nf = new DALNivelFuncionario();

        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {

                return new Funcionario(rs.getInt("codigo"), rs.getString("nome"), rs.getString("cpf"),
                        rs.getString("endereco"), rs.getString("numero"), rs.getString("telefone"),
                        rs.getString("email"), rs.getString("login"), rs.getString("senha"),
                        rs.getString("ativo"), rs.getString("primeiro_acesso"), new Cidade(rs.getInt("cod_cidade")),
                        new NivelFuncionario(rs.getInt("cod_nivel")),
                        rs.getString("bairro"), rs.getString("cep"));

            }
        } catch (SQLException e) {
        }
        return null;
    }

    public Funcionario getLogin(String login) {
        String sql = "select codigo from funcionario where login = '" + login + "'";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return new Funcionario(rs.getInt("codigo"));
            }
        } catch (SQLException e) {
        }
        return null;

    }

    public List<Funcionario> getDiferenteFunc(int cod) {
        List<Funcionario> func = new ArrayList();
        String SQL = "select * from funcionario where codigo <> " + cod;
        SQL = SQL + " order by nome ";

        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            while (rs.next()) {
                func.add(new Funcionario(rs.getInt("codigo"), rs.getString("nome"),
                        rs.getString("cpf"), rs.getString("ativo"), new NivelFuncionario(rs.getInt("cod_nivel"))));
            }
        } catch (SQLException e) {
        }

        return func;
    }

    public List<Funcionario> getFuncPorNivel(int cod) {
        List<Funcionario> func = new ArrayList();
        String SQL = "select * from funcionario where cod_nivel = " + cod;
        SQL = SQL + " order by nome ";

        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            while (rs.next()) {
                func.add(new Funcionario(rs.getInt("codigo"), rs.getString("nome"),
                        rs.getString("cpf"), rs.getString("ativo"), new NivelFuncionario(rs.getInt("cod_nivel"))));
            }
        } catch (SQLException e) {
        }

        return func;
    }

    public Funcionario getporSenha(String senha) {
        String sql = "select *  from funcionario where senha = '" + senha + "'";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return new Funcionario(rs.getInt("codigo"), rs.getString("nome"), rs.getString("cpf"),
                        rs.getString("endereco"), rs.getString("numero"), rs.getString("telefone"),
                        rs.getString("email"), rs.getString("login"), rs.getString("senha"),
                        rs.getString("ativo"), rs.getString("primeiro_acesso"), new Cidade(rs.getInt("cod_cidade")),
                        new NivelFuncionario(rs.getInt("cod_nivel")),
                        rs.getString("bairro"), rs.getString("cep"));
            }
        } catch (SQLException e) {
        }
        return null;
    }

}
