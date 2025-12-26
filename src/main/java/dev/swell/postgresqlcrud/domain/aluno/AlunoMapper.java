package dev.swell.postgresqlcrud.domain.aluno;

public class AlunoMapper {

    AlunoData alunoEntityToAlunoData(AlunoEntity alunoEntity) {
        return new AlunoData(
                alunoEntity.getNome(),
                alunoEntity.getNota1(),
                alunoEntity.getNota2(),
                alunoEntity.getNota3(),
                alunoEntity.getNota4()
        );
    }

    AlunoModel alunoEntityToAlunoModel(AlunoEntity alunoEntity) {
        return new AlunoModel(
                alunoEntity.getId(),
                alunoEntity.getNome(),
                alunoEntity.getNota1(),
                alunoEntity.getNota2(),
                alunoEntity.getNota3(),
                alunoEntity.getNota4()
        );
    }

    AlunoEntity alunoModelToAlunoEntity(AlunoModel alunoModel) {
        return new AlunoEntity(
                alunoModel.getId(),
                alunoModel.getNome(),
                alunoModel.getNota1(),
                alunoModel.getNota2(),
                alunoModel.getNota3(),
                alunoModel.getNota4()
        );
    }
}
