package mh.yourbudget;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mh.yourbudget.bdd.Database;

import java.io.IOException;

public class DashboardApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DashboardApplication.class.getResource("dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Your budget dashboard");
        stage.getIcons().add(new Image(DashboardApplication.class.getResourceAsStream("/mh/yourbudget/assets/images/iconBudget.png")));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        // Effectuer la vérification de la base de données ici
        boolean dbInitialized = Database.isOK();
        if (dbInitialized) {
            System.out.println("La base de données a été correctement initialisée.");
            launch(args); // Lancer l'application seulement si la base de données est OK
        } else {
            System.err.println("Échec de l'initialisation de la base de données. L'application va se fermer.");
            System.exit(1); // Arrêter l'exécution si la base de données n'est pas OK
        }
    }
}