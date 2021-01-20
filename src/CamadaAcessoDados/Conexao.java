package CamadaAcessoDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;

public class Conexao 
{ 
    private static Conexao Instancia;
    private static final String URL = "jdbc:postgresql://localhost/apollo";
    private static String usuario = "postgres";
    private static final String senha = "postgres123";
    private static Connection connect;

    public static Conexao getInstancia()
    {
        if(Instancia == null)
            Instancia=new Conexao();
        return Instancia;
    }
    public static Connection getConnect() {
        return connect;   
    }

    private String erro;
//    public Conexao()
//    {   erro="";
//        this.connect=null;
//    }
    
    
       public static Connection open(){
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, usuario, senha);
            
        }catch (ClassNotFoundException | SQLException ex) {
           Alert a = new Alert(Alert.AlertType.ERROR);
           a.setTitle("Erro de conexão.");
           a.setHeaderText("Não foi possível conectar ao Banco de Dados");
           a.setContentText(ex.toString());
           a.showAndWait();
        }
        return null; 
    }
    
    
    public boolean conectar(String local,String banco,String usuario,String senha)
    {   boolean conectado=false;
        try {
            //Class.forName(driver); "org.postgresql.Driver");
            String url = local+banco; //"jdbc:postgresql://localhost/"+banco;
            connect = DriverManager.getConnection( url, usuario,senha);
            conectado=true;
        }
        catch ( SQLException sqlex )
        { erro="Impossivel conectar com a base de dados: " + sqlex.toString(); }
        catch ( Exception ex )
        { erro="Outro erro: " + ex.toString(); }
        return conectado;
    }
    public String getMensagemErro() {
        return erro;
    }
    public boolean getEstadoConexao() {
        return (connect!=null);
    }
  
    public void close(){
        try{
            connect.close();
        }catch (SQLException e){
        }
    }

    public boolean manipular(String sql) // inserir, alterar,excluir
    {   boolean executou=false;
        try {
            Statement statement = connect.createStatement();
            int result = statement.executeUpdate( sql );
            statement.close();
            if(result>=1)
                executou=true;
        }
        catch ( SQLException sqlex )
        {  erro="Erro: "+sqlex.toString();
        }
        return executou;
    }
    public ResultSet consultar(String sql)
    {   ResultSet rs=null;
        try {
           Statement statement = connect.createStatement();
             //ResultSet.TYPE_SCROLL_INSENSITIVE,
             //ResultSet.CONCUR_READ_ONLY);
           rs = statement.executeQuery( sql );
           //statement.close();
        }
        catch ( SQLException sqlex )
        { erro="Erro: "+sqlex.toString();
          rs = null;
        }
        return rs;
    }
    public int getMaxPK(String tabela,String chave) 
    {
        String sql="select max("+chave+") from "+tabela;
        int max=0;
        ResultSet rs= consultar(sql);
        try 
        {
            if(rs.next())
                max=rs.getInt(1);
        }
        catch (SQLException sqlex)
        { 
             erro="Erro: " + sqlex.toString();
             max = -1;
        }
        return max;
    }
    
}
