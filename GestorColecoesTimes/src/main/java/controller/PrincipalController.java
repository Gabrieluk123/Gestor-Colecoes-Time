package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Time;
import model.dao.DaoFactory;
import model.dao.TimeDaoJDBC;
import start.App;

/**
 * FXML Controller class
 *
 * @author aguia
 */
public class PrincipalController implements Initializable {

    @FXML
    private Button btnEditarPrincipal;
    @FXML
    private Button btnExcluirPrincipal;
    @FXML
    private TextField txtPesquisarPrincipal;
    @FXML
    private Button btnFazerPesquisaPrincipal;
    @FXML
    private TableView<Time> tblTimesPrincipal;
    @FXML
    private TableColumn<Time, String> tblColNomeDoTimePrincipal;
    @FXML
    private TableColumn<Time, String> tblColLigaPrincipal;
    @FXML
    private TableColumn<Time, String> tblColSiglaPrincipal;
    @FXML
    private Button btnVerCamisasPrincipal;
    @FXML
    private Button btnIncluirTimePrincipal;

    private Time timeSelecionado;
    private List<Time> listaTime;
    private ObservableList<Time> observableListTime;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        observableListTime = FXCollections.observableArrayList();
        tblTimesPrincipal.setItems(observableListTime);
        carregarTimes("");
    }

    @FXML
    private void btnEditarPrincipalOnAction(ActionEvent event) throws IOException {
        timeSelecionado = tblTimesPrincipal.getSelectionModel().getSelectedItem();

        if (timeSelecionado != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FormularioTime.fxml"));
            Parent root = loader.load();

            FormularioTimeController controller = loader.getController();
            controller.setTimeSelecionado(timeSelecionado);
            controller.setObservableTimes(observableListTime);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Editar Time");
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setContentText("Por favor, selecione um time para editar.");
            alert.showAndWait();
        }
    }

    @FXML
    private void btnExcluirPrincipalOnAction(ActionEvent event) throws Exception {
        Time timeSelecionado = tblTimesPrincipal.getSelectionModel().getSelectedItem();
        TimeDaoJDBC dao = DaoFactory.novoTimeDAO();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Aviso");
        alert.setContentText("Confirma exclusão de " + timeSelecionado.getNome() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                dao.excluir(timeSelecionado);
            } catch (Exception e) {
                String mensagem = "Ocorreu um erro: " + e.getMessage();
                Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
                alertErro.setTitle("Aviso");
                alertErro.setContentText(mensagem);
                alertErro.showAndWait();
            }
        }
        carregarTimes("");
    }

    @FXML
    private void btnFazerPesquisaPrincipalOnAction(ActionEvent event) {
        carregarTimes(txtPesquisarPrincipal.getText());
    }

    @FXML
    private void btnIncluirTimePrincipalOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FormularioTime.fxml"));
            Parent root = loader.load();

            FormularioTimeController controller = loader.getController();
            controller.setObservableTimes(observableListTime);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Incluir Time");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Erro");
            alertErro.setContentText("Falha ao abrir o formulário: " + e.getMessage());
            alertErro.showAndWait();
        }
    }

    @FXML
    private void btnVerCamisasPrincipalOnAction(ActionEvent event) {
        Time timeSelecionado = tblTimesPrincipal.getSelectionModel().getSelectedItem();

        if (timeSelecionado == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhum time selecionado");
            alert.setContentText("Por favor, selecione um time para visualizar as camisas.");
            alert.showAndWait();
            return;
        }

        try {
            System.out.println("Time selecionado: " + timeSelecionado.getNome() + " (ID: " + timeSelecionado.getId() + ")");

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/view/TabelaCamisas.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            TabelaCamisasController controller = fxmlLoader.getController();
            controller.setTime(timeSelecionado);

            Stage stage = new Stage();
            stage.setTitle("Gerenciador de Camisas");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
            alertErro.setTitle("Erro Desconhecido");
            alertErro.setHeaderText("Ocorreu um erro inesperado.");
            alertErro.setContentText("Erro: " + e.getMessage());
            alertErro.showAndWait();
            e.printStackTrace();
        }
    }

    public void carregarTimes(String param) {
        tblColNomeDoTimePrincipal.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblColLigaPrincipal.setCellValueFactory(new PropertyValueFactory<>("ligaTime"));
        tblColSiglaPrincipal.setCellValueFactory(new PropertyValueFactory<>("sigla"));

        try {
            TimeDaoJDBC dao = DaoFactory.novoTimeDAO();
            listaTime = dao.listar(param);

            if (listaTime == null) {
                listaTime = new ArrayList<>();
            }

            observableListTime.setAll(listaTime);

            System.out.println("Times carregados: " + listaTime.size());

        } catch (Exception e) {
            Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
            alertErro.setTitle("Aviso");
            alertErro.setContentText("Ocorreu um erro: " + e.getMessage());
            alertErro.showAndWait();
        }

        tblTimesPrincipal.setItems(observableListTime);
    }

}
