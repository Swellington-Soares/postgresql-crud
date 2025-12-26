package dev.swell.postgresqlcrud.domain.aluno;

import dev.swell.postgresqlcrud.persistance.CrudRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AlunoViewModel {

    private final ObservableList<AlunoModel> _alunos = FXCollections.observableArrayList();
    private final CrudRepository<AlunoEntity> repository;
    private final AlunoMapper alunoMapper;

    public void cadastrarAluno(AlunoData alunoData) {
        AlunoModel alunoModel = new AlunoModel(alunoData);
        AlunoEntity alunoEntity = repository.save(alunoMapper.alunoModelToAlunoEntity(alunoModel));
        _alunos.add(alunoMapper.alunoEntityToAlunoModel(alunoEntity));
    }

    public void removerAluno(Long id) {
        _alunos.removeIf(aluno -> {
            boolean removed = aluno.getId().equals(id);
            if (removed) {
                repository.deleteById(aluno.getId());
            }
            return removed;
        });
    }

    public ObservableList<AlunoModel> getAlunoList() {
        return _alunos;
    }


    public AlunoViewModel(CrudRepository<AlunoEntity> repository, AlunoMapper alunoMapper) {
        this.repository = repository;
        this.alunoMapper = alunoMapper;
        repopulateList();
    }

    private void repopulateList() {
        _alunos.addAll(repository.findAll().stream().map(alunoMapper::alunoEntityToAlunoModel).toList());
    }
}
