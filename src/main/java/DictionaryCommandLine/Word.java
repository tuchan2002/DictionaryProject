package DictionaryCommandLine;

public class Word {
    private String wordTarget;
    private String wordExplain;
    private String pronounce;

    public Word() {
        this.wordTarget = "";
        this.wordExplain = "";
        this.pronounce = "";
    }

    public Word(String wordTarget, String wordExplain, String pronounce) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
        this.pronounce = pronounce;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    @Override
    public String toString() {
        return wordTarget;
    }
}
