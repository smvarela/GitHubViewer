package es.smvarela.githubviewer.model.network.rest;

import java.util.List;

import es.smvarela.githubviewer.model.Repository;
import es.smvarela.githubviewer.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Create interface to be consumed by Retrofit with
 * GitHub API user and user repositories paths
 */
public interface GithubAPI {
    @GET("/users/{user}")
    Call<User> loadUser(@Path("user") String user);

    @GET("/users/{username}/repos")
    Call<List<Repository>> loadRepos(@Path("username") String username);
}
