/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Cidade;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author paulo
 */
public class CidadeController {

    public void carregarConsulta(String cidade, String filtro, TableView<Cidade> tabela) {
        Cidade c = new Cidade();
        List<Cidade> cid = new ArrayList();
        ObservableList<Cidade> f;

        cid = (List<Cidade>) c.get(filtro);
        f = FXCollections.observableArrayList(cid);
        tabela.setItems(f);
    }

}
