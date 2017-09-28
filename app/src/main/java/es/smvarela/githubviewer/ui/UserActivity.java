package es.smvarela.githubviewer.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.smvarela.githubviewer.model.network.DownloadImage;
import es.smvarela.githubviewer.R;
import es.smvarela.githubviewer.ui.adapter.ReposAdapter;
import es.smvarela.githubviewer.events.MessageAvatarEvent;
import es.smvarela.githubviewer.events.MessageRepoListEvent;
import es.smvarela.githubviewer.model.Repository;
import es.smvarela.githubviewer.model.network.rest.GithubServiceRepos;

public class UserActivity extends AppCompatActivity {
    private TextView login;
    private CircleImageView avatar;
    private ProgressBar progressBar;
    private ReposAdapter adapter;
    private boolean imageDownloadEnd, reposDownloadEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // Load widgets
        login = (TextView) findViewById(R.id.txtvw_username);
        avatar = (CircleImageView) findViewById(R.id.imgvw_avatar);
        progressBar = (ProgressBar) findViewById(R.id.progressBarRepos);
        progressBar.setVisibility(View.VISIBLE);

        // Initialize control variables
        imageDownloadEnd = false;
        reposDownloadEnd = false;

        // get username an avatar icon name
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String username = extras.getString("user_name");
            login.setText(username);

            // Download user avatar image from GitHub
            String URL = extras.getString("user_avatar");
            new DownloadImage().execute(URL);

            // Download repository list from GitHub
            GithubServiceRepos githubServiceRepos = new GithubServiceRepos();
            githubServiceRepos.start(username);
        }

        // Construct the data source
        ArrayList<Repository> arrayOfRepos = new ArrayList<Repository>();

        // Create the adapter to convert the array to views
        adapter = new ReposAdapter(this, arrayOfRepos);

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lstvw_repos);
        listView.setAdapter(adapter);
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
    public void onMessageEvent(MessageAvatarEvent event) {
        avatar.setImageBitmap(event.getBitmap());

        imageDownloadEnd = true;
        if(imageDownloadEnd && reposDownloadEnd){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Subscribe
    public void onMessageRepoListEvent(MessageRepoListEvent event) {
        List<Repository> repositories = event.getRepositories();
        for (Repository repo:repositories) {
            adapter.add(repo);
        }

        reposDownloadEnd = true;
        if(imageDownloadEnd && reposDownloadEnd){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
