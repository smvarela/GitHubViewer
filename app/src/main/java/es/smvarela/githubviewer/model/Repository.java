package es.smvarela.githubviewer.model;


/**
 * Class to model the user repositories in GitHub.
 */
public class Repository {
    String name;
    String language;

    public Repository(String name, String language) {
        this.name = name;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
