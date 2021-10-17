package Controllers;

import DictionaryCommandLine.GrammarItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class GrammarController implements Initializable {
    @FXML
    private ListView<GrammarItem> listGrammar;
    @FXML
    private TextArea explanation;

    private ObservableList<GrammarItem> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:./src/main/resources/db/dict_hh.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "SELECT * FROM grammar";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                String explanation = rs.getString("explanation");
                GrammarItem grammarItem = new GrammarItem(name, explanation);
                list.add(grammarItem);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        listGrammar.setItems(list);
    }

    /**
     * MouseClicked list of grammars.
     */
    public void onMouseClickListView() {
        GrammarItem selectedGrammar = listGrammar.getSelectionModel().getSelectedItem();
        explanation.setText(selectedGrammar.getExplanation());
    }

}
