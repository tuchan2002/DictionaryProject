package Controllers;

import DictionaryCommandLine.DictionaryManagement;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AdditionController implements Initializable {
    @FXML
    private TextField addField1, addField2;
    @FXML
    private TextArea addArea;
    @FXML
    private Pane notify;
    @FXML
    private Label title, message;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notify.setVisible(false);
    }

    /**
     * MouseClicked SaveBtn
     */
    public void onActionSaveBtn() {
        String word = addField1.getText().toLowerCase();
        String pronounce = addField2.getText();
        String description = addArea.getText();

        int countWord = DictionaryManagement.dbCountWord(word, "av");
        System.out.println(countWord);

        if (word.trim().equals("") || description.trim().equals("")) {
            title.setText("Thêm thất bại");
            title.setTextFill(Paint.valueOf("#fa314a"));
            message.setText("Điền thiếu thông tin");
            message.setTextFill(Paint.valueOf("#fa314a"));
        } else if (countWord > 0) {
            title.setText("Thêm thất bại");
            title.setTextFill(Paint.valueOf("#fa314a"));
            message.setText("Từ này đã tồn tại");
            message.setTextFill(Paint.valueOf("#fa314a"));
        } else {
            DictionaryManagement.dbInsertWord(word, pronounce, description, "av");
            title.setText("Thêm thành công");
            title.setTextFill(Paint.valueOf("#2ecc71"));
            message.setText("Cảm ơn đã đóng góp");
            message.setTextFill(Paint.valueOf("#2ecc71"));
        }
        addField1.clear();
        addField2.clear();
        addArea.clear();

        /* Animation */
        notify.setVisible(true);
        FadeTransition fade = new FadeTransition(Duration.millis(50), notify);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
        fade.setOnFinished(event -> {
            FadeTransition fade1 = new FadeTransition(Duration.millis(1400), notify);
            fade1.setByValue(1);
            fade1.play();
            fade1.setOnFinished(event1 -> {
                FadeTransition fade2 = new FadeTransition(Duration.millis(150), notify);
                fade2.setFromValue(1);
                fade2.setToValue(0);
                fade2.play();
                fade2.setOnFinished(event2 -> {
                    notify.setVisible(false);
                });
            });
        });
    }

}
