package es.smvarela.githubviewer.events;

import android.graphics.Bitmap;

/**
 * EventBus message. This event is launched when user avatar icon
 * is completely downloaded and ready to be used.
 */
public class MessageAvatarEvent {
    public Bitmap bitmap;

    public MessageAvatarEvent(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
