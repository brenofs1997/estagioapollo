/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import CamadaAcessoDados.Banco;
import Erros.Erros;
import Models.Categoria;
import Models.Cidade;
import Models.Cliente;
import Models.Estado;
import Models.Fornecedor;
import static View.TelaFornecedorCadastroController.itensCategoria;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;

/**
 *
 * @author paulo
 */
public class FornecedorController {

    Erros msg = new Erros();

    public void carregaTabela(String Filtro, TableView<Fornecedor> tabela) {
        Fornecedor f = new Fornecedor();
        List<Fornecedor> res = f.get(Filtro);
        ObservableList<Fornecedor> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }

    public List<Estado> carregarUf() {
        Estado e = new Estado();
        List<Estado> Lista = e.get("");
        return Lista;
    }

    public List<Categoria> CarregarCategorias() {
        Categoria c = new Categoria();
        List<Categoria> Lista = c.get("");
        return Lista;
    }

    public List<Cidade> CarregarCidUF(int coduf) {
        Cidade c = new Cidade();
        List<Cidade> Lista = c.getCidUf(coduf);
        return Lista;
    }

    public int procLista(List<Categoria> lista, int codCat) {
        int index = -1;
        for (Categoria item : lista) {
            if (item.getCodigo() == codCat) {
                index = lista.indexOf(item);
                return index;
            }
        }
        return index;
    }

    public List<Categoria> AddProduto(Categoria cat, TableView<Categoria> tabelaCat) {
        int index = -1;
        if (!tabelaCat.getItems().isEmpty()) {
            index = procLista(tabelaCat.getItems(), cat.getCodigo());
        }
        if (index >= 0) {
            msg.Affirmation("Apollo Informa:", "Item já está na lista!");
        } else {
            itensCategoria.add(cat);
        }
        return itensCategoria;
    }

    public void evtTabela(TableView<Fornecedor> tabela, JFXButton btalterar, JFXButton btapagar) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    public void Pesquisar(JFXTextField txpesquisar, JFXRadioButton rbnome, JFXRadioButton rbcpf, TableView<Fornecedor> tabela) {
        if (!txpesquisar.getText().isEmpty()) {
            if (rbnome.isSelected()) {
                carregaTabela("upper(nomefantasia) like '%" + txpesquisar.getText().toUpperCase() + "%'", tabela);
            } else {
                if (rbcpf.isSelected()) {
                    carregaTabela("upper(cnpj) like '%" + txpesquisar.getText().toUpperCase() + "%'", tabela);
                }
            }
        } else {
            carregaTabela("", tabela);
        }
    }

    public boolean gravar(int cod, String nome, String razao, String txendereco, String numero, String bairro, int codcid,
            String cep, String telefone, String cnpj, String email, String ativo, List<Categoria> itensCategoria) {
        Fornecedor f = new Fornecedor(cod, nome, cnpj, ativo.toUpperCase(), txendereco, bairro, numero, new Cidade(codcid), email, razao, cep, telefone);
        boolean aux = false;
        if (f.salvar(f)) {
            int cod_for = 0;

            cod_for = Banco.getCon().getMaxPK("fornecedor", "codigo");

            for (Categoria categoria : itensCategoria) {
                if (f.salvarCategoria(cod_for, categoria.getCodigo())) {
                    aux = true;
                }
            }
            return aux;
        }
        return aux;
    }

    public boolean alterar(int cod, String nome, String razao, String txendereco, String numero, String bairro, int codcid,
            String cep, String telefone, String cnpj, String email, String ativo, List<Categoria> itensCategoria) {
        Fornecedor f = new Fornecedor(cod, nome, cnpj, ativo.toUpperCase(), txendereco, bairro, numero, new Cidade(codcid), email, razao, cep, telefone);
        boolean aux = false;
        if (f.alterar(f) && f.apagarCats(cod)) {
            int cod_for = 0;
            cod_for = cod;
            
            for (Categoria categoria : itensCategoria) {
                if (f.salvarCategoria(cod_for, categoria.getCodigo())) {
                    aux = true;
                }
            }
            return aux;
        }
        return aux;
    }

    public boolean apagar(TableView<Fornecedor> tabela) {
        boolean confirma = false;
        Fornecedor aux = new Fornecedor();
        aux = tabela.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirmar exclusão?");
        if (a.showAndWait().get() == ButtonType.OK) {

            if (aux.apagar(aux.getCodigo())) {
                msg.Affirmation("Apollo Informa:", "Exclusão feita com sucesso");
                return true;
            } else {
                msg.Error("Erro ", "Exclusão não realizada");
            }
        }
        return confirma;
    }

    public void alterar(JFXTextField txcod, JFXTextField txnome, JFXTextField txrazao, JFXTextField txcnpj, JFXTextField txendereco, JFXTextField txnum, JFXTextField txbairro, JFXTextField txtelefone, JFXCheckBox chkAtivo, JFXComboBox<Estado> cbUf, JFXComboBox<Cidade> cbCid, JFXTextField txcep, JFXTextField txemail, TableView<Categoria> tabelaCat, TableView<Fornecedor> tabela) {
       
        Fornecedor f = new Fornecedor();
        f = f.get(tabela.getSelectionModel().getSelectedItem().getCodigo());
        
        txcod.setText("" + f.getCodigo());
        if (f.getAtivo().equals("ATIVO")) {
            chkAtivo.setSelected(true);
            chkAtivo.setText("Ativo");
        } else {
            chkAtivo.setSelected(false);
            chkAtivo.setText("Não Ativo");
        }
        txnome.setText(f.getNomefantasia());
        txrazao.setText(f.getRazaosocial());
        txendereco.setText(f.getEndereco());
        txnum.setText(f.getNumero());
        txbairro.setText(f.getBairro());

        Cidade c = new Cidade();
        c = c.get(f.getCidade().getCid_cod());

        Estado e = new Estado();
        e = e.getPorCid(c.getCid_cod());

        cbUf.getSelectionModel().select(0);
        cbUf.getSelectionModel().select(e);

        cbCid.getSelectionModel().select(0);
        cbCid.getSelectionModel().select(c);

        txcep.setText(f.getCep());
        txtelefone.setText(f.getTelefonecontato());
        txcnpj.setText(f.getCnpj());
        txemail.setText(f.getEmail());
        itensCategoria = f.getCategorias(f.getCodigo());
        ObservableList<Categoria> prod_v = FXCollections.observableArrayList(itensCategoria);
        tabelaCat.getItems().clear();
        tabelaCat.setItems(prod_v);

    }

}
