package Saper;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RulesController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private TextArea textRules;

    @FXML
    void back(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane rules = FXMLLoader.load(getClass().getResource("/FXML/menu.fxml"));
        root.getChildren().addAll(rules);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
