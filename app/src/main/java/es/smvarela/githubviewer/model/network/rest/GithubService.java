package es.smvarela.githubviewer.model.network.rest;

abstract class GithubService {
    static final String BASE_URL = "https://api.github.com/";

    abstract void start(String user);
}
