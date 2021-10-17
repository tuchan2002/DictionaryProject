package Controllers;

import com.darkprograms.speech.translator.GoogleTranslate;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TranslateController {
    private final String VIETNAMESE = "vi";
    private final String ENGLISH = "en";

    @FXML
    private TextArea sourceDoc, targetDoc;
    @FXML
    private Pane rightFlat, leftFlat;
    private String langTarget = "vi";

    public static String googleTranslate(String languageTarget, String target) {
        String targetTranslated = null;

        try {
            targetTranslated = GoogleTranslate.translate(languageTarget, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return targetTranslated;
    }

    public void onActionTranslate() {
        String source = sourceDoc.getText();
        source = source.replace(". ", ",");
        String target = googleTranslate(langTarget, source);
        targetDoc.setText(target);
    }

    public void onMouseClickedSwapEnVn() {
        if (langTarget.compareTo(VIETNAMESE) == 0) {
            rightFlat.setLayoutX(618);
            leftFlat.setLayoutX(100);
            langTarget = ENGLISH;
        } else {
            rightFlat.setLayoutX(100);
            leftFlat.setLayoutX(618);
            langTarget = VIETNAMESE;
        }
        sourceDoc.clear();
        targetDoc.clear();
    }

}
