package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HomeController implements Initializable {
    @FXML
    private AnchorPane container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showComponent("/Fxml/SearchGui.fxml");
    }

    /**
     * show component.
     */
    private void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource(path));
            container.getChildren().clear();
            container.getChildren().add(component);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onActionSearchBtn() {
        showComponent("/Fxml/SearchGui.fxml");
    }

    public void onActionAddBtn() {
        showComponent("/Fxml/AdditionGui.fxml");
    }

    public void onActionTranslateBtn() {
        showComponent("/Fxml/TranslateGui.fxml");
    }

    public void onActionGrammarBtn() {
        showComponent("/Fxml/GrammarGui.fxml");
    }

}
