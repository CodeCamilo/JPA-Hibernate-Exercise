package org.ieselgrao.hibernatepractica.view;

import javafx.fxml.FXML;
import org.ieselgrao.hibernatepractica.UniGraoVerse;
import org.ieselgrao.hibernatepractica.controller.UniGraoVerseController;

import java.io.IOException;

public class MainViewController {

    /**
     * It goes to the "play" scene with MySQL Setup
     */
    @FXML
    public void newLoginMySQL(){
        try{
            // Establecer la unidad de persistencia para MySQL
            UniGraoVerseController.setPersistenceUnitName("unidad_planetas");
            UniGraoVerse.main.goScene("play");
        }catch(IOException e){
            System.exit(1);
        }
    }

    @FXML
    public void newLoginSQLite(){
        try{
            // Establecer la unidad de persistencia para SQLite
            UniGraoVerseController.setPersistenceUnitName("unidad_sqlite");
            UniGraoVerse.main.goScene("play");
        }catch(IOException e){
            System.exit(1);
        }
    }

    /**
     * It goes to the "credits" scene.
     */
    @FXML
    public void readCredits(){
        try{
            UniGraoVerse.main.goScene("credits");
        }catch(IOException e){
            System.exit(1);
        }
    }

}

