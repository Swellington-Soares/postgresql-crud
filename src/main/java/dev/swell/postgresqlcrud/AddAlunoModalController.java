package dev.swell.postgresqlcrud;

import dev.swell.postgresqlcrud.domain.aluno.AlunoData;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class AddAlunoModalController {


    private final Consumer<AlunoData> callback;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnConfirmar;

    @FXML
    private HBox hboxNomeError;

    @FXML
    private HBox hboxNota1Error;

    @FXML
    private HBox hboxNota2Error;

    @FXML
    private HBox hboxNota3Error;

    @FXML
    private HBox hboxNota4Error;

    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldNota1;

    @FXML
    private TextField textFieldNota2;

    @FXML
    private TextField textFieldNota3;

    @FXML
    private TextField textFieldNota4;

    public AddAlunoModalController(Consumer<AlunoData> callback) {
        this.callback = callback;
    }

    private void clearErrors() {
        hboxNomeError.setVisible(false);
        hboxNota1Error.setVisible(false);
        hboxNota2Error.setVisible(false);
        hboxNota3Error.setVisible(false);
        hboxNota4Error.setVisible(false);
    }

    private void showError(HBox errorContainer, String message) {
        Text text = (Text) errorContainer.getChildren().get(0);
        if (text != null) {
            text.setText(message);
            errorContainer.setVisible(true);
        }
    }

    private void validateNotaField(HBox container, TextField textField, String field) {

        try {
           var nota = Double.parseDouble(textField.getText());
           if (nota < 0 || nota > 10) {
               throw new FieldErrorException(container, String.format("Campo %s deve ser entre 0 e 10.", field));
           }
        } catch (Exception e) {
            if (e instanceof NumberFormatException) {
                throw new FieldErrorException(container, String.format("Campo %s inválido.", field));
            }
            throw e;
        }
    }

    private void validate() {
        if (textFieldNome.getText().isBlank())
            throw new FieldErrorException(hboxNomeError, "Campo Nome não pode ser vazio.");

        validateNotaField(hboxNota1Error, textFieldNota1, "Nota 1");
        validateNotaField(hboxNota2Error, textFieldNota2,"Nota 2");
        validateNotaField(hboxNota3Error, textFieldNota3,"Nota 3");
        validateNotaField(hboxNota4Error, textFieldNota4,"Nota 4");
    }

    @FXML
    void initialize() {
        clearErrors();
        btnConfirmar.setOnAction(event -> {
            try {
                clearErrors();
                validate();
                Node target = (Node) event.getSource();
                Stage window = (Stage) target.getScene().getWindow();
                window.close();
                if (callback != null) {
                    callback.accept(new AlunoData(
                            textFieldNome.getText(),
                            Double.parseDouble(textFieldNota1.getText()),
                            Double.parseDouble(textFieldNota2.getText()),
                            Double.parseDouble(textFieldNota3.getText()),
                            Double.parseDouble(textFieldNota4.getText())
                    ));
                }
            } catch (Exception e) {
                if (e instanceof FieldErrorException) {
                    showError(((FieldErrorException) e).getContainer(), e.getMessage());
                } else {
                    throw e;
                }
            }
        });
    }


}
