/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Camisa;
import model.Time;
import model.dao.CamisaDaoJDBC;
import model.dao.DaoFactory;
import start.App;

/**
 * FXML Controller class
 *
 * @author aguia
 */
public class TabelaCamisasController {

    @FXML
    private TableView<Camisa> tblCamisas;
    @FXML
    private Button btnZerarCamisaTblCamisa;
    @FXML
    private Button btnAddFotoCamisaTblCamisa;
    @FXML
    private Button btnEditarTblCamisa;
    @FXML
    private Button btnEstatisticaTblCamisa;
    @FXML
    private Button btnExcluirTblCamisa;
    @FXML
    private Button btnFazerPesquisaTblCamisa;
    @FXML
    private Button btnSalvarCamisaTblCamisa;
    @FXML
    private Button btnVoltarCamisaTblCamisa;
    @FXML
    private CheckBox chkBxSimCamisaTblCamisa;
    @FXML
    private ImageView imgViewTblCamisa;
    private String localImagem;
    private String caminhoImagem;
    private final String diretorioImagens = "src/main/resources/imagens";
    @FXML
    private TableColumn<Camisa, String> tblColMarcaTblCamisa;
    @FXML
    private TableColumn<Camisa, String> tblColNomedaCamisaTblCamisa;
    @FXML
    private TableColumn<Camisa, Boolean> tblColPossuiTblCamisa;
    @FXML
    private TableColumn<Camisa, String> tblColTamanhoTblCamisa;
    @FXML
    private TableColumn<Camisa, String> tblColTemporadaTblCamisa;
    @FXML
    private TextField txtMarcaTblCamisa;
    @FXML
    private TextField txtNomeCamisaTblCamisa;
    @FXML
    private TextField txtPesquisarTblCamisa;
    @FXML
    private TextField txtTamanhoTblCamisa;
    @FXML
    private TextField txtTempTblCamisa;

    private static Camisa camisaSelecionada;
    private Time time;

    public static void setCamisaSelecionada(Camisa camisa) {
        camisaSelecionada = camisa;
    }

    @FXML
    public void initialize() throws Exception {
        carregarCamisas("", 0);
    }

    @FXML
private void btnZerarTblCamisaOnAction(ActionEvent event) {
    txtNomeCamisaTblCamisa.clear();
    txtMarcaTblCamisa.clear();
    txtTempTblCamisa.clear();
    txtTamanhoTblCamisa.clear();
    chkBxSimCamisaTblCamisa.setSelected(false);
    imgViewTblCamisa.setImage(null);
    
    caminhoImagem = null;

    camisaSelecionada = null;
}

    @FXML
    private void btnAddFotoCamisaTblCamisaOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.png", "*.jpeg"));
        java.io.File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {

                File diretorioImagensFile = new File(diretorioImagens);
                if (!diretorioImagensFile.exists()) {
                    diretorioImagensFile.mkdirs();
                }

                Path destino = Paths.get(diretorioImagens + File.separator + file.getName());
                Files.copy(file.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

                caminhoImagem = file.getName();
                Image image = new Image(new File(diretorioImagens, file.getName()).toURI().toString());
                if (image != null) {
                    imgViewTblCamisa.setImage(image);
                }
            } catch (IOException e) {
                Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
                alertErro.setTitle("Aviso");
                alertErro.setContentText("Ocorreu um erro: " + e.getMessage());
                alertErro.showAndWait();
            }
        }
    }

    private void limparCampos() {
        txtNomeCamisaTblCamisa.clear();
        txtMarcaTblCamisa.clear();
        txtTamanhoTblCamisa.clear();
        txtTempTblCamisa.clear();
        chkBxSimCamisaTblCamisa.setSelected(false);
        imgViewTblCamisa.setImage(null);
    }

    @FXML
    private void btnEditarTblCamisaOnAction(ActionEvent event) throws IOException {
        camisaSelecionada = tblCamisas.getSelectionModel().getSelectedItem();

        if (camisaSelecionada != null) {
            txtNomeCamisaTblCamisa.setText(camisaSelecionada.getNome());
            txtMarcaTblCamisa.setText(camisaSelecionada.getMarca());
            txtTempTblCamisa.setText(camisaSelecionada.getTemporada());
            txtTamanhoTblCamisa.setText(camisaSelecionada.getTamanho());
            chkBxSimCamisaTblCamisa.setSelected(camisaSelecionada.isPossui());
            caminhoImagem = camisaSelecionada.getLocalImagem();
            if (caminhoImagem != null) {
                Image image = new Image(new File(diretorioImagens, caminhoImagem).toURI().toString());
                imgViewTblCamisa.setImage(image);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setContentText("Por favor, selecione uma camisa para editar.");
            alert.showAndWait();
        }
    }

    @FXML
    private void btnEstatisticaTblCamisaOnAction(ActionEvent event) {
        List<Camisa> listaCamisas = new ArrayList<>(tblCamisas.getItems());

        EstatisticaCamisaController.setListaCamisa(listaCamisas);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/view/EstatisticaCamisa.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.setTitle("Estatísticas");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
            alertErro.setTitle("Aviso");
            alertErro.setContentText("Ocorreu um erro: " + e.getMessage());
            alertErro.showAndWait();
        }
    }

   @FXML
private void btnExcluirTblCamisaOnAction(ActionEvent event) throws Exception {
    Camisa camisaSelecionada = tblCamisas.getSelectionModel().getSelectedItem();

    if (camisaSelecionada == null) {
        Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
        alertErro.setTitle("Erro");
        alertErro.setContentText("Por favor, selecione uma camisa para excluir.");
        alertErro.showAndWait();
        return;
    }

    CamisaDaoJDBC dao = DaoFactory.novaCamisaDAO();

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Aviso");
    alert.setContentText("Confirma exclusão de " + camisaSelecionada.getNome() + "?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        try {
            dao.excluir(camisaSelecionada);
        } catch (Exception e) {
            String mensagem = "Ocorreu um erro: " + e.getMessage();
            Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
            alertErro.setTitle("Aviso");
            alertErro.setContentText(mensagem);
            alertErro.showAndWait();
        }
    }

    limparCampos();
    carregarCamisas("", time.getId());
}


    @FXML
    private void btnFazerPesquisaTblCamisa(ActionEvent event) throws Exception {
        carregarCamisas(txtPesquisarTblCamisa.getText(), time.getId());
    }

    @FXML
    private void btnSalvarCamisaTblCamisaOnAction(ActionEvent event) {
        if (time == null) {
            Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
            alertErro.setTitle("Erro");
            alertErro.setContentText("Por favor, selecione um time antes de salvar.");
            alertErro.showAndWait();
            return;
        }

        if (txtNomeCamisaTblCamisa.getText().isEmpty()
                || txtMarcaTblCamisa.getText().isEmpty()
                || txtTamanhoTblCamisa.getText().isEmpty()
                || txtTempTblCamisa.getText().isEmpty()) {
            Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
            alertErro.setTitle("Erro");
            alertErro.setContentText("Por favor, preencha todos os campos antes de salvar.");
            alertErro.showAndWait();
            return;
        }

        try {
            CamisaDaoJDBC dao = DaoFactory.novaCamisaDAO();

            if (camisaSelecionada != null && camisaSelecionada.getId() > 0) {
                camisaSelecionada.setNome(txtNomeCamisaTblCamisa.getText());
                camisaSelecionada.setMarca(txtMarcaTblCamisa.getText());
                camisaSelecionada.setTamanho(txtTamanhoTblCamisa.getText());
                camisaSelecionada.setTemporada(txtTempTblCamisa.getText());
                camisaSelecionada.setPossui(chkBxSimCamisaTblCamisa.isSelected());
                camisaSelecionada.setLocalImagem(caminhoImagem);
                camisaSelecionada.setTimeId(time.getId());
                dao.editar(camisaSelecionada);
            } else {
                Camisa novaCamisa = new Camisa();
                novaCamisa.setNome(txtNomeCamisaTblCamisa.getText());
                novaCamisa.setMarca(txtMarcaTblCamisa.getText());
                novaCamisa.setTamanho(txtTamanhoTblCamisa.getText());
                novaCamisa.setTemporada(txtTempTblCamisa.getText());
                novaCamisa.setPossui(chkBxSimCamisaTblCamisa.isSelected());
                novaCamisa.setLocalImagem(caminhoImagem);
                novaCamisa.setTimeId(time.getId());
                dao.incluir(novaCamisa);
            }

            limparCampos();
            camisaSelecionada = null;
            carregarCamisas("", time.getId());
        } catch (Exception e) {
            Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
            alertErro.setTitle("Aviso");
            alertErro.setContentText("Ocorreu um erro: " + e.getMessage());
            alertErro.showAndWait();
        }
    }

    @FXML
    void btnVoltarCamisaTblCamisaOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void tblCamisasElementoOnAction(MouseEvent event) {
        limparCampos();

        Camisa camisaSelecionada = tblCamisas.getSelectionModel().getSelectedItem();

        if (camisaSelecionada != null) {
            txtNomeCamisaTblCamisa.setText(camisaSelecionada.getNome());
            txtMarcaTblCamisa.setText(camisaSelecionada.getMarca());
            txtTamanhoTblCamisa.setText(camisaSelecionada.getTamanho());
            txtTempTblCamisa.setText(camisaSelecionada.getTemporada());
            chkBxSimCamisaTblCamisa.setSelected(camisaSelecionada.isPossui());

            caminhoImagem = camisaSelecionada.getLocalImagem();

            if (caminhoImagem != null) {
                try {
                    Image image = new Image(new File(diretorioImagens, caminhoImagem).toURI().toString());
                    imgViewTblCamisa.setImage(image);
                } catch (Exception e) {
                    Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
                    alertErro.setTitle("Aviso");
                    alertErro.setContentText("Ocorreu um erro ao carregar a imagem: " + e.getMessage());
                    alertErro.showAndWait();
                }
            }
        }
    }

    @FXML
    private void tblCamisasOnAction(MouseEvent event) {
        Camisa camisaSelecionada = tblCamisas.getSelectionModel().getSelectedItem();

        if (camisaSelecionada != null) {
            txtNomeCamisaTblCamisa.setText(camisaSelecionada.getNome());
            txtMarcaTblCamisa.setText(camisaSelecionada.getMarca());
            txtTamanhoTblCamisa.setText(camisaSelecionada.getTamanho());
            txtTempTblCamisa.setText(camisaSelecionada.getTemporada());
            chkBxSimCamisaTblCamisa.setSelected(camisaSelecionada.isPossui());

            caminhoImagem = camisaSelecionada.getLocalImagem();
            if (caminhoImagem != null) {
                try {
                    Image image = new Image(new File(diretorioImagens, caminhoImagem).toURI().toString());
                    imgViewTblCamisa.setImage(image);
                } catch (Exception e) {
                    Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
                    alertErro.setTitle("Aviso");
                    alertErro.setContentText("Ocorreu um erro ao carregar a imagem: " + e.getMessage());
                    alertErro.showAndWait();
                }
            } else {
                imgViewTblCamisa.setImage(null);
            }
        }
    }

    public void carregarCamisas(String filtro, int timeId) throws Exception {
        tblColNomedaCamisaTblCamisa.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblColMarcaTblCamisa.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tblColTemporadaTblCamisa.setCellValueFactory(new PropertyValueFactory<>("temporada"));
        tblColTamanhoTblCamisa.setCellValueFactory(new PropertyValueFactory<>("tamanho"));
        tblColPossuiTblCamisa.setCellValueFactory(new PropertyValueFactory<>("possui"));

        CamisaDaoJDBC dao = DaoFactory.novaCamisaDAO();
        List<Camisa> camisas = dao.listarPorTime(filtro, timeId);
        ObservableList<Camisa> observableCamisas = FXCollections.observableArrayList(camisas);

        tblCamisas.setItems(observableCamisas);
    }

    public void setTime(Time time) {
        this.time = time;
        try {
            carregarCamisas("", time.getId());
        } catch (Exception e) {
            Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
            alertErro.setTitle("Aviso");
            alertErro.setContentText("Ocorreu um erro ao carregar camisas: " + e.getMessage());
            alertErro.showAndWait();
        }
    }

}
