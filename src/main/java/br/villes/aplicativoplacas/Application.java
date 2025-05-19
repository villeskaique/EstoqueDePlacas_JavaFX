package br.villes.aplicativoplacas;

import br.villes.aplicativoplacas.controllers.TableController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("placas-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);
        stage.setTitle("Placas");
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void stop() {
        TableController.placaService.encerrarMonitoramento();
        TableController.placaService.salvarPlacas(TableController.placasList);
    }

    public static void main(String[] args) {
        launch();
    }
}