/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Time;
import model.dao.DaoFactory;
import model.dao.TimeDaoJDBC;

/**
 * FXML Controller class
 *
 * @author aguia
 */
public class FormularioTimeController implements Initializable {

    @FXML
    private TableView<Time> tblTimesPrincipal;
    @FXML
    private TextField txtNomeTimeFormTime;
    @FXML
    private TextField txtLigaFormTime;
    @FXML
    private TextField txtSiglaFormTime;
    @FXML
    private Button btnSalvarTimeFormTime;
    @FXML
    private Button btnCancelarTimeFormTime1;

    private static Time timeSelecionado;
    private Time time;
    private ObservableList<Time> observableTimes;

    public void setTimeSelecionado(Time time) {
        timeSelecionado = time;
        if (timeSelecionado != null) {
            txtNomeTimeFormTime.setText(timeSelecionado.getNome());
            txtLigaFormTime.setText(timeSelecionado.getLigaTime());
            txtSiglaFormTime.setText(timeSelecionado.getSigla());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (timeSelecionado != null) {
            txtNomeTimeFormTime.setText(timeSelecionado.getNome());
            txtLigaFormTime.setText(timeSelecionado.getLigaTime());
            txtSiglaFormTime.setText(timeSelecionado.getSigla());
        }
    }

    private void tblElementoOnAction(MouseEvent event) {
        limparCampos();
        timeSelecionado = tblTimesPrincipal.getSelectionModel().getSelectedItem();
        if (timeSelecionado != null) {
            txtNomeTimeFormTime.setText(timeSelecionado.getNome());
            txtLigaFormTime.setText(timeSelecionado.getLigaTime());
            txtSiglaFormTime.setText(timeSelecionado.getSigla());
        }
    }

    private void limparCampos() {
        txtNomeTimeFormTime.clear();
        txtLigaFormTime.clear();
        txtSiglaFormTime.clear();

    }

    @FXML
    private void btnSalvarTimeFormTimeOnAction(ActionEvent event) {
        Time time = new Time();
        time.setNome(txtNomeTimeFormTime.getText());
        time.setLigaTime(txtLigaFormTime.getText());
        time.setSigla(txtSiglaFormTime.getText());

        try {
            TimeDaoJDBC dao = DaoFactory.novoTimeDAO();

            if (timeSelecionado == null) {
                dao.incluir(time);
                observableTimes.add(time);
            } else {
                time.setId(timeSelecionado.getId());
                dao.editar(time);
                int index = observableTimes.indexOf(timeSelecionado);
                observableTimes.set(index, time);
                timeSelecionado = null;
            }

            limparCampos();
        } catch (Exception e) {
            Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
            alertErro.setTitle("Aviso");
            alertErro.setContentText("Ocorreu um erro: " + e.getMessage());
            alertErro.showAndWait();
        }
    }

    @FXML
    private void btnCancelarTimeFormTimeOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setObservableTimes(ObservableList<Time> observableTimes) {
        this.observableTimes = observableTimes;
    }
}
