package es.smvarela.githubviewer.events;

import java.util.List;
import es.smvarela.githubviewer.model.Repository;

/**
 * EventBus message. This event is launched when all user repositories are
 * completely downloaded and ready to be used.
 */
public class MessageRepoListEvent {
    public List<Repository> repositories;

    public MessageRepoListEvent(List<Repository> repositories) {
        this.repositories = repositories;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }
}
