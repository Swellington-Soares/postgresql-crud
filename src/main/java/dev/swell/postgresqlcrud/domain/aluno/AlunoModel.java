package dev.swell.postgresqlcrud.domain.aluno;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class AlunoModel {

    private final AlunoData data;
    private final SimpleObjectProperty<Long> id = new SimpleObjectProperty<>(null);

    public Long getId() {
        return id.get();
    }

    public SimpleObjectProperty<Long> idProperty() {
        return id;
    }

    private final SimpleStringProperty nome = new SimpleStringProperty();
    private final SimpleDoubleProperty nota1 = new SimpleDoubleProperty(0.0);
    private final SimpleDoubleProperty nota2 = new SimpleDoubleProperty(0.0);
    private final SimpleDoubleProperty nota3 = new SimpleDoubleProperty(0.0);
    private final SimpleDoubleProperty nota4 = new SimpleDoubleProperty(0.0);
    private final SimpleDoubleProperty media = new SimpleDoubleProperty(0.0);
    private final SimpleObjectProperty<Situacao> situacao = new SimpleObjectProperty<>(Situacao.REPROVADO);

    public String getNome() {
        return nome.get();
    }

    public SimpleStringProperty nomeProperty() {
        return nome;
    }

    public double getNota1() {
        return nota1.get();
    }

    public SimpleDoubleProperty nota1Property() {
        return nota1;
    }

    public double getNota2() {
        return nota2.get();
    }

    public SimpleDoubleProperty nota2Property() {
        return nota2;
    }

    public double getNota3() {
        return nota3.get();
    }

    public SimpleDoubleProperty nota3Property() {
        return nota3;
    }

    public double getNota4() {
        return nota4.get();
    }

    public SimpleDoubleProperty nota4Property() {
        return nota4;
    }

    public double getMedia() {
        return media.get();
    }

    public SimpleDoubleProperty mediaProperty() {
        return media;
    }

    public Situacao getSituacao() {
        return situacao.get();
    }

    public SimpleObjectProperty<Situacao> situacaoProperty() {
        return situacao;
    }

    public AlunoModel(
            AlunoData data
    ) {
        this.data = data;
        populateData();
    }

    public AlunoModel(Long id, String nome, Double nota1, Double nota2, Double nota3, Double nota4) {
        this(new AlunoData(nome, nota1, nota2, nota3, nota4));
        this.id.set(id);
    }

    private void populateData() {
        nome.set(data.nome());
        nota1.set(data.nota1());
        nota2.set(data.nota2());
        nota3.set(data.nota3());
        nota4.set(data.nota4());
        media.set(calcularMedia());
        situacao.set(checkSituacao());
    }

    private Situacao checkSituacao() {
        if (media.get() >= 7.0) return Situacao.APROVADO;
        if (media.get() >= 4.0) return Situacao.RECUPERACAO;
        return Situacao.REPROVADO;
    }


    public Double menorNota() {
        return Math.min(Math.min(data.nota1(), data.nota2()), Math.min(data.nota3(), data.nota4()));
    }

    public Integer getMenorNotaIndex() {
        return Stream.of(
                data.nota1(),
                data.nota2(),
                data.nota3(),
                data.nota4()
        ).toList().indexOf(menorNota()) + 1;
    }

    private Double calcularMedia() {
        return ((data.nota1() + data.nota2() + data.nota3() + data.nota4()) - menorNota()) / 3.0;

    }


    public enum Situacao {
        APROVADO,
        RECUPERACAO,
        REPROVADO
    }
}
