package es.smvarela.githubviewer.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import es.smvarela.githubviewer.R;
import es.smvarela.githubviewer.events.MessageUserEvent;
import es.smvarela.githubviewer.events.MessageUserFailedEvent;
import es.smvarela.githubviewer.model.User;
import es.smvarela.githubviewer.model.network.rest.GithubServiceUser;
import es.smvarela.githubviewer.ui.dialogs.DialogConnectionError;
import es.smvarela.githubviewer.ui.dialogs.DialogUserError;


public class MainActivity extends AppCompatActivity {
    private Button searchButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar !=  null) {
            setSupportActionBar(toolbar);
            // Remove default title text
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        // Load widgets
        searchButton = findViewById(R.id.searchButton);
        progressBar = findViewById(R.id.progressBar);

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getGithubData(view);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onMessageEvent(MessageUserEvent event) {
        // The user exists in GitHub and can start a new Activity with repositories list
        passUserToActivity(event.getUser());
    }

    @Subscribe
    public void onMessageEvent(MessageUserFailedEvent event) {
        // The user doesn't exist in GitHub
        showUserError();
    }

    private void getGithubData(View view){
        progressBar.setVisibility(View.VISIBLE);

        if(isInternetConnected()){
            EditText inputEditText = (EditText) findViewById(R.id.textInputLayout);
            String user = inputEditText.getText().toString().trim();

            GithubServiceUser githubServiceUser = new GithubServiceUser();
            githubServiceUser.start(user);
        }else{
            showConnectionError();
        }
    }

    // No connection available
    private void showConnectionError() {
        progressBar.setVisibility(View.INVISIBLE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogConnectionError dialogConnectionError = new DialogConnectionError();
        dialogConnectionError.show(fragmentManager, "tagConnectionError");
    }

    // The user don't exists
    private void showUserError() {
        progressBar.setVisibility(View.INVISIBLE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogUserError dialogUserError = new DialogUserError();
        dialogUserError.show(fragmentManager, "tagUserError");
    }

    public void passUserToActivity(User user){
        progressBar.setVisibility(View.INVISIBLE);

        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra("user_name", user.getLogin());
        intent.putExtra("user_avatar", user.getAvatar_url());
        startActivity(intent);
    }

    // Check if device is connected or not to internet
    public boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
