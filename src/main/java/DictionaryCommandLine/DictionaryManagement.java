package DictionaryCommandLine;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DictionaryManagement {
    private static final String url = "jdbc:sqlite:./src/main/resources/db/dict_hh.db";

    /**
     * search for words starting with keyWord.
     *
     * @return ObservableList of words starting with keyWord.
     */
    public static ObservableList<Word> dbSearchWord(String keyWord, String tableName) {
        ObservableList<Word> list = FXCollections.observableArrayList();
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "SELECT * FROM " + tableName + " WHERE word like " + keyWord + " ORDER BY word";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Word word = new Word(rs.getString("word"),
                        rs.getString("description"),
                        rs.getString("pronounce")
                );
                list.add(word);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    /**
     * delete word from the database.
     */
    public static void dbDeleteWord(String word, String tableName) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);

            stmt = c.createStatement();
            String sql = "DELETE FROM " + tableName + " where word = '" + word + "'";
            int rs = stmt.executeUpdate(sql);
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * insert word in the database.
     */
    public static void dbInsertWord(String word, String pronounce, String description, String tableName) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);

            stmt = c.createStatement();
            String sql = "INSERT INTO " + tableName + " (word, pronounce, description) values ('" + word + "', '"
                    + pronounce + "', '"
                    + description + "')";
            int rs = stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * count word.
     */
    public static int dbCountWord(String keyWord, String tableName) {
        int countRes = 0;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "SELECT COUNT(word) FROM " + tableName + " WHERE word = '" + keyWord + "'";
            ResultSet rs = stmt.executeQuery(sql);
            countRes = rs.getInt(1);
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return countRes;
    }

    /**
     * update word in the database.
     */
    public static void dbUpdateWord(String descriptionEdited, String word, String tableName) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);

            stmt = c.createStatement();
            String sql = "UPDATE " + tableName + " set description = '" + descriptionEdited
                    + "' where word = '" + word + "'";
            int rs = stmt.executeUpdate(sql);
            System.out.println(rs);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * text to speech.
     */
    public static void textToSpeech(String text) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();
            voice.speak(text);
        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }
    }

}
