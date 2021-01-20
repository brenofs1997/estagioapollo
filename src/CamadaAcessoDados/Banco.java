package CamadaAcessoDados;

public class Banco 
{
    private static Conexao con;
    public static boolean conectar()
    {
        con = new Conexao();
        return con.conectar("jdbc:postgresql://localhost/", "apollo", "postgres", "postgres123"); 
    }
    public static Conexao getCon()
    {  return con;
    }    
}
