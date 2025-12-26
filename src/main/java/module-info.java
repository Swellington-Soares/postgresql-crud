module dev.swell.postgresqlcrud {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    opens dev.swell.postgresqlcrud to javafx.fxml, jakarta.persistence, org.hibernate.orm.core, java.sql;
    opens dev.swell.postgresqlcrud.domain.aluno to jakarta.persistence, javafx.base, org.hibernate.orm.core;
    exports dev.swell.postgresqlcrud;
}
