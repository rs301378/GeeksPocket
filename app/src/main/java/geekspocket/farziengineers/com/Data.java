package geekspocket.farziengineers.com;

public class Data {
    private String keyword;
    private String keywordDefination;
    private String languageLogo;
    private String keywordExample;
    public Data() {
    }

    public Data(String keyword, String keywordDefination, String languageLogo, String keywordExample) {
        this.keyword = keyword;
        this.keywordDefination = keywordDefination;
        this.languageLogo = languageLogo;
        this.keywordExample = keywordExample;
    }

    public Data(String keyword, String keywordDefination) {
        this.keyword = keyword;
        this.keywordDefination = keywordDefination;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getKeywordDefination() {
        return keywordDefination;
    }

    public String getLanguageLogo() {
        return languageLogo;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setKeywordDefination(String keywordDefination) {
        this.keywordDefination = keywordDefination;
    }
    public void setLanguageLogo(String languageLogo) {
        this.languageLogo = languageLogo;
    }

    public void setKeywordExample(String keywordExample) {
        this.keywordExample = keywordExample;
    }

    public String getKeywordExample() {
        return keywordExample;
    }
}
