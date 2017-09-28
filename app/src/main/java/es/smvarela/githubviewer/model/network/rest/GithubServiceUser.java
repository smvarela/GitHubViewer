package es.smvarela.githubviewer.model.network.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import es.smvarela.githubviewer.events.MessageUserEvent;
import es.smvarela.githubviewer.events.MessageUserFailedEvent;
import es.smvarela.githubviewer.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class calls the GitHub API with an username. If is successful returns the user login
 * and their avatar icon and sends an EventBus message. If the user doesn't exist, sends another
 * EventBus messages to display a Dialog user error.
 */
public class GithubServiceUser extends GithubService implements Callback<User> {

    public void start(String user) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GithubAPI githubAPI = retrofit.create(GithubAPI.class);

        Call<User> call = githubAPI.loadUser(user);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()) {
            User user = response.body();

            EventBus.getDefault().post(new MessageUserEvent(user));
        } else {
            EventBus.getDefault().post(new MessageUserFailedEvent("User fails"));
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        t.printStackTrace();
    }
}