// Purpose: Observer interface for note add/delete notification events.
public interface NoteObserver {
    void update(String action, User user, Note note);
}
