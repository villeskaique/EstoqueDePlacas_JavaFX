module br.villes.aplicativoplacas {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires com.fasterxml.jackson.databind;

    opens br.villes.aplicativoplacas.controllers to javafx.fxml;
    opens br.villes.aplicativoplacas.moldels to javafx.base;
    exports br.villes.aplicativoplacas.moldels;
    exports br.villes.aplicativoplacas;
    opens br.villes.aplicativoplacas to javafx.fxml, javafx.graphics;
}

