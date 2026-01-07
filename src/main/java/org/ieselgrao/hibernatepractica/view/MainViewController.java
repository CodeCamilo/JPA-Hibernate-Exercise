package org.ieselgrao.hibernatepractica.view;

import javafx.fxml.FXML;
import org.ieselgrao.hibernatepractica.UniGraoVerse;

import java.io.IOException;

public class MainViewController {

    /**
     * It goes to the "play" scene with MySQL Steup
     */
    @FXML
    public void newLoginMySQL(){
        try{
            UniGraoVerse.main.goScene("play");
            // Todo: setup the unit persistence name (probably one line is enough)
        }catch(IOException e){
            System.exit(1);
        }
    }
    @FXML
        public void newLoginSQLite(){
            try{
                UniGraoVerse.main.goScene("play");
                // Todo: setup the unit persistence name (probably one line is enough)
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
