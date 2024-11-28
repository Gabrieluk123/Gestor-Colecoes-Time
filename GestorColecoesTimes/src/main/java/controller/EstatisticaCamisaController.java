/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Camisa;

/**
 * FXML Controller class
 *
 * @author aguia
 */
public class EstatisticaCamisaController implements Initializable {

    @FXML
    private PieChart grafPizza;
    @FXML
    private Button btnVoltarEstatistica;

    /**
     * Initializes the controller class.
     */
    private static List<Camisa> listaCamisas = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        atualizarDados();
    }

    @FXML
    private void btnVoltarEstatisticaOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private float contPossuir() {
        int possui = 0;
        int total = listaCamisas.size();

        for (int i = 0; i < total; i++) {
            if (listaCamisas.get(i).isPossui()) {
                possui++;
            }
        }

        if (total > 0) {
            return (float) possui / total * 100;
        } else {
            return 0;
        }
    }

    private void atualizarDados() {
        grafPizza.getData().clear();

        ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList(
                new PieChart.Data("Camisas possuidas", contPossuir()),
                new PieChart.Data("Camisas nao possuidas", 100 - contPossuir()));

        pieChartData2.forEach(data -> data.nameProperty().bind(
                Bindings.concat(
                        data.getName(), " porcentagem ", data.pieValueProperty())));

        grafPizza.getData().addAll(pieChartData2);
    }

    public static void setListaCamisa(List<Camisa> listaCamisas) {
        EstatisticaCamisaController.listaCamisas = listaCamisas;
    }
}
