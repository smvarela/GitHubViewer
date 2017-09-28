package es.smvarela.githubviewer.events;

import es.smvarela.githubviewer.model.User;

/**
 * EventBus message. This event is launched when user name and user avatar name
 * are downloaded from Github.
 */
public class MessageUserEvent {
    public User user;

    public MessageUserEvent(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
