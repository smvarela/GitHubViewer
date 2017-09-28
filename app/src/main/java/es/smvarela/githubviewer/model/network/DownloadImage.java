package es.smvarela.githubviewer.model.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import es.smvarela.githubviewer.events.MessageAvatarEvent;

/**
 * This class calls downloads the user avatar icon from GitHub. When the file is downloaded sends
 * an EventBus messages to draw the icon in the UserActivity.
 */
public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... URL) {

        String imageURL = URL[0];
        int responseCode = -1;
        Bitmap myBitmap = null;
        try {
            java.net.URL url = new URL(imageURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);
                input.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return myBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        EventBus.getDefault().post(new MessageAvatarEvent(result));
    }
}
