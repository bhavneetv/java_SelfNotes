// Observer Pattern: observer contract for note events.
public interface NoteObserver {
    void update(String action, Note note);
}
