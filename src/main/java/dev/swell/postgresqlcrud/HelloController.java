package dev.swell.postgresqlcrud;

import dev.swell.postgresqlcrud.domain.aluno.AlunoEntity;
import dev.swell.postgresqlcrud.domain.aluno.AlunoMapper;
import dev.swell.postgresqlcrud.domain.aluno.AlunoModel;
import dev.swell.postgresqlcrud.domain.aluno.AlunoViewModel;
import dev.swell.postgresqlcrud.persistance.CrudRepository;
import dev.swell.postgresqlcrud.persistance.DBConnection;
import dev.swell.postgresqlcrud.persistance.aluno.AlunoRepository;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class HelloController {

    @FXML
    private Button btnAdicionar;

    @FXML
    private TableColumn<AlunoModel, Double> columnMedia;

    @FXML
    private TableColumn<AlunoModel, String> columnNome;

    @FXML
    private TableColumn<AlunoModel, Double> columnNota1;

    @FXML
    private TableColumn<AlunoModel, Double> columnNota2;

    @FXML
    private TableColumn<AlunoModel, Double> columnNota3;

    @FXML
    private TableColumn<AlunoModel, Double> columnNota4;

    @FXML
    private TableColumn<AlunoModel, AlunoModel.Situacao> columnSituacao;

    @FXML
    private Pagination pg;

    @FXML
    private TableView<AlunoModel> tbAlunos;

    @FXML
    private TextField tfBuscar;

    private final CrudRepository<AlunoEntity> alunoRepository = new AlunoRepository();
    private final AlunoMapper alunoMapper = new AlunoMapper();
    private final AlunoViewModel alunoViewModel = new AlunoViewModel(alunoRepository, alunoMapper);

    @FXML
    void initialize() {

        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnNota1.setCellValueFactory(new PropertyValueFactory<>("nota1"));
        columnNota2.setCellValueFactory(new PropertyValueFactory<>("nota2"));
        columnNota3.setCellValueFactory(new PropertyValueFactory<>("nota3"));
        columnNota4.setCellValueFactory(new PropertyValueFactory<>("nota4"));
        columnMedia.setCellValueFactory(new PropertyValueFactory<>("media"));
        columnSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));


        var formaterNumberFactoryView = new Callback<TableColumn<AlunoModel, Double>, TableCell<AlunoModel, Double>>() {
            @Override
            public TableCell<AlunoModel, Double> call(TableColumn<AlunoModel, Double> coluna) {
                return new TableCell<>() {
                    @Override
                    public void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && item != null) {
                            setText(String.format("%.2f", item));
                            if (!coluna.equals(columnMedia)) {
                                AlunoModel alunoModel = getTableRow().getItem();
                                if (alunoModel == null) return;

                                Integer menorNotaIndex = alunoModel.getMenorNotaIndex();

                                boolean destacar = (coluna.equals(columnNota1) && menorNotaIndex == 1)
                                        || (coluna.equals(columnNota2) && menorNotaIndex == 2)
                                        || (coluna.equals(columnNota3) && menorNotaIndex == 3)
                                        || (coluna.equals(columnNota4) && menorNotaIndex == 4);

                                if (destacar) {
                                    if (!getStyleClass().contains("menor-nota")) {
                                        getStyleClass().add("menor-nota");
                                        setText(String.format("%.2f (Excluída)", item));
                                    }
                                } else {
                                    getStyleClass().remove("menor-nota");
                                }
                            }
                        }
                    }
                };
            }
        };

        columnNota1.setCellFactory(formaterNumberFactoryView);
        columnNota2.setCellFactory(formaterNumberFactoryView);
        columnNota3.setCellFactory(formaterNumberFactoryView);
        columnNota4.setCellFactory(formaterNumberFactoryView);
        columnMedia.setCellFactory(formaterNumberFactoryView);

        columnSituacao.setCellFactory(new Callback<>() {
            @Override
            public TableCell<AlunoModel, AlunoModel.Situacao> call(TableColumn<AlunoModel, AlunoModel.Situacao> coluna) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(AlunoModel.Situacao situacao, boolean empty) {
                        super.updateItem(situacao, empty);
                        if (!empty && situacao != null) {
                            switch (situacao) {
                                case APROVADO -> {
                                    setText("APROVADO");
                                    setStyle("-fx-background-color: #5bde5b; -fx-font-weight: bold;-fx-text-fill: #0d5102;");
                                }
                                case REPROVADO -> {
                                    setText("REPROVADO");
                                    setStyle("-fx-background-color: #710404;-fx-font-weight: bold;-fx-text-fill: #e31111;");
                                }
                                case RECUPERACAO -> {
                                    setText("RECUPERAÇÃO");
                                    setStyle("-fx-background-color: #0191cf;-fx-font-weight: bold;-fx-text-fill: #02335a;");
                                }
                            }
                        }
                    }
                };
            }
        });


        tbAlunos.setRowFactory(tv -> {
            TableRow<AlunoModel> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem menuItem = new MenuItem("Deletar");
            menuItem.setOnAction(event -> {
                AlunoModel alunoModel = row.getItem();
                if (alunoModel != null) {
                    alunoViewModel.removerAluno(alunoModel.getId());
                }
            });
            contextMenu.getItems().add(menuItem);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu));

            return row;
        });

        tbAlunos.setItems(alunoViewModel.getAlunoList());

        btnAdicionar.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-aluno-view.fxml"));
                fxmlLoader.setController(new AddAlunoModalController(alunoViewModel::cadastrarAluno));
                Stage stage = new Stage();
                stage.setTitle("Add Aluno");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.showAndWait();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

}
