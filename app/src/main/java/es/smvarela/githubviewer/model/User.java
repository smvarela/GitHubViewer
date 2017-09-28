package es.smvarela.githubviewer.model;

/**
 * Class to model a user in GitHub.
 */
public class User {
    String login;
    String avatar_url;

    public String getLogin() {
        return login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }
}
