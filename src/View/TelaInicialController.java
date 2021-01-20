 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Models.Funcionario;
import com.jfoenix.controls.JFXButton;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaInicialController implements Initializable {

    @FXML
    private Label lbUsuario;
    public static Funcionario funcionario;

    public static Funcionario getFuncionario() {
        return funcionario;
    }

    public static void setFuncionario(Funcionario funcionario) {
        TelaInicialController.funcionario = funcionario;
    }
    @FXML
    private JFXButton cbLogado;
    @FXML
    private JFXButton cbEmpresa;
    @FXML
    private Pane pnCad;
    @FXML
    private Label lbDtAtual;
    @FXML
    private JFXButton tpDesp;
    @FXML
    private JFXButton nivFunc;
    @FXML
    private JFXButton cat;
    @FXML
    private JFXButton cadCliente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TIRAR ESSE SET ////////////////////////////////////////////////////////////////////////////////
//        Funcionario f = new Funcionario();
//        if(f.get("").get(0)!=null)
//         f = f.get("").get(0);
//        setFuncionario(f);

        lbUsuario.setText(funcionario.getNome());
        Date data = new Date();
        data = java.sql.Date.valueOf(LocalDate.now());
        if (funcionario.getNivel().getCodigo() != 1) {
            cbEmpresa.setVisible(false);
        }
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        lbDtAtual.setText(dt.format(data));

    }


    @FXML
    private void funCad() throws IOException {
        Parent inicial = FXMLLoader.load(getClass().getResource("/View/TelaFuncionarioCadastro.fxml"));
        Scene scene = new Scene(inicial);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BEM-VINDO: AO CADASTRO DE FUNCIONARIO");
        // stage.setMaximized(true);

        //Close authentication Window
        //Show main Window
        stage.show();
    }

    @FXML
    private void Empresa(ActionEvent event) throws IOException {
        Parent inicial = FXMLLoader.load(getClass().getResource("TelaEmpresaParametrosCad.fxml"));
        Scene scene = new Scene(inicial);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BEM-VINDO: AO CADASTRO DE EMPRESA");
        // stage.setMaximized(true);

        //Close authentication Window
        //Show main Window
        stage.show();
    }

    @FXML
    private void cadDesp(ActionEvent event) throws IOException {
        Parent inicial = FXMLLoader.load(getClass().getResource("TelaTipoDespesa.fxml"));
        Scene scene = new Scene(inicial);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BEM-VINDO: AO CADASTRO DE TIPOS DE DESPESA");
        // stage.setMaximized(true);

        //Close authentication Window
        //Show main Window
        stage.show();
    }

    @FXML
    private void cadNivelFunc(ActionEvent event) throws IOException {
         Parent inicial = FXMLLoader.load(getClass().getResource("TelaNivelFuncionario.fxml"));
        Scene scene = new Scene(inicial);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BEM-VINDO: AO CADASTRO DE NÍVEL DE FUNCIONARIO");
        // stage.setMaximized(true);
        //Close authentication Window
        //Show main Window
        stage.show();
    }

    @FXML
    private void cadCat(ActionEvent event) throws IOException {
        Parent inicial = FXMLLoader.load(getClass().getResource("TelaCategoria.fxml"));
        Scene scene = new Scene(inicial);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BEM-VINDO: AO CADASTRO DE CATEGORIAS");
        // stage.setMaximized(true);

        //Close authentication Window
        //Show main Window
        stage.show();
    }

    @FXML
    private void cadCliente(ActionEvent event) throws IOException {
           Parent inicial = FXMLLoader.load(getClass().getResource("TelaClienteCadastro.fxml"));
        Scene scene = new Scene(inicial);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BEM-VINDO: AO CADASTRO DE CATEGORIAS");
        // stage.setMaximized(true);

        //Close authentication Window
        //Show main Window
        stage.show();
    }
}
