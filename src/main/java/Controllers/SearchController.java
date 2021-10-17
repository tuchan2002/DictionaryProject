package Controllers;

import DictionaryCommandLine.DictionaryManagement;
import DictionaryCommandLine.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private final String tableNameInSearchGui = "av";

    @FXML
    private AnchorPane editTab;
    @FXML
    private TextArea editTabTextArea;
    @FXML
    private Label editTabLabel;
    @FXML
    private ListView<Word> listResult;
    @FXML
    private TextArea definitionArea;
    @FXML
    private TextField searchField;
    @FXML
    private Label pronounceWord;

    private ObservableList<Word> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list = DictionaryManagement.dbSearchWord("'bell%'", tableNameInSearchGui);
        listResult.setItems(list);

        editTab.setVisible(false);
    }

    /**
     * onActionSearchBtn.
     */
    public void onActionSearchBtn() {
        list = DictionaryManagement.dbSearchWord("'" + searchField.getText().toLowerCase().trim() + "%'",
                tableNameInSearchGui);
        listResult.setItems(list);
    }

    /**
     * onMouseClickListView.
     */
    public void onMouseClickListView() {
        Word selectedWord = listResult.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            definitionArea.setText(selectedWord.getWordExplain());
            pronounceWord.setText(selectedWord.getPronounce());
            editTabLabel.setText(selectedWord.getWordTarget());
        }
    }

    /**
     * onMouseClicked XBtn in editTab.
     */
    public void onMouseClickedXBtn() {
        editTab.setVisible(false);
        editTabTextArea.clear();
    }

    /**
     * MouseClicked editBtn.
     */
    public void onActionEditWord() {
        Word selectedWord = listResult.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            editTab.setVisible(true);
        }
    }

    /**
     * MouseClicked confirmBtn in editTab.
     */
    public void onActionConfirmBtn() {
        String descriptionEdited = editTabTextArea.getText().toLowerCase().trim();

        if (!descriptionEdited.equals("")) {
            Word selectedWord = listResult.getSelectionModel().getSelectedItem();
            String word = selectedWord.getWordTarget();
            DictionaryManagement.dbUpdateWord(descriptionEdited, word, tableNameInSearchGui);

            editTab.setVisible(false);
            definitionArea.setText(descriptionEdited);
            editTabTextArea.clear();
        }
    }

    /**
     * MouseClicked deleteBtn.
     */
    public void onActionDeleteWord() {
        Word selectedWord = listResult.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            String word = selectedWord.getWordTarget();
            DictionaryManagement.dbDeleteWord(word, tableNameInSearchGui);

            int selectedIdx = listResult.getSelectionModel().getSelectedIndex();
            int newSelectedIdx = (selectedIdx == listResult.getItems().size() - 1) ? selectedIdx - 1 : selectedIdx;
            listResult.getItems().remove(selectedIdx);
            listResult.getSelectionModel().select(newSelectedIdx);

            System.out.println(newSelectedIdx);
            if (newSelectedIdx != -1) {
                selectedWord = listResult.getSelectionModel().getSelectedItem();
                pronounceWord.setText(selectedWord.getWordTarget());
                definitionArea.setText(selectedWord.getWordExplain());
            } else {
                pronounceWord.setText("");
                definitionArea.clear();
            }
        }
    }

    /**
     * MouseClicked speakerBtn.
     */
    public void onActionSpeakerBtn() {
        Word selectedWord = listResult.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            DictionaryManagement.textToSpeech(selectedWord.getWordTarget());
        }
    }

}
