package dev.swell.postgresqlcrud.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AlunoViewModel {

    private final ObservableList<AlunoModel> _alunos = FXCollections.observableArrayList();

    public void cadastrarAluno(AlunoData alunoData) {
        _alunos.add(new AlunoModel(alunoData));
    }

    public void removerAluno(String nome) {
        _alunos.removeIf(aluno -> aluno.getNome().equals(nome));
    }

    public ObservableList<AlunoModel> getAlunoList() {
        return _alunos;
    }


}
