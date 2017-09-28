package es.smvarela.githubviewer.events;

/**
 * EventBus message. This event is launched when user is not validated by GitHub API
 */
public class MessageUserFailedEvent {
    public String message;

    public MessageUserFailedEvent(String message) {
        this.message = message;
    }
}
