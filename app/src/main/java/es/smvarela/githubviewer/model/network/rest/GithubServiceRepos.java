package es.smvarela.githubviewer.model.network.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import es.smvarela.githubviewer.events.MessageRepoListEvent;
import es.smvarela.githubviewer.model.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class downloads all the user public repositories from GitHub and returns a list.
 */
public class GithubServiceRepos extends GithubService implements Callback<List<Repository>> {

    public void start(String user) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GithubAPI githubAPI = retrofit.create(GithubAPI.class);

        Call<List<Repository>> call = githubAPI.loadRepos(user);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
        if(response.isSuccessful()) {
            List<Repository> repositoryList = response.body();

            EventBus.getDefault().post(new MessageRepoListEvent(repositoryList));
        } else {
            Log.d("repos", response.errorBody().toString());
        }
    }

    @Override
    public void onFailure(Call<List<Repository>> call, Throwable t) {
        t.printStackTrace();
    }
}