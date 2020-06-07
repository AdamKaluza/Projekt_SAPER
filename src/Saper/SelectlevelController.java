package Saper;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectlevelController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Button easy;

    @FXML
    private Button medium;

    @FXML
    private Button hard;

    @FXML
    void
    easy(javafx.event.ActionEvent actionEvent) throws IOException {
        Generate easyMode = new Generate();
        easyMode.setALL(400,400);
        Stage stage = new Stage();
        stage.setResizable(false);
        easyMode.scene = new Scene(easyMode.createContent());
        stage.setScene(easyMode.scene);
        stage.setTitle("Minesweeper");
        stage.show();
    }
    @FXML
    void medium(javafx.event.ActionEvent actionEvent) throws IOException {
        Generate mediumMode = new Generate();
        mediumMode.setALL(400,600);
        Stage stage = new Stage();
        stage.setResizable(false);
        mediumMode.scene = new Scene(mediumMode.createContent());
        stage.setScene(mediumMode.scene);
        stage.setTitle("Minesweeper");
        stage.show();

    }

    @FXML
    void hard(javafx.event.ActionEvent actionEvent) throws IOException {

        Generate hardMode = new Generate();
        hardMode.setALL(600,800);
        Stage stage = new Stage();
        stage.setResizable(false);
        hardMode.scene = new Scene(hardMode.createContent());
        stage.setScene(hardMode.scene);
        stage.setTitle("Minesweeper");
        stage.show();

    }

    @FXML
    void back(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane goBack = FXMLLoader.load(getClass().getResource("/FXML/menu.fxml"));
        root.getChildren().addAll(goBack);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}



